package net.kaneka.planttech2.utilities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper
{
	public static int getIntSave(ItemStack stack, String key, int value)
	{
		return getIntSave(stack.getTag(), key, value);  
	}
	
	public static int getIntSave(NBTTagCompound nbt, String key,  int value)
	{
		if(nbt != null)
		{
			if(nbt.hasKey(key))
			{
				return nbt.getInt(key); 
			}
		}
		
		return value; 
	}
	
	public static float getFloatSave(ItemStack stack, String key, int value)
	{
		return getFloatSave(stack.getTag(), key, value);  
	}
	
	public static float getFloatSave(NBTTagCompound nbt, String key,  float value)
	{
		if(nbt != null)
		{
			if(nbt.hasKey(key))
			{
				return nbt.getFloat(key); 
			}
		}
		
		return value; 
	}
	
	public static boolean getBooleanSave(ItemStack stack, String key, boolean value)
	{
		return getBooleanSave(stack.getTag(), key, value); 
	}
	
	public static boolean getBooleanSave(NBTTagCompound nbt, String key,  boolean value)
	{
		if(nbt != null)
		{
			if(nbt.hasKey(key))
			{
				return nbt.getBoolean(key); 
			}
		}
		
		return value; 
	}
}
