package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.PlantTopiaTeleporterContainer;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;

public class PlantTopiaTeleporterTileEntity extends EnergyInventoryTileEntity
{
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return PlantTopiaTeleporterTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return PlantTopiaTeleporterTileEntity.this.energystorage.getMaxEnergyStored();
			case 2: 
				return PlantTopiaTeleporterTileEntity.this.worldPosition.getX(); 
			case 3: 
				return PlantTopiaTeleporterTileEntity.this.worldPosition.getY(); 
			case 4: 
				return PlantTopiaTeleporterTileEntity.this.worldPosition.getZ(); 
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				PlantTopiaTeleporterTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				PlantTopiaTeleporterTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				PlantTopiaTeleporterTileEntity.this.worldPosition = new BlockPos(value, PlantTopiaTeleporterTileEntity.this.worldPosition.getY(), PlantTopiaTeleporterTileEntity.this.worldPosition.getZ());
				break;
			case 3:
				PlantTopiaTeleporterTileEntity.this.worldPosition = new BlockPos(PlantTopiaTeleporterTileEntity.this.worldPosition.getX(), value, PlantTopiaTeleporterTileEntity.this.worldPosition.getZ());
				break;
			case 4:
				PlantTopiaTeleporterTileEntity.this.worldPosition = new BlockPos(PlantTopiaTeleporterTileEntity.this.worldPosition.getX(), PlantTopiaTeleporterTileEntity.this.worldPosition.getY(), value);
				break;
			}
		}
		public int getCount()
		{
			return 5;
		}
	};

	public PlantTopiaTeleporterTileEntity()
	{
		super(ModTileEntities.PLANTTOPIATELEPORTER_TE, 10000, 3, 0);
	}

	@Override
	public String getNameString()
	{
		return "planttopia_teleporter";
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
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
	public IIntArray getIntArray()
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
