package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.inventory.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fmllegacy.network.IContainerFactory;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Function;

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
		registry.register(makeBlock(COMPRESSORCONTAINER, CompressorMenu::new));
		registry.register(make(DNACLEANERCONTAINER, DNACleanerMenu::new));
		registry.register(makeBlock(DNACOMBINERCONTAINER, DNACombinerMenu::new));
		registry.register(makeBlock(DNAEXTRACTORCONTAINER, DNAExtractorMenu::new));
		registry.register(makeBlock(DNAREMOVERCONTAINER, DNARemoverMenu::new));
		registry.register(makeBlock(ENERGYSTORAGECONTAINER, EnergyStorageMenu::new));
		registry.register(makeBlock(IDENTIFIERCONTAINER, IdentifierMenu::new));
		registry.register(makeBlock(INFUSERCONTAINER, InfuserMenu::new));
		registry.register(makeItem(UPGRADEABLEITEMCONTAINER, ItemUpgradeableContainer::new));
		registry.register(makeBlock(MEGAFURNACECONTAINER, MegaFurnaceMenu::new));
		registry.register(makeBlock(PLANTFARMCONTAINER, PlantFarmMenu::new));
		registry.register(makeBlock(SEEDCONSTRUCTORCONTAINER, SeedConstructorMenu::new));
		registry.register(makeBlock(SEEDQUEEZERCONTAINER, SeedSqueezerMenu::new));
		registry.register(makeBlock(SOLARGENERATORCONTAINER, SolarGeneratorMenu::new));
		registry.register(makeBlock(CHIPALYZERCONTAINER, ChipalyzerMenu::new));
		registry.register(makeBlock(MACHINEBULBREPROCESSORCONTAINER, MachineBulbReprocessorMenu::new));
		registry.register(makeBlock(TELEPORTERBLOCKCONTAINER, PlantTopiaTeleporterMenu::new));
		registry.register(makeItem(TELEPORTERITEMCONTAINER, TeleporterContainer::new));
		registry.register(makeBlock(ENERGYSUPPLIERCONTAINER, EnergySupplierMenu::new));
		registry.register(makeBlock(CROPAURAGENERATORCONTAINER, CropAuraGeneratorMenu::new));
	}

//	static <M extends AbstractContainerMenu> MenuType<M> make(String registryName, MenuType<M> menuType)
//	{
//		menuType.setRegistryName(registryName);
//		return menuType;
//	}

	static <M extends AbstractContainerMenu> MenuType<M> makeItem(String registryName, PlantTechItemMenuFactory<M> factory)
	{
		return make(registryName, factory);
	}

	static <M extends AbstractContainerMenu> MenuType<M> makeBlock(String registryName, PlantTechMachineMenuFactory<M> factory)
	{
		return make(registryName, factory);
	}

	static <M extends AbstractContainerMenu> MenuType<M> make(String registryName, MenuType.MenuSupplier<M> factory)
	{
		MenuType<M> menuType = new MenuType<>(factory);
		menuType.setRegistryName(registryName);
		return menuType;
	}

	interface PlantTechMachineMenuFactory<M extends AbstractContainerMenu> extends IContainerFactory<M>
	{
		@Override
		default M create(int windowId, Inventory inv, FriendlyByteBuf data)
		{
			return create(windowId, inv, data.readBlockPos());
		}

		M create(int id, Inventory inventory, BlockPos te);
	}

	interface PlantTechItemMenuFactory<M extends AbstractContainerMenu> extends IContainerFactory<M>
	{
		@Override
		default M create(int windowId, Inventory inv, FriendlyByteBuf data)
		{
			return create(windowId, inv, data.readItem(), data.readInt());
		}

		M create(int id, Inventory inventory, ItemStack type, int slot);
	}
}
