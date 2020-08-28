package net.kaneka.planttech2.registries;

import net.minecraft.util.DamageSource;

public class ModDamageSources
{
    public static final DamageSource RADIATION_SICKNESS = new DamageSource("radiation_sickness").setDamageBypassesArmor().setMagicDamage();

    public static final DamageSource ELECTRIC_FENCE = new DamageSource("electricFence");
}
