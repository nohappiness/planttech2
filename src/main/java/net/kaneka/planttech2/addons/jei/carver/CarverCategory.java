package net.kaneka.planttech2.addons.jei.carver;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CarverCategory implements IRecipeCategory<CarverRecipe>
{

	protected final static ResourceLocation TEXTURE = new ResourceLocation(PlantTechMain.MODID, "textures/gui/jei/jeibackground.png");;
    protected String localizedName; 
    protected ResourceLocation UID; 
    private final IDrawable icon;
    private IDrawable background; 
	
    private static final int INPUT = 0; 
    private static final int OUTPUT = 1; 
    
    public CarverCategory(IGuiHelper helper)
    {
    	this.UID = new ResourceLocation(PlantTechMain.MODID, "carver"); 
    	this.background = helper.createDrawable(TEXTURE, 32, 0, 60, 18);
    	this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.CARVER));
    	localizedName = new TranslatableComponent("planttech2.carver").getString();
    }

	@Override
	public ResourceLocation getUid()
	{
		return UID;
	}


	@Override
	public Class<? extends CarverRecipe> getRecipeClass()
	{
		return CarverRecipe.class;
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
	public void setIngredients(CarverRecipe recipe, IIngredients ingredients)
	{
		ingredients.setInput(VanillaTypes.ITEM, recipe.getInput(0));
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput(0));
	}


	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CarverRecipe recipe, IIngredients ingredients)
	{
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(INPUT, true, 0, 0);
		stacks.init(OUTPUT, false, 42, 0);
		stacks.set(ingredients);
	}

}
