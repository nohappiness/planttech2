package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.kaneka.planttech2.inventory.SolarGeneratorMenu;
import net.kaneka.planttech2.registries.ModBlocks;
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
	protected final ContainerData data = new ContainerData()
	{
		public int get(int index)
		{
			return switch (index)
					{
						case 0 -> SolarGeneratorBlockEntity.this.energystorage.getEnergyStored();
						case 1 -> SolarGeneratorBlockEntity.this.energystorage.getMaxEnergyStored();
						case 2 -> SolarGeneratorBlockEntity.this.ticksPassed;
						default -> 0;
					};
		}

		public void set(int index, int value)
		{
			switch (index)
			{
				case 0 -> SolarGeneratorBlockEntity.this.energystorage.setEnergyStored(value);
				case 1 -> SolarGeneratorBlockEntity.this.energystorage.setEnergyMaxStored(value);
				case 2 -> SolarGeneratorBlockEntity.this.ticksPassed = value;
			}
		}
		public int getCount()
		{
			return 3;
		}
	};

	public SolarGeneratorBlockEntity()
	{
		this(BlockPos.ZERO, ModBlocks.SOLARGENERATOR.defaultBlockState());
	}

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
		return data;
	}

	private int getEnergyPerTick(int focusLevel)
	{
		return switch (focusLevel)
				{
					case 1 -> 20;
					case 2 -> 60;
					case 3 -> 180;
					case 4 -> 540;
					default -> 0;
				};
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
