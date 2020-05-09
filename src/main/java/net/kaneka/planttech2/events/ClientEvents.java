package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.dimensions.planttopia.biomes.PlantTopiaBaseBiome;
import net.kaneka.planttech2.registries.ModBiomes;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModDimensions;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents
{
	public static boolean hasSendUpdateAvailable = false; 
	
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
	
	@SubscribeEvent
	public static void onWorldStart(EntityJoinWorldEvent evt)
	{
		VersionChecker.CheckResult res = VersionChecker.getResult(ModList.get().getModContainerById(PlantTechMain.MODID).get().getModInfo());
		if (evt.getEntity() instanceof ClientPlayerEntity && res.status == VersionChecker.Status.OUTDATED && !hasSendUpdateAvailable)
		{
			hasSendUpdateAvailable = true; 
			
			ITextComponent info = new TranslationTextComponent("planttech2.update.available");
			ITextComponent link = new TranslationTextComponent("planttech2.update.click");
			link.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/planttech-2/files"))
							.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("planttech2.update.tooltip")))
							.setColor(TextFormatting.BLUE)
							.setUnderlined(true);
			evt.getEntity().sendMessage(info.appendSibling(link));
		}
	}

	@SubscribeEvent
	public static void onFogRenderDensity(EntityViewRenderEvent.FogDensity event)
	{
		Minecraft minecraft = Minecraft.getInstance();
		ClientPlayerEntity player = minecraft.player;
		if (player == null || player.getEntityWorld().getDimension().getType() != ModDimensions.getPlantTopiaDimensionType())
		{
			return;
		}
		Biome biome = player.getEntityWorld().getBiome(player.getPosition());
		if (biome instanceof PlantTopiaBaseBiome)
		{
			float dencity = ((PlantTopiaBaseBiome) biome).getFogDensity();
			if (dencity != -1)
			{
				event.setCanceled(true);
				event.setDensity(dencity);
			}
		}
	}

	@SubscribeEvent
	public static void onFogRenderColour(EntityViewRenderEvent.FogColors event)
	{
		Minecraft minecraft = Minecraft.getInstance();
		ClientPlayerEntity player = minecraft.player;
		if (player == null || player.getEntityWorld().getDimension().getType() != ModDimensions.getPlantTopiaDimensionType())
		{
			return;
		}
		Biome biome = player.getEntityWorld().getBiome(player.getPosition());
		if (biome instanceof PlantTopiaBaseBiome)
		{
			if (!((PlantTopiaBaseBiome) biome).getFogRGB().isEmpty())
			{
				event.setRed(((PlantTopiaBaseBiome) biome).getFogRed() / (float) 255);
				event.setGreen(((PlantTopiaBaseBiome) biome).getFogGreen() / (float) 255);
				event.setBlue(((PlantTopiaBaseBiome) biome).getFogBlue() / (float) 255);
			}
//			System.out.println("rgb: " + event.getRed() + " " + event.getGreen() + " " + event.getBlue() + " ");
		}
	}
	
	/*
	@SubscribeEvent
	public static void textureStitchEvent(TextureStitchEvent.Pre event) 
	{
		if(event.getMap().getBasePath() == "textures")
		{
    	    event.addSprite(BiomassFluid.ATTRIBUTES.getStillTexture());
    	    event.addSprite(BiomassFluid.ATTRIBUTES.getFlowingTexture());
		}
	}
	*/
}
