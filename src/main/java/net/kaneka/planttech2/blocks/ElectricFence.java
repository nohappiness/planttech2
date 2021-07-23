package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.BaseElectricFence;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public class ElectricFence extends BaseElectricFence
{
    public static final BooleanProperty NORTH = FenceBlock.NORTH;
    public static final BooleanProperty EAST = FenceBlock.EAST;
    public static final BooleanProperty SOUTH = FenceBlock.SOUTH;
    public static final BooleanProperty WEST = FenceBlock.WEST;

    public static final VoxelShape POST = Block.box(7.5D, 0.10D, 7.5D, 8.5D, 15.90D, 8.5D);
    public static final VoxelShape NEGATIVE_Z = Block.box(7.0D, 0.10D, 0.10D, 9.0D, 15.90D, 8.5D);
    public static final VoxelShape POSITIVE_Z = Block.box(7.0D, 0.10D, 7.5D, 9.0D, 15.90D, 15.90D);
    public static final VoxelShape NEGATIVE_X = Block.box(0.10D, 0.10D, 7.0D, 8.5D, 15.90D, 9.0D);
    public static final VoxelShape POSITIVE_X = Block.box(7.5D, 0.10D, 7.0D, 15.90D, 15.90D, 9.0D);

    public ElectricFence(BlockBehaviour.Properties property)
    {
        super(property);
        registerDefaultState(defaultBlockState()
                .setValue(ELECTRIC_POWER, 0)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false));
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor levelIn, BlockPos currentPos, BlockPos facingPos)
    {
        return getState(this.defaultBlockState()
                .setValue(ELECTRIC_POWER, calculatePower((Level) levelIn, currentPos)), (Level) levelIn, currentPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return getState(this.defaultBlockState()
                .setValue(ELECTRIC_POWER, calculatePower(context.getLevel(), context.getClickedPos())), context.getLevel(), context.getClickedPos());
    }

    public boolean canConnectTo(Level level, BlockPos pos, Direction direction)
    {
        BlockState state = level.getBlockState(pos.relative(direction));
        Block block = state.getBlock();
        return ((block instanceof BaseElectricFence || block instanceof ElectricFenceGate) || state.isFaceSturdy(level, pos.relative(direction), direction.getOpposite()));
    }

    private BlockState getState(BlockState state, Level levelIn, BlockPos pos)
    {
        return state
                .setValue(NORTH, canConnectTo(levelIn, pos, Direction.SOUTH))
                .setValue(EAST, canConnectTo(levelIn, pos, Direction.WEST))
                .setValue(SOUTH, canConnectTo(levelIn, pos, Direction.NORTH))
                .setValue(WEST, canConnectTo(levelIn, pos, Direction.EAST));
    }

    @SuppressWarnings("deprecation")
	@Override
    public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context)
    {
        VoxelShape shape = POST;
        if (state.getValue(NORTH))
            shape = Shapes.or(shape, POSITIVE_Z);
        if (state.getValue(SOUTH))
            shape = Shapes.or(shape, NEGATIVE_Z);
        if (state.getValue(WEST))
            shape = Shapes.or(shape, POSITIVE_X);
        if (state.getValue(EAST))
            shape = Shapes.or(shape, NEGATIVE_X);
        return shape;
    }
}
