package net.kaneka.planttech2.items;

import java.util.HashMap;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.gui.GUIReferences;
import net.kaneka.planttech2.items.armors.CustomArmorMaterial;
import net.kaneka.planttech2.items.armors.ItemArmorBase;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
    public static ItemBase THERMOMETER, ANALYSER, WRENCH, BIOMASS, SOLARFOCUS_TIER_1, SOLARFOCUS_TIER_2, SOLARFOCUS_TIER_3, SOLARFOCUS_TIER_4, RANGEUPGRADE_TIER_1, RANGEUPGRADE_TIER_2, RANGEUPGRADE_TIER_3, RANGEUPGRADE_TIER_4,
	    SPEEDUPGRADE_TIER_1, SPEEDUPGRADE_TIER_2, SPEEDUPGRADE_TIER_3, SPEEDUPGRADE_TIER_4, PLANTIUM_INGOT, PLANTIUM_NUGGET, DNA_CONTAINER_EMPTY, DNA_CONTAINER, DANCIUM_INGOT, DANCIUM_NUGGET, KINNOIUM_INGOT, KINNOIUM_NUGGET,
	    KANEKIUM_INGOT, KANEKIUM_NUGGET, LENTHURIUM_INGOT, LENTHURIUM_NUGGET, FERTILIZER_TIER_1, FERTILIZER_TIER_2, FERTILIZER_TIER_3, FERTILIZER_TIER_4;
    
    public static ItemBase FERTILIZER_CREATIVE; 
    
    
    public static ItemBase GUIDE_OVERVIEW, GUIDE_PLANTS, GUIDE_GENETIC_ENGINEERING;

    public static ItemBase COLOR_PARTICLES;

    public static ItemArmorBase CHESTPLATE_KANEKIUM, BOOTS_KANEKIUM, HELMET_KANEKIUM, LEGGINGS_KANEKIUM, CHESTPLATE_DANCIUM, BOOTS_DANCIUM, HELMET_DANCIUM, LEGGINGS_DANCIUM, CHESTPLATE_LENTHURIUM, BOOTS_LENTHURIUM, HELMET_LENTHURIUM,
	    LEGGINGS_LENTHURIUM, CHESTPLATE_KINNOIUM, BOOTS_KINNOIUM, HELMET_KINNOIUM, LEGGINGS_KINNOIUM;

    public static HashMap<String, ItemBase> SEEDS = new HashMap<String, ItemBase>();
    public static HashMap<String, ItemBase> PARTICLES = new HashMap<String, ItemBase>();

    public static void register(IForgeRegistry<Item> registry)
    {
	registry.registerAll(GUIDE_OVERVIEW = new ItemGuide("overview"), 
		GUIDE_PLANTS = new ItemGuide("plants"),
		GUIDE_GENETIC_ENGINEERING = new ItemGuide("genetic_engineering"), 
		THERMOMETER = new ItemThermometer(), ANALYSER = new ItemAnalyser(), WRENCH = new ItemWrench(),
		SOLARFOCUS_TIER_1 = new ItemWithTier("solarfocus_1", new Item.Properties().group(PlantTechMain.groupmain), 1, 0),
		SOLARFOCUS_TIER_2 = new ItemWithTier("solarfocus_2", new Item.Properties().group(PlantTechMain.groupmain), 2, 0),
		SOLARFOCUS_TIER_3 = new ItemWithTier("solarfocus_3", new Item.Properties().group(PlantTechMain.groupmain), 3, 0),
		SOLARFOCUS_TIER_4 = new ItemWithTier("solarfocus_4", new Item.Properties().group(PlantTechMain.groupmain), 4, 0),
		RANGEUPGRADE_TIER_1 = new ItemWithTier("rangeupgrade_1", new Item.Properties().group(PlantTechMain.groupmain), 1, 1),
		RANGEUPGRADE_TIER_2 = new ItemWithTier("rangeupgrade_2", new Item.Properties().group(PlantTechMain.groupmain), 2, 1),
		RANGEUPGRADE_TIER_3 = new ItemWithTier("rangeupgrade_3", new Item.Properties().group(PlantTechMain.groupmain), 3, 1),
		RANGEUPGRADE_TIER_4 = new ItemWithTier("rangeupgrade_4", new Item.Properties().group(PlantTechMain.groupmain), 4, 1),
		SPEEDUPGRADE_TIER_1 = new ItemWithTier("speedupgrade_1", new Item.Properties().group(PlantTechMain.groupmain), 1, 2),
		SPEEDUPGRADE_TIER_2 = new ItemWithTier("speedupgrade_2", new Item.Properties().group(PlantTechMain.groupmain), 2, 2),
		SPEEDUPGRADE_TIER_3 = new ItemWithTier("speedupgrade_3", new Item.Properties().group(PlantTechMain.groupmain), 3, 2),
		SPEEDUPGRADE_TIER_4 = new ItemWithTier("speedupgrade_4", new Item.Properties().group(PlantTechMain.groupmain), 4, 2), PLANTIUM_INGOT = new ItemBase("plantium_ingot", new Item.Properties().group(PlantTechMain.groupmain)),
		PLANTIUM_NUGGET = new ItemBase("plantium_nugget", new Item.Properties().group(PlantTechMain.groupmain)), DANCIUM_INGOT = new ItemBase("dancium_ingot", new Item.Properties().group(PlantTechMain.groupmain)),
		DANCIUM_NUGGET = new ItemBase("dancium_nugget", new Item.Properties().group(PlantTechMain.groupmain)), KANEKIUM_INGOT = new ItemBase("kanekium_ingot", new Item.Properties().group(PlantTechMain.groupmain)),
		KANEKIUM_NUGGET = new ItemBase("kanekium_nugget", new Item.Properties().group(PlantTechMain.groupmain)), KINNOIUM_INGOT = new ItemBase("kinnoium_ingot", new Item.Properties().group(PlantTechMain.groupmain)),
		KINNOIUM_NUGGET = new ItemBase("kinnoium_nugget", new Item.Properties().group(PlantTechMain.groupmain)), LENTHURIUM_INGOT = new ItemBase("lenthurium_ingot", new Item.Properties().group(PlantTechMain.groupmain)),
		LENTHURIUM_NUGGET = new ItemBase("lenthurium_nugget", new Item.Properties().group(PlantTechMain.groupmain)), COLOR_PARTICLES = new ItemParticle("color"),
		BIOMASS = new ItemBase("biomass", new Item.Properties().group(PlantTechMain.groupmain)), DNA_CONTAINER = new ItemDNAContainer(),
		DNA_CONTAINER_EMPTY = new ItemBase("dna_container_empty", new Item.Properties().group(PlantTechMain.groupmain)),

		CHESTPLATE_KANEKIUM = new ItemArmorBase("chestplate_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EntityEquipmentSlot.CHEST, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		BOOTS_KANEKIUM = new ItemArmorBase("boots_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EntityEquipmentSlot.FEET, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		HELMET_KANEKIUM = new ItemArmorBase("helmet_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EntityEquipmentSlot.HEAD, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		LEGGINGS_KANEKIUM = new ItemArmorBase("leggings_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EntityEquipmentSlot.LEGS, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		CHESTPLATE_DANCIUM = new ItemArmorBase("chestplate_dancium", "dancium", CustomArmorMaterial.DANCIUM, EntityEquipmentSlot.CHEST, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		BOOTS_DANCIUM = new ItemArmorBase("boots_dancium", "dancium", CustomArmorMaterial.DANCIUM, EntityEquipmentSlot.FEET, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		HELMET_DANCIUM = new ItemArmorBase("helmet_dancium", "dancium", CustomArmorMaterial.DANCIUM, EntityEquipmentSlot.HEAD, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		LEGGINGS_DANCIUM = new ItemArmorBase("leggings_dancium", "dancium", CustomArmorMaterial.DANCIUM, EntityEquipmentSlot.LEGS, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		CHESTPLATE_LENTHURIUM = new ItemArmorBase("chestplate_lenthurium", "lenthurium", CustomArmorMaterial.LENTHURIUM, EntityEquipmentSlot.CHEST, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		BOOTS_LENTHURIUM = new ItemArmorBase("boots_lenthurium", "lenthurium", CustomArmorMaterial.LENTHURIUM, EntityEquipmentSlot.FEET, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		HELMET_LENTHURIUM = new ItemArmorBase("helmet_lenthurium", "lenthurium", CustomArmorMaterial.LENTHURIUM, EntityEquipmentSlot.HEAD, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		LEGGINGS_LENTHURIUM = new ItemArmorBase("leggings_lenthurium", "lenthurium", CustomArmorMaterial.LENTHURIUM, EntityEquipmentSlot.LEGS, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		CHESTPLATE_KINNOIUM = new ItemArmorBase("chestplate_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EntityEquipmentSlot.CHEST, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		BOOTS_KINNOIUM = new ItemArmorBase("boots_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EntityEquipmentSlot.FEET, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		HELMET_KINNOIUM = new ItemArmorBase("helmet_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EntityEquipmentSlot.HEAD, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		LEGGINGS_KINNOIUM = new ItemArmorBase("leggings_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EntityEquipmentSlot.LEGS, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
		
		FERTILIZER_TIER_1 = new ItemFertilizer("fertilizer_tier_1", PlantTechMain.groupmain),
		FERTILIZER_TIER_2 = new ItemFertilizer("fertilizer_tier_2", PlantTechMain.groupmain),
		FERTILIZER_TIER_3 = new ItemFertilizer("fertilizer_tier_3", PlantTechMain.groupmain),
		FERTILIZER_TIER_4 = new ItemFertilizer("fertilizer_tier_4", PlantTechMain.groupmain),
		FERTILIZER_CREATIVE = new ItemFertilizer("fertilizer_creative", PlantTechMain.groupmain)
		
		);
	ItemBase tempseed, tempparticle;
	String name;
	for (CropListEntry entry : PlantTechMain.instance.croplist.getAllEntries())
	{
	    name = entry.getString();
	    tempseed = new ItemCropSeed(name);
	    SEEDS.put(name, tempseed);
	    registry.register(tempseed);
	    if (entry.hasParticle())
	    {
		tempparticle = new ItemParticle(name);
		PARTICLES.put(name, tempparticle);
		registry.register(tempparticle);
	    }
	}

    }
    
    public static void registerItemColorHandler(ColorHandlerEvent.Item event)
    {
	for (ItemBase entry : PARTICLES.values())
	{
	    event.getItemColors().register(new ItemParticle.ColorHandler(), entry);
	}
	
	for (ItemBase entry : SEEDS.values())
	{
	    event.getItemColors().register(new ItemCropSeed.ColorHandler(), entry);
	}
    }
}
