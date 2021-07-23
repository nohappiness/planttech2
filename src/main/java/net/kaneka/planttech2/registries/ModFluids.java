package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.fluids.BiomassFluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(PlantTechMain.MODID)
public class ModFluids
{
	@ObjectHolder("biomass") public static FlowingFluid BIOMASS;
	@ObjectHolder("biomass_flowing") public static FlowingFluid BIOMASS_FLOWING;

	public static void register(IForgeRegistry<Fluid> registry)
	{
		registry.register(new BiomassFluid.Source().setRegistryName("biomass"));
		registry.register(new BiomassFluid.Flowing().setRegistryName("biomass_flowing"));
	}
}
