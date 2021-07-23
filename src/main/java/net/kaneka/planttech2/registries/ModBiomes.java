package net.kaneka.planttech2.registries;


import com.mojang.datafixers.util.Pair;
import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBiomes
{
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, PlantTechMain.MODID);

	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> BEE_FOREST;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> CHORUS_FOREST;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> DARK_WETLANDS;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> DEAD_FOREST;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> DREAM_FOREST;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> DRIED_LAKE;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> ENERGIZED_FOREST;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> FLOWER_HILLS;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> FLOWER_MEADOWS;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> FLOWER_MOUNTAINS;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> ICY_CLIFFS;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> ICY_MEADOWS;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> LAKE;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> LLAMA_MEADOW;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> MEADOWS;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> MUSHROOM_FOREST;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> MUSHROOM_HILLS;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> NIGHTMARE_FOREST;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> PUMPKIN_FOREST;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> RADIATED_WETLANDS;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> RADIATED_WASTELANDS;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> RIVER;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> VULCANO;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> WASTELAND_MESA;
	public static Pair<ResourceKey<Biome>, RegistryObject<Biome>> WETLANDS;

	static
	{
		BEE_FOREST 			= create(ModReferences.BEE_FOREST, BiomeMaker::theVoidBiome);
		CHORUS_FOREST 		= create(ModReferences.CHORUS_FOREST, BiomeMaker::theVoidBiome);
		DARK_WETLANDS 		= create(ModReferences.DARK_WETLANDS, BiomeMaker::theVoidBiome);
		DEAD_FOREST 		= create(ModReferences.DEAD_FOREST, BiomeMaker::theVoidBiome);
		DREAM_FOREST 		= create(ModReferences.DREAM_FOREST, BiomeMaker::theVoidBiome);
		DRIED_LAKE 			= create(ModReferences.DRIED_LAKE, BiomeMaker::theVoidBiome);
		ENERGIZED_FOREST 	= create(ModReferences.ENERGIZED_FOREST, BiomeMaker::theVoidBiome);
		FLOWER_HILLS 		= create(ModReferences.FLOWER_HILLS, BiomeMaker::theVoidBiome);
		FLOWER_MEADOWS 		= create(ModReferences.FLOWER_MEADOWS, BiomeMaker::theVoidBiome);
		FLOWER_MOUNTAINS 	= create(ModReferences.FLOWER_MOUNTAINS, BiomeMaker::theVoidBiome);
		ICY_CLIFFS 			= create(ModReferences.ICY_CLIFFS, BiomeMaker::theVoidBiome);
		ICY_MEADOWS 		= create(ModReferences.ICY_MEADOWS, BiomeMaker::theVoidBiome);
		LAKE 				= create(ModReferences.LAKE, BiomeMaker::theVoidBiome);
		LLAMA_MEADOW		= create(ModReferences.LLAMA_MEADOW, BiomeMaker::theVoidBiome);
		MEADOWS 			= create(ModReferences.MEADOWS, BiomeMaker::theVoidBiome);
		MUSHROOM_FOREST		= create(ModReferences.MUSHROOM_FOREST, BiomeMaker::theVoidBiome);
		MUSHROOM_HILLS 		= create(ModReferences.MUSHROOM_HILLS, BiomeMaker::theVoidBiome);
		NIGHTMARE_FOREST 	= create(ModReferences.NIGHTMARE_FOREST, BiomeMaker::theVoidBiome);
		PUMPKIN_FOREST 		= create(ModReferences.PUMPKIN_FOREST, BiomeMaker::theVoidBiome);
		RADIATED_WETLANDS 	= create(ModReferences.RADIATED_WETLANDS, BiomeMaker::theVoidBiome);
		RADIATED_WASTELANDS = create(ModReferences.RADIATED_WASTELANDS, BiomeMaker::theVoidBiome);
		RIVER 				= create(ModReferences.RIVER, BiomeMaker::theVoidBiome);
		VULCANO 			= create(ModReferences.VULCANO, BiomeMaker::theVoidBiome);
		WASTELAND_MESA 		= create(ModReferences.WASTELAND_MESA, BiomeMaker::theVoidBiome);
		WETLANDS 			= create(ModReferences.WETLANDS, BiomeMaker::theVoidBiome);
	}

	private static Pair<ResourceKey<Biome>, RegistryObject<Biome>> create(String name, Supplier<Biome> biome)
	{
		return new Pair<>(getResourceKey(name), BIOMES.register(name, biome));
	}

	public static ResourceKey<Biome> getResourceKey(String name)
	{
		return ResourceKey.create(Registry.BIOME_REGISTRY, ModReferences.prefix(name));
	}
}
