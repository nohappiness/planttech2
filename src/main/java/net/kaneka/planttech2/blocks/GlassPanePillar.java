package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.CustomRotatedPillarBlock;

import net.minecraft.block.AbstractBlock.Properties;

public class GlassPanePillar extends CustomRotatedPillarBlock
{
	public GlassPanePillar(Properties property)
	{
		super(property.notSolid());
	}
}
