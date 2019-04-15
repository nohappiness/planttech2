package net.kaneka.planttech2.items;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.item.Item;

public class ItemWrench extends ItemBase
{

	public ItemWrench()
	{
		super("wrench", new Item.Properties().maxStackSize(1).group(PlantTechMain.groupmain));
	}
}
