package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.blocks.machines.BlockEnergyStorage;
import net.kaneka.planttech2.container.ContainerEnergyStorage;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergyInventory;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;

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
	if (world != null)
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
		    BlockState state = world.getBlockState(pos);
		    if (state != null)
		    {
			if (state.getBlock() == ModBlocks.ENERGYSTORAGE)
			{
			    world.setBlockState(pos, state.with(BlockEnergyStorage.TIER, newTier), 2);
			    markDirty();
			}
		    }
		    currentTier = newTier;
		}
	    }
	}
    }

    @Override
    public void read(CompoundNBT compound)
    {
	super.read(compound);
    }

    @Override
    public String getNameString()
    {
	return "energystorage";
    }
}
