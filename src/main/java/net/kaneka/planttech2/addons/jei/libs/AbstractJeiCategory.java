package net.kaneka.planttech2.addons.jei.libs;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AbstractJeiCategory<T extends Recipe<Container>> implements IRecipeCategory<T>
{
	protected final static ResourceLocation TEXTURE = new ResourceLocation(PlantTechMain.MODID, "textures/gui/jei/jeibackground.png");;
    protected String localizedName; 
    protected ResourceLocation UID; 
    private final IDrawable icon;
    protected IDrawable background; 
    
    public AbstractJeiCategory(String name,Item icon, IGuiHelper helper, int u, int v, int width, int height)
    {
    	this.UID = new ResourceLocation(PlantTechMain.MODID, name); 
    	this.background = helper.createDrawable(TEXTURE, u, v, width, height); 
    	this.icon = helper.createDrawableIngredient(new ItemStack(icon));
    	localizedName = new TranslatableComponent("planttech2." + name).getString();
    }

	@Override
	public ResourceLocation getUid()
	{
		return UID;
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
}
