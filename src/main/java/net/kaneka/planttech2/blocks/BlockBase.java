package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;

public class BlockBase extends Block
{
    private String name; 
    private ItemGroup group;

    public BlockBase(Properties property, String name, ItemGroup group)
    {
	super(property);
	this.group = group;
	this.name = name; 
	setRegistryName(name);
	//This is a command
    }

    public Item createItemBlock()
    {
	return new ItemBlock(this, new Item.Properties().group(group)).setRegistryName(name);
    }
}