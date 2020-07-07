package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.blocks.baseclasses.BaseElectricFence;
import net.kaneka.planttech2.blocks.machines.EnergySupplierBlock;
import net.kaneka.planttech2.container.EnergySupplierContainer;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;

public class EnergySupplierTileEntity extends EnergyInventoryTileEntity
{
	private int ticks;
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
				return EnergySupplierTileEntity.this.ticks;
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
				EnergySupplierTileEntity.this.ticks = value;
				break;
			}

		}

		public int size()
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
		if (world == null)
		{
			return;
		}
		ticks++;
		if(energystorage.getEnergyStored() <= 0)
		{
			setPower(false);
		}
		else if (getConnected() != null && ticks >= ticksPerEnergy())
		{
			energystorage.extractEnergy(1);
			setPower(true);
			ticks = 0;
		}
		doEnergyLoop();
	}

	private void setPower(boolean powered)
	{
		if (world != null && world.getBlockState(pos).getBlock() instanceof EnergySupplierBlock)
		{
			world.setBlockState(pos, world.getBlockState(pos).
					getBlock().getDefaultState()
					.with(EnergySupplierBlock.SUPPLYING, powered));
		}
	}

	/*private void setPower(int value)
	{
		if (getConnected() == null)
		{
			return;
		}
		for (BlockPos pos : getConnected())
		{
			world.setBlockState(pos, world.getBlockState(pos).
					getBlock().getDefaultState()
					.with(ElectricFence.ELECTRIC_POWER, value)
					.with(ElectricFence.HORIZONTAL_FACING, world.getBlockState(pos)
							.get(ElectricFence.HORIZONTAL_FACING)));
		}
	}*/

	private HashSet<BlockPos> getConnected()
	{
		HashSet<BlockPos> list = new HashSet<>();
		for (Direction direction : Direction.values())
		{
			BlockPos blockPos = this.pos.offset(direction);
			if (world.getBlockState(blockPos).getBlock() instanceof BaseElectricFence)
			{
				list.add(blockPos);
			}
		}
		return list.isEmpty() ? null : list;
	}

	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		this.ticks = compound.getInt("ticks");
		super.read(state, compound);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putInt("ticks", this.ticks);
		return super.write(compound);
	}

	public int ticksPerEnergy()
	{
		return 20;
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
