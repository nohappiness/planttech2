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

public class ThermometerItem extends Item
{

	public ThermometerItem()
	{
		super(new Item.Properties().tab(ModCreativeTabs.MAIN));
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		if (worldIn.isClientSide)
			playerIn.sendMessage(new StringTextComponent(new TranslationTextComponent("text.biometemperature").getString()).append(": ").append(EnumTemperature.byValue(worldIn.getBiomeManager().getBiome(playerIn.blockPosition()).getTemperature(playerIn.blockPosition())).getDisplayString()), playerIn.getUUID());
		return ActionResult.pass(playerIn.getItemInHand(handIn));
	}
}
