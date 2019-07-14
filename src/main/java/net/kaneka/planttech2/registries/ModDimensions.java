package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.world.planttopia.ModDimensionPlantTopia;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;

public class ModDimensions 
{
	public static final ModDimensionPlantTopia PLANTTOPIA = new ModDimensionPlantTopia();
	
	
	public static final void registerAll(RegistryEvent.Register<ModDimension> event)
	{
		event.getRegistry().registerAll(PLANTTOPIA);
		DimensionManager.registerDimension(new ResourceLocation(ModReferences.PLANTTOPIA), PLANTTOPIA, null, true);
	}
}