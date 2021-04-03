package net.kaneka.planttech2.datagen;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = PlantTechMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        if (event.includeClient())
        {
            gen.addProvider(new Languages(gen));
            gen.addProvider(new BlockModelGenerator(gen, existingFileHelper));
            gen.addProvider(new ItemModelGenerator(gen, existingFileHelper));
        }
        if (event.includeServer())
        {
            gen.addProvider(new Recipes(gen));
            gen.addProvider(new LootTables(gen));
            gen.addProvider(new DefaultCropConfigProvider(gen));
        }
    }
}
