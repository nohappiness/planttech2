package net.kaneka.planttech2.utilities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import static net.minecraftforge.common.util.Constants.NBT.TAG_ANY_NUMERIC;

public class NBTHelper
{
	public static int getInt(ItemStack stack, String key, int defaultValue)
	{
		return getInt(stack.getTag(), key, defaultValue);
	}

	public static int getInt(CompoundNBT nbt, String key, int defaultValue)
	{
		if (nbt != null && nbt.contains(key, TAG_ANY_NUMERIC))
		{
			return nbt.getInt(key);
		}
		return defaultValue;
	}

	public static float getFloat(ItemStack stack, String key, int defaultValue)
	{
		return getFloat(stack.getTag(), key, defaultValue);
	}

	public static float getFloat(CompoundNBT nbt, String key, float defaultValue)
	{
		if (nbt != null && nbt.contains(key, TAG_ANY_NUMERIC))
		{
			return nbt.getFloat(key);
		}
		return defaultValue;
	}

	public static boolean getBoolean(ItemStack stack, String key, boolean defaultValue)
	{
		return getBoolean(stack.getTag(), key, defaultValue);
	}

	public static boolean getBoolean(CompoundNBT nbt, String key, boolean defaultValue)
	{
		if (nbt != null && nbt.contains(key))
		{
			return nbt.getBoolean(key);
		}
		return defaultValue;
	}
}
