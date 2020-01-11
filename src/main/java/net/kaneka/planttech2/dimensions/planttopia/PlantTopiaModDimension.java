package net.kaneka.planttech2.dimensions.planttopia;

import java.util.function.BiFunction;

import net.kaneka.planttech2.registries.ModReferences;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

public class PlantTopiaModDimension extends ModDimension
{
	public PlantTopiaModDimension()
	{
		this.setRegistryName(ModReferences.PLANTTOPIA_RESLOC);
	}

	public static DimensionType getDimensionType()
	{
		return DimensionType.byName(ModReferences.PLANTTOPIA_RESLOC);
	}

	@Override
	public BiFunction<World, DimensionType, ? extends Dimension> getFactory()
	{
		return PlantTopiaDimension::new;
	}

}
