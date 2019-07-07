package net.kaneka.planttech2.recipes;

import net.kaneka.planttech2.recipes.recipeclasses.ChipalyzerRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.minecraft.item.crafting.IRecipeType;

public final class ModRecipeTypes
{
    public static final IRecipeType<CompressorRecipe> COMPRESSING = IRecipeType.register("planttech2:compressing");
    public static final IRecipeType<InfuserRecipe> INFUSING = IRecipeType.register("planttech2:infusing");
    public static final IRecipeType<ChipalyzerRecipe> CHIPALYZER = IRecipeType.register("planttech2:chipalyzer");
}
