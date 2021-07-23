package net.kaneka.planttech2.tileentity.machine.baseclasses;

import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.core.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Random;

abstract public class EnergyTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider
{

	protected BioEnergyStorage energystorage;
	private final LazyOptional<IEnergyStorage> energyCap;
	public String customname;
	protected final Random rand = new Random();

	public EnergyTileEntity(TileEntityType<?> type, int energyStorage)
	{
		super(type);
		energystorage = new BioEnergyStorage(energyStorage);
		energyCap = LazyOptional.of(() -> energystorage);
	}

	@Override
	public void tick()
	{
		if (this.level != null && !this.level.isClientSide)
			doUpdate();
	}

	public void doUpdate()
	{
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing)
	{
		return capability == CapabilityEnergy.ENERGY ? energyCap.cast() : super.getCapability(capability, facing);
	}

	@Override
	public CompoundNBT save(CompoundNBT compound)
	{
		compound.put("energy", this.energystorage.serializeNBT());
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound)
	{
		super.load(state, compound);
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
		if (level == null || this.level.getBlockEntity(worldPosition) != this)
			return false;
		return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
	}


	public void onSlotContentChanged()
	{

	}

	public int getUpgradeSlot()
	{
		return -1;
	}

	public abstract IIntArray getIntArray();
	
	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("container." + getNameString());
	}

	public void notifyClient()
	{
		if (level != null && !level.isClientSide())
		{
			BlockState state = level.getBlockState(getBlockPos());
			level.sendBlockUpdated(getBlockPos(), state, state, 3);
		}
	}

	public boolean requireSyncOnOpen()
	{
		return false;
	}
}
