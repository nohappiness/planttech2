package net.kaneka.planttech2.blocks.machines;

import java.util.function.Supplier;

import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.item.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.state.StateDefinition.Builder;
import net.minecraft.BlockEntity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.World;

public class EnergyStorageBlock extends MachineBaseBlock
{
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final IntegerProperty TIER = IntegerProperty.create("tier", 0, 3);

    public EnergyStorageBlock(Supplier<? extends BlockEntity> teCreator, int tier)
    {
	super(teCreator, tier);
	this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TIER, 0));
    }

	public EnergyStorageBlock(Supplier<? extends BlockEntity> teCreator)
	{
		this(teCreator, 0);
	}


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
	return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
	worldIn.setBlock(pos, this.defaultBlockState().setValue(FACING, placer.getDirection().getOpposite()), 2);
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
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
    {
        builder.add(FACING).add(TIER);
    }

}
