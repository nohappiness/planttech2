package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.effects.RadiationSickness;
import net.minecraft.potion.Effect;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(PlantTechMain.MODID)
public class ModEffects
{
	@ObjectHolder("radiation_sickness") public static Effect RADIATION_SICKNESS;

	public static void registerAll(IForgeRegistry<Effect> registry)
	{
		registry.register(new RadiationSickness().setRegistryName("radiation_sickness"));
	}
}
