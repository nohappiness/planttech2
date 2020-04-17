package net.kaneka.planttech2.blocks.baseclasses;

import net.kaneka.planttech2.blocks.ElectricFenceGate;
import net.kaneka.planttech2.blocks.FibreFence;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

public abstract class AbstractCustomFence extends BaseBlock
{
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public AbstractCustomFence(Properties property, String name, ItemGroup group, boolean hasItem)
    {
        super(property.notSolid(), name, group, hasItem);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return getState(this.getDefaultState(), (World) worldIn, currentPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getState(this.getDefaultState(), context.getWorld(), context.getPos());
    }

    public boolean canConnectTo(World world, BlockPos pos, Direction direction)
    {
        return canAttachToSolid() && world.getBlockState(pos.offset(direction)).isSolidSide(world, pos.offset(direction), direction.getOpposite());
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
        if (state.get(NORTH))
            shape = VoxelShapes.or(shape, getShapeByDirection(Direction.NORTH));
        if (state.get(SOUTH))
            shape = VoxelShapes.or(shape, getShapeByDirection(Direction.SOUTH));
        if (state.get(WEST))
            shape = VoxelShapes.or(shape, getShapeByDirection(Direction.WEST));
        if (state.get(EAST))
            shape = VoxelShapes.or(shape, getShapeByDirection(Direction.EAST));
        return shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return getShape(state, worldIn, pos, context);
    }

    public abstract VoxelShape getShapeByDirection(Direction direction);

    public abstract VoxelShape getPostShape();

    private BlockState getState(BlockState state, World worldIn, BlockPos pos)
    {
        return state
                .with(NORTH, canConnectTo(worldIn, pos, Direction.SOUTH))
                .with(EAST, canConnectTo(worldIn, pos, Direction.WEST))
                .with(SOUTH, canConnectTo(worldIn, pos, Direction.NORTH))
                .with(WEST, canConnectTo(worldIn, pos, Direction.EAST));
    }
}
