package net.kaneka.planttech2.entities.capabilities.player;

import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.packets.SyncRadiationLevelMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RadiationEffect implements ICapabilitySerializable<CompoundNBT>, IRadiationEffect
{
    @CapabilityInject(IRadiationEffect.class)
    public static Capability<IRadiationEffect> RADIATION_CAPABILITY = null;

    private final LazyOptional<IRadiationEffect> lazyOptional = LazyOptional.of(RadiationEffect::new);
    private float level;

    public RadiationEffect() {}

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        return cap == RADIATION_CAPABILITY ? lazyOptional.cast() : LazyOptional.empty();
    }

    public static IRadiationEffect getCap(ServerPlayerEntity player)
    {
        IRadiationEffect cap = player.getCapability(RADIATION_CAPABILITY).orElseThrow(() -> new NullPointerException("getting capability"));
        PlantTech2PacketHandler.sendTo(new SyncRadiationLevelMessage(cap.getLevel()), player);
        return cap;
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        return (CompoundNBT) RADIATION_CAPABILITY.getStorage().writeNBT(RADIATION_CAPABILITY, lazyOptional.orElseThrow(() -> new NullPointerException("An error has occur during writing Radiation Effect Capability by Plant Tech 2")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
       RADIATION_CAPABILITY.getStorage().readNBT(RADIATION_CAPABILITY, lazyOptional.orElseThrow(() -> new NullPointerException("An error has occur during reading Radiation Effect Capability by Plant Tech 2")), null, nbt);
    }

    @Override
    public void setLevel(float level)
    {
        this.level = level;
    }

    @Override
    public float getLevel()
    {
        return this.level;
    }

    @Override
    public void increaseLevel(float amount)
    {
        this.level += amount;
    }

    @Override
    public void decreaseLevel(float amount)
    {
        this.level -= amount;
    }

    public static class RadiationEffectStorage implements Capability.IStorage<IRadiationEffect>
    {
        public RadiationEffectStorage(){}

        @Nullable
        @Override
        public INBT writeNBT(Capability<IRadiationEffect> capability, IRadiationEffect instance, Direction side)
        {
            CompoundNBT compound = new CompoundNBT();
            compound.putFloat("radiationeffect", instance.getLevel());
            return compound;
        }

        @Override
        public void readNBT(Capability<IRadiationEffect> capability, IRadiationEffect instance, Direction side, INBT nbt)
        {
            instance.setLevel(((CompoundNBT) nbt).getFloat("radiationeffect"));
        }
    }
}