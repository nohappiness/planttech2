package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.kaneka.planttech2.crops.CropEntry;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.inventory.IdentifierMenu;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nullable;

public class IdentifierBlockEntity extends EnergyInventoryBlockEntity
{
	private final RangedWrapper inputs;
	private final RangedWrapper outputs;
	private final LazyOptional<IItemHandler> inputs_provider;
	private final LazyOptional<IItemHandler> outputs_provider;
	protected final ContainerData data = new ContainerData()
	{
		public int get(int index)
		{
			return switch (index)
					{
						case 0 -> IdentifierBlockEntity.this.energystorage.getEnergyStored();
						case 1 -> IdentifierBlockEntity.this.energystorage.getMaxEnergyStored();
						case 2 -> IdentifierBlockEntity.this.ticksPassed;
						default -> 0;
					};
		}

		public void set(int index, int value)
		{
			switch (index)
			{
				case 0 -> IdentifierBlockEntity.this.energystorage.setEnergyStored(value);
				case 1 -> IdentifierBlockEntity.this.energystorage.setEnergyMaxStored(value);
				case 2 -> IdentifierBlockEntity.this.ticksPassed = value;
			}
		}
		public int getCount()
		{
			return 3;
		}
	};

	public IdentifierBlockEntity()
	{
		this(BlockPos.ZERO, ModBlocks.IDENTIFIER.defaultBlockState());
	}

	public IdentifierBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.IDENTIFIER_TE, pos, state, 10000, 22, PlantTechConstants.MACHINETIER_IDENTIFIER);
		inputs = new RangedWrapper(itemhandler, 0,9); 
		outputs = new RangedWrapper(itemhandler, 9, 18); 
		inputs_provider = LazyOptional.of(() -> inputs);
		outputs_provider = LazyOptional.of(() -> outputs);
	}
	
	@Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            if (facing == Direction.DOWN) return outputs_provider.cast();
            if (facing != null) return inputs_provider.cast();
            return inventoryCap.cast();
        }
        return super.getCapability(capability, facing);
    }

	@Override
	public void doUpdate()
	{
		super.doUpdate();
		if (this.energystorage.getEnergyStored() > energyPerAction())
		{
			if (this.canIdentify())
			{
				this.energystorage.extractEnergy(energyPerAction(), false);
				ticksPassed++;
				if (ticksPassed >= ticksPerItem())
				{
					this.identifyItem();
					ticksPassed = 0;
				}
			}
			else
				ticksPassed = 0;
		}
		else if (!this.canIdentify() && ticksPassed > 0)
			ticksPassed = 0;
	}

	@Override
	public ContainerData getContainerData()
	{
		return data;
	}

	private boolean canIdentify()
	{
		if (!this.hasFreeOutputSlot())
			return false;
		for (int i = 0; i < 9; i++)
		{
			ItemStack stack = this.itemhandler.getStackInSlot(i);
			if (!stack.isEmpty())
			{
				CompoundTag nbt = stack.getTag();
				if (nbt != null)
				{
					if (nbt.contains("analysed"))
						if (!nbt.getBoolean("analysed"))
							return true;
				}
				else
					return PlantTechMain.getCropList().getBySeed(stack.getItem()) != null;

			}
		}
		return false;
	}

	public void identifyItem()
	{
		if (this.canIdentify())
		{
			for (int i = 0; i < 9; i++)
			{
				ItemStack stack = this.itemhandler.getStackInSlot(i);
				if (!stack.isEmpty())
				{
					CompoundTag nbt = stack.getTag();
					if (nbt != null)
					{
						if (nbt.contains("analysed"))
						{
							if (!nbt.getBoolean("analysed"))
							{
								nbt.putBoolean("analysed", true);
								stack.setTag(nbt);
								this.itemhandler.setStackInSlot(this.getFreeOutputSlot(), stack);
								this.itemhandler.setStackInSlot(i, ItemStack.EMPTY);
								addKnowledge();
								break;
							}
						}
					}
					else
					{
						CropEntry entry = PlantTechMain.getCropList().getBySeed(stack.getItem());
				    	if (entry != null)
				    	{
				    		HashMapCropTraits newtraits = new HashMapCropTraits();
				    		newtraits.setType(entry.getName());
				    		newtraits.setAnalysed(true);
				    		ItemStack result = new ItemStack(entry.getPrimarySeed().getItem().get()).copy();
				    		result.setCount(stack.getCount());
				    		newtraits.addToItemStack(stack);
				    		this.itemhandler.setStackInSlot(this.getFreeOutputSlot(), result);
							this.itemhandler.setStackInSlot(i, ItemStack.EMPTY);
							addKnowledge();
				    	}
					}
				}
			}
		}
	}

	public boolean hasFreeOutputSlot()
	{
		return getFreeOutputSlot() != -1;
	}

	public int getFreeOutputSlot()
	{
		for (int i = 9; i < 18; i++)
		{
			ItemStack stack = this.itemhandler.getStackInSlot(i);
			if (stack.isEmpty())
				return i;
		}
		return -1;
	}

	@Override
	public String getNameString()
	{
		return "identifier";
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new IdentifierMenu(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 19;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 20;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 21;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 5;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 18;
	}
}
