package net.kaneka.planttech2.recipes;

import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.RecipeSerializers;

public class ModRecipeSerializers
{
    public static IRecipeSerializer<CompressorRecipe> COMPRESSING;
    
    public static void registerAll()
    {
	COMPRESSING = RecipeSerializers.register(new CompressorRecipe.Serializer());
    }
}
