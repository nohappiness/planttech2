package net.kaneka.planttech2.world.planttopia.features;

import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class NightmareForestSurfaceReplacer extends ReplaceBlocksSurfaceFeature {
    public NightmareForestSurfaceReplacer() {
        super(Blocks.CRIMSON_NYLIUM.defaultBlockState(),
                List.of(Blocks.WARPED_NYLIUM.defaultBlockState()
            , Blocks.WARPED_NYLIUM.defaultBlockState()
            , Blocks.WARPED_NYLIUM.defaultBlockState()
            , Blocks.SOUL_SAND.defaultBlockState()));
    }
}
