package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.FacingWallLightBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class WallLight extends FacingWallLightBase
{
    public static final VoxelShape NEGATIVE_Z = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 12.0D, 4.0D);
    public static final VoxelShape POSITIVE_Z = Block.box(6.0, 6.0, 12.0, 10.0, 12.0, 16.0);
    public static final VoxelShape NEGATIVE_X = Block.box(0.0, 6.0, 6.0, 4.0, 12.0, 10.0);
    public static final VoxelShape POSITIVE_X = Block.box(12.0, 6.0, 6.0, 16.0, 12.0, 10.0);

    public static final VoxelShape NEGATIVE_Z_BROKE = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 12.0D, 1.0D);
    public static final VoxelShape POSITIVE_Z_BROKE = Block.box(6.0, 6.0, 15.0, 10.0, 12.0, 16.0);
    public static final VoxelShape NEGATIVE_X_BROKE = Block.box(0.0, 6.0, 6.0, 1.0, 12.0, 10.0);
    public static final VoxelShape POSITIVE_X_BROKE = Block.box(15.0, 6.0, 6.0, 16.0, 12.0, 10.0);

    public WallLight(Properties property)
    {
        super(property);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context)
    {
        boolean broke = state.getValue(LIGHT_STATUS) == 0;
        return switch (state.getValue(HORIZONTAL_FACING))
                {
                    default -> broke ? NEGATIVE_Z_BROKE : NEGATIVE_Z;
                    case SOUTH -> broke ? POSITIVE_Z_BROKE : POSITIVE_Z;
                    case WEST -> broke ? NEGATIVE_X_BROKE : NEGATIVE_X;
                    case EAST -> broke ? POSITIVE_X_BROKE : POSITIVE_X;
                };
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity)
    {
        return state.getValue(LIGHT_STATUS) == 0 ? SoundType.STONE : super.getSoundType(state, level, pos, entity);
    }

    @Override
    public void playerWillDestroy(Level levelIn, BlockPos pos, BlockState state, Player player)
    {
        super.playerWillDestroy(levelIn, pos, state, player);
    }
}
