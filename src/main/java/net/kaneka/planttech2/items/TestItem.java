package net.kaneka.planttech2.items;


import net.kaneka.planttech2.datagen.helper.JsonFileConverter;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestItem extends Item
{

	public TestItem()
	{
		super(new Item.Properties().tab(ModCreativeTabs.MAIN));

	}

	/*@Override
	public InteractionResultHolder<ItemStack> onItemRightClick(Level level, PlayerEntity playerIn, Hand handIn)
	{
		if (playerIn instanceof ServerPlayerEntity)
		{
			System.out.println(RadiationEffect.getCap((ServerPlayerEntity) playerIn).getLevel());
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}*/
	private BufferedWriter out;
	@Override
	public InteractionResult useOn(UseOnContext ctx)
	{
		//		System.out.println(ModDimensions.getPlantTopiaDimensionType());
		if (!ctx.getLevel().isClientSide)
		{
			JsonFileConverter.act();
			//https://planttech-2.fandom.com/wiki/Items
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileDescriptor.out), StandardCharsets.US_ASCII));
			List<Item> items = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
			items.sort(Comparator.comparing(item -> item.getDescription().getString()));
			items.stream().filter((item) -> item.getRegistryName().getNamespace().equals("planttech2") && !(item instanceof CropSeedItem || item instanceof ParticleItem || item instanceof BlockItem)).forEach((item) ->
			{
				try
				{
					this.out = out;
					write("-");
					write(item.getDescription().getString());
					write(item.getRegistryName().getPath());
					int size = item.getMaxStackSize();
					write((size == 64 ? "" : String.valueOf(size)));
					int durability = item.getMaxDamage();
					write((durability == 0 ? "" : String.valueOf(durability)));
					write(item.getCreativeTabs().stream().findFirst().get().getDisplayName().getString());
					out.flush();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			});
			//				|-
			//				|Crop Remover
			//				|cropremover
			//				|1
			//				|1024
			//				|"
		}
			/*
			CropEntry entry = PlantTechMain.getCropList().getByName("coal");
			System.out.println(entry);
			if (entry != null)
			{
				List<Supplier<Item>> seeds = entry.getSeeds();
				for (Supplier<Item> seed : seeds)
					System.out.println(seed.get());
			}
			System.out.println(Feature.STRUCTURES); 
			
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
		//TeleportationUtils.changeDimension(ctx.getWorld(), ctx.getPos(), ctx.getPlayer(), ModDimensions.getPlantTopiaDimensionType(), Blocks.DIRT);


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
		return super.useOn(ctx);
	}

	private void write(String text) throws IOException
	{
		out.write("|" + text);
		out.write("\n");
	}

	@Override
	public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_)
	{
		Component text = new TextComponent("Leave me alone!");
		p_77624_3_.add(text);
	}
}
