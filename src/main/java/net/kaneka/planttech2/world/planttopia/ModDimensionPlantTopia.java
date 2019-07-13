package net.kaneka.planttech2.world.planttopia;

import java.util.function.BiFunction;

import net.kaneka.planttech2.registries.ModReferences;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

public class ModDimensionPlantTopia extends ModDimension
{

	private final static ResourceLocation REGISTRYNAME = new ResourceLocation(ModReferences.PLANTTOPIA);

	public ModDimensionPlantTopia()
	{
		this.setRegistryName(REGISTRYNAME);
	}

	public static DimensionType getDimensionType()
	{
		return DimensionType.byName(REGISTRYNAME);
	}

	@Override
	public BiFunction<World, DimensionType, ? extends Dimension> getFactory()
	{
		return DimensionPlantTopia::new;
	}
}