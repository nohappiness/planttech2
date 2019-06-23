package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.SolarGeneratorContainer;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;

public class SolarGeneratorTileEntity extends EnergyInventoryTileEntity
{
    int workload = 0;

    public SolarGeneratorTileEntity()
    {
	super(ModTileEntities.SOLARGENERATOR_TE, 100000, 2);
    }

    @Override
    public void doUpdate()
    {
	if (world.isDaytime() && world.canBlockSeeSky(pos.up()))
	{
	    if (energystorage.getMaxEnergyStored() - energystorage.getEnergyStored() > 0)
	    {
		workload++;
		if (workload >= getTicksPerAmount())
		{
		    energystorage.receiveEnergy(getEnergyPerTick(getUpgradeTier(0, PlantTechConstants.SOLARFOCUS_TYPE)));
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
	return 80 - (getUpgradeTier(1, PlantTechConstants.SPEEDUPGRADE_TYPE) * 15);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
	compound.putInt("workload", workload);
	super.write(compound);
	return compound;
    }

    @Override
    public void read(CompoundNBT compound)
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
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new SolarGeneratorContainer(id, inv, this);
	}
}
