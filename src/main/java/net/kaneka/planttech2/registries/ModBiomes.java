package net.kaneka.planttech2.registries;


import java.util.function.Supplier;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes
{
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, PlantTechMain.MODID);
	
	static {
		create(ModReferences.BEE_FOREST, BiomeMaker::theVoidBiome);
		create(ModReferences.CHORUS_FOREST, BiomeMaker::theVoidBiome);
		create(ModReferences.DARK_WETLANDS, BiomeMaker::theVoidBiome);
		create(ModReferences.DEAD_FOREST, BiomeMaker::theVoidBiome);
		create(ModReferences.DREAM_FOREST, BiomeMaker::theVoidBiome);
		create(ModReferences.DRIED_LAKE, BiomeMaker::theVoidBiome);
		create(ModReferences.ENERGIZED_FOREST, BiomeMaker::theVoidBiome);
		create(ModReferences.FLOWER_HILLS, BiomeMaker::theVoidBiome);
		create(ModReferences.FLOWER_MEADOWS, BiomeMaker::theVoidBiome);
		create(ModReferences.FLOWER_MOUNTAINS, BiomeMaker::theVoidBiome);
		create(ModReferences.ICY_CLIFFS, BiomeMaker::theVoidBiome);
		create(ModReferences.ICY_MEADOWS, BiomeMaker::theVoidBiome);
		create(ModReferences.LAKE, BiomeMaker::theVoidBiome);
		create(ModReferences.LLAMA_MEADOW, BiomeMaker::theVoidBiome);
		create(ModReferences.MEADOWS, BiomeMaker::theVoidBiome);
		create(ModReferences.MUSHROOM_FOREST, BiomeMaker::theVoidBiome);
		create(ModReferences.MUSHROOM_HILLS, BiomeMaker::theVoidBiome);
		create(ModReferences.NIGHTMARE_FOREST, ModBiomes::theTestBiome);
		create(ModReferences.PUMPKIN_FOREST, BiomeMaker::theVoidBiome);
		create(ModReferences.RADIATED_WETLANDS, BiomeMaker::theVoidBiome);
		create(ModReferences.RADIATED_WASTELANDS, BiomeMaker::theVoidBiome);
		create(ModReferences.RIVER, BiomeMaker::theVoidBiome);
		create(ModReferences.VULCANO, BiomeMaker::theVoidBiome);
		create(ModReferences.WASTELAND_MESA, BiomeMaker::theVoidBiome);
		create(ModReferences.WETLANDS, BiomeMaker::theVoidBiome);
	}

	public static Biome theTestBiome()
	{
		BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.NOPE);
		biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Features.VOID_START_PLATFORM);
		biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.TEST);
		return (new Biome.Builder()).precipitation(Biome.RainType.NONE).biomeCategory(Biome.Category.NONE).depth(0.1F).scale(0.2F).temperature(0.5F).downfall(0.5F).specialEffects((new BiomeAmbience.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.5F)).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(MobSpawnInfo.EMPTY).generationSettings(biomegenerationsettings$builder.build()).build();
	}

	private static int calculateSkyColor(float p_244206_0_)
	{
		float lvt_1_1_ = p_244206_0_ / 3.0F;
		lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
		return MathHelper.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
	}

	private static void create(String name, Supplier<Biome> biome) 
	{
		BIOMES.register(name, biome);
		getRegistryKey(name);
	}
	
	public static RegistryKey<Biome> getRegistryKey(String name)
	{
		return RegistryKey.create(Registry.BIOME_REGISTRY, ModReferences.prefix(name));
	}
}
