package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.BaseElectricFence;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
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

import net.minecraft.block.AbstractBlock.Properties;

public class ElectricFenceTop extends BaseElectricFence
{
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.FACING;
    public static final VoxelShape TOP_X_AXIS = VoxelShapes.or(
            Block.box(7.0D, 0.10D, 0.10D, 9.0D, 5.0D, 15.90D),
            Block.box(6.0D, 5.0D, 0.10D, 10.0D, 7.0D, 15.90D),

            Block.box(0.10D, 10.0D, 0.10D, 3.0D, 13.0D, 15.90D),
            Block.box(3.0D, 7.0D, 0.10D, 6.0D, 10.0D, 15.90D),
            Block.box(10.0D, 7.0D, 0.10D, 13.0D, 10.0D, 15.90D),
            Block.box(13.0D, 10.0D, 0.10D, 15.90D, 13.0D, 15.90D));
    public static final VoxelShape TOP_Z_AXIS = VoxelShapes.or(
            Block.box(0.10D, 0.10D, 7.0D, 15.90D, 5.0D, 9.0D),
            Block.box(0.10D, 5.0D, 6.0D, 15.90D, 7.0D, 10.0D),

            Block.box(0.10D, 10.0D, 0.10D, 15.90D, 13.0D, 3.0D),
            Block.box(0.10D, 7.0D, 3.0D, 15.90D, 10.0D, 6.0D),
            Block.box(0.10D, 7.0D, 10.0D, 15.90D, 10.0D, 13.0D),
            Block.box(0.10D, 10.0D, 13.0D, 15.90D, 13.0D, 15.90D));

    public ElectricFenceTop(Properties property)
    {
        super(property);
        registerDefaultState(defaultBlockState()
                .setValue(ELECTRIC_POWER, 0)
                .setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return defaultBlockState()
                .setValue(HORIZONTAL_FACING, stateIn.getValue(HORIZONTAL_FACING))
                .setValue(ELECTRIC_POWER, calculatePower((World) worldIn, currentPos));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.defaultBlockState()
                .setValue(HORIZONTAL_FACING, context.getHorizontalDirection())
                .setValue(ELECTRIC_POWER, calculatePower(context.getLevel(), context.getClickedPos()));
    }

    @SuppressWarnings("deprecation")
	@Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        switch (state.getValue(HORIZONTAL_FACING).getAxis())
        {
            case X:
            default:
                return TOP_X_AXIS;
            case Z:
                return TOP_Z_AXIS;
        }
    }
}
