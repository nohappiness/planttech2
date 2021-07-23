package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.inventory.*;
import net.kaneka.planttech2.inventory.entities.TechVillagerContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static net.kaneka.planttech2.registries.ModReferences.*;

@ObjectHolder(PlantTechMain.MODID)
public class ModContainers
{
	@ObjectHolder(COMPRESSORCONTAINER) public static MenuType<CompressorContainer> COMPRESSOR;
	@ObjectHolder(DNACLEANERCONTAINER) public static ContainerType<DNACleanerContainer> DNACLEANER;
	@ObjectHolder(DNACOMBINERCONTAINER) public static ContainerType<DNACombinerContainer> DNACOMBINER;
	@ObjectHolder(DNAEXTRACTORCONTAINER) public static ContainerType<DNAExtractorContainer> DNAEXTRACTOR;
	@ObjectHolder(DNAREMOVERCONTAINER) public static ContainerType<DNARemoverContainer> DNAREMOVER;
	@ObjectHolder(ENERGYSTORAGECONTAINER) public static ContainerType<EnergyStorageContainer> ENERGYSTORAGE;
	@ObjectHolder(IDENTIFIERCONTAINER) public static ContainerType<IdentifierContainer> IDENTIFIER;
	@ObjectHolder(INFUSERCONTAINER) public static ContainerType<InfuserContainer> INFUSER;
	@ObjectHolder(UPGRADEABLEITEMCONTAINER) public static ContainerType<ItemUpgradeableContainer> UPGRADEABLEITEM;
	@ObjectHolder(MEGAFURNACECONTAINER) public static ContainerType<MegaFurnaceContainer> MEGAFURNACE;
	@ObjectHolder(PLANTFARMCONTAINER) public static ContainerType<PlantFarmContainer> PLANTFARM;
	@ObjectHolder(SEEDCONSTRUCTORCONTAINER) public static ContainerType<SeedConstructorContainer> SEEDCONSTRUCTOR;
	@ObjectHolder(SEEDQUEEZERCONTAINER) public static ContainerType<SeedSqueezerContainer> SEEDQUEEZER;
	@ObjectHolder(SOLARGENERATORCONTAINER) public static ContainerType<SolarGeneratorContainer> SOLARGENERATOR;
	@ObjectHolder(CHIPALYZERCONTAINER) public static ContainerType<ChipalyzerContainer> CHIPALYZER;
	@ObjectHolder(MACHINEBULBREPROCESSORCONTAINER) public static ContainerType<MachineBulbReprocessorContainer> MACHINEBULBREPROCESSOR;
	@ObjectHolder(TELEPORTERBLOCKCONTAINER) public static ContainerType<PlantTopiaTeleporterContainer> PLANTTOPIATELEPORTER;
	@ObjectHolder(TELEPORTERITEMCONTAINER) public static ContainerType<TeleporterContainer> TELEPORTERITEM;
	@ObjectHolder(TECHVILLAGERCONTAINER) public static ContainerType<TechVillagerContainer> TECHVILLAGER;
	@ObjectHolder(ENERGYSUPPLIERCONTAINER) public static ContainerType<EnergySupplierContainer> ENERGYSUPPLIER;
	@ObjectHolder(CROPAURAGENERATORCONTAINER) public static ContainerType<CropAuraGeneratorContainer> CROPAURAGENERATOR;

	public static void registerAll(RegistryEvent.Register<ContainerType<?>> event)
	{
		IForgeRegistry<ContainerType<?>> registry = event.getRegistry();
		registry.register(make(COMPRESSORCONTAINER, new ContainerType<>(CompressorContainer::new)));
		registry.register(make(DNACLEANERCONTAINER, new ContainerType<>(DNACleanerContainer::new)));
		registry.register(make(DNACOMBINERCONTAINER, new ContainerType<>(DNACombinerContainer::new)));
		registry.register(make(DNAEXTRACTORCONTAINER, new ContainerType<>(DNAExtractorContainer::new)));
		registry.register(make(DNAREMOVERCONTAINER, new ContainerType<>(DNARemoverContainer::new)));
		registry.register(make(ENERGYSTORAGECONTAINER, new ContainerType<>(EnergyStorageContainer::new)));
		registry.register(make(IDENTIFIERCONTAINER, new ContainerType<>(IdentifierContainer::new)));
		registry.register(make(INFUSERCONTAINER, new ContainerType<>(InfuserContainer::new)));
		registry.register(make(UPGRADEABLEITEMCONTAINER, new ContainerType<>(ItemUpgradeableContainer::new)));
		registry.register(make(MEGAFURNACECONTAINER, new ContainerType<>(MegaFurnaceContainer::new)));
		registry.register(make(PLANTFARMCONTAINER, new ContainerType<>(PlantFarmContainer::new)));
		registry.register(make(SEEDCONSTRUCTORCONTAINER, new ContainerType<>(SeedConstructorContainer::new)));
		registry.register(make(SEEDQUEEZERCONTAINER, new ContainerType<>(SeedSqueezerContainer::new)));
		registry.register(make(SOLARGENERATORCONTAINER, new ContainerType<>(SolarGeneratorContainer::new)));
		registry.register(make(CHIPALYZERCONTAINER, new ContainerType<>(ChipalyzerContainer::new)));
		registry.register(make(MACHINEBULBREPROCESSORCONTAINER, new ContainerType<>(MachineBulbReprocessorContainer::new)));
		registry.register(make(TELEPORTERBLOCKCONTAINER, new ContainerType<>(PlantTopiaTeleporterContainer::new)));
		registry.register(make(TELEPORTERITEMCONTAINER, new ContainerType<>(TeleporterContainer::new)));
		registry.register(make(TECHVILLAGERCONTAINER, IForgeContainerType.create(TechVillagerContainer::new)));
		registry.register(make(ENERGYSUPPLIERCONTAINER, new ContainerType<>(EnergySupplierContainer::new)));
		registry.register(make(CROPAURAGENERATORCONTAINER, new ContainerType<>(CropAuraGeneratorContainer::new)));
	}

	static <C extends Container> ContainerType<C> make(String registryName, ContainerType<C> containerType)
	{
		containerType.setRegistryName(registryName);
		return containerType;
	}
}
