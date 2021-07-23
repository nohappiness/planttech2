package net.kaneka.planttech2.datagen.blocks;

import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.world.level.block.Block;

public class BuildingBlockModels extends BlockModelBase
{
	BuildingBlockModels(BlockStateGenerator states)
	{
		super(states);
	}

	@Override
	public void registerStatesAndModels()
	{
		simpleBlock(ModBlocks.DARK_CRYSTAL_BLOCK, "blocks/building_blocks/dark_crystal_block");
		simpleBlock(ModBlocks.DARK_CRYSTAL_BRICK, "blocks/building_blocks/dark_crystal_brick");
		simpleBlock(ModBlocks.DARK_CRYSTAL_TILING, "blocks/building_blocks/dark_crystal_tiling");

		simpleBlock(ModBlocks.WHITE_CRYSTAL_BLOCK, "blocks/building_blocks/white_crystal_block");
		simpleBlock(ModBlocks.WHITE_CRYSTAL_BRICK, "blocks/building_blocks/white_crystal_brick");
		simpleBlock(ModBlocks.WHITE_CRYSTAL_TILING, "blocks/building_blocks/white_crystal_tiling");

		simpleBlockItem(ModBlocks.INFUSED_ICE, "blocks/infused_ice");
		simpleBlockItem(ModBlocks.INFUSED_PACKED_ICE, "blocks/infused_packed_ice");
		simpleBlockItem(ModBlocks.INFUSED_BLUE_ICE, "blocks/infused_blue_ice");
		simpleBlockItem(ModBlocks.BLACK_ICE, "blocks/black_ice");
		simpleBlockItem(ModBlocks.INFUSED_STONE, "blocks/infused_stone");
		simpleBlockItem(ModBlocks.INFUSED_COBBLESTONE, "blocks/infused_cobblestone");
	}

	private void simpleBlockItem(Block block, String texturePath)
	{
		simpleBlock(block, texturePath);
		blockItem(block, models().cubeAll("block/building_blocks/" + block.getRegistryName().getPath(), states.modLoc(texturePath)));
	}

	private void simpleBlock(Block block, String texturePath)
	{
		String name = block.getRegistryName().getPath();
		states.simpleBlock(block, models().cubeAll("block/building_blocks/" + name, states.modLoc(texturePath)));
	}
}
