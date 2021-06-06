package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.world.planttopia.PlanttopiaBiomeProvider;
import net.kaneka.planttech2.world.planttopia.PlanttopiaChunkGenerator;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class ModDimensions
{
	public static long seed = 3818919209114L;
	public static final RegistryKey<DimensionType> PLANTTOPIA_DIMENSIONTYPE = RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY, ModReferences.prefix("planttopia"));
	public static final RegistryKey<Dimension> PLANTTOPIA_DIMENSION = RegistryKey.create(Registry.LEVEL_STEM_REGISTRY, ModReferences.prefix("planttopia"));
	public static final RegistryKey<World> PLANTTOPIA_WORLD = RegistryKey.create(Registry.DIMENSION_REGISTRY, ModReferences.prefix("planttopia"));
	
	public static void initDimensions()
	{
		Registry.register(Registry.CHUNK_GENERATOR, ModReferences.prefix("chunk_generator_planttopia"), PlanttopiaChunkGenerator.CODEC);
        Registry.register(Registry.BIOME_SOURCE, ModReferences.prefix("biome_provider_planttopia"), PlanttopiaBiomeProvider.CODEC);
	}
	
	
	/*
	private static final ModDimension PLANTTOPIA = new ModDimension()
	{
		@Override
		public BiFunction<World, DimensionType, ? extends Dimension> getFactory()
		{
			return PlantTopiaDimension::new;
		}
	};


	public static void initDimensions(IForgeRegistry<ModDimension> registry)
	{
		PLANTTOPIA.setRegistryName(ModReferences.PLANTTOPIA_RESLOC);
		registry.register(PLANTTOPIA);
		DimensionManager.registerDimension(ModReferences.PLANTTOPIA_RESLOC, PLANTTOPIA, new PacketBuffer(Unpooled.buffer()), true);
	}




	public static final void registerAll()
	{
		if(DimensionType.byName(ModReferences.PLANTTOPIA_RESLOC) == null)
		{
			DimensionManager.registerDimension(ModReferences.PLANTTOPIA_RESLOC, PLANTTOPIA, new PacketBuffer(Unpooled.buffer()), true);
		}
	}

	public static DimensionType getPlantTopiaDimensionType()
	{
		return DimensionType.byName(ModReferences.PLANTTOPIA_RESLOC);
	}
	*/
}
