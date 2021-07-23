package net.kaneka.planttech2.fluids;

import net.minecraft.nbt.CompoundTag;

public class TempFluidTank
{
    private int capacity; 
    private int biomass; 
    
    public TempFluidTank(int capacity, int biomass)
    {
	this.capacity = capacity; 
	this.biomass = biomass; 
    }
    
    public TempFluidTank(int capacity)
    {
	this.capacity = capacity; 
	this.biomass = 0; 
    }
    
    public int receive(int maxReceive)
    {
	int space = capacity -  biomass; 
	
	if(space >= maxReceive)
	{
	    biomass += maxReceive; 
	    return maxReceive; 
	}
	else
	{
	    biomass = capacity; 
	    return space; 
	}
    }

    public int extract(int maxExtract)
    {
	if(biomass >= maxExtract)
	{
	    biomass -= maxExtract; 
	    return maxExtract; 
	}
	else
	{
	    biomass = 0; 
	    return biomass; 
	}
    }
    
    
    public void setCapacity(int value)
    {
	capacity = value; 
    }
    
    public int getCapacity()
    {
	return capacity; 
    }
    
    public void setBiomass(int value)
    {
	biomass = value; 
    }
    
    public int getBiomass()
    {
	return biomass; 
    }
    
    public void deserializeNBT(CompoundTag nbt)
    {
	capacity = nbt.getInt("tankcapacity");
	biomass = nbt.getInt("biomass"); 
    }

    public CompoundTag serializeNBT()
    {
    	CompoundTag nbtList = new CompoundTag();
	nbtList.putInt("tankcapacity", capacity);
	nbtList.putInt("biomass", biomass);
	return nbtList;
    }
}
