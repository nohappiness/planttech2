package net.kaneka.planttech2.world.planttopia.features;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.List;
import java.util.Random;

public class ReplaceBlocksSurfaceFeature extends Feature<ProbabilityConfig> {

    BlockState target;
    List<BlockState> weightedList;


    public ReplaceBlocksSurfaceFeature(BlockState target, List<BlockState> weightedList) {
        super(ProbabilityConfig.CODEC);
        this.target = target;
        this.weightedList = weightedList;
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator chunkGenerator, Random rand, BlockPos pos, ProbabilityConfig config) {
//        System.out.println(target.getBlock());
        if(reader.getBlockState(pos).is(Blocks.AIR) && reader.getBlockState(pos.below()).is(target.getBlock())){
//            System.out.println("replace");
                reader.setBlock(pos.below(), weightedList.get(rand.nextInt(weightedList.size())), 2);
        }

        return true;
    }
}
