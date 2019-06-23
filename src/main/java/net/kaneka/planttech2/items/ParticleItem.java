package net.kaneka.planttech2.items;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ParticleItem extends BaseItem
{
	private String entryName;
	
	public ParticleItem(String name)
	{
		super(name + "_particles",new Item.Properties().group(ModCreativeTabs.groupparticles));
		this.entryName = name; 
	}
	
	private String getEntryName()
	{
		return entryName;
	}
	
	public static class ColorHandler implements IItemColor 
	{
		@Override
		public int getColor(ItemStack stack, int tintIndex)
		{
		    return PlantTechMain.croplist.getEntryByName(((ParticleItem) stack.getItem()).getEntryName()).getSeedColor();
		}
	}
}
