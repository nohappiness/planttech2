package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.ParticleItem;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlantTechMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusEventsClient
{
	@SubscribeEvent
	public static void registerColorItem(ColorHandlerEvent.Item event)
	{
		for (Item entry : ModItems.PARTICLES.values())
		{
			event.getItemColors().register(new ParticleItem.ColorHandler(), entry);
		}

		for (Item entry : ModItems.SEEDS.values())
		{
			event.getItemColors().register(new CropSeedItem.ColorHandler(), entry);
		}
	}

	@SubscribeEvent
	public static void registerColorBlock(ColorHandlerEvent.Block event)
	{
		for (CropBaseBlock block : ModBlocks.CROPS.values())
		{
			event.getBlockColors().register(new CropBaseBlock.ColorHandler(), block);
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
