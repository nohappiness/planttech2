package net.kaneka.planttech2.dimensions.placements;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

public class LakeBiomass extends Placement<ChanceConfig>
{
	public LakeBiomass(Function<Dynamic<?>, ? extends ChanceConfig> configFactory)
	{
		super(configFactory);
	}

	@Override
	public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generatorIn, Random random, ChanceConfig configIn, BlockPos pos)
	{
		if (random.nextInt(configIn.chance / 10) == 0) 
		{
	         int i = random.nextInt(16) + pos.getX();
	         int j = random.nextInt(16) + pos.getZ();
	         int k = random.nextInt(random.nextInt(generatorIn.getMaxHeight() - 8) + 8);
	         if (k < worldIn.getSeaLevel() || random.nextInt(configIn.chance / 8) == 0) 
	         {
	            return Stream.of(new BlockPos(i, k, j));
	         }
	      }

	      return Stream.empty();
	}

}
