package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.AbstractCustomFence;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

public class FibreFence extends AbstractCustomFence
{
    public static final VoxelShape POST = Block.makeCuboidShape(7.5D, 0.10D, 7.5D, 8.5D, 15.90D, 8.5D);
    public static final VoxelShape NEGATIVE_Z = Block.makeCuboidShape(7.0D, 0.10D, 0.10D, 9.0D, 15.90D, 8.5D);
    public static final VoxelShape POSITIVE_Z = Block.makeCuboidShape(7.0D, 0.10D, 7.5D, 9.0D, 15.90D, 15.90D);
    public static final VoxelShape NEGATIVE_X = Block.makeCuboidShape(0.10D, 0.10D, 7.0D, 8.5D, 15.90D, 9.0D);
    public static final VoxelShape POSITIVE_X = Block.makeCuboidShape(7.5D, 0.10D, 7.0D, 15.90D, 15.90D, 9.0D);

    public FibreFence(Properties property)
    {
        super(property);
    }

    @Override
    public boolean canConnectTo(World world, BlockPos pos, Direction direction)
    {
        BlockState state = world.getBlockState(pos.offset(direction));
        Block block = state.getBlock();
        return (block instanceof FibreFence || super.canConnectTo(world, pos, direction));
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
