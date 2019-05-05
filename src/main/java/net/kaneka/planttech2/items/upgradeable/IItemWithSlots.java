package net.kaneka.planttech2.items.upgradeable;

import net.minecraft.item.ItemStack;

public interface IItemWithSlots
{
	public int getAmountSlots(); 
	
	public int getItemInSlot(ItemStack stack, int slot);
	
	public int setItemInSlot(ItemStack stack, int slot, ItemStack item); 
	
	public int extractEnergy(ItemStack stack, int amount, boolean simulate);
	
	public int receiveEnergy(ItemStack stack, int amount, boolean simulate); 
}
