package net.kaneka.planttech2.world.planttopia.layers;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.kaneka.planttech2.registries.ModReferences;
import net.kaneka.planttech2.world.planttopia.PlanttopiaBiomeProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class GenLayerBiomes implements IAreaTransformer0
{
	//private static final int CHANCE_UNCOMMON_BIOMES = 10, CHANCE_RARE_BIOMES = 15; 

	private Registry<Biome> registry;
	
	protected static final List<RegistryKey<Biome>> biomes = ImmutableList.of(
			RegistryKey.create(Registry.BIOME_REGISTRY, ModReferences.prefix(ModReferences.FLOWER_MEADOW)), 
			RegistryKey.create(Registry.BIOME_REGISTRY, ModReferences.prefix(ModReferences.RADIATED_WASTELAND)),
			RegistryKey.create(Registry.BIOME_REGISTRY, ModReferences.prefix(ModReferences.LAKE)),
			RegistryKey.create(Registry.BIOME_REGISTRY, ModReferences.prefix(ModReferences.DRIED_LAKE))
	);
	
	public GenLayerBiomes setup(Registry<Biome> registry) {
		this.registry = registry;
		return this;
	}
	@Override
	public int applyPixel(INoiseRandom rand, int x_, int y)
	{
		//TODO different rarities of biomes. s
		return getRandomBiome(rand, biomes);
	}

	
	private int getRandomBiome(INoiseRandom random, List<RegistryKey<Biome>> biomes) 
	{
		return PlanttopiaBiomeProvider.getBiomeId(biomes.get(random.nextRandom(biomes.size())), registry);
	}
}
