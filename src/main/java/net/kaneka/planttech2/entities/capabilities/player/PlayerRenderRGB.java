package net.kaneka.planttech2.entities.capabilities.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerRenderRGB implements ICapabilitySerializable<CompoundTag>, IPlayerRenderRGB
{
    @CapabilityInject(IPlayerRenderRGB.class)
    public static Capability<IPlayerRenderRGB> PLAYER_RENDER_RGB_CAPABILITY = null;

    private final LazyOptional<IPlayerRenderRGB> lazyOptional = LazyOptional.of(PlayerRenderRGB::new);
    private float[] rgb = new float[3];
    private float fogDensity;

    public PlayerRenderRGB() {}

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        return cap == PLAYER_RENDER_RGB_CAPABILITY ? lazyOptional.cast() : LazyOptional.empty();
    }

    public static IPlayerRenderRGB getCap(Player player)
    {
        return player.getCapability(PLAYER_RENDER_RGB_CAPABILITY).orElseThrow(() -> new NullPointerException("getting capability"));
    }

    @Override
    public CompoundTag serializeNBT()
    {
        return (CompoundTag) PLAYER_RENDER_RGB_CAPABILITY.orEmpty().getStorage().writeNBT(PLAYER_RENDER_RGB_CAPABILITY, lazyOptional.orElseThrow(() -> new NullPointerException("An error has occur during writing Render RGB Capability by Plant Tech 2")), null);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
      PLAYER_RENDER_RGB_CAPABILITY.getStorage().readNBT(PLAYER_RENDER_RGB_CAPABILITY, lazyOptional.orElseThrow(() -> new NullPointerException("An error has occur during reading Render RGB Capability by Plant Tech 2")), null, nbt);
    }

    @Override
    public float[] getRGB()
    {
        return rgb;
    }

    @Override
    public float getCurrentRed()
    {
        return rgb[0];
    }

    @Override
    public float getCurrentGreen()
    {
        return rgb[1];
    }

    @Override
    public float getCurrentBlue()
    {
        return rgb[2];
    }

    @Override
    public float getCurrentFogDensity()
    {
        return this.fogDensity;
    }

    @Override
    public void setRGB(float[] rgb)
    {
        setCurrentRed(rgb[0]);
        setCurrentGreen(rgb[1]);
        setCurrentBlue(rgb[2]);
    }

    @Override
    public void setRGB(float r, float g, float b)
    {
        setRGB(new float[]{r, g, b});
    }

    @Override
    public void setCurrentRed(float value)
    {
        this.rgb[0] = (value < 0) ? 0 : Math.min(value, 255);
    }

    @Override
    public void changeCurrentRed(float amount)
    {
        this.setCurrentRed(this.getCurrentRed() + amount);
    }

    @Override
    public void setCurrentGreen(float value)
    {
        this.rgb[1] = (value < 0) ? 0 : Math.min(value, 255);
    }

    @Override
    public void changeCurrentGreen(float amount)
    {
        this.setCurrentGreen(this.getCurrentGreen() + amount);
    }

    @Override
    public void setCurrentBlue(float value)
    {
        this.rgb[2] = (value < 0) ? 0 : Math.min(value, 255);
    }

    @Override
    public void changeCurrentBlue(float amount)
    {
        this.setCurrentBlue(this.getCurrentBlue() + amount);
    }

    @Override
    public void setCurrentFogDensity(float value)
    {
        this.fogDensity = value;
    }

    public static class PlayerRenderRGBStorage implements Capability.Storage<IPlayerRenderRGB>
    {
        public PlayerRenderRGBStorage(){}

        @Nullable
        @Override
        public Tag writeNBT(Capability<IPlayerRenderRGB> capability, IPlayerRenderRGB instance, Direction side)
        {
            CompoundTag compound = new CompoundTag();
            compound.putFloat("red", instance.getCurrentRed());
            compound.putFloat("green", instance.getCurrentGreen());
            compound.putFloat("blue", instance.getCurrentBlue());
            compound.putFloat("density", instance.getCurrentFogDensity());
            return compound;
        }

        @Override
        public void readNBT(Capability<IPlayerRenderRGB> capability, IPlayerRenderRGB instance, Direction side, INBT nbt)
        {
            CompoundTag compound = (CompoundTag) nbt;
            instance.setRGB(compound.getFloat("red"), compound.getFloat("green"), compound.getFloat("blue"));
            instance.setCurrentFogDensity(compound.getFloat("density"));
        }
    }
}