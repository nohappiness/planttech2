package net.kaneka.planttech2.world.planttopia;


import java.util.Map;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kaneka.planttech2.world.planttopia.layers.GenLayerUtils;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModDimensions;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;

public class PlanttopiaBiomeProvider extends BiomeProvider
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
	protected Codec<? extends BiomeProvider> codec()
	{
		return CODEC;
	}

	@Override
	public BiomeProvider withSeed(long seed)
	{
		return new PlanttopiaBiomeProvider(seed, biomeReg);
	}
}
