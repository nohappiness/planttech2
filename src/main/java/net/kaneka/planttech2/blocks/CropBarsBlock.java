package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.baseclasses.BaseBlock;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.tileentity.CropsTileEntity;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class CropBarsBlock extends BaseBlock
{
	public CropBarsBlock()
	{
		super(Block.Properties.create(Material.WOOD).doesNotBlockMovement(), "cropbars", ModCreativeTabs.groupmain, true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) 
    {
    	ItemStack possibleSeedStack = player.inventory.getCurrentItem();
    	CropListEntry entry = PlantTechMain.croplist.getBySeed(possibleSeedStack);
    	if (entry != null)
    	{
    		if (!world.isRemote)
    		{
	    		world.setBlockState(pos, ModBlocks.CROPS.get(entry.getString()).getDefaultState());
	    		if (world.getTileEntity(pos) instanceof CropsTileEntity)
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
	    			((CropsTileEntity) world.getTileEntity(pos)).setTraits(toPass);
	        		((CropsTileEntity) world.getTileEntity(pos)).setStartTick();
			
	        		if (!player.isCreative())
	        		{
	        			possibleSeedStack.shrink(1);
	        		}
	    		}
    		}
    		return ActionResultType.SUCCESS;
    	}
    	return ActionResultType.FAIL;
    }

	@Override
	public BlockRenderType getRenderType(BlockState iBlockState)
	{
		return BlockRenderType.MODEL;
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos)
	{
		return true; 
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
	{
		if(world.getBlockState(pos.down()).isSolid())
		{
			return true; 
		}
		else
		{
			return false; 
		}
	}

}
