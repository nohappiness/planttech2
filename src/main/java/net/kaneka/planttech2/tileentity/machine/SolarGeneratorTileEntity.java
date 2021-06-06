package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.SolarGeneratorContainer;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IIntArray;

import static net.kaneka.planttech2.items.TierItem.ItemType.SOLAR_FOCUS;
import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

public class SolarGeneratorTileEntity extends EnergyInventoryTileEntity
{
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
					return SolarGeneratorTileEntity.this.ticksPassed;
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
					SolarGeneratorTileEntity.this.ticksPassed = value;
					break;
			}
		}
		public int getCount()
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
		super.doUpdate();
		if (level != null && level.isDay() && level.canSeeSky(worldPosition.above()))
		{
			if (energystorage.getMaxEnergyStored() - energystorage.getEnergyStored() > 0)
			{
				ticksPassed++;
				if (ticksPassed >= getTicksPerAmount())
				{
					energystorage.receiveEnergy(getEnergyPerTick(getUpgradeTier(0, SOLAR_FOCUS)));
					ticksPassed = 0;
					addKnowledge();
				}
			}
		}
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
		return 80 - getUpgradeTier(SPEED_UPGRADE) * 15;
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

	@Override
	public int getUpgradeSlot()
	{
		return 1;
	}
}
