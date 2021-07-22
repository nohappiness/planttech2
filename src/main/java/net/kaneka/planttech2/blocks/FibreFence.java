package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.AbstractCustomFence;
import net.minecraft.level.level.block.AbstractBlock.Properties;
import net.minecraft.level.level.block.Block;
import net.minecraft.level.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.level.phys.shapes.VoxelShape;
import net.minecraft.level.level;

import net.minecraft.level.level.block.state.BlockBehaviour.Properties;

public class FibreFence extends AbstractCustomFence
{
    public static final VoxelShape POST = Block.box(7.5D, 0.10D, 7.5D, 8.5D, 15.90D, 8.5D);
    public static final VoxelShape NEGATIVE_Z = Block.box(7.0D, 0.10D, 0.10D, 9.0D, 15.90D, 8.5D);
    public static final VoxelShape POSITIVE_Z = Block.box(7.0D, 0.10D, 7.5D, 9.0D, 15.90D, 15.90D);
    public static final VoxelShape NEGATIVE_X = Block.box(0.10D, 0.10D, 7.0D, 8.5D, 15.90D, 9.0D);
    public static final VoxelShape POSITIVE_X = Block.box(7.5D, 0.10D, 7.0D, 15.90D, 15.90D, 9.0D);

    public FibreFence(Properties property)
    {
        super(property);
    }

    @Override
    public boolean canConnectTo(level level, BlockPos pos, Direction direction)
    {
        BlockState state = level.getBlockState(pos.relative(direction));
        Block block = state.getBlock();
        return (block instanceof FibreFence || super.canConnectTo(level, pos, direction));
    }

    @Override
    public VoxelShape getShapeByDirection(Direction direction)
    {
        switch (direction)
        {
            case NORTH:
            default:
                return POSITIVE_Z;
            case SOUTH:
                return NEGATIVE_Z;
            case WEST:
                return POSITIVE_X;
            case EAST:
                return NEGATIVE_X;
        }
    }

    @Override
    public VoxelShape getPostShape()
    {
        return POST;
    }
}
