package net.kaneka.planttech2.blocks;


import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class BaseBlock extends Block
{
	private String name;
	private ItemGroup group;

	public BaseBlock(Properties property, String name, ItemGroup group, boolean hasItem)
	{
		super(property);
		this.group = group;
		this.name = name;
		setRegistryName(name);

		ModBlocks.BLOCKS.add(this);
		if (hasItem)
		{
			ModBlocks.BLOCKITEMS.add(this);
		}
	}

	public Item createItemBlock()
	{
		return new BlockItem(this, new Item.Properties().group(group)).setRegistryName(name);
	}
}