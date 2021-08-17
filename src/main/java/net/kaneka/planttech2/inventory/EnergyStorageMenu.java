package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.EnergyStorageBlockEntity;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EnergyStorageMenu extends BaseMenu
{
	public EnergyStorageMenu(int id, Inventory inv)
	{
		this(id, inv, new EnergyStorageBlockEntity());
	}

    public EnergyStorageMenu(int id, Inventory player, EnergyStorageBlockEntity tileentity)
    {
		super(id, ModContainers.ENERGYSTORAGE,player, tileentity, 3);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		this.addSlot(createCapacityChipSlot(handler, 0, 132, 64));
		this.addSlot(createEnergyInSlot(handler, 150, 86));
		this.addSlot(createEnergyOutSlot(handler, 168, 86));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
    }
}


