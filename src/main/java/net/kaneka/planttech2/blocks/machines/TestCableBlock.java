package net.kaneka.planttech2.blocks.machines;

import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.tileentity.cable.TestCableTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
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
import java.util.function.Consumer;

import static net.minecraftforge.common.util.Constants.BlockFlags;

import net.minecraftforge.common.util.Constants.BlockFlags;

public class TestCableBlock extends Block
{
    public static final IntegerProperty
            NORTH = IntegerProperty.create("north", 0, 3),
            EAST = IntegerProperty.create("east", 0, 3),
            SOUTH = IntegerProperty.create("south", 0, 3),
            WEST = IntegerProperty.create("west", 0, 3),
            UP = IntegerProperty.create("up", 0, 3),
            DOWN = IntegerProperty.create("down", 0, 3);

    public static final Map<Direction, IntegerProperty> DIRECTIONS = new HashMap<Direction, IntegerProperty>()
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

    private static final VoxelShape POST = box(7F, 7F, 7F, 9F, 9F, 9F);
    private static final Map<Direction, VoxelShape> CONNECTION_VOXELS = new HashMap<Direction, VoxelShape>()
    {
        private static final long serialVersionUID = 1L;
        {
            put(Direction.DOWN, box(6F, 0F, 6F, 10F, 2F, 10F)); // DOWN
            put(Direction.UP, box(6F, 14F, 6F, 10F, 16F, 10F)); // UP
            put(Direction.NORTH, box(6F, 6F, 0F, 10F, 10F, 2F)); // NORTH
            put(Direction.SOUTH, box(6F, 6F, 14F, 10F, 10F, 16F)); // SOUTH
            put(Direction.WEST, box(0F, 6F, 6F, 2F, 10F, 10F)); // WEST
            put(Direction.EAST, box(14F, 6F, 6F, 16F, 10F, 10F)); // EAST
        }
    };

    private static final Map<Direction, VoxelShape> CABLE_VOXELS = new HashMap<Direction, VoxelShape>()
    {
        private static final long serialVersionUID = 1L;
        {
            put(Direction.DOWN, box(7F, 0F, 7F, 9F, 7F, 9F)); // DOWN
            put(Direction.UP, box(7F, 9F, 7F, 9F, 16F, 9F)); // UP
            put(Direction.NORTH, box(7F, 7F, 0F, 9F, 9F, 7F)); // NORTH
            put(Direction.SOUTH, box(7F, 7F, 9F, 9F, 9F, 16F)); // SOUTH
            put(Direction.WEST, box(0F, 7F, 7F, 7F, 9F, 9F)); // WEST
            put(Direction.EAST, box(9F, 7F, 7F, 16F, 9F, 9F));// EAST
        }
    };
    protected final VoxelShape[][][][][][] shapes = new  VoxelShape[3][3][3][3][3][3];

    public TestCableBlock()
    {
        super(Block.Properties.of(Material.METAL).strength(0.5F));
        this.registerDefaultState(stateDefinition.any().setValue(NORTH, 0).setValue(EAST, 0).setValue(SOUTH, 0).setValue(WEST, 0).setValue(UP, 0).setValue(DOWN, 0));
        initShapes();
    }
    
    private void initShapes()
    {
    	//Direction indexes are DUNSWE
    	 for (int d=0;d<3;d++)
             for (int u=0;u<3;u++)
                 for (int n=0;n<3;n++)
                     for (int s=0;s<3;s++)
                         for (int w=0;w<3;w++)
                             for (int e=0;e<3;e++)
                            	 shapes[d][u][n][s][w][e] = getCombinedShape(d,u,n,s,w,e);
    }
    
