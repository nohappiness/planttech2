package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(PlantTechMain.MODID)
public class ModSounds
{
    @ObjectHolder("electric_fence_idle") public static SoundEvent ELECTRIC_FENCE_IDLE;

    @ObjectHolder("music_adventure") public static SoundEvent MUSIC_ADVENTURE;
    @ObjectHolder("music_dangerous") public static SoundEvent MUSIC_DANGEROUS;

    public static void registerAll(IForgeRegistry<SoundEvent> registry)
    {
        registry.register(make("electric_fence_idle"));
        registry.register(make("music_adventure"));
        registry.register(make("music_dangerous"));
    }

    private static SoundEvent make(String soundName)
    {
        return new SoundEvent(new ResourceLocation(PlantTechMain.MODID, soundName)).setRegistryName(soundName);
    }
}
