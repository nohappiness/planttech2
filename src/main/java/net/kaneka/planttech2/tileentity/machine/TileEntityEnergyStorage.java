package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.ContainerCompressor;
import net.kaneka.planttech2.container.ContainerEnergyStorage;
import net.kaneka.planttech2.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityEnergyStorage extends TileEntityEnergyInventory
{

    public TileEntityEnergyStorage()
    {
	super(ModTileEntities.ENERGYSTORAGE_TE, 10000, 1);
    }

    @Override
    public void doUpdate()
    {
	if ((world.getGameTime() % 40) == 0)
	{
	   
	}
    }
    @Override
    public String getNameString()
    {
	return "energystorage";
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
	return new ContainerEnergyStorage(playerInventory, this);
    }

}
