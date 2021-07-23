package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.FacingWallLightBase;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.CollisionContext;
import net.minecraft.level.phys.shapes.VoxelShape;
import net.minecraft.level.BlockGetter;
import net.minecraft.level.IlevelReader;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class WallLight extends FacingWallLightBase
{
    public static final VoxelShape NEGATIVE_Z = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 12.0D, 4.0D);
    public static final VoxelShape POSITIVE_Z = Block.box(10.0, 6.0, 16.0, 6.0, 12.0, 12.0);
    public static final VoxelShape NEGATIVE_X = Block.box(0.0, 6.0, 10.0, 4.0, 12.0, 6.0);
    public static final VoxelShape POSITIVE_X = Block.box(16.0, 6.0, 6.0, 12.0, 12.0, 10.0);

    public static final VoxelShape NEGATIVE_Z_BROKE = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 12.0D, 1.0D);
    public static final VoxelShape POSITIVE_Z_BROKE = Block.box(10.0, 6.0, 16.0, 6.0, 12.0, 15.0);
    public static final VoxelShape NEGATIVE_X_BROKE = Block.box(0.0, 6.0, 10.0, 1.0, 12.0, 6.0);
    public static final VoxelShape POSITIVE_X_BROKE = Block.box(16.0, 6.0, 6.0, 15.0, 12.0, 10.0);

    public WallLight(Properties property)
    {
        super(property);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context)
    {
        boolean broke = state.getValue(LIGHT_STATUS) == 0;
        switch (state.getValue(HORIZONTAL_FACING))
        {
            case NORTH:
            default:
                return broke ? NEGATIVE_Z_BROKE : NEGATIVE_Z;
            case SOUTH:
                return broke ? POSITIVE_Z_BROKE : POSITIVE_Z;
            case WEST:
                return broke ? NEGATIVE_X_BROKE : NEGATIVE_X;
            case EAST:
                return broke ? POSITIVE_X_BROKE : POSITIVE_X;
        }
    }

    @Override
    public SoundType getSoundType(BlockState state, IlevelReader level, BlockPos pos, @Nullable Entity entity)
    {
        return state.getValue(LIGHT_STATUS) == 0 ? SoundType.STONE : super.getSoundType(state, level, pos, entity);
    }

    @Override
    public void playerWillDestroy(level levelIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        super.playerWillDestroy(levelIn, pos, state, player);
    }
}
