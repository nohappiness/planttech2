package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.MachineBulbReprocessorBlockEntity;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class MachineBulbReprocessorMenu extends BaseMenu
{
	public MachineBulbReprocessorMenu(int id, Inventory inv)
	{
		this(id, inv, new MachineBulbReprocessorBlockEntity());
	}
	
	public MachineBulbReprocessorMenu(int id, Inventory player, MachineBulbReprocessorBlockEntity tileentity)
	{
		super(id, ModContainers.MACHINEBULBREPROCESSOR, player, tileentity, 7);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		this.addSlot(new SlotItemHandlerWithInfo(handler, 0, 68, 85, "slot.machinebulbreprocessor.input").setShouldListen());
		this.addSlot(createOutoutSlot(handler, 1, 122, 85));

		this.addSlot(new SlotItemHandlerWithInfo(handler, 2, 23, 38, "slot.util.fluidin"));
		this.addSlot(new SlotItemHandlerWithInfo(handler, 3, 23, 57, "slot.util.fluidout"));
		
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
	}
}
