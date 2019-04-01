package net.kaneka.planttech2.items;

import java.util.List;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemParticle extends ItemBase
{
	private String entryName;
	
	public ItemParticle(String name)
	{
		super(name + "_particles",new Item.Properties().group(PlantTechMain.groupparticles));
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
		    return PlantTechMain.instance.croplist.getEntryByName(((ItemParticle) stack.getItem()).getEntryName()).getSeedColor();
		}
	}
}
