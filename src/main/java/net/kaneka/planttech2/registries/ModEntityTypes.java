package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.entities.passive.TechVillagerEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;

public class ModEntityTypes
{
	public static void registerAll(RegistryEvent.Register<EntityType<?>> event)
	{
		event.getRegistry().registerAll(EntityType.Builder.create(TechVillagerEntity::new, EntityClassification.MISC).size(0.6F, 1.95F).setUpdateInterval(3).setTrackingRange(16)
		        .setShouldReceiveVelocityUpdates(true).build(ModReferences.TECHVILLAGER).setRegistryName(ModReferences.TECHVILLAGER));
	}

}
