package net.kaneka.planttech2.addons.jei.machine_growing;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.item.ItemStack;

public class MachineGrowingCategory implements IRecipeCategory<MachineGrowingRecipe>
{

	protected final static ResourceLocation TEXTURE = new ResourceLocation(PlantTechMain.MODID, "textures/gui/jei/jeibackground.png");;
    protected String localizedName; 
    protected ResourceLocation UID; 
    private final IDrawable icon;
    private IDrawable background; 
	
    private static final int BULB = 0; 
    private static final int SHELL = 1; 
    private static final int OUTPUT = 2; 
    
    public MachineGrowingCategory(IGuiHelper helper)
    {
    	this.UID = new ResourceLocation(PlantTechMain.MODID, "machine_growing"); 
    	this.background = helper.createDrawable(TEXTURE, 32, 160, 86, 18);
    	this.icon = helper.createDrawableIngredient(new ItemStack(ModItems.MACHINEBULBREPROCESSOR_BULB));
    	localizedName = new TranslatableComponent("planttech2.machine_growing").getString();
    }

	@Override
	public ResourceLocation getUid()
	{
		return UID;
	}


	@Override
	public Class<? extends MachineGrowingRecipe> getRecipeClass()
	{
		return MachineGrowingRecipe.class;
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
	public void setIngredients(MachineGrowingRecipe recipe, IIngredients ingredients)
	{
		ingredients.setInputs(VanillaTypes.ITEM, recipe.getInputs());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput(0));
	}


	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MachineGrowingRecipe recipe, IIngredients ingredients)
	{
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(BULB, true, 0, 0);
		stacks.init(SHELL, true, 26, 0);
		stacks.init(OUTPUT, false, 68, 0);
		stacks.set(ingredients);
	}

}
