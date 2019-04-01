package net.kaneka.planttech2.enums;

import java.util.EnumSet;

import net.minecraft.util.text.TextComponentTranslation;

public enum EnumTemperature
{
    EXTREME_COLD(0, -1.0F, 0.0F), COLD(1, 0.0F, 0.5F), NORMAL(2, 0.5F, 1.0F), WARM(3, 1.0F, 1.5F), EXTREME_WARM(4, 1.5F, 2.5F);

    private int id;
    private float min, max;

    EnumTemperature(int id, float min, float max)
    {
	this.id = id;
	this.min = min;
	this.max = max;
    }

    public static EnumTemperature byValue(float value)
    {
	for (EnumTemperature temp : EnumTemperature.values())
	{
	    if (temp.min <= value && temp.max > value)
	    {
		return temp;
	    }
	}
	return EXTREME_WARM;
    }

    public static EnumTemperature byId(int id)
    {
	for (EnumTemperature temp : EnumTemperature.values())
	{
	    if (temp.id == id)
	    {
		return temp;
	    }
	}
	return NORMAL;
    }

    public static EnumTemperature byName(String name)
    {
	for (EnumTemperature temp : EnumTemperature.values())
	{
	    if (temp.name().equals(name))
	    {
		return temp;
	    }
	}
	return NORMAL;
    }

    public String getDisplayString()
    {
	return new TextComponentTranslation("temp." + this.name()).getUnformattedComponentText();
    }

    public boolean inRange(float value, int tolerance)
    {
	for (EnumTemperature temp : EnumTemperature.values())
	{
	    if (this.id - tolerance <= temp.id && temp.id <= this.id + tolerance)
	    {
		if (temp.min <= value && temp.max > value)
		{
		    return true;
		}
	    }
	}
	return false;
    }

    public int getId()
    {
	return this.id;
    }
}
