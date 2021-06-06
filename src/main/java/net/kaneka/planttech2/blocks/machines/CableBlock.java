package net.kaneka.planttech2.blocks.machines;

import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.tileentity.cable.CableTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

import java.util.HashMap;
import java.util.Map;

public class CableBlock extends Block
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
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray)
	{
		if (!worldIn.isClientSide && hand.equals(Hand.MAIN_HAND) && player.getMainHandItem().getItem().equals(ModItems.WRENCH))
		{
			Vector3d hitvec = ray.getLocation();
			hitvec = hitvec.add(-pos.getX(), -pos.getY(), -pos.getZ());
			VoxelShape tempshape;
			for (Direction dir : Direction.values())
			{
				tempshape = connection_voxels.get(dir);
				if (tempshape.min(Axis.X) <= hitvec.x && tempshape.max(Axis.X) >= hitvec.x)
				{
					if (tempshape.min(Axis.Y) <= hitvec.y && tempshape.max(Axis.Y) >= hitvec.y)
					{
						if (tempshape.min(Axis.Z) <= hitvec.z && tempshape.max(Axis.Z) >= hitvec.z)
						{
							CableTileEntity te = getTECable(worldIn, pos);
							if (te != null)
							{
								te.rotateConnection(dir);
								worldIn.setBlockAndUpdate(pos, getCurrentState(state, worldIn, pos));
								return ActionResultType.SUCCESS;
							}
						}
					}
				}
			}
			/*
			 * Integer result = getConnectionLookedOn(worldIn, pos,
			 * player.getPositionVector().add(0, player.getEyeHeight(), 0),
			 * player.getPositionVector().add(0f, player.getEyeHeight(),
			 * 0f).add(player.getLookVec().scale(5))); if (result != -1) { CableTileEntity
			 * te = getTECable(worldIn, pos); if (te != null) { te.rotateConnection(result);
			 * } }
			 */
		}
		return ActionResultType.PASS;
	}

	@Override
	public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldstate, boolean bool)
	{
		CableTileEntity te = getTECable(world, pos);
		if (te != null)
		{
			te.initCable(state);
			world.setBlockAndUpdate(pos, getCurrentState(state, world, pos));
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new CableTileEntity();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
	{
		CableTileEntity te = getTECable(worldIn, pos);
		if (te != null)
		{
			te.deleteCable();
		}
		super.onRemove(state, worldIn, pos, newState, isMoving);
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	/*
	 * public int getConnectionLookedOn(World worldIn, BlockPos pos, Vec3d start,
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
	 * getCollisionBoxListConnectionsList(World world, BlockPos pos) {
	 * HashMap<Integer, AxisAlignedBB> list = new HashMap<Integer, AxisAlignedBB>();
	 * CableTileEntity te = getTECable(world, pos); if (te != null) { for (Direction
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
		CableTileEntity cable = getTECable((World) world, pos);
		if (cable != null)
		{
			cable.checkConnections("onNeighborChange");
		}
	}*/

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
	{
		super.neighborChanged(state, world, pos, block, fromPos, isMoving);
		CableTileEntity cable = getTECable(world, pos);
		if (cable != null)
		{
			cable.checkConnections(true);
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		IBlockReader world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = defaultBlockState();
		for (Direction facing : Direction.values())
		{

			BlockState stateDirection = world.getBlockState(pos.relative(facing));
			TileEntity te = world.getBlockEntity(pos.relative(facing));

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
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos)
	{
		return getCurrentState(state, world, currentPos);
	}

	public BlockState getCurrentState(BlockState state, IWorld world, BlockPos pos)
	{
		CableTileEntity te = getTECable((World) world, pos);
		if (te != null)
		{
			te.checkConnections(false);
			return state.setValue(UP, te.getConnection(Direction.UP)).setValue(DOWN, te.getConnection(Direction.DOWN)).setValue(EAST, te.getConnection(Direction.EAST))
					.setValue(WEST, te.getConnection(Direction.WEST)).setValue(NORTH, te.getConnection(Direction.NORTH)).setValue(SOUTH, te.getConnection(Direction.SOUTH));
		}
		return state;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return getCombinedShape(state);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
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
				shape = VoxelShapes.or(shape, cable_voxels.get(dir));
				if (value > 1)
				{
					shape = VoxelShapes.or(shape, connection_voxels.get(dir));
				}
			}
		}
		return shape;
	}

	private CableTileEntity getTECable(World world, BlockPos pos)
	{
		TileEntity te = world.getBlockEntity(pos);
		return te instanceof CableTileEntity ? (CableTileEntity) te : null;
	}
}
