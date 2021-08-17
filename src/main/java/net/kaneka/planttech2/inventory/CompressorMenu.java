package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.CompressorBlockEntity;
import net.kaneka.planttech2.items.ParticleItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class CompressorMenu extends BaseMenu
{
	public CompressorMenu(int id, Inventory inv)
	{
		this(id, inv, new CompressorBlockEntity());
	}

	public CompressorMenu(int id, Inventory player, CompressorBlockEntity tileentity)
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
}