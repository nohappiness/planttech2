package net.kaneka.planttech2.energy;

import net.minecraft.item.ItemStack;

public interface IItemChargeable
{
	public int receiveEnergy(ItemStack stack, int amount, boolean simulate);
	
	public int extractEnergy(ItemStack stack, int amount, boolean simulate);
	
	public int maxEnergy(ItemStack stack); 
	
	public int currentEnergy(ItemStack stack); 
}
