package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.dimensions.planttopia.biomes.PlantTopiaBaseBiome;
import net.kaneka.planttech2.entities.capabilities.player.IPlayerRenderRGB;
import net.kaneka.planttech2.entities.capabilities.player.PlayerRenderRGB;
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
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.storage.loot.conditions.WeatherCheck;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

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
		if (player == null || !player.isAlive() || player.getEntityWorld().getDimension().getType() != ModDimensions.getPlantTopiaDimensionType())
		{
			return;
		}
		Biome biome = player.getEntityWorld().getBiome(player.getPosition());
		if (!(biome instanceof PlantTopiaBaseBiome))
		{
			return;
		}
//		float biomeDencity = 0.003F;
		float biomeDencity = ((PlantTopiaBaseBiome) biome).getFogDensity();
		IPlayerRenderRGB capability = PlayerRenderRGB.getCap(player);
		if (biomeDencity != -1 && Math.abs(capability.getCurrentFogDensity() - biomeDencity) > 0.00001F)
		{
			event.setCanceled(true);
			capability.setCurrentFogDensity(capability.getCurrentFogDensity() + ((capability.getCurrentFogDensity() < biomeDencity) ? 0.00001F : -0.00001F));
			event.setDensity(capability.getCurrentFogDensity());
		}
		else if (Math.abs(capability.getCurrentFogDensity() - biomeDencity) < 0.00001F)
		{
			event.setCanceled(true);
			event.setDensity(biomeDencity);
		}
		/*Minecraft minecraft = Minecraft.getInstance();
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
		}*/
	}

	@SubscribeEvent
	public static void onFogRenderColour(EntityViewRenderEvent.FogColors event)
	{
		Minecraft minecraft = Minecraft.getInstance();
		ClientPlayerEntity player = minecraft.player;
		if (player == null || !player.isAlive())
		{
			return;
		}
		IPlayerRenderRGB capability = PlayerRenderRGB.getCap(player);
		Biome biome = player.getEntityWorld().getBiome(player.getPosition());
		float[] targetRGB = {event.getRed(), event.getGreen(), event.getBlue()};
		if (biome instanceof PlantTopiaBaseBiome && !Arrays.equals(((PlantTopiaBaseBiome) biome).getFogRGB(), new float[]{-1, -1, -1}))
		{
			targetRGB[0] = ((PlantTopiaBaseBiome) biome).getFogRed() / (float) 255;
			targetRGB[1] = ((PlantTopiaBaseBiome) biome).getFogGreen() / (float) 255;
			targetRGB[2] = ((PlantTopiaBaseBiome) biome).getFogBlue() / (float) 255;
		}
		if (Math.abs(capability.getCurrentRed() - targetRGB[0]) > 0.001F)
		{
			capability.changeCurrentRed((capability.getCurrentRed() < targetRGB[0]) ? 0.001F : -0.001F);
			event.setRed(capability.getCurrentRed());
		}
		else
		{
			event.setRed(targetRGB[0]);
		}
		if (Math.abs(capability.getCurrentGreen() - targetRGB[1]) > 0.001F)
		{
			capability.changeCurrentGreen((capability.getCurrentGreen() < targetRGB[1]) ? 0.001F : -0.001F);
			event.setGreen(capability.getCurrentGreen());
		}
		else
		{
			event.setGreen(targetRGB[1]);
		}
		if (Math.abs(capability.getCurrentBlue() - targetRGB[2]) > 0.001F)
		{
			capability.changeCurrentBlue((capability.getCurrentBlue() < targetRGB[2]) ? 0.001F : -0.001F);
			event.setBlue(capability.getCurrentBlue());
		}
		else
		{
			event.setBlue(targetRGB[2]);
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
