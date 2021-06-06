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

import java.util.function.Supplier;

public class FacingGrowingBlock extends GrowingBlock
{
	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	public FacingGrowingBlock(Supplier<Block> blockSupplier, boolean growAlone)
	{
		super(blockSupplier, growAlone);
		this.registerDefaultState(this.stateDefinition.any().setValue(GROWINGSTATE, 0).setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void placeBlock(ServerWorld world, BlockPos pos, BlockState state)
	{
		world.setBlockAndUpdate(pos, blockSupplier.get().defaultBlockState().setValue(FACING, state.getValue(FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.setValue(FACING, rot.rotate((Direction) state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.getRotation((Direction) state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(GROWINGSTATE, FACING);
	}

}
