package net.kaneka.planttech2.blocks.baseclasses;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FourWayBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

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
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return getState(this.defaultBlockState(), (World) worldIn, currentPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getState(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    public boolean canConnectTo(World world, BlockPos pos, Direction direction)
    {
        return canAttachToSolid() && world.getBlockState(pos.relative(direction)).isFaceSturdy(world, pos.relative(direction), direction.getOpposite());
    }

    /**
     * if the fence can attach to solid blocks
     */
    public boolean canAttachToSolid()
    {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
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

    private BlockState getState(BlockState state, World worldIn, BlockPos pos)
    {
        return state
                .setValue(NORTH, canConnectTo(worldIn, pos, Direction.SOUTH))
                .setValue(EAST, canConnectTo(worldIn, pos, Direction.WEST))
                .setValue(SOUTH, canConnectTo(worldIn, pos, Direction.NORTH))
                .setValue(WEST, canConnectTo(worldIn, pos, Direction.EAST));
    }
}
