package net.kaneka.planttech2.datagen.blocks;

import com.google.common.collect.ImmutableMap;
import net.kaneka.planttech2.blocks.Hedge;
import net.kaneka.planttech2.blocks.baseclasses.CustomFenceBlock;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;

import java.util.Map;

public class HedgeModels extends BlockModelBase
{
	HedgeModels(BlockStateGenerator states)
	{
		super(states);
	}

	@Override
	public void registerStatesAndModels()
	{
		// Base models
		BlockModelBuilder hedgeBase = models().withExistingParent("block/hedge/base/hedge_base", "block/block")
				.element().from(5, 5, 5).to(11, 16, 11).allFaces((d, e) -> e.texture("#leaves").tintindex(0)).end()
				.element().from(4, 0, 4).to(5, 4, 5).textureAll("#wood").end()
				.element().from(4, 0, 11).to(5, 4, 12).textureAll("#wood").end()
				.element().from(11, 0, 11).to(12, 4, 12).textureAll("#wood").end()
				.element().from(11, 0, 4).to(12, 4, 5).textureAll("#wood").end()
				.element().from(5, 0, 5).to(11, 4, 11).allFaces((d, e) -> e.texture("#soil").tintindex(1)).end();

		BlockModelBuilder hedgeAdding = models().getBuilder("block/hedge/base/hedge_adding")
				.element().from(11, 5, 5).to(16, 16, 11).allFaces((d, e) -> e.texture("#leaves").tintindex(0)).end()
				.element().from(12, 0, 11).to(16, 4, 12).textureAll("#wood").end()
				.element().from(12, 0, 4).to(16, 4, 5).textureAll("#wood").end()
				.element().from(11, 0, 5).to(16, 4, 11).allFaces((d, e) -> e.texture("#soil").tintindex(1)).end();

		BlockModelBuilder hedgeNone = models().getBuilder("block/hedge/base/hedge_none")
				.element().from(11, 0, 5).to(12, 4, 11).textureAll("#wood").end();

		// Maps a soil name to the relevant texture
		Map<String, String> soilToTexture = ImmutableMap.of("grass", "grass_block_top", "podzol", "podzol_top");

		for (Hedge hedgeBlock : ModBlocks.HEDGE_BLOCKS)
		{
			// Assume that leaves and logs have the same registry name as their texture
			ResourceLocation leavesLoc = hedgeBlock.getLeaves().getRegistryName();
			ResourceLocation leavesTexture = new ResourceLocation(leavesLoc.getNamespace(), "block/" + leavesLoc.getPath());
			String leaves = strip(leavesLoc.getPath());

			ResourceLocation woodLoc = hedgeBlock.getWood().getRegistryName();
			ResourceLocation woodTexture = new ResourceLocation(woodLoc.getNamespace(), "block/" + woodLoc.getPath());
			String wood = strip(woodLoc.getPath());

			ResourceLocation soilLoc = hedgeBlock.getSoil().getRegistryName();
			String soil = strip(soilLoc.getPath());
			ResourceLocation soilTexture = new ResourceLocation(soilLoc.getNamespace(), "block/" + soilToTexture.getOrDefault(soil, soilLoc.getPath()));

			String combinedType = leaves + "_" + wood;

			// Models

			BlockModelBuilder base = models().getBuilder("block/hedge/" + combinedType + "_" + soil + "_base")
					.parent(hedgeBase)
					.texture("leaves", leavesTexture)
					.texture("wood", woodTexture)
					.texture("soil", soilTexture)
					.texture("particle", woodTexture);

			BlockModelBuilder adding = models().getBuilder("block/hedge/" + combinedType + "_" + soil + "_adding")
					.parent(hedgeAdding)
					.texture("leaves", leavesTexture)
					.texture("wood", woodTexture)
					.texture("soil", soilTexture)
					.texture("particle", woodTexture);

			BlockModelBuilder none = models().getBuilder("block/hedge/" + combinedType + "_none")
					.parent(hedgeNone)
					.texture("wood", woodTexture)
					.texture("particle", woodTexture);

			// Block state
			states.getMultipartBuilder(hedgeBlock)
					.part().modelFile(base).addModel().end()
					.part().modelFile(adding).rotationY(270).addModel().condition(CustomFenceBlock.NORTH, true).end()
					.part().modelFile(none).rotationY(270).addModel().condition(CustomFenceBlock.NORTH, false).end()

					.part().modelFile(adding).addModel().condition(CustomFenceBlock.EAST, true).end()
					.part().modelFile(none).addModel().condition(CustomFenceBlock.EAST, false).end()

					.part().modelFile(adding).rotationY(90).addModel().condition(CustomFenceBlock.SOUTH, true).end()
					.part().modelFile(none).rotationY(90).addModel().condition(CustomFenceBlock.SOUTH, false).end()

					.part().modelFile(adding).rotationY(180).addModel().condition(CustomFenceBlock.WEST, true).end()
					.part().modelFile(none).rotationY(180).addModel().condition(CustomFenceBlock.WEST, false).end();

			// Item model
			states.itemModels().getBuilder(hedgeBlock.getRegistryName().getPath())
					.parent(base);
		}
	}

	private String strip(String str)
	{
		// Assume that registry names of leaves and logs end in `_leaves` and `_log`
		// Take the last index of `_`, and only take the characters from the start til the character before the given index
		// Taking the LAST index is important, so ex. `dark_oak_leaves` becomes `dark_oak` and not `dark`

		int idx = str.lastIndexOf('_');
		return idx == -1 ? str : str.substring(0, idx);
	}
}
