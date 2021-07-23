package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.CustomRotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class GlassPanePillar extends CustomRotatedPillarBlock
{
	public GlassPanePillar(Properties property)
	{
		super(property.noOcclusion());
	}
}
