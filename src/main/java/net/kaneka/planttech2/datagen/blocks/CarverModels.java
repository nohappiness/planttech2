package net.kaneka.planttech2.datagen.blocks;

import net.kaneka.planttech2.blocks.CarverBlock;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.generators.BlockModelBuilder;

public class CarverModels extends BlockModelBase
{
	CarverModels(BlockStateGenerator states)
	{
		super(states);
	}
	
	@Override
	public void registerStatesAndModels()
	{
		BlockModelBuilder carver_block_base = models().withExistingParent("block/basic/carver_block_base", "block/block")
			.element().from(0.0f, 0.0f, 8.0f).to(16.0f, 16.0f, 8.0f)
				.face(Direction.byName("north")).uvs(0.0f, 0.0f, 16.0f, 16.0f).texture("#ew_1").end()
				.face(Direction.byName("east")).uvs(0.0f, 0.0f, 0.0f, 16.0f).texture("#ew_1").end()
				.face(Direction.byName("south")).uvs(0.0f, 0.0f, 16.0f, 16.0f).texture("#ew_2").end()
				.face(Direction.byName("west")).uvs(0.0f, 0.0f, 0.0f, 16.0f).texture("#ew_1").end()
				.face(Direction.byName("up")).uvs(0.0f, 0.0f, 16.0f, 0.0f).texture("#ew_1").end()
				.face(Direction.byName("down")).uvs(0.0f, 0.0f, 16.0f, 0.0f).texture("#ew_1").end().end()
			.element().from(8.0f, 0.0f, 0.0f).to(8.0f, 16.0f, 16.0f)
				.face(Direction.byName("north")).uvs(0.0f, 0.0f, 0.0f, 16.0f).texture("#ns_1").end()
				.face(Direction.byName("east")).uvs(0.0f, 0.0f, 16.0f, 16.0f).texture("#ns_2").end()
				.face(Direction.byName("south")).uvs(0.0f, 0.0f, 0.0f, 16.0f).texture("#ns_1").end()
				.face(Direction.byName("west")).uvs(0.0f, 0.0f, 16.0f, 16.0f).texture("#ns_1").end()
				.face(Direction.byName("up")).uvs(0.0f, 0.0f, 0.0f, 16.0f).texture("#ns_1").end()
				.face(Direction.byName("down")).uvs(0.0f, 0.0f, 0.0f, 16.0f).texture("#ns_1").end().end()
			.element().from(8.0f, 0.0f, 0.0f).to(8.0f, 16.0f, 16.0f)
				.rotation().angle(-45.0f).axis(Direction.Axis.byName("y")).origin(8.0f, 8.0f, 8.0f).end()
				.face(Direction.byName("north")).uvs(0.0f, 0.0f, 0.0f, 16.0f).texture("#diagonal_1").end()
				.face(Direction.byName("east")).uvs(0.0f, 0.0f, 16.0f, 16.0f).texture("#diagonal_2").end()
				.face(Direction.byName("south")).uvs(0.0f, 0.0f, 0.0f, 16.0f).texture("#diagonal_1").end()
				.face(Direction.byName("west")).uvs(0.0f, 0.0f, 16.0f, 16.0f).texture("#diagonal_1").end()
				.face(Direction.byName("up")).uvs(0.0f, 0.0f, 0.0f, 16.0f).texture("#diagonal_1").end()
				.face(Direction.byName("down")).uvs(0.0f, 0.0f, 0.0f, 16.0f).texture("#diagonal_1").end().end()
			.element().from(8.0f, 0.0f, 0.0f).to(8.0f, 16.0f, 16.0f)
				.rotation().angle(45.0f).axis(Direction.Axis.byName("y")).origin(8.0f, 8.0f, 8.0f).end()
				.face(Direction.byName("north")).uvs(0.0f, 0.0f, 16.0f, 16.0f).texture("#diagonal_1").end()
				.face(Direction.byName("east")).uvs(0.0f, 0.0f, 16.0f, 16.0f).texture("#diagonal_1").end()
				.face(Direction.byName("south")).uvs(0.0f, 0.0f, 16.0f, 16.0f).texture("#diagonal_1").end()
				.face(Direction.byName("west")).uvs(0.0f, 0.0f, 16.0f, 16.0f).texture("#diagonal_2").end()
				.face(Direction.byName("up")).uvs(0.0f, 0.0f, 16.0f, 16.0f).texture("#diagonal_1").end()
				.face(Direction.byName("down")).uvs(0.0f, 0.0f, 16.0f, 16.0f).texture("#diagonal_1").end().end();
		
		BlockModelBuilder carver_block = models().getBuilder("block/carver/carver_block_base")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_base")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_base_mirror")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_base")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_base_mirror")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_e = models().getBuilder("block/carver/carver_block_e")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_one")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_base")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_base_mirror")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_en = models().getBuilder("block/carver/carver_block_en")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_one")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_one")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_ens = models().getBuilder("block/carver/carver_block_ens")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_one")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_both")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_both_mirror")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_ensw = models().getBuilder("block/carver/carver_block_ensw")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_both")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_both_mirror")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_both")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_both_mirror")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_enw = models().getBuilder("block/carver/carver_block_enw")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_both")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_both_mirror")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_one")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_es = models().getBuilder("block/carver/carver_block_es")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_one")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_one")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_esw = models().getBuilder("block/carver/carver_block_esw")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_both")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_both_mirror")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_one")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_ew = models().getBuilder("block/carver/carver_block_ew")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_both")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_both_mirror")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_base")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_base")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_n = models().getBuilder("block/carver/carver_block_n")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_base")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_base_mirror")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_one")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_ns = models().getBuilder("block/carver/carver_block_ns")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_base")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_base_mirror")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_both")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_both_mirror")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_nsw = models().getBuilder("block/carver/carver_block_nsw")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_one")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_both")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_both_mirror")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_nw = models().getBuilder("block/carver/carver_block_nw")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_one")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_one")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_s = models().getBuilder("block/carver/carver_block_s")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_base")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_base_mirror")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_one")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_sw = models().getBuilder("block/carver/carver_block_sw")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_one")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_one")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 BlockModelBuilder carver_block_w = models().getBuilder("block/carver/carver_block_w")
				 	.parent(carver_block_base)
				 	.texture("diagonal_1", "planttech2:blocks/machine/carver_base_2")
				 	.texture("diagonal_2", "planttech2:blocks/machine/carver_base_2_mirror")
				 	.texture("ew_1", "planttech2:blocks/machine/carver_one")
				 	.texture("ew_2", "planttech2:blocks/machine/carver_one_mirror")
				 	.texture("ns_1", "planttech2:blocks/machine/carver_base")
				 	.texture("ns_2", "planttech2:blocks/machine/carver_base_mirror")
				 	.texture("particles", "planttech2:blocks/machine/carver_base");
				 
				 states.getVariantBuilder(ModBlocks.CARVER)
				 	.partialState()
				 		.with(CarverBlock.EAST, false).with(CarverBlock.NORTH, false).with(CarverBlock.SOUTH, false).with(CarverBlock.WEST, false)
				 		.modelForState().modelFile(carver_block).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, true).with(CarverBlock.NORTH, false).with(CarverBlock.SOUTH, false).with(CarverBlock.WEST, false)
				 		.modelForState().modelFile(carver_block_e).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, false).with(CarverBlock.NORTH, true).with(CarverBlock.SOUTH, false).with(CarverBlock.WEST, false)
				 		.modelForState().modelFile(carver_block_n).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, false).with(CarverBlock.NORTH, false).with(CarverBlock.SOUTH, true).with(CarverBlock.WEST, false)
				 		.modelForState().modelFile(carver_block_s).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, false).with(CarverBlock.NORTH, false).with(CarverBlock.SOUTH, false).with(CarverBlock.WEST, true)
				 		.modelForState().modelFile(carver_block_w).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, true).with(CarverBlock.NORTH, true).with(CarverBlock.SOUTH, false).with(CarverBlock.WEST, false)
				 		.modelForState().modelFile(carver_block_en).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, true).with(CarverBlock.NORTH, false).with(CarverBlock.SOUTH, true).with(CarverBlock.WEST, false)
				 		.modelForState().modelFile(carver_block_es).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, true).with(CarverBlock.NORTH, false).with(CarverBlock.SOUTH, false).with(CarverBlock.WEST, true)
				 		.modelForState().modelFile(carver_block_ew).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, false).with(CarverBlock.NORTH, true).with(CarverBlock.SOUTH, true).with(CarverBlock.WEST, false)
				 		.modelForState().modelFile(carver_block_ns).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, false).with(CarverBlock.NORTH, true).with(CarverBlock.SOUTH, false).with(CarverBlock.WEST, true)
				 		.modelForState().modelFile(carver_block_nw).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, false).with(CarverBlock.NORTH, false).with(CarverBlock.SOUTH, true).with(CarverBlock.WEST, true)
				 		.modelForState().modelFile(carver_block_sw).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, true).with(CarverBlock.NORTH, true).with(CarverBlock.SOUTH, true).with(CarverBlock.WEST, false)
				 		.modelForState().modelFile(carver_block_ens).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, true).with(CarverBlock.NORTH, true).with(CarverBlock.SOUTH, false).with(CarverBlock.WEST, true)
				 		.modelForState().modelFile(carver_block_enw).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, true).with(CarverBlock.NORTH, false).with(CarverBlock.SOUTH, true).with(CarverBlock.WEST, true)
				 		.modelForState().modelFile(carver_block_esw).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, false).with(CarverBlock.NORTH, true).with(CarverBlock.SOUTH, true).with(CarverBlock.WEST, true)
				 		.modelForState().modelFile(carver_block_nsw).addModel()
				 	.partialState()
				 		.with(CarverBlock.EAST, true).with(CarverBlock.NORTH, true).with(CarverBlock.SOUTH, true).with(CarverBlock.WEST, true)
				 		.modelForState().modelFile(carver_block_ensw).addModel();
	}
}
