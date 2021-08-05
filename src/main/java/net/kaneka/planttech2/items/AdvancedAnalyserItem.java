package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.entity.CropsBlockEntity;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class AdvancedAnalyserItem extends EnergyStorageItem
{
	private static final int capacity = 1000;

	public AdvancedAnalyserItem()
	{
		super(new Item.Properties().stacksTo(1).tab(ModCreativeTabs.MAIN), capacity);
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx)
	{
		ItemStack stack = ctx.getItemInHand();
		Level level = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		Player player = ctx.getPlayer();
		if (!level.isClientSide && !stack.isEmpty() && player != null)
		{

			if (level.getBlockEntity(pos) instanceof CropsBlockEntity cbe)
			{
				if (cbe.isAnalysed())
				{
					HashMapCropTraits traits = cbe.getTraits();
					sendTraits(player, traits);
				} else
				{
					if (currentEnergy(stack) >= getEnergyCosts())
					{
						extractEnergy(stack, getEnergyCosts(), false);
						HashMapCropTraits traits = cbe.setAnalysedAndGetTraits();
						sendTraits(player, traits);
					} else
					{
						player.sendMessage(new TextComponent("Not enough energy"), player.getUUID());
					}
				}
			}
		}
		return super.useOn(ctx);
	}

	private void sendTraits(Player player, HashMapCropTraits traits)
	{
		player.sendMessage(new TextComponent("--------------------------------------"), player.getUUID());
		player.sendMessage(new TextComponent(new TranslatableComponent("info.type").getString() + ": " + traits.getType()), player.getUUID());
		player.sendMessage(new TextComponent(new TranslatableComponent("info.soil").getString() + ": " + CropSeedItem.getSoilString(traits.getType())), player.getUUID());
		player.sendMessage(new TextComponent(new TranslatableComponent("info.growspeed").getString() + ": " + traits.getTrait(EnumTraitsInt.GROWSPEED)), player.getUUID());
		player.sendMessage(new TextComponent(new TranslatableComponent("info.sensitivity").getString() + ": " + traits.getTrait(EnumTraitsInt.SENSITIVITY)), player.getUUID());
		player.sendMessage(new TextComponent(new TranslatableComponent("info.needed_lightlevel").getString() + ": " + (14 - traits.getTrait(EnumTraitsInt.LIGHTSENSITIVITY))), player.getUUID());
		player.sendMessage(new TextComponent(new TranslatableComponent("info.waterrange").getString() + ": " + (1 + traits.getTrait(EnumTraitsInt.WATERSENSITIVITY))), player.getUUID());
		player.sendMessage(new TextComponent(new TranslatableComponent("info.temperature").getString() + ": ").append(CropSeedItem.temperatureString(traits.getType(), traits.getTrait(EnumTraitsInt.TEMPERATURETOLERANCE))), player.getUUID());
		player.sendMessage(new TextComponent(new TranslatableComponent("info.productivity").getString() + ": " + traits.getTrait(EnumTraitsInt.PRODUCTIVITY)), player.getUUID());
		player.sendMessage(new TextComponent(new TranslatableComponent("info.fertility").getString() + ": " + traits.getTrait(EnumTraitsInt.FERTILITY)), player.getUUID());
		player.sendMessage(new TextComponent(new TranslatableComponent("info.spreedingspeed").getString() + ": " + traits.getTrait(EnumTraitsInt.SPREEDINGSPEED)), player.getUUID());
		player.sendMessage(new TextComponent(new TranslatableComponent("info.genestrength").getString() + ": " + traits.getTrait(EnumTraitsInt.GENESTRENGHT)), player.getUUID());
		player.sendMessage(new TextComponent(new TranslatableComponent("info.energyvalue").getString() + ": " + traits.getTrait(EnumTraitsInt.ENERGYVALUE) * 20), player.getUUID());
	}

	private static int getEnergyCosts()
	{
		return 100;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn)
	{
		super.appendHoverText(stack, level, tooltip, flagIn);
		tooltip.add(new TextComponent("Rightclick on a crop to analyse traits"));
		tooltip.add(new TextComponent("Analysing unknown traits consumes " + getEnergyCosts() + " BE"));
	}
}
