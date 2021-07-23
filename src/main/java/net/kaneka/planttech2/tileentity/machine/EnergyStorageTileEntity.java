package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.blocks.machines.EnergyStorageBlock;
import net.kaneka.planttech2.container.EnergyStorageContainer;
import net.kaneka.planttech2.items.TierItem;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IIntArray;

public class EnergyStorageTileEntity extends EnergyInventoryTileEntity
{
    private int currentTier = -1; // Always forcing a update when loaded
    protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return EnergyStorageTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return EnergyStorageTileEntity.this.energystorage.getMaxEnergyStored();
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				EnergyStorageTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				EnergyStorageTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			}
		}
		public int getCount()
		{
			return 2;
		}
	};

    public EnergyStorageTileEntity()
    {
	super(ModTileEntities.ENERGYSTORAGE_TE, 1000, 4, 0);
    }
    
    @Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

	@Override
    public void onContainerUpdated(int slotIndex)
    {
		if (level != null)
		{
			if (!level.isClientSide)
			{
				int newTier = getUpgradeTier(TierItem.ItemType.CAPACITY_UPGRADE);
				if (currentTier != newTier)
				{
					energystorage.setEnergyMaxStored(getTotalCapacity());
					BlockState state = level.getBlockState(worldPosition);
					if (state.getBlock() == ModBlocks.ENERGYSTORAGE)
					{
						level.setBlock(worldPosition, state.setValue(EnergyStorageBlock.TIER, newTier), 3);
						setChanged();
					}
					currentTier = newTier;
				}
			}
		}
    }

    @Override
    public String getNameString()
    {
	return "energystorage";
    }
    
    @Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new EnergyStorageContainer(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 1;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 2;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 3;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 0;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 0;
	}
}
