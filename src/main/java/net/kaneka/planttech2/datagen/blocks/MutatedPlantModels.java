package net.kaneka.planttech2.datagen.blocks;

import net.kaneka.planttech2.blocks.ObtainableTallBushBlock;
import net.kaneka.planttech2.blocks.baseclasses.ObtainableNaturalPlants;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraftforge.client.model.generators.ConfiguredModel;

public class MutatedPlantModels extends BlockModelBase
{
	MutatedPlantModels(BlockStateGenerator states)
	{
		super(states);
	}

	public void registerStatesAndModels()
	{
		mutatedSingle(ModBlocks.MUTATED_ALLIUM);
		mutatedSingle(ModBlocks.MUTATED_AZURE_BLUET);
		mutatedSingle(ModBlocks.MUTATED_BLUE_ORCHID);
		mutatedSingle(ModBlocks.MUTATED_CORNFLOWER);
		mutatedSingle(ModBlocks.MUTATED_DANDELION);
		mutatedTall(ModBlocks.MUTATED_LILAC);
		mutatedSingle(ModBlocks.MUTATED_LILY_OF_THE_VALLEY);
		mutatedSingle(ModBlocks.MUTATED_ORANGE_TULIP);
		mutatedSingle(ModBlocks.MUTATED_OXEYE_DAISY);
		mutatedSingle(ModBlocks.MUTATED_PINK_TULIP);
		mutatedTall(ModBlocks.MUTATED_PEONY);
		mutatedSingle(ModBlocks.MUTATED_POPPY);
		mutatedSingle(ModBlocks.MUTATED_RED_TULIP);
		mutatedTall(ModBlocks.MUTATED_ROSE_BUSH);
		mutatedSingle(ModBlocks.MUTATED_WHITE_TULIP);
	}

	void mutatedSingle(ObtainableNaturalPlants mutatedPlant)
	{
		String path = mutatedPlant.getRegistryName().getPath();
		states.simpleBlock(mutatedPlant,
				states.models().cross("block/plants/" + path, states.modLoc("blocks/plants/" + path)));
	}

	void mutatedTall(ObtainableTallBushBlock mutatedPlant)
	{
		String path = mutatedPlant.getRegistryName().getPath();
		states.getVariantBuilder(mutatedPlant)
				.partialState()
				.with(ObtainableTallBushBlock.IS_TOP, false)
				.addModels(new ConfiguredModel(states.models().cross("block/plants/" + path + "_bottom", states.modLoc("blocks/plants/" + path + "_bottom"))))
				.partialState()
				.with(ObtainableTallBushBlock.IS_TOP, true)
				.addModels(new ConfiguredModel(states.models().cross("block/plants/" + path + "_top", states.modLoc("blocks/plants/" + path + "_top"))));
	}
}
