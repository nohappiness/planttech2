package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.world.planttopia.PlanttopiaBiomeProvider;
import net.kaneka.planttech2.world.planttopia.PlanttopiaChunkGenerator;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;


public class ModDimensions
{
	public static long seed = 3818919209114L;
	public static final ResourceKey<DimensionType> PLANTTOPIA_DIMENSIONTYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, ModReferences.prefix("planttopia"));
	//public static final ResourceKey<Dimension> PLANTTOPIA_DIMENSION = ResourceKey.create(Registry.DIMENSION_REGISTRY, ModReferences.prefix("planttopia"));
	public static final ResourceKey<Level> PLANTTOPIA_WORLD = ResourceKey.create(Registry.DIMENSION_REGISTRY, ModReferences.prefix("planttopia"));
	
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
