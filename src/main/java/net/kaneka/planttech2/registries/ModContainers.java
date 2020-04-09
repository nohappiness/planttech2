package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.container.*;
import net.kaneka.planttech2.container.entities.TechVillagerContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.network.IContainerFactory;

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
	public static final ContainerType<MachineBulbReprocessorContainer> MACHINEBULBREPROCESSOR = new ContainerType<MachineBulbReprocessorContainer>(MachineBulbReprocessorContainer::new);
	public static final ContainerType<PlantTopiaTeleporterContainer> PLANTTOPIATELEPORTER = new ContainerType<PlantTopiaTeleporterContainer>(PlantTopiaTeleporterContainer::new);
	public static final ContainerType<TeleporterContainer> TELEPORTERITEM = new ContainerType<TeleporterContainer>(TeleporterContainer::new);
	public static ContainerType<TechVillagerContainer> TECHVILLAGER = new ContainerType<TechVillagerContainer>((IContainerFactory<TechVillagerContainer>) TechVillagerContainer::new);
	public static final ContainerType<EnergySupplierContainer> ENERGYSUPPLIER = new ContainerType<EnergySupplierContainer>(EnergySupplierContainer::new);
	
	
	public static final void registerAll(RegistryEvent.Register<ContainerType<?>> event)
	{
		event.getRegistry().registerAll(COMPRESSOR.setRegistryName(ModReferences.COMPRESSORCONTAINER), 
										DNACLEANER.setRegistryName(ModReferences.DNACLEANERCONTAINER), 
										DNACOMBINER.setRegistryName(ModReferences.DNACOMBINERCONTAINER), 
										DNAEXTRACTOR.setRegistryName(ModReferences.DNAEXTRACTORCONTAINER),
										DNAREMOVER.setRegistryName(ModReferences.DNAREMOVERCONTAINER),
										ENERGYSTORAGE.setRegistryName(ModReferences.ENERGYSTORAGECONTAINER),
										IDENTIFIER.setRegistryName(ModReferences.IDENTIFIERCONTAINER),
										INFUSER.setRegistryName(ModReferences.INFUSERCONTAINER),
										UPGRADEABLEITEM.setRegistryName(ModReferences.UPGRADEABLEITEMCONTAINER),
										MEGAFURNACE.setRegistryName(ModReferences.MEGAFURNACECONTAINER),
										PLANTFARM.setRegistryName(ModReferences.PLANTFARMCONTAINER),
										SEEDCONSTRUCTOR.setRegistryName(ModReferences.SEEDCONSTRUCTORCONTAINER),
										SEEDQUEEZER.setRegistryName(ModReferences.SEEDQUEEZERCONTAINER),
										TECHVILLAGER.setRegistryName(ModReferences.TECHVILLAGERCONTAINER),
										SOLARGENERATOR.setRegistryName(ModReferences.SOLARGENERATORCONTAINER), 
										CHIPALYZER.setRegistryName(ModReferences.CHIPALYZERCONTAINER), 
										MACHINEBULBREPROCESSOR.setRegistryName(ModReferences.MACHINEBULBREPROCESSORCONTAINER), 
										TELEPORTERITEM.setRegistryName(ModReferences.TELEPORTERITEMCONTAINER), 
										PLANTTOPIATELEPORTER.setRegistryName(ModReferences.TELEPORTERBLOCKCONTAINER), 
										ENERGYSUPPLIER.setRegistryName(ModReferences.ENERGYSUPPLIERCONTAINER));
	}
}
