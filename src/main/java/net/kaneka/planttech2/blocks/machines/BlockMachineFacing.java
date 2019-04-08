package net.kaneka.planttech2.blocks.machines;

import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMachineFacing extends BlockMachineBase
{
    public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;

    public BlockMachineFacing(String name, ItemGroup tab)
    {
	super(name, tab);
	this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
    }

    
    @Override
    public void onBlockAdded(IBlockState state, World worldIn, BlockPos pos, IBlockState oldState)
    {
	if (!worldIn.isRemote)
	{
	    IBlockState north = worldIn.getBlockState(pos.north());
	    IBlockState south = worldIn.getBlockState(pos.south());
	    IBlockState west = worldIn.getBlockState(pos.west());
	    IBlockState east = worldIn.getBlockState(pos.east());
	    EnumFacing face = (EnumFacing) state.get(FACING);

	    if (face == EnumFacing.NORTH && north.isFullCube() && !south.isFullCube())
		face = EnumFacing.SOUTH;
	    else if (face == EnumFacing.SOUTH && south.isFullCube() && !north.isFullCube())
		face = EnumFacing.NORTH;
	    else if (face == EnumFacing.WEST && west.isFullCube() && !east.isFullCube())
		face = EnumFacing.EAST;
	    else if (face == EnumFacing.EAST && east.isFullCube() && !west.isFullCube())
		face = EnumFacing.WEST;
	    worldIn.setBlockState(pos, state.with(FACING, face), 2);
	}
    }

    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context)
    {
	return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
	worldIn.setBlockState(pos, this.getDefaultState().with(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public IBlockState rotate(IBlockState state, Rotation rot)
    {
	return state.with(FACING, rot.rotate((EnumFacing) state.get(FACING)));
    }

    @Override
    public IBlockState mirror(IBlockState state, Mirror mirrorIn)
    {
	return state.rotate(mirrorIn.toRotation((EnumFacing) state.get(FACING)));
    }
    
    @Override
    protected void fillStateContainer(Builder<Block, IBlockState> builder)
    {
        builder.add(FACING);
    }

}
