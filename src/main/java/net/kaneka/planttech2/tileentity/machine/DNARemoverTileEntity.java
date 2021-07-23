package net.kaneka.planttech2.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.kaneka.planttech2.container.DNARemoverContainer;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.ConvertEnergyInventoryTileEntity;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

public class DNARemoverTileEntity extends ConvertEnergyInventoryTileEntity
{
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return DNARemoverTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return DNARemoverTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return DNARemoverTileEntity.this.ticksPassed;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				DNARemoverTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				DNARemoverTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				DNARemoverTileEntity.this.ticksPassed = value;
				break;
			}
		}
		public int getCount()
		{
			return 3;
		}
	};

	public DNARemoverTileEntity()
	{
		super(ModTileEntities.DNAREMOVER_TE, 1000, 6, PlantTechConstants.MACHINETIER_DNA_REMOVER);
	}

	@Override
	protected boolean canProceed(ItemStack input, ItemStack output)
	{
		return !input.isEmpty() && input.hasTag() && !getAvailableTraits(input).isEmpty();
	}

	@Override
	protected ItemStack getResult(ItemStack input, ItemStack output)
	{
		List<String> traitsList = getAvailableTraits(input);
		Collections.shuffle(traitsList);
		CompoundNBT nbt = input.getTag().copy();
		nbt.remove(traitsList.get(0));
		ItemStack stack = new ItemStack(ModItems.DNA_CONTAINER);
		stack.setTag(nbt);
		return stack;
	}

	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

	private List<String> getAvailableTraits(ItemStack stack)
	{
		List<String> list = new ArrayList<String>();
		CompoundNBT nbt = stack.getOrCreateTag();
		for (String key : HashMapCropTraits.getTraitsKeyList())
			if (nbt.contains(key))
				list.add(key);
		return list;
	}

	@Override
	public String getNameString()
	{
		return "dnaremover";
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new DNARemoverContainer(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 3;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 4;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 5;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 50;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 2;
	}
}
