package net.kaneka.planttech2.items;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.CropsBlockEntity;
import net.kaneka.planttech2.crops.CropEntry;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
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
		DispenserBlock.registerBehavior(this, new OptionalDispenseItemBehavior()
		{
			@Override
			protected ItemStack execute(BlockSource source, ItemStack stack)
			{
				Level level = source.getLevel();
				BlockPos target = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				this.setSuccess(plant(level, target, stack));
				if (!level.isClientSide() && this.isSuccess())
					level.levelEvent(2005, target, 0);
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
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn)
	{
		CompoundTag nbt = stack.getTag();
		if (nbt != null)
		{
			if (nbt.getBoolean("analysed"))
			{
				tooltip.add(new TextComponent(new TranslatableComponent("info.type").getString() + ": " + nbt.getString("type")));
				tooltip.add(new TextComponent(new TranslatableComponent("info.soil").getString() + ": " + getSoilString(nbt.getString("type"))));
				tooltip.add(new TextComponent(new TranslatableComponent("info.temperature").getString() + ": ").append(temperatureString(nbt.getString("type"), nbt.getInt("temperaturetolerance"))));
				tooltip.add(new TextComponent(getTraitColor(nbt, "growspeed") + new TranslatableComponent("info.growspeed").getString() + ": " + nbt.getInt("growspeed")));
				tooltip.add(new TextComponent(getTraitColor(nbt, "sensitivity") + new TranslatableComponent("info.sensitivity").getString() + ": " + nbt.getInt("sensitivity")));
				tooltip.add(new TextComponent(getTraitColor(nbt, "lightsensitivity") + new TranslatableComponent("info.needed_lightlevel").getString() + ": " + (14 - nbt.getInt("lightsensitivity"))));
				tooltip.add(new TextComponent(getTraitColor(nbt, "watersensitivity") + new TranslatableComponent("info.waterrange").getString() + ": " + (1 + nbt.getInt("watersensitivity"))));
				tooltip.add(new TextComponent(getTraitColor(nbt, "productivity") + new TranslatableComponent("info.productivity").getString() + ": " + nbt.getInt("productivity")));
				tooltip.add(new TextComponent(getTraitColor(nbt, "fertility") + new TranslatableComponent("info.fertility").getString() + ": " + nbt.getInt("fertility")));
				tooltip.add(new TextComponent(getTraitColor(nbt, "spreedingspeed") + new TranslatableComponent("info.spreedingspeed").getString() + ": " + nbt.getInt("spreedingspeed")));
				tooltip.add(new TextComponent(getTraitColor(nbt, "genestrenght") + new TranslatableComponent("info.genestrength").getString() + ": " + nbt.getInt("genestrenght")));
				tooltip.add(new TextComponent(getTraitColor(nbt, "energyvalue") + new TranslatableComponent("info.energyvalue").getString() + ": " + nbt.getInt("energyvalue") * 20));
			} else
			{
				tooltip.add(new TextComponent(new TranslatableComponent("info.type").getString() + ": " + new TranslatableComponent("info.unknown").getString()));
				tooltip.add(new TextComponent(new TranslatableComponent("info.soil").getString() + ": " + new TranslatableComponent("info.unknown").getString()));
				tooltip.add(new TextComponent(new TranslatableComponent("info.temperaturetolerance").getString() + ": " + new TranslatableComponent("info.unknown").getString()));
				tooltip.add(new TextComponent(new TranslatableComponent("info.growspeed").getString() + ": " + new TranslatableComponent("info.unknown").getString()));
				tooltip.add(new TextComponent(new TranslatableComponent("info.sensitivity").getString() + ": " + new TranslatableComponent("info.unknown").getString()));
				tooltip.add(new TextComponent(new TranslatableComponent("info.needed_lightlevel").getString() + ": " + new TranslatableComponent("info.unknown").getString()));
				tooltip.add(new TextComponent(new TranslatableComponent("info.waterrange").getString() + ": " + new TranslatableComponent("info.unknown").getString()));
				tooltip.add(new TextComponent(new TranslatableComponent("info.productivity").getString() + ": " + new TranslatableComponent("info.unknown").getString()));
				tooltip.add(new TextComponent(new TranslatableComponent("info.fertility").getString() + ": " + new TranslatableComponent("info.unknown").getString()));
				tooltip.add(new TextComponent(new TranslatableComponent("info.spreedingspeed").getString() + ": " + new TranslatableComponent("info.unknown").getString()));
				tooltip.add(new TextComponent(new TranslatableComponent("info.genestrength").getString() + ": " + new TranslatableComponent("info.unknown").getString()));
				tooltip.add(new TextComponent(new TranslatableComponent("info.energyvalue").getString() + ": " + new TranslatableComponent("info.unknown").getString()));
			}
		} else
		{
			tooltip.add(new TextComponent(new TranslatableComponent("info.type").getString() + ": " + entryName));
			tooltip.add(new TextComponent(new TranslatableComponent("info.soil").getString() + ": " + getSoilString(entryName)));
			tooltip.add(new TextComponent(getTraitColor(TRAIT_MIN) + new TranslatableComponent("info.growspeed").getString() + ": " + 0));
			tooltip.add(new TextComponent(getTraitColor(TRAIT_MIN) + new TranslatableComponent("info.sensitivity").getString() + ": " + 0));
			tooltip.add(new TextComponent(getTraitColor(TRAIT_MIN) + new TranslatableComponent("info.needed_lightlevel").getString() + ": " + 14));
			tooltip.add(new TextComponent(getTraitColor(TRAIT_MIN) + new TranslatableComponent("info.waterrange").getString() + ": " + 1));
			tooltip.add(new TextComponent(new TranslatableComponent("info.temperaturetolerance").getString() + ": ").append(temperatureString(entryName, 0)));
			tooltip.add(new TextComponent(getTraitColor(TRAIT_MIN) + new TranslatableComponent("info.productivity").getString() + ": " + 0));
			tooltip.add(new TextComponent(getTraitColor(TRAIT_MIN) + new TranslatableComponent("info.fertility").getString() + ": " + 0));
			tooltip.add(new TextComponent(getTraitColor(TRAIT_MIN) + new TranslatableComponent("info.spreedingspeed").getString() + ": " + 0));
			tooltip.add(new TextComponent(getTraitColor(TRAIT_MIN) + new TranslatableComponent("info.genestrength").getString() + ": " + 0));
			tooltip.add(new TextComponent(getTraitColor(TRAIT_MIN) + new TranslatableComponent("info.energyvalue").getString() + ": " + 20));
		}
	}

	public static Component temperatureString(String type, int tolerance)
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
		
		return soil == null ? "" : new TranslatableComponent(soil.getConfiguration().getSoil().get().getDescriptionId()).getContents();
	}

	public static class ColorHandler implements ItemColor
	{
		@Override
		public int getColor(ItemStack stack, int color)
		{
			return PlantTechMain.getCropList().getByName(((CropSeedItem) stack.getItem()).getEntryName()).getSeedColor();
		}
	}

	private ChatFormatting getTraitColor(CompoundTag nbt, String trait)
	{
		if (nbt.getInt(trait) == Objects.requireNonNull(EnumTraitsInt.getByName(trait)).getMax())
			return getTraitColor(TRAIT_MAX);
		if (nbt.getInt(trait) == Objects.requireNonNull(EnumTraitsInt.getByName(trait)).getMin())
			return getTraitColor(TRAIT_MIN);
		return ChatFormatting.RESET;
	}

	private ChatFormatting getTraitColor(int level)
	{
		if (level == TRAIT_MIN)
		{
			return ChatFormatting.GRAY;
		}
		if (level == TRAIT_MAX)
		{
			return ChatFormatting.GREEN;
		}
		return ChatFormatting.RESET;
	}

	public static boolean plant(Level world, BlockPos pos, ItemStack stack)
	{
		CropEntry entry = PlantTechMain.getCropList().getBySeed(stack.getItem());
//		System.out.println("Seed" + stack.getItem());
//		System.out.println("Crop list keys" + PlantTechMain.getCropList().keySet());
		if (entry == null)
			return false;
		world.setBlockAndUpdate(pos, ModBlocks.CROPS.get(entry.getName()).defaultBlockState());
		if (world.getBlockEntity(pos) instanceof CropsBlockEntity cbe)
		{
			HashMapCropTraits toPass = new HashMapCropTraits();
			toPass.setType(entry.getName());
			if (stack.hasTag())
				toPass.fromStack(stack);
			else
				toPass.setAnalysed(true);
			cbe.setTraits(toPass);
			cbe.setStartTick();
			return true;
		}
		return false;
	}
}
