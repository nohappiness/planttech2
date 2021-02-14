package net.kaneka.planttech2.blocks.machines;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public class MachineFacingBlock extends MachineBaseBlock
{
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public MachineFacingBlock(Supplier<? extends TileEntity> teCreator, int tier)
    {
        super(teCreator, tier);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

	public MachineFacingBlock(Supplier<? extends TileEntity> teCreator)
	{
		this(teCreator, 0);
	}

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
	    return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
	    return state.with(FACING, rot.rotate(state.get(FACING)));
    }

	@Override
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }
    
    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

}
