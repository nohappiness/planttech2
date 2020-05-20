package net.kaneka.planttech2.blocks.baseclasses;


import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

public class BaseOreBlock extends BaseBlock
{
	int expmin, expmax; 
	
	
	public BaseOreBlock(Properties property, String name, int expmin, int expmax)
	{
		super(property, name, ModCreativeTabs.groupblocks, true);
		this.expmin = expmin; 
		this.expmax = expmax; 
	}
	
	@Override
	public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch)
	{
		if(silktouch == 0)
		{
			return MathHelper.nextInt(RANDOM, expmin, expmax);
		}
		return 0; 
	}
}
