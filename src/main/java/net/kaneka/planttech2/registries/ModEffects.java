package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(PlantTechMain.MODID)
public class ModEffects
{
	//temporary disabled as PlantTopia is not implemented yet
//	@ObjectHolder("radiation_sickness") public static Effect RADIATION_SICKNESS;

	public static void registerAll(IForgeRegistry<MobEffect> registry)
	{
//		registry.register(new RadiationSickness().setRegistryName("radiation_sickness"));
	}
}
