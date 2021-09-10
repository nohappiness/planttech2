package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.items.upgradeable.IUpgradeable;
import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.HashMap;
import java.util.Map;

public class ItemUpgradeableContainer extends AbstractContainerMenu {
	public final static Map<Integer, Integer[]> settings = new HashMap<Integer, Integer[]>() {
		private static final long serialVersionUID = 1L;

		{
			// invsize            rowcount, 	columncount, 	startx, starty
			put(10, new Integer[]{2, 5, 20, 20});
		}
	};
	private int slot;
	private final ItemStack stack;

	public ItemUpgradeableContainer(int id, Inventory inv) {
		this(id, inv, new ItemStack(ModItems.CYBERBOW), -1);
	}

	public ItemUpgradeableContainer(int id, Inventory playerInv, ItemStack itemInv, int slot)
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
			slot = playerInv.findSlotMatchingItem(stack) + 1;
		this.slot = slot;
		for (int x = 0; x < 9; x++)
			addSlot(new Slot(playerInv, x, 24 + x * 18, 165));
		this.slot = this.slots.size() - 9 + slot;
	}

    @Override
	public boolean stillValid(Player playerIn)
	{
		return true;
	}

	@Override
	public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player)
	{
		if(slotId != this.slot) super.clicked(slotId, dragType, clickTypeIn, player);
	}

	class ChangeCheckSlot extends SlotItemHandlerWithInfo {
		private ItemStack stack;

		public ChangeCheckSlot(ItemStack stack, IItemHandler itemHandler, int index, int xPosition, int yPosition, String usage) {
			super(itemHandler, index, xPosition, yPosition, usage);
			this.stack = stack;
		}

		@Override
		public int getMaxStackSize() {
			return 1;
		}

		@Override
		public void setChanged() {
			if (stack.getItem() instanceof IUpgradeable) ((IUpgradeable) stack.getItem()).updateNBTValues(stack);
			super.setChanged();
		}
	}

	public class SlotItemHandlerWithInfo extends SlotItemHandler
	{
		private final String usage;

		public SlotItemHandlerWithInfo(IItemHandler itemHandler, int index, int xPosition, int yPosition, String usage)
		{
			super(itemHandler, index, xPosition, yPosition);
			this.usage = usage;
		}

		public String getUsageString()
		{
			return usage;
		}
	}

	public ItemStack getStack() {
		return stack;
	}

}