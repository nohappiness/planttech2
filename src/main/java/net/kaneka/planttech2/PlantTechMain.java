package net.kaneka.planttech2;

import net.kaneka.planttech2.configuration.PlantTech2Configuration;
import net.kaneka.planttech2.datapack.reloadlistener.ReloadListenerCropListEntryConfiguration;
import net.kaneka.planttech2.entities.neutral.TechGhoulEntity;
import net.kaneka.planttech2.events.AttachCapabilityEvents;
import net.kaneka.planttech2.events.ClientEvents;
import net.kaneka.planttech2.events.ForgeBusEvents;
import net.kaneka.planttech2.events.PlayerEvents;
import net.kaneka.planttech2.handlers.CapabilityHandler;
import net.kaneka.planttech2.handlers.LootTableHandler;
import net.kaneka.planttech2.items.BiomassContainerItem;
import net.kaneka.planttech2.items.PlantObtainerItem;
import net.kaneka.planttech2.items.upgradeable.MultitoolItem;
import net.kaneka.planttech2.items.upgradeable.RangedWeaponItem;
import net.kaneka.planttech2.librarys.CropList;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.proxy.ClientProxy;
import net.kaneka.planttech2.proxy.IProxy;
import net.kaneka.planttech2.proxy.ServerProxy;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.registries.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("planttech2")
public class PlantTechMain
{
	public static final String MODID = "planttech2";

	@SuppressWarnings("deprecation")
	public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public static PlantTechMain instance;
	
	public static CropList croplist = new CropList();
	

	@SuppressWarnings("deprecation")
	public PlantTechMain()
	{
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, PlantTech2Configuration.SERVER, "planttech2-server.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, PlantTech2Configuration.CLIENT, "planttech2-client.toml");
		
		PlantTech2Configuration.loadConfig(PlantTech2Configuration.CLIENT, FMLPaths.CONFIGDIR.get().resolve("planttech2-client.toml").toString());
		PlantTech2Configuration.loadConfig(PlantTech2Configuration.SERVER, FMLPaths.CONFIGDIR.get().resolve("planttech2-server.toml").toString());

		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			//FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEvents::textureStitchEvent);
		});

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, AttachCapabilityEvents::attachEntityCapability);
		MinecraftForge.EVENT_BUS.addGenericListener(TileEntity.class, AttachCapabilityEvents::attachTileEntityCapability);
		MinecraftForge.EVENT_BUS.addListener(this::onServerAboutToStarting);
		MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
		MinecraftForge.EVENT_BUS.addListener(PlayerEvents::playerConnect);
//		MinecraftForge.EVENT_BUS.addListener(AttachCapabilityEvents::attachItemStackCapability);
		MinecraftForge.EVENT_BUS.addListener(PlayerEvents::playerTicking);
		MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerClone);
		MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerChangedDimension);
		MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerRespawn);
		MinecraftForge.EVENT_BUS.addListener(PlayerEvents::onPlayerHurt);
		MinecraftForge.EVENT_BUS.addListener(ModCommands::onCommandRegister);
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
				MinecraftForge.EVENT_BUS.addListener(ClientEvents::onWorldStart);
				MinecraftForge.EVENT_BUS.addListener(ClientEvents::onFogRenderDensity);
				FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEvents::registerColorBlock);
				FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEvents::registerColorItem);
				MinecraftForge.EVENT_BUS.addListener(ClientEvents::onFogRenderColour);
		});
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
		CapabilityHandler.registerAll(); 
		PlantTech2PacketHandler.register();
		PlantTechMain.croplist.configurePlanttechEntries();
		LootTableHandler.register();
        DeferredWorkQueue.runLater(PlantTechMain::registerAllEntityAttributes);
	}

	private static void registerAllEntityAttributes()
    {
        GlobalEntityTypeAttributes.put((EntityType<? extends LivingEntity>) ModEntityTypes.TECHGHOULENTITY, TechGhoulEntity.getAttributes().create());
    }

    private void doClientStuff(final FMLClientSetupEvent event)
	{
		ModRenderer.registerEntityRenderer();
		ModScreens.registerGUI();
		for (Block block : ModBlocks.SPECIAL_RENDER_BLOCKS)
			RenderTypeLookup.setRenderLayer(block, RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.BIOMASSFLUIDBLOCK, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModFluids.BIOMASS, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModFluids.BIOMASS_FLOWING, RenderType.getTranslucent());
		DeferredWorkQueue.runLater(PlantTechMain::addAllItemModelsOverrides);
	}

	@OnlyIn(Dist.CLIENT)
	private static void addAllItemModelsOverrides()
	{
		ItemModelsProperties.func_239418_a_(
				ModItems.MULTITOOL, new ResourceLocation(PlantTechMain.MODID, "drilling"),
				(stack, world, entity) -> entity == null || !(stack.getItem() instanceof MultitoolItem) ? 0.0F : (entity.ticksExisted % 4) + 1
		);
		ItemModelsProperties.func_239418_a_(
				ModItems.PLANT_OBTAINER, new ResourceLocation(PlantTechMain.MODID, "filled"),
				(stack, world, entity) -> {
					if (!(stack.getItem() instanceof PlantObtainerItem)) return 0.0F;
					return PlantObtainerItem.isFilled(PlantObtainerItem.initTags(stack)) ? 1.0F : 0.0F; }
		);
		ItemModelsProperties.func_239418_a_(ModItems.PLANT_OBTAINER, new ResourceLocation(PlantTechMain.MODID, "filled"),
				(stack, world, entity) -> BiomassContainerItem.getFillLevelModel(stack));
		ItemModelsProperties.func_239418_a_(
				ModItems.CYBERBOW, new ResourceLocation(PlantTechMain.MODID, "pull"),
				(stack, world, entity) -> entity == null || !(entity.getActiveItemStack().getItem() instanceof RangedWeaponItem) ? 0.0F : (float) (stack.getUseDuration() - entity.getItemInUseCount()) / 20.0F
		);
		ItemModelsProperties.func_239418_a_(
				ModItems.CYBERBOW, new ResourceLocation(PlantTechMain.MODID, "pulling"),
				(stack, world, entity) -> entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);
	}
}
