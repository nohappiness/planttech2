package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid=PlantTechMain.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistry {
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        for(Supplier<? extends Block> block: ModBlocks.SPECIAL_RENDER_BLOCKS_CUTOUT) {
            ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.cutout());
        }
        for(Supplier<? extends Block> block: ModBlocks.SPECIAL_RENDER_BLOCKS_TRANSLUCENT){
            ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.translucent());
        }
    }
}
