package net.kaneka.planttech2.enums;

import javax.annotation.Nullable;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public enum EnumTemperature
{

	EXTREME_COLD(-1.0F, 0.0F, TextFormatting.BLUE),
	COLD(0.0F, 0.5F, TextFormatting.AQUA),
	NORMAL(0.5F, 1.0F, TextFormatting.GREEN),
	WARM(1.0F, 1.5F, TextFormatting.YELLOW),
	EXTREME_WARM(1.5F, 2.5F, TextFormatting.RED);

	private final float min, max;
	private final TextFormatting color;

	EnumTemperature(float min, float max, TextFormatting color)
	{
		this.min = min;
		this.max = max;
		this.color = color;
	}

	/**
	 * if coloured is true, will returned coloured text by their temperature (Icy -- Blue/Aqua/Green/Yellow/Red -- Hot), else text without colours
	 */
	public IFormattableTextComponent getDisplayString()
	{
		return new TranslationTextComponent("temp." + this.name().toLowerCase()).withStyle(color);
	}

	public static boolean inRange(float value, int tolerance, List<EnumTemperature> temperatures)
	{
		System.out.println(temperatures);
		System.out.println(tolerance);
		for (EnumTemperature temperature : temperatures)
		{
			System.out.println(temperature);
			if (temperature.inRange(value, tolerance))
				return true;
		}
		return false;
	}

	public boolean inRange(float value, int tolerance)
	{
		for (EnumTemperature temp : EnumTemperature.values())
			if (ordinal() - tolerance <= temp.ordinal() && temp.ordinal() <= this.ordinal() + tolerance)
				if (temp.min <= value && temp.max > value)
					return true;
		return false;
	}

	public TextFormatting getColor()
	{
		return this.color;
	}

	public static EnumTemperature byValue(float value)
	{
		for (EnumTemperature temp : EnumTemperature.values())
			if (temp.min <= value && temp.max > value)
				return temp;
		return NORMAL;
	}

	@Nullable
	public static EnumTemperature byName(String name)
	{
		for (EnumTemperature temp : EnumTemperature.values())
			if (temp.name().equals(name))
				return temp;
		return null;
	}
}
