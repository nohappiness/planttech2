package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.CustomRotatedPillarBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.BlockRenderLayer;

public class GlassPanePillar extends CustomRotatedPillarBlock
{

	public GlassPanePillar(Properties property, String name, ItemGroup group, boolean hasItem)
	{
		super(property, name, group, hasItem);
	}

	@Override
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

}
