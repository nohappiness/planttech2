package net.kaneka.planttech2;

import net.kaneka.planttech2.configuration.PlantTech2Configuration;
import net.kaneka.planttech2.crops.CropList;
import net.kaneka.planttech2.handlers.CapabilityHandler;
import net.kaneka.planttech2.handlers.LootTableHandler;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.registries.ModBiomes;
import net.kaneka.planttech2.registries.ModCommands;
import net.kaneka.planttech2.registries.ModDimensions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlserverevents.FMLServerAboutToStartEvent;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PlantTechMain.MODID)
public class PlantTechMain
{
    public static final String MODID = "planttech2";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    private static final CropList CROP_LIST = CropList.addDefaultEntries(new CropList());
    // TODO: find a better place for this

    public PlantTechMain()
    {
        //TODO
        //ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, PlantTech2Configuration.SERVER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, PlantTech2Configuration.CLIENT);
        
        
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::onServerAboutToStarting);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        //		MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerClone);
        //		MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerChangedDimension);
        //		MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerRespawn);
        //		MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerHurt);
        MinecraftForge.EVENT_BUS.addListener(ModCommands::onCommandRegister);
        
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBiomes.BIOMES.register(modEventBus);
    }

    private void onServerAboutToStarting(FMLServerAboutToStartEvent event)
    {
    }

    private void onServerStarting(final FMLServerStartingEvent event)
    {
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        new ModRecipeTypes();
        //new ModStructurePieceTypes();
        ModDimensions.initDimensions();
        CapabilityHandler.registerAll();
        PlantTech2PacketHandler.register();
        LootTableHandler.register();
        registerAllEntityAttributes();
    }

    private static void registerAllEntityAttributes()
    {
        //GlobalEntityTypeAttributes.put(ModEntityTypes.TECHGHOULENTITY, TechGhoulEntity.getAttributes().build());
    }

    public static CropList getCropList() {
        return CROP_LIST;
    }
}
