package net.kaneka.planttech2.blocks.baseclasses;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.fluid.FluidState;

public class CustomSlabBlock extends Block implements IWaterLoggable
{

	public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
	static final VoxelShape TOP_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	public CustomSlabBlock(Properties builder)
	{
		super(builder);
	}

	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return worldIn.getMaxLightLevel();
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(TYPE, WATERLOGGED);
	}

	protected boolean canSilkHarvest()
	{
		return false;
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		SlabType slabtype = state.get(TYPE);
		switch (slabtype)
		{
		case DOUBLE:
			return VoxelShapes.fullCube();
		case TOP:
			return TOP_SHAPE;
		default:
			return BOTTOM_SHAPE;
		}
	}

	public boolean isTopSolid(BlockState state)
	{
		return state.get(TYPE) == SlabType.DOUBLE || state.get(TYPE) == SlabType.TOP;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockPos blockpos = context.getPos();
		BlockState blockstate = context.getWorld().getBlockState(blockpos);
		if (blockstate.getBlock() == this)
		{
			return blockstate.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, Boolean.valueOf(false));
		} else
		{
			FluidState ifluidstate = context.getWorld().getFluidState(blockpos);
			BlockState blockstate1 = this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
			Direction direction = context.getFace();
			return direction != Direction.DOWN && (direction == Direction.UP || !(context.getHitVec().y - (double) blockpos.getY() > 0.5D)) ? blockstate1
			        : blockstate1.with(TYPE, SlabType.TOP);
		}
	}

	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext)
	{
		ItemStack itemstack = useContext.getItem();
		SlabType slabtype = state.get(TYPE);
		if (slabtype != SlabType.DOUBLE && itemstack.getItem() == this.asItem())
		{
			if (useContext.replacingClickedOnBlock())
			{
				boolean flag = useContext.getHitVec().y - (double) useContext.getPos().getY() > 0.5D;
				Direction direction = useContext.getFace();
				if (slabtype == SlabType.BOTTOM)
				{
					return direction == Direction.UP || flag && direction.getAxis().isHorizontal();
				} else
				{
					return direction == Direction.DOWN || !flag && direction.getAxis().isHorizontal();
				}
			} else
			{
				return true;
			}
		} else
		{
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState state)
	{
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn)
	{
		return state.get(TYPE) != SlabType.DOUBLE ? IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn) : false;
	}

	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn)
	{
		return state.get(TYPE) != SlabType.DOUBLE ? IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn) : false;
	}

	public Fluid pickupFluid(IWorld worldIn, BlockPos pos, BlockState state)
	{
		if (state.get(WATERLOGGED))
		{
			worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(false)), 3);
			return Fluids.WATER;
		} else
		{
			return Fluids.EMPTY;
		}
	}

	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		if (stateIn.get(WATERLOGGED))
		{
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type)
	{
		switch (type)
		{
		case LAND:
			return state.get(TYPE) == SlabType.BOTTOM;
		case WATER:
			return worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
		case AIR:
			return false;
		default:
			return false;
		}
	}
}
