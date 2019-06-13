package net.kaneka.planttech2.recipes;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class ModRecipeSerializers
{
    public static IRecipeSerializer<CompressorRecipe> COMPRESSING = new CompressorRecipe.Serializer();
    public static IRecipeSerializer<InfuserRecipe> INFUSING = new InfuserRecipe.Serializer();
    
	public static void registerAll(RegistryEvent.Register<IRecipeSerializer<?>> event)
    {
    	event.getRegistry().registerAll(COMPRESSING.setRegistryName(new ResourceLocation(PlantTechMain.MODID, "compressing")),
    									INFUSING.setRegistryName(new ResourceLocation(PlantTechMain.MODID, "infusing"))); 
    }
}
