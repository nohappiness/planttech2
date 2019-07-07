package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.gui.ChipalyzerScreen;
import net.kaneka.planttech2.gui.CompressorScreen;
import net.kaneka.planttech2.gui.DNACleanerScreen;
import net.kaneka.planttech2.gui.DNACombinerScreen;
import net.kaneka.planttech2.gui.DNAExtractorScreen;
import net.kaneka.planttech2.gui.DNARemoverScreen;
import net.kaneka.planttech2.gui.EnergyStorageScreen;
import net.kaneka.planttech2.gui.IdentifierScreen;
import net.kaneka.planttech2.gui.InfuserScreen;
import net.kaneka.planttech2.gui.MegaFurnaceScreen;
import net.kaneka.planttech2.gui.PlantFarmScreen;
import net.kaneka.planttech2.gui.SeedSqueezerScreen;
import net.kaneka.planttech2.gui.SeedconstructorScreen;
import net.kaneka.planttech2.gui.SolarGeneratorScreen;
import net.kaneka.planttech2.gui.TeleporterScreen;
import net.kaneka.planttech2.gui.ItemUpgradeableScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModScreens
{
	@OnlyIn(Dist.CLIENT)
	public static final void registerGUI()
	{
		ScreenManager.registerFactory(ModContainers.COMPRESSOR, CompressorScreen::new);
		ScreenManager.registerFactory(ModContainers.DNACLEANER, DNACleanerScreen::new);
		ScreenManager.registerFactory(ModContainers.DNACOMBINER, DNACombinerScreen::new);
		ScreenManager.registerFactory(ModContainers.DNAEXTRACTOR, DNAExtractorScreen::new);
		ScreenManager.registerFactory(ModContainers.DNAREMOVER, DNARemoverScreen::new);
		ScreenManager.registerFactory(ModContainers.ENERGYSTORAGE, EnergyStorageScreen::new);
		ScreenManager.registerFactory(ModContainers.IDENTIFIER, IdentifierScreen::new);
		ScreenManager.registerFactory(ModContainers.INFUSER, InfuserScreen::new);
		ScreenManager.registerFactory(ModContainers.UPGRADEABLEITEM, ItemUpgradeableScreen::new);
		ScreenManager.registerFactory(ModContainers.MEGAFURNACE, MegaFurnaceScreen::new);
		ScreenManager.registerFactory(ModContainers.PLANTFARM, PlantFarmScreen::new);
		ScreenManager.registerFactory(ModContainers.SEEDCONSTRUCTOR, SeedconstructorScreen::new);
		ScreenManager.registerFactory(ModContainers.SEEDQUEEZER, SeedSqueezerScreen::new);
		ScreenManager.registerFactory(ModContainers.SOLARGENERATOR, SolarGeneratorScreen::new);
		ScreenManager.registerFactory(ModContainers.CHIPALYZER, ChipalyzerScreen::new);
		ScreenManager.registerFactory(ModContainers.TELEPORTERITEM, TeleporterScreen::new);
	}
}
