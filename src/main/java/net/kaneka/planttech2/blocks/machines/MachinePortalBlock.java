package net.kaneka.planttech2.blocks.machines;

import net.kaneka.planttech2.blocks.BaseBlock;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.kaneka.planttech2.world.planttopia.ModDimensionPlantTopia;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SServerDifficultyPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.hooks.BasicEventHooks;

public class MachinePortalBlock extends BaseBlock {

	public MachinePortalBlock() {
		super(Block.Properties.create(Material.IRON).hardnessAndResistance(100f, 100f), "planttopia_portal", ModCreativeTabs.groupmachines, true);
	}
	
	@Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult rts) {
        if(!worldIn.isRemote) {
            //FROM OVERWORLD TO MINING DIM
            if (worldIn.getDimension().getType().getId() == DimensionType.OVERWORLD.getId()) {
                World otherWorld = worldIn.getServer().getWorld(ModDimensionPlantTopia.getDimensionType());
                otherWorld.getBlockState(pos);
                BlockPos otherWorldPos = otherWorld.getHeight(Heightmap.Type.WORLD_SURFACE, pos);
                boolean foundBlock = false;
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(0, 0, 0);

                for (int y = 0; y < 256; y++) {
                    for (int x = pos.getX() - 6; x < pos.getX() + 6; x++) {
                        for (int z = pos.getZ() - 6; z < pos.getZ() + 6; z++) {
                            mutableBlockPos.setPos(x,y,z);
                            if (otherWorld.getBlockState(mutableBlockPos).getBlock() == ModBlocks.PLANTTOPIA_PORTAL) {
                                otherWorldPos = new BlockPos(x, y + 1, z);
                                foundBlock = true;
                                break;
                            }
                        }
                    }
                }
                if (foundBlock){
                    changeDim(((ServerPlayerEntity) playerIn), otherWorldPos, ModDimensionPlantTopia.getDimensionType());
                }
                if (!foundBlock){
                    otherWorld.setBlockState(otherWorldPos.down(), ModBlocks.PLANTTOPIA_PORTAL.getDefaultState());
                    changeDim(((ServerPlayerEntity) playerIn), otherWorldPos, ModDimensionPlantTopia.getDimensionType());
                }
            }

            //FROM MINING DIM TO OVERWORLD
            if (worldIn.getDimension().getType() == ModDimensionPlantTopia.getDimensionType()) {
                World overWorld = worldIn.getServer().getWorld(DimensionType.OVERWORLD);
                overWorld.getBlockState(pos);
                BlockPos overWorldPos = overWorld.getHeight(Heightmap.Type.WORLD_SURFACE, pos);
                boolean foundBlock = false;
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(0, 0, 0);

                for (int y = 0; y < 256; y++) {
                    for (int x = pos.getX() - 6; x < pos.getX() + 6; x++) {
                        for (int z = pos.getZ() - 6; z < pos.getZ() + 6; z++) {
                            mutableBlockPos.setPos(x, y, z);
                            if (overWorld.getBlockState(mutableBlockPos).getBlock() == ModBlocks.PLANTTOPIA_PORTAL) {
                                overWorldPos = new BlockPos(x, y + 1, z);
                                foundBlock = true;
                                break;
                            }
                        }
                    }
                }
                if (foundBlock){
                    changeDim(((ServerPlayerEntity) playerIn), overWorldPos, DimensionType.OVERWORLD);
                }
                if (!foundBlock){
                    overWorld.setBlockState(overWorldPos.down(), ModBlocks.PLANTTOPIA_PORTAL.getDefaultState());
                    changeDim(((ServerPlayerEntity) playerIn), overWorldPos, DimensionType.OVERWORLD);
                }
            }

            return true;
        }
        return false;
    }


    public static void changeDim(ServerPlayerEntity player, BlockPos pos, DimensionType type) { // copy from ServerPlayerEntity#changeDimension
        if (!ForgeHooks.onTravelToDimension(player, type)) return;

        DimensionType dimensiontype = player.dimension;

        ServerWorld serverworld = player.server.getWorld(dimensiontype);
        player.dimension = type;
        ServerWorld serverworld1 = player.server.getWorld(type);
        WorldInfo worldinfo = player.world.getWorldInfo();
        player.connection.sendPacket(new SRespawnPacket(type, worldinfo.getGenerator(), player.interactionManager.getGameType()));
        player.connection.sendPacket(new SServerDifficultyPacket(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
        PlayerList playerlist = player.server.getPlayerList();
        playerlist.updatePermissionLevel(player);
        serverworld.removeEntity(player, true);
        player.revive();
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;

        player.setLocationAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);
        serverworld.getProfiler().endSection();
        serverworld.getProfiler().startSection("placing");
        player.setLocationAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);

        serverworld.getProfiler().endSection();
        player.setWorld(serverworld1);
        serverworld1.func_217447_b(player);
        player.connection.setPlayerLocation(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);
        player.interactionManager.setWorld(serverworld1);
        player.connection.sendPacket(new SPlayerAbilitiesPacket(player.abilities));
        playerlist.sendWorldInfo(player, serverworld1);
        playerlist.sendInventory(player);

        for(EffectInstance effectinstance : player.getActivePotionEffects()) {
            player.connection.sendPacket(new SPlayEntityEffectPacket(player.getEntityId(), effectinstance));
        }

        player.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
        BasicEventHooks.firePlayerChangedDimensionEvent(player, dimensiontype, type);
    }

}
