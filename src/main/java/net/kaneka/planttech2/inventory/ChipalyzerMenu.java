package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.ChipalyzerBlockEntity;
import net.kaneka.planttech2.blocks.entity.machine.CompressorBlockEntity;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ChipalyzerMenu extends BaseMenu
{
	public ChipalyzerMenu(int id, Inventory inv)
	{
		this(id, inv, new ChipalyzerBlockEntity());
	}
	
	public ChipalyzerMenu(int id, Inventory player, ChipalyzerBlockEntity tileentity)
	{
		super(id, ModContainers.CHIPALYZER, player, tileentity, 5);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		this.addSlot(new SlotItemHandlerWithInfo(handler, 0, 41, 47, "slot.chipalyzer.chipinput").setShouldListen());
		this.addSlot(new SlotItemHandlerWithInfo(handler, 1, 68, 27, "slot.chipalyzer.iteminput").setShouldListen());
		this.addSlot(createOutoutSlot(handler, tileentity.getOutputSlotIndex(), 95, 47));
		this.addSlot(createSpeedUpgradeSlot(handler, 3, 68, 69));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
	}

    public ChipalyzerMenu(int i, Inventory inventory, BlockPos blockPos)
    {
		this(i, inventory, (ChipalyzerBlockEntity) inventory.player.level.getBlockEntity(blockPos));
	}
}
