package net.kaneka.planttech2.entities.passive;

import net.kaneka.planttech2.entities.TechCreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

//Example Entity
public class TechPenguinEntity extends TechCreatureEntity
{
	public TechPenguinEntity(EntityType<? extends TechPenguinEntity> type, World worldIn)
	{
		super(type, worldIn);
	}
}
