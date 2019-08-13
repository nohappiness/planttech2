package net.kaneka.planttech2.items;

import java.util.List;

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

public class AdvancedAnalyserItem extends EnergyStorageItem
{
	private static final int capacity = 1000;

	public AdvancedAnalyserItem()
	{
		super("advanced_analyser", new Item.Properties().maxStackSize(1).group(ModCreativeTabs.groupmain), capacity);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
		ItemStack stack = ctx.getItem();
		if (!stack.isEmpty())
		{
			if (stack.getItem() instanceof AdvancedAnalyserItem)
			{
				World world = ctx.getWorld();
				BlockPos pos = ctx.getPos();
				PlayerEntity player = ctx.getPlayer();
				if (!world.isRemote)
				{
					TileEntity te = world.getTileEntity(pos);

					if (te instanceof CropsTileEntity)
					{
						CropsTileEntity cte = (CropsTileEntity) te;
						if (cte.isAnalysed())
						{
							HashMapCropTraits traits =  cte.getTraits();
							sendTraits(player, traits);
						}
						else
						{
							if(currentEnergy(stack) >= getEnergyCosts())
							{
								extractEnergy(stack, getEnergyCosts(), false); 
								HashMapCropTraits traits =  cte.setAnalysedAndGetTraits();
								sendTraits(player, traits);
							}
							else
							{
								player.sendMessage(new StringTextComponent("Not enough energy"));
							}
						}
					}
				}
			}
		}

		return super.onItemUse(ctx);
	}

	private void sendTraits(PlayerEntity player, HashMapCropTraits traits)
	{
		player.sendMessage(new StringTextComponent("--------------------------------------"));
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.type").getUnformattedComponentText() + ": " + traits.getType()));
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.soil").getUnformattedComponentText() + ": " + CropSeedItem.getSoilString(traits.getType())));
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.growspeed").getUnformattedComponentText() + ": " + traits.getTrait(EnumTraitsInt.GROWSPEED)));
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.sensitivity").getUnformattedComponentText() + ": " + traits.getTrait(EnumTraitsInt.SENSITIVITY)));
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.needed_lightlevel").getUnformattedComponentText() + ": " + (14 - traits.getTrait(EnumTraitsInt.LIGHTSENSITIVITY))));
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.waterrange").getUnformattedComponentText() + ": " + (1 + traits.getTrait(EnumTraitsInt.WATERSENSITIVITY))));
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.temperature").getUnformattedComponentText() + ": " + CropSeedItem.temperatureString(traits.getType(), traits.getTrait(EnumTraitsInt.TEMPERATURETOLERANCE))));
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.productivity").getUnformattedComponentText() + ": " + traits.getTrait(EnumTraitsInt.PRODUCTIVITY)));
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.fertility").getUnformattedComponentText() + ": " + traits.getTrait(EnumTraitsInt.FERTILITY)));
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.spreedingspeed").getUnformattedComponentText() + ": " + traits.getTrait(EnumTraitsInt.SPREEDINGSPEED)));
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.genestrength").getUnformattedComponentText() + ": " + traits.getTrait(EnumTraitsInt.GENESTRENGHT)));
		player.sendMessage(new StringTextComponent(new TranslationTextComponent("info.energyvalue").getUnformattedComponentText() + ": " + traits.getTrait(EnumTraitsInt.ENERGYVALUE) * 20));
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
