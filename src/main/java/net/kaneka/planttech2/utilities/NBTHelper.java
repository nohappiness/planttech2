package net.kaneka.planttech2.utilities;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

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
			return nbt.getInt(key);
		return defaultValue;
	}

	public static float getFloat(ItemStack stack, String key, int defaultValue)
	{
		return getFloat(stack.getTag(), key, defaultValue);
	}

	public static float getFloat(CompoundNBT nbt, String key, float defaultValue)
	{
		if (nbt != null && nbt.contains(key, TAG_ANY_NUMERIC))
			return nbt.getFloat(key);
		return defaultValue;
	}

	public static boolean getBoolean(ItemStack stack, String key, boolean defaultValue)
	{
		return getBoolean(stack.getTag(), key, defaultValue);
	}

	public static boolean getBoolean(CompoundNBT nbt, String key, boolean defaultValue)
	{
		if (nbt != null && nbt.contains(key))
			return nbt.getBoolean(key);
		return defaultValue;
	}

	public static <E extends ISerializable> void putSerilizableList(CompoundNBT compound, String key, Collection<E> collection)
	{
		putList(compound, key, collection, ISerializable::write);
	}

	public static <E> void putList(CompoundNBT compound, String key, Collection<E> collection, Function<E, INBT> serializer)
	{
		ListNBT list = new ListNBT();
		for (E e : collection)
			list.add(serializer.apply(e));
		compound.put(key, list);
	}

	public static <E> List<E> constructListFromCompound(CompoundNBT compound, String key, Function<CompoundNBT, E> constructor)
	{
		return constructListNotNull(compound, key, (nbt) -> (CompoundNBT) nbt, constructor, Constants.NBT.TAG_COMPOUND);
	}

	public static <E> List<E> constructListFromString(CompoundNBT compound, String key, Function<String, E> constructor)
	{
		return constructListNotNull(compound, key, (nbt) -> ((StringNBT) nbt).getString(), constructor, Constants.NBT.TAG_STRING);
	}

	private static <E, T> List<E> constructListNotNull(CompoundNBT compound, String key, Function<INBT, T> typeGetter, Function<T, E> constructor, int type)
	{
		ListNBT list = compound.getList(key, type);
		List<E> results = new ArrayList<>();
		for (INBT inbt : list)
		{
			T t = typeGetter.apply(inbt);
			if (t != null)
			{
				E e = constructor.apply(t);
				if (e != null)
					results.add(e);
				else PlantTechMain.LOGGER.error("value is null, this should not happen");
			}
			else PlantTechMain.LOGGER.error("value type is null, this should not happen");
		}
		return results;
	}
}
