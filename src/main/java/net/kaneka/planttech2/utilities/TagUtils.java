package net.kaneka.planttech2.utilities;

import java.util.Collection;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

public class TagUtils
{
	public static Item getAnyTagItem(ResourceLocation res)
	{
		Item result = Items.AIR;
		Collection<Item> items = ItemTags.getAllTags().getTag(res).getValues();
		for (Item item : items)
		{
			result = item;
		}
		return result;
	}
}
