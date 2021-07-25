package net.kaneka.planttech2.world.planttopia;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kaneka.planttech2.registries.ModDimensions;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.function.Supplier;

public class PlanttopiaChunkGenerator extends NoiseBasedChunkGenerator
{
	public static final Codec<PlanttopiaChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BiomeSource.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource),
			Codec.LONG.fieldOf("seed").stable().orElseGet(() -> ModDimensions.seed).forGetter((chunkGen) -> chunkGen.getSeed()),
			NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter((chunkGen) -> chunkGen.getDimensionSettings())
	).apply(instance, instance.stable(PlanttopiaChunkGenerator::new)));

	private long seed;
	

	public PlanttopiaChunkGenerator(BiomeSource biomeProvider, long seed, Supplier<NoiseGeneratorSettings> settings)
	{
		super(biomeProvider, seed, settings);
		this.seed = seed;
	}
	
	@Override
	protected Codec<? extends ChunkGenerator> codec()
	{
		return CODEC;
	}
	
	private Supplier<NoiseGeneratorSettings> getDimensionSettings()
	{
		return this.settings;
	}
	
	private long getSeed()
	{
		return this.seed; 
	}

}
