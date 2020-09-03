package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.datapack.dataprovider.Recipes;
import net.kaneka.planttech2.datapack.reloadlistener.ReloadListenerCropListEntryConfiguration;
import net.kaneka.planttech2.recipes.ModRecipeSerializers;
import net.kaneka.planttech2.registries.*;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

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
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event)
	{
		ModSounds.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Effect> event)
	{
		ModEffects.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event)
	{
		ModEntityTypes.registerAll(event.getRegistry());
	}

	/*
	@SubscribeEvent
	public static void registerBiomes(final RegistryEvent.Register<Biome> event)
	{
		ModBiomes.registerBiomes(event.getRegistry());
	}
	
	@SubscribeEvent
	public static void registerDimensions(final RegistryEvent.Register<ModDimension> event)
	{
		ModDimensions.initDimensions(event.getRegistry());
	}

	@SubscribeEvent
	public void onRegisteredDimension(RegisterDimensionsEvent event)
	{
		ModDimensions.registerAll();
	}
	
	@SubscribeEvent
	public void registerPlacements(RegistryEvent.Register<Placement<?>> event)
	{
		ModPlacements.registerDimensions(event.getRegistry());
	}

	
	@SubscribeEvent
	public static void registerFeatures(RegistryEvent.Register<Feature<?>> event)
	{
		ModStructures.registerAll(event);
	}

	*/
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();
		gen.addProvider(new Recipes(gen));
	}

	@SubscribeEvent
	public static void registerFluids(RegistryEvent.Register<Fluid> event)
	{
		ModFluids.register(event.getRegistry());
	}
}
