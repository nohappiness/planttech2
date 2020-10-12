package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.items.CropSeedItem;
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

public class CropBarsBlock extends Block
{
	public CropBarsBlock()
	{
		super(Block.Properties.create(Material.WOOD).doesNotBlockMovement().notSolid());
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) 
    {
    	ItemStack possibleSeedStack = player.getHeldItemMainhand();
    	if (!world.isRemote)
		{
			if (CropSeedItem.plant(world, pos, possibleSeedStack))
			{
				if (!player.abilities.isCreativeMode)
					possibleSeedStack.shrink(1);
				return ActionResultType.SUCCESS;
			}
		}
    	return ActionResultType.PASS;
    }
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos)
	{
		return true; 
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
	{
		return world.getBlockState(pos.down()).isSolid();
	}
}
