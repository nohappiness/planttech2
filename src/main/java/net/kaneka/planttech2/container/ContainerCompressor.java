package net.kaneka.planttech2.container;

import net.kaneka.planttech2.items.ItemCropSeed;
import net.kaneka.planttech2.tileentity.machine.TileEntityCompressor;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNACleaner;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNAExtractor;
import net.kaneka.planttech2.tileentity.machine.TileEntityIdentifier;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCompressor extends ContainerBase
{

    public ContainerCompressor(InventoryPlayer player, TileEntityCompressor tileentity)
    {
	super(player, tileentity, 18);
	IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);

	this.addSlot(new ChangeCheckSlot(tileentity, handler, 0, 35, 84));
	this.addSlot(new SlotItemHandler(handler, 1, 127, 84));
	this.addSlot(new SlotItemHandler(handler, 2, 79, 88));
	
	for (int y = 0; y < 3; y++)
	{
	    for(int x = 0; x < 6; x++)
	    {
		addSlot(new NoAccessSlot(handler, x + y * 6 + 3, 36 + x * 18, 27 + y * 18));
	    }
	}
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
	ItemStack stack = ItemStack.EMPTY;
	Slot slot = (Slot) this.inventorySlots.get(index);
	if (slot != null && slot.getHasStack())
	{
	    ItemStack stack1 = slot.getStack();
	    stack = stack1.copy();

	    if (index > 35)
	    {
		if (!this.mergeItemStack(stack1, 0, 34, true))
		{
		    return ItemStack.EMPTY;
		}
	    }
	    else if (index < 36)
	    {
		if (!this.mergeItemStack(stack1, 36, 37, false))
		{
		    return ItemStack.EMPTY;
		}
		else if (index >= 0 && index < 27)
		{
		    if (!this.mergeItemStack(stack1, 27, 35, false))
			return ItemStack.EMPTY;
		}
		else if (index >= 27 && index < 36 && !this.mergeItemStack(stack1, 0, 26, false))
		{
		    return ItemStack.EMPTY;
		}
	    }
	    else if (!this.mergeItemStack(stack1, 0, 35, false))
	    {
		return ItemStack.EMPTY;
	    }

	    if (stack1.isEmpty())
	    {
		slot.putStack(ItemStack.EMPTY);
	    }
	    else
	    {
		slot.onSlotChanged();

	    }
	    if (stack1.getCount() == stack.getCount())
		return ItemStack.EMPTY;
	    slot.onTake(playerIn, stack1);
	}
	return stack;
    }

}


class ChangeCheckSlot extends SlotItemHandler
{
    private TileEntityEnergy te; 

    public ChangeCheckSlot(TileEntityEnergy te, IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
	super(itemHandler, index, xPosition, yPosition);
	this.te = te; 
    }

    @Override
    public void onSlotChanged()
    {
	te.onSlotContentChanged();
	super.onSlotChanged();
    }
}
