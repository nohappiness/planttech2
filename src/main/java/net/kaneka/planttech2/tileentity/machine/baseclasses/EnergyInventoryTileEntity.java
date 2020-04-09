package net.kaneka.planttech2.tileentity.machine.baseclasses;

import java.util.ArrayList;
import java.util.List;

import net.kaneka.planttech2.energy.IItemChargeable;
import net.kaneka.planttech2.items.KnowledgeChip;
import net.kaneka.planttech2.items.TierItem;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

abstract public class EnergyInventoryTileEntity extends EnergyTileEntity
{
	protected ItemStackHandler itemhandler;
	protected LazyOptional<IItemHandler> inventoryCap;
	protected int tier; 

	public EnergyInventoryTileEntity(TileEntityType<?> type, int energyStorage, int invSize, int tier)
	{
		super(type, energyStorage);
		itemhandler = new ItemStackHandler(invSize);
		inventoryCap = LazyOptional.of(() -> itemhandler);
		this.tier = tier;
	}

	/**
	 * Get Items that will be spawned in the world when the block is destroyed
	 * 
	 * @return list of items to spawn
	 */
	public List<ItemStack> getInventoryContent()
	{
		List<ItemStack> stack = new ArrayList<ItemStack>();

		for (int i = 0; i < itemhandler.getSlots(); i++)
		{
			stack.add(itemhandler.getStackInSlot(i).copy());
		}

		return stack;
	}

	public void doEnergyLoop()
	{
		ItemStack stack = itemhandler.getStackInSlot(getEnergyInSlot());
		ItemStack stack2 = itemhandler.getStackInSlot(getEnergyOutSlot());
		if (stack != null)
		{
			if (stack.getItem() instanceof IItemChargeable)
			{
				if(energystorage.getEnergyStored() < energystorage.getMaxEnergyStored())
				{
					energystorage.receiveEnergy(((IItemChargeable) stack.getItem()).extractEnergy(stack, 1, false));
				}
			}
		}

		if (stack2 != null)
		{
			if (stack2.getItem() instanceof IItemChargeable)
			{
				if(energystorage.getEnergyStored() >= 1)
				{
					energystorage.extractEnergy(((IItemChargeable) stack2.getItem()).receiveEnergy(stack2, 1, false));
				}
			}
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return inventoryCap.cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.put("inventory", itemhandler.serializeNBT());
		super.write(compound);
		return compound;
	}

	@Override
	public void read(CompoundNBT compound)
	{
		int slotamount = itemhandler.getSlots();// prevent crash
		itemhandler.deserializeNBT(compound.getCompound("inventory"));
		if (itemhandler.getSlots() != slotamount)
			itemhandler.setSize(slotamount);// prevent crash when invsize changed while update
		super.read(compound);
	}

	public static void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack)
	{
		// TODO Implement Gamerule "doTileDrops"
		if (!worldIn.isRemote && !stack.isEmpty() && !worldIn.restoringBlockSnapshots) // do not drop items while restoring
		                                                                               // blockstates, prevents item dupe
		{
			double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
			double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
			double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
			ItemEntity entityitem = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
			entityitem.setDefaultPickupDelay();
			worldIn.addEntity(entityitem);
		}
	}

	public int getUpgradeTier(int slot, int itemtype)
	{
		ItemStack stack = itemhandler.getStackInSlot(slot);
		if (!stack.isEmpty())
		{
			if (stack.getItem() instanceof TierItem)
			{
				TierItem item = (TierItem) stack.getItem();
				if (item.getItemType() == itemtype)
				{
					return item.getTier();
				}
			}
		}
		return 0;
	}

	protected abstract int getEnergyInSlot();

	protected abstract int getEnergyOutSlot();
	

	
	protected ItemStack getKnowledgeChip()
	{
		return itemhandler.getStackInSlot(getKnowledgeChipSlot()); 
	}
	
	protected void addKnowledge()
	{
		ItemStack chip = getKnowledgeChip(); 
		ItemStack newChip = KnowledgeChip.addKnowledge(chip, getKnowledgePerAction(), tier); 
		if(!chip.isEmpty() && !newChip.isEmpty())
		{
			if(!chip.getItem().equals(newChip.getItem()))
			{
				itemhandler.setStackInSlot(getKnowledgeChipSlot(), newChip);
			}
		}
	}
	
	public abstract int getKnowledgeChipSlot();
	
	public abstract int getKnowledgePerAction();
}
