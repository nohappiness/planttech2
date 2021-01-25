package net.kaneka.planttech2.datagen;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.crops.CropEntry;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModEffects;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.commons.lang3.StringUtils;

import static net.kaneka.planttech2.PlantTechMain.MODID;

public class Languages extends LanguageProvider
{
    public Languages(DataGenerator gen)
    {
        super(gen, MODID, "en_us");
    }

    @Override
    protected void addTranslations()
    {
        addBlocks();
        addHedges(); 
        addCrops();
        addGuides();
        addGUIs();
        addItems();
        addItemGroups();
        addInfo();
        addMisc();
    }

    private void addMisc()
    {
        add(MODID + ".update.available", "An update for PlantTech 2 is available. ");
        add(MODID + ".update.click", "Click here");
        add(MODID + ".update.tooltip", "View CurseForge");
        add(MODID + ".electric_fence.idle", "Electric fence hums");
        //temporary disabled as PlantTopia is not implemented yet
//        add(ModEffects.RADIATION_SICKNESS, "Radiation Sickness");

        add(MODID + ".crossbreeding", "Crossbreeding");
        add(MODID + ".infuser", "Infuser");
        add(MODID + ".compressor", "Compressor");
        add(MODID + ".chipalyzer", "Chipalyzer");
        add(MODID + ".carver", "Carver Crop");
        add(MODID + ".machinebulbreprocessor", "Machinebulb Reprocessor");
        add(MODID + ".machine_growing", "Growing Machine");
        add("config.test", "TEST");
        add("fluid.biomass", "Biomass");

        add("death.attack.radiation_sickness", "%1$s was infected by radiation sickness");
        add("armorinformation.when_worn", "When worn:");
        add("armorinformation.selfrepair", "Self-repair");
        add("armorinformation.autofeed", "Auto-feed");
        add("techvillager.profession.scientist", "Scientist");
        add("techvillager.profession.botanist", "Botanist");
        add("techvillager.profession.headhunter", "Headhunter");
        add("techvillager.profession.engineer", "Engineer");
        add("text.biometemperature", "temperature");
        add("temp.extreme_cold", "Extremely Cold");
        add("temp.cold", "Cold");
        add("temp.normal", "Normal");
        add("temp.warm", "Warm");
        add("temp.extreme_warm", "Extremely Warm");
    }

    private void addItemGroups()
    {
        add("itemGroup.planttech2_main", "General");
        add("itemGroup.planttech2_blocks", "Building Blocks");
        add("itemGroup.planttech2_seeds", "Seeds");
        add("itemGroup.planttech2_machines", "Machines");
        add("itemGroup.planttech2_particles", "Particles");
        add("itemGroup.planttech2_toolsandarmor", "Tools and Armor");
        add("itemGroup.planttech2_chips", "Chips and Upgrades");
    }

