package net.kaneka.planttech2.tileentity.machine;

import javax.annotation.Nullable;

import net.kaneka.planttech2.container.SeedSqueezerContainer;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryFluidTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

public class SeedSqueezerTileEntity extends EnergyInventoryFluidTileEntity
{
	public int ticksPassed = 0;
	
	private RangedWrapper inputs; 
	private LazyOptional<IItemHandler> inputs_provider;
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
				case 0:
					return SeedSqueezerTileEntity.this.energystorage.getEnergyStored();
				case 1:
					return SeedSqueezerTileEntity.this.energystorage.getMaxEnergyStored();
				case 2:
					return SeedSqueezerTileEntity.this.BIOMASS_CAP.getCurrentStorage();
				case 3:
					return SeedSqueezerTileEntity.this.BIOMASS_CAP.getMaxStorage();
				case 4:
					return SeedSqueezerTileEntity.this.ticksPassed;
				default:
					return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
				case 0:
					SeedSqueezerTileEntity.this.energystorage.setEnergyStored(value);
					break;
				case 1:
					SeedSqueezerTileEntity.this.energystorage.setEnergyMaxStored(value);
					break;
				case 2:
					SeedSqueezerTileEntity.this.BIOMASS_CAP.setCurrentStorage(value);
					break;
				case 3:
					SeedSqueezerTileEntity.this.BIOMASS_CAP.setMaxStorage(value);
					break;
				case 4:
					SeedSqueezerTileEntity.this.ticksPassed = value;
					break;
			}

		}

		public int size()
		{
			return 5;
		}
	};

	public SeedSqueezerTileEntity()
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
	public void tick()
	{
		if (!world.isRemote)
		{
			if (itemhandler.getStackInSlot(9).isEmpty())
			{
				int i = this.getSqueezeableItem();
				if (i != 100)
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
					ticksPassed += getUpgradeTier(10, SPEED_UPGRADE) + 1;
					if (ticksPassed >= this.getTicksPerItem())
					{
						squeezeItem();
						addKnowledge();
						BIOMASS_CAP.changeCurrentStorage(10);
						ticksPassed = 0;
					}
				} else if (stack.getCount() > 0)
				{
					if (!world.isRemote)
					{
						world.addEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
						itemhandler.setStackInSlot(9, ItemStack.EMPTY);
					}
				} else if (ticksPassed > 0)
				{
					ticksPassed = 0;
				}
			}
		}
		doEnergyLoop();
		doFluidLoop();
	}
	
	@Override
	public IIntArray getIntArray()
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
				{
					return i;
				}
			}
		}
		return 100;
	}

	public void squeezeItem()
	{
		this.energystorage.receiveEnergy(getEnergyPerItem(), false);
		itemhandler.setStackInSlot(9, ItemStack.EMPTY);
	}

	public int getTicksPerItem()
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
				CompoundNBT nbt = stack.getTag();
				if (nbt != null)
				{
					if (nbt.contains("energyvalue"))
					{
						return nbt.getInt("energyvalue") * 20;
					}
				}
			}
		}
		return 20;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putInt("cooktime", ticksPassed);
		super.write(compound);
		return compound;
	}

	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		this.ticksPassed = compound.getInt("cooktime");
		super.read(state, compound);
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
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
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

}
