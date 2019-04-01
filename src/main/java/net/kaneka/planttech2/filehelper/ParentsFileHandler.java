package net.kaneka.planttech2.filehelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.kaneka.planttech2.PlantTechMain;

public class ParentsFileHandler extends BaseFileHandler
{
	
    	@Override
	public String getPath()
	{
	    return "config/planttech2/cropparents.txt";
	}
	
    	@Override
	protected List<String> defaultValues()
	{
		List<String> values = new ArrayList<>();
		values.add("/Use:<crop>=<parent1>+<parent2>,<chance to mutate>");
		values.add("/chance to mutate: 0.1 means 10%"); 
		values.add("beast=illager+witch,0.1");
		values.add("blaze=creeper+lava,0.1");
		values.add("chicken=beetroots+cactus,0.1");
		values.add("coal=netherrack+chorus,0.1");
		values.add("cow=carrot+potato,0.1");
		values.add("creeper=pig+coal,0.1");
		values.add("dancium=plantium+fish,0.1");
		values.add("diamond=quartz+enderdragon,0.1");
		values.add("dirt=melon+pumpkin,0.1");
		values.add("drowned=stray+husk,0.1");
		values.add("emerald=diamond+wither,0.1");
		values.add("enderdragon=enderman+shulker,0.1");
		values.add("enderman=wither_skeleton+netherrack,0.1");
		values.add("endstone=coal+chorus,0.1");
		values.add("fish=cactus+sugarcane,0.1");
		values.add("ghast=magma_cube+blaze,0.1");
		values.add("glowstone=soulsand+netherrack,0.1");
		values.add("gold=coal+lapis,0.1");
		values.add("guardian=ghast+witch,0.1");
		values.add("husk=zombie+lava,0.1");
		values.add("illager=villager+witch,0.1");
		values.add("iron=redstone+gold,0.1");
		values.add("kanekium=plantium+squid,0.1");
		values.add("kinnoium=plantium+pig,0.1");
		values.add("lapis=stone+coal,0.1");
		values.add("lava=stone+water,0.1");
		values.add("lenthurium=plantium+chicken,0.1");
		values.add("magma_cube=slime+lava,0.1");
		values.add("mooshroom=cow+mushroom,0.1");
		values.add("mycelium=netherrack+mooshroom,0.1");
		values.add("netherrack=endstone+nether_wart,0.1");
		values.add("panda=beetroots+mushroom,0.1");
		values.add("parrot=wheat+cocoa_bean,0.1");
		values.add("pig=beetroots+carrot,0.1");
		values.add("polarbear=cow+panda,0.1");
		values.add("prismarin=soulsand+water,0.1");
		values.add("quartz=netherrack+iron,0.1");
		values.add("redstone=gold+glowstone,0.1");
		values.add("sand=mushroom+cactus,0.1");
		values.add("sheep=potato+wheat,0.1");
		values.add("shulker=blaze+endstone,0.1");
		values.add("skeleton=pig+cow,0.1");
		values.add("slime=creeper+zombie,0.1");
		values.add("snow=polarbear+water,0.1");
		values.add("soulsand=sand+netherrack,0.1");
		values.add("spider=sheep+squid,0.1");
		values.add("sponge=mycelium+guardian,0.1");
		values.add("squid=nether_wart+vine,0.1");
		values.add("stone=wheat+mushroom,0.1");
		values.add("stray=skeleton+snow,0.1");
		values.add("turtle=squid+fish,0.1");
		values.add("villager=polarbear+turtle,0.1");
		values.add("water=vine+sugarcane,0.1");
		values.add("witch=villager+soulsand,0.1");
		values.add("wither_skeleton=skeleton+netherrack,0.1");
		values.add("wither=ghast+zombie_villager,0.1");
		values.add("wood=dirt+sand,0.1");
		values.add("zombie_pigman=zombie+nether_wart,0.1");
		values.add("zombie_villager=zombie+villager,0.1");
		values.add("zombie=chicken+sheep,0.1");
		

		return values;
	}
}
