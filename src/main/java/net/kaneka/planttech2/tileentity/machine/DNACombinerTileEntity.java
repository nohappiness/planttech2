package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.DNACombinerContainer;
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

public class DNACombinerTileEntity extends EnergyInventoryTileEntity
{
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return DNACombinerTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return DNACombinerTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return DNACombinerTileEntity.this.ticksPassed;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				DNACombinerTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				DNACombinerTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				DNACombinerTileEntity.this.ticksPassed = value;
				break;
			}

		}
		public int size()
		{
			return 3;
		}
	};

	public DNACombinerTileEntity()
	{
		super(ModTileEntities.DNACOMBINER_TE, 1000, 8, PlantTechConstants.MACHINETIER_DNA_COMBINER);
	}

	@Override
	public void doUpdate()
	{
		super.doUpdate();
		if (this.energystorage.getEnergyStored() > energyPerTick())
		{
			ItemStack stack1 = itemhandler.getStackInSlot(0);
			ItemStack stack2 = itemhandler.getStackInSlot(1);
			ItemStack stack3 = itemhandler.getStackInSlot(2);
			ItemStack stack4 = itemhandler.getStackInSlot(3);
			if (!stack1.isEmpty() && !stack2.isEmpty() && !stack3.isEmpty() && stack4.isEmpty())
			{
				if (stack1.getItem() == ModItems.DNA_CONTAINER && stack2.getItem() == ModItems.DNA_CONTAINER && stack1.hasTag() && stack2.hasTag()
				        && stack3.getItem() == ModItems.DNA_CONTAINER_EMPTY)
				{
					if (ticksPassed < ticksPerItem())
					{
						ticksPassed++;
						energystorage.extractEnergy(energyPerTick(), false);
					}
					else
					{
						ticksPassed = 0;
						energystorage.extractEnergy(energyPerTick(), false);
						CompoundNBT nbt = getCombinedNBT(stack1.getTag(), stack2.getTag());
						ItemStack stack = new ItemStack(ModItems.DNA_CONTAINER);
						stack.setTag(nbt);
						itemhandler.setStackInSlot(3, stack);
						stack3.shrink(1);
						addKnowledge();
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

	private CompoundNBT getCombinedNBT(CompoundNBT nbt1, CompoundNBT nbt2)
	{
		CompoundNBT newNBT = new CompoundNBT();
		for (String key : HashMapCropTraits.getTraitsKeyList())
		{
			if (key.equals("type"))
			{
				if (nbt1.contains(key) && !nbt2.contains(key))
					newNBT.putString(key, nbt1.getString(key));
				else if (!nbt1.contains(key) && nbt2.contains(key))
					newNBT.putString(key, nbt2.getString(key));
				else if (nbt1.contains(key) && nbt2.contains(key))
				{
					if (rand.nextBoolean())
						newNBT.putString(key, nbt1.getString(key));
					else
						newNBT.putString(key, nbt2.getString(key));
				}
			}
			else
			{
				if (nbt1.contains(key) && !nbt2.contains(key))
					newNBT.putInt(key, nbt1.getInt(key));
				else if (!nbt1.contains(key) && nbt2.contains(key))
					newNBT.putInt(key, nbt2.getInt(key));
				else if (nbt1.contains(key) && nbt2.contains(key))
					newNBT.putInt(key, Math.min(nbt1.getInt(key), nbt2.getInt(key)));
			}
		}
		return newNBT;
	}

	@Override
	public String getNameString()
	{
		return "dnacombiner";
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new DNACombinerContainer(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 5;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 6;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 7;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 50;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 4;
	}
}
