package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.tileentity.TileEntityCrops;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCropBars extends BlockBase
{
    public BlockCropBars()
    {
	super(Block.Properties.create(Material.WOOD).doesNotBlockMovement(), "cropbars", ModCreativeTabs.groupmain, true);
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ)
    {
	ItemStack possibleSeedStack = player.inventory.getCurrentItem();
	CropListEntry entry = PlantTechMain.croplist.getBySeed(possibleSeedStack);
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
    public boolean isFullCube(BlockState state)
    {
	return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
	return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockRenderType getRenderType(BlockState iBlockState)
    {
	return BlockRenderType.MODEL;
    }

}
