package net.kaneka.planttech2.utilities;

import net.minecraft.nbt.CompoundNBT;

public interface ISerializable
{
    CompoundNBT write();

    default void read(CompoundNBT compound) {}
}
