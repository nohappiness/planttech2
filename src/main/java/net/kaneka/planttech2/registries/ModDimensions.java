package net.kaneka.planttech2.registries;

import io.netty.buffer.Unpooled;
import net.kaneka.planttech2.dimensions.planttopia.PlantTopiaModDimension;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;

public class ModDimensions
{
    public static DimensionType planttopia_dimtype;
	public static final PlantTopiaModDimension PLANTTOPIA = new PlantTopiaModDimension();
	
	
	public static final void registerAll(RegistryEvent.Register<ModDimension> event)
	{
		planttopia_dimtype = DimensionManager.registerDimension(ModReferences.PLANTTOPIA_RESLOC, PLANTTOPIA, new PacketBuffer(Unpooled.buffer()), true);
		//DimensionManager.registerDimension(ModReferences.PLANTTOPIA_RESLOC, PLANTTOPIA, null, true);
		event.getRegistry().register(PLANTTOPIA);
	}
}
