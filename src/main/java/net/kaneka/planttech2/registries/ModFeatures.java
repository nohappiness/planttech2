package net.kaneka.planttech2.registries;

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
    public static final Feature<ProbabilityConfig> TEST_CONFIG = createFeatureConfig("test", new TestFeature());
    public static final ConfiguredFeature<?, ?> TEST = TEST_CONFIG.configured(new ProbabilityConfig(0.2F)).decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE).squared().decorated(Placement.COUNT_NOISE_BIASED.configured(new TopSolidWithNoiseConfig(160, 80.0D, 0.3D)));

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
        registry.register(TEST_CONFIG);

    }

    public static void registerFeatures(IForgeRegistry<Feature<?>> registry)
    {
    }

    static class TestFeature extends Feature<ProbabilityConfig>
    {
        public TestFeature()
        {
            super(ProbabilityConfig.CODEC);
        }

        @Override
        public boolean place(ISeedReader seedReader, ChunkGenerator generator, Random rand, BlockPos pos, ProbabilityConfig config)
        {
            if (seedReader.isEmptyBlock(pos))
            {
                int radius = 3;
                for (int x = pos.getX() - radius; x <= pos.getX() + radius; ++x)
                    for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; ++z)
                        seedReader.setBlock(new BlockPos(x, seedReader.getHeight(Heightmap.Type.WORLD_SURFACE, x, z), z), Blocks.CRIMSON_NYLIUM.defaultBlockState(), 2);
                return true;
            }
            return false;
        }
    }
}
