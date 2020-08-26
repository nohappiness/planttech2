package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.gui.GuidePlantsScreen;
import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ForgeBusEventsClient
{
    public static boolean hasSendUpdateAvailable = false;

    @SubscribeEvent
    public static void onTextInsert(ClientChatEvent event)
    {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null)
            return;
        int screen = 0;
        switch (event.getOriginalMessage())
        {
            case "/pt2 guide overview":
                screen = 1;
                break;
            case "/pt2 guide plant":
                screen = 2;
                break;
        }
        if (screen != 0)
        {
            player.getPersistentData().putInt("planttech2_screen_delay", screen);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null)
        {
            CompoundNBT data = player.getPersistentData();
            if (data.contains("planttech2_screen_delay"))
            {
                Screen screen = null;
                switch (data.getInt("planttech2_screen_delay"))
                {
                    case 1:
                        screen = new GuideScreen();
                        break;
                    case 2:
                        screen = new GuidePlantsScreen();
                        break;
                }
                if (screen != null)
                {
                    Minecraft.getInstance().displayGuiScreen(screen);
                    data.putInt("planttech2_screen_delay", 0);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWorldStart(EntityJoinWorldEvent evt)
    {
        VersionChecker.CheckResult res = VersionChecker.getResult(ModList.get().getModContainerById(PlantTechMain.MODID).get().getModInfo());
        if (evt.getEntity() instanceof ClientPlayerEntity && res.status == VersionChecker.Status.OUTDATED && !hasSendUpdateAvailable)
        {
            hasSendUpdateAvailable = true;

            TextComponent info = new TranslationTextComponent("planttech2.update.available");
            TextComponent link = new TranslationTextComponent("planttech2.update.click");
            link.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/planttech-2/files"))
                    .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("planttech2.update.tooltip")))
                    .setFormatting(TextFormatting.BLUE)
                    .setUnderlined(true);
            evt.getEntity().sendMessage(info.append(link), evt.getEntity().getUniqueID());
            evt.getEntity().sendMessage(info, evt.getEntity().getUniqueID());
//            TODO
        }
    }

//    @SubscribeEvent
//    public static void onFogRenderDensity(EntityViewRenderEvent.FogDensity event)
//    {
//		/*
//		Minecraft minecraft = Minecraft.getInstance();
//		ClientPlayerEntity player = minecraft.player;
//		if (player == null || !player.isAlive() || player.getEntityWorld().getDimension().getType() != ModDimensions.getPlantTopiaDimensionType())
//		{
//			return;
//		}
//		Biome biome = player.getEntityWorld().getBiome(player.getPosition());
//		if (!(biome instanceof PlantTopiaBaseBiome))
//		{
//			return;
//		}
////		float biomeDencity = 0.003F;
//		float biomeDencity = ((PlantTopiaBaseBiome) biome).getFogDensity();
//		IPlayerRenderRGB capability = PlayerRenderRGB.getCap(player);
//		if (biomeDencity != -1 && Math.abs(capability.getCurrentFogDensity() - biomeDencity) > 0.00001F)
//		{
//			event.setCanceled(true);
//			capability.setCurrentFogDensity(capability.getCurrentFogDensity() + ((capability.getCurrentFogDensity() < biomeDencity) ? 0.00001F : -0.00001F));
//			event.setDensity(capability.getCurrentFogDensity());
//		}
//		else if (Math.abs(capability.getCurrentFogDensity() - biomeDencity) < 0.00001F)
//		{
//			event.setCanceled(true);
//			event.setDensity(biomeDencity);
//		}
//		Minecraft minecraft = Minecraft.getInstance();
//		ClientPlayerEntity player = minecraft.player;
//		if (player == null || player.getEntityWorld().getDimension().getType() != ModDimensions.getPlantTopiaDimensionType())
//		{
//			return;
//		}
//		Biome biome = player.getEntityWorld().getBiome(player.getPosition());
//		if (biome instanceof PlantTopiaBaseBiome)
//		{
//			float dencity = ((PlantTopiaBaseBiome) biome).getFogDensity();
//			if (dencity != -1)
//			{
//				event.setCanceled(true);
//				event.setDensity(dencity);
//			}
//		}*/
//    }

//    @SubscribeEvent
//    public static void onFogRenderColour(EntityViewRenderEvent.FogColors event)
//    {
//		/*
//		Minecraft minecraft = Minecraft.getInstance();
//		ClientPlayerEntity player = minecraft.player;
//		if (player == null || !player.isAlive())
//		{
//			return;
//		}
//		IPlayerRenderRGB capability = PlayerRenderRGB.getCap(player);
//		Biome biome = player.getEntityWorld().getBiome(player.getPosition());
//		float[] targetRGB = {event.getRed(), event.getGreen(), event.getBlue()};
//		if (biome instanceof PlantTopiaBaseBiome && !Arrays.equals(((PlantTopiaBaseBiome) biome).getFogRGB(), new float[]{-1, -1, -1}))
//		{
//			targetRGB[0] = ((PlantTopiaBaseBiome) biome).getFogRed() / (float) 255;
//			targetRGB[1] = ((PlantTopiaBaseBiome) biome).getFogGreen() / (float) 255;
//			targetRGB[2] = ((PlantTopiaBaseBiome) biome).getFogBlue() / (float) 255;
//		}
//		if (Math.abs(capability.getCurrentRed() - targetRGB[0]) > 0.001F)
//		{
//			capability.changeCurrentRed((capability.getCurrentRed() < targetRGB[0]) ? 0.001F : -0.001F);
//			event.setRed(capability.getCurrentRed());
//		}
//		else
//		{
//			event.setRed(targetRGB[0]);
//		}
//		if (Math.abs(capability.getCurrentGreen() - targetRGB[1]) > 0.001F)
//		{
//			capability.changeCurrentGreen((capability.getCurrentGreen() < targetRGB[1]) ? 0.001F : -0.001F);
//			event.setGreen(capability.getCurrentGreen());
//		}
//		else
//		{
//			event.setGreen(targetRGB[1]);
//		}
//		if (Math.abs(capability.getCurrentBlue() - targetRGB[2]) > 0.001F)
//		{
//			capability.changeCurrentBlue((capability.getCurrentBlue() < targetRGB[2]) ? 0.001F : -0.001F);
//			event.setBlue(capability.getCurrentBlue());
//		}
//		else
//		{
//			event.setBlue(targetRGB[2]);
//		}
//		*/
//    }

}
