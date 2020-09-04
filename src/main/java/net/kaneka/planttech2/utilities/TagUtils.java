package net.kaneka.planttech2.utilities;

import java.util.Collection;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class TagUtils
{
	public static Item getAnyTagItem(ResourceLocation res)
	{
		Item result = Items.AIR;
		Collection<Item> items = ItemTags.getCollection().get(res).getAllElements();
		for (Item item : items)
		{
			result = item;
		}
		return result;
	}
}
