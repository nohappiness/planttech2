package net.kaneka.planttech2.events;

import net.kaneka.planttech2.gui.GuidePlantsScreen;
import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeBusEventsClient
{
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
}
