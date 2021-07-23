package net.kaneka.planttech2.recipes;

import net.kaneka.planttech2.recipes.recipeclasses.ChipalyzerRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.minecraft.world.item.crafting.IRecipeType;
import net.minecraft.world.item.crafting.RecipeType;

public final class ModRecipeTypes
{
    public static final RecipeType<CompressorRecipe> COMPRESSING = IRecipeType.register("planttech2:compressing");
    public static final RecipeType<InfuserRecipe> INFUSING = IRecipeType.register("planttech2:infusing");
    public static final RecipeType<ChipalyzerRecipe> CHIPALYZER = IRecipeType.register("planttech2:chipalyzer");
}
