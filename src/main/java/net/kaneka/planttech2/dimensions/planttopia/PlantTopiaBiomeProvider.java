package net.kaneka.planttech2.dimensions.planttopia;

import java.util.List;
import java.util.Set;
import java.util.function.LongFunction;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import net.kaneka.planttech2.dimensions.planttopia.layers.GenLayerBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public class PlantTopiaBiomeProvider extends BiomeProvider
{
	private final Layer genLayer;
	public static final List<Biome> SPAWNS = Lists.newArrayList(Biomes.PLAINS);
	private static final Set<Biome> BIOMES = ImmutableSet.of(Biomes.PLAINS, Biomes.BIRCH_FOREST, Biomes.DESERT);

	protected PlantTopiaBiomeProvider(long seed)
	{
		super(BIOMES);
		genLayer = genLayers(seed);

	}

	private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> genLayers(LongFunction<C> seed)
	{
		IAreaFactory<T> biomes = new GenLayerBiomes().apply(seed.apply(1L));
		biomes = ZoomLayer.FUZZY.apply(seed.apply(2000L), biomes);
		biomes = repeat(1000L, ZoomLayer.NORMAL, biomes, 6, seed);

		return biomes;
	}

	public static Layer genLayers(long seed)
	{
		IAreaFactory<LazyArea> areaFactory = genLayers((context) -> new LazyAreaLayerContext(25, seed, context));
		return new Layer(areaFactory);
	}

	public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> repeat(long seed, IAreaTransformer1 parent, IAreaFactory<T> p_202829_3_, int count,
	        LongFunction<C> contextFactory)
	{
		IAreaFactory<T> iareafactory = p_202829_3_;

		for (int i = 0; i < count; ++i)
		{
			iareafactory = parent.apply(contextFactory.apply(seed + (long) i), iareafactory);
		}

		return iareafactory;
	}

	@Override
	public List<Biome> getBiomesToSpawnIn()
	{
		return SPAWNS;
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z)
	{
		return this.genLayer.func_215738_a(x, z);
	}

}
