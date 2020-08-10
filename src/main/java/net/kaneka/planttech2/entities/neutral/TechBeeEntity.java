package net.kaneka.planttech2.entities.neutral;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;

public class TechBeeEntity extends BeeEntity
{
    public TechBeeEntity(EntityType<? extends BeeEntity> type, World worldIn)
    {
        super(type, worldIn);
    }
}
