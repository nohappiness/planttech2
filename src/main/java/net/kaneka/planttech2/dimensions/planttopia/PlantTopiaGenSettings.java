package net.kaneka.planttech2.dimensions.planttopia;


import java.util.function.LongFunction;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.GenerationSettings;

public class PlantTopiaGenSettings extends GenerationSettings
{
	private long seed; 
	
	public PlantTopiaGenSettings(long seed)
	{
		this.seed = seed; 
	}
	
	@Override
	public BlockState getDefaultBlock()
	{
		return Blocks.DIRT.getDefaultState();
	}
	
	public int getBiomeSize() {
		return 4;
	}

	public int getRiverSize() {
		return 4;
	}

	public int getBiomeId() {
		return -1;
	}

	@Override
	public int getBedrockFloorHeight() {
		return 0;
	}

	public long getSeed()
	{
		return seed;
	}
}
