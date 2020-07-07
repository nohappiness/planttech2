package net.kaneka.planttech2.tileentity.machine.baseclasses;

import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

abstract public class EnergyTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider
{

	protected BioEnergyStorage energystorage;
	private LazyOptional<IEnergyStorage> energyCap;
	public String customname;

	public EnergyTileEntity(TileEntityType<?> type, int energyStorage)
	{
		super(type);
		energystorage = new BioEnergyStorage(energyStorage);
		energyCap = LazyOptional.of(() -> energystorage);
	}

	@Override
	public void tick()
	{
		if (this.world != null && !this.world.isRemote)
		{
			doUpdate();
		}
	}

	public void doUpdate()
	{

	}
	
	

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
		{
			return energyCap.cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{

		compound.put("energy", this.energystorage.serializeNBT());
		super.write(compound);
		return compound;
	}

	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		super.read(state, compound);
		this.energystorage.deserializeNBT(compound.getCompound("energy"));
	}

	public String getNameString()
	{
		return "default";
	}

	public int getEnergyStored()
	{
		return this.energystorage.getEnergyStored();
	}

	public int getMaxEnergyStored()
	{
		return this.energystorage.getMaxEnergyStored();
	}

	public boolean isUsableByPlayer(PlayerEntity player)
	{
		return this.world.getTileEntity(this.pos) != this ? false
		        : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public void onSlotContentChanged()
	{

	}
	
	public abstract IIntArray getIntArray(); 
	
	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("container." + getNameString());
	}
	 
}
