package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.gui.GUICompressor;
import net.kaneka.planttech2.gui.GUIDNACleaner;
import net.kaneka.planttech2.gui.GUIDNACombiner;
import net.kaneka.planttech2.gui.GUIDNAExtractor;
import net.kaneka.planttech2.gui.GUIDNARemover;
import net.kaneka.planttech2.gui.GUIEnergyStorage;
import net.kaneka.planttech2.gui.GUIIdentifier;
import net.kaneka.planttech2.gui.GUIInfuser;
import net.kaneka.planttech2.gui.GUIMegaFurnace;
import net.kaneka.planttech2.gui.GUIPlantFarm;
import net.kaneka.planttech2.gui.GUISeedSqueezer;
import net.kaneka.planttech2.gui.GUISeedconstructor;
import net.kaneka.planttech2.gui.GUISolarGenerator;
import net.kaneka.planttech2.gui.GuiItemUpgradeable;
import net.minecraft.client.gui.ScreenManager;

public class ModScreens
{
	public static final void registerGUI()
	{
		ScreenManager.registerFactory(ModContainers.COMPRESSOR, GUICompressor::new);
		ScreenManager.registerFactory(ModContainers.DNACLEANER, GUIDNACleaner::new);
		ScreenManager.registerFactory(ModContainers.DNACOMBINER, GUIDNACombiner::new);
		ScreenManager.registerFactory(ModContainers.DNAEXTRACTOR, GUIDNAExtractor::new);
		ScreenManager.registerFactory(ModContainers.DNAREMOVER, GUIDNARemover::new);
		ScreenManager.registerFactory(ModContainers.ENERGYSTORAGE, GUIEnergyStorage::new);
		ScreenManager.registerFactory(ModContainers.IDENTIFIER, GUIIdentifier::new);
		ScreenManager.registerFactory(ModContainers.INFUSER, GUIInfuser::new);
		ScreenManager.registerFactory(ModContainers.UPGRADEABLEITEM, GuiItemUpgradeable::new);
		ScreenManager.registerFactory(ModContainers.MEGAFURNACE, GUIMegaFurnace::new);
		ScreenManager.registerFactory(ModContainers.PLANTFARM, GUIPlantFarm::new);
		ScreenManager.registerFactory(ModContainers.SEEDCONSTRUCTOR, GUISeedconstructor::new);
		ScreenManager.registerFactory(ModContainers.SEEDQUEEZER, GUISeedSqueezer::new);
		ScreenManager.registerFactory(ModContainers.SOLARGENERATOR, GUISolarGenerator::new);
	}
}
