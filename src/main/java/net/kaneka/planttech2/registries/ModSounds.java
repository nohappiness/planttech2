package net.kaneka.planttech2.registries;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModSounds
{
    public static final SoundEvent ELECTRIC_FENCE_IDLE = get("electric_fence_idle");

    private static SoundEvent get(String name)
    {
        return new SoundEvent(new ResourceLocation("planttech2", name)).setRegistryName("planttech2", name);
    }

    public static void registerAll(IForgeRegistry<SoundEvent> registry)
    {
        registry.registerAll(
                ELECTRIC_FENCE_IDLE
        );
    }
}
