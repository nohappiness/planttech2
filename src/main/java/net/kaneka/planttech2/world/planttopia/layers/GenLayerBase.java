package net.kaneka.planttech2.world.planttopia.layers;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import net.kaneka.planttech2.registries.ModReferences;
import net.kaneka.planttech2.world.utils.BiomeHolder;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class GenLayerBase implements IAreaTransformer0
{
	//private static final int CHANCE_UNCOMMON_BIOMES = 10, CHANCE_RARE_BIOMES = 15;
	private List<Integer> COMMON, UNCOMMON, RARE;
	private int RARITY_UNCOMMON = 5, RARITY_RARE = 15;
	
	public GenLayerBase setup(HashMap<BiomeHolder.RARITY, List<Integer>> hmap) {
		COMMON = hmap.get(BiomeHolder.RARITY.COMMON);
		UNCOMMON = hmap.get(BiomeHolder.RARITY.UNCOMMON);
		RARE = hmap.get(BiomeHolder.RARITY.RARE);
		return this;
	}

	@Override
	public int applyPixel(INoiseRandom rand, int x_, int y) {
		if(rand.nextRandom(RARITY_RARE) == 0 && !RARE.isEmpty()) return randomBiome(rand, RARE);
		if(rand.nextRandom(RARITY_UNCOMMON) == 0 && !UNCOMMON.isEmpty()) return randomBiome(rand, UNCOMMON);
		return randomBiome(rand, COMMON);
	}

	private int randomBiome(INoiseRandom random, List<Integer> biomes) {
		return biomes.get(random.nextRandom(biomes.size()));
	}
}
