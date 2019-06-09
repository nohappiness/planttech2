package net.kaneka.planttech2.blocks.machines;

import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEnergyStorage extends BlockMachineBase
{
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final IntegerProperty TIER = IntegerProperty.create("tier", 0, 3);
    
    public BlockEnergyStorage()
    {
	super("energystorage", ModCreativeTabs.groupmachines);
	this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(TIER, 0));
    }
    
    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState)
    {
	if (!worldIn.isRemote)
	{
	    BlockState north = worldIn.getBlockState(pos.north());
	    BlockState south = worldIn.getBlockState(pos.south());
	    BlockState west = worldIn.getBlockState(pos.west());
	    BlockState east = worldIn.getBlockState(pos.east());
	    Direction face = (Direction) state.get(FACING);

	    if (face == Direction.NORTH && north.isFullCube() && !south.isFullCube())
		face = Direction.SOUTH;
	    else if (face == Direction.SOUTH && south.isFullCube() && !north.isFullCube())
		face = Direction.NORTH;
	    else if (face == Direction.WEST && west.isFullCube() && !east.isFullCube())
		face = Direction.EAST;
	    else if (face == Direction.EAST && east.isFullCube() && !west.isFullCube())
		face = Direction.WEST;
	    worldIn.setBlockState(pos, state.with(FACING, face).with(TIER, 0), 2);
	}
    }
    
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
	return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
	worldIn.setBlockState(pos, this.getDefaultState().with(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
	return state.with(FACING, rot.rotate((Direction) state.get(FACING)));
    }

    @SuppressWarnings("deprecation")
	@Override
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
	return state.rotate(mirrorIn.toRotation((Direction) state.get(FACING)));
    }
    
    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder)
    {
        builder.add(FACING).add(TIER);
    }

}
