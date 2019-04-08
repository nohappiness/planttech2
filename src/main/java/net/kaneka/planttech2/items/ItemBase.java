package net.kaneka.planttech2.items;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class ItemBase extends Item
{
    protected String name;

    public ItemBase(String name, Properties property)
    {
	super(property);
	this.name = name;
	setRegistryName(name);
	
	ModItems.ITEMS.add(this);
    }
}
