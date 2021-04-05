package net.kaneka.planttech2.datagen.blocks;

import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;

public abstract class BlockModelBase
{
	protected final BlockStateProvider states;
	
	BlockModelBase(BlockStateProvider states)
	{
		this.states = states; 
	}
	
	abstract void registerStatesAndModels();
	
	protected BlockModelProvider models()
	{
		return states.models();
	}
}
