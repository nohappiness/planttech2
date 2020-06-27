package net.kaneka.planttech2.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumTraitsInt implements IStringSerializable
{
    GROWSPEED(0, "growspeed", 0, 10, 0.04F), 
    SENSITIVITY(1, "sensitivity", 0, 10, 0.04F), 
    LIGHTSENSITIVITY(2, "lightsensitivity", 0, 14, 0.06F), 
    WATERSENSITIVITY(3, "watersensitivity", 0, 8, 0.06F), 
    TEMPERATURETOLERANCE(4, "temperaturetolerance", 0, 4, 0.03F), 
    PRODUCTIVITY(5, "productivity", 0, 5, 0.04F), 
    FERTILITY(6, "fertility", 0, 5, 0.04F), 
    SPREEDINGSPEED(7, "spreedingspeed", 0, 10, 0.04F), 
    GENESTRENGHT(8, "genestrenght", 0, 10, 0.04F), 
    ENERGYVALUE(9, "energyvalue", 1, 10, 0.04F);

    private final int meta, min, max;
    private final float transitionpossibility;
    private final String name;
    private static final EnumTraitsInt[] META_LOOKUP = new EnumTraitsInt[values().length];

    private EnumTraitsInt(int meta, String name, int min, int max, float transitionpossibility)
    {
	this.meta = meta;
	this.name = name;
	this.min = min;
	this.max = max;
	this.transitionpossibility = transitionpossibility;
    }

    

    public static EnumTraitsInt getByMetadata(int meta)
    {
	if (meta < 0 || meta >= META_LOOKUP.length)
	{
	    meta = 0;
	}

	return META_LOOKUP[meta];
    }

    public static EnumTraitsInt getByName(String name)
    {
	for (EnumTraitsInt trait : EnumTraitsInt.values())
	{
	    if (trait.name.equalsIgnoreCase(name))
	    {
		return trait;
	    }
	}
	return null;
    }

    public int getMetadata()
    {
	return this.meta;
    }

    public String getName()
    {
	return this.name;
    }

    public int getMin()
    {
	return this.min;
    }

    public int getMax()
    {
	return this.max;
    }

    public float getTransitionPossibility()
    {
	return this.transitionpossibility;
    }

    static
    {
	for (EnumTraitsInt type : values())
	{
	    META_LOOKUP[type.getMetadata()] = type;
	}
    }

	@Override
	public String func_176610_l()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
