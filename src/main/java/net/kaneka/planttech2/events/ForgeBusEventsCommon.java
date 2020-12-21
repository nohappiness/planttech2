package net.kaneka.planttech2.events;

import net.kaneka.planttech2.crops.CropListReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ForgeBusEventsCommon
{
    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event)
    {
        event.addListener(new CropListReloadListener());
    }
}
