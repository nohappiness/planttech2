package net.kaneka.planttech2.events;

import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents
{
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
}
