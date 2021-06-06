package net.kaneka.planttech2.container;

import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.upgradeable.UpgradeChipItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.tileentity.machine.ChipalyzerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ChipalyzerContainer extends BaseContainer
{
	public ChipalyzerContainer(int id, PlayerInventory inv)
	{
		this(id, inv, new ChipalyzerTileEntity()); 
	}
	
	public ChipalyzerContainer(int id, PlayerInventory player, ChipalyzerTileEntity tileentity) 
	{
		super(id, ModContainers.CHIPALYZER, player, tileentity, 5);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		this.addSlot(new SlotItemHandlerWithInfo(handler, 0, 41, 47, "slot.chipalyzer.chipinput").setShouldListen());
		this.addSlot(new SlotItemHandlerWithInfo(handler, 1, 68, 27, "slot.chipalyzer.iteminput").setShouldListen());
		this.addSlot(createOutoutSlot(handler, tileentity.getOutputSlotIndex(), 95, 47));
		this.addSlot(createSpeedUpgradeSlot(handler, 3, 68, 69));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) 
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.slots.get(index);
		if(slot != null && slot.hasItem()) 
		{
			ItemStack stack1 = slot.getItem();
			stack = stack1.copy();
			
			if(index > 35)
			{
				if (!this.moveItemStackTo(stack1, 0, 34, true))
                {
                    return ItemStack.EMPTY;
                }
			}
			else if(index < 36)
			{
				if(!this.moveItemStackTo(stack1, 36, 37, false) && stack1.getItem() instanceof CropSeedItem) 
				{
					return ItemStack.EMPTY;
				}
				else if(index >= 0 && index < 27)
				{
					if(!this.moveItemStackTo(stack1, 27, 35, false)) return ItemStack.EMPTY;
				}
				else if(index >= 27 && index < 36 && !this.moveItemStackTo(stack1, 0, 26, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if(!this.moveItemStackTo(stack1, 0, 35, false)) 
			{
				return ItemStack.EMPTY;
			}
			
			
			if(stack1.isEmpty())
			{
				slot.set(ItemStack.EMPTY);
			}
			else
			{
				slot.setChanged();

			}
			if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		return stack;
	}
}
