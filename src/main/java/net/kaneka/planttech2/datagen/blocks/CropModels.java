package net.kaneka.planttech2.datagen.blocks;

import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.crops.CropList;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import org.apache.commons.lang3.Validate;

public class CropModels extends BlockModelBase
{
	private final CropList crops;

	CropModels(BlockStateGenerator states, CropList crops)
	{
		super(states);
		this.crops = crops;
	}

	@Override
	public void registerStatesAndModels()
	{
		ModelFile cropBase = models().getExistingFile(models().modLoc("block/basic/crops"));
		for (int num : new int[]{0, 1, 2}) {
			models().getBuilder("crop_" + num)
					.parent(cropBase)
					.texture("bars", models().modLoc("blocks/cropbars"))
					.texture("crops", models().modLoc("blocks/crop_" + num))
					.texture("particles", models().modLoc("blocks/empty"));
		}
		for (int num : new int[]{3, 4, 5, 6, 7}) {
			models().getBuilder("crop_" + num)
					.parent(cropBase)
					.texture("bars", models().modLoc("blocks/cropbars"))
					.texture("crops", models().modLoc("blocks/crop_" + num))
					.texture("particles", models().modLoc("blocks/crop_" + num + "_blooms"));
		}
		crops.keySet().forEach(cropName -> {
			CropBaseBlock b = ModBlocks.CROPS.get(cropName);
			Validate.notNull(b, "Crop list entry %s does not have corresponding block", cropBase);
			states.getVariantBuilder(b)
					.forAllStates(state -> new ConfiguredModel[]{
							new ConfiguredModel(models().getBuilder("crop_" + state.get(CropBaseBlock.GROWSTATE)))
					});
		});
	}
}
