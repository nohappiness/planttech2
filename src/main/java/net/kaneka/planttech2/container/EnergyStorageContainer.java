package net.kaneka.planttech2.container;

import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.tileentity.machine.EnergyStorageTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EnergyStorageContainer extends BaseContainer
{
	public EnergyStorageContainer(int id, PlayerInventory inv)
	{
		this(id, inv, new EnergyStorageTileEntity()); 
	}

    public EnergyStorageContainer(int id, PlayerInventory player, EnergyStorageTileEntity tileentity)
    {
		super(id, ModContainers.ENERGYSTORAGE,player, tileentity, 3);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		this.addSlot(createCapacityChipSlot(handler, 0, 132, 64));
		this.addSlot(createEnergyInSlot(handler, 150, 86));
		this.addSlot(createEnergyOutSlot(handler, 168, 86));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
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
    
    class ChangeCheckSlot extends SlotItemHandlerWithInfo
    {
        private EnergyStorageTileEntity te; 

        public ChangeCheckSlot(EnergyStorageTileEntity te, IItemHandler itemHandler, int index, int xPosition, int yPosition, String usage)
        {
    	super(itemHandler, index, xPosition, yPosition, usage);
    	this.te = te; 
        }

        @Override
        public void onSlotChanged()
        {
    	te.onSlotContentChanged();
    	super.onSlotChanged();
        }
    }

}


