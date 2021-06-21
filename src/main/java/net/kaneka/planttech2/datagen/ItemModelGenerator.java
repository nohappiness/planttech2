package net.kaneka.planttech2.datagen;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.Hedge;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider
{

	public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper)
	{
		super(generator, PlantTechMain.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
//		registerBlockModels();
		registerItemModels();
		block(ModBlocks.INFUSED_ICE);
		block(ModBlocks.INFUSED_PACKED_ICE);
		block(ModBlocks.INFUSED_BLUE_ICE);
		block(ModBlocks.BLACK_ICE);
		block(ModBlocks.INFUSED_STONE);
		block(ModBlocks.INFUSED_COBBLESTONE);
	}

	private void registerBlockModels()
	{
		for(Hedge b: ModBlocks.HEDGE_BLOCKS)
		{
			blockHedge(b);
		}
	}

	private void registerItemModels()
	{
//		generatedItemModel("aura_chip_temperature_extreme_cold");
//		generatedItemModel("aura_chip_temperature_cold");
//		generatedItemModel("aura_chip_temperature_normal");
//		generatedItemModel("aura_chip_temperature_warm");
//		generatedItemModel("aura_chip_temperature_extreme_warm");
	}

	private void generatedItemModel(String name)
	{
		getBuilder(name)
				.parent(new ModelFile.UncheckedModelFile("item/generated"))
				.texture("layer0", modLoc("item/" + name));
	}
	
	private void block(Block b)
	{
		withExistingParent(b.getRegistryName().getPath(), modLoc("block/" + b.getRegistryName().getPath())); 
	}

	private void blockHedge(Hedge b)
	{
		withExistingParent(b.getRegistryName().getPath(), modLoc("block/hedge/" + b.getRegistryName().getPath().replace("hedge_", "") + "_base"));
	}
	
	@Override
    public String getName() 
	{
        return " planttech2 item model generator";
    }

}
