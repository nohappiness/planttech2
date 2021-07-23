package net.kaneka.planttech2.items;

import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.BlockEntity.CropsTileEntity;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.InteractionResultHolderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

import InteractionResultHolderType;

public class AdvancedAnalyserItem extends EnergyStorageItem
{
	private static final int capacity = 1000;

	public AdvancedAnalyserItem()
	{
		super(new Item.Properties().stacksTo(1).tab(ModCreativeTabs.MAIN), capacity);
	}

	@Override
	public InteractionResultHolderType useOn(ItemUseContext ctx)
	{
		ItemStack stack = ctx.getItemInHand();
		World world = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		PlayerEntity player = ctx.getPlayer();
		if (!world.isClientSide && !stack.isEmpty() && player != null)
		{
			BlockEntity te = world.getBlockEntity(pos);

			if (te instanceof CropsTileEntity)
			{
				CropsTileEntity cte = (CropsTileEntity) te;
				if (cte.isAnalysed())
				{
					HashMapCropTraits traits = cte.getTraits();
					sendTraits(player, traits);
				} else
				{
					if (currentEnergy(stack) >= getEnergyCosts())
					{
						extractEnergy(stack, getEnergyCosts(), false);
						HashMapCropTraits traits = cte.setAnalysedAndGetTraits();
						sendTraits(player, traits);
					} else
					{
						player.sendMessage(new StringTextComponent("Not enough energy"), player.getUUID());
					}
				}
			}
		}
		return super.useOn(ctx);
	}

	private void sendTraits(PlayerEntity player, HashMapCropTraits traits)
	{
		player.sendMessage(new StringTextComponent("--------------------------------------"), player.getUUID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.type").getString() + ": " + traits.getType()), player.getUUID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.soil").getString() + ": " + CropSeedItem.getSoilString(traits.getType())), player.getUUID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.growspeed").getString() + ": " + traits.getTrait(EnumTraitsInt.GROWSPEED)), player.getUUID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.sensitivity").getString() + ": " + traits.getTrait(EnumTraitsInt.SENSITIVITY)), player.getUUID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.needed_lightlevel").getString() + ": " + (14 - traits.getTrait(EnumTraitsInt.LIGHTSENSITIVITY))), player.getUUID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.waterrange").getString() + ": " + (1 + traits.getTrait(EnumTraitsInt.WATERSENSITIVITY))), player.getUUID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.temperature").getString() + ": ").append(CropSeedItem.temperatureString(traits.getType(), traits.getTrait(EnumTraitsInt.TEMPERATURETOLERANCE))), player.getUUID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.productivity").getString() + ": " + traits.getTrait(EnumTraitsInt.PRODUCTIVITY)), player.getUUID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.fertility").getString() + ": " + traits.getTrait(EnumTraitsInt.FERTILITY)), player.getUUID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.spreedingspeed").getString() + ": " + traits.getTrait(EnumTraitsInt.SPREEDINGSPEED)), player.getUUID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.genestrength").getString() + ": " + traits.getTrait(EnumTraitsInt.GENESTRENGHT)), player.getUUID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.energyvalue").getString() + ": " + traits.getTrait(EnumTraitsInt.ENERGYVALUE) * 20), player.getUUID());
	}

	private static int getEnergyCosts()
	{
		return 100;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new StringTextComponent("Rightclick on a crop to analyse traits"));
		tooltip.add(new StringTextComponent("Analysing unknown traits consumes " + getEnergyCosts() + " BE"));
	}
}
