package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.fluids.BiomassFluid;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.registries.IForgeRegistry;

public class ModFluids
{
	public static FlowingFluid BIOMASS = new BiomassFluid.Source() {{ setRegistryName("biomass"); }};
	public static FlowingFluid BIOMASS_FLOWING = new BiomassFluid.Flowing() {{ setRegistryName("biomass_flowing"); }};

	public static void register(IForgeRegistry<Fluid> registry)
    {
		registry.registerAll(
				BIOMASS,
				BIOMASS_FLOWING);
    }
}
