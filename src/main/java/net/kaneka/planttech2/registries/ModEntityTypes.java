package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.entities.neutral.TechGhoulEntity;
import net.kaneka.planttech2.entities.passive.TechPenguinEntity;
import net.kaneka.planttech2.entities.passive.TechVillagerEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.IForgeRegistry;

public class ModEntityTypes
{
	public static final EntityType<?>
			TECHVILLAGERENTITY = EntityType.Builder.<TechVillagerEntity>create(TechVillagerEntity::new, EntityClassification.MISC)
			.size(0.6F, 1.95F)
			.setUpdateInterval(3)
			.setTrackingRange(16)
			.setShouldReceiveVelocityUpdates(true)
			.build(ModReferences.TECHVILLAGER)
			.setRegistryName(ModReferences.TECHVILLAGER),

			TECHGHOULENTITY = EntityType.Builder.<TechGhoulEntity>create(TechGhoulEntity::new, EntityClassification.MISC)
					.size(0.9F, 1.8F)
					.build(ModReferences.TECHGHOUL)
					.setRegistryName(ModReferences.TECHGHOUL),
			TECHPENGUINENTITY = EntityType.Builder.<TechPenguinEntity>create(TechPenguinEntity::new, EntityClassification.MISC)
					.size(0.5F, 1.5F)
					.build(ModReferences.TECHPENGUIN)
					.setRegistryName(ModReferences.TECHPENGUIN);

	public static void registerAll(IForgeRegistry<EntityType<?>> registry)
	{
		registry.registerAll(
				TECHVILLAGERENTITY,
				TECHGHOULENTITY,
				TECHPENGUINENTITY
		);
	}
}
