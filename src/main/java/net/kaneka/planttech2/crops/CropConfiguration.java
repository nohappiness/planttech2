package net.kaneka.planttech2.crops;

import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.utilities.ISerializable;
import net.kaneka.planttech2.utilities.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

public class CropConfiguration implements ISerializable
{
	private final boolean enabled;
	private final EnumTemperature temperature;
	private final DropEntry primarySeed;
	private final List<Supplier<Item>> seeds;
	private final List<DropEntry> drops;
	private final List<ParentPair> parents;
	private final Supplier<Block> soil;

	public CropConfiguration(CompoundTag compound)
	{
		this(compound.getBoolean("enabled"),
				EnumTemperature.byName(compound.getString("temperature")),
				DropEntry.of(compound.getCompound("primaryseed")),
				NBTHelper.constructListFromString(compound, "seeds", (name) -> () -> ITEMS.getValue(new ResourceLocation(name))),
				NBTHelper.constructListFromCompound(compound, "drops", DropEntry::of),
				NBTHelper.constructListFromCompound(compound, "parents", ParentPair::of),
				() -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(compound.getString("soil"))));

	}

	public CropConfiguration(boolean enabled, EnumTemperature temperature, DropEntry primarySeed, List<Supplier<Item>> seeds,
	                  List<DropEntry> drops, List<ParentPair> parents, Supplier<Block> soil)
	{
		this.enabled = enabled;
		this.temperature = temperature;
		this.primarySeed = primarySeed;
		this.seeds = seeds;
		this.drops = drops;
		this.parents = parents;
		this.soil = soil;
	}

	@Override
	public CompoundTag save()
	{
		CompoundTag compound = new CompoundTag();
		compound.putBoolean("enabled", enabled);
		compound.putString("temperature", temperature.toString());
		compound.put("primaryseed", primarySeed.save());
		NBTHelper.putList(compound, "seeds", seeds, (seed) -> StringTag.valueOf(seed.get().getRegistryName().toString()));
		NBTHelper.putSerilizableList(compound, "drops", drops);
		NBTHelper.putSerilizableList(compound, "parents", parents);
		compound.putString("soil", soil.get().getRegistryName().toString());
		return compound;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public EnumTemperature getTemperature()
	{
		return temperature;
	}

	public DropEntry getPrimarySeed()
	{
		return primarySeed;
	}

	public List<Supplier<Item>> getSeeds()
	{
		return seeds;
	}

	public List<DropEntry> getDrops()
	{
		return drops;
	}

	public List<ParentPair> getParents()
	{
		return parents;
	}

	public Supplier<Block> getSoil()
	{
		return soil;
	}

	public float getMutateChanceForParents(String parent1, String parent2)
	{
		return parents.stream().filter(parentPair -> parentPair.test(parent1, parent2)).map(ParentPair::getMutationChance).findFirst().orElse(0F);
	}

	public static CropConfiguration fromConfigData(CropEntryConfigData data)
	{
		return new CropConfiguration(data.isEnabled(), data.getTemperature(), data.getPrimarySeed(), data.getSeeds(), data.getDrops(),
				data.getParents(),
				data.getSoil());
	}

	public static Builder builder(DropEntry primarySeed)
	{
		return new Builder(primarySeed);
	}

	public static class Builder
	{
		boolean enabled = true;
		EnumTemperature temperature = EnumTemperature.NORMAL;
		final DropEntry primarySeed;
		final List<Supplier<Item>> seeds = new ArrayList<>();
		final List<DropEntry> drops = new ArrayList<>();
		final List<ParentPair> parents = new ArrayList<>();
		Supplier<Block> soil = () -> Blocks.DIRT;

		public Builder(DropEntry primarySeed)
		{
			this.primarySeed = primarySeed;
		}

		public CropConfiguration build()
		{
			return new CropConfiguration(enabled, temperature, primarySeed, seeds, drops, parents, soil);
		}

		public Builder enabled(boolean enabled)
		{
			this.enabled = enabled;
			return this;
		}

		public Builder enabled()
		{
			return this.enabled(true);
		}

		public Builder disabled()
		{
			return this.enabled(false);
		}

		public Builder temperature(EnumTemperature newTemp)
		{
			this.temperature = newTemp;
			return this;
		}

		public Builder seed(Supplier<Item> item)
		{
			this.seeds.add(item);
			return this;
		}

		public Builder seed(ResourceLocation item)
		{
			return this.seed(RegistryEntrySupplier.of(item, ITEMS));
		}

		public Builder seed(String item)
		{
			return this.seed(new ResourceLocation(item));
		}

		public Builder parents(ParentPair entry)
		{
			this.parents.add(entry);
			return this;
		}

		public Builder parents(String parent1, String parent2, float chance)
		{
			return this.parents(ParentPair.of(parent1, parent2, chance));
		}

		public Builder parents(ParentPair... entries)
		{
			this.parents.addAll(Arrays.asList(entries));
			return this;
		}

		public Builder drop(DropEntry entry)
		{
			this.drops.add(entry);
			return this;
		}

		public Builder drop(Supplier<Item> item, int min, int max)
		{
			return this.drop(DropEntry.of(item, min, max));
		}

		public Builder drop(ResourceLocation item, int min, int max)
		{
			return this.drop(RegistryEntrySupplier.of(item, ITEMS), min, max);
		}

		public Builder drop(String item, int min, int max)
		{
			return this.drop(new ResourceLocation(item), min, max);
		}

		public Builder drop(DropEntry... entries)
		{
			this.drops.addAll(Arrays.asList(entries));
			return this;
		}

		public Builder soil(Supplier<Block> newSoil)
		{
			this.soil = newSoil;
			return this;
		}
	}
}
