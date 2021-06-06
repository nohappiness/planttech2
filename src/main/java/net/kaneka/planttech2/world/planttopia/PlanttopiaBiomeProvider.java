package net.kaneka.planttech2.world.planttopia;


import java.util.Map;
import java.util.stream.Collectors;
import java.util.function.LongFunction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModDimensions;
import net.kaneka.planttech2.world.planttopia.layers.GenLayerBiomes;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public class PlanttopiaBiomeProvider extends BiomeProvider
{
	private final Layer genLayer;
	
	public static final Codec<PlanttopiaBiomeProvider> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Codec.LONG.fieldOf("seed").stable().orElseGet(() -> ModDimensions.seed).forGetter((obj) -> obj.seed),
			RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter(provider -> provider.biomeReg)
	).apply(instance, instance.stable(PlanttopiaBiomeProvider::new)));
	
	private final Registry<Biome> biomeReg;
	private final long seed; 
	
	public PlanttopiaBiomeProvider(Registry<Biome> registryIn)
	{
		this(0L, registryIn);
	}
	
	public PlanttopiaBiomeProvider(long seed, Registry<Biome> registryIn)
	{
		super(registryIn.getEntries().stream()
                .filter(entry -> entry.getKey().getLocation().getNamespace().equals(PlantTechMain.MODID))
                .map(Map.Entry::getValue).collect(Collectors.toList()));
		genLayer = genLayers(seed, registryIn);
		this.biomeReg = registryIn;
		this.seed = seed; 
	}
	
	public static int getBiomeId(RegistryKey<Biome> biome, Registry<Biome> registry) 
	{
		return registry.getId(registry.getValueForKey(biome));
	}

	private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> genLayers(LongFunction<C> seed, Registry<Biome> registry)
	{
		IAreaFactory<T> biomes = new GenLayerBiomes().setup(registry).apply(seed.apply(1L));
		biomes = ZoomLayer.FUZZY.apply(seed.apply(2000L), biomes);
		biomes = repeat(1000L, ZoomLayer.NORMAL, biomes, 6, seed);

		return biomes;
	}

	public static Layer genLayers(long seed, Registry<Biome> registry)
	{
		IAreaFactory<LazyArea> areaFactory = genLayers((context) -> new LazyAreaLayerContext(25, seed, context), registry);
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
	public Biome getNoiseBiome(int x, int y, int z)
	{
		return this.genLayer.func_242936_a(biomeReg, x, z);
	}

	@Override
	protected Codec<? extends BiomeProvider> getBiomeProviderCodec()
	{
		return CODEC;
	}

	@Override
	public BiomeProvider getBiomeProvider(long seed)
	{
		return new PlanttopiaBiomeProvider(seed, biomeReg);
	}
}
