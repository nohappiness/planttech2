package net.kaneka.planttech2.items;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.crops.CropEntry;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.BlockEntity.CropsTileEntity;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class CropSeedItem extends Item
{
	private final String entryName;
	private int TRAIT_MIN = 0;
	private int TRAIT_MAX = 1;

	public CropSeedItem(String entryName)
	{
		super(new Item.Properties().tab(ModCreativeTabs.SEEDS));
		this.entryName = entryName;
		DispenserBlock.registerBehavior(this, new OptionalDispenseBehavior()
		{
			@Override
			protected ItemStack execute(IBlockSource source, ItemStack stack)
			{
				World world = source.getLevel();
				BlockPos target = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				this.setSuccess(plant(world, target, stack));
				if (!world.isClientSide() && this.isSuccess())
					world.levelEvent(2005, target, 0);
				return stack;
			}
		});
	}

	public String getEntryName()
	{
		return entryName;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		CompoundNBT nbt = stack.getTag();
		if (nbt != null)
		{
			if (nbt.getBoolean("analysed"))
			{
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.type").getString() + ": " + nbt.getString("type")));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.soil").getString() + ": " + getSoilString(nbt.getString("type"))));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.temperature").getString() + ": ").append(temperatureString(nbt.getString("type"), nbt.getInt("temperaturetolerance"))));
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "growspeed") + new TranslationTextComponent("info.growspeed").getString() + ": " + nbt.getInt("growspeed")));
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "sensitivity") + new TranslationTextComponent("info.sensitivity").getString() + ": " + nbt.getInt("sensitivity")));
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "lightsensitivity") + new TranslationTextComponent("info.needed_lightlevel").getString() + ": " + (14 - nbt.getInt("lightsensitivity"))));
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "watersensitivity") + new TranslationTextComponent("info.waterrange").getString() + ": " + (1 + nbt.getInt("watersensitivity"))));
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "productivity") + new TranslationTextComponent("info.productivity").getString() + ": " + nbt.getInt("productivity")));
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "fertility") + new TranslationTextComponent("info.fertility").getString() + ": " + nbt.getInt("fertility")));
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "spreedingspeed") + new TranslationTextComponent("info.spreedingspeed").getString() + ": " + nbt.getInt("spreedingspeed")));
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "genestrenght") + new TranslationTextComponent("info.genestrength").getString() + ": " + nbt.getInt("genestrenght")));
				tooltip.add(new StringTextComponent(getTraitColor(nbt, "energyvalue") + new TranslationTextComponent("info.energyvalue").getString() + ": " + nbt.getInt("energyvalue") * 20));
			} else
			{
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.type").getString() + ": " + new TranslationTextComponent("info.unknown").getString()));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.soil").getString() + ": " + new TranslationTextComponent("info.unknown").getString()));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.temperaturetolerance").getString() + ": " + new TranslationTextComponent("info.unknown").getString()));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.growspeed").getString() + ": " + new TranslationTextComponent("info.unknown").getString()));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.sensitivity").getString() + ": " + new TranslationTextComponent("info.unknown").getString()));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.needed_lightlevel").getString() + ": " + new TranslationTextComponent("info.unknown").getString()));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.waterrange").getString() + ": " + new TranslationTextComponent("info.unknown").getString()));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.productivity").getString() + ": " + new TranslationTextComponent("info.unknown").getString()));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.fertility").getString() + ": " + new TranslationTextComponent("info.unknown").getString()));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.spreedingspeed").getString() + ": " + new TranslationTextComponent("info.unknown").getString()));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.genestrength").getString() + ": " + new TranslationTextComponent("info.unknown").getString()));
				tooltip.add(new StringTextComponent(new TranslationTextComponent("info.energyvalue").getString() + ": " + new TranslationTextComponent("info.unknown").getString()));
			}
		} else
		{
			tooltip.add(new StringTextComponent(new TranslationTextComponent("info.type").getString() + ": " + entryName));
			tooltip.add(new StringTextComponent(new TranslationTextComponent("info.soil").getString() + ": " + getSoilString(entryName)));
			tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.growspeed").getString() + ": " + 0));
			tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.sensitivity").getString() + ": " + 0));
			tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.needed_lightlevel").getString() + ": " + 14));
			tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.waterrange").getString() + ": " + 1));
			tooltip.add(new StringTextComponent(new TranslationTextComponent("info.temperaturetolerance").getString() + ": ").append(temperatureString(entryName, 0)));
			tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.productivity").getString() + ": " + 0));
			tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.fertility").getString() + ": " + 0));
			tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.spreedingspeed").getString() + ": " + 0));
			tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.genestrength").getString() + ": " + 0));
			tooltip.add(new StringTextComponent(getTraitColor(TRAIT_MIN) + new TranslationTextComponent("info.energyvalue").getString() + ": " + 20));
		}
	}

	public static ITextComponent temperatureString(String type, int tolerance)
	{
		if (tolerance == 0)
			return PlantTechMain.getCropList().getByName(type).getConfiguration().getTemperature().getDisplayString();
		int id = PlantTechMain.getCropList().getByName(type).getConfiguration().getTemperature().ordinal();
		int min = id - tolerance;
		int max = id + tolerance;
		if (min < 0)
		{
			min = 0;
		}
		if (max > 4)
		{
			max = 4;
		}
		return EnumTemperature.values()[min].getDisplayString().append(" - ").append(EnumTemperature.values()[max].getDisplayString());
	}

	public static String getSoilString(String type)
	{
		CropEntry soil = PlantTechMain.getCropList().getByName(type);
		
		return soil == null ? "" : new TranslationTextComponent(soil.getConfiguration().getSoil().get().getDescriptionId()).getContents();
	}

	public static class ColorHandler implements IItemColor
	{
		@Override
		public int getColor(ItemStack stack, int color)
		{
			return PlantTechMain.getCropList().getByName(((CropSeedItem) stack.getItem()).getEntryName()).getSeedColor();
		}
	}

	private TextFormatting getTraitColor(CompoundNBT nbt, String trait)
	{
		if (nbt.getInt(trait) == Objects.requireNonNull(EnumTraitsInt.getByName(trait)).getMax())
			return getTraitColor(TRAIT_MAX);
		if (nbt.getInt(trait) == Objects.requireNonNull(EnumTraitsInt.getByName(trait)).getMin())
			return getTraitColor(TRAIT_MIN);
		return TextFormatting.RESET;
	}

	private TextFormatting getTraitColor(int level)
	{
		if (level == TRAIT_MIN)
		{
			return TextFormatting.GRAY;
		}
		if (level == TRAIT_MAX)
		{
			return TextFormatting.GREEN;
		}
		return TextFormatting.RESET;
	}

	public static boolean plant(World world, BlockPos pos, ItemStack stack)
	{
		CropEntry entry = PlantTechMain.getCropList().getBySeed(stack.getItem());
//		System.out.println("Seed" + stack.getItem());
//		System.out.println("Crop list keys" + PlantTechMain.getCropList().keySet());
		if (entry == null)
			return false;
		world.setBlockAndUpdate(pos, ModBlocks.CROPS.get(entry.getName()).defaultBlockState());
		BlockEntity BlockEntity = world.getBlockEntity(pos);
		if (BlockEntity instanceof CropsTileEntity)
		{
			HashMapCropTraits toPass = new HashMapCropTraits();
			toPass.setType(entry.getName());
			if (stack.hasTag())
				toPass.fromStack(stack);
			else
				toPass.setAnalysed(true);
			((CropsTileEntity) BlockEntity).setTraits(toPass);
			((CropsTileEntity) BlockEntity).setStartTick();
			return true;
		}
		return false;
	}
}
