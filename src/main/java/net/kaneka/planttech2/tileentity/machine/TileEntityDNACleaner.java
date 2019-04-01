package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.ContainerDNACleaner;
import net.kaneka.planttech2.items.ModItems;
import net.kaneka.planttech2.tileentity.ModTileEntities;
import net.kaneka.planttech2.utilities.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityDNACleaner extends TileEntityEnergyInventory
{
    private int ticksPassed = 0;

    public TileEntityDNACleaner()
    {
	super(ModTileEntities.DNACLEANER_TE,1000, 3);
    }

    @Override
    public void doUpdate()
    {
	if (this.energystorage.getEnergyStored() > energyPerTick() || true)
	{
	    ItemStack stack1 = itemhandler.getStackInSlot(0);
	    ItemStack stack2 = itemhandler.getStackInSlot(1);
	    if (!stack1.isEmpty())
	    {
		if (stack1.getItem() == ModItems.DNA_CONTAINER)
		{
		    if (ticksPassed < ticksPerItem())
		    {
			ticksPassed++;
			//energystorage.extractEnergy(energyPerTick(), false);
		    }
		    else
		    {
			if (stack2.isEmpty())
			{
			    itemhandler.setStackInSlot(1, new ItemStack(ModItems.DNA_CONTAINER_EMPTY));
			    //energystorage.extractEnergy(energyPerTick(), false);
			    stack1.shrink(1);
			    ticksPassed = 0;
			}
			else if (stack2.getItem() == ModItems.DNA_CONTAINER_EMPTY)
			{
			    stack2.grow(1);
			    //energystorage.extractEnergy(energyPerTick(), false);
			    stack1.shrink(1);
			    ticksPassed = 0;
			}
		    }
		}
	    }
	}
    }

    public int energyPerTick()
    {
	return 4 + (getUpgradeTier(2, Constants.SPEEDUPGRADE_TYPE) * 4);
    }

    public int ticksPerItem()
    {
	return 200 - (getUpgradeTier(2, Constants.SPEEDUPGRADE_TYPE) * 35);
    }

    @Override
    public String getNameString()
    {
	return "dna_cleaner";
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound)
    {
	compound.setInt("tickspassed", ticksPassed);
	super.write(compound);
	return compound;
    }

    @Override
    public void read(NBTTagCompound compound)
    {
	this.ticksPassed = compound.getInt("tickspassed");
	super.read(compound);
    }

    @Override
    public int getField(int id)
    {
	switch (id)
	{
	case 0:
	case 1:
	    return super.getField(id);
	case 2:
	    return this.ticksPassed;
	default:
	    return 0;
	}
    }

    @Override
    public void setField(int id, int value)
    {
	switch (id)
	{
	case 0:
	case 1:
	    super.setField(id, value);
	    break;
	case 2:
	    this.ticksPassed = value;
	    break;
	}
    }

    @Override
    public int getAmountFields()
    {
	return 3;
    }
    
    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
	return new ContainerDNACleaner(playerInventory, this);
    }

}
