package net.kaneka.planttech2.recipes;

import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.RecipeType;

public final class ModRecipeTypes
{
    public static final RecipeType<CompressorRecipe> COMPRESSING = RecipeType.get(new ResourceLocation("compressing"), CompressorRecipe.class);
}
