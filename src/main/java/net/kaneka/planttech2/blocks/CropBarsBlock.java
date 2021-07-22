package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.items.CropSeedItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;


public class CropBarsBlock extends Block
{
	public CropBarsBlock()
	{
		super(Block.Properties.of(Material.WOOD).noCollission().noOcclusion());
	}

	

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) 
    {
    	ItemStack possibleSeedStack = player.getMainHandItem();
    	if (!level.isClientSide)
		{
			if (CropSeedItem.plant(level, pos, possibleSeedStack))
			{
				if (!player.abilities.instabuild)
					possibleSeedStack.shrink(1);
				return InteractionResult.SUCCESS;
			}
		}
    	return InteractionResult.PASS;
    }
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos)
	{
		return true; 
	}


	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
	{
		return level.getBlockState(pos.below()).isFaceSturdy(level, pos, Direction.UP);
	}
}
