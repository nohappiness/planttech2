package net.kaneka.planttech2.world.planttopia;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModDimensions;
import net.kaneka.planttech2.world.planttopia.layers.GenLayerUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.newbiome.layer.Layer;

import java.util.Map;
import java.util.stream.Collectors;

public class PlanttopiaBiomeProvider extends BiomeSource
{
	private final Layer genLayer;
	
	public static final Codec<PlanttopiaBiomeProvider> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Codec.LONG.fieldOf("seed").stable().orElseGet(() -> ModDimensions.seed).forGetter((obj) -> obj.seed),
			RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter(provider -> provider.biomeReg)
	).apply(instance, instance.stable(PlanttopiaBiomeProvider::new)));
	
	private final Registry<Biome> biomeReg;
	private final long seed; 
	
	public PlanttopiaBiomeProvider(Registry<Biome> registryIn)
	{
		this(0L, registryIn);
	}
	
	public PlanttopiaBiomeProvider(long seed, Registry<Biome> registryIn)
	{
		super(registryIn.entrySet().stream()
                .filter(entry -> entry.getKey().location().getNamespace().equals(PlantTechMain.MODID))
                .map(Map.Entry::getValue).collect(Collectors.toList()));
		genLayer = GenLayerUtils.genLayers(seed, registryIn);
		this.biomeReg = registryIn;
		this.seed = seed; 
	}
	


	@Override
	public Biome getNoiseBiome(int x, int y, int z)
	{
		return this.genLayer.get(biomeReg, x, z);
	}

	@Override
	protected Codec<? extends BiomeSource> codec()
	{
		return CODEC;
	}

	@Override
	public BiomeSource withSeed(long seed)
	{
		return new PlanttopiaBiomeProvider(seed, biomeReg);
	}
}
