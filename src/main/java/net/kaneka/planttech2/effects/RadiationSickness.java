package net.kaneka.planttech2.effects;

import net.kaneka.planttech2.entities.capabilities.player.RadiationEffect;
import net.kaneka.planttech2.registries.ModDamageSources;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class RadiationSickness extends MobEffect
{
    public RadiationSickness()
    {
        super(MobEffectCategory.HARMFUL, 16744256);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier)
    {
        if (entityLivingBaseIn instanceof ServerPlayer sp && !entityLivingBaseIn.getCommandSenderWorld().isClientSide())
            entityLivingBaseIn.hurt(ModDamageSources.RADIATION_SICKNESS, (RadiationEffect.getCap(sp).getLevel() - 1) * 6.0F);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier)
    {
        int i = 60 >> amplifier;
        if (i > 0)
            return duration % i == 0;
        return true;
    }
}
