package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SixWayBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CarverBlock extends Block
{
	public static final BooleanProperty NORTH = SixWayBlock.NORTH;
	public static final BooleanProperty EAST = SixWayBlock.EAST;
	public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
	public static final BooleanProperty WEST = SixWayBlock.WEST;
	protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((p_199775_0_) -> {
		return p_199775_0_.getKey().getAxis().isHorizontal();
	}).collect(Util.toMapCollector());

	public CarverBlock()
	{
		super(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(0.5f).tickRandomly());
		this.setDefaultState(this.stateContainer.getBaseState()
				.with(NORTH, false)
				.with(EAST, false)
				.with(SOUTH, false)
		        .with(WEST, false));
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand)
	{
		List<Direction> directions = new ArrayList<Direction>();
		directions.add(Direction.NORTH);
		directions.add(Direction.EAST);
		directions.add(Direction.SOUTH);
		directions.add(Direction.WEST);
		Collections.shuffle(directions);
		for(Direction direction: directions)
		{
			BlockState state2 = world.getBlockState(pos.offset(direction));
			Block block = state2.getBlock();
			if(checkForGrowable(block))
			{
				if(block == Blocks.IRON_BLOCK)
					world.setBlockState(pos.offset(direction), ModBlocks.MACHINESHELL_IRON_GROWING.getDefaultState());
				else if(block == ModBlocks.PLANTIUM_BLOCK)
					world.setBlockState(pos.offset(direction), ModBlocks.MACHINESHELL_PLANTIUM_GROWING.getDefaultState());
				else if(block == ModBlocks.MACHINESHELL_IRON_GROWING || block == ModBlocks.MACHINESHELL_PLANTIUM_GROWING)
					((GrowingBlock)block).grow(state2, world, pos.offset(direction));
				break;
			}
		}

	}

	@Override
	public BlockRenderType getRenderType(BlockState iBlockState)
	{
		return BlockRenderType.MODEL;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos)
	{
		return true;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		switch (rot)
		{
		case CLOCKWISE_180:
			return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
		case COUNTERCLOCKWISE_90:
			return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
		case CLOCKWISE_90:
			return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
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
				return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
			case FRONT_BACK:
				return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
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
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), Boolean.valueOf(this.checkForConnection(facingState)))
		        : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		return getDefaultState()
		        .with(NORTH, this.checkForConnection(world.getBlockState(pos.north())))
		        .with(EAST, this.checkForConnection(world.getBlockState(pos.east())))
		        .with(SOUTH, this.checkForConnection(world.getBlockState(pos.south())))
		        .with(WEST, this.checkForConnection(world.getBlockState(pos.west())));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(NORTH, EAST, WEST, SOUTH);
	}
}
