package net.kaneka.planttech2.events;

import net.kaneka.planttech2.recipes.ModRecipeSerializers;
import net.kaneka.planttech2.registries.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
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
		ModBlocks.register(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerTileEntities(RegistryEvent.Register<BlockEntityType<?>> event)
	{
		ModTileEntities.register(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerContainer(RegistryEvent.Register<MenuType<?>> event)
	{
		ModContainers.registerAll(event);
	}

	@SubscribeEvent
	public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event)
	{
		ModRecipeSerializers.registerAll(event);
	}

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event)
	{
		ModSounds.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<MobEffect> event)
	{
		ModEffects.registerAll(event.getRegistry());
	}
	/*
	@SubscribeEvent
	public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event)
	{
		ModEntityTypes.registerAll(event.getRegistry());
	}

 	*/

	@SubscribeEvent
	public static void registerConfigs(RegistryEvent.Register<Feature<?>> event)
	{
		ModFeatures.registerConfigs(event.getRegistry());
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
		ModFeatures.registerFeatures(event);
	}
*/

	@SubscribeEvent
	public static void registerFluids(RegistryEvent.Register<Fluid> event)
	{
		ModFluids.register(event.getRegistry());
	}

	@SubscribeEvent
	public static void onMissingMappings(RegistryEvent.MissingMappings<Item> event)
	{
		// Migrates `prismarin_*` to new `prismarine_*`
		for (MissingMappings.Mapping<Item> mapping : event.getMappings())
		{
			String path = mapping.key.getPath();
			switch (path)
			{
				case "prismarin_seeds":
				{
					mapping.remap(ModItems.SEEDS.get("prismarine_seeds"));
					break;
				}
				case "prismarin_particles":
				{
					mapping.remap(ModItems.PARTICLES.get("prismarine_particles"));
					break;
				}
			}
		}
	}
}
