package net.kaneka.planttech2.container;

import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.tileentity.machine.MegaFurnaceTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class MegaFurnaceContainer extends BaseContainer
{
	public MegaFurnaceContainer(int id, PlayerInventory inv)
	{
		this(id, inv, new MegaFurnaceTileEntity()); 
	}
	public MegaFurnaceContainer(int id, PlayerInventory player, MegaFurnaceTileEntity tileentity) 
	{
		super(id, ModContainers.MEGAFURNACE, player, tileentity, 15);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		for(int y = 0; y < 2; y++)
		{
			String usage = "slot.megafurnace.input"; 
			if(y == 1)
			{
				usage = "slot.util.output"; 
			}
			for(int x = 0; x < 6; x++)
			{
				this.addSlot(new SlotItemHandlerWithInfo(handler, x + y * 6, 26 + x * 22 , 26 + y * 37, usage));
			}
		}
		this.addSlot(new SlotItemHandlerWithInfo(handler, 12, 113, 85, "slot.util.speedupgrade"));
		this.addSlot(new SlotItemHandlerWithInfo(handler, tileentity.getEnergyInSlot(), 150, 86, "slot.util.energyin"));
		this.addSlot(new SlotItemHandlerWithInfo(handler, tileentity.getEnergyOutSlot(), 168, 86, "slot.util.energyout"));
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) 
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
