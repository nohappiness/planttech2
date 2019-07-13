package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.world.planttopia.biomes.PlantTopiaForestBiome;
import net.kaneka.planttech2.world.planttopia.biomes.PlantTopiaPlainsBiome;
import net.kaneka.planttech2.world.planttopia.biomes.PlantTopiaRiverBiome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {

	public static final Biome PLANTTOPIA_PLAINS = new PlantTopiaPlainsBiome();
	public static final Biome PLANTTOPIA_FOREST = new PlantTopiaForestBiome();
	public static final Biome PLANTTOPIA_RIVER = new PlantTopiaRiverBiome();
	
	public static void registerBiomes() {
		registerBiome(PLANTTOPIA_PLAINS, "planttopia_plains", BiomeType.COOL, BiomeDictionary.Type.PLAINS);
		registerBiome(PLANTTOPIA_FOREST, "planttopia_forest", BiomeType.COOL, BiomeDictionary.Type.FOREST);
		registerBiome(PLANTTOPIA_RIVER, "planttopia_river", BiomeType.COOL, BiomeDictionary.Type.RIVER);
	}

	private static Biome registerBiome(Biome biome, String name, BiomeType biomeType, net.minecraftforge.common.BiomeDictionary.Type... type) {
		biome.setRegistryName(new ResourceLocation(PlantTechMain.MODID, name));
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, type);
		return biome;
	}
}