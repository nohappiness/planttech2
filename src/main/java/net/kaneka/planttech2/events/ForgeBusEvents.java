package net.kaneka.planttech2.events;

import net.kaneka.planttech2.datapack.reloadlistener.ReloadListenerCropListEntryConfiguration;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEvents
{
    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event)
    {
        event.addListener(new ReloadListenerCropListEntryConfiguration());
    }
}
