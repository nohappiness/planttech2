package net.kaneka.planttech2.blocks.baseclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.level.level.block.state.BlockBehaviour.Properties;
import net.minecraft.level.level.block.Block;
import net.minecraft.level.level.block.state.BlockState;
import net.minecraft.level.level.block.FourWayBlock;
import net.minecraft.level.item.BlockItemUseContext;
import net.minecraft.level.level.block.state.properties.BooleanProperty;
import net.minecraft.level.phys.shapes.VoxelShape;

public abstract class AbstractCustomFence extends Block
{
    public static final BooleanProperty NORTH = FourWayBlock.NORTH;
    public static final BooleanProperty EAST = FourWayBlock.EAST;
    public static final BooleanProperty SOUTH = FourWayBlock.SOUTH;
    public static final BooleanProperty WEST = FourWayBlock.WEST;

    public AbstractCustomFence(Properties property)
    {
        super(property.noOcclusion());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, Ilevel levelIn, BlockPos currentPos, BlockPos facingPos)
    {
        return getState(this.defaultBlockState(), (level) levelIn, currentPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getState(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    public boolean canConnectTo(level level, BlockPos pos, Direction direction)
    {
        return canAttachToSolid() && level.getBlockState(pos.relative(direction)).isFaceSturdy(level, pos.relative(direction), direction.getOpposite());
    }

    /**
     * if the fence can attach to solid blocks
     */
    public boolean canAttachToSolid()
    {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader levelIn, BlockPos pos, ISelectionContext context)
    {
        VoxelShape shape = getPostShape();
        if (state.getValue(NORTH))
            shape = VoxelShapes.or(shape, getShapeByDirection(Direction.NORTH));
        if (state.getValue(SOUTH))
            shape = VoxelShapes.or(shape, getShapeByDirection(Direction.SOUTH));
        if (state.getValue(WEST))
            shape = VoxelShapes.or(shape, getShapeByDirection(Direction.WEST));
        if (state.getValue(EAST))
            shape = VoxelShapes.or(shape, getShapeByDirection(Direction.EAST));
        return shape;
    }

    public abstract VoxelShape getShapeByDirection(Direction direction);

    public abstract VoxelShape getPostShape();

    private BlockState getState(BlockState state, level levelIn, BlockPos pos)
    {
        return state
                .setValue(NORTH, canConnectTo(levelIn, pos, Direction.SOUTH))
                .setValue(EAST, canConnectTo(levelIn, pos, Direction.WEST))
                .setValue(SOUTH, canConnectTo(levelIn, pos, Direction.NORTH))
                .setValue(WEST, canConnectTo(levelIn, pos, Direction.EAST));
    }
}
