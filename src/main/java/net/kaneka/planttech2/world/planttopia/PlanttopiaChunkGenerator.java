package net.kaneka.planttech2.world.planttopia;

import java.util.function.Supplier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.kaneka.planttech2.registries.ModDimensions;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;

public class PlanttopiaChunkGenerator extends NoiseChunkGenerator
{
	public static final Codec<PlanttopiaChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BiomeProvider.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource),
			Codec.LONG.fieldOf("seed").stable().orElseGet(() -> ModDimensions.seed).forGetter((chunkGen) -> chunkGen.getSeed()),
			DimensionSettings.CODEC.fieldOf("settings").forGetter((chunkGen) -> chunkGen.getDimensionSettings())
	).apply(instance, instance.stable(PlanttopiaChunkGenerator::new)));
	

	public PlanttopiaChunkGenerator(BiomeProvider biomeProvider, long seed, Supplier<DimensionSettings> settings)
	{
		super(biomeProvider, seed, settings);
	}
	
	@Override
	protected Codec<? extends ChunkGenerator> codec() 
	{
		return CODEC;
	}
	
	private Supplier<DimensionSettings> getDimensionSettings() 
	{
		return this.settings;
	}
	
	private long getSeed()
	{
		return this.seed; 
	}

}