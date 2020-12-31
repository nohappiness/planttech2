package net.kaneka.planttech2.container;


import net.kaneka.planttech2.energy.IItemChargeable;
import net.kaneka.planttech2.items.KnowledgeChip;
import net.kaneka.planttech2.items.TierItem;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryFluidTileEntity;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
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

import java.util.function.Predicate;

public class BaseContainer extends Container
{
	protected final EnergyInventoryTileEntity tileentity;
	protected final IIntArray fieldArray;

	public BaseContainer(int id, ContainerType<?> type, PlayerInventory player, EnergyInventoryTileEntity tileentity, int slots)
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

	protected LimitedItemInfoSlot createSpeedUpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, index, xPosition, yPosition, "slot.util.speedupgrade", (stack) -> {
			Item item = stack.getItem();
			return item instanceof TierItem && ((TierItem) item).getItemType() == TierItem.ItemType.SPEED_UPGRADE;
		});
	}

	protected LimitedItemInfoSlot createRangeUpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, index, xPosition, yPosition, "slot.util.rangeupgrade", (stack) -> {
			Item item = stack.getItem();
			return item instanceof TierItem && ((TierItem) item).getItemType() == TierItem.ItemType.RANGE_UPGRADE;
		});
	}

	protected LimitedItemInfoSlot createKnowledgeChipSlot(IItemHandler itemHandler, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, tileentity.getKnowledgeChipSlot(), xPosition, yPosition, "slot.util.knowledgechip", (stack) -> stack.getItem() instanceof KnowledgeChip);
	}

	protected LimitedItemInfoSlot createEnergyInSlot(IItemHandler itemHandler, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, tileentity.getEnergyInSlot(), xPosition, yPosition, "slot.util.energyin", (stack) -> stack.getItem() instanceof IItemChargeable);
	}

	protected LimitedItemInfoSlot createEnergyOutSlot(IItemHandler itemHandler, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, tileentity.getEnergyOutSlot(), xPosition, yPosition, "slot.util.energyout", (stack) -> stack.getItem() instanceof IItemChargeable);
	}

	protected LimitedItemInfoSlot createFluidInSlot(IItemHandler itemHandler, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, ((EnergyInventoryFluidTileEntity) tileentity).getFluidInSlot(), xPosition, yPosition, "slot.util.fluidin");
	}

	protected LimitedItemInfoSlot createFluidOutSlot(IItemHandler itemHandler, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, ((EnergyInventoryFluidTileEntity) tileentity).getFluidOutSlot(), xPosition, yPosition, "slot.util.fluidout");
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

	public static class LimitedItemInfoSlot extends SlotItemHandlerWithInfo
	{
		private final Predicate<ItemStack> conditions;

		public LimitedItemInfoSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, String usage)
		{
			this(itemHandler, index, xPosition, yPosition, usage, (stack) -> true);
		}

		public LimitedItemInfoSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, String usage, Predicate<ItemStack> conditions)
		{
			super(itemHandler, index, xPosition, yPosition, usage);
			this.conditions = conditions;
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return conditions.test(stack);
		}

		@Override
		public int getItemStackLimit(ItemStack stack)
		{
			return 1;
		}
	}
}