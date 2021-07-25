package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.world.planttopia.features.NightmareForestSurfaceReplacer;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraftforge.registries.IForgeRegistry;

public class ModFeatures
{
    public static final Feature<ProbabilityFeatureConfiguration> NIGHTMARE_FOREST_SURFACE_REPLACEMENT_CONFIG = createFeatureConfig("nightmare_forest_surface_replacements", new NightmareForestSurfaceReplacer());
   // public static final ConfiguredFeature<?, ?> NIGHTMARE_FOREST_SURFACE_REPLACEMENT = NIGHTMARE_FOREST_SURFACE_REPLACEMENT_CONFIG.configured(new ProbabilityFeatureConfiguration(0.2F)).decorated(Features.Decora.HEIGHTMAP_WORLD_SURFACE).squared().decorated(Placement.COUNT_NOISE_BIASED.configured(new TopSolidWithNoiseConfig(160, 80.0D, 0.3D)));

    private static <C extends FeatureConfiguration, F extends Feature<C>> F createFeatureConfig(String name, F feature)
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
