package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.SeedSqueezerBlockEntity;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class SeedSqueezerMenu extends BaseMenu
{
	public SeedSqueezerMenu(int id, Inventory inv)
	{
		this(id, inv, new SeedSqueezerBlockEntity());
	}
	
	public SeedSqueezerMenu(int id, Inventory player, SeedSqueezerBlockEntity tileentity)
	{
		super(id, ModContainers.SEEDQUEEZER, player, tileentity, 16);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		for(int y = 0; y < 3; y++)
			for(int x = 0; x < 3; x++)
				this.addSlot(new LimitedItemInfoSlot(handler, x + y * 3, 59 + x * 18, 28 + y * 18, "slot.seedsqueezer.input").setConditions((stack) -> stack.getItem() instanceof CropSeedItem));
		this.addSlot(new NoAccessSlot(handler, 9, 122, 46, "slot.seedsqueezer.squeeze"));
		this.addSlot(createSpeedUpgradeSlot(handler, 10, 97, 85));
		this.addSlot(createFluidInSlot(handler, 23, 38).setShouldListen());
		this.addSlot(createFluidOutSlot(handler, 23, 57).setShouldListen());
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
		
	}
	
	@Override
	public ItemStack quickMoveStack(Player playerIn, int index)
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.slots.get(index);
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

	class NoAccessSlot extends SlotItemHandlerWithInfo
	{

		public NoAccessSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, String usage)
		{
			super(itemHandler, index, xPosition, yPosition, usage);
		}

		@Override
		public boolean mayPickup(Player playerIn)
		{
			return false;
		}

		@Override
		public boolean mayPlace(ItemStack stack)
		{
			return false;
		}

	}
}