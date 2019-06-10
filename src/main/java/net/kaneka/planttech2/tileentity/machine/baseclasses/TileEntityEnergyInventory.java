package net.kaneka.planttech2.tileentity.machine.baseclasses;

import java.util.ArrayList;
import java.util.List;

import net.kaneka.planttech2.items.ItemWithTier;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
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

abstract public class TileEntityEnergyInventory extends TileEntityEnergy
{
    protected ItemStackHandler itemhandler;
    private LazyOptional<IItemHandler> inventoryCap;

    public TileEntityEnergyInventory(TileEntityType<?> type, int energyStorage, int invSize)
    {
	super(type, energyStorage);
	itemhandler = new ItemStackHandler(invSize);
	inventoryCap = LazyOptional.of(() -> itemhandler);
    }
    
    
    /**
     * Get Items that will be spawned in the world
     * when the block is destroyed
     * @return list of items to spawn
     */
    public List<ItemStack> getInventoryContent()
    {
	List<ItemStack> stack = new ArrayList<ItemStack>(); 
	
	for(int i = 0; i < itemhandler.getSlots(); i++)
	{
	    stack.add(itemhandler.getStackInSlot(i).copy()); 
	}
	
	return stack;
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
	if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean("doTileDrops") && !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
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
	    if (stack.getItem() instanceof ItemWithTier)
	    {
		ItemWithTier item = (ItemWithTier) stack.getItem();
		if (item.getItemType() == itemtype)
		{
		    return item.getTier();
		}
	    }
	}
	return 0;
    }

    /*
    @Override
    public int getSizeInventory()
    {
	return itemhandler.getSlots();
    }

    @Override
    public boolean isEmpty()
    {
	return false;
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
	return itemhandler.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
	itemhandler.getStackInSlot(index).shrink(count);
	return itemhandler.getStackInSlot(index);
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
	ItemStack stack = itemhandler.getStackInSlot(index).copy();
	itemhandler.setStackInSlot(index, ItemStack.EMPTY);
	return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
	itemhandler.setStackInSlot(index, stack);
	
    }

    @Override
    public int getInventoryStackLimit()
    {
	return 64;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
	
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
	
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
	return true;
    }

    @Override
    public int getFieldCount()
    {
	return getAmountFields();
    }

    @Override
    public void clear()
    {
	for(int i = 0; i < itemhandler.getSlots(); i++)
	{
	    itemhandler.setStackInSlot(i, ItemStack.EMPTY);
	}
	
    }
    */

}
