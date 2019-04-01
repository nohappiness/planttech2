package net.kaneka.planttech2.utilities;

import java.util.Collection;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class TagUtils
{
    public static Item getAnyTagItem(ResourceLocation res)
    {
	Item returnvalue = null; 
	Collection<Item> items = ItemTags.getCollection().get(res).getAllElements();
	for(Item item: items)
	{
	    returnvalue = item; 
	}
	return returnvalue; 
    }
}
