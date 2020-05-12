package net.kaneka.planttech2.librarys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.kaneka.planttech2.datapack.CropListEntryConfiguration;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static net.minecraft.item.Items.*;

public class CropList
{
	private HashMap<String, CropListEntry> croplist = new HashMap<String, CropListEntry>();

	private Map<String, CropListEntryConfiguration> configs = new HashMap<String, CropListEntryConfiguration>();

	private int nextID = 0;

	public void addEntry(String name, int seedColor, boolean hasParticle)
	{
		croplist.put(name, new CropListEntry(this.nextID, name, seedColor, hasParticle));
		this.nextID++;
	}

	public void removeEntry(String name)
	{
		croplist.remove(name);
	}

	public Set<String> getAllKeys()
	{
		return croplist.keySet();
	}

	public Collection<CropListEntry> getAllEntries()
	{
		return croplist.values();
	}

	public List<String> getAllEntriesWithoutBlacklisted()
	{
		List<String> list = new ArrayList<String>();
		for (CropListEntry entry : croplist.values())
		{
			if (!entry.isBlacklisted())
			{
				list.add(entry.getString());
			}
		}
		Collections.sort(list);
		return list;
	}

	public Set<String> getAllWithParticles()
	{
		Set<String> keys = new HashSet<String>();
		for (CropListEntry entry : getAllEntries())
		{
			if (entry.hasParticle())
			{
				keys.add(entry.getString());
			}
		}
		return keys;
	}

	public CropListEntry getEntryByName(String name)
	{
		return croplist.get(name);
	}

	public CropListEntry getBySeed(ItemStack item)
	{
		CropListEntry returnvalue = null;
		for (CropListEntry entry : this.croplist.values())
		{
			if (entry.isSeed(item))
			{
				returnvalue = (CropListEntry) entry;
				break;
			}
		}
		return returnvalue;

	}

	public CropListEntry getByID(int id)
	{
		CropListEntry returnvalue = null;
		for (CropListEntry entry : this.croplist.values())
		{
			if (entry.getID() == id)
			{
				returnvalue = (CropListEntry) entry;
				break;
			}
		}
		return returnvalue;

	}

	public Set<CropListEntry> getByParents(String parent1, String parent2)
	{
		Set<CropListEntry> returnvalue = new HashSet<CropListEntry>();
		for (CropListEntry entry : this.croplist.values())
		{
			if (entry.isChild(parent1, parent2))
			{
				returnvalue.add(entry);
			}
		}
		return returnvalue;
	}
	
	public void setConfigs(Map<String, CropListEntryConfiguration> newConfigs)
	{
		configs = newConfigs; 
	}
	
	public Map<String, CropListEntryConfiguration> getConfigs()
	{
		return configs; 
	}

	public void setMainSeeds()
	{
		for (CropListEntry entry : croplist.values())
		{
			entry.setMainSeed(new ItemStack(ModItems.SEEDS.get(entry.getString())), 1, 4);
		}
	}

	public void setParticles()
	{
		for (CropListEntry entry : this.getAllEntries())
		{
			if (entry.hasParticle())
			{
				entry.addDrop(new ItemStack(ModItems.PARTICLES.get(entry.getString())), 0, 8);
			}
		}
	}

	public int getLength()
	{
		return nextID;
	}

	public int getLengthWithoutBlacklisted()
	{
		int i = 0;
		for (CropListEntry entry : this.getAllEntries())
		{
			if (entry.isBlacklisted())
			{
				i++;
			}
		}
		return nextID - i;
	}

