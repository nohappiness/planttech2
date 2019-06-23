package net.kaneka.planttech2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.kaneka.planttech2.datapack.reloadlistener.ReloadListenerCropListEntryConfiguration;
import net.kaneka.planttech2.events.ClientEvents;
import net.kaneka.planttech2.events.PlayerEvents;
import net.kaneka.planttech2.handlers.LootTableHandler;
import net.kaneka.planttech2.librarys.CropList;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.proxy.ClientProxy;
import net.kaneka.planttech2.proxy.IProxy;
import net.kaneka.planttech2.proxy.ServerProxy;
import net.kaneka.planttech2.recipes.ModRecipeSerializers;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModScreens;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.rendering.cable.ModelLoaderCable;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("planttech2")
public class PlantTechMain
{
	public static final String MODID = "planttech2";

	public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public static PlantTechMain instance;

	public static CropList croplist = new CropList();

	public PlantTechMain()
	{

		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			// ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, ()
			// -> GuiHandler::openGui);
			FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEvents::registerColorBlock);
			FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEvents::registerColorItem);
		});

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.addListener(PlayerEvents::playerConnect);
		MinecraftForge.EVENT_BUS.addListener(this::modelBake);
	}

	private void onServerStarting(FMLServerStartingEvent event)
	{
		event.getServer().getResourceManager().addReloadListener(new ReloadListenerCropListEntryConfiguration());
	}

	private void setup(final FMLCommonSetupEvent event)
	{
		new ModRecipeTypes();  
		PlantTech2PacketHandler.register();
		PlantTechMain.croplist.configuratePlanttechEntries();
		LootTableHandler.register();
		ModScreens.registerGUI();
	}

	private void doClientStuff(final FMLClientSetupEvent event)
	{
		ModelLoaderRegistry.registerLoader(new ModelLoaderCable());
	}

	private void enqueueIMC(final InterModEnqueueEvent event)
	{
		// some example code to dispatch IMC to another mod
	}

	private void processIMC(final InterModProcessEvent event)
	{
		// some example code to receive and process InterModComms from other mods
	}

	// You can use EventBusSubscriber to automatically subscribe events on the
	// contained class (this is subscribing to the MOD event bus
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event)
		{
			ModItems.register(event.getRegistry());
			ModBlocks.registerItemBlocks(event.getRegistry());
			// new DropsJSONGenerator().defaultValues();
		}

		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event)
		{
			PlantTechMain.croplist.addPlanttechEntries();
			ModBlocks.register(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event)
		{
			ModTileEntities.register(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event)
		{
			ModContainers.registerAll(event);
		}

		@SubscribeEvent
		public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event)
		{
			ModRecipeSerializers.registerAll(event);
		}

	}

	public void modelBake(ModelBakeEvent event)
	{
		// event.getModelRegistry().put(new ModelResourceLocation("planttech2:cable"),
		// new CompositeModel());
	}
}
