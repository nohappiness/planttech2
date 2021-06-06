package net.kaneka.planttech2.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class PlantTech2Configuration
{
	public static final ForgeConfigSpec SERVER;
	public static final ForgeConfigSpec CLIENT;

	public static final String CATEGORY_UPGRADECHIPS = "upgradechips";

	public static ForgeConfigSpec.DoubleValue MULTIPLIER_ENERGYCOSTS;
	public static ForgeConfigSpec.DoubleValue MULTIPLIER_CAPACITY;
	public static ForgeConfigSpec.DoubleValue MULTIPLIER_ENERGYPRODUCTION;
	public static ForgeConfigSpec.DoubleValue MULTIPLIER_ARMOR;
	public static ForgeConfigSpec.DoubleValue MULTIPLIER_ATTACKSPEED;
	public static ForgeConfigSpec.DoubleValue MULTIPLIER_ATTACK;
	public static ForgeConfigSpec.DoubleValue MULTIPLIER_BREAKDOWNRATE;
	public static ForgeConfigSpec.DoubleValue MULTIPLIER_THOUGHNESS;

	public static ForgeConfigSpec.DoubleValue MULTIPLIER_CAPACITY_MAX;
	public static ForgeConfigSpec.DoubleValue MULTIPLIER_ATTACKSPEED_MAX;
	public static ForgeConfigSpec.DoubleValue MULTIPLIER_ATTACK_MAX;
	public static ForgeConfigSpec.DoubleValue MULTIPLIER_BREAKDOWNRATE_MAX;
	public static ForgeConfigSpec.DoubleValue MULTIPLIER_THOUGHNESS_MAX;

	static
	{
		ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
		ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

		ClientConfig.init(CLIENT_BUILDER);

		SERVER_BUILDER.comment("Upgradechip settings").push(CATEGORY_UPGRADECHIPS);

		MULTIPLIER_ENERGYCOSTS = SERVER_BUILDER.comment("Multiply the energycosts per chip").defineInRange("multiplier_energycosts", 1D, 0.2D, 8D);
		MULTIPLIER_CAPACITY = SERVER_BUILDER.comment("Multiply the capacity per chip").defineInRange("multiplier_capacity", 1D, 0.2D, 8D);
		MULTIPLIER_ENERGYPRODUCTION = SERVER_BUILDER.comment("Multiply the energyproduction per chip").defineInRange("multiplier_energyproduction", 1D, 0.2D, 8D);
		MULTIPLIER_ARMOR = SERVER_BUILDER.comment("Multiply the armor per chip").defineInRange("multiplier_armor", 1D, 0.2D, 8D);
		MULTIPLIER_ATTACKSPEED = SERVER_BUILDER.comment("Multiply the attackspeed per chip").defineInRange("multiplier_attackspeed", 1D, 0.2D, 8D);
		MULTIPLIER_ATTACK = SERVER_BUILDER.comment("Multiply the attack per chip").defineInRange("multiplier_attack", 1D, 0.2D, 8D);
		MULTIPLIER_BREAKDOWNRATE = SERVER_BUILDER.comment("Multiply the breakdownrate per chip").defineInRange("multiplier_breakdownrate", 1D, 0.2D, 8D);
		MULTIPLIER_THOUGHNESS = SERVER_BUILDER.comment("Multiply the thoughness per chip").defineInRange("multiplier_thoughness", 1D, 0.2D, 8D);

		MULTIPLIER_CAPACITY_MAX = SERVER_BUILDER.comment("Multiply the max capacity").defineInRange("multiplier_max_capacity", 1D, 0.2D, 8D);
		MULTIPLIER_ATTACKSPEED_MAX = SERVER_BUILDER.comment("Multiply the max attackspeed").defineInRange("multiplier_max_attackspeed", 1D, 0.2D, 8D);
		MULTIPLIER_ATTACK_MAX = SERVER_BUILDER.comment("Multiply the max Attack").defineInRange("multiplier_max_attack", 1D, 0.2D, 8D);
		MULTIPLIER_BREAKDOWNRATE_MAX = SERVER_BUILDER.comment("Multiply the max breakdownrate").defineInRange("multiplier_max_breakdownrate", 1D, 0.2D, 8D);
		MULTIPLIER_THOUGHNESS_MAX = SERVER_BUILDER.comment("Multiply the max thoughness").defineInRange("multiplier_max_thoughness", 1D, 0.2D, 8D);

		SERVER_BUILDER.pop();

		SERVER = SERVER_BUILDER.build();
		CLIENT = CLIENT_BUILDER.build();
	}
}
