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
import net.kaneka.planttech2.items.upgradeable.ItemRangedWeapon;
import net.kaneka.planttech2.items.upgradeable.ItemUpgradeChip;
import net.kaneka.planttech2.items.upgradeable.ItemUpgradeableArmor;
import net.kaneka.planttech2.items.upgradeable.ItemUpgradeableHand;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
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
	    		   BIOMASS = new ItemBase("biomass", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   BIOMASSCONTAINER = new ItemBiomassContainer(),
	    		   CYBERBOW = new ItemRangedWeapon("cyberbow", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor), 1000, 10), 
	    		   CYBERDAGGER = new ItemUpgradeableHand("cyberdagger", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor), 1000, 10, 1, -1.4F), 
	    		   CYBERKATANA = new ItemUpgradeableHand("cyberkatana", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor), 1000, 10, 8, -3.4F),
	    		   CYBERRAPIER = new ItemUpgradeableHand("cyberrapier", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor), 1000, 10, 4, -2.4F),
	    		   CAPACITYUPGRADE_TIER_1 = new ItemWithTier("capacityupgrade_1", new Item.Properties().group(ModCreativeTabs.groupchips), 1, 3),
	    		   CAPACITYUPGRADE_TIER_2 = new ItemWithTier("capacityupgrade_2", new Item.Properties().group(ModCreativeTabs.groupchips), 2, 3),
	    		   CAPACITYUPGRADE_TIER_3 = new ItemWithTier("capacityupgrade_3", new Item.Properties().group(ModCreativeTabs.groupchips), 3, 3),
	    		   COLOR_PARTICLES = new ItemParticle("color"),
	    		   DNA_CONTAINER_EMPTY = new ItemBase("dna_container_empty", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   DNA_CONTAINER = new ItemDNAContainer(), 
	    		   DANCIUM_INGOT = new ItemBase("dancium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   DANCIUM_NUGGET = new ItemBase("dancium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   FERTILIZER_TIER_1 = new ItemFertilizer("fertilizer_tier_1", ModCreativeTabs.groupmain),
	    		   FERTILIZER_TIER_2 = new ItemFertilizer("fertilizer_tier_2", ModCreativeTabs.groupmain),
	    		   FERTILIZER_TIER_3 = new ItemFertilizer("fertilizer_tier_3", ModCreativeTabs.groupmain),
	    		   FERTILIZER_TIER_4 = new ItemFertilizer("fertilizer_tier_4", ModCreativeTabs.groupmain),
	    		   FERTILIZER_CREATIVE = new ItemFertilizer("fertilizer_creative", ModCreativeTabs.groupmain),
	    		   GEAR_KANEKIUM = new ItemBase("gear_kanekium", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_DANCIUM = new ItemBase("gear_dancium", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_LENTHURIUM = new ItemBase("gear_lenthurium", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_KINNOIUM = new ItemBase("gear_kinnoium", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_PLANTIUM = new ItemBase("gear_plantium", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_IRON = new ItemBase("gear_iron", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_KANEKIUM_INFUSED = new ItemBase("gear_kanekium_infused", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_DANCIUM_INFUSED = new ItemBase("gear_dancium_infused", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_LENTHURIUM_INFUSED = new ItemBase("gear_lenthurium_infused", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_KINNOIUM_INFUSED = new ItemBase("gear_kinnoium_infused", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_PLANTIUM_INFUSED = new ItemBase("gear_plantium_infused", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_IRON_INFUSED = new ItemBase("gear_iron_infused", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GUIDE_OVERVIEW = new ItemGuide("overview"), 
	    		   GUIDE_PLANTS  = new ItemGuide("plants"), 
	    		   GUIDE_GENETIC_ENGINEERING = new ItemGuide("genetic_engineering"),
	    		   KANEKIUM_INGOT = new ItemBase("kanekium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   KANEKIUM_NUGGET = new ItemBase("kanekium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   KINNOIUM_INGOT = new ItemBase("kinnoium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   KINNOIUM_NUGGET = new ItemBase("kinnoium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   LENTHURIUM_INGOT = new ItemBase("lenthurium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   LENTHURIUM_NUGGET = new ItemBase("lenthurium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   MULTITOOL = new ItemMultitool(), 
	    		   PLANTIUM_INGOT  = new ItemBase("plantium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   PLANTIUM_NUGGET = new ItemBase("plantium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   RANGEUPGRADE_TIER_1 = new ItemWithTier("rangeupgrade_1", new Item.Properties().group(ModCreativeTabs.groupchips), 1, 1),
	    		   RANGEUPGRADE_TIER_2 = new ItemWithTier("rangeupgrade_2", new Item.Properties().group(ModCreativeTabs.groupchips), 2, 1),
	    		   RANGEUPGRADE_TIER_3 = new ItemWithTier("rangeupgrade_3", new Item.Properties().group(ModCreativeTabs.groupchips), 3, 1),
	    		   RANGEUPGRADE_TIER_4 = new ItemWithTier("rangeupgrade_4", new Item.Properties().group(ModCreativeTabs.groupchips), 4, 1),
	    		   SOLARFOCUS_TIER_1 = new ItemWithTier("solarfocus_1", new Item.Properties().group(ModCreativeTabs.groupchips), 1, 0),
	    		   SOLARFOCUS_TIER_2 = new ItemWithTier("solarfocus_2", new Item.Properties().group(ModCreativeTabs.groupchips), 2, 0),
	    		   SOLARFOCUS_TIER_3 = new ItemWithTier("solarfocus_3", new Item.Properties().group(ModCreativeTabs.groupchips), 3, 0),
	    		   SOLARFOCUS_TIER_4 = new ItemWithTier("solarfocus_4", new Item.Properties().group(ModCreativeTabs.groupchips), 4, 0),
	    		   SPEEDUPGRADE_TIER_1 = new ItemWithTier("speedupgrade_1", new Item.Properties().group(ModCreativeTabs.groupchips), 1, 2),
	    		   SPEEDUPGRADE_TIER_2 = new ItemWithTier("speedupgrade_2", new Item.Properties().group(ModCreativeTabs.groupchips), 2, 2),
	    		   SPEEDUPGRADE_TIER_3 = new ItemWithTier("speedupgrade_3", new Item.Properties().group(ModCreativeTabs.groupchips), 3, 2),
	    		   SPEEDUPGRADE_TIER_4 = new ItemWithTier("speedupgrade_4", new Item.Properties().group(ModCreativeTabs.groupchips), 4, 2),
	    		   THERMOMETER = new ItemThermometer(), 
	    		   WRENCH = new ItemWrench();
    
	public static ItemUpgradeChip CAPACITYCHIP_TIER_1 = new ItemUpgradeChip("capacitychip_tier_1").setIncreaseCapacity(2000).setEnergyCost(10), 
				CAPACITYCHIP_TIER_2 = new ItemUpgradeChip("capacitychip_tier_2").setIncreaseCapacity(5000).setEnergyCost(20), 
				CAPACITYCHIP_TIER_3 = new ItemUpgradeChip("capacitychip_tier_3").setIncreaseCapacity(10000).setEnergyCost(50), 
				REAKTORCHIP_TIER_1 = new ItemUpgradeChip("reaktorchip_1").setEnergyProduction(1).setEnergyCost(10), 
				REAKTORCHIP_TIER_2 = new ItemUpgradeChip("reaktorchip_2").setEnergyProduction(3).setEnergyCost(20), 
				REAKTORCHIP_TIER_3 = new ItemUpgradeChip("reaktorchip_3").setEnergyProduction(5).setEnergyCost(50), 
				UNLOCKCHIP_SHOVEL = new ItemUpgradeChip("unlockchip_shovel").setUnlockShovelFeat().setEnergyCost(20), 
				UNLOCKCHIP_AXE = new ItemUpgradeChip("unlockchip_axe").setUnlockAxeFeat().setEnergyCost(20), 
				UNLOCKCHIP_SHEARS = new ItemUpgradeChip("unlockchip_shears").setUnlockShearsFeat().setEnergyCost(20), 
				UNLOCKCHIP_HOE = new ItemUpgradeChip("unlockchip_hoe").setUnlockHoeFeat().setEnergyCost(20), 
				HARVESTLEVELCHIP_TIER_1 = new ItemUpgradeChip("harvestlevelchip_tier_1").setIncreaseHarvestlevel(1).setEnergyCost(20), 
				HARVESTLEVELCHIP_TIER_2 = new ItemUpgradeChip("harvestlevelchip_tier_2").setIncreaseHarvestlevel(2).setEnergyCost(60), 
				HARVESTLEVELCHIP_TIER_3 = new ItemUpgradeChip("harvestlevelchip_tier_3").setIncreaseHarvestlevel(4).setEnergyCost(150), 
				ATTACKCHIP_TIER_1 = new ItemUpgradeChip("attackchip_tier_1").setIncreaseAttack(0.5F).setEnergyCost(5), 
				ATTACKCHIP_TIER_2 = new ItemUpgradeChip("attackchip_tier_2").setIncreaseAttack(1F).setEnergyCost(15), 
				ATTACKCHIP_TIER_3 = new ItemUpgradeChip("attackchip_tier_3").setIncreaseAttack(2F).setEnergyCost(45), 
				ATTACKSPEEDCHIP_TIER_1 = new ItemUpgradeChip("attackspeedupgradechip_tier_1").setIncreaseAttackSpeed(0.1F).setEnergyCost(5), 
				ATTACKSPEEDCHIP_TIER_2 = new ItemUpgradeChip("attackspeedupgradechip_tier_2").setIncreaseAttackSpeed(0.25F).setEnergyCost(15), 
				ATTACKSPEEDCHIP_TIER_3 = new ItemUpgradeChip("attackspeedupgradechip_tier_3").setIncreaseAttackSpeed(0.5F).setEnergyCost(45), 
				BREAKDOWNRATECHIP_TIER_1 = new ItemUpgradeChip("breakdownratechip_tier_1").setIncreaseBreakdownRate(0.5F).setEnergyCost(5), 
				BREAKDOWNRATECHIP_TIER_2 = new ItemUpgradeChip("breakdownratechip_tier_2").setIncreaseBreakdownRate(1F).setEnergyCost(15), 
				BREAKDOWNRATECHIP_TIER_3 = new ItemUpgradeChip("breakdownratechip_tier_3").setIncreaseBreakdownRate(2.5F).setEnergyCost(80), 
				ARMORCHIP_TIER_1 = new ItemUpgradeChip("armorchip_tier_1").setIncreaseArmor(1).setEnergyCost(10), 
				ARMORCHIP_TIER_2 = new ItemUpgradeChip("armorchip_tier_2").setIncreaseArmor(2).setEnergyCost(30), 
				ARMORCHIP_TIER_3 = new ItemUpgradeChip("armorchip_tier_3").setIncreaseArmor(4).setEnergyCost(70), 
				TOUGHNESSCHIP_TIER_1 = new ItemUpgradeChip("toughnesschip_tier_1").setIncreaseToughness(0.5F).setEnergyCost(10), 
				TOUGHNESSCHIP_TIER_2 = new ItemUpgradeChip("toughnesschip_tier_2").setIncreaseToughness(1F).setEnergyCost(30), 
				TOUGHNESSCHIP_TIER_3 = new ItemUpgradeChip("toughnesschip_tier_3").setIncreaseToughness(2F).setEnergyCost(70);
	    		   
	    		   

    public static ItemArmorBase CHESTPLATE_KANEKIUM = new ItemArmorBase("chestplate_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EntityEquipmentSlot.CHEST, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				BOOTS_KANEKIUM = new ItemArmorBase("boots_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EntityEquipmentSlot.FEET, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				HELMET_KANEKIUM = new ItemArmorBase("helmet_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EntityEquipmentSlot.HEAD, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				LEGGINGS_KANEKIUM = new ItemArmorBase("leggings_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EntityEquipmentSlot.LEGS, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				CHESTPLATE_DANCIUM = new ItemArmorBase("chestplate_dancium", "dancium", CustomArmorMaterial.DANCIUM, EntityEquipmentSlot.CHEST, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				BOOTS_DANCIUM = new ItemArmorBase("boots_dancium", "dancium", CustomArmorMaterial.DANCIUM, EntityEquipmentSlot.FEET, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				HELMET_DANCIUM = new ItemArmorBase("helmet_dancium", "dancium", CustomArmorMaterial.DANCIUM, EntityEquipmentSlot.HEAD, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				LEGGINGS_DANCIUM = new ItemArmorBase("leggings_dancium", "dancium", CustomArmorMaterial.DANCIUM, EntityEquipmentSlot.LEGS, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				CHESTPLATE_LENTHURIUM = new ItemArmorBase("chestplate_lenthurium", "lenthurium", CustomArmorMaterial.LENTHURIUM, EntityEquipmentSlot.CHEST, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				BOOTS_LENTHURIUM = new ItemArmorBase("boots_lenthurium", "lenthurium", CustomArmorMaterial.LENTHURIUM, EntityEquipmentSlot.FEET, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				HELMET_LENTHURIUM = new ItemArmorBase("helmet_lenthurium", "lenthurium", CustomArmorMaterial.LENTHURIUM, EntityEquipmentSlot.HEAD, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				LEGGINGS_LENTHURIUM = new ItemArmorBase("leggings_lenthurium", "lenthurium", CustomArmorMaterial.LENTHURIUM, EntityEquipmentSlot.LEGS, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				CHESTPLATE_KINNOIUM = new ItemArmorBase("chestplate_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EntityEquipmentSlot.CHEST, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				BOOTS_KINNOIUM = new ItemArmorBase("boots_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EntityEquipmentSlot.FEET, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				HELMET_KINNOIUM = new ItemArmorBase("helmet_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EntityEquipmentSlot.HEAD, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				LEGGINGS_KINNOIUM = new ItemArmorBase("leggings_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EntityEquipmentSlot.LEGS, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor));
    
    public static ItemUpgradeableArmor CYBERARMOR_HELMET = new ItemUpgradeableArmor("cyberarmor_helmet",EntityEquipmentSlot.HEAD, 1000, 10, 1, 0),
    								   CYBERARMOR_CHEST = new ItemUpgradeableArmor("cyberarmor_chest",EntityEquipmentSlot.CHEST, 1000, 10, 3, 0),
    								   CYBERARMOR_LEGGINGS = new ItemUpgradeableArmor("cyberarmor_leggings",EntityEquipmentSlot.LEGS, 1000, 10, 2, 0),
    								   CYBERARMOR_BOOTS = new ItemUpgradeableArmor("cyberarmor_boots",EntityEquipmentSlot.FEET, 1000, 10, 1, 0);

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
