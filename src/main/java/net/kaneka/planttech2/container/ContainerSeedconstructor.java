package net.kaneka.planttech2.container;

import net.kaneka.planttech2.items.ItemCropSeed;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedconstructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSeedconstructor extends ContainerBase
{
	
	public ContainerSeedconstructor(InventoryPlayer player, TileEntitySeedconstructor tileentity) 
	{
		super(player, tileentity, 18);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		this.addSlot(new SlotItemHandler(handler, 0, 96, 30));
		this.addSlot(new SlotItemHandler(handler, 1, 96, 67));
		this.addSlot(new SlotItemHandler(handler, 2, 72, 44));

		this.addSlot(new SlotItemHandler(handler, 3, 18, 86));
		this.addSlot(new SlotItemHandler(handler, 4, 36, 86));
		
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
			
			if(index > 35)
			{
				if (!this.mergeItemStack(stack1, 0, 34, true))
                {
                    return ItemStack.EMPTY;
                }
			}
			else if(index < 36)
			{
				if(!this.mergeItemStack(stack1, 36, 38, false) && stack1.getItem() instanceof ItemCropSeed) 
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

}
