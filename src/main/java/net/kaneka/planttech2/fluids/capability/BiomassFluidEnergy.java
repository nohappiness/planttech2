package net.kaneka.planttech2.fluids.capability;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.entities.capabilities.player.IPlayerRenderRGB;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.packets.SyncBiomassFluidEnergyMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BiomassFluidEnergy implements ICapabilitySerializable<CompoundNBT>, IBiomassFluidEnergy
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
    public CompoundNBT serializeNBT()
    {
        return (CompoundNBT) BIOMASS_FLUID_ENERGY.getStorage().writeNBT(BIOMASS_FLUID_ENERGY, lazyOptional.orElseThrow(() -> new NullPointerException("An error has occur during writing Biomass Capability by Plant Tech 2")), null);
    }

    public static IBiomassFluidEnergy getTECap(TileEntity te)
    {
        return te.getCapability(BIOMASS_FLUID_ENERGY).orElseThrow(() -> new NullPointerException("getting capability for tileentity"));
    }


    public static IBiomassFluidEnergy getItemStackCap(ItemStack stack)
    {
        IBiomassFluidEnergy cap = stack.getCapability(BIOMASS_FLUID_ENERGY).orElseThrow(() -> new NullPointerException("getting capability for itemstack"));
        return cap;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
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
        {
            this.currentStorage = 0;
        }
        else if (this.currentStorage + amount > this.maxStorage)
        {
            this.currentStorage = this.maxStorage;
        }
        else
        {
            this.currentStorage += amount;
        }
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
        public INBT writeNBT(Capability<IBiomassFluidEnergy> capability, IBiomassFluidEnergy instance, Direction side)
        {
            CompoundNBT compound = new CompoundNBT();
            compound.putInt("maxstorage", instance.getMaxStorage());
            compound.putInt("currentstorage", instance.getCurrentStorage());
            return compound;
        }

        @Override
        public void readNBT(Capability<IBiomassFluidEnergy> capability, IBiomassFluidEnergy instance, Direction side, INBT nbt)
        {
            CompoundNBT compound = (CompoundNBT) nbt;
            instance.setMaxStorage(compound.getInt("maxstorage"));
            instance.setCurrentStorage(compound.getInt("currentstorage"));
        }
    }

}
