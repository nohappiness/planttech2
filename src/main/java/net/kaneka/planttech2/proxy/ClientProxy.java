package net.kaneka.planttech2.proxy;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.BlockCropBase;
import net.kaneka.planttech2.items.ItemBase;
import net.kaneka.planttech2.items.ItemCropSeed;
import net.kaneka.planttech2.items.ItemParticle;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.rendering.cable.ModelLoaderCable;
import net.kaneka.planttech2.utilities.CustomFontRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
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
