package net.kaneka.planttech2.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.kaneka.planttech2.container.DNARemoverContainer;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

public class DNARemoverTileEntity extends EnergyInventoryTileEntity
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
		public int size()
		{
			return 3;
		}
	};

	public DNARemoverTileEntity()
	{
		super(ModTileEntities.DNAREMOVER_TE, 1000, 6, PlantTechConstants.MACHINETIER_DNA_REMOVER);
	}

	@Override
	public void doUpdate()
	{
		super.doUpdate();
		if (this.energystorage.getEnergyStored() > energyPerTick())
		{
			ItemStack stack1 = itemhandler.getStackInSlot(0);
			ItemStack stack2 = itemhandler.getStackInSlot(1);
			if (!stack1.isEmpty() && stack2.isEmpty())
			{
				if (stack1.getItem() == ModItems.DNA_CONTAINER && stack1.hasTag())
				{
					List<String> traitsList = getAvailableTraits(stack1);
					if (traitsList.size() > 1)
					{
						if (ticksPassed < ticksPerItem())
						{
							ticksPassed++;
							energystorage.extractEnergy(energyPerTick(), false);
						}
						else
						{
							Collections.shuffle(traitsList);
							CompoundNBT nbt = stack1.getTag().copy();
							nbt.remove(traitsList.get(0));
							ItemStack stack = new ItemStack(ModItems.DNA_CONTAINER);
							stack.setTag(nbt);
							itemhandler.setStackInSlot(1, stack);
							stack1.shrink(1);
							energystorage.extractEnergy(energyPerTick(), false);
							ticksPassed = 0;
							addKnowledge();
						}
					}
				}
			}
		}
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
