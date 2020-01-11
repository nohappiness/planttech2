package net.kaneka.planttech2.container;

import net.kaneka.planttech2.container.BaseContainer.SlotItemHandlerWithInfo;
import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.tileentity.machine.PlantFarmTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class PlantFarmContainer extends BaseContainer
{
	public PlantFarmContainer(int id, PlayerInventory inv)
	{
		this(id, inv, new PlantFarmTileEntity()); 
	}
	
	public PlantFarmContainer(int id, PlayerInventory player, PlantFarmTileEntity tileentity) 
	{
		super(id, ModContainers.PLANTFARM, player, tileentity, 17);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		
		this.addSlot(new SlotItemHandlerWithInfo(handler, 0, 129, 38, "slot.plantfarm.seed"));
		
		for(int y = 0; y < 2; y++)
		{
			for(int x = 0; x < 5; x++)
			{
				this.addSlot(new SlotItemHandlerWithInfo(handler, 1 + x + y * 5, 24 + x * 18, 29 + y * 18, "slot.plantfarm.storage"));
			}
		}
		
		this.addSlot(new SlotItemHandlerWithInfo(handler, 11, 129, 38, "slot.util.squeezerupgrade"));
		this.addSlot(new SlotItemHandlerWithInfo(handler, 12, 129, 56, "slot.util.rangeupgrade"));
		this.addSlot(new SlotItemHandlerWithInfo(handler, tileentity.getFluidInSlot(), 18, 86, "slot.util.fluidin")); 
		this.addSlot(new SlotItemHandlerWithInfo(handler, tileentity.getFluidOutSlot(), 36, 86, "slot.util.fluidout")); 
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
				if(index >= 0 && index < 27)
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
