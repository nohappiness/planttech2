package net.kaneka.planttech2.inventory;


import net.kaneka.planttech2.BlockEntity.machine.baseclasses.EnergyBlockEntity;
import net.kaneka.planttech2.BlockEntity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.kaneka.planttech2.BlockEntity.machine.baseclasses.EnergyInventoryFluidBlockEntity;
import net.kaneka.planttech2.energy.IItemChargeable;
import net.kaneka.planttech2.items.KnowledgeChip;
import net.kaneka.planttech2.items.TierItem;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IIntArray;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Arrays;
import java.util.function.Predicate;

public class BaseContainer extends AbstractContainerMenu
{
	protected final EnergyInventoryBlockEntity BlockEntity;
	protected final IIntArray fieldArray;

	public BaseContainer(int id, ContainerType<?> type, Inventory player, EnergyInventoryBlockEntity BlockEntity, int slots)
	{
		super(type, id);
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlot(new Slot(player, x + y * 9 + 9, 23 + x * 18, 106 + y * 18));
		for (int x = 0; x < 9; x++)
			addSlot(new Slot(player, x, 23 + x * 18, 164));
		this.BlockEntity = BlockEntity;
		fieldArray = BlockEntity.getIntArray();
		addDataSlots(fieldArray);
	}

	@Override
	public boolean stillValid(Player playerIn)
	{
		return BlockEntity.isUsableByPlayer(playerIn);
	}

	public EnergyBlockEntity getTE()
	{
		return BlockEntity;
	}

	public int getValue(int id)
	{
		return fieldArray.get(id);
	}

	protected LimitedItemInfoSlot createSpeedUpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, index, xPosition, yPosition, "slot.util.speedupgrade").setConditions((stack) -> {
			Item item = stack.getItem();
			return item instanceof TierItem && ((TierItem) item).getItemType() == TierItem.ItemType.SPEED_UPGRADE;
		}).setLimited();
	}

	protected LimitedItemInfoSlot createRangeUpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, index, xPosition, yPosition, "slot.util.rangeupgrade").setConditions((stack) -> {
			Item item = stack.getItem();
			return item instanceof TierItem && ((TierItem) item).getItemType() == TierItem.ItemType.RANGE_UPGRADE;
		}).setLimited();
	}

	protected LimitedItemInfoSlot createKnowledgeChipSlot(IItemHandler itemHandler, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, BlockEntity.getKnowledgeChipSlot(), xPosition, yPosition, "slot.util.knowledgechip").setConditions((stack) -> stack.getItem() instanceof KnowledgeChip);
	}

	protected LimitedItemInfoSlot createCapacityChipSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		return (LimitedItemInfoSlot) new LimitedItemInfoSlot(itemHandler, index, xPosition, yPosition, "slot.util.energystorageupgrade").setConditions((stack) -> 			{
			Item item = stack.getItem();
			return item instanceof TierItem && ((TierItem) item).getItemType() == TierItem.ItemType.CAPACITY_UPGRADE;
		}).setLimited().setShouldListen();
	}

	protected LimitedItemInfoSlot createEnergyInSlot(IItemHandler itemHandler, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, BlockEntity.getEnergyInSlot(), xPosition, yPosition, "slot.util.energyin").setConditions((stack) -> stack.getItem() instanceof IItemChargeable);
	}

	protected LimitedItemInfoSlot createEnergyOutSlot(IItemHandler itemHandler, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, BlockEntity.getEnergyOutSlot(), xPosition, yPosition, "slot.util.energyout").setConditions((stack) -> stack.getItem() instanceof IItemChargeable);
	}

	protected LimitedItemInfoSlot createFluidInSlot(IItemHandler itemHandler, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, ((EnergyInventoryFluidBlockEntity) BlockEntity).getFluidInSlot(), xPosition, yPosition, "slot.util.fluidin");
	}

	protected LimitedItemInfoSlot createFluidOutSlot(IItemHandler itemHandler, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, ((EnergyInventoryFluidBlockEntity) BlockEntity).getFluidOutSlot(), xPosition, yPosition, "slot.util.fluidout");
	}

	protected LimitedItemInfoSlot createFakeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, String usage)
	{
		return new LimitedItemInfoSlot(itemHandler, index, xPosition, yPosition, usage).setConditions(false).setCanTake(false);
	}

	protected LimitedItemInfoSlot createOutoutSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		return new LimitedItemInfoSlot(itemHandler, index, xPosition, yPosition, "slot.util.output").setConditions(false);
	}

	protected LimitedItemInfoSlot createDNAContainerSlot(IItemHandler handler, int index, int xPosition, int yPosition, String usage, boolean isEmpty)
	{
		return new LimitedItemInfoSlot(handler, index, xPosition, yPosition, usage).setConditions(
				(stack) -> (isEmpty && stack.getItem() == ModItems.DNA_CONTAINER_EMPTY) || (!isEmpty && stack.getItem() == ModItems.DNA_CONTAINER && stack.hasTag()));
	}

	public class SlotItemHandlerWithInfo extends SlotItemHandler
	{
		private final String usage;
		protected boolean listening = false;

		public SlotItemHandlerWithInfo(IItemHandler itemHandler, int index, int xPosition, int yPosition, String usage)
		{
			super(itemHandler, index, xPosition, yPosition);
			this.usage = usage;
		}

		public String getUsageString()
		{
			return usage;
		}

		@Override
		public void setChanged()
		{
			super.setChanged();
			if (listening)
			{
				BaseContainer.this.broadcastChanges();
				BaseContainer.this.BlockEntity.onContainerUpdated(getSlotIndex());
			}
		}

		public SlotItemHandlerWithInfo setShouldListen()
		{
			listening = true;
			return this;
		}
	}

	public class LimitedItemInfoSlot extends SlotItemHandlerWithInfo
	{
		private Predicate<ItemStack> conditions = (stack) -> true;
		private Predicate<Player> canTake = (stack) -> true;

		private boolean limited = false;

		public LimitedItemInfoSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, String usage)
		{
			super(itemHandler, index, xPosition, yPosition, usage);
		}

		@Override
		public boolean mayPlace(ItemStack stack)
		{
			return conditions.test(stack);
		}

		@Override
		public boolean mayPickup(Player playerIn)
		{
			return canTake.test(playerIn);
		}

		@Override
		public int getMaxStackSize(ItemStack stack)
		{
			return limited ? 1 : super.getMaxStackSize(stack);
		}

		public LimitedItemInfoSlot setLimited()
		{
			limited = true;
			return this;
		}

		public LimitedItemInfoSlot setConditions(boolean enabled)
		{
			return setConditions((stack) -> enabled);
		}

		public LimitedItemInfoSlot setConditions(Item... acceptableItems)
		{
			return setConditions((stack) -> acceptableItems.length == 0 || Arrays.stream(acceptableItems).anyMatch((item) -> stack.getItem() == item));
		}

		public LimitedItemInfoSlot setConditions(Predicate<ItemStack> conditions)
		{
			this.conditions = conditions;
			return this;
		}

		public LimitedItemInfoSlot setCanTake(boolean enabled)
		{
			return setCanTake((stack) -> enabled);
		}

		public LimitedItemInfoSlot setCanTake(Predicate<Player> conditions)
		{
			this.canTake = conditions;
			return this;
		}
	}
}