    private void addItems()
    {
        add(ModItems.GUIDE_OVERVIEW, "Tablet: PlantTech 2 Guide");
        add(ModItems.GUIDE_PLANTS, "Tablet: Plant Encyclopedia");
        add(ModItems.GUIDE_GENETIC_ENGINEERING, "Tablet: Genetic Engineering (NO FUNKTION!)");
        add(ModItems.ANALYSER, "Analyzer");
        add(ModItems.ADVANCED_ANALYSER, "Advanced Analyzer");
        add(ModItems.CROPREMOVER, "Crop Remover");
        add(ModItems.WRENCH, "Wrench");
        add(ModItems.THERMOMETER, "Thermometer");
        add(ModItems.SOLARFOCUS_TIER_1, "Class 1 Solar Focus");
        add(ModItems.SOLARFOCUS_TIER_2, "Class 2 Solar Focus");
        add(ModItems.SOLARFOCUS_TIER_3, "Class 3 Solar Focus");
        add(ModItems.SOLARFOCUS_TIER_4, "Class 4 Solar Focus");
        add(ModItems.SPEEDUPGRADE_TIER_1, "Speed Upgrade v1.0");
        add(ModItems.SPEEDUPGRADE_TIER_2, "Speed Upgrade v2.0");
        add(ModItems.SPEEDUPGRADE_TIER_3, "Speed Upgrade v3.0");
        add(ModItems.SPEEDUPGRADE_TIER_4, "Speed Upgrade v4.0");
        add(ModItems.RANGEUPGRADE_TIER_1, "Range Upgrade v1.0");
        add(ModItems.RANGEUPGRADE_TIER_2, "Range Upgrade v2.0");
        add(ModItems.RANGEUPGRADE_TIER_3, "Range Upgrade v3.0");
        add(ModItems.RANGEUPGRADE_TIER_4, "Range Upgrade v4.0");
        add(ModItems.CAPACITYUPGRADE_TIER_1, "Capacity Upgrade v1.0");
        add(ModItems.CAPACITYUPGRADE_TIER_2, "Capacity Upgrade v2.0");
        add(ModItems.CAPACITYUPGRADE_TIER_3, "Capacity Upgrade v3.0");
        add(ModItems.PLANTIUM_INGOT, "Plantium Ingot");
        add(ModItems.PLANTIUM_NUGGET, "Plantium Nugget");
        add(ModItems.DANCIUM_INGOT, "Dancium Ingot");
        add(ModItems.DANCIUM_NUGGET, "Dancium Nugget");
        add(ModItems.KANEKIUM_INGOT, "Kanekium Ingot");
        add(ModItems.KANEKIUM_NUGGET, "Kanekium Nugget");
        add(ModItems.KINNOIUM_INGOT, "Kinnoium Ingot");
        add(ModItems.KINNOIUM_NUGGET, "Kinnoium Nugget");
        add(ModItems.LENTHURIUM_INGOT, "Lenthurium Ingot");
        add(ModItems.LENTHURIUM_NUGGET, "Lenthurium Nugget");
        add(ModItems.BIOMASS, "Biomass");
        add(ModItems.DNA_CONTAINER_EMPTY, "Empty DNA Container");
        add(ModItems.DNA_CONTAINER, "DNA Container");
        add(ModItems.FERTILIZER_TIER_1, "G1 Fertilizer");
        add(ModItems.FERTILIZER_TIER_2, "G2 Fertilizer");
        add(ModItems.FERTILIZER_TIER_3, "G3 Fertilizer");
        add(ModItems.FERTILIZER_TIER_4, "G4 Fertilizer");
        add(ModItems.FERTILIZER_CREATIVE, "Creative Fertilizer");
        add(ModItems.GEAR_KANEKIUM, "Kanekium Gear");
        add(ModItems.GEAR_LENTHURIUM, "Lenthurium Gear");
        add(ModItems.GEAR_KINNOIUM, "Kinnoium Gear");
        add(ModItems.GEAR_DANCIUM, "Dancium Gear");
        add(ModItems.GEAR_IRON, "Iron Gear");
        add(ModItems.GEAR_PLANTIUM, "Plantium Gear");
        add(ModItems.GEAR_KANEKIUM_INFUSED, "Infused Kanekium Gear");
        add(ModItems.GEAR_LENTHURIUM_INFUSED, "Infused Lenthurium Gear");
        add(ModItems.GEAR_KINNOIUM_INFUSED, "Infused Kinnoium Gear");
        add(ModItems.GEAR_DANCIUM_INFUSED, "Infused Dancium Gear");
        add(ModItems.GEAR_IRON_INFUSED, "Infused Iron Gear");
        add(ModItems.GEAR_PLANTIUM_INFUSED, "Infused Plantium Gear");
        add(ModItems.REDSTONE_INFUSED, "Infused Redstone");
        add(ModItems.BIOMASSCONTAINER, "Biomasscontainer");
        add(ModItems.ENERGYSTORAGE_TIER_1, "Class 1 Battery");
        add(ModItems.ENERGYSTORAGE_TIER_2, "Class 2 Battery");
        add(ModItems.ENERGYSTORAGE_TIER_3, "Class 3 Battery");
        add(ModItems.KNOWLEDGECHIP_TIER_0, "Knowledge Chip v0a");
        add(ModItems.KNOWLEDGECHIP_TIER_1, "Knowledge Chip v1.0");
        add(ModItems.KNOWLEDGECHIP_TIER_2, "Knowledge Chip v2.0");
        add(ModItems.KNOWLEDGECHIP_TIER_3, "Knowledge Chip v3.0");
        add(ModItems.KNOWLEDGECHIP_TIER_4, "Knowledge Chip v4.0");
        add(ModItems.KNOWLEDGECHIP_TIER_5, "Knowledge Chip v5.0");
        add(ModItems.WHITE_CRYSTAL, "White Crystal");
        add(ModItems.DARK_CRYSTAL, "Dark Crystal");

        add(ModItems.PLANT_OBTAINER, "Plant Obtainer");
        add(ModItems.RADIATION_METRE, "Radiation Metre");

        add(ModItems.BIOMASS_BUCKET, "Biomass Bucket");
        add(ModItems.MACHINEBULBREPROCESSOR_BULB, "Machinebulb Reprocessor Bulb");
        add(ModItems.DNA_COMBINER_BULB, "DNA Combiner Bulb");
        add(ModItems.DNA_EXTRACTOR_BULB, "DNA Extractor Bulb");
        add(ModItems.DNA_REMOVER_BULB, "DNA Remover Bulb");
        add(ModItems.DNA_CLEANER_BULB, "DNA Cleaner Bulb");
        add(ModItems.CHIPALYZER_BULB, "Chipalyzer Bulb");
        add(ModItems.COMPRESSOR_BULB, "Compressor Bulb");
        add(ModItems.IDENTIFIER_BULB, "Identifier Bulb");
        add(ModItems.INFUSER_BULB, "Infuser Bulb");
        add(ModItems.SEEDSQUEEZER_BULB, "Seed Squeezer Bulb");
        add(ModItems.SOLARGENERATOR_BULB, "Solar Generator Bulb");
        add(ModItems.MEGAFURNACE_BULB, "Megafurnace Bulb");
        add(ModItems.PLANTFARM_BULB, "Plantfarm Bulb");
        add(ModItems.SEEDCONSTRUCTOR_BULB, "Seed Constructor Bulb");
        add(ModItems.ENERGY_SUPPLIER_BULB, "Energy Supplier Bulb");

        add(ModItems.CYBERBOW, "CyberBow");
        add(ModItems.CYBERDAGGER, "CyberDagger");
        add(ModItems.CYBERKATANA, "CyberKatana");
        add(ModItems.CYBERRAPIER, "CyberRapier");
        add(ModItems.MULTITOOL, "MultiTool");

        add(ModItems.CYBERARMOR_HELMET, "CyberArmor Helmet");
        add(ModItems.CYBERARMOR_CHEST, "CyberArmor Chest");
        add(ModItems.CYBERARMOR_LEGGINGS, "CyberArmor Leggings");
        add(ModItems.CYBERARMOR_BOOTS, "CyberArmor Boots");

        add(ModItems.EMPTY_UPGRADECHIP_TIER_1, "Blank Upgrade Chip");
        add(ModItems.EMPTY_UPGRADECHIP_TIER_2, "v2.0 Chip Upgrade Pack");
        add(ModItems.EMPTY_UPGRADECHIP_TIER_3, "v3.0 Chip Upgrade Pack");
        add(ModItems.CHIP_UPGRADEPACK_CAPACITY_1, "v2.0 Capacity Chip Upgrade Pack");
        add(ModItems.CHIP_UPGRADEPACK_CAPACITY_2, "v3.0 Capacity Chip Upgrade Pack");
        add(ModItems.CHIP_UPGRADEPACK_HARVESTLEVEL_1, "v2.0 Harvest Level Chip Upgrade Pack");
        add(ModItems.CHIP_UPGRADEPACK_HARVESTLEVEL_2, "v3.0 Harvest Level Chip Upgrade Pack");
        add(ModItems.CHIP_UPGRADEPACK_REACTOR_1, "v2.0 Reactor Chip Upgrade Pack");
        add(ModItems.CHIP_UPGRADEPACK_REACTOR_2, "v3.0 Reactor Chip Upgrade Pack");

        add(ModItems.CAPACITYCHIP_TIER_1, "Capacity Chip v1.0");
        add(ModItems.CAPACITYCHIP_TIER_2, "Capacity Chip v2.0");
        add(ModItems.CAPACITYCHIP_TIER_3, "Capacity Chip v3.0");
        add(ModItems.HARVESTLEVELCHIP_TIER_1, "Harvest Level Chip v1.0");
        add(ModItems.HARVESTLEVELCHIP_TIER_2, "Harvest Level Chip v2.0");
        add(ModItems.HARVESTLEVELCHIP_TIER_3, "Harvest Level Chip v3.0");
        add(ModItems.ATTACKCHIP_TIER_1, "Attack Chip v1.0");
        add(ModItems.ATTACKCHIP_TIER_2, "Attack Chip v2.0");
        add(ModItems.ATTACKCHIP_TIER_3, "Attack Chip v3.0");
        add(ModItems.BREAKDOWNRATECHIP_TIER_1, "Breakdown Rate Chip v1.0");
        add(ModItems.BREAKDOWNRATECHIP_TIER_2, "Breakdown Rate Chip v2.0");
        add(ModItems.BREAKDOWNRATECHIP_TIER_3, "Breakdown Rate Chip v3.0");
        add(ModItems.ARMORCHIP_TIER_1, "Armor Chip v1.0");
        add(ModItems.ARMORCHIP_TIER_2, "Armor Chip v2.0");
        add(ModItems.ARMORCHIP_TIER_3, "Armor Chip v3.0");
        add(ModItems.TOUGHNESSCHIP_TIER_1, "Toughness Chip v1.0");
        add(ModItems.TOUGHNESSCHIP_TIER_2, "Toughness Chip v2.0");
        add(ModItems.TOUGHNESSCHIP_TIER_3, "Toughness Chip v3.0");
        add(ModItems.ATTACKSPEEDCHIP_TIER_1, "Attack Speed Chip v1.0");
        add(ModItems.ATTACKSPEEDCHIP_TIER_2, "Attack Speed Chip v2.0");
        add(ModItems.ATTACKSPEEDCHIP_TIER_3, "Attack Speed Chip v3.0");
        add(ModItems.REACTORCHIP_TIER_1, "Reactor Chip v1.0");
        add(ModItems.REACTORCHIP_TIER_2, "Reactor Chip v2.0");
        add(ModItems.REACTORCHIP_TIER_3, "Reactor Chip v3.0");
        add(ModItems.UNLOCKCHIP_SHOVEL, "Unlock Chip: Shovel");
        add(ModItems.UNLOCKCHIP_AXE, "Unlock Chip: Axe");
        add(ModItems.UNLOCKCHIP_SHEARS, "Unlock Chip: Shears");
        add(ModItems.UNLOCKCHIP_HOE, "Unlock Chip: Hoe");

        add(ModItems.PROTECTION_CHIP, "Protection Chip");
        add(ModItems.FIRE_PROTECTION_CHIP, "Fire Protection Chip");
        add(ModItems.FEATHER_FALLING_CHIP, "Feather Falling Chip");
        add(ModItems.BLAST_PROTECTION_CHIP, "Blast Protection Chip");
        add(ModItems.PROJECTILE_PROTECTION_CHIP, "Projectile Protection Chip");
        add(ModItems.RESPIRATION_CHIP, "Respiration Chip");
        add(ModItems.AQUA_AFFINITY_CHIP, "Aqua Affinity Chip");
        add(ModItems.THORNS_CHIP, "Thorns Chip");
        add(ModItems.DEPTH_STRIDER_CHIP, "Depth Strider Chip");
        add(ModItems.FROST_WALKER_CHIP, "Frost Walker Chip");
        add(ModItems.SHARPNESS_CHIP, "Sharpness Chip");
        add(ModItems.SMITE_CHIP, "Smite Chip");
        add(ModItems.BANE_OF_ARTHROPODS_CHIP, "Bane of Arthropods Chip");
        add(ModItems.KNOCKBACK_CHIP, "Knockback Chip");
        add(ModItems.FIRE_ASPECT_CHIP, "Fire Aspect Chip");
        add(ModItems.LOOTING_CHIP, "Looting Chip");
        add(ModItems.SWEEPING_CHIP, "Sweeping Chip");
        add(ModItems.EFFICIENCY_CHIP, "Efficiency Chip");
        add(ModItems.SILK_TOUCH_CHIP, "Silk Touch Chip");
        add(ModItems.UNBREAKING_CHIP, "Unbreaking Chip");
        add(ModItems.FORTUNE_CHIP, "Fortune Chip");
        add(ModItems.POWER_CHIP, "Power Chip");
        add(ModItems.PUNCH_CHIP, "Punch Chip");
        add(ModItems.FLAME_CHIP, "Flame Chip");
        add(ModItems.INFINITY_CHIP, "Infinity Chip");

        //        add(ModItems.CHESTPLATE_DANCIUM, "Dancium Chestplate");
        //        add(ModItems.BOOTS_DANCIUM, "Dancium Boots");
        //        add(ModItems.HELMET_DANCIUM, "Dancium Helmet");
        //        add(ModItems.LEGGINGS_DANCIUM, "Dancium Leggings");
        //        add(ModItems.CHESTPLATE_KANEKIUM, "Kanekium Chestplate");
        //        add(ModItems.BOOTS_KANEKIUM, "Kanekium Boots");
        //        add(ModItems.HELMET_KANEKIUM, "Kanekium Helmet");
        //        add(ModItems.LEGGINGS_KANEKIUM, "Kanekium Leggings");
        //        add(ModItems.CHESTPLATE_KINNOIUM, "Kinnoium Chestplate");
        //        add(ModItems.BOOTS_KINNOIUM, "Kinnoium Boots");
        //        add(ModItems.HELMET_KINNOIUM, "Kinnoium Helmet");
        //        add(ModItems.LEGGINGS_KINNOIUM, "Kinnoium Leggings");
        //        add(ModItems.CHESTPLATE_LENTHURIUM, "Lenthurium Chestplate");
        //        add(ModItems.BOOTS_LENTHURIUM, "Lenthurium Boots");
        //        add(ModItems.HELMET_LENTHURIUM, "Lenthurium Helmet");
        //        add(ModItems.LEGGINGS_LENTHURIUM, "Lenthurium Leggings");
    }

