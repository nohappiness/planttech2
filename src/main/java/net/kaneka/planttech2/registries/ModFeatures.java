package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.world.planttopia.features.NightmareForestSurfaceReplacer;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Random;

public class ModFeatures
{
    public static final Feature<ProbabilityConfig> NIGHTMARE_FOREST_SURFACE_REPLACEMENT_CONFIG = createFeatureConfig("nightmare_forest_surface_replacements", new NightmareForestSurfaceReplacer());
    public static final ConfiguredFeature<?, ?> NIGHTMARE_FOREST_SURFACE_REPLACEMENT = NIGHTMARE_FOREST_SURFACE_REPLACEMENT_CONFIG.configured(new ProbabilityConfig(0.2F)).decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE).squared().decorated(Placement.COUNT_NOISE_BIASED.configured(new TopSolidWithNoiseConfig(160, 80.0D, 0.3D)));

    private static <C extends IFeatureConfig, F extends Feature<C>> F createFeatureConfig(String name, F feature)
    {
        feature.setRegistryName("planttech2:" + name);
        return feature;
    }

    private static ConfiguredFeature<?, ?> createFeatureConfig(String name, ConfiguredFeature<?, ?> feature)
    {
        return feature;
    }

    public static void registerConfigs(IForgeRegistry<Feature<?>> registry)
    {
        registry.register(NIGHTMARE_FOREST_SURFACE_REPLACEMENT_CONFIG);

    }

    public static void registerFeatures(IForgeRegistry<Feature<?>> registry)
    {
    }
}
