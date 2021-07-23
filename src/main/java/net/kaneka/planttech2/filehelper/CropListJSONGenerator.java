package net.kaneka.planttech2.filehelper;
/*
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.librarys.CropEntry;
import net.kaneka.planttech2.librarys.DropEntry;
import net.kaneka.planttech2.librarys.ParentPair;
import net.minecraft.world.item.ItemStack;

public class CropListJSONGenerator
{

	int maxLines = 500;
	int nextLine = 0;
	String[] lines = new String[maxLines];
	HashMap<String, String> colors = new HashMap<String, String>();
	HashMap<String, Boolean> hasParticle = new HashMap<String, Boolean>();

	private void saveFile(String file)
	{

		try
		{
			File f = new File("json/" + file + ".json");
			f.createNewFile();
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < nextLine; i++)
			{
				bw.write(lines[i]);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		lines = new String[maxLines];
		nextLine = 0;
	}

	private void addLine(String line)
	{
		this.lines[nextLine] = line;
		this.nextLine++;
	}

	private void createBlockstateFile(String name)
	{
		addLine("{");
		addLine("    \"variants\": {");
		addLine("        \"growstate=0\": {");
		addLine("            \"model\": \"planttech2:block/crop_0\"");
		addLine("       },");
		addLine("       \"growstate=1\": {");
		addLine("            \"model\": \"planttech2:block/crop_1\"");
		addLine("        },");
		addLine("        \"growstate=2\": {");
		addLine("            \"model\": \"planttech2:block/crop_2\"");
		addLine("        },");
		addLine("       \"growstate=3\": {");
		addLine("            \"model\": \"planttech2:block/crop_3\"");
		addLine("       },");
		addLine("       \"growstate=4\": {");
		addLine("           \"model\": \"planttech2:block/crop_4\"");
		addLine("       },");
		addLine("     \"growstate=5\": {");
		addLine("            \"model\": \"planttech2:block/crop_5\"");
		addLine("        },");
		addLine("        \"growstate=6\": {");
		addLine("            \"model\": \"planttech2:block/crop_6\"");
		addLine("        },");
		addLine("        \"growstate=7\": {");
		addLine("            \"model\": \"planttech2:block/crop_7\"");
		addLine("        }");
		addLine("    }");
		addLine("}");
		saveFile(name + "_crop");
	}

	private void createConfigJSON(CropEntry entry)
	{
		addLine("{");
		addLine("    \"enabled\": " + !entry.isBlacklisted() +",");
		addLine("    \"temperature\": " + entry.getTemperature().getId() + ",");
		addLine("    \"seeds\": [");
		List<ItemStack> seeds = entry.getSeeds();
		int i = 0; 
		if(seeds != null)
		{
			for(ItemStack stack: seeds)
			{
				String string = "        \"" + stack.getItem().getRegistryName() + "\"";
				if(i < seeds.size() - 1)
				{
					string += ","; 
				}
				addLine(string);
				i++;
			}
		}
		addLine("    ],");
		addLine("    \"drops\":[");
		Set<DropEntry> add_drops = entry.getDrops();
		i = 0;
		if(add_drops != null)
		{
			for(DropEntry drop: add_drops)
			{
				
				addLine("        {");
				addLine("            \"item\": \"" + drop.getItemSupplier().getItem().getRegistryName() + "\",");
				addLine("            \"min\": " + drop.getMin() + ", ");
				addLine("            \"max\": " + drop.getMax());
				String string = "        }";
				if(i < add_drops.size() - 1)
				{
					string += ","; 
				}
				addLine(string);
				i++;
			}
		}
		addLine("    ],");
		addLine("    \"parents\": [");
		Set<ParentPair> parents = entry.getParents();
		i = 0;
		if(parents != null)
		{
			for(ParentPair parent_pair: parents)
			{
				
				addLine("        {");
				addLine("            \"partner_1\": \"" + parent_pair.getParent(0) + "\",");
				addLine("            \"partner_2\": \"" + parent_pair.getParent(1) + "\", ");
				addLine("            \"chance\": " + parent_pair.getMutateChance());
				String string = "        }";
				if(i < parents.size() - 1)
				{
					string += ","; 
				}
				addLine(string);
				i++;
			}
		}
		addLine("    ],");
		addLine("    \"soil\": ");
		addLine("    {");
		ItemStack soil = entry.getSoil();
		if(!soil.isEmpty())
		{
			addLine("        \"block\": \"" + soil.getItem().getRegistryName() + "\"");
		}
		addLine("    }");
		addLine("}");
		saveFile(entry.getName());
	}

	private void addEntry(String name, String color, boolean hasParticle)
	{
		colors.put(name, color);
		this.hasParticle.put(name, hasParticle);
	}

	public void createFiles()
	{
		addEntry("allium", "a65ee1", false);
		addEntry("aluminum", "b4b4b4", true);
		addEntry("ardite", "88471b", true);
		addEntry("azure_bluet", "d6e8e8", false);
		addEntry("beast", "6a6965", true);
		addEntry("beetroots", "bf2529", false);
		addEntry("black_quartz", "202020", true);
		addEntry("blaze", "fc9600", true);
		addEntry("blue_orchid", "1c92d6", false);
		addEntry("cactus", "527d26", false);
		addEntry("carrot", "e38a1d", false);
		addEntry("chicken", "e2e2e2", true);
		addEntry("certus_quartz", "36a3a3", true);
		addEntry("chorus", "8f718f", false);
		addEntry("coal", "3f3f3f", true);
		addEntry("cobalt", "1d5791", true);
		addEntry("cocoa_bean", "b97335", false);
		addEntry("copper", "b47800", true);
		addEntry("cornflower", "466aeb", false);
		addEntry("cow", "443626", true);
		addEntry("creeper", "41b736", true);
		addEntry("dancium", "eb8c14", true);
		addEntry("dandelion", "fed639", false);
		addEntry("diamond", "77cefb", true);
		addEntry("dirt", "593d29", true);
		addEntry("drowned", "8ff1d7", true);
		addEntry("emerald", "17dd62", true);
		addEntry("enderdragon", "181818", true);
		addEntry("enderman", "181818", true);
		addEntry("endstone", "f6fabd", true);
		addEntry("fish", "bf841b", true);
		addEntry("ghast", "f0f0f0", true);
		addEntry("glowstone", "fbda74", true);
		addEntry("gold", "f8af2b", true);
		addEntry("guardian", "668980", true);
		addEntry("husk", "6a5d4a", true);
		addEntry("illager", "939999", true);
		addEntry("iron", "bc9980", true);
		addEntry("kanekium", "572e8a", true);
		addEntry("kinnoium", "246b2d", true);
		addEntry("lilly_of_the_valley", "e7e7e7", false);
		addEntry("lapis", "1044ac", true);
		addEntry("lava", "d14f0c", true);
		addEntry("lead", "326e99", true);
		addEntry("lenthurium", "2c8585", true);
		addEntry("magma_cube", "330000", true);
		addEntry("melon", "a7ac1d", false);
		addEntry("mooshroom", "a81012", true);
		addEntry("mushroom", "e21212", false);
		addEntry("mycelium", "736162", true);
		addEntry("netherrack", "652828", true);
		addEntry("nether_wart", "831c20", false);
		addEntry("nickel", "aeaf3a", true);
		addEntry("orange_tulip", "bd6a22", false);
		addEntry("oxeye_daisy", "f5ba27", false);
		addEntry("panda", "f5ba27", true);
		addEntry("parrot", "18bdff", true);
		addEntry("pig", "f19e98", true);
		addEntry("pink_tulip", "e4aff4", false);
		addEntry("plantium", "35a048", true);
		addEntry("platinum", "a2a2a2", true);
		addEntry("polarbear", "f6f6f6", true);
		addEntry("poppy", "ed302c", false);
		addEntry("potato", "c8a24b", false);
		addEntry("prismarine", "5ea496", true);
		addEntry("pumpkin", "e38a1d", false);
		addEntry("quartz", "d4caba", true);
		addEntry("redstone", "ff0000", true);
		addEntry("red_tulip", "ed302c", false);
		addEntry("sand", "dacfa3", true);
		addEntry("sheep", "c09e86", true);
		addEntry("shulker", "8e608e", true);
		addEntry("silver", "dadada", true);
		addEntry("skeleton", "bcbcbc", true);
		addEntry("slime", "59bd45", true);
		addEntry("snow", "ffffff", true);
		addEntry("soulsand", "49372c", true);
		addEntry("spider", "605448", true);
		addEntry("sponge", "cdce4a", true);
		addEntry("squid", "cdce4a", true);
		addEntry("stone", "616161", true);
		addEntry("stray", "acbabd", true);
		addEntry("sugarcane", "82a859", false);
		addEntry("tin", "aba78c", true);
		addEntry("turtle", "388d3a", true);
		addEntry("uranium", "3abd22", true);
		addEntry("villager", "b57b67", true);
		addEntry("vine", "1b5011", false);
		addEntry("water", "2b5fff", true);
		addEntry("wheat", "ae19", false);
		addEntry("white_tulip", "f7f7f7", false);
		addEntry("witch", "a39483", true);
		addEntry("wither_rose", "0", false);
		addEntry("wither_skeleton", "515353", true);
		addEntry("wither", "343434", true);
		addEntry("wood", "605e54", true);
		addEntry("zombie_pigman", "eea5a4", true);
		addEntry("zombie_villager", "3b622f", true);
		addEntry("zombie", "71955b", true);
		addEntry("rubber", "FFFFFF", true);
		addEntry("silicon", "FFFFFF", true);
		addEntry("sulfur", "FFFFFF", true);
		addEntry("saltpeter", "FFFFFF", true);
		addEntry("bronze", "FFFFFF", true);
		addEntry("zinc", "FFFFFF", true);
		addEntry("brass_silver", "FFFFFF", true);
		addEntry("graphite", "FFFFFF", true);
		addEntry("steel", "FFFFFF", true);
		addEntry("nickel", "FFFFFF", true);
		addEntry("constantan", "FFFFFF", true);
		addEntry("electrum", "FFFFFF", true);
		addEntry("invar", "FFFFFF", true);
		addEntry("mithril", "FFFFFF", true);
		addEntry("tungsten", "FFFFFF", true);
		addEntry("titanium", "FFFFFF", true);
		addEntry("chrome", "FFFFFF", true);
		addEntry("iridium ", "FFFFFF", true);
		addEntry("ruby", "FFFFFF", true);
		addEntry("sapphire", "FFFFFF", true);
		addEntry("peridot", "FFFFFF", true);
		addEntry("amber", "FFFFFF", true);
		addEntry("topaz", "FFFFFF", true);
		addEntry("malachite", "FFFFFF", true);
		addEntry("tanzanite", "FFFFFF", true);
		addEntry("blizz", "FFFFFF", true);
		addEntry("blitz", "FFFFFF", true);
		addEntry("basalt", "FFFFFF", true);
		addEntry("signalum", "FFFFFF", true);
		addEntry("lumium", "FFFFFF", true);
		addEntry("enderium", "FFFFFF", true);
		addEntry("fluxed_electrum", "FFFFFF", true);
		addEntry("aluminum_brass", "FFFFFF", true);
		addEntry("knightslime", "FFFFFF", true);
		addEntry("manyullyn", "FFFFFF", true);
		addEntry("electrical_steel", "FFFFFF", true);
		addEntry("redstone_alloy", "FFFFFF", true);
		addEntry("conductive_iron", "FFFFFF", true);
		addEntry("soularium", "FFFFFF", true);
		addEntry("dark_steel", "FFFFFF", true);
		addEntry("pulsating_iron", "FFFFFF", true);
		addEntry("energetic_alloy", "FFFFFF", true);
		addEntry("vibrant_alloy", "FFFFFF", true);
		addEntry("end_steel", "FFFFFF", true);
		addEntry("manasteel", "FFFFFF", true);
		addEntry("elementium", "FFFFFF", true);
		addEntry("terrasteel", "FFFFFF", true);
		addEntry("quicksilver", "FFFFFF", true);
		addEntry("thaumium", "FFFFFF", true);
		addEntry("void_metal", "FFFFFF", true);
		addEntry("osmium", "FFFFFF", true);
		addEntry("glowstone_ingot", "FFFFFF", true);
		addEntry("refined_obsidian", "FFFFFF", true);
		addEntry("aquarium", "FFFFFF", true);
		addEntry("cold_iron", "FFFFFF", true);
		addEntry("star_steel", "FFFFFF", true);
		addEntry("adamantine", "FFFFFF", true);
		addEntry("apatite", "FFFFFF", true);
		addEntry("electrotine", "FFFFFF", true);
		addEntry("alumite", "FFFFFF", true);
		addEntry("meteoric_iron", "FFFFFF", true);
		addEntry("desh", "FFFFFF", true);
		addEntry("coralium", "FFFFFF", true);
		addEntry("abyssalnite", "FFFFFF", true);
		addEntry("dreadium", "FFFFFF", true);
		addEntry("slimy_bone", "FFFFFF", true);
		addEntry("syrmorite", "FFFFFF", true);
		addEntry("octine", "FFFFFF", true);
		addEntry("valonite", "FFFFFF", true);
		addEntry("thorium", "FFFFFF", true);
		addEntry("boron", "FFFFFF", true);
		addEntry("lithium", "FFFFFF", true);
		addEntry("magnesium", "FFFFFF", true);
		addEntry("vinteum", "FFFFFF", true);
		addEntry("chimerite", "FFFFFF", true);
		addEntry("blue_topaz", "FFFFFF", true);
		addEntry("moonstone", "FFFFFF", true);
		addEntry("sunstone", "FFFFFF", true);
		addEntry("aquamarine", "FFFFFF", true);
		addEntry("starmetal", "FFFFFF", true);
		addEntry("rock_crystal", "FFFFFF", true);
		addEntry("ender_biotite", "FFFFFF", true);
		addEntry("slate", "FFFFFF", true);
		addEntry("dark_gem", "FFFFFF", true);
		addEntry("compressed_iron", "FFFFFF", true);
		addEntry("ender_amethyst", "FFFFFF", true);
		addEntry("draconium", "FFFFFF", true);
		addEntry("yellorium", "FFFFFF", true);
		addEntry("sky_stone", "FFFFFF", true);
		addEntry("certus_quartz", "FFFFFF", true);
		addEntry("fluix", "FFFFFF", true);
		addEntry("quartz_enriched_iron", "FFFFFF", true);
		addEntry("awakened_draconium", "FFFFFF", true);
		addEntry("neutronium", "FFFFFF", true);

		for (String name : colors.keySet())
		{
			createBlockstateFile(name);
		}
	}
	
	public void createConfigFiles()
	{
		for(CropEntry entry: PlantTechMain.croplist.values())
		{
			createConfigJSON(entry);
		}
	}
}
*/
