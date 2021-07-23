package net.kaneka.planttech2.energy;

import net.minecraft.nbt.CompoundTag;
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

    public void deserializeNBT(CompoundTag nbt)
    {
        energy = nbt.getInt("energy");
        capacity = nbt.getInt("capacity");
        maxReceive = nbt.getInt("maxreceive");
        maxExtract = nbt.getInt("maxextract");
    }

    public CompoundTag serializeNBT()
    {
    	CompoundTag nbtList = new CompoundTag();
        nbtList.putInt("energy", energy);
        nbtList.putInt("capacity", capacity);
        nbtList.putInt("maxreceive", maxReceive);
        nbtList.putInt("maxextract", maxExtract);
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
