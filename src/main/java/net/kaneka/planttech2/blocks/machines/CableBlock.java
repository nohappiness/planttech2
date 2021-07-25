package net.kaneka.planttech2.blocks.machines;

import net.kaneka.planttech2.blocks.entity.cable.CableBlockEntity;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class CableBlock extends Block implements EntityBlock
{
	public static final IntegerProperty
			NORTH = IntegerProperty.create("north", 0, 3),
			EAST = IntegerProperty.create("east", 0, 3),
			SOUTH = IntegerProperty.create("south", 0, 3),
			WEST = IntegerProperty.create("west", 0, 3),
			UP = IntegerProperty.create("up", 0, 3),
			DOWN = IntegerProperty.create("down", 0, 3);
	public static final Map<Direction, IntegerProperty> directions = new HashMap<Direction, IntegerProperty>()
	{
		private static final long serialVersionUID = 1L;
		{
			put(Direction.NORTH, NORTH);
			put(Direction.EAST, EAST);
			put(Direction.SOUTH, SOUTH);
			put(Direction.WEST, WEST);
			put(Direction.UP, UP);
			put(Direction.DOWN, DOWN);
		}
	};

	private static final VoxelShape core_voxel = Block.box(7F, 7F, 7F, 9F, 9F, 9F);
	private static final Map<Direction, VoxelShape> connection_voxels = new HashMap<Direction, VoxelShape>()
	{
		private static final long serialVersionUID = 1L;
		{
			put(Direction.DOWN, Block.box(6F, 0F, 6F, 10F, 2F, 10F)); // DOWN
			put(Direction.UP, Block.box(6F, 14F, 6F, 10F, 16F, 10F)); // UP
			put(Direction.NORTH, Block.box(6F, 6F, 0F, 10F, 10F, 2F)); // NORTH
			put(Direction.SOUTH, Block.box(6F, 6F, 14F, 10F, 10F, 16F)); // SOUTH
			put(Direction.WEST, Block.box(0F, 6F, 6F, 2F, 10F, 10F)); // WEST
			put(Direction.EAST, Block.box(14F, 6F, 6F, 16F, 10F, 10F));// EAST
		}
	};

	private static final Map<Direction, VoxelShape> cable_voxels = new HashMap<Direction, VoxelShape>()
	{
		private static final long serialVersionUID = 1L;
		{
			put(Direction.DOWN, Block.box(7F, 0F, 7F, 9F, 7F, 9F)); // DOWN
			put(Direction.UP, Block.box(7F, 9F, 7F, 9F, 16F, 9F)); // UP
			put(Direction.NORTH, Block.box(7F, 7F, 0F, 9F, 9F, 7F)); // NORTH
			put(Direction.SOUTH, Block.box(7F, 7F, 9F, 9F, 9F, 16F)); // SOUTH
			put(Direction.WEST, Block.box(0F, 7F, 7F, 7F, 9F, 9F)); // WEST
			put(Direction.EAST, Block.box(9F, 7F, 7F, 16F, 9F, 9F));// EAST
		}
	};

	public CableBlock()
	{
		super(Block.Properties.of(Material.METAL).strength(0.5F));
		this.registerDefaultState(stateDefinition.any().setValue(NORTH, 0).setValue(EAST, 0).setValue(SOUTH, 0).setValue(WEST, 0).setValue(UP, 0).setValue(DOWN, 0));
	}

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray)
	{
		if (!level.isClientSide && hand.equals(InteractionHand.MAIN_HAND) && player.getMainHandItem().getItem().equals(ModItems.WRENCH))
		{
			Vec3 hitvec = ray.getLocation();
			hitvec = hitvec.add(-pos.getX(), -pos.getY(), -pos.getZ());
			VoxelShape tempshape;
			for (Direction dir : Direction.values())
			{
				tempshape = connection_voxels.get(dir);
				if (tempshape.min(Direction.Axis.X) <= hitvec.x && tempshape.max(Direction.Axis.X) >= hitvec.x)
				{
					if (tempshape.min(Direction.Axis.Y) <= hitvec.y && tempshape.max(Direction.Axis.Y) >= hitvec.y)
					{
						if (tempshape.min(Direction.Axis.Z) <= hitvec.z && tempshape.max(Direction.Axis.Z) >= hitvec.z)
						{
							CableBlockEntity te = getTECable(level, pos);
							if (te != null)
							{
								te.rotateConnection(dir);
								level.setBlockAndUpdate(pos, getCurrentState(state, level, pos));
								return InteractionResult.SUCCESS;
							}
						}
					}
				}
			}
			/*
			 * Integer result = getConnectionLookedOn(worldIn, pos,
			 * player.getPositionVector().add(0, player.getEyeHeight(), 0),
			 * player.getPositionVector().add(0f, player.getEyeHeight(),
			 * 0f).add(player.getLookVec().scale(5))); if (result != -1) { CableBlockEntity
			 * te = getTECable(worldIn, pos); if (te != null) { te.rotateConnection(result);
			 * } }
			 */
		}
		return InteractionResult.PASS;
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldstate, boolean bool)
	{
		CableBlockEntity te = getTECable(level, pos);
		if (te != null)
		{
			te.initCable(state);
			level.setBlockAndUpdate(pos, getCurrentState(state, level, pos));
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
	{
		CableBlockEntity te = getTECable(level, pos);
		if (te != null)
		{
			te.deleteCable();
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	public RenderShape getRenderShape(BlockState state)
	{
		return RenderShape.MODEL;
	}

	/*
	 * public int getConnectionLookedOn(Level level, BlockPos pos, Vec3d start,
	 * Vec3d end) { HashMap<Integer, AxisAlignedBB> boxes =
	 * getCollisionBoxListConnectionsList(worldIn, pos); HashMap<Integer,
	 * RayTraceResult> rayTraces = rayTraceList(pos, start, end, boxes);
	 *
	 * double d1 = 0.0D; int returnval = -1; for (HashMap.Entry<Integer,
	 * RayTraceResult> entry : rayTraces.entrySet()) { double d0 =
	 * entry.getValue().getHitVec().squareDistanceTo(end);
	 *
	 * if (d0 > d1) { d1 = d0; returnval = entry.getKey();
	 *
	 * } } return returnval; }
	 *
	 *
	 * private HashMap<Integer, AxisAlignedBB>
	 * getCollisionBoxListConnectionsList(Level world, BlockPos pos) {
	 * HashMap<Integer, AxisAlignedBB> list = new HashMap<Integer, AxisAlignedBB>();
	 * CableBlockEntity te = getTECable(world, pos); if (te != null) { for (Direction
	 * facing : Direction.values()) { if (te.getConnection(facing) > 1) {
	 * list.put(facing.getIndex(), AABB_CONNECTIONS[facing.getIndex()]); } } }
	 * return list; }
	 */

	/*@Nullable
	protected HashMap<Integer, RayTraceResult> rayTraceList(BlockPos pos, Vec3d start, Vec3d end, HashMap<Integer, AxisAlignedBB> boxes) {
		HashMap<Integer, RayTraceResult> list = new HashMap<Integer, RayTraceResult>();*/
	/*
	 * boxes.forEach((k, v) -> { Vec3d vec3d = start.subtract((double) pos.getX(),
	 * (double) pos.getY(), (double) pos.getZ()); Vec3d vec3d1 =
	 * end.subtract((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
	 * RayTraceResult raytraceresult = v.intersects(vec3d, vec3d1); if
	 * (raytraceresult != null) { list.put(k, new
	 * RayTraceResult(raytraceresult.getHitVec().add((double) pos.getX(), (double)
	 * pos.getY(), (double) pos.getZ()), raytraceresult, pos)); } });
	 */
	/*return list;}*/

	/*@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor)
	{
		System.out.println("onNeighborChange called");
		CableBlockEntity cable = getTECable((World) world, pos);
		if (cable != null)
		{
			cable.checkConnections("onNeighborChange");
		}
	}*/

	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
	{
		super.neighborChanged(state, world, pos, block, fromPos, isMoving);
		CableBlockEntity cable = getTECable(world, pos);
		if (cable != null)
		{
			cable.checkConnections(true);
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		BlockGetter level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = defaultBlockState();
		for (Direction facing : Direction.values())
		{

			BlockState stateDirection = level.getBlockState(pos.relative(facing));
			BlockEntity te = level.getBlockEntity(pos.relative(facing));

			if (stateDirection.getBlock() instanceof CableBlock)
			{
				state.setValue(directions.get(facing), 1);
			}
			else if (te != null)
			{
				if (te.getCapability(CapabilityEnergy.ENERGY).isPresent())
				{

					state.setValue(directions.get(facing), 2);
				}
			}
			else
			{
				state.setValue(directions.get(facing), 0);
			}
		}
		return state;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos)
	{
		return getCurrentState(state, world, currentPos);
	}

	public BlockState getCurrentState(BlockState state, LevelAccessor level, BlockPos pos)
	{
		CableBlockEntity te = getTECable((Level) level, pos);
		if (te != null)
		{
			te.checkConnections(false);
			return state.setValue(UP, te.getConnection(Direction.UP)).setValue(DOWN, te.getConnection(Direction.DOWN)).setValue(EAST, te.getConnection(Direction.EAST))
					.setValue(WEST, te.getConnection(Direction.WEST)).setValue(NORTH, te.getConnection(Direction.NORTH)).setValue(SOUTH, te.getConnection(Direction.SOUTH));
		}
		return state;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
	{
		return getCombinedShape(state);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
	{
		return getCombinedShape(state);
	}

	private VoxelShape getCombinedShape(BlockState state)
	{
		VoxelShape shape = core_voxel;
		for (Direction dir : Direction.values())
		{
			int value = state.getValue(directions.get(dir));
			if (value > 0)
			{
				shape = Shapes.or(shape, cable_voxels.get(dir));
				if (value > 1)
				{
					shape = Shapes.or(shape, connection_voxels.get(dir));
				}
			}
		}
		return shape;
	}

	private CableBlockEntity getTECable(Level level, BlockPos pos)
	{
		BlockEntity te = level.getBlockEntity(pos);
		return te instanceof CableBlockEntity ? (CableBlockEntity) te : null;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CableBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> be) {
		if(!level.isClientSide) return CableBlockEntity::tick;
		return null;
	}
}
