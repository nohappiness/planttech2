package net.kaneka.planttech2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.kaneka.planttech2.events.ClientEvents;
import net.kaneka.planttech2.handlers.GuiHandler;
import net.kaneka.planttech2.handlers.LootTableHandler;
import net.kaneka.planttech2.librarys.CropList;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.proxy.ClientProxy;
import net.kaneka.planttech2.proxy.IProxy;
import net.kaneka.planttech2.proxy.ServerProxy;
import net.kaneka.planttech2.recipes.ModRecipeSerializers;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.rendering.cable.ModelLoaderCable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
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
    
    public static final ItemGroup groupmain = new ItemGroup("planttech2_main")
    {
	@OnlyIn(Dist.CLIENT)
	public ItemStack createIcon()
	{
	    return new ItemStack(ModItems.WRENCH);
	}
    };
    
    public static final ItemGroup groupmachines = new ItemGroup("planttech2_machines")
    {
	@OnlyIn(Dist.CLIENT)
	public ItemStack createIcon()
	{
	    return new ItemStack(ModBlocks.SOLARGENERATOR);
	}
    };

    public static final ItemGroup groupseeds = new ItemGroup("planttech2_seeds")
    {
	@OnlyIn(Dist.CLIENT)
	public ItemStack createIcon()
	{
	    return new ItemStack(ModItems.SEEDS.get("diamond"));
	}
    };

    public static final ItemGroup groupparticles = new ItemGroup("planttech2_particles")
    {
	@OnlyIn(Dist.CLIENT)
	public ItemStack createIcon()
	{
	    return new ItemStack(ModItems.COLOR_PARTICLES);
	}
    };

    public static final ItemGroup groupToolsAndArmor = new ItemGroup("planttech2_toolsandarmor")
    {
	@OnlyIn(Dist.CLIENT)
	public ItemStack createIcon()
	{
	    return new ItemStack(ModItems.CHESTPLATE_KANEKIUM);
	}
    };
    
    public static CropList croplist = new CropList();

    public PlantTechMain()
    {
	
	DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> GuiHandler::openGui);
			MinecraftForge.EVENT_BUS.addListener(ClientEvents::registerColorBlock);
			MinecraftForge.EVENT_BUS.addListener(ClientEvents::registerColorItem);
	});

	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
	MinecraftForge.EVENT_BUS.register(this);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event)
    {
	// do something when the server starts
    }

    private void setup(final FMLCommonSetupEvent event)
    {
	ModRecipeSerializers.registerAll(); 
	PlantTech2PacketHandler.register();
	//NetworkRegistry.INSTANCE.registerGuiHandler(PlantTechMain.instance, new GuiHandler());
	PlantTechMain.croplist.configuratePlanttechEntries();
	//PlantTechMain.instance.recipeLists.initRecipeCompressor();
	LootTableHandler.register();
	
	//new RecipeJSONGenerator().createRecipes();
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
	
    }
    
    /*
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegistryForgeEvents
    {
	
	
	@SubscribeEvent
	public static void registerColorItem(ColorHandlerEvent.Item event)
	{
	    ModItems.registerItemColorHandler(event);
	}
	
	@SubscribeEvent
	public static void registerColorBlock(ColorHandlerEvent.Block event)
	{
	    ModBlocks.registerBlockColorHandler(event);
	}
    }
    */
}
