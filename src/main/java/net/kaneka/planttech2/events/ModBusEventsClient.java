package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechClient;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.blocks.Hedge;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.ParticleItem;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModRenderer;
import net.kaneka.planttech2.registries.ModScreens;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PlantTechMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusEventsClient
{
	@SubscribeEvent
	static void clientSetup(final FMLClientSetupEvent event)
	{
		event.enqueueWork(PlantTechClient::addAllItemModelsOverrides);
		ModRenderer.registerEntityRenderer();
		ModScreens.registerGUI();
		//TODO
		/*
		for (Map.Entry<Supplier<? extends Block>, RenderType> entry : ModBlocks.SPECIAL_RENDER_BLOCKS.entrySet())
			RenderTypeLookup.setRenderLayer(entry.getKey().get(), entry.getValue());
		RenderTypeLookup.setRenderLayer(ModBlocks.BIOMASSFLUIDBLOCK, RenderType.translucent());
		RenderTypeLookup.setRenderLayer(ModFluids.BIOMASS, RenderType.translucent());
		RenderTypeLookup.setRenderLayer(ModFluids.BIOMASS_FLOWING, RenderType.translucent());

		 */
	}

	@SubscribeEvent
	public static void registerColorItem(ColorHandlerEvent.Item event)
	{
		for (Item entry : ModItems.PARTICLES.values())
			event.getItemColors().register(new ParticleItem.ColorHandler(), entry);
		for (Item entry : ModItems.SEEDS.values())
			event.getItemColors().register(new CropSeedItem.ColorHandler(), entry);
		for(Hedge block: ModBlocks.HEDGE_BLOCKS)
			event.getItemColors().register(new Hedge.ColorHandlerItem(block.getLeaves()), block); 
	}

	@SubscribeEvent
	public static void registerColorBlock(ColorHandlerEvent.Block event)
	{
		for (CropBaseBlock block : ModBlocks.CROPS.values())
			event.getBlockColors().register(new CropBaseBlock.ColorHandler(), block);
		for(Hedge block: ModBlocks.HEDGE_BLOCKS)
			event.getBlockColors().register(new Hedge.ColorHandler(block.getLeaves(), block.getSoil()), block);
	}
}
