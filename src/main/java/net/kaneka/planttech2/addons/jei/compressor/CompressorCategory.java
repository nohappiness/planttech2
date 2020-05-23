package net.kaneka.planttech2.addons.jei.compressor;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.kaneka.planttech2.addons.jei.libs.AbstractJeiCategory;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.registries.ModBlocks;

public class CompressorCategory extends AbstractJeiCategory<CompressorRecipe>
{
	protected static final int INPUT = 0;
	protected static final int OUTPUT = 1;
	
	
	public CompressorCategory(IGuiHelper helper)
	{
		super("compressor", ModBlocks.COMPRESSOR.asItem(), helper, 32, 64, 60, 18);
	}


	@Override
	public Class<? extends CompressorRecipe> getRecipeClass()
	{
		return CompressorRecipe.class;
	}


	@Override
	public void setIngredients(CompressorRecipe recipe, IIngredients ingredients)
	{
		ingredients.setInput(VanillaTypes.ITEM, recipe.getInput());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}


	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CompressorRecipe recipe, IIngredients ingredients)
	{
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(INPUT, true, 0, 0);
		stacks.init(OUTPUT, false, 42, 0);
		stacks.set(ingredients);
	}

	
}
