package net.kaneka.planttech2.world.planttopia;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SServerDifficultyPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.hooks.BasicEventHooks;

public class PlantTopiaTeleportManager {

	public static void tele(Entity entity){
		if(!entity.world.isRemote){
			DimensionType type = ModDimensionPlantTopia.getDimensionType();
			if(entity.getRidingEntity()==null && !entity.isBeingRidden()){
				entity.setPortal(new BlockPos(entity.posX,entity.posY,entity.posZ));
				if (entity.timeUntilPortal > 0) {
					entity.timeUntilPortal = 10;
				}else if(entity.dimension != type){
					entity.timeUntilPortal=10;
					changeDim(entity, new BlockPos(entity.posX, entity.posY, entity.posZ), type);
				}else if(entity.dimension == type){
					entity.timeUntilPortal = 10;
					changeDim(entity, new BlockPos(entity.posX, entity.posY, entity.posZ), DimensionType.OVERWORLD);
				}
			}
		}
	}
	
	public static void changeDim(Entity entity, BlockPos pos, DimensionType type) { // copy from ServerPlayerEntity#changeDimension
        if (!ForgeHooks.onTravelToDimension(entity, type)) return;
        DimensionType dimensiontype = entity.dimension;
        ServerWorld serverworld = entity.getServer().getWorld(dimensiontype);
        entity.dimension = type;
        ServerWorld serverworld1 = entity.getServer().getWorld(type);
        WorldInfo worldinfo = entity.world.getWorldInfo();
        if (entity instanceof ServerPlayerEntity) {
        	((ServerPlayerEntity) entity).connection.sendPacket(new SRespawnPacket(type, worldinfo.getGenerator(), ((ServerPlayerEntity) entity).interactionManager.getGameType()));
        	((ServerPlayerEntity) entity).connection.sendPacket(new SServerDifficultyPacket(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
	        PlayerList playerlist = entity.getServer().getPlayerList();
	        playerlist.updatePermissionLevel((ServerPlayerEntity) entity);
	        serverworld.removeEntity(entity, true);
	        entity.revive();
	        float f = entity.rotationPitch;
	        float f1 = entity.rotationYaw;
	
	        entity.setLocationAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);
	        serverworld.getProfiler().endSection();
	        serverworld.getProfiler().startSection("placing");
	        entity.setLocationAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);
	
	        serverworld.getProfiler().endSection();
	        entity.setWorld(serverworld1);
	        serverworld1.func_217447_b(((ServerPlayerEntity) entity));
	        ((ServerPlayerEntity) entity).interactionManager.setWorld(serverworld1);
	        ((ServerPlayerEntity) entity).connection.sendPacket(new SPlayerAbilitiesPacket(((ServerPlayerEntity) entity).abilities));
	        playerlist.sendWorldInfo(((ServerPlayerEntity) entity), serverworld1);
	        playerlist.sendInventory(((ServerPlayerEntity) entity));
	
	        for(EffectInstance effectinstance : ((LivingEntity) entity).getActivePotionEffects()) {
	        	((ServerPlayerEntity) entity).connection.sendPacket(new SPlayEntityEffectPacket(entity.getEntityId(), effectinstance));
	        }
	
	        ((ServerPlayerEntity) entity).connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
	        BasicEventHooks.firePlayerChangedDimensionEvent(((ServerPlayerEntity) entity), dimensiontype, type);
        }/* else {
        	Entity entity1 = entity.getType().create(serverworld1);
            if (entity1 != null) {
               entity1.copyDataFromOld(entity);
               entity1.moveToBlockPosAndAngles(pos, entity.rotationYaw, entity.rotationPitch);
               entity1.setMotion(entity.getMotion());
               serverworld1.func_217460_e(entity);
            }
            entity.remove(true);
	        float f = entity.rotationPitch;
	        float f1 = entity.rotationYaw;
	
	        entity.setLocationAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);
	        serverworld.getProfiler().endSection();
	        serverworld.getProfiler().startSection("placing");
	        entity.setLocationAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);
	
	        serverworld.getProfiler().endSection();
	        entity.setWorld(serverworld1);
        }*/
	}
}