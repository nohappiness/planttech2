package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.CustomRotatedPillarBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GlassPanePillar extends CustomRotatedPillarBlock
{

	public GlassPanePillar(Properties property, String name, ItemGroup group, boolean hasItem)
	{
		super(property, name, group, hasItem);
	}
	
	@Override 
	@OnlyIn(Dist.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) 
	{
	   return true;
	}
}
