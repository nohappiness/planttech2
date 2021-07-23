package net.kaneka.planttech2.utilities;

import net.minecraft.nbt.CompoundTag;

/**
 * Easier management of nbt data i/o, currently used for crop list syncing
 */
public interface ISerializable
{
    CompoundTag save();

    default void load(CompoundTag compound) {}
}
