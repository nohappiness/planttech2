package net.kaneka.planttech2.world.planttopia.layers;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import net.kaneka.planttech2.registries.ModReferences;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class GenLayerBase implements IAreaTransformer0
{
	//private static final int CHANCE_UNCOMMON_BIOMES = 10, CHANCE_RARE_BIOMES = 15; 
	private Registry<Biome> registry;
	private List<RegistryKey<Biome>> COMMON, UNCOMMON, RARE;
	private int RARITY_UNCOMMON = 5, RARITY_RARE = 15;
	
	public GenLayerBase setup(Registry<Biome> registry) {
		COMMON = GenLayerUtils.byFlags(BIOMEFLAGS.COMMON, BIOMEFLAGS.BASE);
		UNCOMMON = GenLayerUtils.byFlags(BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.BASE);
		RARE = GenLayerUtils.byFlags(BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.BASE);
		this.registry = registry;
		return this;
	}

	@Override
	public int applyPixel(INoiseRandom rand, int x_, int y) {
		if(rand.nextRandom(RARITY_RARE) == 0) return randomBiome(rand, RARE);
		if(rand.nextRandom(RARITY_UNCOMMON) == 0) return randomBiome(rand, UNCOMMON);
		return randomBiome(rand, COMMON);
	}

	private int randomBiome(INoiseRandom random, List<RegistryKey<Biome>> biomes) {
		return GenLayerUtils.getBiomeId(biomes.get(random.nextRandom(biomes.size())), registry);
	}
}
