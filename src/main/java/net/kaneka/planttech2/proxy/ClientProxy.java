package net.kaneka.planttech2.proxy;

import net.kaneka.planttech2.items.BaseItem;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.client.Minecraft;
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
	//fontRenderer = new CustomFontRenderer(Minecraft.getInstance().gameSettings, new ResourceLocation("planttech2:textures/font/font.png"), Minecraft.getInstance().textureManager, true);
	
	
//	for (BaseItem item : ModItems.SEEDS.values())
//	{
//	    Minecraft.getInstance().getItemColors().register(new CropSeedItem.ColorHandler(), item);
//	}
    }
}
