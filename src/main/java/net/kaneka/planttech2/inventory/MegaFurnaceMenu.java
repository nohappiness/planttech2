package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.MegaFurnaceBlockEntity;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class MegaFurnaceContainer extends BaseContainer
{
	public MegaFurnaceContainer(int id, Inventory inv)
	{
		this(id, inv, new MegaFurnaceBlockEntity());
	}
	public MegaFurnaceContainer(int id, Inventory player, MegaFurnaceBlockEntity tileentity)
	{
		super(id, ModContainers.MEGAFURNACE, player, tileentity, 15);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		for(int x = 0; x < 6; x++)
			this.addSlot(new SlotItemHandlerWithInfo(handler, x, 21 + x * 22 , 27, "slot.megafurnace.input"));
		for(int x = 0; x < 6; x++)
			this.addSlot(createOutoutSlot(handler, x + 6, 21 + x * 22 , 64));
		this.addSlot(createSpeedUpgradeSlot(handler, 12, 109, 85));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
	}
	
	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) 
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if(slot != null && slot.hasItem()) 
		{
			ItemStack stack1 = slot.getItem();
			stack = stack1.copy();
			
			if(index > 37)
			{
				if(!this.moveItemStackTo(stack1, 0, 35, true)) return ItemStack.EMPTY;
				slot.onQuickCraft(stack1, stack);
			}
			else if(index < 36)
			{
				if(!this.moveItemStackTo(stack1, 36, 42, false)) 
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
