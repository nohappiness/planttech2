package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.common.Mod;

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
}
