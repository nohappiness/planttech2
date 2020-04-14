package net.kaneka.planttech2.registries;

import java.util.function.BiFunction;

import io.netty.buffer.Unpooled;
import net.kaneka.planttech2.dimensions.planttopia.PlantTopiaDimension;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.registries.IForgeRegistry;

public class ModDimensions
{
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
}