    private void addBlocks()
    {
        add(ModBlocks.WALL_LIGHT, "Wall Light");
        add(ModBlocks.BIOMASSFLUIDBLOCK, "Biomass Fluid");
        add(ModBlocks.CROPBARS, "Cropbars");
        add(ModBlocks.IDENTIFIER, "Identifier");
        add(ModBlocks.MEGAFURNACE, "Mega Furnace");
        add(ModBlocks.SEEDSQUEEZER, "Seed Squeezer");
        add(ModBlocks.SOLARGENERATOR, "Solar Generator");
        add(ModBlocks.PLANTFARM, "Void-Plantfarm");
        //            add(ModBlocks.CROP, "Crop");
        add(ModBlocks.PLANTIUM_BLOCK, "Plantium Block");
        add(ModBlocks.DANCIUM_BLOCK, "Dancium Block");
        add(ModBlocks.KANEKIUM_BLOCK, "Kanekium Block");
        add(ModBlocks.KINNOIUM_BLOCK, "Kinnoium Block");
        add(ModBlocks.LENTHURIUM_BLOCK, "Lenthurium Block");
        add(ModBlocks.UNIVERSAL_SOIL, "Experimental Soil Prototype");
        add(ModBlocks.UNIVERSAL_SOIL_INFUSED, "Biomass-Infused OmniSoil");
        add(ModBlocks.CABLE, "Cable");
        add(ModBlocks.DNA_EXTRACTOR, "DNA Extractor");
        add(ModBlocks.DNA_COMBINER, "DNA Combiner");
        add(ModBlocks.DNA_REMOVER, "DNA Remover");
        add(ModBlocks.DNA_CLEANER, "DNA Cleaner");
        add(ModBlocks.SEEDCONSTRUCTOR, "Seed Constructor");
        add(ModBlocks.COMPRESSOR, "Compressor");
        add(ModBlocks.ENERGYSTORAGE, "Energy Storage");
        add(ModBlocks.INFUSER, "Infuser");
        //        add(ModBlocks.MACHINESHELL, "Machine Shell"); TODO: verify
        //        add(ModBlocks.MACHINESHELL_INFUSED, "Infused Machine Shell"); TODO: verify
        //        add(ModBlocks.PLANTTOPIA_PORTAL, "PlantTopia Portal");
        add(ModBlocks.CHIPALYZER, "Chipalyzer");
        add(ModBlocks.MACHINEBULBREPROCESSOR, "Machinebulb Reprocessor");
        add(ModBlocks.MACHINESHELL_IRON, "Iron Machine Shell");
        add(ModBlocks.MACHINESHELL_PLANTIUM, "Plantium Machine Shell");
        add(ModBlocks.CARVER, "Carver Crop");
        add(ModBlocks.ENERGY_SUPPLIER, "Energy Supplier");

        add(ModBlocks.DARK_CRYSTAL_BLOCK, "Dark Crystal Block");
        add(ModBlocks.DARK_CRYSTAL_BRICK, "Dark Crystal Bricks");
        add(ModBlocks.DARK_CRYSTAL_TILING, "Dark Crystal Tiling");
        add(ModBlocks.DARK_CRYSTAL_LAMP, "Dark Crystal Lamp");
        add(ModBlocks.DARK_CRYSTAL_STAIRS, "Dark Crystal Stairs");
        add(ModBlocks.DARK_CRYSTAL_SLAB, "Dark Crystal Slab");
        add(ModBlocks.DARK_CRYSTAL_FENCE, "Dark Crystal Fence");
        add(ModBlocks.DARK_CRYSTAL_DOOR, "Dark Crystal Door");
        add(ModBlocks.DARK_CRYSTAL_GLASSPANE_END, "Dark Crystal Glasspane End");
        add(ModBlocks.DARK_CRYSTAL_GLASSPANE_MIDDLE, "Dark Crystal Glasspane Middle");
        add(ModBlocks.DARK_CRYSTAL_GLASSPANE_CROSS, "Dark Crystal Glasspane Cross");
        add(ModBlocks.DARK_CRYSTAL_ORE, "Dark Crystal Ore");

        add(ModBlocks.ELECTRIC_FENCE, "Electric Fence");
        add(ModBlocks.ELECTRIC_FENCE_TOP, "Electric Fence Top");
        add(ModBlocks.ELECTRIC_FENCE_GATE, "Electric Fence Gate");

        add(ModBlocks.WHITE_CRYSTAL_BLOCK, "White Crystal Block");
        add(ModBlocks.WHITE_CRYSTAL_BRICK, "White Crystal Bricks");
        add(ModBlocks.WHITE_CRYSTAL_TILING, "White Crystal Tiling");
        add(ModBlocks.WHITE_CRYSTAL_STAIRS, "White Crystal Stairs");
        add(ModBlocks.WHITE_CRYSTAL_SLAB, "White Crystal Slab");
        add(ModBlocks.WHITE_CRYSTAL_FENCE, "White Crystal Fence");
        add(ModBlocks.WHITE_CRYSTAL_DOOR, "White Crystal Door");
        add(ModBlocks.WHITE_CRYSTAL_GLASSPANE_END, "White Crystal Glasspane End");
        add(ModBlocks.WHITE_CRYSTAL_GLASSPANE_MIDDLE, "White Crystal Glasspane Middle");
        add(ModBlocks.WHITE_CRYSTAL_GLASSPANE_CROSS, "White Crystal Glasspane Cross");
        add(ModBlocks.WHITE_CRYSTAL_ORE, "White Crystal Ore");
        
        
    }

