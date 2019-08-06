package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.recipes.ModRecipeSerializers;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.registries.ModDimensions;
import net.kaneka.planttech2.registries.ModEntityTypes;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModStructures;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents
{
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		ModItems.register(event.getRegistry());
		ModBlocks.registerItemBlocks(event.getRegistry());
		// new DropsJSONGenerator().defaultValues();
		//new JsonGenerator().create();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		PlantTechMain.croplist.addPlanttechEntries();
		ModBlocks.register(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event)
	{
		ModTileEntities.register(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event)
	{
		ModContainers.registerAll(event);
	}

	@SubscribeEvent
	public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event)
	{
		ModRecipeSerializers.registerAll(event);
	}
	
	@SubscribeEvent
	public static void registerDimensions(RegistryEvent.Register<ModDimension> event)
	{
		ModDimensions.registerAll(event);
	}
	
	@SubscribeEvent
	public static void registerFeatures(RegistryEvent.Register<Feature<?>> event)
	{
		ModStructures.registerAll(event);
	}
	
	@SubscribeEvent
	public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event)
	{
		ModEntityTypes.registerAll(event); 
	}
}
