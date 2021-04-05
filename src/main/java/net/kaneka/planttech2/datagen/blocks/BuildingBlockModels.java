package net.kaneka.planttech2.datagen.blocks;

import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;

public class BuildingBlockModels
{
	private final BlockStateGenerator states;

	BuildingBlockModels(BlockStateGenerator states)
	{
		this.states = states;
	}

	public void registerStatesAndModels()
	{
		simpleBlock(ModBlocks.DARK_CRYSTAL_BLOCK, "blocks/building_blocks/dark_crystal_block");
		simpleBlock(ModBlocks.DARK_CRYSTAL_BRICK, "blocks/building_blocks/dark_crystal_brick");
		simpleBlock(ModBlocks.DARK_CRYSTAL_TILING, "blocks/building_blocks/dark_crystal_tiling");

		simpleBlock(ModBlocks.WHITE_CRYSTAL_BLOCK, "blocks/building_blocks/white_crystal_block");
		simpleBlock(ModBlocks.WHITE_CRYSTAL_BRICK, "blocks/building_blocks/white_crystal_brick");
		simpleBlock(ModBlocks.WHITE_CRYSTAL_TILING, "blocks/building_blocks/white_crystal_tiling");
	}

	private void simpleBlock(Block block, String texturePath)
	{
		String name = block.getRegistryName().getPath();
		states.simpleBlock(block, models().cubeAll("block/building_blocks/" + name, states.modLoc(texturePath)));
	}

	private BlockModelProvider models()
	{
		return this.states.models();
	}
}
