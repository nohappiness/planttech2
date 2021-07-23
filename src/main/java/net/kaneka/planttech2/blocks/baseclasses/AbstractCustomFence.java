package net.kaneka.planttech2.blocks.baseclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.level.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractCustomFence extends Block
{
    public static final BooleanProperty NORTH = FenceBlock.NORTH;
    public static final BooleanProperty EAST = FenceBlock.EAST;
    public static final BooleanProperty SOUTH = FenceBlock.SOUTH;
    public static final BooleanProperty WEST = FenceBlock.WEST;

    public AbstractCustomFence(Properties property)
    {
        super(property.noOcclusion());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor levelIn, BlockPos currentPos, BlockPos facingPos)
    {
        return getState(this.defaultBlockState(), (Level) levelIn, currentPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return getState(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    public boolean canConnectTo(Level level, BlockPos pos, Direction direction)
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
    public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context)
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
