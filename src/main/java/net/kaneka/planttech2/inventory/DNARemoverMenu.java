package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.DNARemoverBlockEntity;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class DNARemoverMenu extends BaseMenu
{
	public DNARemoverMenu(int id, Inventory inv)
	{
		this(id, inv, new DNARemoverBlockEntity());
	}
	
	public DNARemoverMenu(int id, Inventory player, DNARemoverBlockEntity tileentity)
	{
		super(id, ModContainers.DNAREMOVER,player, tileentity, 5);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		this.addSlot(createDNAContainerSlot(handler, 0, 41, 47, "slot.dnaremover.container", false));
		this.addSlot(createOutoutSlot(handler, 1, 95, 47));
		this.addSlot(createSpeedUpgradeSlot(handler, 2, 68, 69));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
	}
}
