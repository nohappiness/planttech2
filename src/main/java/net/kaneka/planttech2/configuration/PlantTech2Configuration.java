package net.kaneka.planttech2.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class PlantTech2Configuration
{
	public static final ForgeConfigSpec SERVER;
	public static final ForgeConfigSpec CLIENT;
	
	static 
	{
		ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
		ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

		ClientConfig.init(CLIENT_BUILDER);
		
		SERVER = SERVER_BUILDER.build();
		CLIENT = CLIENT_BUILDER.build();
	}
}
