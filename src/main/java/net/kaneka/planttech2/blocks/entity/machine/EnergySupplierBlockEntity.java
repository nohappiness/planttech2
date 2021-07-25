package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.baseclasses.BaseElectricFence;
import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.kaneka.planttech2.blocks.machines.EnergySupplierBlock;
import net.kaneka.planttech2.inventory.EnergySupplierMenu;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;

public class EnergySupplierBlockEntity extends EnergyInventoryBlockEntity
{
	protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return EnergySupplierBlockEntity.this.energystorage.getEnergyStored();
			case 1:
				return EnergySupplierBlockEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return EnergySupplierBlockEntity.this.ticksPassed;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				EnergySupplierBlockEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				EnergySupplierBlockEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				EnergySupplierBlockEntity.this.ticksPassed = value;
				break;
			}

		}

		public int getCount()
		{
			return 3;
		}
	};

	public EnergySupplierBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.ENERGY_SUPPLIER_TE, pos, state, 12000, 2, PlantTechConstants.MACHINETIER_ENERGY_SUPPLIER);
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
	public ContainerData getContainerData()
	{
		return field_array;
	}

	@Override
	public String getNameString()
	{
		return "energysupplier";
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new EnergySupplierMenu(id, inv, this);
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
