package net.kaneka.planttech2.blocks.baseclasses;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Map;

import net.minecraft.block.AbstractBlock.Properties;

public class CustomFenceBlock extends Block
{
	public static final BooleanProperty NORTH = SixWayBlock.NORTH;
	public static final BooleanProperty EAST = SixWayBlock.EAST;
	public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
	public static final BooleanProperty WEST = SixWayBlock.WEST;
	protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((facingProperty) -> facingProperty.getKey().getAxis().isHorizontal()).collect(Util.toMapCollector());
	protected VoxelShape[] collisionShapes;
	protected VoxelShape[] shapes;
	private final Object2IntMap<BlockState> statePaletteMap = new Object2IntOpenHashMap<>();

	public CustomFenceBlock(Properties property)
	{
		super(property);
		this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
		this.collisionShapes = this.makeShapes(1.0F, 1.0F, 24.0F, 0.0F, 24.0F);
		this.shapes = this.makeShapes(1.0F, 1.0F, 16.0F, 0.0F, 16.0F);
	}

	public boolean canConnect(BlockState state, boolean isSideSolid, Direction direction)
	{
		Block block = state.getBlock();
		boolean flag = block.isIn(BlockTags.FENCES) && state.getMaterial() == this.material;
		boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.isParallel(state, direction);
		boolean flag2 = block instanceof CustomFenceBlock;
		return !cannotAttach(block) && isSideSolid || flag || flag1 || flag2;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		World iblockreader = context.getWorld();
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
				.with(NORTH, this.canConnect(blockstate, Block.hasEnoughSolidSide(iblockreader, blockpos1, Direction.SOUTH), Direction.SOUTH))
				.with(EAST, this.canConnect(blockstate1, Block.hasEnoughSolidSide(iblockreader, blockpos2, Direction.WEST), Direction.WEST))
				.with(SOUTH, this.canConnect(blockstate2, Block.hasEnoughSolidSide(iblockreader, blockpos3, Direction.NORTH), Direction.NORTH))
				.with(WEST, this.canConnect(blockstate3, Block.hasEnoughSolidSide(iblockreader, blockpos4, Direction.EAST), Direction.EAST));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		switch (rot) {
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
		switch (mirrorIn) {
			case LEFT_RIGHT:
				return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
			case FRONT_BACK:
				return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
			default:
				return super.mirror(state, mirrorIn);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return this.shapes[this.getIndex(state)];
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return this.collisionShapes[this.getIndex(state)];
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
//		return this.renderShapes[this.getIndex(state)];
		return this.collisionShapes[this.getIndex(state)];
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{

		return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL
				? stateIn.with(FACING_TO_PROPERTY_MAP.get(facing),
				this.canConnect(facingState, Block.hasEnoughSolidSide(worldIn, facingPos, facing.getOpposite()), facing.getOpposite())
		)
				: super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	protected int getIndex(BlockState state)
	{
		return this.statePaletteMap.computeIntIfAbsent(state, (stateIn) -> {
			int i = 0;
			if (stateIn.get(NORTH)) {
				i |= getMask(Direction.NORTH);
			}

			if (stateIn.get(EAST)) {
				i |= getMask(Direction.EAST);
			}

			if (stateIn.get(SOUTH)) {
				i |= getMask(Direction.SOUTH);
			}

			if (stateIn.get(WEST)) {
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
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(NORTH, EAST, WEST, SOUTH);
	}

	protected VoxelShape[] makeShapes(float nodeWidth, float extensionWidth, float nodeHeight, float extensionBottom, float extensionHeight)
	{
		float f = 8.0F - nodeWidth;
		float f1 = 8.0F + nodeWidth;
		float f2 = 8.0F - extensionWidth;
		float f3 = 8.0F + extensionWidth;
		VoxelShape voxelshape = Block.makeCuboidShape(f, 0.0D, f, f1, nodeHeight, f1);
		VoxelShape voxelshape1 = Block.makeCuboidShape(f2, extensionBottom, 0.0D, f3, extensionHeight, f3);
		VoxelShape voxelshape2 = Block.makeCuboidShape(f2, extensionBottom, f2, f3, extensionHeight, 16.0D);
		VoxelShape voxelshape3 = Block.makeCuboidShape(0.0D, extensionBottom, f2, f3, extensionHeight, f3);
		VoxelShape voxelshape4 = Block.makeCuboidShape(f2, extensionBottom, f2, 16.0D, extensionHeight, f3);
		VoxelShape voxelshape5 = VoxelShapes.or(voxelshape1, voxelshape4);
		VoxelShape voxelshape6 = VoxelShapes.or(voxelshape2, voxelshape3);
		VoxelShape[] avoxelshape = new VoxelShape[] { VoxelShapes.empty(), voxelshape2, voxelshape3, voxelshape6, voxelshape1, VoxelShapes.or(voxelshape2, voxelshape1), VoxelShapes.or(voxelshape3, voxelshape1), VoxelShapes.or(voxelshape6, voxelshape1), voxelshape4, VoxelShapes.or(voxelshape2, voxelshape4), VoxelShapes.or(voxelshape3, voxelshape4), VoxelShapes.or(voxelshape6, voxelshape4), voxelshape5, VoxelShapes.or(voxelshape2, voxelshape5), VoxelShapes.or(voxelshape3, voxelshape5),
				VoxelShapes.or(voxelshape6, voxelshape5) };

		for (int i = 0; i < 16; ++i) {
			avoxelshape[i] = VoxelShapes.or(voxelshape, avoxelshape[i]);
		}

		return avoxelshape;
	}
}
