package net.kaneka.planttech2.blocks;

import java.util.Random;

import net.kaneka.planttech2.blocks.baseclasses.BaseBlock;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class GrowBlockCropBlock extends BaseBlock
{
	public static final IntegerProperty GROWINGSTATE = IntegerProperty.create("growingstate", 0, 7);
	private final Block block; 
	
	public GrowBlockCropBlock(Properties property, String name, Block block)
	{
		super(property.tickRandomly(), name, ModCreativeTabs.groupmain, false);
		this.block = block; 
		this.setDefaultState(this.stateContainer.getBaseState().with(GROWINGSTATE, Integer.valueOf(0)));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand)
	{
		super.tick(state, world, pos, rand);
		int i = state.get(GROWINGSTATE).intValue();
		if(i < 7)
		{
    		if(rand.nextInt(8) == 0)
    		{
    			world.setBlockState(pos, this.getDefaultState().with(GROWINGSTATE, i + 1), 2);
    		}
		}
		
		if(i >= 7)
		{
			world.setBlockState(pos, block.getDefaultState(), 1);
		}
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) 
	{
		builder.add(GROWINGSTATE);
	}

}