    public void addCrops()
    {
        add(ModItems.COLOR_PARTICLES, "Color Particles");
        Map<String, String> langKeys = new HashMap<>();
        // put manual overrides into langKeys

        Map<String, String> crops = defaultCrops();
        crops.forEach((k, v) -> langKeys.putIfAbsent("crop." + k, v));

        for (CropEntry entry : PlantTechMain.getCropList().values())
        {
            String name = entry.getName().intern();
            langKeys.putIfAbsent("item." + MODID + "." + name + "_seeds", crops.get(name) + " Seeds");
            if (entry.hasParticle())
            {
                langKeys.putIfAbsent("item." + MODID + "." + name + "_particles", crops.get(name) + " Particles");
            }
        }
        langKeys.forEach(this::add);
    }
    
    public void addHedges()
    {
    	String[] types = new String[] {"oak", "spruce", "birch", "jungle", "acacia", "dark_oak"}; 
    	String[] soils = new String[] {"dirt", "grass", "podzol"}; 
     	
        for(String type_1: types)
		{
			for(String type_2: types)
			{
				for(String soil: soils)
				{
					add("block." + PlantTechMain.MODID + ".hedge_" + type_1 + "_" + type_2 + "_" + soil, "Hedge");
				}
			}
		}
    }

//    private static String capitalize(String str)
//    {
//        return StringUtils.capitalize(str.toLowerCase());
//    }

    public void addGUIs()
    {
        add("container.megafurnace", "Mega Furnace");
        add("container.identifier", "Identifier");
        add("container.solargenerator", "Solar Generator");
        add("container.seedsqueezer", "Seed Squeezer");
        add("container.plantfarm", "Plantfarm");
        add("container.dnaextractor", "DNA Extractor");
        add("container.dnacombiner", "DNA Combiner");
        add("container.dnaremover", "DNA Remover");
        add("container.dnacleaner", "DNA Cleaner");
        add("container.seedconstructor", "Seed Constructor");
        add("container.compressor", "Compressor");
        add("container.energystorage", "Energy Storage");
        add("container.infuser", "Infuser");
        add("container.upgradeableitem", "Upgrade Item");
        add("container.chipalyzer", "Chipalyzer");
        add("container.planttopia_teleporter", "PlantTopia Teleporter");
        add("container.machinebulbreprocessor", "Machinebulb Reprocessor");
        add("container.energysupplier", "Energy Supplier");

        add("gui.soil", "Soil");
        add("gui.seeds", "Seeds");
        add("gui.drops", "Drops");
        add("gui.temperature", "Temperature");
        add("gui.parents", "Parents");
        add("gui.non_selected", "Nothing selected");
        add("gui.next", "Next");
        add("gui.last", "Last");
        add("gui.back", "Back");

        add("slot.chipalyzer.chipinput", "Insert empty chip");
        add("slot.chipalyzer.iteminput", "Insert item to implant in empty chip");
        add("slot.compressor.input", "Insert particle and select result");
        add("slot.compressor.select", "Select result after placing particle in input slot");
        add("slot.dnacleaner.input", "Insert DNA container you want to empty");
        add("slot.dnacombiner.container", "Insert DNA container with traits you want to combine");
        add("slot.dnacombiner.empty_container", "Insert empty DNA container");
        add("slot.dnaextractor.seeds", "Insert seed you want to extract traits from");
        add("slot.dnaextractor.empty_container", "Insert empty DNA container");
        add("slot.dnaremover.container", "Insert DNA container to remove a single trait");
        add("slot.identifier.input", "Insert seeds with unknown traits");
        add("slot.infuser.input", "Insert item/block you want to infuse with biomass");
        add("slot.megafurnace.input", "Insert item/block you want to smelt");
        add("slot.machinebulbreprocessor.input", "Seeds placed here will be reprocessed into a Machinebulb");
        add("slot.plantfarm.storage", "Drops of harvested crops will be inserted here");
        add("slot.seedconstructor.container", "Insert DNA container to construct a new seed with its traits");
        add("slot.seedsqueezer.input", "Insert PlantTech2 seeds that should be squeezed into biomass and energy");
        add("slot.seedsqueezer.squeeze", "No input, will automatically pull from input");
        add("slot.solargenerator.focus", "Insert focus to start energy production");
        add("slot.upgradeableitem.chipslot", "Insert chip to upgrade item");

        add("slot.util.speedupgrade", "Insert Speed Upgrade to speed up the process");
        add("slot.util.rangeupgrade", "Insert Range Upgrade to increase the production");
        add("slot.util.energystorageupgrade", "Insert Storage Upgrade to increase the energy storage");
        add("slot.util.squeezerupgrade", "Not implemented yet");
        add("slot.util.knowledgechip", "Insert Knowledge Chip to collect knowledge while machine is active (limited by the tier of the machine)");
        add("slot.util.output", "Output slot");
        add("slot.util.fluidin", "Insert biomass container to insert biomass");
        add("slot.util.fluidout", "Insert biomass container to extract biomass");
        add("slot.util.energyin", "Insert item with energy to charge this machine");
        add("slot.util.energyout", "Insert item with energy to charge the item");
    }

