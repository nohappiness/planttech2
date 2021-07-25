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

    @Override
    public CompoundTag serializeNBT()
    {
        return (CompoundTag) BIOMASS_FLUID_ENERGY.getStorage().writeNBT(BIOMASS_FLUID_ENERGY, lazyOptional.orElseThrow(() -> new NullPointerException("An error has occur during writing Biomass Capability by Plant Tech 2")), null);
    }

    public static IBiomassFluidEnergy getTECap(BlockEntity te)
    {
        return te.getCapability(BIOMASS_FLUID_ENERGY).orElseThrow(() -> new NullPointerException("getting capability for tileentity"));
    }


    public static IBiomassFluidEnergy getItemStackCap(ItemStack stack)
    {
        return stack.getCapability(BIOMASS_FLUID_ENERGY).orElseThrow(() -> new NullPointerException("getting capability for itemstack"));
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        BIOMASS_FLUID_ENERGY.getStorage().readNBT(BIOMASS_FLUID_ENERGY, lazyOptional.orElseThrow(() -> new NullPointerException("An error has occur during reading Biomass Capability by Plant Tech 2")), null, nbt);
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

    public static class BiomassFluidEnergyStorage implements Capability.IStorage<IBiomassFluidEnergy>
    {
        public BiomassFluidEnergyStorage(){}

        @Nullable
        @Override
        public Tag writeNBT(Capability<IBiomassFluidEnergy> capability, IBiomassFluidEnergy instance, Direction side)
        {
            CompoundTag compound = new CompoundTag();
            compound.putInt("maxstorage", instance.getMaxStorage());
            compound.putInt("currentstorage", instance.getCurrentStorage());
            return compound;
        }

        @Override
        public void readNBT(Capability<IBiomassFluidEnergy> capability, IBiomassFluidEnergy instance, Direction side, Tag tag)
        {
            CompoundTag compound = (CompoundTag) tag;
            instance.setMaxStorage(compound.getInt("maxstorage"));
            instance.setCurrentStorage(compound.getInt("currentstorage"));
        }
    }

}
