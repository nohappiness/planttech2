package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.kaneka.planttech2.inventory.DNAExtractorMenu;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class DNAExtractorBlockEntity extends EnergyInventoryBlockEntity
{
    protected final ContainerData data = new ContainerData()
	{
		public int get(int index)
		{
			return switch (index)
					{
						case 0 -> DNAExtractorBlockEntity.this.energystorage.getEnergyStored();
						case 1 -> DNAExtractorBlockEntity.this.energystorage.getMaxEnergyStored();
						case 2 -> DNAExtractorBlockEntity.this.ticksPassed;
						default -> 0;
					};
		}

		public void set(int index, int value)
		{
			switch (index)
			{
				case 0 -> DNAExtractorBlockEntity.this.energystorage.setEnergyStored(value);
				case 1 -> DNAExtractorBlockEntity.this.energystorage.setEnergyMaxStored(value);
				case 2 -> DNAExtractorBlockEntity.this.ticksPassed = value;
			}
		}
		public int getCount()
		{
			return 3;
		}
	};

    public DNAExtractorBlockEntity()
	{
		this(BlockPos.ZERO, ModBlocks.DNA_EXTRACTOR.defaultBlockState());
	}

    public DNAExtractorBlockEntity(BlockPos pos, BlockState state)
    {
		super(ModTileEntities.DNAEXTRACTOR_TE, pos, state, 1000, 7, PlantTechConstants.MACHINETIER_DNA_EXTRACTOR);
    }

    @Override
    public void doUpdate()
    {
    	super.doUpdate();
		if (energystorage.getEnergyStored() > energyPerAction())
		{
			ItemStack stack1 = itemhandler.getStackInSlot(0);
			ItemStack stack2 = itemhandler.getStackInSlot(1);
			ItemStack stack3 = itemhandler.getStackInSlot(2);
			if (!stack1.isEmpty() && !stack2.isEmpty())
			{
				if (stack1.getItem() instanceof CropSeedItem && stack2.getItem() == ModItems.DNA_CONTAINER_EMPTY)
				{
					if (stack1.hasTag())
					{
						if (ticksPassed < ticksPerItem())
						{
							ticksPassed++;
							energystorage.extractEnergy(energyPerAction(), false);
						}
						else
						{
							if (stack3.isEmpty())
							{
								ItemStack stack = new ItemStack(ModItems.DNA_CONTAINER);
								CompoundTag nbt = stack1.getTag().copy();
								nbt.remove("analysed");
								stack.setTag(nbt);
								itemhandler.setStackInSlot(2, stack);
								endProcess();
								addKnowledge();
							}
							else if (stack3.hasTag() && stack3.getItem() == ModItems.DNA_CONTAINER)
							{
								if (stack3.getTag().equals(stack1.getTag()))
								{
									stack3.grow(1);
									addKnowledge();
									endProcess();
								}
							}
						}
					}
				}
			}
		}
    }
    
    @Override
	public ContainerData getContainerData()
	{
		return data;
	}

    private void endProcess()
    {
		ticksPassed = 0;
		itemhandler.getStackInSlot(0).shrink(1);
		itemhandler.getStackInSlot(1).shrink(1);
    }

    @Override
    public String getNameString()
    {
	return "dnaextractor";
    }

    @Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new DNAExtractorMenu(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 4;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 5;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 6;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 50;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 3;
	}
}
