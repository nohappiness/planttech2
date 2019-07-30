package net.kaneka.planttech2.world;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SServerDifficultyPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.hooks.BasicEventHooks;

public class TeleporterUtilities
{
	public static void changeDimension(World worldIn, BlockPos pos, PlayerEntity playerIn, DimensionType destDim, Block... portalBlock)
	{
		World destination = worldIn.getServer().getWorld(destDim);
		BlockPos surfacePos = trySpawnPortal(destination, pos, destDim, portalBlock);
		changeDim(((ServerPlayerEntity) playerIn), surfacePos, destDim);
	}
	
	private static BlockPos trySpawnPortal(World world, BlockPos pos, DimensionType destDim, Block... portalBlock)
	{
		preLoadChunk(world.getServer(), pos.getX(), pos.getZ(), destDim); 
		BlockPos surfacePos = new BlockPos(pos.getX(), 256, pos.getZ()); 
		for(int y = 0; y < 256; y++)
		{
			if (world.getBlockState(pos.down(y)).isSolid())
			{
				surfacePos = new BlockPos(pos.down(y-2));
				break;
			}
		}
		boolean flag = false; 
		if(surfacePos.getY() < 55)
		{
			surfacePos = new BlockPos(surfacePos.getX(), 90, surfacePos.getZ()); 
			flag = true; 
		}
		
		boolean foundBlock = false;
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(0, 0, 0);

		for (int y = 0; y < 256; y++)
		{
			for (int x = pos.getX() - 6; x < pos.getX() + 6; x++)
			{
				for (int z = pos.getZ() - 6; z < pos.getZ() + 6; z++)
				{
					mutableBlockPos.setPos(x, y, z);
					for(int i = 0; i < portalBlock.length; i++)
					{
    					if (world.getBlockState(mutableBlockPos).getBlock() == portalBlock[i])
    					{
    						surfacePos = new BlockPos(x, y + 1, z);
    						foundBlock = true;
    						break;
    					}
					}
				}
			}
		}
		if(flag)
		{
			for(int y = -1; y <= 0; y++)
			{
				for(int x = -2; x <= 2; x++)
				{
					for(int z = -2; z <= 2; z++)
					{
						world.setBlockState(surfacePos.up(y).north(x).east(z), Blocks.GRASS_BLOCK.getDefaultState());
					}
				}
			}
		}
		if (!foundBlock)
		{
			world.setBlockState(surfacePos.down(), portalBlock[0].getDefaultState());
			world.setBlockState(surfacePos, Blocks.AIR.getDefaultState());
			world.setBlockState(surfacePos.up(), Blocks.AIR.getDefaultState());
		}
		return surfacePos; 
	}

	private static void changeDim(ServerPlayerEntity player, BlockPos pos, DimensionType type)
	{ // copy from ServerPlayerEntity#changeDimension
		if (!ForgeHooks.onTravelToDimension(player, type))
			return;

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

		for (EffectInstance effectinstance : player.getActivePotionEffects())
		{
			player.connection.sendPacket(new SPlayEntityEffectPacket(player.getEntityId(), effectinstance));
		}

		player.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
		BasicEventHooks.firePlayerChangedDimensionEvent(player, dimensiontype, type);
	}
	
	private static boolean preLoadChunk(MinecraftServer server, int x, int z, DimensionType dimensionID) {
       ServerChunkProvider chunkProvider = server.getWorld(dimensionID).getChunkProvider();
        if (chunkProvider.chunkExists(x, z)) 
        {
            return false;
        }
        chunkProvider.func_225313_a(x, z);
        return true;
    }
	
}
