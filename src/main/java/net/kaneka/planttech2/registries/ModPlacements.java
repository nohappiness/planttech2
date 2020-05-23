package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.dimensions.placements.LakeBiomass;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.IForgeRegistry;

public class ModPlacements
{
	public static final Placement<ChanceConfig> BIOMASS_LAKE = new LakeBiomass(ChanceConfig::deserialize);
	
	public static void registerDimensions(IForgeRegistry<Placement<?>> registry)
	{
		BIOMASS_LAKE.setRegistryName("planttech2:biomass_lake");
		registry.registerAll(BIOMASS_LAKE);
	}
}
