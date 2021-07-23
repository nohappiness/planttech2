package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.CompressorBlockEntity;
import net.kaneka.planttech2.items.ParticleItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class CompressorContainer extends BaseContainer
{
	public CompressorContainer(int id, Inventory inv)
	{
		this(id, inv, new CompressorBlockEntity());
	}

	public CompressorContainer(int id, Inventory player, CompressorBlockEntity tileentity)
	{
		super(id, ModContainers.COMPRESSOR, player, tileentity, 25);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);

		this.addSlot(new LimitedItemInfoSlot(handler, 0, 34, 83, "slot.compressor.input").setConditions((stack) -> stack.getItem() instanceof ParticleItem).setShouldListen());
		this.addSlot(createOutoutSlot(handler, tileentity.getOutputSlotIndex(), 126, 83));
		this.addSlot(createSpeedUpgradeSlot(handler, 2, 78, 87));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 6; x++)
				addSlot(createFakeSlot(handler, x + y * 6 + 3, 35 + x * 18, 26 + y * 18, "slot.compressor.select"));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index)
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.slots.get(index);
		if (slot != null && slot.hasItem())
		{
			ItemStack stack1 = slot.getItem();
			stack = stack1.copy();

			if (index > 35)
			{
				if (!this.moveItemStackTo(stack1, 0, 34, true))
				{
					return ItemStack.EMPTY;
				}
			} else if (index < 36)
			{
				if (!this.moveItemStackTo(stack1, 36, 37, false))
				{
					return ItemStack.EMPTY;
				} else if (index >= 0 && index < 27)
				{
					if (!this.moveItemStackTo(stack1, 27, 35, false))
						return ItemStack.EMPTY;
				} else if (index >= 27 && index < 36 && !this.moveItemStackTo(stack1, 0, 26, false))
				{
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(stack1, 0, 35, false))
			{
				return ItemStack.EMPTY;
			}

			if (stack1.isEmpty())
			{
				slot.set(ItemStack.EMPTY);
			} else
			{
				slot.setChanged();

			}
			if (stack1.getCount() == stack.getCount())
				return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		return stack;
	}
}