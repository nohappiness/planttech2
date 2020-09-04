package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(PlantTechMain.MODID)
public class ModSounds
{
    @ObjectHolder("electric_fence_idle") public static SoundEvent ELECTRIC_FENCE_IDLE;


    public static void registerAll(IForgeRegistry<SoundEvent> registry)
    {
        registry.register(make("electric_fence_idle"));
    }

    private static SoundEvent make(String soundName)
    {
        return new SoundEvent(new ResourceLocation(PlantTechMain.MODID, soundName)).setRegistryName(soundName);
    }
}
