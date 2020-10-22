package net.kaneka.planttech2.datagen;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.crops.CropConfigProvider;
import net.kaneka.planttech2.crops.CropConfiguration;
import net.kaneka.planttech2.crops.CropEntryConfigData;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static net.minecraft.item.Items.*;

public class DefaultCropConfigProvider extends CropConfigProvider
{
	private final Map<ResourceLocation, CropEntryConfigData> data = new HashMap<>();

	public DefaultCropConfigProvider(DataGenerator generator)
	{
		super(generator);
	}

	private static ResourceLocation modLoc(String path)
	{
		return new ResourceLocation(PlantTechMain.MODID, path);
	}

	@Override
	public Map<ResourceLocation, CropEntryConfigData> getCropData()
	{
		addEntryWithSeeds("allium", false,
				b -> b.seed(() -> ALLIUM)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> ALLIUM, 0, 4));
		addEntryWithSeeds("azure_bluet", false,
				b -> b.seed(() -> AZURE_BLUET)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> AZURE_BLUET, 0, 4));
		addEntryWithSeeds("bamboo", false,
				b -> b.seed(() -> BAMBOO)
						.drop(() -> BAMBOO, 0, 4));
		addEntryWithSeeds("beast",
				b -> b.parents("illager", "witch", 0.1f));
		addEntryWithSeeds("beetroots", false,
				b -> b.seed(() -> BEETROOT_SEEDS)
						.drop(() -> BEETROOT, 0, 4));
		addEntryWithSeeds("blaze",
				b -> b.parents("creeper", "lava", 0.1f)
						.temperature(EnumTemperature.EXTREME_WARM));
		addEntryWithSeeds("blue_orchid", false,
				b -> b.seed(() -> BLUE_ORCHID)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> BLUE_ORCHID, 0, 4));
		addEntryWithSeeds("cactus", false,
				b -> b.seed(() -> CACTUS)
						.drop(() -> CACTUS, 0, 4)
						.temperature(EnumTemperature.WARM)
						.soil(() -> Blocks.SAND));
		addEntryWithSeeds("carrot", false,
				b -> b.seed(() -> CARROT)
						.drop(() -> CARROT, 0, 4));
		addEntryWithSeeds("chicken",
				b -> b.parents("beetroots", "cactus", 0.1f));
		addEntryWithSeeds("chorus", false,
				b -> b.seed(() -> CHORUS_FLOWER)
						.drop(() -> CHORUS_FRUIT, 0, 4)
						.temperature(EnumTemperature.COLD)
						.soil(() -> Blocks.END_STONE));
		addEntryWithSeeds("coal",
				b -> b.parents("nether_wart", "chorus", 0.1f)
						.soil(() -> Blocks.COAL_ORE));
		addEntryWithSeeds("cocoa_bean", false,
				b -> b.seed(() -> COCOA_BEANS)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> COCOA_BEANS, 0, 4)
						.temperature(EnumTemperature.WARM));
		addEntryWithSeeds("cornflower", false,
				b -> b.seed(() -> CORNFLOWER)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> CORNFLOWER, 0, 4));
		addEntryWithSeeds("cow",
				b -> b.parents("carrot", "potato", 0.1f));
		addEntryWithSeeds("creeper",
				b -> b.parents("pig", "coal", 0.1f));
		addEntryWithSeeds("dancium",
				b -> b.parents("plantium", "fish", 0.1f));
		addEntryWithSeeds("dandelion", false,
				b -> b.seed(() -> DANDELION)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> DANDELION, 0, 4));
		addEntryWithSeeds("diamond",
				b -> b.parents("quartz", "enderdragon", 0.1f)
						.soil(() -> Blocks.DIAMOND_ORE));
		addEntryWithSeeds("dirt",
				b -> b.parents("melon", "pumpkin", 0.1f));
		addEntryWithSeeds("drowned",
				b -> b.parents("stray", "husk", 0.1f)
						.temperature(EnumTemperature.COLD));
		addEntryWithSeeds("emerald",
				b -> b.parents("diamond", "wither", 0.1f)
						.soil(() -> Blocks.DIAMOND_ORE));
		addEntryWithSeeds("enderdragon",
				b -> b.parents("enderman", "shulker", 0.1f)
						.temperature(EnumTemperature.EXTREME_WARM)
						.soil(() -> Blocks.BEDROCK));
		addEntryWithSeeds("enderman",
				b -> b.parents("wither_skeleton", "netherrack", 0.1f));
		addEntryWithSeeds("endstone",
				b -> b.parents("coal", "chorus", 0.1f)
						.temperature(EnumTemperature.COLD));
		addEntryWithSeeds("fish",
				b -> b.parents("cactus", "sugarcane", 0.1f)
						.temperature(EnumTemperature.COLD));
		addEntryWithSeeds("ghast",
				b -> b.parents("magma_cube", "blaze", 0.1f)
						.temperature(EnumTemperature.EXTREME_WARM));
		addEntryWithSeeds("glowstone",
				b -> b.parents("soulsand", "netherrack", 0.1f)
						.temperature(EnumTemperature.EXTREME_WARM));
		addEntryWithSeeds("gold",
				b -> b.parents("coal", "lapis", 0.1f)
						.soil(() -> Blocks.GOLD_ORE));
		addEntryWithSeeds("guardian",
				b -> b.parents("ghast", "witch", 0.1f)
						.temperature(EnumTemperature.COLD));
		addEntryWithSeeds("husk",
				b -> b.parents("zombie", "lava", 0.1f)
						.temperature(EnumTemperature.WARM));
		addEntryWithSeeds("illager",
				b -> b.parents("villager", "witch", 0.1f));
		addEntryWithSeeds("iron",
				b -> b.parents("redstone", "gold", 0.1f)
						.soil(() -> Blocks.IRON_ORE));
		addEntryWithSeeds("kanekium",
				b -> b.parents("plantium", "squid", 0.1f));
		addEntryWithSeeds("kelp", false,
				b -> b.seed(() -> KELP)
						.drop(() -> KELP, 0, 4));
		addEntryWithSeeds("kinnoium",
				b -> b.parents("plantium", "pig", 0.1f));
		addEntryWithSeeds("lapis",
				b -> b.parents("stone", "coal", 0.1f)
						.soil(() -> Blocks.LAPIS_ORE));
		addEntryWithSeeds("lava",
				b -> b.parents("stone", "water", 0.1f)
						.temperature(EnumTemperature.EXTREME_WARM));
		addEntryWithSeeds("lenthurium",
				b -> b.parents("plantium", "chicken", 0.1f));
		addEntryWithSeeds("lilly_of_the_valley", false,
				b -> b.seed(() -> LILY_OF_THE_VALLEY)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> LILY_OF_THE_VALLEY, 0, 4));
		addEntryWithSeeds("magma_cube",
				b -> b.parents("slime", "lava", 0.1f)
						.temperature(EnumTemperature.EXTREME_WARM));
		addEntryWithSeeds("melon", false,
				b -> b.seed(() -> MELON_SEEDS)
						.drop(() -> MELON, 0, 4));
		addEntryWithSeeds("mooshroom",
				b -> b.parents("cow", "mushroom", 0.1f));
		addEntryWithSeeds("mushroom", false,
				b -> b.seed(() -> BROWN_MUSHROOM)
						.seed(() -> RED_MUSHROOM)
						.drop(() -> BROWN_MUSHROOM, 0, 4)
						.drop(() -> RED_MUSHROOM, 0, 4));
		addEntryWithSeeds("mycelium",
				b -> b.parents("netherrack", "mooshroom", 0.1f));
		addEntryWithSeeds("nether_wart", false,
				b -> b.seed(() -> NETHER_WART)
						.drop(() -> NETHER_WART, 0, 4)
						.temperature(EnumTemperature.EXTREME_WARM));
		addEntryWithSeeds("netherrack",
				b -> b.parents("endstone", "nether_wart", 0.1f)
						.temperature(EnumTemperature.EXTREME_WARM));
		addEntryWithSeeds("orange_tulip", false,
				b -> b.seed(() -> ORANGE_TULIP)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> ORANGE_TULIP, 0, 4));
		addEntryWithSeeds("oxeye_daisy", false,
				b -> b.seed(() -> OXEYE_DAISY)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> OXEYE_DAISY, 0, 4));
		addEntryWithSeeds("pink_tulip", false,
				b -> b.seed(() -> PINK_TULIP)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> PINK_TULIP, 0, 4));
		addEntryWithSeeds("plantium",
				b -> b.seed(() -> ModItems.PLANTIUM_NUGGET)
						.soil(() -> ModBlocks.PLANTIUM_BLOCK));
		addEntryWithSeeds("poppy", false,
				b -> b.seed(() -> POPPY)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> POPPY, 0, 4));
		addEntryWithSeeds("potato", false,
				b -> b.seed(() -> POTATO)
						.drop(() -> POTATO, 0, 4)
						.drop(() -> POISONOUS_POTATO, 0, 2));
		addEntryWithSeeds("pumpkin", false,
				b -> b.seed(() -> PUMPKIN_SEEDS)
						.drop(() -> PUMPKIN, 0, 3));
		addEntryWithSeeds("panda",
				b -> b.parents("beetroots", "mushroom", 0.1f));
		addEntryWithSeeds("parrot",
				b -> b.parents("wheat", "cocoa_bean", 0.1f)
						.temperature(EnumTemperature.WARM));
		addEntryWithSeeds("pig",
				b -> b.parents("beetroots", "carrot", 0.1f));
		addEntryWithSeeds("polarbear",
				b -> b.parents("cow", "panda", 0.1f)
						.temperature(EnumTemperature.EXTREME_COLD));
		addEntryWithSeeds("prismarine",
				b -> b.parents("soulsand", "water", 0.1f)
						.temperature(EnumTemperature.COLD));
		addEntryWithSeeds("quartz",
				b -> b.parents("netherrack", "iron", 0.1f)
						.temperature(EnumTemperature.EXTREME_WARM));
		addEntryWithSeeds("redstone",
				b -> b.parents("gold", "glowstone", 0.1f)
						.soil(() -> Blocks.REDSTONE_ORE));
		addEntryWithSeeds("red_tulip", false,
				b -> b.seed(() -> RED_TULIP)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> RED_TULIP, 0, 4));
		addEntryWithSeeds("sand",
				b -> b.parents("mushroom", "cactus", 0.1f));
		addEntryWithSeeds("sheep",
				b -> b.parents("potato", "wheat", 0.1f));
		addEntryWithSeeds("shulker",
				b -> b.parents("blaze", "endstone", 0.1f)
						.temperature(EnumTemperature.COLD));
		addEntryWithSeeds("skeleton",
				b -> b.parents("pig", "cow", 0.1f));
		addEntryWithSeeds("slime",
				b -> b.parents("creeper", "zombie", 0.1f));
		addEntryWithSeeds("snow",
				b -> b.parents("polarbear", "water", 0.1f)
						.temperature(EnumTemperature.EXTREME_COLD));
		addEntryWithSeeds("soulsand",
				b -> b.parents("sand", "netherrack", 0.1f)
						.temperature(EnumTemperature.EXTREME_WARM));
		addEntryWithSeeds("spider",
				b -> b.parents("sheep", "squid", 0.1f));
		addEntryWithSeeds("sponge",
				b -> b.parents("mycelium", "guardian", 0.1f)
						.temperature(EnumTemperature.COLD));
		addEntryWithSeeds("squid",
				b -> b.parents("nether_wart", "vine", 0.1f)
						.temperature(EnumTemperature.COLD));
		addEntryWithSeeds("stone",
				b -> b.parents("wheat", "mushroom", 0.1f));
		addEntryWithSeeds("stray",
				b -> b.parents("skeleton", "snow", 0.1f)
						.temperature(EnumTemperature.COLD));
		addEntryWithSeeds("sugarcane", false,
				b -> b.seed(() -> SUGAR_CANE)
						.drop(() -> SUGAR_CANE, 0, 4));
		addEntryWithSeeds("turtle",
				b -> b.parents("squid", "fish", 0.1f));
		addEntryWithSeeds("villager",
				b -> b.parents("polarbear", "turtle", 0.1f));
		addEntryWithSeeds("vine", false,
				b -> b.seed(() -> VINE)
						.drop(() -> VINE, 0, 4));
		addEntryWithSeeds("water",
				b -> b.parents("vine", "sugarcane", 0.1f));
		addEntryWithSeeds("wheat", false,
				b -> b.seed(() -> WHEAT_SEEDS)
						.drop(() -> WHEAT, 0, 4));
		addEntryWithSeeds("white_tulip", false,
				b -> b.seed(() -> WHITE_TULIP)
						.drop(() -> ModItems.COLOR_PARTICLES, 1, 4)
						.drop(() -> WHITE_TULIP, 0, 4));
		addEntryWithSeeds("witch",
				b -> b.parents("villager", "soulsand", 0.1f));
		addEntryWithSeeds("wither",
				b -> b.parents("ghast", "zombie_villager", 0.1f)
						.soil(() -> Blocks.BEDROCK));
		addEntryWithSeeds("wither_rose", false,
				b -> b.seed(() -> WITHER_ROSE)
						.drop(() -> WITHER_ROSE, 0, 1));
		addEntryWithSeeds("wither_skeleton",
				b -> b.parents("skeleton", "netherrack", 0.1f)
						.temperature(EnumTemperature.EXTREME_WARM));
		addEntryWithSeeds("wood",
				b -> b.parents("dirt", "sand", 0.1f));
		addEntryWithSeeds("zombie",
				b -> b.parents("chicken", "sheep", 0.1f));
		addEntryWithSeeds("zombie_pigman",
				b -> b.parents("zombie", "nether_wart", 0.1f)
						.temperature(EnumTemperature.EXTREME_WARM));
		addEntryWithSeeds("zombie_villager",
				b -> b.parents("zombie", "villager", 0.1f));

		addDisabledEntry("abyssalnite");
		addDisabledEntry("adamantine");
		addDisabledEntry("aluminum");
		addDisabledEntry("aluminum_brass");
		addDisabledEntry("alumite");
		addDisabledEntry("amber");
		addDisabledEntry("apatite");
		addDisabledEntry("aquamarine");
		addDisabledEntry("ardite");
		addDisabledEntry("awakened_draconium");
		addDisabledEntry("basalt");
		addDisabledEntry("black_quartz");
		addDisabledEntry("blitz");
		addDisabledEntry("blizz");
		addDisabledEntry("blue_topaz");
		addDisabledEntry("brass");
		addDisabledEntry("bronze");
		addDisabledEntry("certus_quartz");
		addDisabledEntry("chimerite");
		addDisabledEntry("chrome");
		addDisabledEntry("cobalt");
		addDisabledEntry("cold_iron");
		addDisabledEntry("compressed_iron");
		addDisabledEntry("conductive_iron");
		addDisabledEntry("constantan");
		addDisabledEntry("copper");
		addDisabledEntry("coralium");
		addDisabledEntry("dark_gem");
		addDisabledEntry("dark_steel");
		addDisabledEntry("desh");
		addDisabledEntry("draconium");
		addDisabledEntry("dreadium");
		addDisabledEntry("electrical_steel");
		addDisabledEntry("electrotine");
		addDisabledEntry("electrum");
		addDisabledEntry("elementium");
		addDisabledEntry("end_steel");
		addDisabledEntry("ender_amethyst");
		addDisabledEntry("ender_biotite");
		addDisabledEntry("enderium");
		addDisabledEntry("energetic_alloy");
		addDisabledEntry("fluix_crystal");
		addDisabledEntry("fluxed_electrum");
		addDisabledEntry("glowstone_ingot");
		addDisabledEntry("graphite");
		addDisabledEntry("invar");
		addDisabledEntry("iridium");
		addDisabledEntry("knightslime");
		addDisabledEntry("lead");
		addDisabledEntry("lithium");
		addDisabledEntry("lumium");
		addDisabledEntry("magnesium");
		addDisabledEntry("malachite");
		addDisabledEntry("manasteel");
		addDisabledEntry("manyullyn");
		addDisabledEntry("meteoric_iron");
		addDisabledEntry("mithril");
		addDisabledEntry("moonstone");
		addDisabledEntry("neutronium");
		addDisabledEntry("nickel");
		addDisabledEntry("octine");
		addDisabledEntry("osmium");
		addDisabledEntry("peridot");
		addDisabledEntry("platinum");
		addDisabledEntry("pulsating_iron");
		addDisabledEntry("quicksilver");
		addDisabledEntry("redstone_alloy");
		addDisabledEntry("refined_obsidian");
		addDisabledEntry("rock_crystal");
		addDisabledEntry("rubber");
		addDisabledEntry("ruby");
		addDisabledEntry("saltpeter");
		addDisabledEntry("signalum");
		addDisabledEntry("silicon");
		addDisabledEntry("silver");
		addDisabledEntry("sky_stone");
		addDisabledEntry("slate");
		addDisabledEntry("slimy_bone");
		addDisabledEntry("soularium");
		addDisabledEntry("sapphire");
		addDisabledEntry("star_steel");
		addDisabledEntry("starmetal");
		addDisabledEntry("steel");
		addDisabledEntry("sulfur");
		addDisabledEntry("sunstone");
		addDisabledEntry("syrmorite");
		addDisabledEntry("tanzanite");
		addDisabledEntry("terrasteel");
		addDisabledEntry("thaumium");
		addDisabledEntry("tin");
		addDisabledEntry("titanium");
		addDisabledEntry("topaz");
		addDisabledEntry("tungsten");
		addDisabledEntry("uranium");
		addDisabledEntry("valonite");
		addDisabledEntry("vibrant_alloy");
		addDisabledEntry("vinteum");
		addDisabledEntry("void_metal");
		addDisabledEntry("yellorium");
		addDisabledEntry("zinc");

		return data;
	}

	private void addDisabledEntry(final String crop)
	{
		addDisabledEntry(crop, CropConfiguration.Builder::disabled);
	}

	private void addDisabledEntry(final String crop, Consumer<CropConfiguration.Builder> config)
	{
		addEntryWithSeeds(crop, true, builder -> config.accept(builder.disabled()));
	}

	private void addEntryWithSeeds(String crop, Consumer<CropConfiguration.Builder> config)
	{
		addEntryWithSeeds(crop, true, config);
	}

	private void addEntryWithSeeds(String crop, final boolean hasParticle, Consumer<CropConfiguration.Builder> config)
	{
		addEntry(crop, builder -> {
			builder.primarySeed(() -> ModItems.SEEDS.get(crop), 1, 4);
			if (hasParticle)
				builder.drop(() -> ModItems.PARTICLES.get(crop), 0, 8);
			config.accept(builder);
		});
	}

	private void addEntry(String crop, Consumer<CropConfiguration.Builder> config)
	{
		CropConfiguration.Builder builder = CropConfiguration.builder();
		config.accept(builder);
		data.put(modLoc(crop), CropEntryConfigData.create(crop, builder.build()));
	}
}
