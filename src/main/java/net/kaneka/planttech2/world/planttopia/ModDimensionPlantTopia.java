package net.kaneka.planttech2.world.planttopia;

import java.util.function.BiFunction;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

public class ModDimensionPlantTopia extends ModDimension {

	public ModDimensionPlantTopia(final ResourceLocation registryName) {
		this.setRegistryName(registryName);
	}
	
	public static DimensionType getDimensionType() {
		return PlantTechMain.PLANTTOPIA;
	}

	@Override
	public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
		return DimensionPlantTopia::new;
	}
}