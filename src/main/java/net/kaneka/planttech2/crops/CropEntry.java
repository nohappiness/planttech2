package net.kaneka.planttech2.crops;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.utilities.ISerializable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CropEntry implements Comparable<CropEntry>, ISerializable
{
	private final String name;
	private final int seedColor;
	private final boolean hasParticle;
	private final CropConfiguration defaultConfig;
	private CropConfiguration currentConfig;

	public CropEntry(CompoundNBT compound)
	{
		this(compound.getString("name"),
				compound.getInt("colour"),
				compound.getBoolean("hasparticle"),
				new CropConfiguration(compound.getCompound("defaultconfig")));
		currentConfig = new CropConfiguration(compound.getCompound("currentconfig"));
	}

	public CropEntry(String name, int seedColor, boolean hasParticle, CropConfiguration defaultConfig)
	{
		this.name = name;
		this.seedColor = seedColor;
		this.hasParticle = hasParticle;
		this.defaultConfig = defaultConfig;
		this.currentConfig = this.defaultConfig;
	}

	@Override
	public CompoundNBT write()
	{
		CompoundNBT compound = new CompoundNBT();
		compound.putString("name", name);
		compound.putInt("colour", seedColor);
		compound.putBoolean("hasparticle", hasParticle);
		compound.put("defaultconfig", defaultConfig.write());
		compound.put("currentconfig", currentConfig.write());
		return compound;
	}

	public String getName()
	{
		return this.name;
	}

	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("crop." + this.name);
	}

	public int getSeedColor()
	{
		return this.seedColor;
	}

	public boolean hasParticle()
	{
		return this.hasParticle;
	}

	public void setConfiguration(CropConfiguration config)
	{
		if (config == null)
		{
			config = defaultConfig;
		}
		this.currentConfig = config;
	}

	public CropConfiguration getConfiguration()
	{
		return this.currentConfig;
	}

	public boolean isSeed(final Item item)
	{
		return getConfiguration().getSeeds().stream().anyMatch((sup) -> sup.get() == item);
	}

	public List<ItemStack> calculateDrops(List<ItemStack> drops, HashMapCropTraits traits, int growstate, Random rand)
	{
		ItemStack seed = this.getPrimarySeed().getDroppedStack(traits.getTrait(EnumTraitsInt.FERTILITY), EnumTraitsInt.FERTILITY.getMax(),
				rand);
		if (!seed.isEmpty())
		{
			if (growstate < 7)
			{
				seed.setCount(1);
			}
			drops.add(traits.addToItemStack(seed));
		}

		if (growstate > 6)
		{
			List<DropEntry> dropEntries = this.getConfiguration().getDrops();
			for (int i = 1; i < dropEntries.size(); i++)
			{
				DropEntry drop = dropEntries.get(i);
				ItemStack addDrop = drop.getDroppedStack(traits.getTrait(EnumTraitsInt.PRODUCTIVITY), EnumTraitsInt.PRODUCTIVITY.getMax(),
						rand);
				if (!addDrop.isEmpty())
				{
					drops.add(addDrop);
				}
			}
		}
		return drops;
	}

	public NonNullList<ItemStack> calculateDropsReduced(NonNullList<ItemStack> drops, HashMapCropTraits traits, int growstate, Random rand)
	{
		List<DropEntry> dropEntries = this.getConfiguration().getDrops();
		if (growstate > 6)
		{
			ItemStack seed = this.getPrimarySeed().getDroppedStack(traits.getTrait(EnumTraitsInt.FERTILITY), EnumTraitsInt.FERTILITY.getMax(),
					rand);
			if (!seed.isEmpty() && seed.getCount() > 1)
			{
				seed.shrink(1);
				drops.add(traits.addToItemStack(seed));
			}
			for (DropEntry drop : dropEntries)
			{
				ItemStack stack = drop.getDroppedStack(traits.getTrait(EnumTraitsInt.PRODUCTIVITY), EnumTraitsInt.PRODUCTIVITY.getMax(),
						rand);
				if (!stack.isEmpty())
				{
					drops.add(stack);
				}
			}
		}
		return drops;
	}

	public boolean isChild(final String parent1, final String parent2)
	{
		return getConfiguration().getParents().stream().anyMatch(pair -> pair.test(parent1, parent2));
	}

	public List<Supplier<Item>> getSeeds()
	{
		return getConfiguration().getSeeds();
	}

	public List<DropEntry> getAdditionalDrops()
	{
		return getConfiguration().getDrops();
	}

	public DropEntry getPrimarySeed()
	{
		return this.getConfiguration().getPrimarySeed();
	}

	public List<ParentPair> getParents()
	{
		return getConfiguration().getParents();
	}

	public boolean isEnabled()
	{
		return getConfiguration().isEnabled();
	}

	@Override
	public int compareTo(CropEntry o)
	{
		return this.getName().compareTo(o.getName());
	}
}
