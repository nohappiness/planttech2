package net.kaneka.planttech2.utilities;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
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
		return get(nbt, key, CompoundNBT::getInt, defaultValue, TAG_ANY_NUMERIC);

	}

	public static float getFloat(ItemStack stack, String key, int defaultValue)
	{
		return getFloat(stack.getTag(), key, defaultValue);
	}

	public static float getFloat(CompoundNBT nbt, String key, float defaultValue)
	{
		return get(nbt, key, CompoundNBT::getFloat, defaultValue, TAG_ANY_NUMERIC);
	}

	public static boolean getBoolean(ItemStack stack, String key, boolean defaultValue)
	{
		return getBoolean(stack.getTag(), key, defaultValue);
	}

	public static boolean getBoolean(CompoundNBT nbt, String key, boolean defaultValue)
	{
		return get(nbt, key, CompoundNBT::getBoolean, defaultValue);
	}

	private static <T> T get(CompoundNBT compound, String key, BiFunction<CompoundNBT, String, T> getter, T defaultValue)
	{
		return compound != null && compound.contains(key) ? getter.apply(compound, key) : defaultValue;
	}

	private static <T> T get(CompoundNBT compound, String key, BiFunction<CompoundNBT, String, T> getter, T defaultValue, Integer type)
	{
		return compound != null && compound.contains(key, type) ? getter.apply(compound, key) : defaultValue;
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

	public static <E> List<E> constructListFromInteger(CompoundNBT compound, String key, Function<Integer, E> constructor)
	{
		return constructListNotNull(compound, key, (nbt) -> ((IntNBT) nbt).getInt(), constructor, Constants.NBT.TAG_INT);
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
