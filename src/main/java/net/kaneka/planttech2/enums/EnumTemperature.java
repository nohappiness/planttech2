package net.kaneka.planttech2.enums;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;

import javax.annotation.Nullable;
import java.util.List;

public enum EnumTemperature
{
	EXTREME_COLD(-1.0F, 0.0F, ChatFormatting.BLUE),
	COLD(0.0F, 0.5F, ChatFormatting.AQUA),
	NORMAL(0.5F, 1.0F, ChatFormatting.GREEN),
	WARM(1.0F, 1.5F, ChatFormatting.YELLOW),
	EXTREME_WARM(1.5F, 2.5F, ChatFormatting.RED);

	private final float min, max;
	private final ChatFormatting color;

	EnumTemperature(float min, float max, ChatFormatting color)
	{
		this.min = min;
		this.max = max;
		this.color = color;
	}

	/**
	 * if coloured is true, will returned coloured text by their temperature (Icy -- Blue/Aqua/Green/Yellow/Red -- Hot), else text without colours
	 */
	public TextComponent getDisplayString()
	{
		return new TextComponent(new TranslatableComponent("temp." + this.name().toLowerCase()).withStyle(color).getString());
	}

	public static boolean inRange(float value, int tolerance, List<EnumTemperature> temperatures)
	{
		for (EnumTemperature temperature : temperatures)
		{
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

	public ChatFormatting getColor()
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
