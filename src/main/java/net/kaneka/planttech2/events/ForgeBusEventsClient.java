package net.kaneka.planttech2.events;

import net.kaneka.planttech2.gui.GuidePlantsScreen;
import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ForgeBusEventsClient
{
    public static boolean hasSendUpdateAvailable = false;

    @SubscribeEvent
    public static void onTextInsert(ClientChatEvent event)
    {
        Player player = Minecraft.getInstance().player;
        if (player == null)
            return;
        int screen = switch (event.getOriginalMessage())
                {
                    case "/pt2 guide overview" -> 1;
                    case "/pt2 guide plant" -> 2;
                    default -> 0;
                };
        if (screen != 0)
        {
            player.getPersistentData().putInt("planttech2_screen_delay", screen);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = Minecraft.getInstance().player;
        if (event.phase == TickEvent.Phase.START)
            return;
        if (player != null)
        {
            CompoundTag data = player.getPersistentData();
            if (data.contains("planttech2_screen_delay")) {
                Screen screen = switch (data.getInt("planttech2_screen_delay"))
                        {
                            case 1 -> new GuideScreen();
                            case 2 -> new GuidePlantsScreen();
                            default -> null;
                        };
                if (screen != null)
                {
                    Minecraft.getInstance().setScreen(screen);
                    data.putInt("planttech2_screen_delay", 0);
                }
            }
        }

        /*
        // Upon world create
        if (minecraft.screen instanceof ConfirmScreen screen)
        {
            if (screen.getTitle().getString().equals(new TranslatableComponent("selectWorld.backupQuestion.experimental").getString()))
            {
                try
                {
                    CALLBACK.setAccessible(true);
                    ((BooleanConsumer) CALLBACK.get(screen)).accept(true);
                }
                catch (Exception e)
                {
                    PlantTechMain.LOGGER.error("failed to skip create confirm screen");
                    e.printStackTrace();
                }
            }
        }

        // Upon load world
        else if (minecraft.screen instanceof BackupConfirmScreen screen)
        {
            if (screen.getTitle().getString().equals(new TranslatableComponent("selectWorld.backupQuestion.experimental").getString()))
            {
                try
                {
                    LISTENER.setAccessible(true);
                    ((BackupConfirmScreen.Listener) LISTENER.get(screen)).proceed(true, false);
                }
                catch (Exception e)
                {
                    PlantTechMain.LOGGER.error("failed to skip backup confirm screen");
                    e.printStackTrace();
                }
            }
        }
    }
    */
    }
    /*

    private static final Field CALLBACK = ObfuscationReflectionHelper.findField(ConfirmScreen.class, "callback");
    private static final Field LISTENER = ObfuscationReflectionHelper.findField(BackupConfirmScreen.class, "listener");

    @SubscribeEvent
    public static void onWorldStart(EntityJoinWorldEvent evt)
    {
        VersionChecker.CheckResult res = VersionChecker.getResult(ModList.get().getModContainerById(PlantTechMain.MODID).get().getModInfo());
        if (evt.getEntity() instanceof LocalPlayer lp && res.status() == VersionChecker.Status.OUTDATED && !hasSendUpdateAvailable)
        {
            Component info = new TranslatableComponent("planttech2.update.available");
            Component link = new TranslatableComponent("planttech2.update.click");
            link.toFlatList(link.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/planttech-2/files"))
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("planttech2.update.tooltip")))
                    .withColor(TextColor.parseColor("BLUE"))
                    .setUnderlined(true));
            lp.sendMessage(info, lp.getUUID());
            lp.sendMessage(link, lp.getUUID());
        }
        hasSendUpdateAvailable = true;
    }
    */
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
