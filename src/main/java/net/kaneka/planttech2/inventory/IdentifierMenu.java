package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.IdentifierBlockEntity;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class IdentifierMenu extends BaseMenu
{
	public IdentifierMenu(int id, Inventory inv)
	{
		this(id, inv, new IdentifierBlockEntity());
	}
	
	public IdentifierMenu(int id, Inventory player, IdentifierBlockEntity tileentity)
	{
		super(id, ModContainers.IDENTIFIER, player, tileentity, 21);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		for(int x = 0; x < 3; x++)
			for(int y = 0; y < 3; y++)
				this.addSlot(new LimitedItemInfoSlot(handler, x + y * 3, 19 + x * 18, 27 + y * 18, "slot.identifier.input").setConditions((stack) -> stack.getItem() instanceof CropSeedItem));
		for(int x = 0; x < 3; x++)
			for(int y = 0; y < 3; y++)
				this.addSlot(createOutoutSlot(handler, x + y * 3 + 9, 95 + x * 18, 27 + y * 18));
		this.addSlot(createSpeedUpgradeSlot(handler, 18, 75, 85));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
		
	}
}
