package net.kaneka.planttech2.energy;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.energy.EnergyStorage;

public class BioEnergyStorage extends EnergyStorage
{
    public BioEnergyStorage(int capacity)
    {
	super(capacity, capacity, capacity, 0);
    }

    public BioEnergyStorage(int capacity, int maxTransfer)
    {
	super(capacity, maxTransfer, maxTransfer, 0);
    }

    public BioEnergyStorage(int capacity, int maxReceive, int maxExtract)
    {
	super(capacity, maxReceive, maxExtract, 0);
    }

    public int receiveEnergy(int maxReceive)
    {
	return super.receiveEnergy(maxReceive, false);
    }

    public int extractEnergy(int maxExtract)
    {
	return super.extractEnergy(maxExtract, false);
    }

    public void deserializeNBT(NBTTagCompound nbt)
    {
	energy = nbt.getInt("energy");
	capacity = nbt.getInt("capacity");
	maxReceive = nbt.getInt("maxreceive");
	maxExtract = nbt.getInt("maxextract");
    }

    public NBTTagCompound serializeNBT()
    {
	NBTTagCompound nbtList = new NBTTagCompound();
	nbtList.setInt("energy", energy);
	nbtList.setInt("capacity", capacity);
	nbtList.setInt("maxreceive", maxReceive);
	nbtList.setInt("maxextract", maxExtract);
	return nbtList;
    }

    // Sync client/server
    public void setEnergyStored(int amount)
    {
	this.energy = amount;
    }

    public void setEnergyMaxStored(int amount)
    {
	this.capacity = amount;
    }
}
