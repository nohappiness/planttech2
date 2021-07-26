package net.kaneka.planttech2.entities.capabilities.player;

import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.packets.SyncRadiationLevelMessage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RadiationEffect implements ICapabilitySerializable<CompoundTag>, IRadiationEffect
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

    public static IRadiationEffect getCap(ServerPlayer player)
    {
        IRadiationEffect cap = player.getCapability(RADIATION_CAPABILITY).orElseThrow(() -> new NullPointerException("getting capability"));
        PlantTech2PacketHandler.sendTo(new SyncRadiationLevelMessage(cap.getLevel()), player);
        return cap;
    }

    @Override
    public CompoundTag serializeNBT()
    {
        CompoundTag compound = new CompoundTag();
        compound.putFloat("level", getCapability(RADIATION_CAPABILITY).orElseThrow(NullPointerException::new).getLevel());
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        getCapability(RADIATION_CAPABILITY).orElseThrow(NullPointerException::new).setLevel(nbt.getFloat("level"));
    }

    @Override
    public void setLevel(float level)
    {
        this.level = (level < 0) ? 0 : Math.min(2, level);
    }

    @Override
    public float getLevel()
    {
        return this.level;
    }

    @Override
    public void changeLevel(float amount)
    {
        this.level += amount;
        this.level = Math.min(Math.max(this.level, 0), 2);
    }
}