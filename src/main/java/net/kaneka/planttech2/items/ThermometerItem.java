package net.kaneka.planttech2.items;

import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.InteractionResultHolder;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import InteractionResultHolder;

public class ThermometerItem extends Item
{

	public ThermometerItem()
	{
		super(new Item.Properties().tab(ModCreativeTabs.MAIN));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		if (worldIn.isClientSide)
			playerIn.sendMessage(new StringTextComponent(new TranslationTextComponent("text.biometemperature").getString()).append(": ").append(EnumTemperature.byValue(worldIn.getBiomeManager().getBiome(playerIn.blockPosition()).getTemperature(playerIn.blockPosition())).getDisplayString()), playerIn.getUUID());
		return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
	}
}
