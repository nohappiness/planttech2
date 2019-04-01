package net.kaneka.planttech2.items;

import java.util.Random;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.librarys.utils.Drop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemThermometer extends ItemBase
{

	public ItemThermometer()
	{
		super("thermometer", new Item.Properties().group(PlantTechMain.groupmain));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		if(worldIn.isRemote)
		{
			playerIn.sendMessage(new TextComponentString(new TextComponentTranslation("text.biometemperature").getUnformattedComponentText() + ": " + EnumTemperature.byValue(worldIn.getBiome(playerIn.getPosition()).getDefaultTemperature()).getDisplayString()));
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
