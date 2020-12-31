package net.kaneka.planttech2.container;

import net.kaneka.planttech2.container.BaseContainer.SlotItemHandlerWithInfo;
import net.kaneka.planttech2.items.upgradeable.BaseUpgradeableItem;
import net.kaneka.planttech2.items.upgradeable.IUpgradeable;
import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.HashMap;
import java.util.Map;

public class ItemUpgradeableContainer extends Container {
	public final static Map<Integer, Integer[]> settings = new HashMap<Integer, Integer[]>() {
		private static final long serialVersionUID = 1L;

		{
			// invsize            rowcount, 	columncount, 	startx, starty
			put(10, new Integer[]{2, 5, 20, 20});
		}
	};
	private int slot;
	private final ItemStack stack;

	public ItemUpgradeableContainer(int id, PlayerInventory inv) {
		this(id, inv, new ItemStack(ModItems.CYBERBOW), -1);
	}

	public ItemUpgradeableContainer(int id, PlayerInventory playerInv, ItemStack itemInv, int slot)
	{
		super(ModContainers.UPGRADEABLEITEM, id);
		this.stack = itemInv;
		LazyOptional<IItemHandler> provider = itemInv.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		IItemHandler handler = provider.orElseThrow(NullPointerException::new);
		int invsize = handler.getSlots();
		Integer[] setting = settings.get(invsize);
		if (setting != null)
			for (int y = 0; y < setting[0]; y++)
				for (int x = 0; x < setting[1]; x++)
					addSlot(new ChangeCheckSlot(itemInv, handler, x + y * setting[1], 47 + x * 18, 39 + y * 18, "slot.upgradeableitem.chipslot"));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlot(new Slot(playerInv, x + y * 9 + 9, 24 + x * 18, 107 + y * 18));
		if (slot == -1)
			slot = playerInv.getSlotFor(stack) + 1;
		this.slot = slot;
		for (int x = 0; x < 9; x++)
			addSlot(new Slot(playerInv, x, 24 + x * 18, 165));
		this.slot = this.inventorySlots.size() - 9 + slot;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return true;
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player)
	{
//		System.out.println("world isremote:" + player.getEntityWorld().isRemote + "slot: " + this.slot + "clicked slot: " + slotId);
		return slotId == this.slot ? ItemStack.EMPTY : super.slotClick(slotId, dragType, clickTypeIn, player);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			int invsize = BaseUpgradeableItem.getInventorySize(stack);

			if (index > 35) {
				if (!this.mergeItemStack(stack1, 0, 35, true)) {
					return ItemStack.EMPTY;
				}
			} else if (index < 36) {
				if (!this.mergeItemStack(stack1, 36, 36 + invsize, false)) {
					return ItemStack.EMPTY;
				} else if (index >= 0 && index < 27) {
					if (!this.mergeItemStack(stack1, 27, 35, false)) return ItemStack.EMPTY;
				} else if (index >= 27 && index < 36 && !this.mergeItemStack(stack1, 0, 26, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(stack1, 0, 35, false)) {
				return ItemStack.EMPTY;
			}


			if (stack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();

			}
			if (stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		return stack;
	}

	static class ChangeCheckSlot extends SlotItemHandlerWithInfo {
		private ItemStack stack;

		public ChangeCheckSlot(ItemStack stack, IItemHandler itemHandler, int index, int xPosition, int yPosition, String usage) {
			super(itemHandler, index, xPosition, yPosition, usage);
			this.stack = stack;
		}

		@Override
		public int getSlotStackLimit() {
			return 1;
		}

		@Override
		public void onSlotChanged() {
			if (stack.getItem() instanceof IUpgradeable) ((IUpgradeable) stack.getItem()).updateNBTValues(stack);
			super.onSlotChanged();
		}
	}

	public ItemStack getStack() {
		return stack;
	}

}