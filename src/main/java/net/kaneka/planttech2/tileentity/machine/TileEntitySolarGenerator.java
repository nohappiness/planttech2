package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.ContainerSolarGenerator;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergyInventory;
import net.kaneka.planttech2.utilities.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySolarGenerator extends TileEntityEnergyInventory
{
    int workload = 0;

    public TileEntitySolarGenerator()
    {
	super(ModTileEntities.SOLARGENERATOR_TE, 100000, 2);
    }

    @Override
    public void doUpdate()
    {
	if (world.isDaytime() && world.canSeeSky(pos.up()))
	{
	    if (energystorage.getMaxEnergyStored() - energystorage.getEnergyStored() > 0)
	    {
		workload++;
		if (workload >= getTicksPerAmount())
		{
		    energystorage.receiveEnergy(getEnergyPerTick(getUpgradeTier(0, Constants.SOLARFOCUS_TYPE)));
		    workload = 0;
		}
	    }
	}
    }

    private int getEnergyPerTick(int focusLevel)
    {
	switch (focusLevel)
	{
	case 1:
	    return 20;
	case 2:
	    return 60;
	case 3:
	    return 180;
	case 4:
	    return 540;
	}

	return 0;
    }

    public int getTicksPerAmount()
    {
	return 80 - (getUpgradeTier(1, Constants.SPEEDUPGRADE_TYPE) * 15);
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound)
    {
	compound.setInt("workload", workload);
	super.write(compound);
	return compound;
    }

    @Override
    public void read(NBTTagCompound compound)
    {
	this.workload = compound.getInt("workload");
	super.read(compound);
    }

    @Override
    public String getNameString()
    {
	return "solargenerator";
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
	    return this.workload;
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
	    this.workload = value;
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
	return new ContainerSolarGenerator(playerInventory, this);
    }
}
