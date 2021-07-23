package net.kaneka.planttech2.world.planttopia.layers;

import net.kaneka.planttech2.world.utils.BiomeHolder;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer0;

import java.util.HashMap;
import java.util.List;

public class GenLayerBase implements AreaTransformer0
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
	public int applyPixel(Context rand, int x_, int y) {
		if(rand.nextRandom(RARITY_RARE) == 0 && !RARE.isEmpty()) return randomBiome(rand, RARE);
		if(rand.nextRandom(RARITY_UNCOMMON) == 0 && !UNCOMMON.isEmpty()) return randomBiome(rand, UNCOMMON);
		return randomBiome(rand, COMMON);
	}

	private int randomBiome(Context random, List<Integer> biomes) {
		return biomes.get(random.nextRandom(biomes.size()));
	}
}
