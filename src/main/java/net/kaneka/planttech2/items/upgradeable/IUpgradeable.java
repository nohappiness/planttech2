package net.kaneka.planttech2.items.upgradeable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public interface IUpgradeable
{
	public void updateNBTValues(ItemStack stack); 
}
