package net.kaneka.planttech2.registries;

import java.util.ArrayList;
import java.util.List;

import net.kaneka.planttech2.dimensions.planttopia.biomes.FlowerBiome;
import net.kaneka.planttech2.dimensions.planttopia.biomes.PlantTopiaBaseBiome;
import net.kaneka.planttech2.dimensions.planttopia.biomes.RadiationWastelastBiome;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBiomes
{
	public static List<PlantTopiaBaseBiome> BIOMES = new ArrayList<>(); 
	public static List<Biome> COMMON_BIOMES = new ArrayList<>();
	public static List<Biome> UNCOMMON_BIOMES = new ArrayList<>();
	public static List<Biome> RARE_BIOMES = new ArrayList<>();

	public static final Biome PLANTTOPIA_FLOWER = new FlowerBiome();
	public static final Biome PLANTTOPIA_WASTELAST = new RadiationWastelastBiome();

	public static void registerBiomes(IForgeRegistry<Biome> registry) 
	{
		for(PlantTopiaBaseBiome biome: BIOMES)
		{
			registerBiome(registry, biome, biome.getBiomeType(), biome.getBiomeDictionaryType());
		}
		
		COMMON_BIOMES.add(Biomes.PLAINS); 
	}
	
	private static Biome registerBiome(IForgeRegistry<Biome> registry, Biome biome, BiomeType biomeType, BiomeDictionary.Type... type) 
	{
		registry.register(biome);
		BiomeDictionary.addTypes(biome, type);
		return biome;
	}
}
