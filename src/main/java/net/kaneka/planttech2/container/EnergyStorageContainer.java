package net.kaneka.planttech2.container;

import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.tileentity.machine.EnergyStorageTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class EnergyStorageContainer extends BaseContainer
{
	public EnergyStorageContainer(int id, PlayerInventory inv)
	{
		this(id, inv, new EnergyStorageTileEntity()); 
	}

    public EnergyStorageContainer(int id, PlayerInventory player, EnergyStorageTileEntity tileentity)
    {
	super(id, ModContainers.ENERGYSTORAGE,player, tileentity, 3);
	IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
	this.addSlot(new ChangeCheckSlot(tileentity, handler, 0, 132, 64));
	this.addSlot(new SlotItemHandler(handler, tileentity.getEnergyInSlot(), 150, 86));
	this.addSlot(new SlotItemHandler(handler, tileentity.getEnergyOutSlot(), 168, 86));
    }

}


