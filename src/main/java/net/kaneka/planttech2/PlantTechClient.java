package net.kaneka.planttech2;

import net.kaneka.planttech2.gui.GuidePlantsScreen;
import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.kaneka.planttech2.items.BiomassContainerItem;
import net.kaneka.planttech2.items.GuideItem;
import net.kaneka.planttech2.items.PlantObtainerItem;
import net.kaneka.planttech2.items.upgradeable.MultitoolItem;
import net.kaneka.planttech2.items.upgradeable.RangedWeaponItem;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModFluids;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModRenderer;
import net.kaneka.planttech2.registries.ModScreens;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = PlantTechMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PlantTechClient
{
	@SubscribeEvent
	static void clientSetup(final FMLClientSetupEvent event)
	{
		DeferredWorkQueue.runLater(() ->
		{
			ModRenderer.registerEntityRenderer();
			ModScreens.registerGUI();
			for (Supplier<? extends Block> block : ModBlocks.SPECIAL_RENDER_BLOCKS)
				RenderTypeLookup.setRenderLayer(block.get(), RenderType.getCutout());
			RenderTypeLookup.setRenderLayer(ModBlocks.BIOMASSFLUIDBLOCK, RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(ModFluids.BIOMASS, RenderType.getTranslucent());
			RenderTypeLookup.setRenderLayer(ModFluids.BIOMASS_FLOWING, RenderType.getTranslucent());
			addAllItemModelsOverrides();
		});
	}

	private static void addAllItemModelsOverrides()
	{
		ItemModelsProperties.registerProperty(
				ModItems.MULTITOOL, new ResourceLocation(PlantTechMain.MODID, "drilling"),
				(stack, world, entity) -> entity == null || !(stack.getItem() instanceof MultitoolItem) ? 0.0F : (entity.ticksExisted % 4) + 1
		);
		ItemModelsProperties.registerProperty(
				ModItems.PLANT_OBTAINER, new ResourceLocation(PlantTechMain.MODID, "filled"),
				(stack, world, entity) -> {
					if (!(stack.getItem() instanceof PlantObtainerItem)) return 0.0F;
					return PlantObtainerItem.isFilled(PlantObtainerItem.initTags(stack)) ? 1.0F : 0.0F;
				}
		);
		ItemModelsProperties.registerProperty(ModItems.PLANT_OBTAINER, new ResourceLocation(PlantTechMain.MODID, "filled"),
				(stack, world, entity) -> BiomassContainerItem.getFillLevelModel(stack));
		ItemModelsProperties.registerProperty(
				ModItems.CYBERBOW, new ResourceLocation(PlantTechMain.MODID, "pull"),
				(stack, world, entity) -> entity == null || !(entity.getActiveItemStack().getItem() instanceof RangedWeaponItem) ? 0.0F : (float) (stack.getUseDuration() - entity.getItemInUseCount()) / 20.0F
		);
		ItemModelsProperties.registerProperty(
				ModItems.CYBERBOW, new ResourceLocation(PlantTechMain.MODID, "pulling"),
				(stack, world, entity) -> entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);
	}

	public static void openGuideScreen(GuideItem guide)
	{
		Screen screen = guide == ModItems.GUIDE_PLANTS ? new GuidePlantsScreen() : new GuideScreen();
		openScreen(screen);
	}

	public static void openScreen(Screen screen)
	{
		Minecraft.getInstance().displayGuiScreen(screen);
	}
}
