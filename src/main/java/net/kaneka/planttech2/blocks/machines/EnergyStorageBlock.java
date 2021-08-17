package net.kaneka.planttech2.blocks.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class EnergyStorageBlock extends MachineBaseBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty TIER = IntegerProperty.create("tier", 0, 3);

    public EnergyStorageBlock(BiFunction<BlockPos, BlockState, ? extends BlockEntity> teCreator, int tier)
    {
        super(teCreator, tier);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TIER, 0));
    }

	public EnergyStorageBlock(BiFunction<BlockPos, BlockState, ? extends BlockEntity> teCreator)
	{
		this(teCreator, 0);
	}

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
	    return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
	    level.setBlock(pos, this.defaultBlockState().setValue(FACING, placer.getDirection().getOpposite()), 2);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
	    return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

	@Override
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
	    return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING).add(TIER);
    }

}
