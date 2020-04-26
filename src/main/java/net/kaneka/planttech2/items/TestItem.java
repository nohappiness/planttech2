package net.kaneka.planttech2.items;


import java.util.Map.Entry;

import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.dimensions.TeleportationUtils;
import net.kaneka.planttech2.entities.capabilities.player.IRadiationEffect;
import net.kaneka.planttech2.entities.capabilities.player.RadiationEffect;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.ITechVillagerTrust;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.TechVillagerTrust;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.packets.SyncRadiationLevelMessage;
import net.kaneka.planttech2.registries.ModDimensions;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.CallbackI;

public class TestItem extends BaseItem
{

	public TestItem()
	{
		super("testitem", new Item.Properties().group(ModCreativeTabs.groupmain));

	}

	/*@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		if (playerIn instanceof ServerPlayerEntity)
		{
			System.out.println(RadiationEffect.getCap((ServerPlayerEntity) playerIn).getLevel());
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}*/

	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
//		System.out.println(ModDimensions.getPlantTopiaDimensionType());
		if(!ctx.getWorld().isRemote)
		{
			
			/*
			System.out.println(" ");
    		System.out.println("Blocks: "); 
    		for(Entry<ResourceLocation, Block> list: ForgeRegistries.BLOCKS.getEntries())
    		{
    			if(list.getKey().getNamespace().contains("planttech2") && !(list.getValue() instanceof CropBaseBlock))
    			{
    				System.out.println(list.getValue().getNameTextComponent().getFormattedText()); 
    			}
    		}
    		
    		System.out.println(" ");
    		System.out.println("Items: "); 
    		for(Entry<ResourceLocation, Item> list: ForgeRegistries.ITEMS.getEntries())
    		{
    			if(list.getKey().getNamespace().contains("planttech2") && !(list.getValue() instanceof CropSeedItem) && !(list.getValue() instanceof ParticleItem))
    			{
    				System.out.println(list.getValue().getDisplayName(new ItemStack(list.getValue())).getFormattedText()); 
    			}
    		}
		
		*/
//			TeleportationUtils.changeDimension(ctx.getWorld(), ctx.getPos(), ctx.getPlayer(), ModDimensions.getPlantTopiaDimensionType(), Blocks.DIRT);
		}
		
		//Minecraft.getInstance().displayGuiScreen(new GuideScreen());
		/*
		if(!ctx.getWorld().isRemote)
		{
			new JsonGenerator().create();
		}
		
		if(!ctx.getWorld().isRemote)
		{
		for(Enchantment ench: ForgeRegistries.ENCHANTMENTS)
		{
			System.out.println("\"item.planttech2."+ ench.getName().replace("enchantment.minecraft.", "") + "_chip\": \""+ench.getName().replace("enchantment.minecraft.", "")+" Chip\""); 
		}
		}
		*/
		// place(Sets.newHashSet(), ctx.getWorld(), new Random(), ctx.getPos().up(),
		// MutableBoundingBox.getNewBoundingBox());
//		 System.out.println(ModDimensionPlantTopia.getDimensionType());
		return super.onItemUse(ctx);
	}
}