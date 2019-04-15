package net.kaneka.planttech2.items;

import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.item.Item;

public class ItemBase extends Item
{

    public ItemBase(String name, Properties property)
    {
	super(property);
	setRegistryName(name);
	
	ModItems.ITEMS.add(this);
    }
}