    private VoxelShape getCombinedShape(int... states)
    {
    	VoxelShape shape = POST;
    	if(states.length == 6)
    	{
            for (int i=0;i<6;i++)
            {
                int state = states[i];
                Direction direction = Direction.from3DDataValue(i);
                if (state > 0)
                {
                    shape = VoxelShapes.or(shape, CABLE_VOXELS.get(direction));
                    if (state > 1)
                        shape = VoxelShapes.or(shape, CONNECTION_VOXELS.get(direction));
                }
            }
    	}
    	return shape; 
    }
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
    	//Direction indexes are DUNSWE
    	return shapes[Math.min(2, state.getValue(DOWN))][Math.min(2, state.getValue(UP))][Math.min(2, state.getValue(NORTH))][Math.min(2, state.getValue(SOUTH))][Math.min(2, state.getValue(WEST))][Math.min(2, state.getValue(EAST))];
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray)
    {
        if (!worldIn.isClientSide() && hand.equals(Hand.MAIN_HAND) && player.getMainHandItem().getItem().equals(ModItems.WRENCH))
        {
            Vector3d hitvec = ray.getLocation();
            hitvec = hitvec.add(-pos.getX(), -pos.getY(), -pos.getZ());
            VoxelShape tempshape;
            for (Direction dir : Direction.values())
            {
                tempshape = CONNECTION_VOXELS.get(dir);
                if (tempshape.min(Direction.Axis.X) <= hitvec.x && tempshape.max(Direction.Axis.X) >= hitvec.x)
                {
                    if (tempshape.min(Direction.Axis.Y) <= hitvec.y && tempshape.max(Direction.Axis.Y) >= hitvec.y)
                    {
                        if (tempshape.min(Direction.Axis.Z) <= hitvec.z && tempshape.max(Direction.Axis.Z) >= hitvec.z)
                        {
                            TestCableTileEntity te = getTECable(worldIn, pos);
                            if (te != null)
                            {
                                te.rotateConnection(dir);
                                worldIn.setBlock(pos, getCurrentState(state, worldIn, pos), BlockFlags.DEFAULT);
                                return ActionResultType.SUCCESS;
                            }
                        }
                    }
                }
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldstate, boolean bool)
    {
        if (!world.isClientSide() && state.getBlock() != oldstate.getBlock())
        {
            TestCableTileEntity te = getTECable(world, pos);
            if (te != null)
            {
                world.setBlock(pos, getCurrentState(state, world, pos), BlockFlags.DEFAULT);
                te.initCable();
            }
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
        return new TestCableTileEntity();
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (!worldIn.isClientSide() && state.getBlock() != newState.getBlock())
        {
            modifyCable(worldIn, pos, TestCableTileEntity::setRemoved);
            worldIn.removeBlockEntity(pos);
        }
    }

    @SuppressWarnings("deprecation")
	@Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if (!world.isClientSide())
            checkConnections(world, pos);
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
                state.setValue(DIRECTIONS.get(facing), 1);
            else if (te != null && te.getCapability(CapabilityEnergy.ENERGY).isPresent())
                state.setValue(DIRECTIONS.get(facing), 2);
            else
                state.setValue(DIRECTIONS.get(facing), 0);
        }
        return state;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos)
    {
        return getCurrentState(state, (World) world, currentPos);
    }

    public BlockState getCurrentState(BlockState state, World world, BlockPos pos)
    {
        TestCableTileEntity cable = getTECable(world, pos);
        if (cable != null)
        {
            cable.checkConnections();
            return state.setValue(UP, cable.getConnection(Direction.UP)).setValue(DOWN, cable.getConnection(Direction.DOWN)).setValue(EAST, cable.getConnection(Direction.EAST))
                    .setValue(WEST, cable.getConnection(Direction.WEST)).setValue(NORTH, cable.getConnection(Direction.NORTH)).setValue(SOUTH, cable.getConnection(Direction.SOUTH));
        }
        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

//    private VoxelShape getCombinedShape(BlockState state)
//    {
//        VoxelShape shape = POST;
//        for (Direction direction : Direction.values())
//        {
//            int value = state.get(DIRECTIONS.get(direction));
//            shape = getCombinedShape(shape, value, direction);
//        }
//        return shape;
//    }

    private TestCableTileEntity getTECable(World world, BlockPos pos)
    {
        TileEntity tileentity = world.getBlockEntity(pos);
        return tileentity instanceof TestCableTileEntity ? (TestCableTileEntity) tileentity : null;
    }

    private void checkConnections(World world, BlockPos pos)
    {
        modifyCable(world, pos, TestCableTileEntity::checkConnections);
    }

    private void modifyCable(World world, BlockPos pos, Consumer<TestCableTileEntity> modification)
    {
        TestCableTileEntity cable = getTECable(world, pos);
        if (cable != null)
            modification.accept(cable);
    }

    public static class ConnnectionState
    {

    }
}
