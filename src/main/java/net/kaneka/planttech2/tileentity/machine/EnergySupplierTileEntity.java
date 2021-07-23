package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.blocks.baseclasses.BaseElectricFence;
import net.kaneka.planttech2.blocks.machines.EnergySupplierBlock;
import net.kaneka.planttech2.container.EnergySupplierContainer;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.core.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.core.BlockPos;

import java.util.HashSet;

public class EnergySupplierTileEntity extends EnergyInventoryTileEntity
{
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return EnergySupplierTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return EnergySupplierTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return EnergySupplierTileEntity.this.ticksPassed;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				EnergySupplierTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				EnergySupplierTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				EnergySupplierTileEntity.this.ticksPassed = value;
				break;
			}

		}

		public int getCount()
		{
			return 3;
		}
	};

	public EnergySupplierTileEntity()
	{
		super(ModTileEntities.ENERGY_SUPPLIER_TE, 12000, 2, PlantTechConstants.MACHINETIER_ENERGY_SUPPLIER);
	}

	@Override
	public void doUpdate()
	{
		super.doUpdate();
		if (level == null)
			return;
		ticksPassed++;
		if(energystorage.getEnergyStored() <= 0)
			setPower(false);
		else if (!getConnected().isEmpty() && ticksPassed >= ticksPerItem())
		{
			energystorage.extractEnergy(energyPerAction());
			setPower(true);
			resetProgress(true);
		}
	}

	private void setPower(boolean powered)
	{
		if (level != null && level.getBlockState(worldPosition).getBlock() instanceof EnergySupplierBlock)
		{
			level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).
					getBlock().defaultBlockState()
					.setValue(EnergySupplierBlock.SUPPLYING, powered));
		}
	}

	private HashSet<BlockPos> getConnected()
	{
		HashSet<BlockPos> list = new HashSet<>();
		if (level != null)
		{
			for (Direction direction : Direction.values())
			{
				BlockPos blockPos = this.worldPosition.relative(direction);
				if (level.getBlockState(blockPos).getBlock() instanceof BaseElectricFence)
					list.add(blockPos);
			}
		}
		return list;
	}

	@Override
	public int energyPerAction()
	{
		return 1;
	}

	@Override
	public int ticksPerItem()
	{
		return 10;
	}

	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

	@Override
	public String getNameString()
	{
		return "energysupplier";
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new EnergySupplierContainer(id, inv, this);
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
