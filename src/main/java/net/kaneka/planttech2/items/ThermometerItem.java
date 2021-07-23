package net.kaneka.planttech2.items;

import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThermometerItem extends Item
{

	public ThermometerItem()
	{
		super(new Item.Properties().tab(ModCreativeTabs.MAIN));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn)
	{
		if (level.isClientSide)
			playerIn.sendMessage(new TextComponent(new TranslatableComponent("text.biometemperature").getString()).append(": ").append(EnumTemperature.byValue(level.getBiomeManager().getBiome(playerIn.blockPosition()).getTemperature(playerIn.blockPosition())).getDisplayString()), playerIn.getUUID());
		return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
	}
}
