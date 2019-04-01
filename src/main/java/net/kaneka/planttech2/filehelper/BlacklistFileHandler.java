package net.kaneka.planttech2.filehelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.kaneka.planttech2.PlantTechMain;

public class BlacklistFileHandler extends BaseFileHandler
{

    @Override
    public String getPath()
    {
	return "config/planttech2/cropblacklist.txt";
    }

    @Override
    protected List<String> defaultValues()
    {
	List<String> values = new ArrayList<>();
	values.add("/Use:<crop>");
	values.add("/When blacklisted, crops can not be crossbreeded and will not appear in the guides");
	values.add("abyssalnite");
	values.add("adamantine");
	values.add("aluminum");
	values.add("aluminum_brass");
	values.add("alumite");
	values.add("amber");
	values.add("apatite");
	values.add("aquamarine");
	values.add("ardite");
	values.add("awakened_draconium");
	values.add("basalt");
	values.add("black_quartz");
	values.add("blitz");
	values.add("blizz");
	values.add("blue_topaz");
	values.add("brass");
	values.add("bronze");
	values.add("certus_quartz");
	values.add("chimerite");
	values.add("chrome");
	values.add("cobalt");
	values.add("cold_iron");
	values.add("compressed_iron");
	values.add("conductive_iron");
	values.add("constantan");
	values.add("copper");
	values.add("coralium");
	values.add("dark_gem");
	values.add("dark_steel");
	values.add("desh");
	values.add("draconium");
	values.add("dreadium");
	values.add("electrical_steel");
	values.add("electrotine");
	values.add("electrum");
	values.add("elementium");
	values.add("end_steel");
	values.add("ender_amethyst");
	values.add("ender_biotite");
	values.add("enderium");
	values.add("energetic_alloy");
	values.add("fluix_crystal");
	values.add("fluxed_electrum");
	values.add("glowstone_ingot");
	values.add("graphite");
	values.add("invar");
	values.add("iridium");
	values.add("knightslime");
	values.add("lead");
	values.add("lithium");
	values.add("lumium");
	values.add("magnesium");
	values.add("malachite");
	values.add("manasteel");
	values.add("manyullyn");
	values.add("meteoric_iron");
	values.add("mithril");
	values.add("moonstone");
	values.add("neutronium");
	values.add("nickel");
	values.add("octine");
	values.add("osmium");
	values.add("peridot");
	values.add("platinum");
	values.add("pulsating_iron");
	values.add("quicksilver");
	values.add("redstone_alloy");
	values.add("refined_obsidian");
	values.add("rock_crystal");
	values.add("rubber");
	values.add("ruby");
	values.add("saltpeter");
	values.add("sapphire");
	values.add("signalum");
	values.add("silicon");
	values.add("silver");
	values.add("sky_stone");
	values.add("slate");
	values.add("slimy_bone");
	values.add("soularium");
	values.add("star_steel");
	values.add("starmetal");
	values.add("steel");
	values.add("stray");
	values.add("sulfur");
	values.add("sunstone");
	values.add("syrmorite");
	values.add("tanzanite");
	values.add("terrasteel");
	values.add("thaumium");
	values.add("tin");
	values.add("titanium");
	values.add("topaz");
	values.add("tungsten");
	values.add("uranium");
	values.add("valonite");
	values.add("vibrant_alloy");
	values.add("vinteum");
	values.add("void_metal");
	values.add("yellorium");
	values.add("zinc");

	return values;
    }
}
