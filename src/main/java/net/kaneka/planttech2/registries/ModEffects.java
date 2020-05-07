package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.effects.RadiationSickness;
import net.minecraft.potion.Effect;
import net.minecraftforge.registries.IForgeRegistry;

public class ModEffects
{
    public static final Effect RADIATION_SICKNESS = new RadiationSickness();

    public static void registerAll(IForgeRegistry<Effect> registry)
    {
        registry.registerAll(
                RADIATION_SICKNESS
        );
    }
}
