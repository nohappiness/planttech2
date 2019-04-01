package net.kaneka.planttech2.filehelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.kaneka.planttech2.PlantTechMain;

public class SoilFileHandler extends BaseFileHandler
{

    @Override
    public String getPath()
    {
	return "config/planttech2/cropsoils.txt";
    }

    @Override
    protected List<String> defaultValues()
    {
	List<String> values = new ArrayList<>();
	values.add("/Use:<crop>=<mod>:<block>,<meta>");
	values.add("cactus=minecraft:sand,0");
	values.add("chorus=minecraft:end_stone,0");
	values.add("coal=minecraft:coal_ore,0");
	values.add("cocoa_bean=minecraft:log,3");
	values.add("diamond=minecraft:diamond_ore,0");
	values.add("emerald=minecraft:emerald_ore,0");
	values.add("enderdragon=minecraft:bedrock,0");
	values.add("fish=planttech2:dna_combiner,0");
	values.add("gold=minecraft:gold_ore,0");
	values.add("iron=minecraft:iron_ore,0");
	values.add("lapis=minecraft:lapis_ore,0");
	values.add("plantium=planttech2:plantium_block,0"); 
	values.add("quartz=minecraft:quartz_ore,0");
	values.add("redstone=minecraft:redstone_ore,0");
	values.add("wither=minecraft:bedrock,0");

	return values;
    }
}
