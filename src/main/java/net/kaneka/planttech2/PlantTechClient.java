package net.kaneka.planttech2;

import net.kaneka.planttech2.gui.GuidePlantsScreen;
import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.kaneka.planttech2.items.BiomassContainerItem;
import net.kaneka.planttech2.items.GuideItem;
import net.kaneka.planttech2.items.PlantObtainerItem;
import net.kaneka.planttech2.items.upgradeable.MultitoolItem;
import net.kaneka.planttech2.items.upgradeable.RangedWeaponItem;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public class PlantTechClient
{
	public static final ResourceLocation DRILLING_PREDICATE = new ResourceLocation(PlantTechMain.MODID, "drilling");
	public static final ResourceLocation FILLED_PREDICATE = new ResourceLocation(PlantTechMain.MODID, "filled");
	public static final ResourceLocation PULL_PREDICATE = new ResourceLocation(PlantTechMain.MODID, "pull");
	public static final ResourceLocation PULLING_PREDICATE = new ResourceLocation(PlantTechMain.MODID, "pulling");

	public static void addAllItemModelsOverrides()
	{ 
		ItemModelsProperties.register(
				ModItems.MULTITOOL, DRILLING_PREDICATE,
				(stack, world, entity) -> entity == null || !(stack.getItem() instanceof MultitoolItem) ? 0.0F : (entity.tickCount % 4) + 1
		);
		ItemModelsProperties.register(
				ModItems.PLANT_OBTAINER, FILLED_PREDICATE,
				(stack, world, entity) -> {
					if (!(stack.getItem() instanceof PlantObtainerItem)) return 0.0F;
					return PlantObtainerItem.isFilled(PlantObtainerItem.initTags(stack)) ? 1.0F : 0.0F;
				}
		);
		ItemModelsProperties.register(ModItems.BIOMASSCONTAINER, FILLED_PREDICATE,
				(stack, world, entity) -> BiomassContainerItem.getFillLevelModel(stack));
		ItemModelsProperties.register(
				ModItems.CYBERBOW, PULL_PREDICATE,
				(stack, world, entity) -> entity == null || !(entity.getUseItem().getItem() instanceof RangedWeaponItem) ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F
		);
		ItemModelsProperties.register(
				ModItems.CYBERBOW, PULLING_PREDICATE,
				(stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
	}

	public static void openGuideScreen(GuideItem guide)
	{
		Screen screen = guide == ModItems.GUIDE_PLANTS ? new GuidePlantsScreen() : new GuideScreen();
		openScreen(screen);
	}

	public static void openScreen(Screen screen)
	{
		Minecraft.getInstance().setScreen(screen);
	}
}
