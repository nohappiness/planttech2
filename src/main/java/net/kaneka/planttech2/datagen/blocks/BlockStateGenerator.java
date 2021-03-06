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
	private final BuildingBlockModels buildingBlocks = new BuildingBlockModels(this);
	private final CarverModels carver = new CarverModels(this);
	private final ElectricFenceModels electric_fence = new ElectricFenceModels(this); 
	private final MachineModels machines = new MachineModels(this); 

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
		buildingBlocks.registerStatesAndModels();
		carver.registerStatesAndModels();
		electric_fence.registerStatesAndModels();
		machines.registerStatesAndModels();
	}
}
