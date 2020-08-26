package net.kaneka.planttech2.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class FacingGrowingBlock extends GrowingBlock
{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	public FacingGrowingBlock(String name, Block block, boolean growAlone)
	{
		super(name, block, growAlone);
		this.setDefaultState(this.stateContainer.getBaseState().with(GROWINGSTATE, Integer.valueOf(0)).with(FACING, Direction.NORTH));
	}

	@Override
	protected void placeBlock(ServerWorld world, BlockPos pos, BlockState state)
	{
		world.setBlockState(pos, block.getDefaultState().with(FACING, state.get(FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.with(FACING, rot.rotate((Direction) state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.toRotation((Direction) state.get(FACING)));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(GROWINGSTATE, FACING);
	}

}
