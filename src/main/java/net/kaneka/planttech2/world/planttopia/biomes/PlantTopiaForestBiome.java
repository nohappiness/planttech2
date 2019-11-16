package net.kaneka.planttech2.world.planttopia.biomes;

import net.kaneka.planttech2.world.planttopia.PlantTopiaBiomeFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class PlantTopiaForestBiome extends Biome {

	public PlantTopiaForestBiome() {
		super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, PlantTopiaBiomeFeatures.GRASS_DIRT_DIRT_SURFACE).precipitation(Biome.RainType.NONE).category(Biome.Category.FOREST).depth(0.125F).scale(0.05F).temperature(0.8F).downfall(0.4F).waterColor(10263708).waterFogColor(329011).parent((String)null));
		this.addStructure(Feature.VILLAGE, new VillageConfig("village/plains/town_centers", 6));
		this.addStructure(Feature.PILLAGER_OUTPOST, new PillagerOutpostConfig(0.004D));
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addStructures(this);
		DefaultBiomeFeatures.addLakes(this);
		DefaultBiomeFeatures.func_222283_Y(this);
		DefaultBiomeFeatures.addStoneVariants(this);
		DefaultBiomeFeatures.addOres(this);
		DefaultBiomeFeatures.addSedimentDisks(this);
		DefaultBiomeFeatures.func_222299_R(this);
		DefaultBiomeFeatures.func_222323_C(this);
		DefaultBiomeFeatures.addTaigaConifers(this);
		DefaultBiomeFeatures.addForestTrees(this);
		DefaultBiomeFeatures.func_222290_D(this);
	    DefaultBiomeFeatures.addDefaultFlowers(this);
	    DefaultBiomeFeatures.addGrass(this);
	    DefaultBiomeFeatures.addMushrooms(this);
		DefaultBiomeFeatures.addReedsAndPumpkins(this);
		DefaultBiomeFeatures.addSprings(this);
		DefaultBiomeFeatures.addFreezeTopLayer(this);
		this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
		this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
	}

	@Override
	public int getGrassColor(BlockPos pos) {
		return Integer.parseInt("054133", 16);
	}
	
	@Override
	public int getFoliageColor(BlockPos pos) {
		return Integer.parseInt("054933", 16);
	}
	
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return Integer.parseInt("260533", 16);
	}
}