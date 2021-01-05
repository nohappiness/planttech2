package net.kaneka.planttech2.container;

import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.tileentity.machine.DNACleanerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class DNACleanerContainer extends BaseContainer
{
	public DNACleanerContainer(int id, PlayerInventory inv)
	{
		this(id, inv, new DNACleanerTileEntity()); 
	}

    public DNACleanerContainer(int id, PlayerInventory player, DNACleanerTileEntity tileentity)
    {
		super(id, ModContainers.DNACLEANER, player, tileentity, 5);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);

		this.addSlot(createDNAContainerSlot(handler, 0, 41, 47, "slot.dnacleaner.input", false));
		this.addSlot(createOutoutSlot(handler, 1, 95, 47));
		this.addSlot(createSpeedUpgradeSlot(handler, 2, 68, 69));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack())
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			if (index > 35)
			{
				if (!this.mergeItemStack(stack1, 0, 34, true))
					return ItemStack.EMPTY;
			}
			else
			{
				if (!this.mergeItemStack(stack1, 36, 37, false))
					return ItemStack.EMPTY;
				else if (index < 27)
					if (!this.mergeItemStack(stack1, 27, 35, false))
						return ItemStack.EMPTY;
			}
			if (stack1.isEmpty())
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();
			if (stack1.getCount() == stack.getCount())
				return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		return stack;
    }

}
