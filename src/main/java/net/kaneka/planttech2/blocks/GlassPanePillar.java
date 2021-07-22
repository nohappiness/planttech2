package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.CustomRotatedPillarBlock;
import net.minecraft.level.level.block.AbstractBlock.Properties;

import net.minecraft.level.level.block.state.BlockBehaviour.Properties;

public class GlassPanePillar extends CustomRotatedPillarBlock
{
	public GlassPanePillar(Properties property)
	{
		super(property.noOcclusion());
	}
}
