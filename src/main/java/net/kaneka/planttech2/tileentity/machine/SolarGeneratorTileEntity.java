package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.SolarGeneratorContainer;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

public class SolarGeneratorTileEntity extends EnergyInventoryTileEntity
{
	int workload = 0;
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return SolarGeneratorTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return SolarGeneratorTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return SolarGeneratorTileEntity.this.workload;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				SolarGeneratorTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				SolarGeneratorTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				SolarGeneratorTileEntity.this.workload = value;
				;
				break;
			}

		}

		public int size()
		{
			return 3;
		}
	};

	public SolarGeneratorTileEntity()
	{
		super(ModTileEntities.SOLARGENERATOR_TE, 10000, 5, PlantTechConstants.MACHINETIER_SOLARGENERATOR);
	}

	@Override
	public void doUpdate()
	{
		if (world.isDaytime() && world.canBlockSeeSky(pos.up()))
		{
			if (energystorage.getMaxEnergyStored() - energystorage.getEnergyStored() > 0)
			{
				workload++;
				if (workload >= getTicksPerAmount())
				{
					energystorage.receiveEnergy(getEnergyPerTick(getUpgradeTier(0, PlantTechConstants.SOLARFOCUS_TYPE)));
					workload = 0;
					addKnowledge();
				}
			}
		}
		doEnergyLoop();
	}

	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

	private int getEnergyPerTick(int focusLevel)
	{
		switch (focusLevel)
		{
		case 1:
			return 20;
		case 2:
			return 60;
		case 3:
			return 180;
		case 4:
			return 540;
		}

		return 0;
	}

	public int getTicksPerAmount()
	{
		return 80 - (getUpgradeTier(1, PlantTechConstants.SPEEDUPGRADE_TYPE) * 15);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putInt("workload", workload);
		super.write(compound);
		return compound;
	}

	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		this.workload = compound.getInt("workload");
		super.read(state, compound);
	}

	@Override
	public String getNameString()
	{
		return "solargenerator";
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new SolarGeneratorContainer(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 2;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 3;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 4;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 50;
	}
}
