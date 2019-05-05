package net.kaneka.planttech2.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.items.ItemAnalyser;
import net.kaneka.planttech2.items.ItemBase;
import net.kaneka.planttech2.items.ItemBiomassContainer;
import net.kaneka.planttech2.items.ItemCropSeed;
import net.kaneka.planttech2.items.ItemDNAContainer;
import net.kaneka.planttech2.items.ItemFertilizer;
import net.kaneka.planttech2.items.ItemGuide;
import net.kaneka.planttech2.items.ItemParticle;
import net.kaneka.planttech2.items.ItemThermometer;
import net.kaneka.planttech2.items.ItemWithTier;
import net.kaneka.planttech2.items.ItemWrench;
import net.kaneka.planttech2.items.armors.CustomArmorMaterial;
import net.kaneka.planttech2.items.armors.ItemArmorBase;
import net.kaneka.planttech2.items.upgradeable.ItemMultitool;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
    public static List<ItemBase> ITEMS = new ArrayList<ItemBase>(); 
    public static List<ItemArmorBase> ITEMSARMOR = new ArrayList<ItemArmorBase>(); 
    
    public static ItemBase ANALYSER = new ItemAnalyser(), 
	    		   BIOMASS = new ItemBase("biomass", new Item.Properties().group(PlantTechMain.groupmain)), 
	    		   BIOMASSCONTAINER = new ItemBiomassContainer(),
	    		   CAPACITYUPGRADE_TIER_1 = new ItemWithTier("capacityupgrade_1", new Item.Properties().group(PlantTechMain.groupmain), 1, 3),
	    		   CAPACITYUPGRADE_TIER_2 = new ItemWithTier("capacityupgrade_2", new Item.Properties().group(PlantTechMain.groupmain), 2, 3),
	    		   CAPACITYUPGRADE_TIER_3 = new ItemWithTier("capacityupgrade_3", new Item.Properties().group(PlantTechMain.groupmain), 3, 3),
	    		   COLOR_PARTICLES = new ItemParticle("color"),
	    		   DNA_CONTAINER_EMPTY = new ItemBase("dna_container_empty", new Item.Properties().group(PlantTechMain.groupmain)), 
	    		   DNA_CONTAINER = new ItemDNAContainer(), 
	    		   DANCIUM_INGOT = new ItemBase("dancium_ingot", new Item.Properties().group(PlantTechMain.groupmain)), 
	    		   DANCIUM_NUGGET = new ItemBase("dancium_nugget", new Item.Properties().group(PlantTechMain.groupmain)), 
	    		   FERTILIZER_TIER_1 = new ItemFertilizer("fertilizer_tier_1", PlantTechMain.groupmain),
	    		   FERTILIZER_TIER_2 = new ItemFertilizer("fertilizer_tier_2", PlantTechMain.groupmain),
	    		   FERTILIZER_TIER_3 = new ItemFertilizer("fertilizer_tier_3", PlantTechMain.groupmain),
	    		   FERTILIZER_TIER_4 = new ItemFertilizer("fertilizer_tier_4", PlantTechMain.groupmain),
	    		   FERTILIZER_CREATIVE = new ItemFertilizer("fertilizer_creative", PlantTechMain.groupmain),
	    		   GEAR_KANEKIUM = new ItemBase("gear_kanekium", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   GEAR_DANCIUM = new ItemBase("gear_dancium", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   GEAR_LENTHURIUM = new ItemBase("gear_lenthurium", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   GEAR_KINNOIUM = new ItemBase("gear_kinnoium", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   GEAR_PLANTIUM = new ItemBase("gear_plantium", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   GEAR_IRON = new ItemBase("gear_iron", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   GEAR_KANEKIUM_INFUSED = new ItemBase("gear_kanekium_infused", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   GEAR_DANCIUM_INFUSED = new ItemBase("gear_dancium_infused", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   GEAR_LENTHURIUM_INFUSED = new ItemBase("gear_lenthurium_infused", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   GEAR_KINNOIUM_INFUSED = new ItemBase("gear_kinnoium_infused", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   GEAR_PLANTIUM_INFUSED = new ItemBase("gear_plantium_infused", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   GEAR_IRON_INFUSED = new ItemBase("gear_iron_infused", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   GUIDE_OVERVIEW = new ItemGuide("overview"), 
	    		   GUIDE_PLANTS  = new ItemGuide("plants"), 
	    		   GUIDE_GENETIC_ENGINEERING = new ItemGuide("genetic_engineering"),
	    		   KANEKIUM_INGOT = new ItemBase("kanekium_ingot", new Item.Properties().group(PlantTechMain.groupmain)), 
	    		   KANEKIUM_NUGGET = new ItemBase("kanekium_nugget", new Item.Properties().group(PlantTechMain.groupmain)), 
	    		   KINNOIUM_INGOT = new ItemBase("kinnoium_ingot", new Item.Properties().group(PlantTechMain.groupmain)), 
	    		   KINNOIUM_NUGGET = new ItemBase("kinnoium_nugget", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   LENTHURIUM_INGOT = new ItemBase("lenthurium_ingot", new Item.Properties().group(PlantTechMain.groupmain)), 
	    		   LENTHURIUM_NUGGET = new ItemBase("lenthurium_nugget", new Item.Properties().group(PlantTechMain.groupmain)), 
	    		   MULTITOOL = new ItemMultitool(), 
	    		   PLANTIUM_INGOT  = new ItemBase("plantium_ingot", new Item.Properties().group(PlantTechMain.groupmain)), 
	    		   PLANTIUM_NUGGET = new ItemBase("plantium_nugget", new Item.Properties().group(PlantTechMain.groupmain)),
	    		   RANGEUPGRADE_TIER_1 = new ItemWithTier("rangeupgrade_1", new Item.Properties().group(PlantTechMain.groupmain), 1, 1),
	    		   RANGEUPGRADE_TIER_2 = new ItemWithTier("rangeupgrade_2", new Item.Properties().group(PlantTechMain.groupmain), 2, 1),
	    		   RANGEUPGRADE_TIER_3 = new ItemWithTier("rangeupgrade_3", new Item.Properties().group(PlantTechMain.groupmain), 3, 1),
	    		   RANGEUPGRADE_TIER_4 = new ItemWithTier("rangeupgrade_4", new Item.Properties().group(PlantTechMain.groupmain), 4, 1),
	    		   SOLARFOCUS_TIER_1 = new ItemWithTier("solarfocus_1", new Item.Properties().group(PlantTechMain.groupmain), 1, 0),
	    		   SOLARFOCUS_TIER_2 = new ItemWithTier("solarfocus_2", new Item.Properties().group(PlantTechMain.groupmain), 2, 0),
	    		   SOLARFOCUS_TIER_3 = new ItemWithTier("solarfocus_3", new Item.Properties().group(PlantTechMain.groupmain), 3, 0),
	    		   SOLARFOCUS_TIER_4 = new ItemWithTier("solarfocus_4", new Item.Properties().group(PlantTechMain.groupmain), 4, 0),
	    		   SPEEDUPGRADE_TIER_1 = new ItemWithTier("speedupgrade_1", new Item.Properties().group(PlantTechMain.groupmain), 1, 2),
	    		   SPEEDUPGRADE_TIER_2 = new ItemWithTier("speedupgrade_2", new Item.Properties().group(PlantTechMain.groupmain), 2, 2),
	    		   SPEEDUPGRADE_TIER_3 = new ItemWithTier("speedupgrade_3", new Item.Properties().group(PlantTechMain.groupmain), 3, 2),
	    		   SPEEDUPGRADE_TIER_4 = new ItemWithTier("speedupgrade_4", new Item.Properties().group(PlantTechMain.groupmain), 4, 2),
	    		   THERMOMETER = new ItemThermometer(), 
	    		   WRENCH = new ItemWrench();
	    		   
	    		   

    public static ItemArmorBase CHESTPLATE_KANEKIUM = new ItemArmorBase("chestplate_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EntityEquipmentSlot.CHEST, new Item.Properties().group(PlantTechMain.groupToolsAndArmor)),
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
				LEGGINGS_KINNOIUM = new ItemArmorBase("leggings_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EntityEquipmentSlot.LEGS, new Item.Properties().group(PlantTechMain.groupToolsAndArmor));

    public static HashMap<String, ItemBase> SEEDS = new HashMap<String, ItemBase>();
    public static HashMap<String, ItemBase> PARTICLES = new HashMap<String, ItemBase>();

    public static void register(IForgeRegistry<Item> registry)
    {

	for(ItemBase item: ITEMS)
	{
	    registry.register(item);
	}
	
	
	for(ItemArmorBase item: ITEMSARMOR)
	{
	    registry.register(item);
	}
	
	ItemBase tempseed, tempparticle;
	String name;
	for (CropListEntry entry : PlantTechMain.croplist.getAllEntries())
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
    
    @OnlyIn(Dist.CLIENT)
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