    public void addGuides()
    {
        addGuide("header", "PlantTech 2 Guide");
        addGuide("crossbreeding.menu", "Crossbreeding");
        addGuide("machines.menu", "Machines");
        addGuide("genetic_engineering.menu", "Genetic Engineering");

        addGuide("general_crossbreeding.header", "General");
        addGuide("getting_started.header", "Getting started");
        addGuide("crop_not_growing.header", "Crop is not growing?");
        addGuide("multiply_crops_seeds.header", "How to multiply crops/seeds");
        addGuide("how_to_crossbreed.header", "How to crossbreed");
        addGuide("autoreplanting.header", "Auto-replanting");
        addGuide("croptraits.header", "Crop traits");
        addGuide("identify_traits.header", "Identify traits");
        addGuide("fertilizer.header", "Fertilizer");
        addGuide("genetic_engineering_crops.header", "Genetic engineering crops");

        addGuide("general_genetic_engineering.header", "General");
        addGuide("extracting_genes.header", "Extracting genes");
        addGuide("purifying_genes.header", "Purifying genes");
        addGuide("combining_genes.header", "Combining genes");
        addGuide("cleaning_containers.header", "Cleaning containers");
        addGuide("creating_designerseeds.header", "Creating designer seeds");

        addGuide("general_machines.header", "General");
        addGuide("chipalyzer.header", "Chipalyzer");
        addGuide("compressor.header", "Compressor");
        addGuide("dna_cleaner.header", "DNA Cleaner");
        addGuide("dna_combiner.header", "DNA Combiner");
        addGuide("dna_extractor.header", "DNA Extractor");
        addGuide("dna_remover.header", "DNA Remover");
        addGuide("energy_supplier.header", "Energy Supplier");
        addGuide("identifier.header", "Identifier");
        addGuide("infuser.header", "Infuser");
        addGuide("machinebulb_reprocessor.header", "Machinebulb Reprocessor");
        addGuide("mega_furnace.header", "Mega Furnace");
        addGuide("void_plantfarm.header", "Void Plantfarm");
        addGuide("seedconstructor.header", "Seed Constructor");
        addGuide("seed_squeezer.header", "Seed Squeezer");
        addGuide("solar_generator.header", "Solar Generator");

        addGuide("general_crossbreeding.text",
                "Crossbreeding is the key for renewable resources. But before starting there is some important information: <br> <br> - PlantTech 2" +
                        " crops act different then vanilla crops. They share some behavior, but not all. <br> - The crops have traits that can be " +
                        "improved with crossbreeding, but most of the base traits are considerably worse than vanilla - however, if properly tended" +
                        " they can be much more efficient. <br> <br> Example: In the beginning plants will need a water source to be 1 block away, " +
                        "but through crossbreeding they can potentially thrive with water as far as 9 blocks away. <br> <br> Please refer to the " +
                        "'Crop traits' section of the Guide for detailed information. <br> <br> **Also be advised that Bonemeal does not work on " +
                        "PlantTech 2 crops (see Fertilizer)");

        addGuide("getting_started.text",
                "To start your career as a crossbreeder, you just need cropbars <br> (crafting: 4 sticks in 2x2 square) and a vanilla seed (or " +
                        "plantium nugget). Place the cropbars and right-click with the seeds on the cropbars, and if conditions are right the crop " +
                        "will grow. (If not, have a look at the next chapter.)");

        addGuide("crop_not_growing.text",
                "The easiest way to check, if the conditions are matched, is using the analyser. Just right-click on the crop and you will get a " +
                        "message with every condition that is not matched. <br>  The conditions are: <br> <br> - Water source near enough <br> - " +
                        "Light level high enough <br> - Right soil <br> - Right temperature");

        addGuide("multiply_crops_seeds.text",
                "When fully grown on a cropbar, plants will spread to an empty cropbar that is immediately adjacent. This is the easiest way to " +
                        "multiply your stock of seeds without increasing any traits, and if the original seed was identified the traits of the new " +
                        "plant will also be known (provided the result was not a crossbreed; see the next chapter). Once the Fertility trait " +
                        "increases, extra seeds may begin to drop when a crop is harvested.");

        addGuide("how_to_crossbreed.text",
                "When trying to get better traits - or new crop types - you need to crossbreed. This happens when a fully-grown plant spreads onto " +
                        "an empty cropbar that is next to another plant. The selection of the new traits/type is random with the following logic: " +
                        "<br> <br> Type: If the parent crops are different but have a matching crossbreed result, there is a small chance the new " +
                        "plant will be of that type. <br> <br> Traits: For each trait, the traits of the parents are compared. If they are equal, " +
                        "there is a small chance the trait will increased by one. Otherwise, the traits of the new crop will be in range of the " +
                        "parents' traits. <br> <br> The traits of a crossbred crop are always unknown and will need to be revealed with the " +
                        "Identifier machine.");

        addGuide("autoreplanting.text",
                "All crops in PlantTech 2 are auto-replanted; right-clicking to harvest the crops when fully grown will generate any drops and " +
                        "reset the plant to the seedling state. Until the Fertility trait is increased this will usually result in no extra seed " +
                        "drops, as a seed was needed for replanting.");

        addGuide("croptraits.text",
                " **Growth Rate** <br>     - Minimum level: 0 <br>    - Maximum level: 10 <br>     - Reduce the amount of time needed for the crop " +
                        "to grow. <br>    - Time per Stage = 90s - level * 6 <br> <br> **Sensitivity** <br> <br>    - Minimum level: 0 <br>    - " +
                        "Maximum level 10 <br>    - Not yet implemented. <br> <br>  **Light Sensitivity** <br> <br>    - Minimum level: 0 <br>    -" +
                        " Maximum level: 14 <br>    - Defines the minimum light level needed for the plant to grow. <br>    - Minimum needed " +
                        "lightlevel = 14 - level <br> <br>  **Water Sensitivity** <br> <br>    - Minimum level: 0 <br>    - Maximum level: 8 <br>  " +
                        "  - Defines the max distance from water that that the crop will be able to grow. <br>    - Distance = 1 + level <br> <br> " +
                        " **Temperature Tolerance** <br> <br>    - Minimum level: 0 <br>    - Maximum level: 4 <br>    - Allows the crop to grow in" +
                        " temperatures outside of its native climate. <br>    - Allowed temperature = default temperature +- level <br> <br> <br> " +
                        "<br> <br> <br> **Productivity** <br> <br>    - Minimum level: 0 <br>    - Maximum level: 5 <br>    - Increases the amount " +
                        "of products dropped <br> <br>  **Fertility** <br> <br>    - Minimum level: 0 <br>    - Maximum level: 5 <br>    - " +
                        "Increases the amount of seeds dropped <br> <br>  **Spreading Speed** <br> <br>    - Minimum level: 0 <br>    - Maximum " +
                        "level: 10 <br>    - Not yet implemented <br> <br> <br> **Gene Strength** <br> <br>    - Minimum level: 0 <br>    - Maximum" +
                        " level: 10 <br>    - Not yet implemented <br> <br>  **Energy Value** <br> <br>    - Minimum level: 1 <br>    - Maximum " +
                        "level: 10 <br>    - Increases the amount of energy produced in the seed squeezer <br>    - Energy = level * 20");

        addGuide("identify_traits.text",
                "The identifier is a machine that uses energy to identify the unknown traits of crops. <br> Just put the seeds with the unknown " +
                        "traits in the correct slot and the seeds will be identified in a bit. <br> For more information, have a look at " +
                        "\"Machines: Identifier.\"");

        addGuide("fertilizer.text",
                "Bonemeal does not work on PlantTech 2 crops. Instead, Fertilizer must be created by infusing Bonemeal with biomass (in the Infuser" +
                        " machine). There are 4 tiers of fertilizer, which may be upgraded in the Infuser as well. <br> <br> Fertilizer has a " +
                        "chance of advancing the growth of the crop it is used on by one stage, from 5% at Tier 1 to 75% at Tier 4.");

        addGuide("genetic_engineering_crops.text",
                "To make the life a bit easier, it is possible to extract genes of crops and make seeds with these genes. <br> This process is a " +
                        "bridge between the way of crossbreeding and the way of PlantTechian. <br> You find more information in the main category " +
                        "'Genetic Engineering'");

        addGuide("general_genetic_engineering.text",
                "Genetic engineering is a way to create crops with selected traits. These traits need to be found first but could be used as much " +
                        "as you want. So, start crossbreeding your crops and develop the traits you like to have.");

        addGuide("extracting_genes.text",
                "Once you got traits you want to keep, you need to extract the whole DNA sequence. <br> Therefore, put a seed with traits and an " +
                        "empty DNA container in the DNA extractor. You will obtain a DNA container with the whole DNA sequence; the seed will be " +
                        "destroyed in the process.");

        addGuide("purifying_genes.text",
                "There will be times when you want to remove a gene entirely from a DNA sequence - this can be accomplished by placing the DNA " +
                        "container into the DNA Remover machine. A single gene will be selected randomly and removed from the sequence - it's not " +
                        "possible to select which one, so don't mess around when you only have a single container left. <br> In this way you can " +
                        "isolate a single gene.");

        addGuide("combining_genes.text",
                "DNA containers can be combined in the DNA combiner. Through this process the DNA sequences will be mixed, with the lower values " +
                        "being dominant. (There is no dominant crop type however; that will be selected at random.) The used DNA containers will " +
                        "not be consumed, so this can be an effective way to backup a DNA sequence as you attempt to isolate a gene.");

        addGuide("cleaning_containers.text", "Useless DNA containers can be cleaned up for reuse, by placing them in the DNA cleaner.");

        addGuide("creating_designerseeds.text",
                "Now the last step in getting personalized seeds is to put a DNA container and 500 biomass in the Seed Constructor. <br> Biomass is" +
                        " produced by the Seed Squeezer - the same stuff used in the Machinebulb Reprocessor. Again, the used DNA container will " +
                        "not be consumed. <br> All traits that are not present in the DNA container will be reset to their initial value. The " +
                        "default crop type is Carrot, and for the traits have a look at \"Crossbreeding: Crop traits\"");

        addGuide("test.text", Strings.repeat("XX ", 675));

        addGuide("general_machines.text",
                "Machines in PlantTech 2 aren't just blocks that can be placed, they are complex symbioses of metal and plants. Therefore the " +
                        "process of obtaining the machines is more complex than you may be used to. <br> First of all the machines need a machine " +
                        "shell appropriate for the tier of the machine - lower tier machines need an iron machine shell, with higher tiers " +
                        "requiring a machine shell made from plantium. To obtain the machine shell, you need to place an Iron Block or Plantium " +
                        "Block next to a Carver Crop and wait. <br>  For the plant part you need the machinebulb of the desired machine. A " +
                        "Machinebulb Reprocessor is required for most of these, as well as a Seed Squeezer as a bioenergy source. (These two " +
                        "machinebulbs are the only ones that are obtainable by crafting.) When clicking on the correct machine shell with a bulb " +
                        "the machine will start growing and once finished, the machine is ready. <br> The Machinebulb Reprocessor requires a " +
                        "Knowledge Chip to access higher tiers of machinebulbs. This Chip collects information from machines while they're running," +
                        " as long as the machine is not a lower tier than the chip. (To start, a Knowledge Chip v0.1 may be used in a Seed " +
                        "Squeezer, a Tier 0 machine.) Once the Knowledge Chip has collected enough information, it may be used in the Machinebulb " +
                        "Reprocessor to get better machinebulbs. (And the Knowledge Chip will only gain useful data from the new Tier 1 machines.)");

        addGuide("chipalyzer.text",
                "The Chipalyzer can analyze various items and record the data on Blank Upgrade Chips. The resulting chips can be slotted into " +
                        "PlantTech2 equipment to enhance its capabilities. (Some effects are stackable; refer to each chip's tooltip for more info" +
                        ".) Some of these chips can also be upgraded; the following pages will have more on this. <br> <br> <br> <br> <br> <br> " +
                        "<br> <br> <br> <br> <br> <br> <br> <br> <br> These chips are upgradeable with standard Chip Upgrade Packs. <br> <br> " +
                        "Attack Chip: Iron Sword <br> Attack Speed Chip: Golden Sword <br> <br> Armor Chip: Iron Chestplate <br> Toughness Chip: " +
                        "Diamond Chestplate <br> <br> Breakdown Rate Chip: Golden Pickaxe <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> " +
                        "This set of chips is upgradeable with their own special Upgrade Packs. <br> <br> Capacity Chip: Battery (0.5kBE) <br> " +
                        "Reactor Chip: Solar Focus MkI <br> Harvestlevel Chip: Wooden Pickaxe <br> Harvest Level Chip: Diamond Pickaxe <br> <br> " +
                        "<br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> <br> The following chips are used to unlock additional right-click " +
                        "functions for the Multi-Tool. They are not upgradeable. <br> <br> Unlock Chip (Axe): Iron Axe <br> Unlock Chip (Hoe): Iron" +
                        " Hoe <br> Unlock Chip (Shears): Shears <br> Unlock Chip (Shovel): Iron Shovel <br> <br> <br> It is also possible to " +
                        "program Upgrade Chips with Enchantment data. These do not have multiple levels (so don't waste a Fortune III - you'll get " +
                        "a Fortune I chip), but the effects are stackable with multiple chips.");

        addGuide("compressor.text", "The Compressor converts crop particles into items. Some particles have more multiple options.");
        addGuide("dna_cleaner.text", "The DNA Cleaner sanitizes used DNA containers and prepares them for reuse.");
        addGuide("dna_combiner.text",
                "The DNA Combiner combines two sequences of DNA (lower values are dominant). The used sample sequences are not lost.");
        addGuide("dna_extractor.text",
                "The DNA Extractor stores the DNA sequence of a crop seed into an empty DNA container. This process destroys the seed.");
        addGuide("dna_remover.text",
                "The DNA Remover removes a single trait, selected randomly. Should be used to reduce the sequence to only good traits.");
        addGuide("energy_supplier.text", "The Energy Supplier does what the names says, it supplies energy for blocks like electric fences.");
        addGuide("identifier.text",
                "The Identifier is used to identify crop traits. It can also be used to convert vanilla seeds into PlantTech 2 seeds.");
        addGuide("infuser.text", "The Infuser infuses biomass into things like bonemeal to obtain fertilizer.");
        addGuide("machinebulb_reprocessor.text",
                "Takes energy and biomass to produce Machinebulbs. Available Machinebulbs are limited by the Knowledge Chip installed. Energy " +
                        "needed for each bulb: 1000.        Biomass needed: Compressor, Identifier, Energy Supplier -> 100; Infuser, Chipalyzer, " +
                        "Mega Furnace -> 1000; DNA Cleaner, DNA Combiner, DNA Extractor, DNA Remover, Seed Constructor, Void-Plantfarm, Solar " +
                        "Generator -> 2000;");
        addGuide("mega_furnace.text", "Powered with BE and has 6 slots for smelting.");
        addGuide("void_plantfarm.text",
                "You want to have cropproducts but don't want to have a farm consume your space? The void plantfarm takes a seed and will plant and" +
                        " farm the crops in the void dimension! It consumes energy to create a portal once it can harvest a crop and the production" +
                        " can be increased by putting a rangeupgrade in it! We can't access the dimension in person, but the machine can.");
        addGuide("seedconstructor.text",
                "The seedconstructor takes a sequence of dna, energy and biomass to creates seeds from it. The sequence will not be consumed. If a " +
                        "trait is not set in the sequence, it will be set to the default value.");
        addGuide("seed_squeezer.text",
                "The seed squeezer is a power generator fuelled by PlantTech2 seeds. The amount of power generated is determined by the seed's " +
                        "energy trait. Biomass is also produced, albeit at a fixed rate.");
        addGuide("solar_generator.text",
                "The solar generator needs a solar focus to produce energy. The higher the tier of the focus, the higher the production.");
    }

