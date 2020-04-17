package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.BaseElectricFence;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ElectricFenceTop extends BaseElectricFence
{
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final VoxelShape TOP_X_AXIS = VoxelShapes.or(
            Block.makeCuboidShape(7.0D, 0.10D, 0.10D, 9.0D, 5.0D, 15.90D),
            Block.makeCuboidShape(6.0D, 5.0D, 0.10D, 10.0D, 7.0D, 15.90D),

            Block.makeCuboidShape(0.10D, 10.0D, 0.10D, 3.0D, 13.0D, 15.90D),
            Block.makeCuboidShape(3.0D, 7.0D, 0.10D, 6.0D, 10.0D, 15.90D),
            Block.makeCuboidShape(10.0D, 7.0D, 0.10D, 13.0D, 10.0D, 15.90D),
            Block.makeCuboidShape(13.0D, 10.0D, 0.10D, 15.90D, 13.0D, 15.90D));
    public static final VoxelShape TOP_Z_AXIS = VoxelShapes.or(
            Block.makeCuboidShape(0.10D, 0.10D, 7.0D, 15.90D, 5.0D, 9.0D),
            Block.makeCuboidShape(0.10D, 5.0D, 6.0D, 15.90D, 7.0D, 10.0D),

            Block.makeCuboidShape(0.10D, 10.0D, 0.10D, 15.90D, 13.0D, 3.0D),
            Block.makeCuboidShape(0.10D, 7.0D, 3.0D, 15.90D, 10.0D, 6.0D),
            Block.makeCuboidShape(0.10D, 7.0D, 10.0D, 15.90D, 10.0D, 13.0D),
            Block.makeCuboidShape(0.10D, 10.0D, 13.0D, 15.90D, 13.0D, 15.90D));

    public ElectricFenceTop(Properties property, String name, ItemGroup group, boolean hasItem)
    {
        super(property, name, group, hasItem);
        setDefaultState(getDefaultState()
                .with(ELECTRIC_POWER, 0)
                .with(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return getDefaultState()
                .with(HORIZONTAL_FACING, stateIn.get(HORIZONTAL_FACING))
                .with(ELECTRIC_POWER, updatePower((World) worldIn, currentPos));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState()
                .with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing())
                .with(ELECTRIC_POWER, updatePower(context.getWorld(), context.getPos()));
    }

    @SuppressWarnings("deprecation")
	@Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        switch (state.get(HORIZONTAL_FACING).getAxis())
        {
            case X:
            default:
                return TOP_X_AXIS;
            case Z:
                return TOP_Z_AXIS;
        }
    }
}
