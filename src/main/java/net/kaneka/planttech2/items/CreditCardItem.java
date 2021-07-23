package net.kaneka.planttech2.items;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CreditCardItem extends Item
{

	public CreditCardItem(Properties property)
	{
		super(property);
	}
	
	public static boolean hasCredits(ItemStack stack)
	{
		CompoundTag nbt = getNBT(stack); 
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
	
	public static CompoundTag getNBT(ItemStack stack)
	{
		CompoundTag nbt = stack.getTag(); 
		if(nbt != null)
		{
			return nbt; 
		}
		return new CompoundTag(); 
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
		CompoundTag nbt = getNBT(stack); 
		nbt.putInt("credits", amount); 
		stack.setTag(nbt);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn)
	{
		tooltip.add(new TextComponent("Plantcredits: " + getCredits(stack)));
		super.appendHoverText(stack, level, tooltip, flagIn);
	}

}
