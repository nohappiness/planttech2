package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.SeedConstructorBlockEntity;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class SeedConstructorMenu extends BaseMenu
{
	public SeedConstructorMenu(int id, Inventory inv)
	{
		this(id, inv, new SeedConstructorBlockEntity());
	}
	
	public SeedConstructorMenu(int id, Inventory player, SeedConstructorBlockEntity tileentity)
	{
		super(id, ModContainers.SEEDCONSTRUCTOR, player, tileentity, 7);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		this.addSlot(new SlotItemHandlerWithInfo(handler, 0, 95, 29, "slot.seedconstructor.container"));
		this.addSlot(createOutoutSlot(handler, 1, 95, 66));
		this.addSlot(createSpeedUpgradeSlot(handler, 2, 71, 43));
		this.addSlot(createFluidInSlot(handler, 23, 38));
		this.addSlot(createFluidOutSlot(handler, 23, 57));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
		
	}
}
