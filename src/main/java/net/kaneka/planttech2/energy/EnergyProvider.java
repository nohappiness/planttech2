package net.kaneka.planttech2.energy;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyProvider implements ICapabilitySerializable<CompoundTag>
{
	
	protected BioEnergyStorage energystorage;
	private LazyOptional<IEnergyStorage> energyCap;
	
	public EnergyProvider(int storage)
	{
		energystorage = new BioEnergyStorage(storage);
		energyCap = LazyOptional.of(() -> energystorage);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		if (cap == CapabilityEnergy.ENERGY)
		{
		    return energyCap.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT()
	{
		CompoundTag compound = new CompoundTag();
		compound.put("energy", energystorage.serializeNBT());
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		energystorage.deserializeNBT(nbt.getCompound("energy"));
	}

}
