package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.gui.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModScreens
{
	public static void registerGUI()
	{
		ScreenManager.registerFactory(ModContainers.COMPRESSOR, CompressorScreen::new);
		ScreenManager.registerFactory(ModContainers.DNACLEANER, DNACleanerScreen::new);
		ScreenManager.registerFactory(ModContainers.DNACOMBINER, DNACombinerScreen::new);
		ScreenManager.registerFactory(ModContainers.DNAEXTRACTOR, DNAExtractorScreen::new);
		ScreenManager.registerFactory(ModContainers.DNAREMOVER, DNARemoverScreen::new);
		ScreenManager.registerFactory(ModContainers.ENERGYSTORAGE, EnergyStorageScreen::new);
		ScreenManager.registerFactory(ModContainers.ENERGYSUPPLIER, EnergySupplierScreen::new);
		ScreenManager.registerFactory(ModContainers.IDENTIFIER, IdentifierScreen::new);
		ScreenManager.registerFactory(ModContainers.INFUSER, InfuserScreen::new);
		ScreenManager.registerFactory(ModContainers.UPGRADEABLEITEM, ItemUpgradeableScreen::new);
		ScreenManager.registerFactory(ModContainers.MEGAFURNACE, MegaFurnaceScreen::new);
		ScreenManager.registerFactory(ModContainers.PLANTFARM, PlantFarmScreen::new);
		ScreenManager.registerFactory(ModContainers.SEEDCONSTRUCTOR, SeedconstructorScreen::new);
		ScreenManager.registerFactory(ModContainers.SEEDQUEEZER, SeedSqueezerScreen::new);
		ScreenManager.registerFactory(ModContainers.SOLARGENERATOR, SolarGeneratorScreen::new);
		ScreenManager.registerFactory(ModContainers.CHIPALYZER, ChipalyzerScreen::new);
		ScreenManager.registerFactory(ModContainers.MACHINEBULBREPROCESSOR, MachineBulbReprocessorScreen::new);
		ScreenManager.registerFactory(ModContainers.TELEPORTERITEM, TeleporterScreen::new);
		ScreenManager.registerFactory(ModContainers.PLANTTOPIATELEPORTER, PlantTopiaTeleporterScreen::new);
		ScreenManager.registerFactory(ModContainers.TECHVILLAGER, TechVillagerScreen::new);
	}
}
