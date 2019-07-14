package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.PlantTopiaTeleporterContainer;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
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
				return PlantTopiaTeleporterTileEntity.this.pos.getX(); 
			case 3: 
				return PlantTopiaTeleporterTileEntity.this.pos.getY(); 
			case 4: 
				return PlantTopiaTeleporterTileEntity.this.pos.getZ(); 
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
				BlockPos newPos = new BlockPos(value, PlantTopiaTeleporterTileEntity.this.pos.getY(), PlantTopiaTeleporterTileEntity.this.pos.getZ()); 
				PlantTopiaTeleporterTileEntity.this.pos = newPos;
				break;
			case 3:
				BlockPos newPos2 = new BlockPos(PlantTopiaTeleporterTileEntity.this.pos.getX(), value, PlantTopiaTeleporterTileEntity.this.pos.getZ()); 
				PlantTopiaTeleporterTileEntity.this.pos = newPos2;
				break;
			case 4:
				BlockPos newPos3 = new BlockPos(PlantTopiaTeleporterTileEntity.this.pos.getX(), PlantTopiaTeleporterTileEntity.this.pos.getY(), value); 
				PlantTopiaTeleporterTileEntity.this.pos = newPos3;
				break;
			}

		}

		public int size()
		{
			return 5;
		}
	};

	public PlantTopiaTeleporterTileEntity()
	{
		super(ModTileEntities.PLANTTOPIATELEPORTER_TE, 10000, 2);
	}

	@Override
	public void doUpdate()
	{
		doEnergyLoop();
	}
	
	public int getEnergyPerTeleportation()
	{
		return 1000;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		return compound;
	}

	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);
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
		energystorage.extractEnergy(getEnergyPerTeleportation()); 
	}
}
