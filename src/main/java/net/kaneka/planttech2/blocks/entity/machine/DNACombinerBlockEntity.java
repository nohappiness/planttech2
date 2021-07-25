package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.ConvertEnergyInventoryBlockEntity;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.inventory.DNACombinerMenu;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class DNACombinerBlockEntity extends ConvertEnergyInventoryBlockEntity
{
	protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return DNACombinerBlockEntity.this.energystorage.getEnergyStored();
			case 1:
				return DNACombinerBlockEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return DNACombinerBlockEntity.this.ticksPassed;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				DNACombinerBlockEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				DNACombinerBlockEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				DNACombinerBlockEntity.this.ticksPassed = value;
				break;
			}

		}
		public int getCount()
		{
			return 3;
		}
	};

	public DNACombinerBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.DNACOMBINER_TE, pos, state, 1000, 8, PlantTechConstants.MACHINETIER_DNA_COMBINER);
	}

	@Override
	protected boolean canProceed(ItemStack input, ItemStack output)
	{
		return !input.isEmpty() && !getInput2().isEmpty() && !getInput3().isEmpty();
	}

	@Override
	protected ItemStack getResult(ItemStack input, ItemStack output)
	{
		CompoundTag nbt = getCombinedNBT(getInput2().getTag(), getInput3().getTag());
		ItemStack stack = new ItemStack(ModItems.DNA_CONTAINER);
		stack.setTag(nbt);
		return stack;
	}

	private ItemStack getInput2()
	{
		return itemhandler.getStackInSlot(0);
	}

	private ItemStack getInput3()
	{
		return itemhandler.getStackInSlot(1);
	}

	@Override
	public ContainerData getContainerData()
	{
		return field_array;
	}

	private CompoundTag getCombinedNBT(CompoundTag nbt1, CompoundTag nbt2)
	{
		CompoundTag newNBT = new CompoundTag();
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
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new DNACombinerMenu(id, inv, this);
	}

	@Override
	public int getInputSlotIndex()
	{
		return 2;
	}

	@Override
	public int getOutputSlotIndex()
	{
		return 3;
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
