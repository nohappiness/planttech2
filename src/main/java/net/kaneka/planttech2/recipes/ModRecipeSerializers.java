package net.kaneka.planttech2.recipes;

import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.RecipeSerializers;

public class ModRecipeSerializers
{
    public static IRecipeSerializer<CompressorRecipe> COMPRESSING;
    public static IRecipeSerializer<InfuserRecipe> INFUSING;
    
    public static void registerAll()
    {
    	COMPRESSING = RecipeSerializers.register(new CompressorRecipe.Serializer());
    	INFUSING = RecipeSerializers.register(new InfuserRecipe.Serializer()); 
    }
}
