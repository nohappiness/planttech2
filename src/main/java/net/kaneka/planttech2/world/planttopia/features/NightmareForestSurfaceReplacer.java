package net.kaneka.planttech2.world.planttopia.features;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class NightmareForestSurfaceReplacer extends ReplaceBlocksSurfaceFeature {
    public NightmareForestSurfaceReplacer() {
        super(Blocks.CRIMSON_NYLIUM.defaultBlockState(), new ArrayList<BlockState>(){{
            add(Blocks.WARPED_NYLIUM.defaultBlockState());
            add(Blocks.WARPED_NYLIUM.defaultBlockState());
            add(Blocks.WARPED_NYLIUM.defaultBlockState());
            add(Blocks.SOUL_SAND.defaultBlockState());
        }});
    }
}
