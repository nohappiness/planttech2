package net.kaneka.planttech2.librarys.utils;

import java.util.Random;

import net.minecraft.item.ItemStack;

public class Drop
{
	int min, max; 
	
	ItemStack drop; 
	
	public Drop(ItemStack stack, int min, int max)
	{
		this.drop = stack; 
		this.min = min; 
		this.max = max; 
	}
	
	public int getAmountDropped(int traitint, int traitmax, Random rand)
	{
		int minAmount = Math.max((int) (((float)traitint/(float)traitmax)*this.max*0.75F), this.min); 
		int maxAmount = Math.max(1,Math.min((int) (((float)traitint/(float)traitmax)*this.max*1.5F), this.max)); 
		int range = maxAmount-minAmount; 
		return rand.nextInt(range + 1) + minAmount; 
	}
	
	public ItemStack getDroppedStack(int traitint, int traitmax, Random rand)
	{
		
		int amountDropped = getAmountDropped(traitint, traitmax, rand);
		if(amountDropped > 0)
		{
			ItemStack result = drop.copy();
			result.setCount(amountDropped);
			return result;
		}
		
		return null; 
	}
	
	public int getMin()
	{
		return this.min; 
	}
	
	public int getMax()
	{
		return this.max; 
	}
	
	public ItemStack getItemStack()
	{
		return drop; 
	}

}
