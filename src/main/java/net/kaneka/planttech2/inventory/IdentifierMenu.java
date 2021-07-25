package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.IdentifierBlockEntity;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class IdentifierContainer extends BaseContainer
{
	public IdentifierContainer(int id, Inventory inv)
	{
		this(id, inv, new IdentifierBlockEntity());
	}
	
	public IdentifierContainer(int id, Inventory player, IdentifierBlockEntity tileentity)
	{
		super(id, ModContainers.IDENTIFIER, player, tileentity, 21);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		for(int x = 0; x < 3; x++)
			for(int y = 0; y < 3; y++)
				this.addSlot(new LimitedItemInfoSlot(handler, x + y * 3, 19 + x * 18, 27 + y * 18, "slot.identifier.input").setConditions((stack) -> stack.getItem() instanceof CropSeedItem));
		for(int x = 0; x < 3; x++)
			for(int y = 0; y < 3; y++)
				this.addSlot(createOutoutSlot(handler, x + y * 3 + 9, 95 + x * 18, 27 + y * 18));
		this.addSlot(createSpeedUpgradeSlot(handler, 18, 75, 85));
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
				if(!this.moveItemStackTo(stack1, 36, 45, false) && stack1.getItem() instanceof CropSeedItem) 
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
