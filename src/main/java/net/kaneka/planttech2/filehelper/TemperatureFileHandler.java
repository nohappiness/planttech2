package net.kaneka.planttech2.filehelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.kaneka.planttech2.PlantTechMain;

public class TemperatureFileHandler extends BaseFileHandler
{
	
    	@Override
	public String getPath()
	{
	    return "config/planttech2/croptemperatures.txt";
	}
	
    	@Override
	protected List<String> defaultValues()
	{
		List<String> values = new ArrayList<>();
		values.add("/Use:<crop>=<temperature>"); 
		values.add("/Possible types: EXTREME_COLD, COLD, Normal, WARM, EXTREME_WARM");
		values.add("/When not set, NORMAL, croptemperature is set to NORMAL"); 
		values.add("blaze=EXTREME_WARM");
		values.add("cactus=WARM");
		values.add("chorus=COLD");
		values.add("cocoa_bean=WARM");
		values.add("drowned=COLD");
		values.add("enderdragon=EXTREME_WARM");
		values.add("endstone=COLD");
		values.add("fish=COLD");
		values.add("ghast=EXTREME_WARM");
		values.add("glowstone=EXTREME_WARM");
		values.add("guardian=COLD");
		values.add("husk=WARM");
		values.add("lava=EXTREME_WARM");
		values.add("magma_cube=EXTREME_WARM");
		values.add("netherrack=EXTREME_WARM");
		values.add("nether_wart=EXTREME_WARM");
		values.add("parrot=WARM");
		values.add("polarbear=EXTREME_COLD");
		values.add("prismarin=COLD");
		values.add("quartz=EXTREME_WARM");
		values.add("shulker=COLD");
		values.add("snow=EXTREME_COLD");
		values.add("soulsand=EXTREME_WARM");
		values.add("sponge=COLD");
		values.add("squid=COLD");
		values.add("stray=COLD");
		values.add("wither_skeleton=EXTREME_WARM");
		values.add("zombie_pigman=EXTREME_WARM");
		

		return values;
	}
}
