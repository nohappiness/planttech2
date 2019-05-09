package net.kaneka.planttech2.items.upgradeable;


import net.kaneka.planttech2.items.ItemBase;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.item.Item;

public class ItemUpgradeChip extends ItemBase
{
	
	private int energyCost; 
	

	public ItemUpgradeChip(String name)
	{
		super(name, new Item.Properties().group(ModCreativeTabs.groupchips));
	}

}
