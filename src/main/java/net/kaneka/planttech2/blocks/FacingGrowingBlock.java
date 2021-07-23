package net.kaneka.planttech2.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import java.util.function.Supplier;


public class FacingGrowingBlock extends GrowingBlock
{
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public FacingGrowingBlock(Supplier<Block> blockSupplier, boolean growAlone)
	{
		super(blockSupplier, growAlone);
		this.registerDefaultState(this.stateDefinition.any().setValue(GROWINGSTATE, 0).setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void placeBlock(ServerLevel level, BlockPos pos, BlockState state)
	{
		level.setBlockAndUpdate(pos, blockSupplier.get().defaultBlockState().setValue(FACING, state.getValue(FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
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
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(GROWINGSTATE, FACING);
	}

}
