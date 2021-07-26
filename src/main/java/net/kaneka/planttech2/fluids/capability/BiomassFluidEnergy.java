package net.kaneka.planttech2.fluids.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BiomassFluidEnergy implements ICapabilitySerializable<CompoundTag>, IBiomassFluidEnergy
{
    @CapabilityInject(IBiomassFluidEnergy.class)
    public static Capability<IBiomassFluidEnergy> BIOMASS_FLUID_ENERGY = null;

    private final LazyOptional<IBiomassFluidEnergy> lazyOptional = LazyOptional.of(BiomassFluidEnergy::new);
    private int currentStorage = 0;
    private int maxStorage = 1000;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        return cap == BIOMASS_FLUID_ENERGY ? lazyOptional.cast() : LazyOptional.empty();
    }

    public static IBiomassFluidEnergy getTECap(BlockEntity te)
    {
        return te.getCapability(BIOMASS_FLUID_ENERGY).orElseThrow(() -> new NullPointerException("getting capability for tileentity"));
    }


    public static IBiomassFluidEnergy getItemStackCap(ItemStack stack)
    {
        return stack.getCapability(BIOMASS_FLUID_ENERGY).orElseThrow(() -> new NullPointerException("getting capability for itemstack"));
    }

    private IBiomassFluidEnergy getCap()
    {
        return getCapability(BIOMASS_FLUID_ENERGY).orElseThrow(NullPointerException::new);
    }

    @Override
    public CompoundTag serializeNBT()
    {
        return write(getCap());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        read(getCap(), nbt);
    }

    public static CompoundTag write(IBiomassFluidEnergy cap)
    {
        CompoundTag compound = new CompoundTag();
        compound.putInt("max", cap.getMaxStorage());
        compound.putInt("current", cap.getCurrentStorage());
        return compound;
    }

    public static void read(IBiomassFluidEnergy cap, CompoundTag nbt)
    {
        cap.setMaxStorage(nbt.getInt("max"));
        cap.setCurrentStorage(nbt.getInt("current"));
    }

    @Override
    public int getCurrentStorage()
    {
        return this.currentStorage;
    }

    @Override
    public int getMaxStorage()
    {
        return this.maxStorage;
    }

    @Override
    public int extractBiomass(int amount)
    {
        amount = Math.abs(amount);
        if (getCurrentStorage() < amount)
        {
            this.changeCurrentStorage(-getCurrentStorage());
            return getCurrentStorage();
        }
        this.changeCurrentStorage(-amount);
        return amount;
    }

    @Override
    public void setCurrentStorage(int value)
    {
        this.currentStorage = (value < 0) ? 0 : Math.min(value, this.maxStorage);
    }

    @Override
    public void setMaxStorage(int value)
    {
        this.maxStorage = value;
    }

    @Override
    public void changeCurrentStorage(int amount)
    {
        if (this.currentStorage + amount < 0)
            this.currentStorage = 0;
        else if (this.currentStorage + amount > this.maxStorage)
            this.currentStorage = this.maxStorage;
        else
            this.currentStorage += amount;
    }

    @Override
    public int recieveBiomass(int amount)
    {
        amount = Math.abs(amount);
        this.changeCurrentStorage(Math.abs(amount));
        return -amount;
    }

    @Override
    public void clearStorage()
    {
        this.currentStorage = 0;
    }

    @Override
    public boolean hasEnoughBiomass(int target)
    {
        return this.currentStorage >= target;
    }
}
