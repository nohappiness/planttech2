package net.kaneka.planttech2.container;

import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.tileentity.machine.DNACombinerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class DNACombinerContainer extends BaseContainer
{
	
	public DNACombinerContainer(int id, PlayerInventory inv)
	{
		this(id, inv, new DNACombinerTileEntity());
	}
	
	public DNACombinerContainer(int id, PlayerInventory player, DNACombinerTileEntity tileentity)
	{
		super(id, ModContainers.DNACOMBINER, player, tileentity, 7);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		this.addSlot(createDNAContainerSlot(handler, 0, 77, 37, "slot.dnacombiner.container", false));
		this.addSlot(createDNAContainerSlot(handler, 1, 113, 37, "slot.dnacombiner.container", false));
		this.addSlot(createDNAContainerSlot(handler, 2, 121, 56, "slot.dnacombiner.empty_container", true));
		this.addSlot(createOutoutSlot(handler, 3, 95, 73));
		this.addSlot(createSpeedUpgradeSlot(handler, 4, 54, 50));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler,167, 57));
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
			else
			{
				if(!this.moveItemStackTo(stack1, 36, 39, false))
				{
					return ItemStack.EMPTY;
				}
				else if(index < 27)
				{
					if(!this.moveItemStackTo(stack1, 27, 35, false)) return ItemStack.EMPTY;
				}
				else if(!this.moveItemStackTo(stack1, 0, 26, false))
				{
					return ItemStack.EMPTY;
				}
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
