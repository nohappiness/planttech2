package net.kaneka.planttech2.entities.passive;

import net.kaneka.planttech2.registries.ModEntityTypes;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

//Example Entity
public class TechPenguinEntity extends CreatureEntity
{
	public TechPenguinEntity(EntityType<? extends TechPenguinEntity> type, World worldIn)
	{
		super(type, worldIn);
	}

	public static EntityType<? extends TechPenguinEntity> getEntityType()
	{
		return (EntityType<? extends TechPenguinEntity>) ModEntityTypes.TECHPENGUINENTITY;
	}
}
