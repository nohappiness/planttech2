package net.kaneka.planttech2.registries;


import java.util.function.Supplier;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
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
		create(ModReferences.FLOWER_HILLS, BiomeMaker::theVoidBiome);
		create(ModReferences.FLOWER_MEADOWS, BiomeMaker::theVoidBiome);
		create(ModReferences.FLOWER_MOUNTAINS, BiomeMaker::theVoidBiome);
		create(ModReferences.ICY_CLIFFS, BiomeMaker::theVoidBiome);
		create(ModReferences.ICY_MEADOWS, BiomeMaker::theVoidBiome);
		create(ModReferences.LAKE, BiomeMaker::theVoidBiome);
		create(ModReferences.MEADOWS, BiomeMaker::theVoidBiome);
		create(ModReferences.MUSHROOM_FOREST, BiomeMaker::theVoidBiome);
		create(ModReferences.MUSHROOM_HILLS, BiomeMaker::theVoidBiome);
		create(ModReferences.NIGHTMARE_FOREST, BiomeMaker::theVoidBiome);
		create(ModReferences.PUMPKIN_FOREST, BiomeMaker::theVoidBiome);
		create(ModReferences.RADIATED_WASTELANDS, BiomeMaker::theVoidBiome);
		create(ModReferences.RIVER, BiomeMaker::theVoidBiome);
		create(ModReferences.VULCANO, BiomeMaker::theVoidBiome);
		create(ModReferences.WASTELAND_MESA, BiomeMaker::theVoidBiome);
		create(ModReferences.WETLANDS, BiomeMaker::theVoidBiome);
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
