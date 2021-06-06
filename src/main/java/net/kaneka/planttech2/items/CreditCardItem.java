package net.kaneka.planttech2.items;


import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class CreditCardItem extends Item
{

	public CreditCardItem(Properties property)
	{
		super(property);
	}
	
	public static boolean hasCredits(ItemStack stack)
	{
		CompoundNBT nbt = getNBT(stack); 
		if(nbt.contains("credits"))
		{
			return true; 
		}
		return false; 
	}
	
	public static int getCredits(ItemStack stack)
	{
		if(!stack.isEmpty())
		{
			if(stack.getItem() instanceof CreditCardItem)
			{
				if(hasCredits(stack))
				{
					return getNBT(stack).getInt("credits"); 
				}
			}
		}
		return 0; 
	}
	
	public static CompoundNBT getNBT(ItemStack stack)
	{
		CompoundNBT nbt = stack.getTag(); 
		if(nbt != null)
		{
			return nbt; 
		}
		return new CompoundNBT(); 
	}
	
	public static int addCredits(ItemStack stack, int amount)
	{
		int newValue = getCredits(stack) + amount;
		setCredits(stack, newValue);
		return newValue; 
	}
	
	public static int shrinkCredits(ItemStack stack, int amount)
	{
		int newValue = Math.max(getCredits(stack) - amount, 0);
		setCredits(stack, newValue);
		return newValue; 
	}
	
	public static void setCredits(ItemStack stack, int amount)
	{
		CompoundNBT nbt = getNBT(stack); 
		nbt.putInt("credits", amount); 
		stack.setTag(nbt);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(new StringTextComponent("Plantcredits: " + getCredits(stack)));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

}