	public void addPlanttechEntries()
	{
		addEntry("abyssalnite", Integer.parseInt("45005f", 16), true);
		addEntry("adamantine", Integer.parseInt("d53c00", 16), true);
		addEntry("allium", Integer.parseInt("a65ee1", 16), false);
		addEntry("aluminum", Integer.parseInt("b4b4b4", 16), true);
		addEntry("aluminum_brass", Integer.parseInt("c8b300", 16), true);
		addEntry("alumite", Integer.parseInt("e789ff", 16), true);
		addEntry("amber", Integer.parseInt("e09e00", 16), true);
		addEntry("apatite", Integer.parseInt("00b3e0", 16), true);
		addEntry("aquamarine", Integer.parseInt("00c3d4", 16), true);
		addEntry("ardite", Integer.parseInt("88471b", 16), true);
		addEntry("awakened_draconium", Integer.parseInt("bf4c00", 16), true);
		addEntry("azure_bluet", Integer.parseInt("d6e8e8", 16), false);
		addEntry("bamboo", Integer.parseInt("5d8824", 16), false);
		addEntry("basalt", Integer.parseInt("424242", 16), true);
		addEntry("beast", Integer.parseInt("6a6965", 16), true);
		addEntry("beetroots", Integer.parseInt("bf2529", 16), false);
		addEntry("black_quartz", Integer.parseInt("202020", 16), true);
		addEntry("blaze", Integer.parseInt("fc9600", 16), true);
		addEntry("blitz", Integer.parseInt("fffdcc", 16), true);
		addEntry("blizz", Integer.parseInt("c3d3ff", 16), true);
		addEntry("blue_topaz", Integer.parseInt("6089ff", 16), true);
		addEntry("blue_orchid", Integer.parseInt("3066ff", 16), false);
		addEntry("brass", Integer.parseInt("eaeaea", 16), true);
		addEntry("bronze", Integer.parseInt("804500", 16), true);
		addEntry("cactus", Integer.parseInt("527d26", 16), false);
		addEntry("carrot", Integer.parseInt("e38a1d", 16), false);
		addEntry("certus_quartz", Integer.parseInt("9fc3ff", 16), true);
		addEntry("chicken", Integer.parseInt("e2e2e2", 16), true);
		addEntry("chimerite", Integer.parseInt("aeffa6", 16), true);
		addEntry("chorus", Integer.parseInt("8f718f", 16), false);
		addEntry("chrome", Integer.parseInt("FFFFFF", 16), true);
		addEntry("coal", Integer.parseInt("3f3f3f", 16), true);
		addEntry("cobalt", Integer.parseInt("1d5791", 16), true);
		addEntry("cocoa_bean", Integer.parseInt("b97335", 16), false);
		addEntry("cold_iron", Integer.parseInt("72a7ff", 16), true);
		addEntry("compressed_iron", Integer.parseInt("bdbdbd", 16), true);
		addEntry("conductive_iron", Integer.parseInt("629dff", 16), true);
		addEntry("constantan", Integer.parseInt("b1ab00", 16), true);
		addEntry("copper", Integer.parseInt("b47800", 16), true);
		addEntry("coralium", Integer.parseInt("00646a", 16), true);
		addEntry("cornflower", Integer.parseInt("466aeb", 16), false);
		addEntry("cow", Integer.parseInt("443626", 16), true);
		addEntry("creeper", Integer.parseInt("41b736", 16), true);
		addEntry("dancium", Integer.parseInt("eb8c14", 16), true);
		addEntry("dandelion", Integer.parseInt("fed639", 16), false);
		addEntry("dark_gem", Integer.parseInt("4e4e4e", 16), true);
		addEntry("dark_steel", Integer.parseInt("8d7aff", 16), true);
		addEntry("desh", Integer.parseInt("535353", 16), true);
		addEntry("diamond", Integer.parseInt("77cefb", 16), true);
		addEntry("dirt", Integer.parseInt("593d29", 16), true);
		addEntry("draconium", Integer.parseInt("7600ba", 16), true);
		addEntry("dreadium", Integer.parseInt("ba000d", 16), true);
		addEntry("drowned", Integer.parseInt("8ff1d7", 16), true);
		addEntry("electrical_steel", Integer.parseInt("b8b8b8", 16), true);
		addEntry("electrotine", Integer.parseInt("005093", 16), true);
		addEntry("electrum", Integer.parseInt("fff158", 16), true);
		addEntry("elementium", Integer.parseInt("eb11ff", 16), true);
		addEntry("emerald", Integer.parseInt("17dd62", 16), true);
		addEntry("end_steel", Integer.parseInt("feffd6", 16), true);
		addEntry("ender_amethyst", Integer.parseInt("fd35ff", 16), true);
		addEntry("ender_biotite", Integer.parseInt("000000", 16), true);
		addEntry("enderdragon", Integer.parseInt("181818", 16), true);
		addEntry("enderium", Integer.parseInt("007b77", 16), true);
		addEntry("enderman", Integer.parseInt("181818", 16), true);
		addEntry("endstone", Integer.parseInt("f6fabd", 16), true);
		addEntry("energetic_alloy", Integer.parseInt("9cff32", 16), true);
		addEntry("fish", Integer.parseInt("bf841b", 16), true);
		addEntry("fluix_crystal", Integer.parseInt("6f0098", 16), true);
		addEntry("fluxed_electrum", Integer.parseInt("fffb87", 16), true);
		addEntry("ghast", Integer.parseInt("f0f0f0", 16), true);
		addEntry("glowstone", Integer.parseInt("fbda74", 16), true);
		addEntry("glowstone_ingot", Integer.parseInt("f6ed00", 16), true);
		addEntry("gold", Integer.parseInt("f8af2b", 16), true);
		addEntry("graphite", Integer.parseInt("444444", 16), true);
		addEntry("guardian", Integer.parseInt("668980", 16), true);
		addEntry("husk", Integer.parseInt("6a5d4a", 16), true);
		addEntry("illager", Integer.parseInt("939999", 16), true);
		addEntry("invar", Integer.parseInt("c3bc00", 16), true);
		addEntry("iridium", Integer.parseInt("cfcfcf", 16), true);
		addEntry("iron", Integer.parseInt("bc9980", 16), true);
		addEntry("kanekium", Integer.parseInt("572e8a", 16), true);
		addEntry("kelp", Integer.parseInt("5b8131", 16), false);
		addEntry("kinnoium", Integer.parseInt("246b2d", 16), true);
		addEntry("knightslime", Integer.parseInt("fd5fff", 16), true);
		addEntry("lapis", Integer.parseInt("1044ac", 16), true);
		addEntry("lava", Integer.parseInt("d14f0c", 16), true);
		addEntry("lead", Integer.parseInt("326e99", 16), true);
		addEntry("lenthurium", Integer.parseInt("2c8585", 16), true);
		addEntry("lilly_of_the_valley", Integer.parseInt("e7e7e7", 16), false);
		addEntry("lithium", Integer.parseInt("fffac4", 16), true);
		addEntry("lumium", Integer.parseInt("fff282", 16), true);
		addEntry("magma_cube", Integer.parseInt("330000", 16), true);
		addEntry("magnesium", Integer.parseInt("615900", 16), true);
		addEntry("malachite", Integer.parseInt("36bf53", 16), true);
		addEntry("manasteel", Integer.parseInt("3d8fff", 16), true);
		addEntry("manyullyn", Integer.parseInt("793393", 16), true);
		addEntry("melon", Integer.parseInt("a7ac1d", 16), false);
		addEntry("meteoric_iron", Integer.parseInt("8f845e", 16), true);
		addEntry("mithril", Integer.parseInt("b7d7ff", 16), true);
		addEntry("moonstone", Integer.parseInt("eef6ff", 16), true);
		addEntry("mooshroom", Integer.parseInt("a81012", 16), true);
		addEntry("mushroom", Integer.parseInt("e21212", 16), false);
		addEntry("mycelium", Integer.parseInt("736162", 16), true);
		addEntry("nether_wart", Integer.parseInt("831c20", 16), false);
		addEntry("netherrack", Integer.parseInt("652828", 16), true);
		addEntry("neutronium", Integer.parseInt("585858", 16), true);
		addEntry("nickel", Integer.parseInt("9f998c", 16), true);
		addEntry("octine", Integer.parseInt("ffb400", 16), true);
		addEntry("orange_tulip", Integer.parseInt("bd6a22", 16), false);
		addEntry("osmium", Integer.parseInt("c6edff", 16), true);
		addEntry("oxeye_daisy", Integer.parseInt("f5ba27", 16), false);
		addEntry("panda", Integer.parseInt("f5ba27", 16), true);
		addEntry("parrot", Integer.parseInt("18bdff", 16), true);
		addEntry("peridot", Integer.parseInt("5fc859", 16), true);
		addEntry("pig", Integer.parseInt("f19e98", 16), true);
		addEntry("pink_tulip", Integer.parseInt("e4aff4", 16), false);
		addEntry("plantium", Integer.parseInt("35a048", 16), true);
		addEntry("platinum", Integer.parseInt("a2a2a2", 16), true);
		addEntry("polarbear", Integer.parseInt("f6f6f6", 16), true);
		addEntry("poppy", Integer.parseInt("ed302c", 16), false);
		addEntry("potato", Integer.parseInt("c8a24b", 16), false);
		addEntry("prismarin", Integer.parseInt("5ea496", 16), true);
		addEntry("pulsating_iron", Integer.parseInt("75d970", 16), true);
		addEntry("pumpkin", Integer.parseInt("e38a1d", 16), false);
		addEntry("quartz", Integer.parseInt("d4caba", 16), true);
		addEntry("quicksilver", Integer.parseInt("d6ffff", 16), true);
		addEntry("red_tulip", Integer.parseInt("ed302c", 16), false);
		addEntry("redstone", Integer.parseInt("ff0000", 16), true);
		addEntry("redstone_alloy", Integer.parseInt("ff0000", 16), true);
		addEntry("refined_obsidian", Integer.parseInt("62316d", 16), true);
		addEntry("rock_crystal", Integer.parseInt("a6a6a6", 16), true);
		addEntry("rubber", Integer.parseInt("444444", 16), true);
		addEntry("ruby", Integer.parseInt("980000", 16), true);
		addEntry("saltpeter", Integer.parseInt("969696", 16), true);
		addEntry("sand", Integer.parseInt("dacfa3", 16), true);
		addEntry("sapphire", Integer.parseInt("000a8e", 16), true);
		addEntry("sheep", Integer.parseInt("c09e86", 16), true);
		addEntry("shulker", Integer.parseInt("8e608e", 16), true);
		addEntry("signalum", Integer.parseInt("8e5700", 16), true);
		addEntry("silicon", Integer.parseInt("777777", 16), true);
		addEntry("silver", Integer.parseInt("dadada", 16), true);
		addEntry("skeleton", Integer.parseInt("bcbcbc", 16), true);
		addEntry("sky_stone", Integer.parseInt("383838", 16), true);
		addEntry("slate", Integer.parseInt("FFFFFF", 16), true);
		addEntry("slime", Integer.parseInt("59bd45", 16), true);
		addEntry("slimy_bone", Integer.parseInt("7b7b7b", 16), true);
		addEntry("snow", Integer.parseInt("ffffff", 16), true);
		addEntry("soularium", Integer.parseInt("443824", 16), true);
		addEntry("soulsand", Integer.parseInt("49372c", 16), true);
		addEntry("spider", Integer.parseInt("605448", 16), true);
		addEntry("sponge", Integer.parseInt("cdce4a", 16), true);
		addEntry("squid", Integer.parseInt("cdce4a", 16), true);
		addEntry("star_steel", Integer.parseInt("171717", 16), true);
		addEntry("starmetal", Integer.parseInt("22002f", 16), true);
		addEntry("steel", Integer.parseInt("686868", 16), true);
		addEntry("stone", Integer.parseInt("616161", 16), true);
		addEntry("stray", Integer.parseInt("acbabd", 16), true);
		addEntry("sugarcane", Integer.parseInt("82a859", 16), false);
		addEntry("sulfur", Integer.parseInt("b1ac27", 16), true);
		addEntry("sunstone", Integer.parseInt("c13b00", 16), true);
		addEntry("syrmorite", Integer.parseInt("c71eff", 16), true);
		addEntry("tanzanite", Integer.parseInt("a951c6", 16), true);
		addEntry("terrasteel", Integer.parseInt("32b100", 16), true);
		addEntry("thaumium", Integer.parseInt("8a00ff", 16), true);
		addEntry("tin", Integer.parseInt("aba78c", 16), true);
		addEntry("titanium", Integer.parseInt("c6c6c6", 16), true);
		addEntry("topaz", Integer.parseInt("ffde29", 16), true);
		addEntry("tungsten", Integer.parseInt("005a40", 16), true);
		addEntry("turtle", Integer.parseInt("388d3a", 16), true);
		addEntry("uranium", Integer.parseInt("3abd22", 16), true);
		addEntry("valonite", Integer.parseInt("cfa5d5", 16), true);
		addEntry("vibrant_alloy", Integer.parseInt("bf7e00", 16), true);
		addEntry("villager", Integer.parseInt("b57b67", 16), true);
		addEntry("vine", Integer.parseInt("1b5011", 16), false);
		addEntry("vinteum", Integer.parseInt("5a81ff", 16), true);
		addEntry("void_metal", Integer.parseInt("000000", 16), true);
		addEntry("water", Integer.parseInt("2b5fff", 16), true);
		addEntry("wheat", Integer.parseInt("ae19", 16), false);
		addEntry("white_tulip", Integer.parseInt("f7f7f7", 16), false);
		addEntry("witch", Integer.parseInt("a39483", 16), true);
		addEntry("wither", Integer.parseInt("343434", 16), true);
		addEntry("wither_rose", Integer.parseInt("000000", 16), false);
		addEntry("wither_skeleton", Integer.parseInt("515353", 16), true);
		addEntry("wood", Integer.parseInt("605e54", 16), true);
		addEntry("yellorium", Integer.parseInt("fffc00", 16), true);
		addEntry("zinc", Integer.parseInt("b8b78b", 16), true);
		addEntry("zombie", Integer.parseInt("71955b", 16), true);
		addEntry("zombie_pigman", Integer.parseInt("eea5a4", 16), true);
		addEntry("zombie_villager", Integer.parseInt("3b622f", 16), true);

		// loadBlacklist();

		// addEntry("", PlanttechMain.modId + ":plants/plant_", PlanttechMain.modId +
		// ":textures/blocks/plants/plant_.png", Integer.parseInt("",16));

	}

