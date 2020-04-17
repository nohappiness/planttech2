package net.kaneka.planttech2.dimensions.planttopia.layers;

import java.util.List;

import net.kaneka.planttech2.registries.ModBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class GenLayerBiomes implements IAreaTransformer0
{
	private static final int CHANCE_UNCOMMON_BIOMES = 10, CHANCE_RARE_BIOMES = 15; 

	@Override
	public int apply(INoiseRandom rand, int x_, int y)
	{
		if(rand.random(CHANCE_UNCOMMON_BIOMES) == 0 && !ModBiomes.UNCOMMON_BIOMES.isEmpty())
		{
			return Registry.BIOME.getId(getRandomBiome(rand, ModBiomes.UNCOMMON_BIOMES));
		}
		else if(rand.random(CHANCE_RARE_BIOMES) == 0 && !ModBiomes.RARE_BIOMES.isEmpty())
		{
			return Registry.BIOME.getId(getRandomBiome(rand, ModBiomes.RARE_BIOMES));
		}
		else
		{
			return Registry.BIOME.getId(getRandomBiome(rand, ModBiomes.COMMON_BIOMES));
		}
		
	}

	
	private Biome getRandomBiome(INoiseRandom rand, List<Biome> list)
	{
		return list.get(rand.random(list.size()));
	}
}
