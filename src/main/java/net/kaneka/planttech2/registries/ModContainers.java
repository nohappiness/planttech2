package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.container.ContainerCompressor;
import net.kaneka.planttech2.container.ContainerDNACleaner;
import net.kaneka.planttech2.container.ContainerDNACombiner;
import net.kaneka.planttech2.container.ContainerDNAExtractor;
import net.kaneka.planttech2.container.ContainerDNARemover;
import net.kaneka.planttech2.container.ContainerEnergyStorage;
import net.kaneka.planttech2.container.ContainerIdentifier;
import net.kaneka.planttech2.container.ContainerInfuser;
import net.kaneka.planttech2.container.ContainerItemUpgradeable;
import net.kaneka.planttech2.container.ContainerMegaFurnace;
import net.kaneka.planttech2.container.ContainerPlantFarm;
import net.kaneka.planttech2.container.ContainerSeedSqueezer;
import net.kaneka.planttech2.container.ContainerSeedconstructor;
import net.kaneka.planttech2.container.ContainerSolarGenerator;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;

public class ModContainers
{
	public static final ContainerType<ContainerCompressor> COMPRESSOR = new ContainerType<ContainerCompressor>(ContainerCompressor::new);
	public static final ContainerType<ContainerDNACleaner> DNACLEANER = new ContainerType<ContainerDNACleaner>(ContainerDNACleaner::new);
	public static final ContainerType<ContainerDNACombiner> DNACOMBINER = new ContainerType<ContainerDNACombiner>(ContainerDNACombiner::new);
	public static final ContainerType<ContainerDNAExtractor> DNAEXTRACTOR = new ContainerType<ContainerDNAExtractor>(ContainerDNAExtractor::new);
	public static final ContainerType<ContainerDNARemover> DNAREMOVER = new ContainerType<ContainerDNARemover>(ContainerDNARemover::new);
	public static final ContainerType<ContainerEnergyStorage> ENERGYSTORAGE = new ContainerType<ContainerEnergyStorage>(ContainerEnergyStorage::new);
	public static final ContainerType<ContainerIdentifier> IDENTIFIER = new ContainerType<ContainerIdentifier>(ContainerIdentifier::new);
	public static final ContainerType<ContainerInfuser> INFUSER = new ContainerType<ContainerInfuser>(ContainerInfuser::new);
	public static final ContainerType<ContainerItemUpgradeable> UPGRADEABLEITEM = new ContainerType<ContainerItemUpgradeable>(ContainerItemUpgradeable::new);
	public static final ContainerType<ContainerMegaFurnace> MEGAFURNACE = new ContainerType<ContainerMegaFurnace>(ContainerMegaFurnace::new);
	public static final ContainerType<ContainerPlantFarm> PLANTFARM = new ContainerType<ContainerPlantFarm>(ContainerPlantFarm::new);
	public static final ContainerType<ContainerSeedconstructor> SEEDCONSTRUCTOR = new ContainerType<ContainerSeedconstructor>(ContainerSeedconstructor::new);
	public static final ContainerType<ContainerSeedSqueezer> SEEDQUEEZER = new ContainerType<ContainerSeedSqueezer>(ContainerSeedSqueezer::new);
	public static final ContainerType<ContainerSolarGenerator> SOLARGENERATOR = new ContainerType<ContainerSolarGenerator>(ContainerSolarGenerator::new);
	
	public static final void registerAll(RegistryEvent.Register<ContainerType<?>> event)
	{
		event.getRegistry().registerAll(COMPRESSOR.setRegistryName("planttech2:compressorcontainer"), 
										DNACLEANER.setRegistryName("planttech2:dnacleanercontainer"), 
										DNACOMBINER.setRegistryName("planttech2:dnacombinercontainer"), 
										DNAEXTRACTOR.setRegistryName("planttech2:dnaextractorcontainer"),
										DNAREMOVER.setRegistryName("planttech2:dnaremovercontainer"),
										ENERGYSTORAGE.setRegistryName("planttech2:energystoragecontainer"),
										IDENTIFIER.setRegistryName("planttech2:identifiercontainer"),
										INFUSER.setRegistryName("planttech2:infusercontainer"),
										UPGRADEABLEITEM.setRegistryName("planttech2:upgradeableitemcontainer"),
										MEGAFURNACE.setRegistryName("planttech2:megafurnacecontainer"),
										PLANTFARM.setRegistryName("planttech2:plantfarmcontainer"),
										SEEDCONSTRUCTOR.setRegistryName("planttech2:seedconstructorcontainer"),
										SEEDQUEEZER.setRegistryName("planttech2:seedconstructorcontainer"),
										SOLARGENERATOR.setRegistryName("planttech2:solargeneratorcontainer"));
	}
}
