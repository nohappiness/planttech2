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
		 create(ModReferences.FLOWER_MEADOW, BiomeMaker::makeVoidBiome);
		 create(ModReferences.RADIATED_WASTELAND, BiomeMaker::makeVoidBiome);
		 create(ModReferences.DRIED_LAKE, BiomeMaker::makeVoidBiome);
		 create(ModReferences.LAKE, BiomeMaker::makeVoidBiome); 
	}
	
	private static void create(String name, Supplier<Biome> biome) 
	{
		BIOMES.register(name, biome);
		getRegistryKey(name);
	}
	
	public static RegistryKey<Biome> getRegistryKey(String name)
	{
		return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, ModReferences.prefix(name));
	}
}
