package net.kaneka.planttech2.proxy;

import net.kaneka.planttech2.items.ItemBase;
import net.kaneka.planttech2.items.ItemCropSeed;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.rendering.cable.ModelLoaderCable;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientProxy implements IProxy
{

    //public static CustomFontRenderer fontRenderer;

    @Override
    public void setup(FMLCommonSetupEvent event)
    {
	ModelLoaderRegistry.registerLoader(new ModelLoaderCable());
	//fontRenderer = new CustomFontRenderer(Minecraft.getInstance().gameSettings, new ResourceLocation("planttech2:textures/font/font.png"), Minecraft.getInstance().textureManager, true);
	
	
	for (ItemBase item : ModItems.SEEDS.values())
	{
	    Minecraft.getInstance().getItemColors().register(new ItemCropSeed.ColorHandler(), item);
	}
    }
}
