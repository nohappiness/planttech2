package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.kaneka.planttech2.inventory.PlantTopiaTeleporterContainer;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.antlr.runtime.misc.ContainerData;

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
			case 2: 
				return PlantTopiaTeleporterBlockEntity.this.worldPosition.getX();
			case 3: 
				return PlantTopiaTeleporterBlockEntity.this.worldPosition.getY();
			case 4: 
				return PlantTopiaTeleporterBlockEntity.this.worldPosition.getZ();
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
			case 2:
				PlantTopiaTeleporterBlockEntity.this.worldPosition = new BlockPos(value, PlantTopiaTeleporterBlockEntity.this.worldPosition.getY(), PlantTopiaTeleporterBlockEntity.this.worldPosition.getZ());
				break;
			case 3:
				PlantTopiaTeleporterBlockEntity.this.worldPosition = new BlockPos(PlantTopiaTeleporterBlockEntity.this.worldPosition.getX(), value, PlantTopiaTeleporterBlockEntity.this.worldPosition.getZ());
				break;
			case 4:
				PlantTopiaTeleporterBlockEntity.this.worldPosition = new BlockPos(PlantTopiaTeleporterBlockEntity.this.worldPosition.getX(), PlantTopiaTeleporterBlockEntity.this.worldPosition.getY(), value);
				break;
			}
		}
		public int getCount()
		{
			return 5;
		}
	};

	public PlantTopiaTeleporterBlockEntity()
	{
		super(ModTileEntities.PLANTTOPIATELEPORTER_TE, 10000, 3, 0);
	}

	@Override
	public String getNameString()
	{
		return "planttopia_teleporter";
	}

	@Override
	public Container createMenu(int id, Inventory inv, Player player)
	{
		return new PlantTopiaTeleporterContainer(id, inv, this);
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
	public ContainerData getContainerData()
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
