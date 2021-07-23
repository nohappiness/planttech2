package net.kaneka.planttech2.hashmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.crops.CropEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class HashMapCropTraits
{
	private String type;
	private HashMap<EnumTraitsInt, Integer> traits = new HashMap<EnumTraitsInt, Integer>();
	private boolean analysed;

	public HashMapCropTraits()
	{
		setDefault();
	}

	private void setDefault()
	{
		this.type = "carrot";
		for (EnumTraitsInt trait : EnumTraitsInt.values())
		{
			traits.put(trait, trait.getMin());
		}
		this.analysed = false;
	}

	public HashMapCropTraits copy()
	{
		HashMapCropTraits copy = new HashMapCropTraits();
		Set<EnumTraitsInt> keyset = this.getTraitsList();
		for (EnumTraitsInt key : keyset)
		{
			copy.setTrait(key, this.getTrait(key));
		}
		copy.setType(this.getType());
		copy.setAnalysed(this.isAnalysed());
		return copy;

	}

	public Set<EnumTraitsInt> getTraitsList()
	{
		return this.traits.keySet();
	}

	public boolean setTrait(EnumTraitsInt trait, int value)
	{
		Set<EnumTraitsInt> keyset = this.getTraitsList();
		if (keyset.contains(trait) && value > 0)
		{
			this.traits.put(trait, value);
			return true;
		}
		return false;
	}

	public int getTrait(EnumTraitsInt trait)
	{
		Set<EnumTraitsInt> keyset = this.getTraitsList();
		if (keyset.contains(trait))
		{
			return this.traits.get(trait);
		}
		return 0;
	}

	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isAnalysed()
	{
		return this.analysed;
	}

	public void setAnalysed(boolean bool)
	{
		this.analysed = bool;
	}

	public ItemStack createItemStackwithTraits(Item item)
	{
		ItemStack stack = new ItemStack(item);
		CompoundNBT nbt = this.toNBT();
		stack.setTag(nbt);
		return stack;
	}

	public ItemStack addToItemStack(ItemStack stack)
	{
		CompoundNBT nbt = this.toNBT();
		stack.setTag(nbt);
		return stack;
	}

	public CompoundNBT toNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		return this.addToNBT(nbt);
	}

	public CompoundNBT addToNBT(CompoundNBT nbt)
	{
		Set<EnumTraitsInt> keyset = this.getTraitsList();
		for (EnumTraitsInt key : keyset)
		{
			nbt.putInt(key.getName(), this.getTrait(key));
		}
		nbt.putString("type", this.type);
		nbt.putBoolean("analysed", this.isAnalysed());
		return nbt;
	}

	public void fromStack(ItemStack stack)
	{
		this.fromNBT(stack.getTag());
	}

	public void fromNBT(CompoundNBT nbt)
	{
		Set<EnumTraitsInt> keyset = this.getTraitsList();
		if (nbt != null)
		{
			for (EnumTraitsInt key : keyset)
			{
				if (nbt.contains(key.getName()))
				{
					this.setTrait(key, nbt.getInt(key.getName()));
				}
			}
			this.setType(nbt.getString("type"));
			this.setAnalysed(nbt.getBoolean("analysed"));
		}
	}

	public HashMapCropTraits calculateNewTraits(HashMapCropTraits oldTraits)
	{
		Random rand = new Random();

		HashMapCropTraits newTraits = new HashMapCropTraits();
		if (rand.nextBoolean())
		{
			newTraits.setType(this.type);
		} else
		{
			newTraits.setType(oldTraits.getType());
		}
		if (!this.getType().equals(oldTraits.getType()))
		{
			Set<CropEntry> possibleChilds = PlantTechMain.getCropList().getByParents(this.getType(), oldTraits.getType());
			if (!possibleChilds.isEmpty())
			{
				float sumNewTyp = 0;
				float sumOldTyp = 0;
				for (CropEntry entry : possibleChilds)
				{
					sumNewTyp += entry.getConfiguration().getMutateChanceForParents(this.getType(), oldTraits.getType());
				}

				sumOldTyp = sumNewTyp;
				if (sumNewTyp < 0.5F)
				{
					sumOldTyp = 1 - sumNewTyp;
				}

				float randomfloat = rand.nextFloat() * (sumOldTyp + sumNewTyp);
				if (randomfloat <= sumNewTyp)
				{
					float sum2 = 0;
					for (CropEntry entry : possibleChilds)
					{
						sum2 += entry.getConfiguration().getMutateChanceForParents(this.getType(), oldTraits.getType());
						if (randomfloat <= sum2)
						{
							newTraits.setType(entry.getName());
						}
					}
				}

			}
		}

		for (EnumTraitsInt trait : EnumTraitsInt.values())
		{
			if (this.getTrait(trait) == oldTraits.getTrait(trait))
			{
				if (trait.getTransitionPossibility() > rand.nextFloat() && this.getTrait(trait) < trait.getMax())
				{
					newTraits.setTrait(trait, (this.getTrait(trait) + 1));
				} 
				else
				{
					newTraits.setTrait(trait, (this.getTrait(trait)));
				}
			} else
			{
				int min = Math.min(this.getTrait(trait), oldTraits.getTrait(trait));
				int max = Math.max(this.getTrait(trait), oldTraits.getTrait(trait));
				newTraits.setTrait(trait, (rand.nextInt((max - min)) + min));
			}
		}

		return newTraits;
	}

	static public List<String> getTraitsKeyList()
	{
		List<String> list = new ArrayList<String>();
		list.add("type");
		for (EnumTraitsInt trait : EnumTraitsInt.values())
		{
			list.add(trait.getName());
		}
		return list;

	}
}
