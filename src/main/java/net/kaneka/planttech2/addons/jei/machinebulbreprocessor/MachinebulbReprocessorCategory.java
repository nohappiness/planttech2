package net.kaneka.planttech2.addons.jei.machinebulbreprocessor;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.item.ItemStack;

public class MachinebulbReprocessorCategory implements IRecipeCategory<MachinebulbReprocessorRecipe>
{

	protected final static ResourceLocation TEXTURE = new ResourceLocation(PlantTechMain.MODID, "textures/gui/jei/jeibackground.png");;
    protected String localizedName; 
    protected ResourceLocation UID; 
    private final IDrawable icon;
    private IDrawable background; 
	
    private static final int CHIP = 0; 
    private static final int OUTPUT = 1; 
    
    public MachinebulbReprocessorCategory(IGuiHelper helper)
    {
    	this.UID = new ResourceLocation(PlantTechMain.MODID, "machinebulbreprocessor"); 
    	this.background = helper.createDrawable(TEXTURE, 32, 192, 60, 26);
    	this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.MACHINEBULBREPROCESSOR));
    	localizedName = new TranslationTextComponent("planttech2.machinebulbreprocessor").getString();
    }

	@Override
	public ResourceLocation getUid()
	{
		return UID;
	}


	@Override
	public Class<? extends MachinebulbReprocessorRecipe> getRecipeClass()
	{
		return MachinebulbReprocessorRecipe.class;
	}


	@Override
	public String getTitle()
	{
		return localizedName;
	}


	@Override
	public IDrawable getBackground()
	{
		return background;
	}


	@Override
	public IDrawable getIcon()
	{
		return icon;
	}


	@Override
	public void setIngredients(MachinebulbReprocessorRecipe recipe, IIngredients ingredients)
	{
		ingredients.setInput(VanillaTypes.ITEM, recipe.getInput(0));
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput(0));
	}


	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MachinebulbReprocessorRecipe recipe, IIngredients ingredients)
	{
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(CHIP, true, 0, 0);
		stacks.init(OUTPUT, false, 42, 0);
		stacks.set(ingredients);
	}
	
	@Override
	public void draw(MachinebulbReprocessorRecipe recipe, MatrixStack mStack, double mouseX, double mouseY)
	{
		int biomass = recipe.getBiomass();
		if (biomass > 0) {
			String biomassString = biomass + " " + new TranslationTextComponent("fluid.biomass").getString();
			Minecraft minecraft = Minecraft.getInstance();
			FontRenderer fontRenderer = minecraft.font;
			int stringWidth = fontRenderer.width(biomassString);
			fontRenderer.draw(mStack, biomassString, (background.getWidth() - stringWidth)/2, 19, 0xFF808080);
		}
		
	}

}
