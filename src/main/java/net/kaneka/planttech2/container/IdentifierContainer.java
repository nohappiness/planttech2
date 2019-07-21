package net.kaneka.planttech2.container;

import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.tileentity.machine.IdentifierTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class IdentifierContainer extends BaseContainer
{
	public IdentifierContainer(int id, PlayerInventory inv)
	{
		this(id, inv, new IdentifierTileEntity()); 
	}
	
	public IdentifierContainer(int id, PlayerInventory player, IdentifierTileEntity tileentity) 
	{
		super(id, ModContainers.IDENTIFIER, player, tileentity, 21);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				this.addSlot(new SlotItemHandlerWithInfo(handler, x + y * 3, 24 + x * 18, 26 + y * 18, "slot.identifier.input"));
			}
		}
		
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				this.addSlot(new SlotItemHandlerWithInfo(handler, x + y * 3 + 9, 100 + x * 18, 26 + y * 18, "slot.util.output"));
			}
		}
		this.addSlot(new SlotItemHandlerWithInfo(handler, 18, 80, 84, "slot.util.speedupgrade"));
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
			
			if(index > 35)
			{
				if (!this.mergeItemStack(stack1, 0, 34, true))
                {
                    return ItemStack.EMPTY;
                }
			}
			else if(index < 36)
			{
				if(!this.mergeItemStack(stack1, 36, 45, false) && stack1.getItem() instanceof CropSeedItem) 
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
