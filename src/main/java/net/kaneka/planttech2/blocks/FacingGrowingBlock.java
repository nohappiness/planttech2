package net.kaneka.planttech2.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.state.StateDefinition.Builder;
import net.minecraft.core.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.level.server.Serverlevel;

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
	protected void placeBlock(Serverlevel level, BlockPos pos, BlockState state)
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
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(GROWINGSTATE, FACING);
	}

}
