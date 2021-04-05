package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.BaseElectricFence;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FourWayBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.item.BlockItemUseContext;
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

public class ElectricFence extends BaseElectricFence
{
    public static final BooleanProperty NORTH = FourWayBlock.NORTH;
    public static final BooleanProperty EAST = FourWayBlock.EAST;
    public static final BooleanProperty SOUTH = FourWayBlock.SOUTH;
    public static final BooleanProperty WEST = FourWayBlock.WEST;

    public static final VoxelShape POST = Block.makeCuboidShape(7.5D, 0.10D, 7.5D, 8.5D, 15.90D, 8.5D);
    public static final VoxelShape NEGATIVE_Z = Block.makeCuboidShape(7.0D, 0.10D, 0.10D, 9.0D, 15.90D, 8.5D);
    public static final VoxelShape POSITIVE_Z = Block.makeCuboidShape(7.0D, 0.10D, 7.5D, 9.0D, 15.90D, 15.90D);
    public static final VoxelShape NEGATIVE_X = Block.makeCuboidShape(0.10D, 0.10D, 7.0D, 8.5D, 15.90D, 9.0D);
    public static final VoxelShape POSITIVE_X = Block.makeCuboidShape(7.5D, 0.10D, 7.0D, 15.90D, 15.90D, 9.0D);

    public ElectricFence(Properties property)
    {
        super(property);
        setDefaultState(getDefaultState()
                .with(ELECTRIC_POWER, 0)
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return getState(this.getDefaultState()
                .with(ELECTRIC_POWER, calculatePower((World) worldIn, currentPos)), (World) worldIn, currentPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getState(this.getDefaultState()
                .with(ELECTRIC_POWER, calculatePower(context.getWorld(), context.getPos())), context.getWorld(), context.getPos());
    }

    public boolean canConnectTo(World world, BlockPos pos, Direction direction)
    {
        BlockState state = world.getBlockState(pos.offset(direction));
        Block block = state.getBlock();
        return ((block instanceof BaseElectricFence || block instanceof ElectricFenceGate) || state.isSolidSide(world, pos.offset(direction), direction.getOpposite()));
    }

    private BlockState getState(BlockState state, World worldIn, BlockPos pos)
    {
        return state
                .with(NORTH, canConnectTo(worldIn, pos, Direction.SOUTH))
                .with(EAST, canConnectTo(worldIn, pos, Direction.WEST))
                .with(SOUTH, canConnectTo(worldIn, pos, Direction.NORTH))
                .with(WEST, canConnectTo(worldIn, pos, Direction.EAST));
    }

    @SuppressWarnings("deprecation")
	@Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        VoxelShape shape = POST;
        if (state.get(NORTH))
            shape = VoxelShapes.or(shape, POSITIVE_Z);
        if (state.get(SOUTH))
            shape = VoxelShapes.or(shape, NEGATIVE_Z);
        if (state.get(WEST))
            shape = VoxelShapes.or(shape, POSITIVE_X);
        if (state.get(EAST))
            shape = VoxelShapes.or(shape, NEGATIVE_X);
        return shape;
    }
}
