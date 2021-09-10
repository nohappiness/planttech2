package net.kaneka.planttech2.blocks.machines;

import net.kaneka.planttech2.blocks.ModBlockEntityBlock;
import net.kaneka.planttech2.blocks.entity.cable.TestCableBlockEntity;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
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
import net.minecraft.world.level.block.DaylightDetectorBlock;
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
import net.minecraftforge.common.util.Constants.BlockFlags;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TestCableBlock extends ModBlockEntityBlock
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
                    shape = Shapes.or(shape, CABLE_VOXELS.get(direction));
                    if (state > 1)
                        shape = Shapes.or(shape, CONNECTION_VOXELS.get(direction));
                }
            }
    	}
    	return shape; 
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
    	//Direction indexes are DUNSWE
    	return shapes[Math.min(2, state.getValue(DOWN))][Math.min(2, state.getValue(UP))][Math.min(2, state.getValue(NORTH))][Math.min(2, state.getValue(SOUTH))][Math.min(2, state.getValue(WEST))][Math.min(2, state.getValue(EAST))];
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray)
    {
        if (!level.isClientSide() && hand.equals(InteractionHand.MAIN_HAND) && player.getMainHandItem().getItem().equals(ModItems.WRENCH))
        {
            Vec3 hitvec = ray.getLocation();
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
                            TestCableBlockEntity te = getTECable(level, pos);
                            if (te != null)
                            {
                                te.rotateConnection(dir);
                                level.setBlock(pos, getCurrentState(state, level, pos), BlockFlags.DEFAULT);
                                return InteractionResult.SUCCESS;
                            }
                        }
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldstate, boolean bool)
    {
        if (!world.isClientSide() && state.getBlock() != oldstate.getBlock())
        {
            TestCableBlockEntity te = getTECable(world, pos);
            if (te != null)
            {
                world.setBlock(pos, getCurrentState(state, world, pos), BlockFlags.DEFAULT);
                te.initCable();
            }
        }
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type)
    {
        return !world.isClientSide && type == ModTileEntities.CABLE_TE ? TestCableBlockEntity::tick : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TestCableBlockEntity(blockPos, blockState);
    }


    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (!level.isClientSide() && state.getBlock() != newState.getBlock())
        {
            modifyCable(level, pos, TestCableBlockEntity::setRemoved);
            level.removeBlockEntity(pos);
        }
    }

    @SuppressWarnings("deprecation")
	@Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if (!world.isClientSide())
            checkConnections(world, pos);
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
                state.setValue(DIRECTIONS.get(facing), 1);
            else if (te != null && te.getCapability(CapabilityEnergy.ENERGY).isPresent())
                state.setValue(DIRECTIONS.get(facing), 2);
            else
                state.setValue(DIRECTIONS.get(facing), 0);
        }
        return state;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        return getCurrentState(state, (Level) level, currentPos);
    }

    public BlockState getCurrentState(BlockState state, Level world, BlockPos pos)
    {
        TestCableBlockEntity cable = getTECable(world, pos);
        if (cable != null)
        {
            cable.checkConnections();
            return state.setValue(UP, cable.getConnection(Direction.UP)).setValue(DOWN, cable.getConnection(Direction.DOWN)).setValue(EAST, cable.getConnection(Direction.EAST))
                    .setValue(WEST, cable.getConnection(Direction.WEST)).setValue(NORTH, cable.getConnection(Direction.NORTH)).setValue(SOUTH, cable.getConnection(Direction.SOUTH));
        }
        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
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

    private TestCableBlockEntity getTECable(Level world, BlockPos pos)
    {
        BlockEntity BlockEntity = world.getBlockEntity(pos);
        return BlockEntity instanceof TestCableBlockEntity ? (TestCableBlockEntity) BlockEntity : null;
    }

    private void checkConnections(Level world, BlockPos pos)
    {
        modifyCable(world, pos, TestCableBlockEntity::checkConnections);
    }

    private void modifyCable(Level world, BlockPos pos, Consumer<TestCableBlockEntity> modification)
    {
        TestCableBlockEntity cable = getTECable(world, pos);
        if (cable != null)
            modification.accept(cable);
    }

    public static class ConnnectionState
    {

    }
}
