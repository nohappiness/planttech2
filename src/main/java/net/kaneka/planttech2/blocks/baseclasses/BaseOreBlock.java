package net.kaneka.planttech2.blocks.baseclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;


public class BaseOreBlock extends Block
{
	private final int expMin, expMax;

	public BaseOreBlock(Properties property, int expMin, int expMax)
	{
		super(property);
		this.expMin = expMin;
		this.expMax = expMax;
	}



	@Override
	public int getExpDrop(BlockState state, LevelReader world, BlockPos pos, int fortune, int silkTouch)
	{
		return silkTouch == 0 ? RANDOM.nextInt(expMax - expMin) + expMin : 0;
	}
}
