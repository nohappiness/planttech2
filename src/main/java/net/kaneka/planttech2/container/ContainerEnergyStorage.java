package net.kaneka.planttech2.container;

import net.kaneka.planttech2.tileentity.machine.TileEntityEnergy;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerEnergyStorage extends ContainerBase
{

    public ContainerEnergyStorage(InventoryPlayer player, TileEntityEnergy tileentity)
    {
	super(player, tileentity, 1);
    }

}
