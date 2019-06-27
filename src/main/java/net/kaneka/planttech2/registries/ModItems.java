package net.kaneka.planttech2.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.items.AnalyserItem;
import net.kaneka.planttech2.items.BaseItem;
import net.kaneka.planttech2.items.BiomassContainerItem;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.DNAContainerItem;
import net.kaneka.planttech2.items.EnergyStorageItem;
import net.kaneka.planttech2.items.FertilizerItem;
import net.kaneka.planttech2.items.GuideItem;
import net.kaneka.planttech2.items.ParticleItem;
import net.kaneka.planttech2.items.ThermometerItem;
import net.kaneka.planttech2.items.TierItem;
import net.kaneka.planttech2.items.WrenchItem;
import net.kaneka.planttech2.items.armors.CustomArmorMaterial;
import net.kaneka.planttech2.items.armors.ArmorBaseItem;
import net.kaneka.planttech2.items.upgradeable.MultitoolItem;
import net.kaneka.planttech2.items.upgradeable.RangedWeaponItem;
import net.kaneka.planttech2.items.upgradeable.UpgradeChipItem;
import net.kaneka.planttech2.items.upgradeable.UpgradeableArmorItem;
import net.kaneka.planttech2.items.upgradeable.UpgradeableHandItem;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
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
    
    public static BaseItem ANALYSER = new AnalyserItem(), 
	    		   BIOMASS = new BaseItem("biomass", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   BIOMASSCONTAINER = new BiomassContainerItem(),
	    		   CYBERBOW = new RangedWeaponItem("cyberbow", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor), 1000, 10), 
	    		   CYBERDAGGER = new UpgradeableHandItem("cyberdagger", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor), 1000, 10, 1, -1.4F), 
	    		   CYBERKATANA = new UpgradeableHandItem("cyberkatana", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor), 1000, 10, 8, -3.4F),
	    		   CYBERRAPIER = new UpgradeableHandItem("cyberrapier", new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor), 1000, 10, 4, -2.4F),
	    		   CAPACITYUPGRADE_TIER_1 = new TierItem("capacityupgrade_1", new Item.Properties().group(ModCreativeTabs.groupchips), 1, 3),
	    		   CAPACITYUPGRADE_TIER_2 = new TierItem("capacityupgrade_2", new Item.Properties().group(ModCreativeTabs.groupchips), 2, 3),
	    		   CAPACITYUPGRADE_TIER_3 = new TierItem("capacityupgrade_3", new Item.Properties().group(ModCreativeTabs.groupchips), 3, 3),
	    		   COLOR_PARTICLES = new ParticleItem("color"),
	    		   DNA_CONTAINER_EMPTY = new BaseItem("dna_container_empty", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   DNA_CONTAINER = new DNAContainerItem(), 
	    		   DANCIUM_INGOT = new BaseItem("dancium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   DANCIUM_NUGGET = new BaseItem("dancium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)), 
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
	    		   LENTHURIUM_INGOT = new BaseItem("lenthurium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   LENTHURIUM_NUGGET = new BaseItem("lenthurium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   MULTITOOL = new MultitoolItem(), 
	    		   PLANTIUM_INGOT  = new BaseItem("plantium_ingot", new Item.Properties().group(ModCreativeTabs.groupmain)), 
	    		   PLANTIUM_NUGGET = new BaseItem("plantium_nugget", new Item.Properties().group(ModCreativeTabs.groupmain)),
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
	    		   THERMOMETER = new ThermometerItem(), 
	    		   WRENCH = new WrenchItem();
    
	public static UpgradeChipItem CAPACITYCHIP_TIER_1 = new UpgradeChipItem("capacitychip_tier_1").setIncreaseCapacity(2000).setEnergyCost(10), 
				CAPACITYCHIP_TIER_2 = new UpgradeChipItem("capacitychip_tier_2").setIncreaseCapacity(5000).setEnergyCost(20), 
				CAPACITYCHIP_TIER_3 = new UpgradeChipItem("capacitychip_tier_3").setIncreaseCapacity(10000).setEnergyCost(50), 
				REAKTORCHIP_TIER_1 = new UpgradeChipItem("reaktorchip_1").setEnergyProduction(1).setEnergyCost(10), 
				REAKTORCHIP_TIER_2 = new UpgradeChipItem("reaktorchip_2").setEnergyProduction(3).setEnergyCost(20), 
				REAKTORCHIP_TIER_3 = new UpgradeChipItem("reaktorchip_3").setEnergyProduction(5).setEnergyCost(50), 
				UNLOCKCHIP_SHOVEL = new UpgradeChipItem("unlockchip_shovel").setUnlockShovelFeat().setEnergyCost(20), 
				UNLOCKCHIP_AXE = new UpgradeChipItem("unlockchip_axe").setUnlockAxeFeat().setEnergyCost(20), 
				UNLOCKCHIP_SHEARS = new UpgradeChipItem("unlockchip_shears").setUnlockShearsFeat().setEnergyCost(20), 
				UNLOCKCHIP_HOE = new UpgradeChipItem("unlockchip_hoe").setUnlockHoeFeat().setEnergyCost(20), 
				HARVESTLEVELCHIP_TIER_1 = new UpgradeChipItem("harvestlevelchip_tier_1").setIncreaseHarvestlevel(1).setEnergyCost(20), 
				HARVESTLEVELCHIP_TIER_2 = new UpgradeChipItem("harvestlevelchip_tier_2").setIncreaseHarvestlevel(2).setEnergyCost(60), 
				HARVESTLEVELCHIP_TIER_3 = new UpgradeChipItem("harvestlevelchip_tier_3").setIncreaseHarvestlevel(4).setEnergyCost(150), 
				ATTACKCHIP_TIER_1 = new UpgradeChipItem("attackchip_tier_1").setIncreaseAttack(0.5F).setEnergyCost(5), 
				ATTACKCHIP_TIER_2 = new UpgradeChipItem("attackchip_tier_2").setIncreaseAttack(1F).setEnergyCost(15), 
				ATTACKCHIP_TIER_3 = new UpgradeChipItem("attackchip_tier_3").setIncreaseAttack(2F).setEnergyCost(45), 
				ATTACKSPEEDCHIP_TIER_1 = new UpgradeChipItem("attackspeedupgradechip_tier_1").setIncreaseAttackSpeed(0.1F).setEnergyCost(5), 
				ATTACKSPEEDCHIP_TIER_2 = new UpgradeChipItem("attackspeedupgradechip_tier_2").setIncreaseAttackSpeed(0.25F).setEnergyCost(15), 
				ATTACKSPEEDCHIP_TIER_3 = new UpgradeChipItem("attackspeedupgradechip_tier_3").setIncreaseAttackSpeed(0.5F).setEnergyCost(45), 
				BREAKDOWNRATECHIP_TIER_1 = new UpgradeChipItem("breakdownratechip_tier_1").setIncreaseBreakdownRate(0.5F).setEnergyCost(5), 
				BREAKDOWNRATECHIP_TIER_2 = new UpgradeChipItem("breakdownratechip_tier_2").setIncreaseBreakdownRate(1F).setEnergyCost(15), 
				BREAKDOWNRATECHIP_TIER_3 = new UpgradeChipItem("breakdownratechip_tier_3").setIncreaseBreakdownRate(2.5F).setEnergyCost(80), 
				ARMORCHIP_TIER_1 = new UpgradeChipItem("armorchip_tier_1").setIncreaseArmor(1).setEnergyCost(10), 
				ARMORCHIP_TIER_2 = new UpgradeChipItem("armorchip_tier_2").setIncreaseArmor(2).setEnergyCost(30), 
				ARMORCHIP_TIER_3 = new UpgradeChipItem("armorchip_tier_3").setIncreaseArmor(4).setEnergyCost(70), 
				TOUGHNESSCHIP_TIER_1 = new UpgradeChipItem("toughnesschip_tier_1").setIncreaseToughness(0.5F).setEnergyCost(10), 
				TOUGHNESSCHIP_TIER_2 = new UpgradeChipItem("toughnesschip_tier_2").setIncreaseToughness(1F).setEnergyCost(30), 
				TOUGHNESSCHIP_TIER_3 = new UpgradeChipItem("toughnesschip_tier_3").setIncreaseToughness(2F).setEnergyCost(70);
	    		   
	    		   

    public static ArmorBaseItem CHESTPLATE_KANEKIUM = new ArmorBaseItem("chestplate_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EquipmentSlotType.CHEST, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				BOOTS_KANEKIUM = new ArmorBaseItem("boots_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EquipmentSlotType.FEET, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				HELMET_KANEKIUM = new ArmorBaseItem("helmet_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EquipmentSlotType.HEAD, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				LEGGINGS_KANEKIUM = new ArmorBaseItem("leggings_kanekium", "kanekium", CustomArmorMaterial.KANEKIUM, EquipmentSlotType.LEGS, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				CHESTPLATE_DANCIUM = new ArmorBaseItem("chestplate_dancium", "dancium", CustomArmorMaterial.DANCIUM, EquipmentSlotType.CHEST, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				BOOTS_DANCIUM = new ArmorBaseItem("boots_dancium", "dancium", CustomArmorMaterial.DANCIUM, EquipmentSlotType.FEET, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				HELMET_DANCIUM = new ArmorBaseItem("helmet_dancium", "dancium", CustomArmorMaterial.DANCIUM, EquipmentSlotType.HEAD, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				LEGGINGS_DANCIUM = new ArmorBaseItem("leggings_dancium", "dancium", CustomArmorMaterial.DANCIUM, EquipmentSlotType.LEGS, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				CHESTPLATE_LENTHURIUM = new ArmorBaseItem("chestplate_lenthurium", "lenthurium", CustomArmorMaterial.LENTHURIUM, EquipmentSlotType.CHEST, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				BOOTS_LENTHURIUM = new ArmorBaseItem("boots_lenthurium", "lenthurium", CustomArmorMaterial.LENTHURIUM, EquipmentSlotType.FEET, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				HELMET_LENTHURIUM = new ArmorBaseItem("helmet_lenthurium", "lenthurium", CustomArmorMaterial.LENTHURIUM, EquipmentSlotType.HEAD, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				LEGGINGS_LENTHURIUM = new ArmorBaseItem("leggings_lenthurium", "lenthurium", CustomArmorMaterial.LENTHURIUM, EquipmentSlotType.LEGS, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				CHESTPLATE_KINNOIUM = new ArmorBaseItem("chestplate_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EquipmentSlotType.CHEST, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				BOOTS_KINNOIUM = new ArmorBaseItem("boots_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EquipmentSlotType.FEET, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				HELMET_KINNOIUM = new ArmorBaseItem("helmet_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EquipmentSlotType.HEAD, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor)),
				LEGGINGS_KINNOIUM = new ArmorBaseItem("leggings_kinnoium", "kinnoium", CustomArmorMaterial.KINNOIUM, EquipmentSlotType.LEGS, new Item.Properties().group(ModCreativeTabs.groupToolsAndArmor));
    
    public static UpgradeableArmorItem CYBERARMOR_HELMET = new UpgradeableArmorItem("cyberarmor_helmet", "cyberarmor", EquipmentSlotType.HEAD, 1000, 10, 1, 0),
    								   CYBERARMOR_CHEST = new UpgradeableArmorItem("cyberarmor_chest", "cyberarmor", EquipmentSlotType.CHEST, 1000, 10, 3, 0),
    								   CYBERARMOR_LEGGINGS = new UpgradeableArmorItem("cyberarmor_leggings", "cyberarmor", EquipmentSlotType.LEGS, 1000, 10, 2, 0),
    								   CYBERARMOR_BOOTS = new UpgradeableArmorItem("cyberarmor_boots", "cyberarmor", EquipmentSlotType.FEET, 1000, 10, 1, 0);

    public static HashMap<String, BaseItem> SEEDS = new HashMap<String, BaseItem>();
    public static HashMap<String, BaseItem> PARTICLES = new HashMap<String, BaseItem>();

    public static void register(IForgeRegistry<Item> registry)
    {

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
