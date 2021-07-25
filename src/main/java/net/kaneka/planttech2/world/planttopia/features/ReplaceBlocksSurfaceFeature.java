package net.kaneka.planttech2.world.planttopia.features;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

import java.util.List;

public class ReplaceBlocksSurfaceFeature extends Feature<ProbabilityFeatureConfiguration> {

    BlockState target;
    List<BlockState> weightedList;


    public ReplaceBlocksSurfaceFeature(BlockState target, List<BlockState> weightedList) {
        super(ProbabilityFeatureConfiguration.CODEC);
        this.target = target;
        this.weightedList = weightedList;
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> ctx) {
//        System.out.println(target.getBlock());
        WorldGenLevel level = ctx.level();
        BlockPos pos = ctx.origin();
        if(level.getBlockState(pos).is(Blocks.AIR) && level.getBlockState(pos.below()).is(target.getBlock())){
//            System.out.println("replace");
                level.setBlock(pos.below(), weightedList.get(ctx.random().nextInt(weightedList.size())), 2);
        }

        return true;
    }
}
