package net.kaneka.planttech2.recipes;

import net.kaneka.planttech2.recipes.recipeclasses.ChipalyzerRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.minecraft.world.item.crafting.RecipeType;

public final class ModRecipeTypes
{
    public static final RecipeType<CompressorRecipe> COMPRESSING = RecipeType.register("planttech2:compressing");
    public static final RecipeType<InfuserRecipe> INFUSING = RecipeType.register("planttech2:infusing");
    public static final RecipeType<ChipalyzerRecipe> CHIPALYZER = RecipeType.register("planttech2:chipalyzer");
}
