package net.kaneka.planttech2.container;

import java.util.HashMap;
import java.util.Map;

import net.kaneka.planttech2.items.ItemCropSeed;
import net.kaneka.planttech2.items.upgradeable.IUpgradeable;
import net.kaneka.planttech2.items.upgradeable.ItemBaseUpgradeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerItemUpgradeable extends Container
{
	public final static Map<Integer, Integer[]> settings = new HashMap<Integer,Integer[]>() {
		private static final long serialVersionUID = 1L;

	{
		// invsize            rowcount, 	columncount, 	startx, starty
		put(10, new Integer[] {2,			5,			20,		20}); 
	}}; 
	
	public ContainerItemUpgradeable(InventoryPlayer playerInv, ItemStack itemInv)
	{ 
		LazyOptional<IItemHandler> provider = itemInv.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY); 
		if (provider != null)
		{
			IItemHandler handler = provider.orElse(null);
			if(handler != null)
			{
				int invsize = handler.getSlots();
				Integer[] setting = settings.get(invsize); 
				if(setting != null)
				{
					for(int y = 0; y < setting[0]; y++)
					{
						for(int x = 0; x < setting[1]; x++)
						{
							addSlot(new ChangeCheckSlot(itemInv, handler, x + y * setting[1], 47 + x * 18, 39 + y * 18));
						}
					}
				}
			}
		}
		for (int y = 0; y < 3; y++)
		{
		    for (int x = 0; x < 9; x++)
		    {
		    	this.addSlot(new Slot(playerInv, x + y * 9 + 9, 24 + x * 18, 107 + y * 18));
		    }
		}

		for (int x = 0; x < 9; x++)
		{
		    this.addSlot(new Slot(playerInv, x, 24 + x * 18, 165));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) 
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);
		if(slot != null && slot.getHasStack()) 
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			int invsize = ItemBaseUpgradeable.getInventorySize(stack);
			
			if(index > 35)
			{
				if (!this.mergeItemStack(stack1, 0, 35, true))
                {
                    return ItemStack.EMPTY;
                }
			}
			else if(index < 36)
			{
				if(!this.mergeItemStack(stack1, 36, 36 + invsize, false)) 
				{
					return ItemStack.EMPTY;
				}
				else if(index >= 0 && index < 27)
				{
					if(!this.mergeItemStack(stack1, 27, 35, false)) return ItemStack.EMPTY;
				}
				else if(index >= 27 && index < 36 && !this.mergeItemStack(stack1, 0, 26, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if(!this.mergeItemStack(stack1, 0, 35, false)) 
			{
				return ItemStack.EMPTY;
			}
			
			
			if(stack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();

			}
			if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		return stack;
	}
	
	class ChangeCheckSlot extends SlotItemHandler
	{
		private ItemStack stack; 
		
	    public ChangeCheckSlot(ItemStack stack, IItemHandler itemHandler, int index, int xPosition, int yPosition)
	    {
	    	super(itemHandler, index, xPosition, yPosition);
	    	this.stack = stack; 
	    }

	    @Override
	    public void onSlotChanged()
	    {
	    	if(stack.getItem() instanceof IUpgradeable)((IUpgradeable) stack.getItem()).updateNBTValues(stack);
	    	super.onSlotChanged();
	    }
	}
}