    private void addInfo()
    {

        addInfo("knowledge", "Knowledge");
        addInfo("tier", "Tier");

        addInfo("type", "type");
        addInfo("soil", "soil");
        addInfo("growspeed", "growth rate");
        addInfo("sensitivity", "sensitivity");
        addInfo("needed_lightlevel", "light level needed");
        addInfo("waterrange", "water range");
        addInfo("temperature", "temperature");
        addInfo("temperaturetolerance", "temperature tolerance");
        addInfo("productivity", "productivity");
        addInfo("fertility", "fertility");
        addInfo("spreedingspeed", "spreading speed");
        addInfo("genestrength", "gene strength");
        addInfo("energyvalue", "energy per seed");

        addInfo("unknown", "unknown");
        addInfo("only_creative", "Only for Creative Mode");
        addInfo("only_seeds", "Only for PlantTech 2 seeds");
        addInfo("no_space", "Not enough space in your inventory");
        addInfo("not_a_trait", "Not a trait");

        addInfo("fertilizer", "Chance");
        addInfo("fertilizer_creative", "Ignores requirements");

        addInfo("energy", "energy: %s");
        addInfo("energycosts", "energycosts per action: %s");
        addInfo("openwithshift", "Shift-rightclick to open inventory");

        addInfo("upgradechip.increasecapacity", "+%s energy capacity per chip");
        addInfo("upgradechip.increasecapacitymax", "Max energy capacity: %s");
        addInfo("upgradechip.energyproduction", "+%s energy production per chip");
        addInfo("upgradechip.increaseharvestlevel", "Max energy production: %s");
        addInfo("upgradechip.increasearmor", "+%s armor per chip");
        addInfo("upgradechip.increaseattack", "+%s attack per chip");
        addInfo("upgradechip.increaseattackmax", "Max attack: %s ");
        addInfo("upgradechip.increaseattackspeed", "+%s attack speed per chip");
        addInfo("upgradechip.increaseattackspeedmax", "Max attack speed: %s");
        addInfo("upgradechip.increasebreakdownrate", "+%s breakdown rate per chip");
        addInfo("upgradechip.increasebreakdownratemax", "Max breakdown rate: %s");
        addInfo("upgradechip.increasetougness", "+%s toughness per chip");
        addInfo("upgradechip.increasetougnessmax", "Max toughness: %s");
        addInfo("upgradechip.unlockaxefeat", "Unlocks axe features");
        addInfo("upgradechip.unlockshovelfeat", "Unlocks shovel features");
        addInfo("upgradechip.unlockshearsfeat", "Unlocks shears features");
        addInfo("upgradechip.unlockhoefeat", "Unlocks hoe features");
        addInfo("upgradechip.energycosts", "+%s energy costs per chip");
        addInfo("upgradechip.add", "Add");
        addInfo("upgradechip.stackable", "Enchantment can be stacked");

        addInfo("any_enchanted_item", "Any item with this enchantment. Level doesn`t matter");

        addInfo("wrench_cable", "Right-click cable connections to toggle");
        addInfo("wrench_dismantle", "Right-click while sneaking to dismantle PlantTech2 machines and cables");
    }

