package net.kaneka.planttech2.effects;

import net.kaneka.planttech2.entities.capabilities.player.RadiationEffect;
import net.kaneka.planttech2.registries.ModDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class RadiationSickness extends Effect
{
    public RadiationSickness()
    {
        super(EffectType.HARMFUL, 16744256);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
        if (entityLivingBaseIn instanceof PlayerEntity && !entityLivingBaseIn.getEntityWorld().isRemote())
            entityLivingBaseIn.attackEntityFrom(ModDamageSources.RADIATION_SICKNESS, ((RadiationEffect.getCap((ServerPlayerEntity) entityLivingBaseIn)).getLevel() - 1) * 6.0F);
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        int i = 60 >> amplifier;
        if (i > 0)
            return duration % i == 0;
        return true;
    }
}
