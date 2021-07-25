package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.inventory.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.awt.*;

import static net.kaneka.planttech2.registries.ModReferences.*;

@ObjectHolder(PlantTechMain.MODID)
public class ModContainers
{
	@ObjectHolder(COMPRESSORCONTAINER) public static MenuType<CompressorMenu> COMPRESSOR;
	@ObjectHolder(DNACLEANERCONTAINER) public static MenuType<DNACleanerMenu> DNACLEANER;
	@ObjectHolder(DNACOMBINERCONTAINER) public static MenuType<DNACombinerMenu> DNACOMBINER;
	@ObjectHolder(DNAEXTRACTORCONTAINER) public static MenuType<DNAExtractorMenu> DNAEXTRACTOR;
	@ObjectHolder(DNAREMOVERCONTAINER) public static MenuType<DNARemoverMenu> DNAREMOVER;
	@ObjectHolder(ENERGYSTORAGECONTAINER) public static MenuType<EnergyStorageMenu> ENERGYSTORAGE;
	@ObjectHolder(IDENTIFIERCONTAINER) public static MenuType<IdentifierMenu> IDENTIFIER;
	@ObjectHolder(INFUSERCONTAINER) public static MenuType<InfuserMenu> INFUSER;
	@ObjectHolder(UPGRADEABLEITEMCONTAINER) public static MenuType<ItemUpgradeableContainer> UPGRADEABLEITEM;
	@ObjectHolder(MEGAFURNACECONTAINER) public static MenuType<MegaFurnaceMenu> MEGAFURNACE;
	@ObjectHolder(PLANTFARMCONTAINER) public static MenuType<PlantFarmMenu> PLANTFARM;
	@ObjectHolder(SEEDCONSTRUCTORCONTAINER) public static MenuType<SeedConstructorMenu> SEEDCONSTRUCTOR;
	@ObjectHolder(SEEDQUEEZERCONTAINER) public static MenuType<SeedSqueezerMenu> SEEDQUEEZER;
	@ObjectHolder(SOLARGENERATORCONTAINER) public static MenuType<SolarGeneratorMenu> SOLARGENERATOR;
	@ObjectHolder(CHIPALYZERCONTAINER) public static MenuType<ChipalyzerMenu> CHIPALYZER;
	@ObjectHolder(MACHINEBULBREPROCESSORCONTAINER) public static MenuType<MachineBulbReprocessorMenu> MACHINEBULBREPROCESSOR;
	@ObjectHolder(TELEPORTERBLOCKCONTAINER) public static MenuType<PlantTopiaTeleporterMenu> PLANTTOPIATELEPORTER;
	@ObjectHolder(TELEPORTERITEMCONTAINER) public static MenuType<TeleporterContainer> TELEPORTERITEM;
	@ObjectHolder(ENERGYSUPPLIERCONTAINER) public static MenuType<EnergySupplierMenu> ENERGYSUPPLIER;
	@ObjectHolder(CROPAURAGENERATORCONTAINER) public static MenuType<CropAuraGeneratorMenu> CROPAURAGENERATOR;

	public static void registerAll(RegistryEvent.Register<MenuType<?>> event)
	{
		IForgeRegistry<MenuType<?>> registry = event.getRegistry();
		registry.register(make(COMPRESSORCONTAINER, new MenuType<>(CompressorMenu::new)));
		registry.register(make(DNACLEANERCONTAINER, new MenuType<>(DNACleanerMenu::new)));
		registry.register(make(DNACOMBINERCONTAINER, new MenuType<>(DNACombinerMenu::new)));
		registry.register(make(DNAEXTRACTORCONTAINER, new MenuType<>(DNAExtractorMenu::new)));
		registry.register(make(DNAREMOVERCONTAINER, new MenuType<>(DNARemoverMenu::new)));
		registry.register(make(ENERGYSTORAGECONTAINER, new MenuType<>(EnergyStorageMenu::new)));
		registry.register(make(IDENTIFIERCONTAINER, new MenuType<>(IdentifierMenu::new)));
		registry.register(make(INFUSERCONTAINER, new MenuType<>(InfuserMenu::new)));
		registry.register(make(UPGRADEABLEITEMCONTAINER, new MenuType<>(ItemUpgradeableContainer::new)));
		registry.register(make(MEGAFURNACECONTAINER, new MenuType<>(MegaFurnaceMenu::new)));
		registry.register(make(PLANTFARMCONTAINER, new MenuType<>(PlantFarmMenu::new)));
		registry.register(make(SEEDCONSTRUCTORCONTAINER, new MenuType<>(SeedConstructorMenu::new)));
		registry.register(make(SEEDQUEEZERCONTAINER, new MenuType<>(SeedSqueezerMenu::new)));
		registry.register(make(SOLARGENERATORCONTAINER, new MenuType<>(SolarGeneratorMenu::new)));
		registry.register(make(CHIPALYZERCONTAINER, new MenuType<>(ChipalyzerMenu::new)));
		registry.register(make(MACHINEBULBREPROCESSORCONTAINER, new MenuType<>(MachineBulbReprocessorMenu::new)));
		registry.register(make(TELEPORTERBLOCKCONTAINER, new MenuType<>(PlantTopiaTeleporterMenu::new)));
		registry.register(make(TELEPORTERITEMCONTAINER, new MenuType<>(TeleporterContainer::new)));
		registry.register(make(ENERGYSUPPLIERCONTAINER, new MenuType<>(EnergySupplierMenu::new)));
		registry.register(make(CROPAURAGENERATORCONTAINER, new MenuType<>(CropAuraGeneratorMenu::new)));
	}

	static <C extends Menu> MenuType<C> make(String registryName, MenuType<C> MenuType)
	{
		MenuType.setRegistryName(registryName);
		return MenuType;
	}
}
