package net.kaneka.planttech2.dimensions.planttopia.biomes;

import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModPlacements;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;

public class PlantTopiaBiomeDecorator
{
	static OreFeatureConfig.FillerBlockType DIRT_GRASS = OreFeatureConfig.FillerBlockType.create("planttech2:dirt_grass", "planttech2:dirt_grass", (p_214739_0_) -> {
         if (p_214739_0_ == null) {
            return false;
         } else {
            Block block = p_214739_0_.getBlock();
            return block == Blocks.DIRT || block == Blocks.GRASS_BLOCK;
         }});
	
	public static void addCrystalOres(Biome biome)
	{
		biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DIRT_GRASS, ModBlocks.DARK_CRYSTAL_ORE.getDefaultState(), 25)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));
		biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DIRT_GRASS, ModBlocks.WHITE_CRYSTAL_ORE.getDefaultState(), 25)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));
	}
	
	public static void addBiomassLake(Biome biome)
	{
		biome.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(ModBlocks.BIOMASSFLUIDBLOCK.getDefaultState())).withPlacement(ModPlacements.BIOMASS_LAKE.configure(new ChanceConfig(20))));
	}
}
