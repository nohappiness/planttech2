package net.kaneka.planttech2.recipes;

import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.RecipeType;

public class ModRecipeSerializers
{
    public static IRecipeSerializer<CompressorRecipe> COMPRESSING;
    public static IRecipeSerializer<InfuserRecipe> INFUSING;
    
    public static void registerAll()
    {
    	COMPRESSING = IRecipeSerializer.func_222156_a("compressing", new CompressorRecipe.Serializer());
    	INFUSING = IRecipeSerializer.func_222156_a("infusing", new InfuserRecipe.Serializer()); 
    }
}
