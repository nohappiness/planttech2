package net.kaneka.planttech2.items;

import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.tileentity.CropsTileEntity;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class AdvancedAnalyserItem extends EnergyStorageItem
{
	private static final int capacity = 1000;

	public AdvancedAnalyserItem()
	{
		super(new Item.Properties().maxStackSize(1).group(ModCreativeTabs.MAIN), capacity);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
		ItemStack stack = ctx.getItem();
		World world = ctx.getWorld();
		BlockPos pos = ctx.getPos();
		PlayerEntity player = ctx.getPlayer();
		if (!world.isRemote && !stack.isEmpty() && player != null)
		{
			TileEntity te = world.getTileEntity(pos);

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
						player.sendMessage(new StringTextComponent("Not enough energy"), player.getUniqueID());
					}
				}
			}
		}
		return super.onItemUse(ctx);
	}

	private void sendTraits(PlayerEntity player, HashMapCropTraits traits)
	{
		player.sendMessage(new StringTextComponent("--------------------------------------"), player.getUniqueID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.type").getString() + ": " + traits.getType()), player.getUniqueID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.soil").getString() + ": " + CropSeedItem.getSoilString(traits.getType())), player.getUniqueID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.growspeed").getString() + ": " + traits.getTrait(EnumTraitsInt.GROWSPEED)), player.getUniqueID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.sensitivity").getString() + ": " + traits.getTrait(EnumTraitsInt.SENSITIVITY)), player.getUniqueID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.needed_lightlevel").getString() + ": " + (14 - traits.getTrait(EnumTraitsInt.LIGHTSENSITIVITY))), player.getUniqueID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.waterrange").getString() + ": " + (1 + traits.getTrait(EnumTraitsInt.WATERSENSITIVITY))), player.getUniqueID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.temperature").getString() + ": ").appendSibling(CropSeedItem.temperatureString(traits.getType(), traits.getTrait(EnumTraitsInt.TEMPERATURETOLERANCE))), player.getUniqueID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.productivity").getString() + ": " + traits.getTrait(EnumTraitsInt.PRODUCTIVITY)), player.getUniqueID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.fertility").getString() + ": " + traits.getTrait(EnumTraitsInt.FERTILITY)), player.getUniqueID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.spreedingspeed").getString() + ": " + traits.getTrait(EnumTraitsInt.SPREEDINGSPEED)), player.getUniqueID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.genestrength").getString() + ": " + traits.getTrait(EnumTraitsInt.GENESTRENGHT)), player.getUniqueID());
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.energyvalue").getString() + ": " + traits.getTrait(EnumTraitsInt.ENERGYVALUE) * 20), player.getUniqueID());
	}

	private static int getEnergyCosts()
	{
		return 100;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new StringTextComponent("Rightclick on a crop to analyse traits"));
		tooltip.add(new StringTextComponent("Analysing unknown traits consumes " + getEnergyCosts() + " BE"));
	}
}
