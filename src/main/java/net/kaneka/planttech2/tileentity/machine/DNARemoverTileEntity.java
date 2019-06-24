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
	private int ticksPassed = 0;
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
				;
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
		super(ModTileEntities.DNAREMOVER_TE, 1000, 3);
	}

	@Override
	public void doUpdate()
	{
		if (this.energystorage.getEnergyStored() > energyPerTick() || true)
		{
			ItemStack stack1 = itemhandler.getStackInSlot(0);
			ItemStack stack2 = itemhandler.getStackInSlot(1);
			if (!stack1.isEmpty() && stack2.isEmpty())
			{
				if (stack1.getItem() == ModItems.DNA_CONTAINER)
				{
					List<String> traitsList = getAvailableTraits(stack1);
					if (traitsList.size() > 1)
					{
						if (ticksPassed < ticksPerItem())
						{
							ticksPassed++;
							// energystorage.extractEnergy(energyPerTick(), false);
						} else
						{
							Collections.shuffle(traitsList);
							CompoundNBT nbt = stack1.getTag().copy();
							nbt.remove(traitsList.get(0));
							ItemStack stack = new ItemStack(ModItems.DNA_CONTAINER);
							stack.setTag(nbt);
							itemhandler.setStackInSlot(1, stack);
							stack1.shrink(1);
							// energystorage.extractEnergy(energyPerTick(), false);
							ticksPassed = 0;
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
		if (stack.hasTag())
		{
			CompoundNBT nbt = stack.getTag();
			for (String key : HashMapCropTraits.getTraitsKeyList())
			{
				if (nbt.contains(key))
					list.add(key);
			}
		}
		return list;
	}

	public int energyPerTick()
	{
		return 4 + (getUpgradeTier(2, PlantTechConstants.SPEEDUPGRADE_TYPE) * 4);
	}

	public int ticksPerItem()
	{
		return 200 - (getUpgradeTier(2, PlantTechConstants.SPEEDUPGRADE_TYPE) * 35);
	}

	@Override
	public String getNameString()
	{
		return "dnayremover";
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putInt("tickspassed", ticksPassed);
		super.write(compound);
		return compound;
	}

	@Override
	public void read(CompoundNBT compound)
	{
		this.ticksPassed = compound.getInt("tickspassed");
		super.read(compound);
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new DNARemoverContainer(id, inv, this);
	}
}
