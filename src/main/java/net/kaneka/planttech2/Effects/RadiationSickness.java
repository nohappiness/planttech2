package net.kaneka.planttech2.Effects;

import net.kaneka.planttech2.entities.capabilities.player.RadiationEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

public class RadiationSickness extends Effect
{
    public RadiationSickness()
    {
        super(EffectType.HARMFUL, 16744256);
        setRegistryName("radiation_sickness");
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
        if (entityLivingBaseIn instanceof PlayerEntity)
        {
            entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, (entityLivingBaseIn.getCapability(RadiationEffect.RADIATION_CAPABILITY).orElseThrow(() -> new NullPointerException("Capability Error")).getLevel() - 1) * 2.50F);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        int i = 60 >> amplifier;
        if (i > 0)
        {
            return duration % i == 0;
        }
        else
        {
            return true;
        }
    }
}
