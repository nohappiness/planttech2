package net.kaneka.planttech2.blocks;

import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class CustomFenceBlock extends BaseBlock
{
	public static final BooleanProperty NORTH = SixWayBlock.NORTH;
	public static final BooleanProperty EAST = SixWayBlock.EAST;
	public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
	public static final BooleanProperty WEST = SixWayBlock.WEST;
	protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((p_199775_0_) -> {
		return p_199775_0_.getKey().getAxis().isHorizontal();
	}).collect(Util.toMapCollector());
	protected final VoxelShape[] collisionShapes;
	protected final VoxelShape[] shapes;
	private final VoxelShape[] renderShapes;
	private final Object2IntMap<BlockState> field_223008_i = new Object2IntOpenHashMap<>();

	public CustomFenceBlock(Properties property, String name, ItemGroup group, boolean hasItem)
	{
		super(property, name, group, hasItem);
		this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, Boolean.valueOf(false)).with(EAST, Boolean.valueOf(false)).with(SOUTH, Boolean.valueOf(false))
		        .with(WEST, Boolean.valueOf(false)));
		this.renderShapes = this.makeShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
		this.collisionShapes = this.makeShapes(2.0F, 2.0F, 24.0F, 0.0F, 24.0F);
		this.shapes = this.makeShapes(2.0F, 2.0F, 16.0F, 0.0F, 16.0F);
	}

	public boolean func_220111_a(BlockState p_220111_1_, boolean p_220111_2_, Direction p_220111_3_)
	{
		Block block = p_220111_1_.getBlock();
		boolean flag = block.isIn(BlockTags.FENCES) && p_220111_1_.getMaterial() == this.material;
		boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.isParallel(p_220111_1_, p_220111_3_);
		boolean flag2 = block instanceof CustomFenceBlock; 
		return !cannotAttach(block) && p_220111_2_ || flag || flag1 || flag2;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		IBlockReader iblockreader = context.getWorld();
		BlockPos blockpos = context.getPos();
		BlockPos blockpos1 = blockpos.north();
		BlockPos blockpos2 = blockpos.east();
		BlockPos blockpos3 = blockpos.south();
		BlockPos blockpos4 = blockpos.west();
		BlockState blockstate = iblockreader.getBlockState(blockpos1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
		return super.getStateForPlacement(context)
		        .with(NORTH, Boolean.valueOf(this.func_220111_a(blockstate, Block.hasSolidSide(blockstate, iblockreader, blockpos1, Direction.SOUTH), Direction.SOUTH)))
		        .with(EAST, Boolean.valueOf(this.func_220111_a(blockstate1, Block.hasSolidSide(blockstate1, iblockreader, blockpos2, Direction.WEST), Direction.WEST)))
		        .with(SOUTH, Boolean.valueOf(this.func_220111_a(blockstate2, Block.hasSolidSide(blockstate2, iblockreader, blockpos3, Direction.NORTH), Direction.NORTH)))
		        .with(WEST, Boolean.valueOf(this.func_220111_a(blockstate3, Block.hasSolidSide(blockstate3, iblockreader, blockpos4, Direction.EAST), Direction.EAST)));
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

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return this.shapes[this.getIndex(state)];
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return this.collisionShapes[this.getIndex(state)];
	}

	public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return this.renderShapes[this.getIndex(state)];
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{

		return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL
		        ? stateIn.with(FACING_TO_PROPERTY_MAP.get(facing),
		                Boolean.valueOf(this.func_220111_a(facingState, Block.hasSolidSide(facingState, worldIn, facingPos, facing.getOpposite()), facing.getOpposite())))
		        : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	protected int getIndex(BlockState state)
	{
		return this.field_223008_i.computeIntIfAbsent(state, (p_223007_0_) -> {
			int i = 0;
			if (p_223007_0_.get(NORTH))
			{
				i |= getMask(Direction.NORTH);
			}

			if (p_223007_0_.get(EAST))
			{
				i |= getMask(Direction.EAST);
			}

			if (p_223007_0_.get(SOUTH))
			{
				i |= getMask(Direction.SOUTH);
			}

			if (p_223007_0_.get(WEST))
			{
				i |= getMask(Direction.WEST);
			}

			return i;
		});
	}

	private static int getMask(Direction facing)
	{
		return 1 << facing.getHorizontalIndex();
	} 
	
	@Override
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(NORTH, EAST, WEST, SOUTH);
	}

}
