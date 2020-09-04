package net.kaneka.planttech2.recipes;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.recipes.recipeclasses.ChipalyzerRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(PlantTechMain.MODID)
public class ModRecipeSerializers
{
    @ObjectHolder("compressing") public static CompressorRecipe.Serializer COMPRESSOR;
    @ObjectHolder("infusing") public static InfuserRecipe.Serializer INFUSER;
    @ObjectHolder("chipalyzer") public static ChipalyzerRecipe.Serializer CHIPALYZER;

    public static void registerAll(RegistryEvent.Register<IRecipeSerializer<?>> event)
    {
        event.getRegistry().register(new CompressorRecipe.Serializer().setRegistryName("compressing"));
        event.getRegistry().register(new InfuserRecipe.Serializer().setRegistryName("infusing"));
        event.getRegistry().register(new ChipalyzerRecipe.Serializer().setRegistryName("chipalyzer"));
    }
}
