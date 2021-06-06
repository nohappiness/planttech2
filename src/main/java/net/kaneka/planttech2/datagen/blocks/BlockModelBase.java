package net.kaneka.planttech2.datagen.blocks;

import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
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
	
	protected void facingBlock(Block b, BlockModelBuilder model)
	{
		states.getVariantBuilder(b)
			.partialState().with(HorizontalBlock.FACING, Direction.EAST).modelForState().modelFile(model).rotationY(90).addModel()
			.partialState().with(HorizontalBlock.FACING, Direction.NORTH).modelForState().modelFile(model).addModel()
			.partialState().with(HorizontalBlock.FACING, Direction.SOUTH).modelForState().modelFile(model).rotationY(180).addModel()
			.partialState().with(HorizontalBlock.FACING, Direction.WEST).modelForState().modelFile(model).rotationY(270).addModel();
	}
	
	protected void simpleBlock(Block b, BlockModelBuilder model)
	{
		states.getVariantBuilder(b).partialState().modelForState().modelFile(model).addModel();
	}
	
	protected void blockItem(Block b, BlockModelBuilder model)
	{
		states.itemModels().getBuilder(b.getRegistryName().getPath()).parent(model);
	}
}
