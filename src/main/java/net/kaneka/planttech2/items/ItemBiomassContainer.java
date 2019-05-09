package net.kaneka.planttech2.items;

import java.util.List;

import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemBiomassContainer extends ItemBase
{

    private static int capacity = 1000;

    public ItemBiomassContainer()
    {
	super("biomasscontainer", new Item.Properties().group(ModCreativeTabs.groupmain));
	addPropertyOverride(new ResourceLocation("filled"), (stack, world, player) ->
	{
	    return getFillLevelModel(stack);
	});
    }

    public static float getFillLevelModel(ItemStack stack)
    {
	NBTTagCompound nbt = stack.getTag();
	if (nbt != null)
	{
	    int biomass = nbt.getInt("biomass");
	    
	    if (biomass > (capacity / 9) * 9)
	    {
		return 9F;
	    }
	    else if (biomass > (capacity / 9) * 8)
	    {
		return 8F;
	    }
	    else if (biomass > (capacity / 9) * 7)
	    {
		return 7F;
	    }
	    else if (biomass > (capacity / 9) * 6)
	    {
		return 6F;
	    }
	    else if (biomass > (capacity / 9) * 5)
	    {
		return 5F;
	    }
	    else if (biomass > (capacity / 9) * 4)
	    {
		return 4F;
	    }
	    else if (biomass > (capacity / 9) * 3)
	    {
		return 3F;
	    }
	    else if (biomass > (capacity / 9) * 2)
	    {
		return 2F;
	    }
	    else if (biomass > (capacity / 9) * 1)
	    {
		return 1F;
	    }

	}

	return 0F;
    }

    public int getFillLevel(ItemStack stack)
    {
	NBTTagCompound nbt = stack.getTag();
	if (nbt != null)
	{
	    return nbt.getInt("biomass");
	}
	return 0;
    }

    private void setFillLevel(ItemStack stack, int value)
    {
	NBTTagCompound nbt = stack.getTag();
	if (nbt == null)
	{
	    nbt = new NBTTagCompound();
	}
	nbt.setInt("biomass", value);
	stack.setTag(nbt);
    }

    public int receiveFillLevel(ItemStack stack, int value)
    {
	NBTTagCompound nbt = stack.getTag();
	if (nbt == null)
	{
	    nbt = new NBTTagCompound();
	}
	int biomass = nbt.getInt("biomass");
	int space = capacity - biomass;
	if (space >= value)
	{
	    setFillLevel(stack, biomass + value);
	    return value;
	}
	else
	{
	    setFillLevel(stack, capacity);
	    return space;
	}
    }

    public int extractFillLevel(ItemStack stack, int value)
    {
	NBTTagCompound nbt = stack.getTag();
	if (nbt == null)
	{
	    nbt = new NBTTagCompound();
	}
	int biomass = nbt.getInt("biomass");
	if (biomass >= value)
	{
	    setFillLevel(stack, biomass - value);
	    return value;
	}
	else
	{
	    setFillLevel(stack, 0);
	    return biomass;
	}
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
	NBTTagCompound nbt = stack.getTag();
	if (nbt != null)
	{
	    tooltip.add(new TextComponentString(nbt.getInt("biomass") + "/" + capacity));
	}
	else
	{
	    tooltip.add(new TextComponentString("0/" + capacity));
	}
	super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
