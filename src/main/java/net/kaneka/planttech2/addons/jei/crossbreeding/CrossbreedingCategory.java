package net.kaneka.planttech2.addons.jei.crossbreeding;


import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.addons.jei.crossbreeding.CrossbreedingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CrossbreedingCategory implements IRecipeCategory<CrossbreedingRecipe>
{
	protected final static ResourceLocation TEXTURE = new ResourceLocation(PlantTechMain.MODID, "textures/gui/jei/jeibackground.png");;
    protected Component localizedName;
    protected ResourceLocation UID; 
    private final IDrawable icon;
    private IDrawable background; 
	
    private static final int INPUT1 = 0; 
    private static final int INPUT2 = 1; 
    private static final int OUTPUT = 2; 
    
    public CrossbreedingCategory(IGuiHelper helper)
    {
    	this.UID = new ResourceLocation(PlantTechMain.MODID, "crossbreeding"); 
    	this.background = helper.createDrawable(TEXTURE, 32, 96, 78, 18);
    	this.icon = helper.createDrawableIngredient(new ItemStack(ModItems.COLOR_PARTICLES));
    	localizedName = new TranslatableComponent("planttech2.crossbreeding");
    }

	@Override
	public ResourceLocation getUid()
	{
		return UID;
	}


	@Override
	public Class<? extends CrossbreedingRecipe> getRecipeClass()
	{
		return CrossbreedingRecipe.class;
	}


	@Override
	public Component getTitle()
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
	public void setIngredients(CrossbreedingRecipe recipe, IIngredients ingredients)
	{
		ingredients.setInputs(VanillaTypes.ITEM, recipe.getParents());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}


	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CrossbreedingRecipe recipe, IIngredients ingredients)
	{
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(INPUT1, true, 0, 0);
		stacks.init(INPUT2, true, 26, 0);
		stacks.init(OUTPUT, false, 60, 0);
		stacks.set(ingredients);
	}
}