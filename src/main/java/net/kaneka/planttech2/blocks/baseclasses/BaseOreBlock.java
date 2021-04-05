package net.kaneka.planttech2.blocks.baseclasses;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

import net.minecraft.block.AbstractBlock.Properties;

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
	public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silkTouch)
	{
		return silkTouch == 0 ? MathHelper.nextInt(RANDOM, expMin, expMax) : 0;
	}
}
