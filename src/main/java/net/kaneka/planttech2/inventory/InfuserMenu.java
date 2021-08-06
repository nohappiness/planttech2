package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.InfuserBlockEntity;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class InfuserMenu extends BaseMenu
{
	public InfuserMenu(int id, Inventory inv)
	{
		this(id, inv, new InfuserBlockEntity());
	}
	
	public InfuserMenu(int id, Inventory player, InfuserBlockEntity tileentity)
	{
		super(id, ModContainers.INFUSER, player, tileentity, 7);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		this.addSlot(new SlotItemHandlerWithInfo(handler, 0, 68, 47, "slot.infuser.input")); 
		this.addSlot(createOutoutSlot(handler, 1, 122, 47));
		this.addSlot(createSpeedUpgradeSlot(handler, 2, 95, 69));

		this.addSlot(createFluidInSlot(handler, 23, 38));
		this.addSlot(createFluidOutSlot(handler, 23, 57));
		
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
		
	}
}
