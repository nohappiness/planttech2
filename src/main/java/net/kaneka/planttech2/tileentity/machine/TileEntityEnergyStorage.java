package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.blocks.machines.BlockEnergyStorage;
import net.kaneka.planttech2.container.ContainerCompressor;
import net.kaneka.planttech2.container.ContainerEnergyStorage;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityEnergyStorage extends TileEntityEnergyInventory
{

    private int currentTier = -1; // Always forcing a update when loaded

    public TileEntityEnergyStorage()
    {
	super(ModTileEntities.ENERGYSTORAGE_TE, 10, 1);
    }

    @Override
    public void onSlotContentChanged()
    {
	if (!world.isRemote)
	{
	    int newTier = getUpgradeTier(0, 3);
	    if (currentTier != newTier)
	    {
		switch (newTier)
		{
		case 0:
		    energystorage.setEnergyMaxStored(1000);
		    break;
		case 1:
		    energystorage.setEnergyMaxStored(10000);
		    break;
		case 2:
		    energystorage.setEnergyMaxStored(100000);
		    break;
		case 3:
		    energystorage.setEnergyMaxStored(1000000);
		    break;
		}
		IBlockState state = world.getBlockState(pos);
		if(state != null)
		{
		    if(state.getBlock() == ModBlocks.ENERGYSTORAGE)
		    {
			world.setBlockState(pos, state.with(BlockEnergyStorage.TIER, newTier),2);
		    }
		}
		currentTier = newTier;
	    }
	}
    }

    @Override
    public void read(NBTTagCompound compound)
    {
	super.read(compound);
	onSlotContentChanged();
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
