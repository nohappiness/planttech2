package net.kaneka.planttech2.blocks.baseclasses;

import java.util.Random;
import java.util.stream.IntStream;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CustomStairsBlock extends BaseBlock implements IWaterLoggable
{

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
	public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape AABB_SLAB_TOP = CustomSlabBlock.TOP_SHAPE;
	protected static final VoxelShape AABB_SLAB_BOTTOM = CustomSlabBlock.BOTTOM_SHAPE;
	protected static final VoxelShape field_196512_A = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
	protected static final VoxelShape field_196513_B = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
	protected static final VoxelShape field_196514_C = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
	protected static final VoxelShape field_196515_D = Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
	protected static final VoxelShape field_196516_E = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
	protected static final VoxelShape field_196517_F = Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
	protected static final VoxelShape field_196518_G = Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
	protected static final VoxelShape field_196519_H = Block.makeCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape[] SLAB_TOP_SHAPES = makeShapes(AABB_SLAB_TOP, field_196512_A, field_196516_E, field_196513_B, field_196517_F);
	protected static final VoxelShape[] SLAB_BOTTOM_SHAPES = makeShapes(AABB_SLAB_BOTTOM, field_196514_C, field_196518_G, field_196515_D, field_196519_H);
	private static final int[] field_196522_K = new int[] { 12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8 };
	private final Block modelBlock;
	private final BlockState modelState;

	private static VoxelShape[] makeShapes(VoxelShape p_199779_0_, VoxelShape p_199779_1_, VoxelShape p_199779_2_, VoxelShape p_199779_3_, VoxelShape p_199779_4_)
	{
		return IntStream.range(0, 16).mapToObj((p_199780_5_) -> {
			return combineShapes(p_199780_5_, p_199779_0_, p_199779_1_, p_199779_2_, p_199779_3_, p_199779_4_);
		}).toArray((p_199778_0_) -> {
			return new VoxelShape[p_199778_0_];
		});
	}

	private static VoxelShape combineShapes(int p_199781_0_, VoxelShape p_199781_1_, VoxelShape p_199781_2_, VoxelShape p_199781_3_, VoxelShape p_199781_4_, VoxelShape p_199781_5_)
	{
		VoxelShape voxelshape = p_199781_1_;
		if ((p_199781_0_ & 1) != 0)
		{
			voxelshape = VoxelShapes.or(p_199781_1_, p_199781_2_);
		}

		if ((p_199781_0_ & 2) != 0)
		{
			voxelshape = VoxelShapes.or(voxelshape, p_199781_3_);
		}

		if ((p_199781_0_ & 4) != 0)
		{
			voxelshape = VoxelShapes.or(voxelshape, p_199781_4_);
		}

		if ((p_199781_0_ & 8) != 0)
		{
			voxelshape = VoxelShapes.or(voxelshape, p_199781_5_);
		}

		return voxelshape;
	}

	public CustomStairsBlock(BlockState state, Properties properties, String name, ItemGroup group, boolean hasItem)
	{
		super(properties, name, group, hasItem);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(HALF, Half.BOTTOM).with(SHAPE, StairsShape.STRAIGHT).with(WATERLOGGED,
		        Boolean.valueOf(false)));
		this.modelBlock = state.getBlock();
		this.modelState = state;
	}

	public boolean func_220074_n(BlockState state)
	{
		return true;
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return (state.get(HALF) == Half.TOP ? SLAB_TOP_SHAPES : SLAB_BOTTOM_SHAPES)[field_196522_K[this.func_196511_x(state)]];
	}

	private int func_196511_x(BlockState state)
	{
		return state.get(SHAPE).ordinal() * 4 + state.get(FACING).getHorizontalIndex();
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		this.modelBlock.animateTick(stateIn, worldIn, pos, rand);
	}

	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player)
	{
		this.modelState.onBlockClicked(worldIn, pos, player);
	}

	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state)
	{
		this.modelBlock.onPlayerDestroy(worldIn, pos, state);
	}

	@SuppressWarnings("deprecation")
	public float getExplosionResistance()
	{
		return this.modelBlock.getExplosionResistance();
	}

	public BlockRenderLayer getRenderLayer()
	{
		return this.modelBlock.getRenderLayer();
	}

	public int tickRate(IWorldReader worldIn)
	{
		return this.modelBlock.tickRate(worldIn);
	}

	@SuppressWarnings("deprecation")
	public void onBlockAdded(BlockState p_220082_1_, World worldIn, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_)
	{
		if (p_220082_1_.getBlock() != p_220082_1_.getBlock())
		{
			this.modelState.neighborChanged(worldIn, pos, Blocks.AIR, pos, false);
			this.modelBlock.onBlockAdded(this.modelState, worldIn, pos, p_220082_4_, false);
		}
	}

	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			this.modelState.onReplaced(worldIn, pos, newState, isMoving);
		}
	}

	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
	{
		this.modelBlock.onEntityWalk(worldIn, pos, entityIn);
	}

	@SuppressWarnings("deprecation")
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random)
	{
		this.modelBlock.tick(state, worldIn, pos, random);
	}

	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		return this.modelState.onBlockActivated(worldIn, player, handIn, hit);
	}

	public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn)
	{
		this.modelBlock.onExplosionDestroy(worldIn, pos, explosionIn);
	}

	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		Direction direction = context.getFace();
		BlockPos blockpos = context.getPos();
		IFluidState ifluidstate = context.getWorld().getFluidState(blockpos);
		BlockState blockstate = this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing())
		        .with(HALF, direction != Direction.DOWN && (direction == Direction.UP || !(context.getHitVec().y - (double) blockpos.getY() > 0.5D)) ? Half.BOTTOM : Half.TOP)
		        .with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
		return blockstate.with(SHAPE, getShapeProperty(blockstate, context.getWorld(), blockpos));
	}

	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		if (stateIn.get(WATERLOGGED))
		{
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		return facing.getAxis().isHorizontal() ? stateIn.with(SHAPE, getShapeProperty(stateIn, worldIn, currentPos))
		        : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	private static StairsShape getShapeProperty(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		Direction direction = state.get(FACING);
		BlockState blockstate = worldIn.getBlockState(pos.offset(direction));
		if (isBlockStairs(blockstate) && state.get(HALF) == blockstate.get(HALF))
		{
			Direction direction1 = blockstate.get(FACING);
			if (direction1.getAxis() != state.get(FACING).getAxis() && isDifferentStairs(state, worldIn, pos, direction1.getOpposite()))
			{
				if (direction1 == direction.rotateYCCW())
				{
					return StairsShape.OUTER_LEFT;
				}

				return StairsShape.OUTER_RIGHT;
			}
		}

		BlockState blockstate1 = worldIn.getBlockState(pos.offset(direction.getOpposite()));
		if (isBlockStairs(blockstate1) && state.get(HALF) == blockstate1.get(HALF))
		{
			Direction direction2 = blockstate1.get(FACING);
			if (direction2.getAxis() != state.get(FACING).getAxis() && isDifferentStairs(state, worldIn, pos, direction2))
			{
				if (direction2 == direction.rotateYCCW())
				{
					return StairsShape.INNER_LEFT;
				}

				return StairsShape.INNER_RIGHT;
			}
		}

		return StairsShape.STRAIGHT;
	}

	private static boolean isDifferentStairs(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face)
	{
		BlockState blockstate = worldIn.getBlockState(pos.offset(face));
		return !isBlockStairs(blockstate) || blockstate.get(FACING) != state.get(FACING) || blockstate.get(HALF) != state.get(HALF);
	}

	public static boolean isBlockStairs(BlockState state)
	{
		return state.getBlock() instanceof StairsBlock || state.getBlock() instanceof CustomStairsBlock;
	}

	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@SuppressWarnings({ "deprecation", "incomplete-switch" })
	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		Direction direction = state.get(FACING);
		StairsShape stairsshape = state.get(SHAPE);
		switch (mirrorIn)
		{
		case LEFT_RIGHT:
			if (direction.getAxis() == Direction.Axis.Z)
			{
				switch (stairsshape)
				{
				case INNER_LEFT:
					return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_RIGHT);
				case INNER_RIGHT:
					return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_LEFT);
				case OUTER_LEFT:
					return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_RIGHT);
				case OUTER_RIGHT:
					return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_LEFT);
				default:
					return state.rotate(Rotation.CLOCKWISE_180);
				}
			}
			break;
		case FRONT_BACK:
			if (direction.getAxis() == Direction.Axis.X)
			{
				switch (stairsshape)
				{
				case INNER_LEFT:
					return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_LEFT);
				case INNER_RIGHT:
					return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_RIGHT);
				case OUTER_LEFT:
					return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_RIGHT);
				case OUTER_RIGHT:
					return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_LEFT);
				case STRAIGHT:
					return state.rotate(Rotation.CLOCKWISE_180);
				}
			}
		}

		return super.mirror(state, mirrorIn);
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, HALF, SHAPE, WATERLOGGED);
	}

	@SuppressWarnings("deprecation")
	public IFluidState getFluidState(BlockState state)
	{
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type)
	{
		return false;
	}
}