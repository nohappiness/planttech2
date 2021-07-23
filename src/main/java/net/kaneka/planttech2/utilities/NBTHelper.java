package net.kaneka.planttech2.utilities;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.nbt.*;
import net.minecraft.world.item.ItemStack;
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

	public static int getInt(CompoundTag nbt, String key, int defaultValue)
	{
		return get(nbt, key, CompoundTag::getInt, defaultValue, TAG_ANY_NUMERIC);

	}

	public static float getFloat(ItemStack stack, String key, int defaultValue)
	{
		return getFloat(stack.getTag(), key, defaultValue);
	}

	public static float getFloat(CompoundTag nbt, String key, float defaultValue)
	{
		return get(nbt, key, CompoundTag::getFloat, defaultValue, TAG_ANY_NUMERIC);
	}

	public static boolean getBoolean(ItemStack stack, String key, boolean defaultValue)
	{
		return getBoolean(stack.getTag(), key, defaultValue);
	}

	public static boolean getBoolean(CompoundTag nbt, String key, boolean defaultValue)
	{
		return get(nbt, key, CompoundTag::getBoolean, defaultValue);
	}

	private static <T> T get(CompoundTag compound, String key, BiFunction<CompoundTag, String, T> getter, T defaultValue)
	{
		return compound != null && compound.contains(key) ? getter.apply(compound, key) : defaultValue;
	}

	private static <T> T get(CompoundTag compound, String key, BiFunction<CompoundTag, String, T> getter, T defaultValue, Integer type)
	{
		return compound != null && compound.contains(key, type) ? getter.apply(compound, key) : defaultValue;
	}

	public static <E extends ISerializable> void putSerilizableList(CompoundTag compound, String key, Collection<E> collection)
	{
		putList(compound, key, collection, ISerializable::save);
	}

	public static <E> void putList(CompoundTag compound, String key, Collection<E> collection, Function<E, Tag> serializer)
	{
		ListTag list = new ListTag();
		for (E e : collection)
			list.add(serializer.apply(e));
		compound.put(key, list);
	}

	public static <E> List<E> constructListFromCompound(CompoundTag compound, String key, Function<CompoundTag, E> constructor)
	{
		return constructListNotNull(compound, key, (nbt) -> (CompoundTag) nbt, constructor, Constants.NBT.TAG_COMPOUND);
	}

	public static <E> List<E> constructListFromString(CompoundTag compound, String key, Function<String, E> constructor)
	{
		return constructListNotNull(compound, key, (nbt) -> ((StringTag) nbt).getAsString(), constructor, Constants.NBT.TAG_STRING);
	}

	public static <E> List<E> constructListFromInteger(CompoundTag compound, String key, Function<Integer, E> constructor)
	{
		return constructListNotNull(compound, key, (nbt) -> ((IntTag) nbt).getAsInt(), constructor, Constants.NBT.TAG_INT);
	}

	private static <E, T> List<E> constructListNotNull(CompoundTag compound, String key, Function<Tag, T> typeGetter, Function<T, E> constructor, int type)
	{
		ListTag list = compound.getList(key, type);
		List<E> results = new ArrayList<>();
		for (Tag tag : list)
		{
			T t = typeGetter.apply(tag);
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
