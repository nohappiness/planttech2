package net.kaneka.planttech2.items.upgradeable;

import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryEnergyProvider implements ICapabilitySerializable<CompoundNBT>
{
	
	protected BioEnergyStorage energystorage;
	private LazyOptional<IEnergyStorage> energyCap;
	protected ItemStackHandler itemhandler;
    private LazyOptional<IItemHandler> inventoryCap;
	
	public InventoryEnergyProvider(int storage, int invSize)
	{
		energystorage = new BioEnergyStorage(storage);
		energystorage.setEnergyStored(storage);
		energyCap = LazyOptional.of(() -> energystorage);
		itemhandler = new ItemStackHandler(invSize);
		inventoryCap = LazyOptional.of(() -> itemhandler);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		if (cap == CapabilityEnergy.ENERGY)
		{
		    return energyCap.cast();
		}
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
		    return inventoryCap.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		CompoundNBT compound = new CompoundNBT();
		compound.put("inventory", itemhandler.serializeNBT());
		compound.put("energy", energystorage.serializeNBT());
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		itemhandler.deserializeNBT(nbt.getCompound("inventory"));
		energystorage.deserializeNBT(nbt.getCompound("energy"));
	}

}
