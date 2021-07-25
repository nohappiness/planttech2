package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.gui.*;
import net.minecraft.client.gui.screens.MenuScreens;

public class ModScreens
{
	public static void registerGUI()
	{
		MenuScreens.register(ModContainers.COMPRESSOR, CompressorScreen::new);
		MenuScreens.register(ModContainers.DNACLEANER, DNACleanerScreen::new);
		MenuScreens.register(ModContainers.DNACOMBINER, DNACombinerScreen::new);
		MenuScreens.register(ModContainers.DNAEXTRACTOR, DNAExtractorScreen::new);
		MenuScreens.register(ModContainers.DNAREMOVER, DNARemoverScreen::new);
		MenuScreens.register(ModContainers.ENERGYSTORAGE, EnergyStorageScreen::new);
		MenuScreens.register(ModContainers.ENERGYSUPPLIER, EnergySupplierScreen::new);
		MenuScreens.register(ModContainers.IDENTIFIER, IdentifierScreen::new);
		MenuScreens.register(ModContainers.INFUSER, InfuserScreen::new);
		MenuScreens.register(ModContainers.UPGRADEABLEITEM, ItemUpgradeableScreen::new);
		MenuScreens.register(ModContainers.MEGAFURNACE, MegaFurnaceScreen::new);
		MenuScreens.register(ModContainers.PLANTFARM, PlantFarmScreen::new);
		MenuScreens.register(ModContainers.SEEDCONSTRUCTOR, SeedconstructorScreen::new);
		MenuScreens.register(ModContainers.SEEDQUEEZER, SeedSqueezerScreen::new);
		MenuScreens.register(ModContainers.SOLARGENERATOR, SolarGeneratorScreen::new);
		MenuScreens.register(ModContainers.CHIPALYZER, ChipalyzerScreen::new);
		MenuScreens.register(ModContainers.MACHINEBULBREPROCESSOR, MachineBulbReprocessorScreen::new);
		MenuScreens.register(ModContainers.TELEPORTERITEM, TeleporterScreen::new);
		MenuScreens.register(ModContainers.PLANTTOPIATELEPORTER, PlantTopiaTeleporterScreen::new);
		MenuScreens.register(ModContainers.CROPAURAGENERATOR, CropAuraGeneratorScreen::new);
	}
}
