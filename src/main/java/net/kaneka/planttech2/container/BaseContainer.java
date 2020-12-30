package net.kaneka.planttech2.container;


import net.kaneka.planttech2.items.TierItem;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BaseContainer extends Container
{
	protected final EnergyTileEntity tileentity;
	protected final IIntArray fieldArray;

	public BaseContainer(int id, ContainerType<?> type, PlayerInventory player, EnergyTileEntity tileentity)
	{
		super(type, id);
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlot(new Slot(player, x + y * 9 + 9, 23 + x * 18, 106 + y * 18));
		for (int x = 0; x < 9; x++)
			addSlot(new Slot(player, x, 23 + x * 18, 164));
		this.tileentity = tileentity;
		fieldArray = tileentity.getIntArray();
		trackIntArray(fieldArray);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return tileentity.isUsableByPlayer(playerIn);
	}

	public EnergyTileEntity getTE()
	{
		return tileentity;
	}

	public int getValue(int id)
	{
		return fieldArray.get(id);
	}

	public static class SlotItemHandlerWithInfo extends SlotItemHandler
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

	public static class SpeedUpgradeSlot extends SlotItemHandlerWithInfo
	{

		public SpeedUpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
		{
			super(itemHandler, index, xPosition, yPosition, "slot.util.speedupgrade");
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			Item item = stack.getItem();
			return item instanceof TierItem && ((TierItem) item).getItemType() == TierItem.ItemType.SPEED_UPGRADE;
		}

		@Override
		public int getItemStackLimit(ItemStack stack)
		{
			return 1;
		}
	}
}