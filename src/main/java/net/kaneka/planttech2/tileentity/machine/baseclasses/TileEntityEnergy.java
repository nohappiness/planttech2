package net.kaneka.planttech2.tileentity.machine.baseclasses;

import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

abstract public class TileEntityEnergy extends TileEntity implements ITickable, IInteractionObject
{

	protected BioEnergyStorage energystorage;
	private LazyOptional<IEnergyStorage> energyCap;
	public String customname;

	public TileEntityEnergy(TileEntityType<?> type, int energyStorage)
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
	public <T> LazyOptional<T> getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
		{
			return energyCap.cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public NBTTagCompound write(NBTTagCompound compound)
	{

		compound.setTag("energy", this.energystorage.serializeNBT());
		super.write(compound);
		return compound;
	}

	@Override
	public void read(NBTTagCompound compound)
	{
		super.read(compound);
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

	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false
		        : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public void onSlotContentChanged()
	{

	}

	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return this.energystorage.getEnergyStored();
		case 1:
			return this.energystorage.getMaxEnergyStored();
		default:
			return 0;
		}
	}

	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
			this.energystorage.setEnergyStored(value);
			break;
		case 1:
			this.energystorage.setEnergyMaxStored(value);
			break;
		}
	}

	public int getAmountFields()
	{
		return 2;
	}

	@Override
	public ITextComponent getName()
	{
		return new TextComponentTranslation("container." + getNameString());
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getCustomName()
	{
		return null;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return null;
	}

	@Override
	public String getGuiID()
	{
		return getType().getRegistryName().toString();
	}
}
