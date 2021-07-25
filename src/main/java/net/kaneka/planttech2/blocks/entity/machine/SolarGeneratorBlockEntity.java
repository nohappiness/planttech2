package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.kaneka.planttech2.inventory.SolarGeneratorMenu;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.state.BlockState;

import static net.kaneka.planttech2.items.TierItem.ItemType.SOLAR_FOCUS;
import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

public class SolarGeneratorBlockEntity extends EnergyInventoryBlockEntity
{
	protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			switch (index)
			{
				case 0:
					return SolarGeneratorBlockEntity.this.energystorage.getEnergyStored();
				case 1:
					return SolarGeneratorBlockEntity.this.energystorage.getMaxEnergyStored();
				case 2:
					return SolarGeneratorBlockEntity.this.ticksPassed;
				default:
					return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
				case 0:
					SolarGeneratorBlockEntity.this.energystorage.setEnergyStored(value);
					break;
				case 1:
					SolarGeneratorBlockEntity.this.energystorage.setEnergyMaxStored(value);
					break;
				case 2:
					SolarGeneratorBlockEntity.this.ticksPassed = value;
					break;
			}
		}
		public int getCount()
		{
			return 3;
		}
	};

	public SolarGeneratorBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.SOLARGENERATOR_TE, pos, state, 10000, 5, PlantTechConstants.MACHINETIER_SOLARGENERATOR);
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
	public ContainerData getContainerData()
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
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new SolarGeneratorMenu(id, inv, this);
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
