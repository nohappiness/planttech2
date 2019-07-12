package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.world.planttopia.ModDimensionPlantTopia;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(PlantTechMain.MODID)
public class ModDimensions {
	public static final ModDimensionPlantTopia PLANTTOPIA = new ModDimensionPlantTopia(new ResourceLocation(PlantTechMain.MODID, "planttopia"));
}