package net.kaneka.planttech2.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public class MachineFacingBlock extends MachineBaseBlock
{
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public MachineFacingBlock(String name, ItemGroup tab)
    {
	super(name, tab);
	this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

   

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
	return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
    
    

    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
	return state.with(FACING, rot.rotate((Direction) state.get(FACING)));
    }

	@Override
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
	return state.rotate(mirrorIn.toRotation((Direction) state.get(FACING)));
    }
    
    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

}
