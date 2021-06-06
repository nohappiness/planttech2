package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.gui.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModScreens
{
	public static void registerGUI()
	{
		ScreenManager.register(ModContainers.COMPRESSOR, CompressorScreen::new);
		ScreenManager.register(ModContainers.DNACLEANER, DNACleanerScreen::new);
		ScreenManager.register(ModContainers.DNACOMBINER, DNACombinerScreen::new);
		ScreenManager.register(ModContainers.DNAEXTRACTOR, DNAExtractorScreen::new);
		ScreenManager.register(ModContainers.DNAREMOVER, DNARemoverScreen::new);
		ScreenManager.register(ModContainers.ENERGYSTORAGE, EnergyStorageScreen::new);
		ScreenManager.register(ModContainers.ENERGYSUPPLIER, EnergySupplierScreen::new);
		ScreenManager.register(ModContainers.IDENTIFIER, IdentifierScreen::new);
		ScreenManager.register(ModContainers.INFUSER, InfuserScreen::new);
		ScreenManager.register(ModContainers.UPGRADEABLEITEM, ItemUpgradeableScreen::new);
		ScreenManager.register(ModContainers.MEGAFURNACE, MegaFurnaceScreen::new);
		ScreenManager.register(ModContainers.PLANTFARM, PlantFarmScreen::new);
		ScreenManager.register(ModContainers.SEEDCONSTRUCTOR, SeedconstructorScreen::new);
		ScreenManager.register(ModContainers.SEEDQUEEZER, SeedSqueezerScreen::new);
		ScreenManager.register(ModContainers.SOLARGENERATOR, SolarGeneratorScreen::new);
		ScreenManager.register(ModContainers.CHIPALYZER, ChipalyzerScreen::new);
		ScreenManager.register(ModContainers.MACHINEBULBREPROCESSOR, MachineBulbReprocessorScreen::new);
		ScreenManager.register(ModContainers.TELEPORTERITEM, TeleporterScreen::new);
		ScreenManager.register(ModContainers.PLANTTOPIATELEPORTER, PlantTopiaTeleporterScreen::new);
		ScreenManager.register(ModContainers.TECHVILLAGER, TechVillagerScreen::new);
		ScreenManager.register(ModContainers.CROPAURAGENERATOR, CropAuraGeneratorScreen::new);
	}
}
