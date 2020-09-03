package net.kaneka.planttech2.blocks.machines;

import net.kaneka.planttech2.blocks.baseclasses.BaseBlock;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.tileentity.cable.TestCableTileEntity;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
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

import java.util.*;
import java.util.function.Consumer;

public class TestCableBlock extends BaseBlock
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

    private static final VoxelShape POST = Block.makeCuboidShape(7F, 7F, 7F, 9F, 9F, 9F);
    private static final Map<Direction, VoxelShape> CONNECTION_VOXELS = new HashMap<Direction, VoxelShape>()
    {
        private static final long serialVersionUID = 1L;
        {
            put(Direction.DOWN, Block.makeCuboidShape(6F, 0F, 6F, 10F, 2F, 10F)); // DOWN
            put(Direction.UP, Block.makeCuboidShape(6F, 14F, 6F, 10F, 16F, 10F)); // UP
            put(Direction.NORTH, Block.makeCuboidShape(6F, 6F, 0F, 10F, 10F, 2F)); // NORTH
            put(Direction.SOUTH, Block.makeCuboidShape(6F, 6F, 14F, 10F, 10F, 16F)); // SOUTH
            put(Direction.WEST, Block.makeCuboidShape(0F, 6F, 6F, 2F, 10F, 10F)); // WEST
            put(Direction.EAST, Block.makeCuboidShape(14F, 6F, 6F, 16F, 10F, 10F)); // EAST
        }
    };

    private static final Map<Direction, VoxelShape> CABLE_VOXELS = new HashMap<Direction, VoxelShape>()
    {
        private static final long serialVersionUID = 1L;
        {
            put(Direction.DOWN, Block.makeCuboidShape(7F, 0F, 7F, 9F, 7F, 9F)); // DOWN
            put(Direction.UP, Block.makeCuboidShape(7F, 9F, 7F, 9F, 16F, 9F));  // UP
            put(Direction.NORTH, Block.makeCuboidShape(7F, 7F, 0F, 9F, 9F, 7F));  // NORTH
            put(Direction.SOUTH, Block.makeCuboidShape(7F, 7F, 9F, 9F, 9F, 16F));  // SOUTH
            put(Direction.WEST, Block.makeCuboidShape(0F, 7F, 7F, 7F, 9F, 9F));  // WEST
            put(Direction.EAST, Block.makeCuboidShape(9F, 7F, 7F, 16F, 9F, 9F));  // EAST
        }
    };
    
    protected final VoxelShape[][][][][][] shapes = new  VoxelShape[3][3][3][3][3][3];


    public TestCableBlock()
    {
        super(Block.Properties.create(Material.IRON).hardnessAndResistance(0.5F), "cable", ModCreativeTabs.groupblocks, true);
        this.setDefaultState(stateContainer.getBaseState().with(NORTH, 0).with(EAST, 0).with(SOUTH, 0).with(WEST, 0).with(UP, 0).with(DOWN, 0));
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
                             {
                            	 shapes[d][u][n][s][w][e] = getCombinedShape(d,u,n,s,w,e);
                             }
    }
    
    private  VoxelShape getCombinedShape(int... states)
    {
    	VoxelShape shape = POST;
    	if(states.length == 6)
    	{
            for (int i=0;i<6;i++)
            {
                int state = states[i];
                Direction direction = Direction.byIndex(i);
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
    	return shapes[Math.min(2, state.get(DOWN))][Math.min(2, state.get(UP))][Math.min(2, state.get(NORTH))][Math.min(2, state.get(SOUTH))][Math.min(2, state.get(WEST))][Math.min(2, state.get(EAST))];
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray)
    {
        if (!worldIn.isRemote() && hand.equals(Hand.MAIN_HAND) && player.getHeldItemMainhand().getItem().equals(ModItems.WRENCH))
        {
            Vector3d hitvec = ray.getHitVec();
            hitvec = hitvec.add(-pos.getX(), -pos.getY(), -pos.getZ());
            VoxelShape tempshape;
            for (Direction dir : Direction.values())
            {
                tempshape = CONNECTION_VOXELS.get(dir);
                if (tempshape.getStart(Direction.Axis.X) <= hitvec.x && tempshape.getEnd(Direction.Axis.X) >= hitvec.x)
                {
                    if (tempshape.getStart(Direction.Axis.Y) <= hitvec.y && tempshape.getEnd(Direction.Axis.Y) >= hitvec.y)
                    {
                        if (tempshape.getStart(Direction.Axis.Z) <= hitvec.z && tempshape.getEnd(Direction.Axis.Z) >= hitvec.z)
                        {
                            TestCableTileEntity te = getTECable(worldIn, pos);
                            if (te != null)
                            {
                                te.rotateConnection(dir);
                                worldIn.setBlockState(pos, getCurrentState(state, worldIn, pos), 3);
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
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldstate, boolean bool)
    {
        if (!world.isRemote() && state.getBlock() != oldstate.getBlock())
        {
            TestCableTileEntity te = getTECable(world, pos);
            if (te != null)
            {
                te.initCable();
                world.setBlockState(pos, getCurrentState(state, world, pos), 3);
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

    public Item createItemBlock()
    {
        return new BlockItem(this, new Item.Properties().group(ModCreativeTabs.groupmain)).setRegistryName("cable");
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (!worldIn.isRemote() && state.getBlock() != newState.getBlock())
        {
            modifyCable(worldIn, pos, TestCableTileEntity::remove);
            worldIn.removeTileEntity(pos);
        }
    }

    @SuppressWarnings("deprecation")
	@Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if (!world.isRemote())
            checkConnections(world, pos);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        IBlockReader world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = getDefaultState();
        for (Direction facing : Direction.values())
        {
            BlockState stateDirection = world.getBlockState(pos.offset(facing));
            TileEntity te = world.getTileEntity(pos.offset(facing));
            if (stateDirection.getBlock() instanceof CableBlock)
                state.with(DIRECTIONS.get(facing), 1);
            else if (te != null && te.getCapability(CapabilityEnergy.ENERGY).isPresent())
                state.with(DIRECTIONS.get(facing), 2);
            else
                state.with(DIRECTIONS.get(facing), 0);
        }
        return state;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos)
    {
        return getCurrentState(state, (World) world, currentPos);
    }

    public BlockState getCurrentState(BlockState state, World world, BlockPos pos)
    {
        if (!world.isRemote())
        {
            TestCableTileEntity cable = getTECable(world, pos);
            if (cable != null)
            {
                cable.checkConnections();
                return state.with(UP, cable.getConnection(Direction.UP)).with(DOWN, cable.getConnection(Direction.DOWN)).with(EAST, cable.getConnection(Direction.EAST))
                        .with(WEST, cable.getConnection(Direction.WEST)).with(NORTH, cable.getConnection(Direction.NORTH)).with(SOUTH, cable.getConnection(Direction.SOUTH));
            }
        }
        return state;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
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
        TileEntity tileentity = world.getTileEntity(pos);
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
