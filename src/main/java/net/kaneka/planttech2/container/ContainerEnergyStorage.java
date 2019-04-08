package net.kaneka.planttech2.container;

import net.kaneka.planttech2.tileentity.machine.TileEntityEnergy;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerEnergyStorage extends ContainerBase
{

    public ContainerEnergyStorage(InventoryPlayer player, TileEntityEnergy tileentity)
    {
	super(player, tileentity, 1);
	IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
	this.addSlot(new ChangeCheckSlot(tileentity, handler, 0, 135, 85));
    }

}


