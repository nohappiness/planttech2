package net.kaneka.planttech2.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig
{
	public static ForgeConfigSpec.BooleanValue colorblind_guis;
	
	public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client)
	{
		client.comment("Client Configuration");
		
		colorblind_guis = client.comment("Defines if the colorblind version of the guis are shown").define("planttech2.config.colorblind_guis", false);
	}
}
