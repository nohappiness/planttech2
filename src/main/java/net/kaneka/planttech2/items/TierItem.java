package net.kaneka.planttech2.items;

import net.minecraft.item.Item;

public class TierItem extends Item
{
	private int tier;
	private ItemType itemtype;

	public TierItem(Properties property, int tier, ItemType itemtype)
	{
		super(property);
		this.tier = tier;
		this.itemtype = itemtype;
	}

	public int getTier()
	{
		return tier;
	}

	public ItemType getItemType()
	{
		return itemtype;
	}

	public enum ItemType
	{
		SOLAR_FOCUS,
		RANGE_UPGRADE,
		SPEED_UPGRADE,
		CAPACITY_UPGRADE,
		UPGRADE_CHIP
	}
}
