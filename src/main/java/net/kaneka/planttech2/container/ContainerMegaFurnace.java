package net.kaneka.planttech2.container;

import net.kaneka.planttech2.tileentity.machine.TileEntityMegaFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMegaFurnace extends ContainerBase
{
	public ContainerMegaFurnace(InventoryPlayer player, TileEntityMegaFurnace tileentity) 
	{
		super(player, tileentity, 12);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		for(int y = 0; y < 2; y++)
		{
			for(int x = 0; x < 6; x++)
			{
				this.addSlot(new SlotItemHandler(handler, x + y * 6, 26 + x * 22 , 26 + y * 37));
			}
		}
		this.addSlot(new SlotItemHandler(handler, 12, 135, 85));
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
			
			if(index > 37)
			{
				if(!this.mergeItemStack(stack1, 0, 35, true)) return ItemStack.EMPTY;
				slot.onSlotChange(stack1, stack);
			}
			else if(index < 36)
			{
				if(!this.mergeItemStack(stack1, 36, 42, false)) 
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
