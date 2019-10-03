package net.kaneka.planttech2.items.upgradeable;

import net.minecraft.item.ItemStack;

public interface IUpgradeable
{
	public void updateNBTValues(ItemStack stack); 
	public int getSlotId(); 
}
