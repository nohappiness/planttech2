package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.DNAExtractorBlockEntity;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class DNAExtractorMenu extends BaseMenu
{
	public DNAExtractorMenu(int id, Inventory inv)
	{
		this(id, inv, new DNAExtractorBlockEntity());
	}
	
	public DNAExtractorMenu(int id, Inventory player, DNAExtractorBlockEntity tileentity)
	{
		super(id, ModContainers.DNAEXTRACTOR, player, tileentity, 6);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		this.addSlot(new SlotItemHandlerWithInfo(handler, 0, 58, 40, "slot.dnaextractor.seeds"));
		this.addSlot(createDNAContainerSlot(handler, 1, 130, 40, "slot.dnaextractor.empty_container", true));
		this.addSlot(createOutoutSlot(handler, 2, 130, 67));
		this.addSlot(createSpeedUpgradeSlot(handler, 3, 94, 66));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
		
	}

    public DNAExtractorMenu(int i, Inventory inventory, BlockPos blockPos)
    {
		this(i, inventory, (DNAExtractorBlockEntity) inventory.player.level.getBlockEntity(blockPos));
	}
}
