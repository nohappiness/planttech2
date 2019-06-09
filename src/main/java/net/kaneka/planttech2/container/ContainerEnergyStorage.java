package net.kaneka.planttech2.container;

import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.tileentity.machine.TileEntityEnergyStorage;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergy;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerEnergyStorage extends ContainerBase
{
	public ContainerEnergyStorage(int id, PlayerInventory inv)
	{
		this(id, inv, new TileEntityEnergyStorage()); 
	}

    public ContainerEnergyStorage(int id, PlayerInventory player, TileEntityEnergy tileentity)
    {
	super(id, ModContainers.ENERGYSTORAGE,player, tileentity, 1);
	IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
	this.addSlot(new ChangeCheckSlot(tileentity, handler, 0, 135, 85));
    }

}