	public void configurePlanttechEntries()
	{
		this.setMainSeeds();
		this.setParticles();

		this.getEntryByName("allium").addSeeds(new ItemStack(Blocks.ALLIUM))
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4)       .addDrop(new ItemStack(ALLIUM), 0, 4);
		this.getEntryByName("azure_bluet").addSeeds(new ItemStack(Blocks.AZURE_BLUET))
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4)       .addDrop(new ItemStack(AZURE_BLUET), 0, 4);
		this.getEntryByName("blue_orchid").addSeeds(new ItemStack(Blocks.BLUE_ORCHID))
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4)       .addDrop(new ItemStack(BLUE_ORCHID), 0, 4);
		this.getEntryByName("cornflower").addSeeds(new ItemStack(Blocks.CORNFLOWER))
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4)       .addDrop(new ItemStack(CORNFLOWER), 0, 4);
		this.getEntryByName("dandelion").addSeeds(new ItemStack(Blocks.DANDELION))
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4)       .addDrop(new ItemStack(DANDELION), 0, 4);
		this.getEntryByName("lilly_of_the_valley").addSeeds(new ItemStack(LILY_OF_THE_VALLEY))
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4)       .addDrop(new ItemStack(LILY_OF_THE_VALLEY), 0, 4);
		this.getEntryByName("orange_tulip").addSeeds(new ItemStack(ORANGE_TULIP))
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4)       .addDrop(new ItemStack(ORANGE_TULIP), 0, 4);
		this.getEntryByName("oxeye_daisy").addSeeds(new ItemStack(OXEYE_DAISY))
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4)       .addDrop(new ItemStack(OXEYE_DAISY), 0, 4);
		this.getEntryByName("pink_tulip").addSeeds(new ItemStack(PINK_TULIP))
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4)       .addDrop(new ItemStack(PINK_TULIP), 0, 4);
		this.getEntryByName("poppy").addSeeds(new ItemStack(POPPY))
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4)       .addDrop(new ItemStack(POPPY), 0, 4);
		this.getEntryByName("red_tulip").addSeeds(new ItemStack(RED_TULIP))
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4)       .addDrop(new ItemStack(RED_TULIP), 0, 4);
		this.getEntryByName("white_tulip").addSeeds(new ItemStack(WHITE_TULIP))
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4)       .addDrop(new ItemStack(Blocks.WHITE_TULIP), 0, 4);

		this.getEntryByName("bamboo").addSeeds(new ItemStack(BAMBOO))
				.addDrop(new ItemStack(BAMBOO), 0, 4);
		this.getEntryByName("beetroots").addSeeds(new ItemStack(BEETROOT_SEEDS))
				.addDrop(new ItemStack(BEETROOT), 0, 4);
		this.getEntryByName("cactus").addSeeds(new ItemStack(CACTUS))
				.addDrop(new ItemStack(CACTUS), 0, 4);
		this.getEntryByName("carrot").addSeeds(new ItemStack(CARROT))
				.addDrop(new ItemStack(CARROT), 0, 4);
		this.getEntryByName("chorus").addSeeds(new ItemStack(CHORUS_FLOWER))
				.addDrop(new ItemStack(CHORUS_FRUIT), 0, 4);
		this.getEntryByName("cocoa_bean").addSeeds(new ItemStack(COCOA_BEANS))
				.addDrop(new ItemStack(COCOA_BEANS), 0, 4)
				.addDrop(new ItemStack(ModItems.COLOR_PARTICLES), 1, 4);
		this.getEntryByName("kelp").addSeeds(new ItemStack(KELP))
				.addDrop(new ItemStack(KELP), 0, 4);
		this.getEntryByName("melon").addSeeds(new ItemStack(MELON_SEEDS))
				.addDrop(new ItemStack(MELON), 0, 2);
				.addDrop(new ItemStack(MELON), 0, 3);
		this.getEntryByName("mushroom").addSeeds(new ItemStack(RED_MUSHROOM), new ItemStack(BROWN_MUSHROOM))
				.addDrop(new ItemStack(RED_MUSHROOM), 0, 4)
				.addDrop(new ItemStack(BROWN_MUSHROOM), 0, 4);
		this.getEntryByName("nether_wart").addSeeds(new ItemStack(NETHER_WART))
				.addDrop(new ItemStack(NETHER_WART), 0, 4);
		this.getEntryByName("potato").addSeeds(new ItemStack(POTATO))
				.addDrop(new ItemStack(POTATO), 0, 4)
				.addDrop(new ItemStack(POISONOUS_POTATO), 0, 2);
		this.getEntryByName("pumpkin").addSeeds(new ItemStack(PUMPKIN_SEEDS))
				.addDrop(new ItemStack(PUMPKIN), 0, 3);
		this.getEntryByName("sugarcane").addSeeds(new ItemStack(SUGAR_CANE))
				.addDrop(new ItemStack(SUGAR_CANE), 0, 4);
		this.getEntryByName("vine").addSeeds(new ItemStack(VINE))
				.addDrop(new ItemStack(VINE), 0, 4);
		this.getEntryByName("wheat").addSeeds(new ItemStack(WHEAT_SEEDS))
				.addDrop(new ItemStack(WHEAT), 0, 4);
		this.getEntryByName("wither_rose").addSeeds(new ItemStack(WITHER_ROSE))
				.addDrop(new ItemStack(WITHER_ROSE), 0, 1);

		this.getEntryByName("plantium").addSeeds(new ItemStack(ModItems.PLANTIUM_NUGGET));

		this.getEntryByName("beast");
		this.getEntryByName("blaze");
		this.getEntryByName("chicken");
		this.getEntryByName("coal");
		this.getEntryByName("cow");
		this.getEntryByName("creeper");
		this.getEntryByName("dancium");
		this.getEntryByName("diamond");
		this.getEntryByName("dirt");
		this.getEntryByName("drowned");
		this.getEntryByName("emerald");
		this.getEntryByName("enderdragon");
		this.getEntryByName("enderman");
		this.getEntryByName("endstone");
		this.getEntryByName("fish");
		this.getEntryByName("ghast");
		this.getEntryByName("glowstone");
		this.getEntryByName("gold");
		this.getEntryByName("guardian");
		this.getEntryByName("husk");
		this.getEntryByName("illager");
		this.getEntryByName("iron");
		this.getEntryByName("kanekium");
		this.getEntryByName("kinnoium");
		this.getEntryByName("lapis");
		this.getEntryByName("lava");
		this.getEntryByName("lenthurium");
		this.getEntryByName("magma_cube");
		this.getEntryByName("mooshroom");
		this.getEntryByName("mycelium");
		this.getEntryByName("netherrack");
		this.getEntryByName("panda");
		this.getEntryByName("parrot");
		this.getEntryByName("pig");
		this.getEntryByName("polarbear");
		this.getEntryByName("prismarin");
		this.getEntryByName("quartz");
		this.getEntryByName("redstone");
		this.getEntryByName("sand");
		this.getEntryByName("sheep");
		this.getEntryByName("shulker");
		this.getEntryByName("skeleton");
		this.getEntryByName("slime");
		this.getEntryByName("snow");
		this.getEntryByName("soulsand");
		this.getEntryByName("spider");
		this.getEntryByName("sponge");
		this.getEntryByName("squid");
		this.getEntryByName("stone");
		this.getEntryByName("stray");
		this.getEntryByName("turtle");
		this.getEntryByName("villager");
		this.getEntryByName("water");
		this.getEntryByName("witch");
		this.getEntryByName("wither_skeleton");
		this.getEntryByName("wither");
		this.getEntryByName("wood");
		this.getEntryByName("zombie_pigman");
		this.getEntryByName("zombie_villager");
		this.getEntryByName("zombie");

	}
	
}
