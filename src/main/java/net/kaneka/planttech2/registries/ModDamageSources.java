package net.kaneka.planttech2.registries;

import net.minecraft.world.damagesource.DamageSource;

public class ModDamageSources
{
    public static final DamageSource RADIATION_SICKNESS = new DamageSource("radiation_sickness").bypassArmor().setMagic();

    public static final DamageSource ELECTRIC_FENCE = new DamageSource("electricFence");
}
