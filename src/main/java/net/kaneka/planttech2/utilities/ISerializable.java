package net.kaneka.planttech2.utilities;

import net.minecraft.nbt.CompoundNBT;

/**
 * Easier management of nbt data i/o, currently used for crop list syncing
 */
public interface ISerializable
{
    CompoundNBT write();

    default void read(CompoundNBT compound) {}
}
