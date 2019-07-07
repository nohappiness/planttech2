package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.container.ChipalyzerContainer;
import net.kaneka.planttech2.container.CompressorContainer;
import net.kaneka.planttech2.container.DNACleanerContainer;
import net.kaneka.planttech2.container.DNACombinerContainer;
import net.kaneka.planttech2.container.DNAExtractorContainer;
import net.kaneka.planttech2.container.DNARemoverContainer;
import net.kaneka.planttech2.container.EnergyStorageContainer;
import net.kaneka.planttech2.container.IdentifierContainer;
import net.kaneka.planttech2.container.InfuserContainer;
import net.kaneka.planttech2.container.ItemUpgradeableContainer;
import net.kaneka.planttech2.container.MegaFurnaceContainer;
import net.kaneka.planttech2.container.PlantFarmContainer;
import net.kaneka.planttech2.container.SeedSqueezerContainer;
import net.kaneka.planttech2.container.SeedconstructorContainer;
import net.kaneka.planttech2.container.SolarGeneratorContainer;
import net.kaneka.planttech2.container.TeleporterContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;

public class ModContainers
{
	public static final ContainerType<CompressorContainer> COMPRESSOR = new ContainerType<CompressorContainer>(CompressorContainer::new);
	public static final ContainerType<DNACleanerContainer> DNACLEANER = new ContainerType<DNACleanerContainer>(DNACleanerContainer::new);
	public static final ContainerType<DNACombinerContainer> DNACOMBINER = new ContainerType<DNACombinerContainer>(DNACombinerContainer::new);
	public static final ContainerType<DNAExtractorContainer> DNAEXTRACTOR = new ContainerType<DNAExtractorContainer>(DNAExtractorContainer::new);
	public static final ContainerType<DNARemoverContainer> DNAREMOVER = new ContainerType<DNARemoverContainer>(DNARemoverContainer::new);
	public static final ContainerType<EnergyStorageContainer> ENERGYSTORAGE = new ContainerType<EnergyStorageContainer>(EnergyStorageContainer::new);
	public static final ContainerType<IdentifierContainer> IDENTIFIER = new ContainerType<IdentifierContainer>(IdentifierContainer::new);
	public static final ContainerType<InfuserContainer> INFUSER = new ContainerType<InfuserContainer>(InfuserContainer::new);
	public static final ContainerType<ItemUpgradeableContainer> UPGRADEABLEITEM = new ContainerType<ItemUpgradeableContainer>(ItemUpgradeableContainer::new);
	public static final ContainerType<MegaFurnaceContainer> MEGAFURNACE = new ContainerType<MegaFurnaceContainer>(MegaFurnaceContainer::new);
	public static final ContainerType<PlantFarmContainer> PLANTFARM = new ContainerType<PlantFarmContainer>(PlantFarmContainer::new);
	public static final ContainerType<SeedconstructorContainer> SEEDCONSTRUCTOR = new ContainerType<SeedconstructorContainer>(SeedconstructorContainer::new);
	public static final ContainerType<SeedSqueezerContainer> SEEDQUEEZER = new ContainerType<SeedSqueezerContainer>(SeedSqueezerContainer::new);
	public static final ContainerType<SolarGeneratorContainer> SOLARGENERATOR = new ContainerType<SolarGeneratorContainer>(SolarGeneratorContainer::new);
	public static final ContainerType<ChipalyzerContainer> CHIPALYZER = new ContainerType<ChipalyzerContainer>(ChipalyzerContainer::new);
	public static final ContainerType<TeleporterContainer> TELEPORTERITEM = new ContainerType<TeleporterContainer>(TeleporterContainer::new);
	
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
										SEEDQUEEZER.setRegistryName("planttech2:seedsqueezercontainer"),
										SOLARGENERATOR.setRegistryName("planttech2:solargeneratorcontainer"), 
										CHIPALYZER.setRegistryName("planttech2:chipalyzercontainer"), 
										TELEPORTERITEM.setRegistryName("planttech2:teleporteritem"));
	}
}
