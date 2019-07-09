package net.kaneka.planttech2.addons.jei.chipalyzer;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.kaneka.planttech2.addons.jei.libs.AbstractJeiCategory;
import net.kaneka.planttech2.recipes.recipeclasses.ChipalyzerRecipe;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.item.ItemStack;

public class ChipalyzerCategory extends AbstractJeiCategory<ChipalyzerRecipe>
{
	protected static final int INPUT1 = 0;
	protected static final int INPUT2 = 2;
	protected static final int OUTPUT = 3;
	
	
	public ChipalyzerCategory(IGuiHelper helper)
	{
		super("chipalyzer", ModBlocks.CHIPALYZER.asItem(), helper, 0, 36, 58, 30);
	}


	@Override
	public Class<? extends ChipalyzerRecipe> getRecipeClass()
	{
		return ChipalyzerRecipe.class;
	}


	@Override
	public void setIngredients(ChipalyzerRecipe recipe, IIngredients ingredients)
	{
		List<ItemStack> list = new ArrayList<ItemStack>(); 
		list.add(recipe.getChip());
		list.add(recipe.getInput()); 
		ingredients.setInputs(VanillaTypes.ITEM, list);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}


	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ChipalyzerRecipe recipe, IIngredients ingredients)
	{
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(INPUT1, true, 0, 12);
		stacks.init(INPUT2, true, 20, 0);
		stacks.init(OUTPUT, false, 40, 12);
		stacks.set(ingredients);
	}

	
}
