package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.kaneka.planttech2.blocks.machines.EnergyStorageBlock;
import net.kaneka.planttech2.inventory.EnergyStorageContainer;
import net.kaneka.planttech2.items.TierItem;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.antlr.runtime.misc.ContainerData;

public class EnergyStorageBlockEntity extends EnergyInventoryBlockEntity
{
    private int currentTier = -1; // Always forcing a update when loaded
    protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return EnergyStorageBlockEntity.this.energystorage.getEnergyStored();
			case 1:
				return EnergyStorageBlockEntity.this.energystorage.getMaxEnergyStored();
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				EnergyStorageBlockEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				EnergyStorageBlockEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			}
		}
		public int getCount()
		{
			return 2;
		}
	};

    public EnergyStorageBlockEntity()
    {
	super(ModTileEntities.ENERGYSTORAGE_TE, 1000, 4, 0);
    }
    
    @Override
	public ContainerData getContainerData()
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
	public Container createMenu(int id, Inventory inv, Player player)
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
