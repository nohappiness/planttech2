package net.kaneka.planttech2.dimensions.planttopia.biomes;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public enum BiomeRadiation
{
    //minutes the radiation level on player reaches 1
    //positive
    FRESH,
    //neutral
    NONE,
    //negative
    LOW,
    MEDIUM,
    HIGH;

    BiomeRadiation() {}

    public static StringTextComponent getText(BiomeRadiation level, boolean coloured)
    {
        switch (level)
        {
            case FRESH:
                return !coloured ? new StringTextComponent("FRESH") : (StringTextComponent) new StringTextComponent("FRESH").setStyle(new Style().setColor(TextFormatting.GOLD));
            case NONE:
            default:
                return !coloured ? new StringTextComponent("NONE") : (StringTextComponent) new StringTextComponent("NONE").setStyle(new Style().setColor(TextFormatting.AQUA));
            case LOW:
                return !coloured ? new StringTextComponent("LOW") : (StringTextComponent) new StringTextComponent("LOW").setStyle(new Style().setColor(TextFormatting.GREEN));
            case MEDIUM:
                return !coloured ? new StringTextComponent("MEDIUM") : (StringTextComponent) new StringTextComponent("MEDIUM").setStyle(new Style().setColor(TextFormatting.YELLOW));
            case HIGH:
                return !coloured ? new StringTextComponent("HIGH") : (StringTextComponent) new StringTextComponent("HIGH").setStyle(new Style().setColor(TextFormatting.RED));
        }
    }

    public static float getDensity(BiomeRadiation level)
    {
        switch (level)
        {
            case FRESH:
            case MEDIUM:
                return (1.0F / 72000.0F);
            case NONE:
            default:
                return 0.0F;
            case LOW:
                return (1.0F / 108000.0F);
            case HIGH:
                return (1.0F / 36000.0F);
        }
    }
}
