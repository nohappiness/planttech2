package net.kaneka.planttech2.datagen.blocks;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateGenerator extends BlockStateProvider
{
	private final MutatedPlantModels mutatedPlants = new MutatedPlantModels(this);
	private final CropModels crops = new CropModels(this, PlantTechMain.getCropList());
	private final HedgeModels hedges = new HedgeModels(this);

	public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper)
	{
		super(gen, PlantTechMain.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels()
	{
		mutatedPlants.registerStatesAndModels();
		crops.registerStatesAndModels();
		hedges.registerStatesAndModels();
	}
}
