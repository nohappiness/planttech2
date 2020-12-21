package net.kaneka.planttech2;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.gui.GuidePlantsScreen;
import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.kaneka.planttech2.items.BiomassContainerItem;
import net.kaneka.planttech2.items.GuideItem;
import net.kaneka.planttech2.items.PlantObtainerItem;
import net.kaneka.planttech2.items.upgradeable.MultitoolItem;
import net.kaneka.planttech2.items.upgradeable.RangedWeaponItem;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;

public class PlantTechClient
{
	public static void addAllItemModelsOverrides()
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
