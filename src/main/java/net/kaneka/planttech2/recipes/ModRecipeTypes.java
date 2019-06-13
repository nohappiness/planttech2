package net.kaneka.planttech2.recipes;

import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.minecraft.item.crafting.IRecipeType;

public final class ModRecipeTypes
{
    public static final IRecipeType<CompressorRecipe> COMPRESSING = IRecipeType.register("compressing");
    public static final IRecipeType<InfuserRecipe> INFUSING = IRecipeType.register("infusing");
    
}
