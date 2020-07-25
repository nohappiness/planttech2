package net.kaneka.planttech2.addons.jei.infuser;

import com.mojang.blaze3d.matrix.MatrixStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.kaneka.planttech2.addons.jei.libs.AbstractJeiCategory;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.TranslationTextComponent;

public class InfuserCategory extends AbstractJeiCategory<InfuserRecipe>
{
	protected static final int INPUT = 0;
	protected static final int OUTPUT = 1;
	
	
	public InfuserCategory(IGuiHelper helper)
	{
		super("infuser", ModBlocks.INFUSER.asItem(), helper, 32, 128, 60, 26);
	}


	@Override
	public Class<? extends InfuserRecipe> getRecipeClass()
	{
		return InfuserRecipe.class;
	}


	@Override
	public void setIngredients(InfuserRecipe recipe, IIngredients ingredients)
	{
		ingredients.setInput(VanillaTypes.ITEM, recipe.getInputStack());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}


	@Override
	public void setRecipe(IRecipeLayout recipeLayout, InfuserRecipe recipe, IIngredients ingredients)
	{
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(INPUT, true, 0, 0);
		stacks.init(OUTPUT, false, 42, 0);
		stacks.set(ingredients);
	}
	
	@Override
	public void draw(InfuserRecipe recipe, MatrixStack mStack, double mouseX, double mouseY)
	{
		int biomass = recipe.getBiomass();
		if (biomass > 0) {
			String biomassString = biomass + " " + new TranslationTextComponent("fluid.biomass").getString();
			Minecraft minecraft = Minecraft.getInstance();
			FontRenderer fontRenderer = minecraft.fontRenderer;
			int stringWidth = fontRenderer.getStringWidth(biomassString);
			fontRenderer.drawString(mStack, biomassString, (background.getWidth() - stringWidth)/2, 19, 0xFF808080);
		}
		
	}

	
}
