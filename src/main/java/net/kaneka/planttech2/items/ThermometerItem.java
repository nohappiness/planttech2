package net.kaneka.planttech2.items;

import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ThermometerItem extends BaseItem
{

	public ThermometerItem()
	{
		super("thermometer", new Item.Properties().group(ModCreativeTabs.groupmain));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		if(worldIn.isRemote)
		{
			playerIn.sendMessage(new StringTextComponent(new TranslationTextComponent("text.biometemperature").getUnformattedComponentText() + ": " + EnumTemperature.byValue(worldIn.getBiomeManager().getBiome(playerIn.getPosition()).getDefaultTemperature()).getDisplayString(true)));
		}
		return ActionResult.resultPass(playerIn.getHeldItem(handIn));
	}
}
