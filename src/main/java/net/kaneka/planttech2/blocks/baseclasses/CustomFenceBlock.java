package net.kaneka.planttech2.blocks.baseclasses;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;


public class CustomFenceBlock extends Block
{
	public static final BooleanProperty NORTH = PipeBlock.NORTH;
	public static final BooleanProperty EAST = PipeBlock.EAST;
	public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
	public static final BooleanProperty WEST = PipeBlock.WEST;
	protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((facingProperty) -> facingProperty.getKey().getAxis().isHorizontal()).collect(Util.toMap());
	protected VoxelShape[] collisionShapes;
	protected VoxelShape[] shapes;
	private final Object2IntMap<BlockState> statePaletteMap = new Object2IntOpenHashMap<>();

	public CustomFenceBlock(Properties property)
	{
		super(property);
		this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false));
		this.collisionShapes = this.makeShapes(1.0F, 1.0F, 24.0F, 0.0F, 24.0F);
		this.shapes = this.makeShapes(1.0F, 1.0F, 16.0F, 0.0F, 16.0F);
	}

	public boolean canConnect(BlockState state, boolean isSideSolid, Direction direction)
	{
		Block block = state.getBlock();
		boolean flag = block.getTags().contains(BlockTags.FENCES.getName()) && state.getMaterial() == this.material;
		boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
		boolean flag2 = block instanceof CustomFenceBlock;
		return !isExceptionForConnection(state) && isSideSolid || flag || flag1 || flag2;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		Level iblockreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockPos blockpos1 = blockpos.north();
		BlockPos blockpos2 = blockpos.east();
		BlockPos blockpos3 = blockpos.south();
		BlockPos blockpos4 = blockpos.west();
		BlockState blockstate = iblockreader.getBlockState(blockpos1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
		return defaultBlockState()
				.setValue(NORTH, this.canConnect(blockstate, Block.canSupportCenter(iblockreader, blockpos1, Direction.SOUTH), Direction.SOUTH))
				.setValue(EAST, this.canConnect(blockstate1, Block.canSupportCenter(iblockreader, blockpos2, Direction.WEST), Direction.WEST))
				.setValue(SOUTH, this.canConnect(blockstate2, Block.canSupportCenter(iblockreader, blockpos3, Direction.NORTH), Direction.NORTH))
				.setValue(WEST, this.canConnect(blockstate3, Block.canSupportCenter(iblockreader, blockpos4, Direction.EAST), Direction.EAST));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return switch (rot)
				{
					case CLOCKWISE_180 -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
					case COUNTERCLOCKWISE_90 -> state.setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
					case CLOCKWISE_90 -> state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
					default -> state;
				};
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return switch (mirrorIn)
				{
					case LEFT_RIGHT -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
					case FRONT_BACK -> state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
					default -> super.mirror(state, mirrorIn);
				};
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
	{
		return this.shapes[this.getIndex(state)];
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
	{
		return this.collisionShapes[this.getIndex(state)];
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos)
	{
//		return this.renderShapes[this.getIndex(state)];
		return this.collisionShapes[this.getIndex(state)];
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
	{

		return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL
				? stateIn.setValue(FACING_TO_PROPERTY_MAP.get(facing),
				this.canConnect(facingState, Block.canSupportCenter(level, facingPos, facing.getOpposite()), facing.getOpposite())
		)
				: super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
	}

	protected int getIndex(BlockState state)
	{
		return this.statePaletteMap.computeIntIfAbsent(state, (stateIn) -> {
			int i = 0;
			if (stateIn.getValue(NORTH)) {
				i |= getMask(Direction.NORTH);
			}

			if (stateIn.getValue(EAST)) {
				i |= getMask(Direction.EAST);
			}

			if (stateIn.getValue(SOUTH)) {
				i |= getMask(Direction.SOUTH);
			}

			if (stateIn.getValue(WEST)) {
				i |= getMask(Direction.WEST);
			}

			return i;
		});
	}

	private static int getMask(Direction facing)
	{
		return 1 << facing.get2DDataValue();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(NORTH, EAST, WEST, SOUTH);
	}

	protected VoxelShape[] makeShapes(float nodeWidth, float extensionWidth, float nodeHeight, float extensionBottom, float extensionHeight)
	{
		float f = 8.0F - nodeWidth;
		float f1 = 8.0F + nodeWidth;
		float f2 = 8.0F - extensionWidth;
		float f3 = 8.0F + extensionWidth;
		VoxelShape voxelshape = Block.box(f, 0.0D, f, f1, nodeHeight, f1);
		VoxelShape voxelshape1 = Block.box(f2, extensionBottom, 0.0D, f3, extensionHeight, f3);
		VoxelShape voxelshape2 = Block.box(f2, extensionBottom, f2, f3, extensionHeight, 16.0D);
		VoxelShape voxelshape3 = Block.box(0.0D, extensionBottom, f2, f3, extensionHeight, f3);
		VoxelShape voxelshape4 = Block.box(f2, extensionBottom, f2, 16.0D, extensionHeight, f3);
		VoxelShape voxelshape5 = Shapes.or(voxelshape1, voxelshape4);
		VoxelShape voxelshape6 = Shapes.or(voxelshape2, voxelshape3);
		VoxelShape[] avoxelshape = new VoxelShape[] { Shapes.empty(), voxelshape2, voxelshape3, voxelshape6, voxelshape1, Shapes.or(voxelshape2, voxelshape1), Shapes.or(voxelshape3, voxelshape1), Shapes.or(voxelshape6, voxelshape1), voxelshape4, Shapes.or(voxelshape2, voxelshape4), Shapes.or(voxelshape3, voxelshape4), Shapes.or(voxelshape6, voxelshape4), voxelshape5, Shapes.or(voxelshape2, voxelshape5), Shapes.or(voxelshape3, voxelshape5),
				Shapes.or(voxelshape6, voxelshape5) };

		for (int i = 0; i < 16; ++i) {
			avoxelshape[i] = Shapes.or(voxelshape, avoxelshape[i]);
		}

		return avoxelshape;
	}
}
