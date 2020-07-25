package net.kaneka.planttech2.enums;

import net.minecraft.util.text.*;

public enum EnumTemperature
{

	EXTREME_COLD(0, -1.0F, 0.0F, TextFormatting.BLUE),
	COLD(1, 0.0F, 0.5F, TextFormatting.AQUA),
	NORMAL(2, 0.5F, 1.0F, TextFormatting.GREEN),
	WARM(3, 1.0F, 1.5F, TextFormatting.YELLOW),
	EXTREME_WARM(4, 1.5F, 2.5F, TextFormatting.RED);

    private int id;
    private float min, max;
    private Color color;

    EnumTemperature(int id, float min, float max, TextFormatting color)
    {
		this.id = id;
		this.min = min;
		this.max = max;
		this.color = Color.func_240744_a_(color);
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

	/**
	 * if coloured is true, will returned coloured text by their temperature (Extreme Cold -- Blue/Aqua/Green/Yellow/Red -- Extreme Hot), else text without colours
	 */
	public IFormattableTextComponent getDisplayString(boolean coloured)
    {
		IFormattableTextComponent str = new TranslationTextComponent("temp." + this.name());
		return coloured ? new StringTextComponent(str.getString()).setStyle(Style.EMPTY.setColor(this.getColor())) : str;
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

	public Color getColor()
	{
		return this.color;
	}
}
