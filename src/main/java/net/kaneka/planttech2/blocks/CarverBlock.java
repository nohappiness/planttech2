package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;
import java.util.*;

public class CarverBlock extends Block
{
	public static final BooleanProperty NORTH = PipeBlock.NORTH;
	public static final BooleanProperty EAST = PipeBlock.EAST;
	public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
	public static final BooleanProperty WEST = PipeBlock.WEST;
	protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((facingProperty) -> {
		return facingProperty.getKey().getAxis().isHorizontal();
	}).collect(Util.toMap());

	public CarverBlock()
	{
		super(Block.Properties.of(Material.WOOD).noCollission().strength(0.5f).randomTicks());
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(NORTH, false)
				.setValue(EAST, false)
				.setValue(SOUTH, false)
		        .setValue(WEST, false));
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, Random rand)
	{
		List<Direction> directions = new ArrayList<Direction>();
		directions.add(Direction.NORTH);
		directions.add(Direction.EAST);
		directions.add(Direction.SOUTH);
		directions.add(Direction.WEST);
		Collections.shuffle(directions);
		for(Direction direction: directions)
		{
			BlockState state2 = level.getBlockState(pos.relative(direction));
			Block block = state2.getBlock();
			if(checkForGrowable(block))
			{
				if(block == Blocks.IRON_BLOCK)
					level.setBlockAndUpdate(pos.relative(direction), ModBlocks.MACHINESHELL_IRON_GROWING.defaultBlockState());
				else if(block == ModBlocks.PLANTIUM_BLOCK)
					level.setBlockAndUpdate(pos.relative(direction), ModBlocks.MACHINESHELL_PLANTIUM_GROWING.defaultBlockState());
				else if(block == ModBlocks.MACHINESHELL_IRON_GROWING || block == ModBlocks.MACHINESHELL_PLANTIUM_GROWING)
					((GrowingBlock)block).grow(state2, level, pos.relative(direction));
				break;
			}
		}

	}



	@Override
	public RenderShape getRenderShape(BlockState iBlockState)
	{
		return RenderShape.MODEL;
	}


	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos)
	{
		return true;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		switch (rot)
		{
		case CLOCKWISE_180:
			return state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
		case COUNTERCLOCKWISE_90:
			return state.setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
		case CLOCKWISE_90:
			return state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
		default:
			return state;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		switch (mirrorIn)
		{
			case LEFT_RIGHT:
				return state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
			case FRONT_BACK:
				return state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
			default:
				return super.mirror(state, mirrorIn);
		}
	}

	private boolean checkForConnection(BlockState state)
	{
		Block block = state.getBlock();
		return checkForGrowable(block) || block == ModBlocks.MACHINESHELL_IRON || block == ModBlocks.MACHINESHELL_PLANTIUM;
	}

	private boolean checkForGrowable(Block block)
	{
		return block == Blocks.IRON_BLOCK || block == ModBlocks.PLANTIUM_BLOCK || block == ModBlocks.MACHINESHELL_IRON_GROWING || block == ModBlocks.MACHINESHELL_PLANTIUM_GROWING ;
	}



	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor levelIn, BlockPos currentPos, BlockPos facingPos)
	{
		return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? stateIn.setValue(FACING_TO_PROPERTY_MAP.get(facing), Boolean.valueOf(this.checkForConnection(facingState)))
		        : super.updateShape(stateIn, facing, facingState, levelIn, currentPos, facingPos);
	}


	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		return defaultBlockState()
		        .setValue(NORTH, this.checkForConnection(level.getBlockState(pos.north())))
		        .setValue(EAST, this.checkForConnection(level.getBlockState(pos.east())))
		        .setValue(SOUTH, this.checkForConnection(level.getBlockState(pos.south())))
		        .setValue(WEST, this.checkForConnection(level.getBlockState(pos.west())));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(NORTH, EAST, WEST, SOUTH);
	}
}
