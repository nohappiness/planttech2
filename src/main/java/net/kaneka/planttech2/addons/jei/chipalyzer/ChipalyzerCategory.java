package net.kaneka.planttech2.addons.jei.chipalyzer;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.kaneka.planttech2.addons.jei.libs.AbstractJeiCategory;
import net.kaneka.planttech2.recipes.recipeclasses.ChipalyzerRecipe;
import net.kaneka.planttech2.registries.ModBlocks;

public class ChipalyzerCategory extends AbstractJeiCategory<ChipalyzerRecipe> {
	protected static final int INPUT1 = 0;
	protected static final int INPUT2 = 1;
	protected static final int OUTPUT = 2;

	public ChipalyzerCategory(IGuiHelper helper) {
		super("chipalyzer", ModBlocks.CHIPALYZER.asItem(), helper, 32, 32, 58, 32);
	}

	@Override
	public Class<? extends ChipalyzerRecipe> getRecipeClass()
	{
		return ChipalyzerRecipe.class;
	}

	@Override
	public void setIngredients(ChipalyzerRecipe recipe, IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, recipe.getComponents());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ChipalyzerRecipe recipe, IIngredients ingredients) {
		IGuiIngredientGroup stacks = recipeLayout.getItemStacks();
		stacks.init(INPUT1, true, 0, 14);
		stacks.init(INPUT2, true, 20, 0);
		stacks.init(OUTPUT, false, 40, 14);
		stacks.set(ingredients);
	}
}