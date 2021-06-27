package net.kaneka.planttech2.registries;


import java.util.HashMap;
import java.util.function.Supplier;

import javafx.util.Pair;
import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class ModBiomes
{
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, PlantTechMain.MODID);
	private static final HashMap<RegistryObject<Biome>, Supplier<SoundEvent>> MUSICS = new HashMap<>();

	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> BEE_FOREST;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> CHORUS_FOREST;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> DARK_WETLANDS;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> DEAD_FOREST;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> DREAM_FOREST;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> DRIED_LAKE;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> ENERGIZED_FOREST;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> FLOWER_HILLS;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> FLOWER_MEADOWS;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> FLOWER_MOUNTAINS;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> ICY_CLIFFS;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> ICY_MEADOWS;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> LAKE;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> LLAMA_MEADOW;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> MEADOWS;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> MUSHROOM_FOREST;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> MUSHROOM_HILLS;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> NIGHTMARE_FOREST;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> PUMPKIN_FOREST;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> RADIATED_WETLANDS;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> RADIATED_WASTELANDS;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> RIVER;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> VULCANO;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> WASTELAND_MESA;
	public static Pair<RegistryKey<Biome>, RegistryObject<Biome>> WETLANDS;

	static
	{
		BEE_FOREST 			= create(ModReferences.BEE_FOREST, BiomeMaker::theVoidBiome);
		CHORUS_FOREST 		= create(ModReferences.CHORUS_FOREST, BiomeMaker::theVoidBiome);
		DARK_WETLANDS 		= createWithIntensiveMusic(ModReferences.DARK_WETLANDS, BiomeMaker::theVoidBiome);
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
		RADIATED_WETLANDS 	= createWithIntensiveMusic(ModReferences.RADIATED_WETLANDS, BiomeMaker::theVoidBiome);
		RADIATED_WASTELANDS = create(ModReferences.RADIATED_WASTELANDS, BiomeMaker::theVoidBiome);
		RIVER 				= create(ModReferences.RIVER, BiomeMaker::theVoidBiome);
		VULCANO 			= createWithIntensiveMusic(ModReferences.VULCANO, BiomeMaker::theVoidBiome);
		WASTELAND_MESA 		= createWithIntensiveMusic(ModReferences.WASTELAND_MESA, BiomeMaker::theVoidBiome);
		WETLANDS 			= create(ModReferences.WETLANDS, BiomeMaker::theVoidBiome);
	}

	private static Pair<RegistryKey<Biome>, RegistryObject<Biome>> createWithIntensiveMusic(String name, Supplier<Biome> biome)
	{
		return createWithMusic(name, biome, () -> ModSounds.MUSIC_ADVENTURE);
	}

	private static Pair<RegistryKey<Biome>, RegistryObject<Biome>> createWithMusic(String name, Supplier<Biome> biome, Supplier<SoundEvent> sound)
	{
		RegistryObject<Biome> object = BIOMES.register(name, biome);
		MUSICS.put(object, sound);
		return new Pair<>(getRegistryKey(name), object);
	}

	private static Pair<RegistryKey<Biome>, RegistryObject<Biome>> create(String name, Supplier<Biome> biome)
	{
		return new Pair<>(getRegistryKey(name), BIOMES.register(name, biome));
	}

	public static RegistryKey<Biome> getRegistryKey(String name)
	{
		return RegistryKey.create(Registry.BIOME_REGISTRY, ModReferences.prefix(name));
	}

	@Nullable
	public static SoundEvent getMusicFor(Biome biome)
	{
		for (RegistryObject<Biome> object : MUSICS.keySet())
		{
			if (object.get() == biome)
				return MUSICS.get(object).get();
		}
		return null;
	}
}
