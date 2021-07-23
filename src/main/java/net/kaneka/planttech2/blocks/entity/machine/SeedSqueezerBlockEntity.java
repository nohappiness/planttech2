package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryFluidBlockEntity;
import net.kaneka.planttech2.inventory.SeedSqueezerContainer;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import org.antlr.runtime.misc.ContainerData;

import javax.annotation.Nullable;

import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

public class SeedSqueezerBlockEntity extends EnergyInventoryFluidBlockEntity
{
	private final RangedWrapper inputs;
	private final LazyOptional<IItemHandler> inputs_provider;
	protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			switch (index)
			{
				case 0:
					return SeedSqueezerBlockEntity.this.energystorage.getEnergyStored();
				case 1:
					return SeedSqueezerBlockEntity.this.energystorage.getMaxEnergyStored();
				case 2:
					return SeedSqueezerBlockEntity.this.biomassCap.getCurrentStorage();
				case 3:
					return SeedSqueezerBlockEntity.this.biomassCap.getMaxStorage();
				case 4:
					return SeedSqueezerBlockEntity.this.ticksPassed;
				default:
					return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
				case 0:
					SeedSqueezerBlockEntity.this.energystorage.setEnergyStored(value);
					break;
				case 1:
					SeedSqueezerBlockEntity.this.energystorage.setEnergyMaxStored(value);
					break;
				case 2:
					SeedSqueezerBlockEntity.this.biomassCap.setCurrentStorage(value);
					break;
				case 3:
					SeedSqueezerBlockEntity.this.biomassCap.setMaxStorage(value);
					break;
				case 4:
					SeedSqueezerBlockEntity.this.ticksPassed = value;
					break;
			}
		}
		public int getCount()
		{
			return 5;
		}
	};

	public SeedSqueezerBlockEntity()
	{
		super(ModTileEntities.SEEDSQUEEZER_TE, 10000, 16, 5000, PlantTechConstants.MACHINETIER_SEEDSQUEEZER);
		inputs = new RangedWrapper(itemhandler, 0,9); 
		inputs_provider = LazyOptional.of(() -> inputs);
	}
	
	@Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            if (facing != null) return inputs_provider.cast();
            return inventoryCap.cast();
        }
        return super.getCapability(capability, facing);
    }

	@Override
	public void doUpdate()
	{
		super.doUpdate();
		if (level != null && !level.isClientSide)
		{
			if (itemhandler.getStackInSlot(9).isEmpty())
			{
				int i = this.getSqueezeableItem();
				if (i != -1)
				{
					ItemStack stack = itemhandler.getStackInSlot(i);
					ItemStack stack2 = stack.copy();
					stack2.setCount(1);
					itemhandler.setStackInSlot(9, stack2);
					stack.shrink(1);
				}
			}
			if (!itemhandler.getStackInSlot(9).isEmpty())
			{
				ItemStack stack = itemhandler.getStackInSlot(9);
				if (stack.getCount() == 1 && (stack.getItem() instanceof CropSeedItem))
				{
					ticksPassed += getUpgradeTier(SPEED_UPGRADE) + 1;
					if (ticksPassed >= ticksPerItem())
					{
						squeezeItem();
						addKnowledge();
						biomassCap.changeCurrentStorage(10);
						resetProgress(true);
					}
				}
				else if (stack.getCount() > 0)
				{
					if (!level.isClientSide)
					{
						level.addFreshEntity(new ItemEntity(level, worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ(), stack));
						itemhandler.setStackInSlot(9, ItemStack.EMPTY);
					}
				}
				else if (ticksPassed > 0)
					resetProgress(false);
			}
		}
	}

	@Override
	public ContainerData getContainerData()
	{
		return field_array;
	}

	private int getSqueezeableItem()
	{
		for (int i = 0; i < 9; i++)
		{
			ItemStack stack = this.itemhandler.getStackInSlot(i);
			if (!stack.isEmpty())
			{
				if (stack.getItem() instanceof CropSeedItem)
					return i;
			}
		}
		return -1;
	}

	public void squeezeItem()
	{
		this.energystorage.receiveEnergy(getEnergyPerItem(), false);
		itemhandler.setStackInSlot(9, ItemStack.EMPTY);
	}

	@Override
	public int ticksPerItem()
	{
		return 200;
	}

	public int getEnergyPerItem()
	{
		ItemStack stack = this.itemhandler.getStackInSlot(9);
		if (!stack.isEmpty())
		{
			if (stack.getItem() instanceof CropSeedItem)
			{
				CompoundTag nbt = stack.getTag();
				if (nbt != null)
					if (nbt.contains("energyvalue"))
						return nbt.getInt("energyvalue") * 20;
			}
		}
		return 20;
	}

	@Override
	public String getNameString()
	{
		return "seedsqueezer";
	}

	@Override
	public int getFluidInSlot()
	{
		return 11;
	}

	@Override
	public int getFluidOutSlot()
	{
		return 12;
	}

	@Override
	public Container createMenu(int id, Inventory inv, Player player)
	{
		return new SeedSqueezerContainer(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 13;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 14;
	}
	
	@Override
	public int getKnowledgeChipSlot()
	{
		return 15;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 2;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 10;
	}
}
