package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.world.structure.tech.TechVillageConfig;
import net.kaneka.planttech2.world.structure.tech.TechVillageStructure;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent.Register;

public class ModStructures
{
	public static final Structure<TechVillageConfig> TECHVILLAGE = new TechVillageStructure(TechVillageConfig::deserialize);

	public static void registerAll(Register<Feature<?>> event)
	{
		event.getRegistry().register(TECHVILLAGE.setRegistryName(ModReferences.TECHVILLAGE));
		Feature.STRUCTURES.put("Tech Village", TECHVILLAGE); 
		Biomes.PLAINS.addStructure(TECHVILLAGE, new TechVillageConfig(5));
	}
}
