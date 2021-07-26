package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.kaneka.planttech2.inventory.PlantTopiaTeleporterMenu;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.state.BlockState;

public class PlantTopiaTeleporterBlockEntity extends EnergyInventoryBlockEntity
{
	protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return PlantTopiaTeleporterBlockEntity.this.energystorage.getEnergyStored();
			case 1:
				return PlantTopiaTeleporterBlockEntity.this.energystorage.getMaxEnergyStored();
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				PlantTopiaTeleporterBlockEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				PlantTopiaTeleporterBlockEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			}
		}
		public int getCount()
		{
			return 5;
		}
	};

	public PlantTopiaTeleporterBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.PLANTTOPIATELEPORTER_TE, pos, state, 10000, 3, 0);
	}

	@Override
	public String getNameString()
	{
		return "planttopia_teleporter";
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new PlantTopiaTeleporterMenu(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 0;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 1;
	}

	@Override
	public ContainerData getIntArray()
	{
		return field_array;
	}

	public void doTeleportation()
	{
		energystorage.extractEnergy(energyPerAction());
	}

	@Override
	public int energyPerAction()
	{
		return 1000;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 0;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 0;
	}
}
