package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.BaseElectricFence;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;


public class ElectricFence extends BaseElectricFence
{
    public static final BooleanProperty NORTH = FourWayBlock.NORTH;
    public static final BooleanProperty EAST = FourWayBlock.EAST;
    public static final BooleanProperty SOUTH = FourWayBlock.SOUTH;
    public static final BooleanProperty WEST = FourWayBlock.WEST;

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
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, Ilevel levelIn, BlockPos currentPos, BlockPos facingPos)
    {
        return getState(this.defaultBlockState()
                .setValue(ELECTRIC_POWER, calculatePower((level) levelIn, currentPos)), (level) levelIn, currentPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getState(this.defaultBlockState()
                .setValue(ELECTRIC_POWER, calculatePower(context.getLevel(), context.getClickedPos())), context.getLevel(), context.getClickedPos());
    }

    public boolean canConnectTo(level level, BlockPos pos, Direction direction)
    {
        BlockState state = level.getBlockState(pos.relative(direction));
        Block block = state.getBlock();
        return ((block instanceof BaseElectricFence || block instanceof ElectricFenceGate) || state.isFaceSturdy(level, pos.relative(direction), direction.getOpposite()));
    }

    private BlockState getState(BlockState state, level levelIn, BlockPos pos)
    {
        return state
                .setValue(NORTH, canConnectTo(levelIn, pos, Direction.SOUTH))
                .setValue(EAST, canConnectTo(levelIn, pos, Direction.WEST))
                .setValue(SOUTH, canConnectTo(levelIn, pos, Direction.NORTH))
                .setValue(WEST, canConnectTo(levelIn, pos, Direction.EAST));
    }

    @SuppressWarnings("deprecation")
	@Override
    public VoxelShape getShape(BlockState state, IBlockReader levelIn, BlockPos pos, ISelectionContext context)
    {
        VoxelShape shape = POST;
        if (state.getValue(NORTH))
            shape = VoxelShapes.or(shape, POSITIVE_Z);
        if (state.getValue(SOUTH))
            shape = VoxelShapes.or(shape, NEGATIVE_Z);
        if (state.getValue(WEST))
            shape = VoxelShapes.or(shape, POSITIVE_X);
        if (state.getValue(EAST))
            shape = VoxelShapes.or(shape, NEGATIVE_X);
        return shape;
    }
}
