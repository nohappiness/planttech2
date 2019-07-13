package net.kaneka.planttech2.world.planttopia.biomes;

import net.kaneka.planttech2.world.planttopia.PlantTopiaBiomeFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class PlantTopiaRiverBiome extends Biome {

	public PlantTopiaRiverBiome() {
		super((new Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, PlantTopiaBiomeFeatures.GRASS_DIRT_DIRT_SURFACE).precipitation(Biome.RainType.RAIN).category(Biome.Category.RIVER).depth(-0.5F).scale(0.0F).temperature(0.5F).downfall(0.5F).waterColor(-10263708).waterFogColor(329011).parent((String)null));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createDecoratedFeature(Feature.SEAGRASS, new SeaGrassConfig(48, 0.4D), Placement.TOP_SOLID_HEIGHTMAP, IPlacementConfig.NO_PLACEMENT_CONFIG));
		addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 2, 1, 4));
		addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.TROPICAL_FISH, 5, 1, 5));
	}
	
	@Override
	public int getGrassColor(BlockPos pos) {
		return Integer.parseInt("053133", 16);
	}
	
	@Override
	public int getFoliageColor(BlockPos pos) {
		return Integer.parseInt("053933", 16);
	}
	
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return Integer.parseInt("260533", 16);
	}

}
