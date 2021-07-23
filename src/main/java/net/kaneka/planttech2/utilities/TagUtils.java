package net.kaneka.planttech2.utilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Collection;

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
