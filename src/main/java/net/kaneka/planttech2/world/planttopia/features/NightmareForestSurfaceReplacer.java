package net.kaneka.planttech2.world.planttopia.features;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

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