package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechClient;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.blocks.Hedge;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.ParticleItem;
import net.kaneka.planttech2.registries.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = PlantTechMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBusEventsClient
{
	@SubscribeEvent
	static void clientSetup(final FMLClientSetupEvent event)
	{
		event.enqueueWork(PlantTechClient::addAllItemModelsOverrides);
		ModRenderer.registerEntityRenderer();
		ModScreens.registerGUI();
		for (Supplier<? extends Block> block : ModBlocks.SPECIAL_RENDER_BLOCKS)
			RenderTypeLookup.setRenderLayer(block.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BIOMASSFLUIDBLOCK, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModFluids.BIOMASS, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModFluids.BIOMASS_FLOWING, RenderType.getTranslucent());
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
			event.getBlockColors().register(new Hedge.ColorHandler(block.getLeaves()), block);
	}
}
