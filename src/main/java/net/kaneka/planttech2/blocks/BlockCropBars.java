package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.items.ItemCropSeed;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.tileentity.TileEntityCrops;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockCropBars extends BlockBase
{
    public BlockCropBars()
    {
	super(Block.Properties.create(Material.WOOD).doesNotBlockMovement(), "cropbars", PlantTechMain.groupmain, true);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
	ItemStack possibleSeedStack = player.inventory.getCurrentItem();
	CropListEntry entry = PlantTechMain.instance.croplist.getBySeed(possibleSeedStack);
	if (entry != null)
	{
	    world.setBlockState(pos, ModBlocks.CROPS.get(entry.getString()).getDefaultState());
	    if (world.getTileEntity(pos) instanceof TileEntityCrops)
	    {
		HashMapCropTraits toPass = new HashMapCropTraits();
		toPass.setType(entry.getString());
		if(possibleSeedStack.hasTag())
		{
        		toPass.fromStack(possibleSeedStack);
		}
		else
		{
		    toPass.setAnalysed(true);
		}
        		((TileEntityCrops) world.getTileEntity(pos)).setTraits(toPass);
        		((TileEntityCrops) world.getTileEntity(pos)).setStartTick();
		
		if (!player.isCreative())
		{
		    possibleSeedStack.shrink(1);
		}
	    }
	}
	return super.onBlockActivated(state,world,pos,player,hand,side,hitX,hitY,hitZ) ;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
	return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
	return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState iBlockState)
    {
	return EnumBlockRenderType.MODEL;
    }

}