    public static Map<String, String> defaultCrops()
    {
        ImmutableMap.Builder<String, String> map = ImmutableMap.builder();

        map.put("abyssalnite", "Abyssalnite");
        map.put("adamantine", "Adamantine");
        map.put("allium", "Allium");
        map.put("aluminum", "Aluminum");
        map.put("aluminum_brass", "Aluminum Brass");
        map.put("alumite", "Alumite");
        map.put("amber", "Amber");
        map.put("apatite", "Apatite");
        map.put("aquamarine", "Aquamarine");
        map.put("ardite", "Ardite");
        map.put("awakened_draconium", "Awakened Draconium");
        map.put("azure_bluet", "Azure Bluet");
        map.put("bamboo", "Bamboo");
        map.put("basalt", "Basalt");
        map.put("beast", "Ravager");
        map.put("beetroots", "Beetroots");
        map.put("black_quartz", "Black Quartz");
        map.put("blaze", "Blaze");
        map.put("blitz", "Blitz");
        map.put("blizz", "Blizz");
        map.put("blue_topaz", "Blue Topaz");
        map.put("blue_orchid", "Blue Orchid");
        map.put("brass", "Brass");
        map.put("bronze", "Bronze");
        map.put("cactus", "Cactus");
        map.put("carrot", "Carrot");
        map.put("certus_quartz", "Certus Quartz");
        map.put("chicken", "Chicken");
        map.put("chimerite", "Chimerite");
        map.put("chorus", "Chorus");
        map.put("chrome", "Chrome");
        map.put("coal", "Coal");
        map.put("cobalt", "Cobalt");
        map.put("cocoa_bean", "Cocoa Bean");
        map.put("cold_iron", "Cold Iron");
        map.put("compressed_iron", "Compressed Iron");
        map.put("conductive_iron", "Conductive Iron");
        map.put("constantan", "Constantan");
        map.put("copper", "Copper");
        map.put("coralium", "Coralium");
        map.put("cornflower", "Cornflower");
        map.put("cow", "Cow");
        map.put("creeper", "Creeper");
        map.put("dancium", "Dancium");
        map.put("dandelion", "Dandelion");
        map.put("dark_gem", "Dark Gem");
        map.put("dark_steel", "Dark Steel");
        map.put("desh", "Desh");
        map.put("diamond", "Diamond");
        map.put("dirt", "Dirt");
        map.put("draconium", "Draconium");
        map.put("dreadium", "Dreadium");
        map.put("drowned", "Drowned");
        map.put("electrical_steel", "Electrical Steel");
        map.put("electrotine", "Electrotine");
        map.put("electrum", "Electrum");
        map.put("elementium", "Elementium");
        map.put("emerald", "Emerald");
        map.put("end_steel", "End Steel");
        map.put("ender_amethyst", "Ender Amethyst");
        map.put("ender_biotite", "Ender Biotite");
        map.put("enderdragon", "Enderdragon");
        map.put("enderium", "Enderium");
        map.put("enderman", "Enderman");
        map.put("endstone", "Endstone");
        map.put("energetic_alloy", "Energetic Alloy");
        map.put("fish", "Fish");
        map.put("fluix_crystal", "Fluix Crystal");
        map.put("fluxed_electrum", "Fluxed Electrum");
        map.put("ghast", "Ghast");
        map.put("glowstone", "Glowstone");
        map.put("glowstone_ingot", "Glowstone Ingot");
        map.put("gold", "Gold");
        map.put("graphite", "Graphite");
        map.put("guardian", "Guardian");
        map.put("husk", "Husk");
        map.put("illager", "Illager");
        map.put("invar", "Invar");
        map.put("iridium", "Iridium");
        map.put("iron", "Iron");
        map.put("kanekium", "Kanekium");
        map.put("kelp", "Kelp");
        map.put("kinnoium", "Kinnoium");
        map.put("knightslime", "Knightslime");
        map.put("lapis", "Lapis");
        map.put("lava", "Lava");
        map.put("lead", "Lead");
        map.put("lenthurium", "Lenthurium");
        map.put("lilly_of_the_valley", "Lily Of The Valley");
        map.put("lithium", "Lithium");
        map.put("lumium", "Lumium");
        map.put("magma_cube", "Magma Cube");
        map.put("magnesium", "Magnesium");
        map.put("malachite", "Malachite");
        map.put("manasteel", "Manasteel");
        map.put("manyullyn", "Manyullyn");
        map.put("melon", "Melon");
        map.put("meteoric_iron", "Meteoric Iron");
        map.put("mithril", "Mithril");
        map.put("moonstone", "Moonstone");
        map.put("mooshroom", "Mooshroom");
        map.put("mushroom", "Mushroom");
        map.put("mycelium", "Mycelium");
        map.put("nether_wart", "Nether Wart");
        map.put("netherrack", "Netherrack");
        map.put("neutronium", "Neutronium");
        map.put("nickel", "Nickel");
        map.put("octine", "Octine");
        map.put("orange_tulip", "Orange Tulip");
        map.put("osmium", "Osmium");
        map.put("oxeye_daisy", "Oxeye Daisy");
        map.put("panda", "Panda");
        map.put("parrot", "Parrot");
        map.put("peridot", "Peridot");
        map.put("pig", "Pig");
        map.put("pink_tulip", "Pink Tulip");
        map.put("plantium", "Plantium");
        map.put("platinum", "Platinum");
        map.put("polarbear", "Polarbear");
        map.put("poppy", "Poppy");
        map.put("potato", "Potato");
        map.put("prismarine", "Prismarine");
        map.put("pulsating_iron", "Pulsating Iron");
        map.put("pumpkin", "Pumpkin");
        map.put("quartz", "Quartz");
        map.put("quicksilver", "Quicksilver");
        map.put("red_tulip", "Red Tulip");
        map.put("redstone", "Redstone");
        map.put("redstone_alloy", "Redstone Alloy");
        map.put("refined_obsidian", "Refined Obsidian");
        map.put("rock_crystal", "Rock Crystal");
        map.put("rubber", "Rubber");
        map.put("ruby", "Ruby");
        map.put("saltpeter", "Saltpeter");
        map.put("sand", "Sand");
        map.put("sapphire", "Sapphire");
        map.put("sheep", "Sheep");
        map.put("shulker", "Shulker");
        map.put("signalum", "Signalum");
        map.put("silicon", "Silicon");
        map.put("silver", "Silver");
        map.put("skeleton", "Skeleton");
        map.put("sky_stone", "Sky Stone");
        map.put("slate", "Slate");
        map.put("slime", "Slime");
        map.put("slimy_bone", "Slimy Bone");
        map.put("snow", "Snow");
        map.put("soularium", "Soularium");
        map.put("soulsand", "Soulsand");
        map.put("spider", "Spider");
        map.put("sponge", "Sponge");
        map.put("squid", "Squid");
        map.put("star_steel", "Star Steel");
        map.put("starmetal", "Starmetal");
        map.put("steel", "Steel");
        map.put("stone", "Stone");
        map.put("stray", "Stray");
        map.put("sugarcane", "Sugarcane");
        map.put("sulfur", "Sulfur");
        map.put("sunstone", "Sunstone");
        map.put("syrmorite", "Syrmorite");
        map.put("tanzanite", "Tanzanite");
        map.put("terrasteel", "Terrasteel");
        map.put("thaumium", "Thaumium");
        map.put("tin", "Tin");
        map.put("titanium", "Titanium");
        map.put("topaz", "Topaz");
        map.put("tungsten", "Tungsten");
        map.put("turtle", "Turtle");
        map.put("uranium", "Uranium");
        map.put("valonite", "Valonite");
        map.put("vibrant_alloy", "Vibrant Alloy");
        map.put("villager", "Villager");
        map.put("vine", "Vine");
        map.put("vinteum", "Vinteum");
        map.put("void_metal", "Void Metal");
        map.put("water", "Water");
        map.put("wheat", "Wheat");
        map.put("white_tulip", "White Tulip");
        map.put("witch", "Witch");
        map.put("wither", "Wither");
        map.put("wither_rose", "Wither Rose");
        map.put("wither_skeleton", "Wither Skeleton");
        map.put("wood", "Wood");
        map.put("yellorium", "Yellorium");
        map.put("zinc", "Zinc");
        map.put("zombie", "Zombie");
        map.put("zombie_pigman", "Zombie Pigman");
        map.put("zombie_villager", "Zombie Villager");

        return map.build();
    }

    public void addInfo(String key, String value)
    {
        add("info." + key, value); // TODO: convert it all to have the modid
    }

    public void addGuide(String key, String value)
    {
        add("guide", key, value);
    }

    public void add(String category, String subKey, String value)
    {
        add(category + "." + MODID + (!subKey.isEmpty() ? "." + subKey : ""), value);
    }
}
