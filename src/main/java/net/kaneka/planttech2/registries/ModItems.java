package net.kaneka.planttech2.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.items.*;
import net.kaneka.planttech2.items.armors.ArmorBaseItem;
import net.kaneka.planttech2.items.upgradeable.MultitoolItem;
import net.kaneka.planttech2.items.upgradeable.RangedWeaponItem;
import net.kaneka.planttech2.items.upgradeable.UpgradeChipItem;
import net.kaneka.planttech2.items.upgradeable.UpgradeableArmorItem;
import net.kaneka.planttech2.items.upgradeable.UpgradeableHandItem;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems
{
    public static List<BaseItem> ITEMS = new ArrayList<BaseItem>(); 
    public static List<ArmorBaseItem> ITEMSARMOR = new ArrayList<ArmorBaseItem>(); 

    public static List<MachineBulbItem> MACHINEBULBS = new ArrayList<MachineBulbItem>(); 
    
    //public static Item BIOMASSBUCKET = new BucketItem(ModFluids.BIOMASS, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ModCreativeTabs.groupmain)); 
    
    public static BaseItem ANALYSER = new AnalyserItem(), 
    			   ADVANCED_ANALYSER = new AdvancedAnalyserItem(),
	    		   BIOMASS = new BaseItem("biomass", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   BIOMASSCONTAINER = new BiomassContainerItem(),
	    		   CROPREMOVER = new CropRemover(), 
	    		   CYBERBOW = new RangedWeaponItem("cyberbow", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor), 1000, 10), 
	    		   CYBERDAGGER = new UpgradeableHandItem("cyberdagger", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor), 1000, 10, 1, -1.4F, UpgradeChipItem.MELEE_WEAPON), 
	    		   CYBERKATANA = new UpgradeableHandItem("cyberkatana", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor), 1000, 10, 8, -3.4F, UpgradeChipItem.MELEE_WEAPON),
	    		   CYBERRAPIER = new UpgradeableHandItem("cyberrapier", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor), 1000, 10, 4, -2.4F, UpgradeChipItem.MELEE_WEAPON),
	    		   CAPACITYUPGRADE_TIER_1 = new TierItem("capacityupgrade_1", new Item.Properties().group(ModCreativeTabs.groupchips), 1, 3),
	    		   CAPACITYUPGRADE_TIER_2 = new TierItem("capacityupgrade_2", new Item.Properties().group(ModCreativeTabs.groupchips), 2, 3),
	    		   CAPACITYUPGRADE_TIER_3 = new TierItem("capacityupgrade_3", new Item.Properties().group(ModCreativeTabs.groupchips), 3, 3),
	    		   COLOR_PARTICLES = new ParticleItem("color"),
	    		   DNA_CONTAINER_EMPTY = new BaseItem("dna_container_empty", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   DNA_CONTAINER = new DNAContainerItem(), 
	    		   DANCIUM_INGOT = new BaseItem("dancium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   DANCIUM_NUGGET = new BaseItem("dancium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   EMPTY_UPGRADECHIP_TIER_1 = new TierItem("empty_upgradechip_1",  new Item.Properties().group(ModCreativeTabs.groupmain), 1, 3),
	    		   EMPTY_UPGRADECHIP_TIER_2 = new TierItem("empty_upgradechip_2",  new Item.Properties().group(ModCreativeTabs.groupmain), 2, 3),
	    		   EMPTY_UPGRADECHIP_TIER_3 = new TierItem("empty_upgradechip_3",  new Item.Properties().group(ModCreativeTabs.groupmain), 3, 3),
	    		   ENERGYSTORAGE_TIER_1 = new EnergyStorageItem("energystorage_tier_1", new Item.Properties().group(ModCreativeTabs.groupmain), 500), 
	    		   ENERGYSTORAGE_TIER_2 = new EnergyStorageItem("energystorage_tier_2", new Item.Properties().group(ModCreativeTabs.groupmain), 5000), 
	    		   ENERGYSTORAGE_TIER_3 = new EnergyStorageItem("energystorage_tier_3", new Item.Properties().group(ModCreativeTabs.groupmain), 50000), 
	    		   FERTILIZER_TIER_1 = new FertilizerItem("fertilizer_tier_1", ModCreativeTabs.groupmain),
	    		   FERTILIZER_TIER_2 = new FertilizerItem("fertilizer_tier_2", ModCreativeTabs.groupmain),
	    		   FERTILIZER_TIER_3 = new FertilizerItem("fertilizer_tier_3", ModCreativeTabs.groupmain),
	    		   FERTILIZER_TIER_4 = new FertilizerItem("fertilizer_tier_4", ModCreativeTabs.groupmain),
	    		   FERTILIZER_CREATIVE = new FertilizerItem("fertilizer_creative", ModCreativeTabs.groupmain),
	    		   GEAR_KANEKIUM = new BaseItem("gear_kanekium", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_DANCIUM = new BaseItem("gear_dancium", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_LENTHURIUM = new BaseItem("gear_lenthurium", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_KINNOIUM = new BaseItem("gear_kinnoium", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_PLANTIUM = new BaseItem("gear_plantium", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_IRON = new BaseItem("gear_iron", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_KANEKIUM_INFUSED = new BaseItem("gear_kanekium_infused", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_DANCIUM_INFUSED = new BaseItem("gear_dancium_infused", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_LENTHURIUM_INFUSED = new BaseItem("gear_lenthurium_infused", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_KINNOIUM_INFUSED = new BaseItem("gear_kinnoium_infused", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_PLANTIUM_INFUSED = new BaseItem("gear_plantium_infused", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GEAR_IRON_INFUSED = new BaseItem("gear_iron_infused", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   GUIDE_OVERVIEW = new GuideItem("overview"), 
	    		   GUIDE_PLANTS  = new GuideItem("plants"), 
	    		   GUIDE_GENETIC_ENGINEERING = new GuideItem("genetic_engineering"),
	    		   KANEKIUM_INGOT = new BaseItem("kanekium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   KANEKIUM_NUGGET = new BaseItem("kanekium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   KINNOIUM_INGOT = new BaseItem("kinnoium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   KINNOIUM_NUGGET = new BaseItem("kinnoium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)),
	    		   KNOWLEDGECHIP_TIER_0 = new KnowledgeChip(0, 50),
	    		   KNOWLEDGECHIP_TIER_1 = new KnowledgeChip(1, 250),
	    		   KNOWLEDGECHIP_TIER_2 = new KnowledgeChip(2, 1250),
	    		   KNOWLEDGECHIP_TIER_3 = new KnowledgeChip(3, 6250),
	    		   KNOWLEDGECHIP_TIER_4 = new KnowledgeChip(4, 31250),
	    		   KNOWLEDGECHIP_TIER_5 = new KnowledgeChip(5, 156250),
	    		   LENTHURIUM_INGOT = new BaseItem("lenthurium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   LENTHURIUM_NUGGET = new BaseItem("lenthurium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   MULTITOOL = new MultitoolItem(), 
	    		   PLANTCARD = new CreditCardItem("plantcard", new Item.Properties().group(ModCreativeTabs.groupmain).maxStackSize(1)),
	    		   PLANTIUM_INGOT  = new BaseItem("plantium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   PLANTIUM_NUGGET = new BaseItem("plantium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)),
				   PLANT_OBTAINER = new PlantObtainer("plant_obtainer", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
	               RADIATION_METRE= new RadiationMetre("radiation_metre", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
	    		   RANGEUPGRADE_TIER_1 = new TierItem("rangeupgrade_1", new Item.Properties().group(ModCreativeTabs.groupchips), 1, 1),
	    		   RANGEUPGRADE_TIER_2 = new TierItem("rangeupgrade_2", new Item.Properties().group(ModCreativeTabs.groupchips), 2, 1),
	    		   RANGEUPGRADE_TIER_3 = new TierItem("rangeupgrade_3", new Item.Properties().group(ModCreativeTabs.groupchips), 3, 1),
	    		   RANGEUPGRADE_TIER_4 = new TierItem("rangeupgrade_4", new Item.Properties().group(ModCreativeTabs.groupchips), 4, 1),
	    		   SOLARFOCUS_TIER_1 = new TierItem("solarfocus_1", new Item.Properties().group(ModCreativeTabs.groupchips), 1, 0),
	    		   SOLARFOCUS_TIER_2 = new TierItem("solarfocus_2", new Item.Properties().group(ModCreativeTabs.groupchips), 2, 0),
	    		   SOLARFOCUS_TIER_3 = new TierItem("solarfocus_3", new Item.Properties().group(ModCreativeTabs.groupchips), 3, 0),
	    		   SOLARFOCUS_TIER_4 = new TierItem("solarfocus_4", new Item.Properties().group(ModCreativeTabs.groupchips), 4, 0),
	    		   SPEEDUPGRADE_TIER_1 = new TierItem("speedupgrade_1", new Item.Properties().group(ModCreativeTabs.groupchips), 1, 2),
	    		   SPEEDUPGRADE_TIER_2 = new TierItem("speedupgrade_2", new Item.Properties().group(ModCreativeTabs.groupchips), 2, 2),
	    		   SPEEDUPGRADE_TIER_3 = new TierItem("speedupgrade_3", new Item.Properties().group(ModCreativeTabs.groupchips), 3, 2),
	    		   SPEEDUPGRADE_TIER_4 = new TierItem("speedupgrade_4", new Item.Properties().group(ModCreativeTabs.groupchips), 4, 2),
	    		   TELEPORTER = new TeleporterItem("teleporter", new Item.Properties().group(ModCreativeTabs.groupmain), 1000), 
	    		   THERMOMETER = new ThermometerItem(),  
	    		   WRENCH = new WrenchItem(), 
	    		   TESTITEM = new TestItem();
    
	public static UpgradeChipItem CAPACITYCHIP_TIER_1 = new UpgradeChipItem("capacitychip_tier_1").setIncreaseCapacity(2000).setEnergyCost(1), 
				CAPACITYCHIP_TIER_2 = new UpgradeChipItem("capacitychip_tier_2").setIncreaseCapacity(5000).setEnergyCost(2), 
				CAPACITYCHIP_TIER_3 = new UpgradeChipItem("capacitychip_tier_3").setIncreaseCapacity(10000).setEnergyCost(5), 
				REACTORCHIP_TIER_1 = new UpgradeChipItem("reactorchip_tier_1").setEnergyProduction(1).setEnergyCost(1), 
				REACTORCHIP_TIER_2 = new UpgradeChipItem("reactorchip_tier_2").setEnergyProduction(3).setEnergyCost(2), 
				REACTORCHIP_TIER_3 = new UpgradeChipItem("reactorchip_tier_3").setEnergyProduction(5).setEnergyCost(5), 
				UNLOCKCHIP_SHOVEL = new UpgradeChipItem("unlockchip_shovel").setUnlockShovelFeat().setEnergyCost(2), 
				UNLOCKCHIP_AXE = new UpgradeChipItem("unlockchip_axe").setUnlockAxeFeat().setEnergyCost(2), 
				UNLOCKCHIP_SHEARS = new UpgradeChipItem("unlockchip_shears").setUnlockShearsFeat().setEnergyCost(2), 
				UNLOCKCHIP_HOE = new UpgradeChipItem("unlockchip_hoe").setUnlockHoeFeat().setEnergyCost(2), 
				HARVESTLEVELCHIP_TIER_1 = new UpgradeChipItem("harvestlevelchip_tier_1").setIncreaseHarvestlevel(1).setEnergyCost(2), 
				HARVESTLEVELCHIP_TIER_2 = new UpgradeChipItem("harvestlevelchip_tier_2").setIncreaseHarvestlevel(2).setEnergyCost(6), 
				HARVESTLEVELCHIP_TIER_3 = new UpgradeChipItem("harvestlevelchip_tier_3").setIncreaseHarvestlevel(4).setEnergyCost(15), 
				ATTACKCHIP_TIER_1 = new UpgradeChipItem("attackchip_tier_1").setIncreaseAttack(0.5F).setEnergyCost(1), 
				ATTACKCHIP_TIER_2 = new UpgradeChipItem("attackchip_tier_2").setIncreaseAttack(1F).setEnergyCost(2), 
				ATTACKCHIP_TIER_3 = new UpgradeChipItem("attackchip_tier_3").setIncreaseAttack(2F).setEnergyCost(4), 
				ATTACKSPEEDCHIP_TIER_1 = new UpgradeChipItem("attackspeedchip_tier_1").setIncreaseAttackSpeed(0.1F).setEnergyCost(1), 
				ATTACKSPEEDCHIP_TIER_2 = new UpgradeChipItem("attackspeedchip_tier_2").setIncreaseAttackSpeed(0.25F).setEnergyCost(2), 
				ATTACKSPEEDCHIP_TIER_3 = new UpgradeChipItem("attackspeedchip_tier_3").setIncreaseAttackSpeed(0.5F).setEnergyCost(4), 
				BREAKDOWNRATECHIP_TIER_1 = new UpgradeChipItem("breakdownratechip_tier_1").setIncreaseBreakdownRate(0.5F).setEnergyCost(1), 
				BREAKDOWNRATECHIP_TIER_2 = new UpgradeChipItem("breakdownratechip_tier_2").setIncreaseBreakdownRate(1F).setEnergyCost(3), 
				BREAKDOWNRATECHIP_TIER_3 = new UpgradeChipItem("breakdownratechip_tier_3").setIncreaseBreakdownRate(2.5F).setEnergyCost(8), 
				ARMORCHIP_TIER_1 = new UpgradeChipItem("armorchip_tier_1").setIncreaseArmor(1).setEnergyCost(1), 
				ARMORCHIP_TIER_2 = new UpgradeChipItem("armorchip_tier_2").setIncreaseArmor(2).setEnergyCost(3), 
				ARMORCHIP_TIER_3 = new UpgradeChipItem("armorchip_tier_3").setIncreaseArmor(4).setEnergyCost(7), 
				TOUGHNESSCHIP_TIER_1 = new UpgradeChipItem("toughnesschip_tier_1").setIncreaseToughness(0.5F).setEnergyCost(1), 
				TOUGHNESSCHIP_TIER_2 = new UpgradeChipItem("toughnesschip_tier_2").setIncreaseToughness(1F).setEnergyCost(3), 
				TOUGHNESSCHIP_TIER_3 = new UpgradeChipItem("toughnesschip_tier_3").setIncreaseToughness(2F).setEnergyCost(7),
				
            	PROTECTION_CHIP = new UpgradeChipItem("protection_chip").setEnchantment(Enchantments.PROTECTION).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET).addRestriction(UpgradeChipItem.CHEST).addRestriction(UpgradeChipItem.LEGGINGS).addRestriction(UpgradeChipItem.BOOTS),
            	FIRE_PROTECTION_CHIP = new UpgradeChipItem("fire_protection_chip").setEnchantment(Enchantments.FIRE_PROTECTION).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET).addRestriction(UpgradeChipItem.CHEST).addRestriction(UpgradeChipItem.LEGGINGS).addRestriction(UpgradeChipItem.BOOTS),
            	FEATHER_FALLING_CHIP = new UpgradeChipItem("feather_falling_chip").setEnchantment(Enchantments.FEATHER_FALLING).setEnergyCost(5).addRestriction(UpgradeChipItem.BOOTS),
            	BLAST_PROTECTION_CHIP = new UpgradeChipItem("blast_protection_chip").setEnchantment(Enchantments.BLAST_PROTECTION).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET).addRestriction(UpgradeChipItem.CHEST).addRestriction(UpgradeChipItem.LEGGINGS).addRestriction(UpgradeChipItem.BOOTS),
            	PROJECTILE_PROTECTION_CHIP = new UpgradeChipItem("projectile_protection_chip").setEnchantment(Enchantments.PROJECTILE_PROTECTION).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET).addRestriction(UpgradeChipItem.CHEST).addRestriction(UpgradeChipItem.LEGGINGS).addRestriction(UpgradeChipItem.BOOTS),
            	RESPIRATION_CHIP = new UpgradeChipItem("respiration_chip").setEnchantment(Enchantments.RESPIRATION).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET),
            	AQUA_AFFINITY_CHIP = new UpgradeChipItem("aqua_affinity_chip").setEnchantment(Enchantments.AQUA_AFFINITY).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET),
            	THORNS_CHIP = new UpgradeChipItem("thorns_chip").setEnchantment(Enchantments.THORNS).setEnergyCost(5).addRestriction(UpgradeChipItem.CHEST),
            	DEPTH_STRIDER_CHIP = new UpgradeChipItem("depth_strider_chip").setEnchantment(Enchantments.DEPTH_STRIDER).setEnergyCost(5).addRestriction(UpgradeChipItem.BOOTS),
            	FROST_WALKER_CHIP = new UpgradeChipItem("frost_walker_chip").setEnchantment(Enchantments.FROST_WALKER).setEnergyCost(5).addRestriction(UpgradeChipItem.BOOTS),
            	SHARPNESS_CHIP = new UpgradeChipItem("sharpness_chip").setEnchantment(Enchantments.SHARPNESS).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON),
            	SMITE_CHIP = new UpgradeChipItem("smite_chip").setEnchantment(Enchantments.SMITE).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON),
            	BANE_OF_ARTHROPODS_CHIP = new UpgradeChipItem("bane_of_arthropods_chip").setEnchantment(Enchantments.BANE_OF_ARTHROPODS).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON),
            	KNOCKBACK_CHIP = new UpgradeChipItem("knockback_chip").setEnchantment(Enchantments.KNOCKBACK).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON),
            	FIRE_ASPECT_CHIP = new UpgradeChipItem("fire_aspect_chip").setEnchantment(Enchantments.FIRE_ASPECT).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON),
            	LOOTING_CHIP = new UpgradeChipItem("looting_chip").setEnchantment(Enchantments.LOOTING).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON),
            	SWEEPING_CHIP = new UpgradeChipItem("sweeping_chip").setEnchantment(Enchantments.SWEEPING).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON),
            	EFFICIENCY_CHIP = new UpgradeChipItem("efficiency_chip").setEnchantment(Enchantments.EFFICIENCY).setEnergyCost(5).addRestriction(UpgradeChipItem.TOOL),
            	SILK_TOUCH_CHIP = new UpgradeChipItem("silk_touch_chip").setEnchantment(Enchantments.SILK_TOUCH).setEnergyCost(5).addRestriction(UpgradeChipItem.TOOL),
            	UNBREAKING_CHIP = new UpgradeChipItem("unbreaking_chip").setEnchantment(Enchantments.UNBREAKING).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET).addRestriction(UpgradeChipItem.CHEST).addRestriction(UpgradeChipItem.LEGGINGS).addRestriction(UpgradeChipItem.BOOTS),
            	FORTUNE_CHIP = new UpgradeChipItem("fortune_chip").setEnchantment(Enchantments.FORTUNE).setEnergyCost(5).addRestriction(UpgradeChipItem.TOOL),
            	POWER_CHIP = new UpgradeChipItem("power_chip").setEnchantment(Enchantments.POWER).setEnergyCost(5).addRestriction(UpgradeChipItem.RANGED_WEAPON),
            	PUNCH_CHIP = new UpgradeChipItem("punch_chip").setEnchantment(Enchantments.PUNCH).setEnergyCost(5).addRestriction(UpgradeChipItem.RANGED_WEAPON),
            	FLAME_CHIP = new UpgradeChipItem("flame_chip").setEnchantment(Enchantments.FLAME).setEnergyCost(5).addRestriction(UpgradeChipItem.RANGED_WEAPON),
            	INFINITY_CHIP = new UpgradeChipItem("infinity_chip").setEnchantment(Enchantments.INFINITY).setEnergyCost(20).addRestriction(UpgradeChipItem.RANGED_WEAPON);
	
    
    public static UpgradeableArmorItem CYBERARMOR_HELMET = new UpgradeableArmorItem("cyberarmor_helmet", "cyberarmor", EquipmentSlotType.HEAD, 1000, 10, 1, 0, UpgradeChipItem.HELMET),
    								   CYBERARMOR_CHEST = new UpgradeableArmorItem("cyberarmor_chest", "cyberarmor", EquipmentSlotType.CHEST, 1000, 10, 3, 0, UpgradeChipItem.CHEST),
    								   CYBERARMOR_LEGGINGS = new UpgradeableArmorItem("cyberarmor_leggings", "cyberarmor", EquipmentSlotType.LEGS, 1000, 10, 2, 0, UpgradeChipItem.LEGGINGS),
    								   CYBERARMOR_BOOTS = new UpgradeableArmorItem("cyberarmor_boots", "cyberarmor", EquipmentSlotType.FEET, 1000, 10, 1, 0, UpgradeChipItem.BOOTS);

    public static MachineBulbItem MACHINEBULBREPROCESSOR_BULB = new MachineBulbItem("machinebulbreprocessor_bulb", ModBlocks.MACHINESHELL_IRON, ModBlocks.MACHINEBULBREPROCESSOR_GROWING, PlantTechConstants.MACHINETIER_MACHINEBULBREPROCESSOR, 0), 
    							SEEDSQUEEZER_BULB = new MachineBulbItem("seedsqueezer_bulb", ModBlocks.MACHINESHELL_IRON, ModBlocks.SEEDSQUEEZER_GROWING, PlantTechConstants.MACHINETIER_SEEDSQUEEZER, 0), 
    							COMPRESSOR_BULB = new MachineBulbItem("compressor_bulb", ModBlocks.MACHINESHELL_IRON ,  ModBlocks.COMPRESSOR_GROWING, PlantTechConstants.MACHINETIER_COMPRESSOR, 100), 
    	                    	IDENTIFIER_BULB = new MachineBulbItem("identifier_bulb", ModBlocks.MACHINESHELL_IRON ,  ModBlocks.IDENTIFIER_GROWING, PlantTechConstants.MACHINETIER_IDENTIFIER, 100),
    	                    	ENERGY_SUPPLIER_BULB = new MachineBulbItem("energy_supplier_bulb", ModBlocks.MACHINESHELL_IRON,  ModBlocks.ENERGY_SUPPLIER_GROWING, PlantTechConstants.MACHINETIER_ENERGY_SUPPLIER, 100),
    	                    	INFUSER_BULB = new MachineBulbItem("infuser_bulb", ModBlocks.MACHINESHELL_IRON ,  ModBlocks.INFUSER_GROWING, PlantTechConstants.MACHINETIER_INFUSER, 1000),
                        		CHIPALYZER_BULB = new MachineBulbItem("chipalyzer_bulb", ModBlocks.MACHINESHELL_PLANTIUM ,  ModBlocks.CHIPALYZER_GROWING, PlantTechConstants.MACHINETIER_CHIPALYZER, 1000),
                            	MEGAFURNACE_BULB = new MachineBulbItem("mega_furnace_bulb", ModBlocks.MACHINESHELL_PLANTIUM ,  ModBlocks.MEGAFURNACE_GROWING, PlantTechConstants.MACHINETIER_MEGAFURNACE, 1000),
                    	        DNA_CLEANER_BULB = new MachineBulbItem("dna_cleaner_bulb", ModBlocks.MACHINESHELL_PLANTIUM ,  ModBlocks.DNA_CLEANER_GROWING, PlantTechConstants.MACHINETIER_DNA_CLEANER, 2000), 
                    	        DNA_COMBINER_BULB = new MachineBulbItem("dna_combiner_bulb", ModBlocks.MACHINESHELL_PLANTIUM ,  ModBlocks.DNA_COMBINER_GROWING, PlantTechConstants.MACHINETIER_DNA_COMBINER, 2000),
                    	        DNA_EXTRACTOR_BULB = new MachineBulbItem("dna_extractor_bulb", ModBlocks.MACHINESHELL_PLANTIUM ,  ModBlocks.DNA_EXTRACTOR_GROWING, PlantTechConstants.MACHINETIER_DNA_EXTRACTOR, 2000), 
                    	        DNA_REMOVER_BULB = new MachineBulbItem("dna_remover_bulb", ModBlocks.MACHINESHELL_PLANTIUM ,  ModBlocks.DNA_REMOVER_GROWING, PlantTechConstants.MACHINETIER_DNA_REMOVER, 2000), 
                    	        SEEDCONSTRUCTOR_BULB = new MachineBulbItem("seedconstructor_bulb", ModBlocks.MACHINESHELL_PLANTIUM ,  ModBlocks.SEEDSQUEEZER_GROWING, PlantTechConstants.MACHINETIER_SEEDCONSTRUCTOR, 2000),
                    	        PLANTFARM_BULB = new MachineBulbItem("plantfarm_bulb", ModBlocks.MACHINESHELL_PLANTIUM ,  ModBlocks.PLANTFARM_GROWING, PlantTechConstants.MACHINETIER_PLANTFARM, 2000),
                    	        SOLARGENERATOR_BULB = new MachineBulbItem("solargenerator_bulb", ModBlocks.MACHINESHELL_PLANTIUM ,  ModBlocks.SOLARGENERATOR_GROWING, PlantTechConstants.MACHINETIER_SOLARGENERATOR, 2000); 
                        
    public static HashMap<String, BaseItem> SEEDS = new HashMap<String, BaseItem>();
    public static HashMap<String, BaseItem> PARTICLES = new HashMap<String, BaseItem>();

    public static void register(IForgeRegistry<Item> registry)
    {
    	//BIOMASSBUCKET.setRegistryName("biomassbucket"); 

	for(BaseItem item: ITEMS)
	{
	    registry.register(item);
	}
	
	
	for(ArmorBaseItem item: ITEMSARMOR)
	{
	    registry.register(item);
	}
	
	BaseItem tempseed, tempparticle;
	String name;
	for (CropListEntry entry : PlantTechMain.croplist.getAllEntries())
	{
	    name = entry.getString();
	    tempseed = new CropSeedItem(name);
	    SEEDS.put(name, tempseed);
	    registry.register(tempseed);
	    if (entry.hasParticle())
	    {
		tempparticle = new ParticleItem(name);
		PARTICLES.put(name, tempparticle);
		registry.register(tempparticle);
	    }
	}
	
	//registry.register(BIOMASSBUCKET);
	
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void registerItemColorHandler(ColorHandlerEvent.Item event)
    {
	for (BaseItem entry : PARTICLES.values())
	{
	    event.getItemColors().register(new ParticleItem.ColorHandler(), entry);
	}
	
	for (BaseItem entry : SEEDS.values())
	{
	    event.getItemColors().register(new CropSeedItem.ColorHandler(), entry);
	}
    }
}
