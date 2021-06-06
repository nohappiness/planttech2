package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.items.*;
import net.kaneka.planttech2.items.upgradeable.MultitoolItem;
import net.kaneka.planttech2.items.upgradeable.RangedWeaponItem;
import net.kaneka.planttech2.items.upgradeable.UpgradeChipItem;
import net.kaneka.planttech2.items.upgradeable.UpgradeableArmorItem;
import net.kaneka.planttech2.items.upgradeable.UpgradeableHandItem;
import net.kaneka.planttech2.crops.CropEntry;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import static net.kaneka.planttech2.PlantTechMain.MODID;
import static net.kaneka.planttech2.items.TierItem.ItemType.*;
import static net.kaneka.planttech2.utilities.ModCreativeTabs.*;

@ObjectHolder(MODID)
public class ModItems
{
    //	static ResourceLocation CROPS_RESLOC =new ResourceLocation(MODID, "crops/");

    public static List<Supplier<MachineBulbItem>> MACHINE_BULBS = new ArrayList<>();
    public static HashMap<String, Item> SEEDS = new HashMap<>();
    public static HashMap<String, Item> PARTICLES = new HashMap<>();

    @ObjectHolder("biomass_bucket") public static BucketItem BIOMASS_BUCKET;

    @ObjectHolder("analyser") public static AnalyserItem ANALYSER;
    @ObjectHolder("advanced_analyser") public static AdvancedAnalyserItem ADVANCED_ANALYSER;
    @ObjectHolder("biomass") public static Item BIOMASS;
    @ObjectHolder("biomasscontainer") public static BiomassContainerItem BIOMASSCONTAINER;
    @ObjectHolder("cropremover") public static CropRemover CROPREMOVER;
    @ObjectHolder("cyberbow") public static RangedWeaponItem CYBERBOW;
    @ObjectHolder("cyberdagger") public static UpgradeableHandItem CYBERDAGGER;
    @ObjectHolder("cyberkatana") public static UpgradeableHandItem CYBERKATANA;
    @ObjectHolder("cyberrapier") public static UpgradeableHandItem CYBERRAPIER;
    @ObjectHolder("capacityupgrade_1") public static TierItem CAPACITYUPGRADE_TIER_1;
    @ObjectHolder("capacityupgrade_2") public static TierItem CAPACITYUPGRADE_TIER_2;
    @ObjectHolder("capacityupgrade_3") public static TierItem CAPACITYUPGRADE_TIER_3;
    @ObjectHolder("color_particles") public static ParticleItem COLOR_PARTICLES;
    @ObjectHolder("dark_crystal") public static Item DARK_CRYSTAL;
    @ObjectHolder("dna_container_empty") public static Item DNA_CONTAINER_EMPTY;
    @ObjectHolder("dna_container") public static DNAContainerItem DNA_CONTAINER;
    @ObjectHolder("dancium_ingot") public static Item DANCIUM_INGOT;
    @ObjectHolder("dancium_nugget") public static Item DANCIUM_NUGGET;
    @ObjectHolder("empty_upgradechip_1") public static TierItem EMPTY_UPGRADECHIP_TIER_1;
    @ObjectHolder("empty_upgradechip_2") public static TierItem EMPTY_UPGRADECHIP_TIER_2;
    @ObjectHolder("empty_upgradechip_3") public static TierItem EMPTY_UPGRADECHIP_TIER_3;

    @ObjectHolder("chip_upgradepack_capacity_1") public static TierItem CHIP_UPGRADEPACK_CAPACITY_1;
    @ObjectHolder("chip_upgradepack_capacity_2") public static TierItem CHIP_UPGRADEPACK_CAPACITY_2;
    @ObjectHolder("chip_upgradepack_harvestlevel_1") public static TierItem CHIP_UPGRADEPACK_HARVESTLEVEL_1;
    @ObjectHolder("chip_upgradepack_harvestlevel_2") public static TierItem CHIP_UPGRADEPACK_HARVESTLEVEL_2;
    @ObjectHolder("chip_upgradepack_reactor_1") public static TierItem CHIP_UPGRADEPACK_REACTOR_1;
    @ObjectHolder("chip_upgradepack_reactor_2") public static TierItem CHIP_UPGRADEPACK_REACTOR_2;
    @ObjectHolder("energystorage_tier_1") public static EnergyStorageItem ENERGYSTORAGE_TIER_1;
    @ObjectHolder("energystorage_tier_2") public static EnergyStorageItem ENERGYSTORAGE_TIER_2;
    @ObjectHolder("energystorage_tier_3") public static EnergyStorageItem ENERGYSTORAGE_TIER_3;
    @ObjectHolder("fertilizer_tier_1") public static FertilizerItem FERTILIZER_TIER_1;
    @ObjectHolder("fertilizer_tier_2") public static FertilizerItem FERTILIZER_TIER_2;
    @ObjectHolder("fertilizer_tier_3") public static FertilizerItem FERTILIZER_TIER_3;
    @ObjectHolder("fertilizer_tier_4") public static FertilizerItem FERTILIZER_TIER_4;
    @ObjectHolder("fertilizer_creative") public static FertilizerItem FERTILIZER_CREATIVE;
    @ObjectHolder("gear_kanekium") public static Item GEAR_KANEKIUM;
    @ObjectHolder("gear_dancium") public static Item GEAR_DANCIUM;
    @ObjectHolder("gear_lenthurium") public static Item GEAR_LENTHURIUM;
    @ObjectHolder("gear_kinnoium") public static Item GEAR_KINNOIUM;
    @ObjectHolder("gear_plantium") public static Item GEAR_PLANTIUM;
    @ObjectHolder("gear_iron") public static Item GEAR_IRON;
    @ObjectHolder("gear_kanekium_infused") public static Item GEAR_KANEKIUM_INFUSED;
    @ObjectHolder("gear_dancium_infused") public static Item GEAR_DANCIUM_INFUSED;
    @ObjectHolder("gear_lenthurium_infused") public static Item GEAR_LENTHURIUM_INFUSED;
    @ObjectHolder("gear_kinnoium_infused") public static Item GEAR_KINNOIUM_INFUSED;
    @ObjectHolder("gear_plantium_infused") public static Item GEAR_PLANTIUM_INFUSED;
    @ObjectHolder("gear_iron_infused") public static Item GEAR_IRON_INFUSED;
    @ObjectHolder("redstone_infused") public static Item REDSTONE_INFUSED;
    @ObjectHolder("guide_overview") public static GuideItem GUIDE_OVERVIEW;
    @ObjectHolder("guide_plants") public static GuideItem GUIDE_PLANTS;
    @ObjectHolder("guide_genetic_engineering") public static GuideItem GUIDE_GENETIC_ENGINEERING;
    @ObjectHolder("kanekium_ingot") public static Item KANEKIUM_INGOT;
    @ObjectHolder("kanekium_nugget") public static Item KANEKIUM_NUGGET;
    @ObjectHolder("kinnoium_ingot") public static Item KINNOIUM_INGOT;
    @ObjectHolder("kinnoium_nugget") public static Item KINNOIUM_NUGGET;
    @ObjectHolder("knowledgechip_0") public static KnowledgeChip KNOWLEDGECHIP_TIER_0;
    @ObjectHolder("knowledgechip_1") public static KnowledgeChip KNOWLEDGECHIP_TIER_1;
    @ObjectHolder("knowledgechip_2") public static KnowledgeChip KNOWLEDGECHIP_TIER_2;
    @ObjectHolder("knowledgechip_3") public static KnowledgeChip KNOWLEDGECHIP_TIER_3;
    @ObjectHolder("knowledgechip_4") public static KnowledgeChip KNOWLEDGECHIP_TIER_4;
    @ObjectHolder("knowledgechip_5") public static KnowledgeChip KNOWLEDGECHIP_TIER_5;
    @ObjectHolder("lenthurium_ingot") public static Item LENTHURIUM_INGOT;
    @ObjectHolder("lenthurium_nugget") public static Item LENTHURIUM_NUGGET;
    @ObjectHolder("multitool") public static MultitoolItem MULTITOOL;
    @ObjectHolder("plantcard") public static CreditCardItem PLANTCARD;
    @ObjectHolder("plantium_ingot") public static Item PLANTIUM_INGOT;
    @ObjectHolder("plantium_nugget") public static Item PLANTIUM_NUGGET;
    @ObjectHolder("plant_obtainer") public static PlantObtainerItem PLANT_OBTAINER;
    @ObjectHolder("radiation_metre") public static RadiationMetreItem RADIATION_METRE;
    @ObjectHolder("rangeupgrade_1") public static TierItem RANGEUPGRADE_TIER_1;
    @ObjectHolder("rangeupgrade_2") public static TierItem RANGEUPGRADE_TIER_2;
    @ObjectHolder("rangeupgrade_3") public static TierItem RANGEUPGRADE_TIER_3;
    @ObjectHolder("rangeupgrade_4") public static TierItem RANGEUPGRADE_TIER_4;
    @ObjectHolder("solarfocus_1") public static TierItem SOLARFOCUS_TIER_1;
    @ObjectHolder("solarfocus_2") public static TierItem SOLARFOCUS_TIER_2;
    @ObjectHolder("solarfocus_3") public static TierItem SOLARFOCUS_TIER_3;
    @ObjectHolder("solarfocus_4") public static TierItem SOLARFOCUS_TIER_4;
    @ObjectHolder("speedupgrade_1") public static TierItem SPEEDUPGRADE_TIER_1;
    @ObjectHolder("speedupgrade_2") public static TierItem SPEEDUPGRADE_TIER_2;
    @ObjectHolder("speedupgrade_3") public static TierItem SPEEDUPGRADE_TIER_3;
    @ObjectHolder("speedupgrade_4") public static TierItem SPEEDUPGRADE_TIER_4;
    @ObjectHolder("teleporter") public static TeleporterItem TELEPORTER;
    @ObjectHolder("thermometer") public static ThermometerItem THERMOMETER;
    @ObjectHolder("white_crystal") public static Item WHITE_CRYSTAL;
    @ObjectHolder("wrench") public static WrenchItem WRENCH;
    @ObjectHolder("testitem") public static TestItem TESTITEM;

    @ObjectHolder("capacitychip_tier_1") public static UpgradeChipItem CAPACITYCHIP_TIER_1;
    @ObjectHolder("capacitychip_tier_2") public static UpgradeChipItem CAPACITYCHIP_TIER_2;
    @ObjectHolder("capacitychip_tier_3") public static UpgradeChipItem CAPACITYCHIP_TIER_3;
    @ObjectHolder("reactorchip_tier_1") public static UpgradeChipItem REACTORCHIP_TIER_1;
    @ObjectHolder("reactorchip_tier_2") public static UpgradeChipItem REACTORCHIP_TIER_2;
    @ObjectHolder("reactorchip_tier_3") public static UpgradeChipItem REACTORCHIP_TIER_3;
    @ObjectHolder("unlockchip_shovel") public static UpgradeChipItem UNLOCKCHIP_SHOVEL;
    @ObjectHolder("unlockchip_axe") public static UpgradeChipItem UNLOCKCHIP_AXE;
    @ObjectHolder("unlockchip_shears") public static UpgradeChipItem UNLOCKCHIP_SHEARS;
    @ObjectHolder("unlockchip_hoe") public static UpgradeChipItem UNLOCKCHIP_HOE;
    @ObjectHolder("harvestlevelchip_tier_1") public static UpgradeChipItem HARVESTLEVELCHIP_TIER_1;
    @ObjectHolder("harvestlevelchip_tier_2") public static UpgradeChipItem HARVESTLEVELCHIP_TIER_2;
    @ObjectHolder("harvestlevelchip_tier_3") public static UpgradeChipItem HARVESTLEVELCHIP_TIER_3;
    @ObjectHolder("attackchip_tier_1") public static UpgradeChipItem ATTACKCHIP_TIER_1;
    @ObjectHolder("attackchip_tier_2") public static UpgradeChipItem ATTACKCHIP_TIER_2;
    @ObjectHolder("attackchip_tier_3") public static UpgradeChipItem ATTACKCHIP_TIER_3;
    @ObjectHolder("attackspeedchip_tier_1") public static UpgradeChipItem ATTACKSPEEDCHIP_TIER_1;
    @ObjectHolder("attackspeedchip_tier_2") public static UpgradeChipItem ATTACKSPEEDCHIP_TIER_2;
    @ObjectHolder("attackspeedchip_tier_3") public static UpgradeChipItem ATTACKSPEEDCHIP_TIER_3;
    @ObjectHolder("breakdownratechip_tier_1") public static UpgradeChipItem BREAKDOWNRATECHIP_TIER_1;
    @ObjectHolder("breakdownratechip_tier_2") public static UpgradeChipItem BREAKDOWNRATECHIP_TIER_2;
    @ObjectHolder("breakdownratechip_tier_3") public static UpgradeChipItem BREAKDOWNRATECHIP_TIER_3;
    @ObjectHolder("armorchip_tier_1") public static UpgradeChipItem ARMORCHIP_TIER_1;
    @ObjectHolder("armorchip_tier_2") public static UpgradeChipItem ARMORCHIP_TIER_2;
    @ObjectHolder("armorchip_tier_3") public static UpgradeChipItem ARMORCHIP_TIER_3;
    @ObjectHolder("toughnesschip_tier_1") public static UpgradeChipItem TOUGHNESSCHIP_TIER_1;
    @ObjectHolder("toughnesschip_tier_2") public static UpgradeChipItem TOUGHNESSCHIP_TIER_2;
    @ObjectHolder("toughnesschip_tier_3") public static UpgradeChipItem TOUGHNESSCHIP_TIER_3;

    @ObjectHolder("protection_chip") public static UpgradeChipItem PROTECTION_CHIP;
    @ObjectHolder("fire_protection_chip") public static UpgradeChipItem FIRE_PROTECTION_CHIP;
    @ObjectHolder("feather_falling_chip") public static UpgradeChipItem FEATHER_FALLING_CHIP;
    @ObjectHolder("blast_protection_chip") public static UpgradeChipItem BLAST_PROTECTION_CHIP;
    @ObjectHolder("projectile_protection_chip") public static UpgradeChipItem PROJECTILE_PROTECTION_CHIP;
    @ObjectHolder("respiration_chip") public static UpgradeChipItem RESPIRATION_CHIP;
    @ObjectHolder("aqua_affinity_chip") public static UpgradeChipItem AQUA_AFFINITY_CHIP;
    @ObjectHolder("thorns_chip") public static UpgradeChipItem THORNS_CHIP;
    @ObjectHolder("depth_strider_chip") public static UpgradeChipItem DEPTH_STRIDER_CHIP;
    @ObjectHolder("frost_walker_chip") public static UpgradeChipItem FROST_WALKER_CHIP;
    @ObjectHolder("sharpness_chip") public static UpgradeChipItem SHARPNESS_CHIP;
    @ObjectHolder("smite_chip") public static UpgradeChipItem SMITE_CHIP;
    @ObjectHolder("bane_of_arthropods_chip") public static UpgradeChipItem BANE_OF_ARTHROPODS_CHIP;
    @ObjectHolder("knockback_chip") public static UpgradeChipItem KNOCKBACK_CHIP;
    @ObjectHolder("fire_aspect_chip") public static UpgradeChipItem FIRE_ASPECT_CHIP;
    @ObjectHolder("looting_chip") public static UpgradeChipItem LOOTING_CHIP;
    @ObjectHolder("sweeping_chip") public static UpgradeChipItem SWEEPING_CHIP;
    @ObjectHolder("efficiency_chip") public static UpgradeChipItem EFFICIENCY_CHIP;
    @ObjectHolder("silk_touch_chip") public static UpgradeChipItem SILK_TOUCH_CHIP;
    @ObjectHolder("unbreaking_chip") public static UpgradeChipItem UNBREAKING_CHIP;
    @ObjectHolder("fortune_chip") public static UpgradeChipItem FORTUNE_CHIP;
    @ObjectHolder("power_chip") public static UpgradeChipItem POWER_CHIP;
    @ObjectHolder("punch_chip") public static UpgradeChipItem PUNCH_CHIP;
    @ObjectHolder("flame_chip") public static UpgradeChipItem FLAME_CHIP;
    @ObjectHolder("infinity_chip") public static UpgradeChipItem INFINITY_CHIP;

//    @ObjectHolder("aura_core_temperature_extreme_cold") public static AuraCoreItem AURA_CHIP_TEMPERATURE_EXTREME_COLD;
//    @ObjectHolder("aura_core_temperature_cold") public static AuraCoreItem AURA_CHIP_TEMPERATURE_COLD;
//    @ObjectHolder("aura_core_temperature_normal") public static AuraCoreItem AURA_CHIP_TEMPERATURE_NORMAL;
//    @ObjectHolder("aura_core_temperature_warm") public static AuraCoreItem AURA_CHIP_TEMPERATURE_WARM;
//    @ObjectHolder("aura_core_temperature_extreme_warm") public static AuraCoreItem AURA_CHIP_TEMPERATURE_EXTREME_WARM;

	@ObjectHolder("aura_core_light_decrease_i") public static AuraCoreItem AURA_CORE_LIGHT_DECREASE_I;
	@ObjectHolder("aura_core_light_decrease_ii") public static AuraCoreItem AURA_CORE_LIGHT_DECREASE_II;
	@ObjectHolder("aura_core_light_decrease_iii") public static AuraCoreItem AURA_CORE_LIGHT_DECREASE_III;
	@ObjectHolder("aura_core_water_range_decrease_i") public static AuraCoreItem AURA_CORE_WATER_RANGE_DECREASE_I;
	@ObjectHolder("aura_core_water_range_decrease_ii") public static AuraCoreItem AURA_CORE_WATER_RANGE_DECREASE_II;
	@ObjectHolder("aura_core_water_range_decrease_iii") public static AuraCoreItem AURA_CORE_WATER_RANGE_DECREASE_III;
	@ObjectHolder("aura_core_temperature_extreme_cold") public static AuraCoreItem AURA_CORE_TEMPERATURE_EXTREME_COLD;
	@ObjectHolder("aura_core_temperature_cold") public static AuraCoreItem AURA_CORE_TEMPERATURE_COLD;
	@ObjectHolder("aura_core_temperature_normal") public static AuraCoreItem AURA_CORE_TEMPERATURE_NORMAL;
	@ObjectHolder("aura_core_temperature_warm") public static AuraCoreItem AURA_CORE_TEMPERATURE_WARM;
	@ObjectHolder("aura_core_temperature_extreme_warm") public static AuraCoreItem AURA_CORE_TEMPERATURE_EXTREME_WARM;
	@ObjectHolder("aura_core_fertility_increase_i") public static AuraCoreItem AURA_CORE_FERTILITY_INCREASE_I;
	@ObjectHolder("aura_core_fertility_increase_ii") public static AuraCoreItem AURA_CORE_FERTILITY_INCREASE_II;
	@ObjectHolder("aura_core_fertility_increase_iii") public static AuraCoreItem AURA_CORE_FERTILITY_INCREASE_III;
	@ObjectHolder("aura_core_productivity_increase_i") public static AuraCoreItem AURA_CORE_PRODUCTIVITY_INCREASE_I;
	@ObjectHolder("aura_core_productivity_increase_ii") public static AuraCoreItem AURA_CORE_PRODUCTIVITY_INCREASE_II;
	@ObjectHolder("aura_core_productivity_increase_iii") public static AuraCoreItem AURA_CORE_PRODUCTIVITY_INCREASE_III;

    @ObjectHolder("cyberarmor_helmet") public static UpgradeableArmorItem CYBERARMOR_HELMET;
    @ObjectHolder("cyberarmor_chest") public static UpgradeableArmorItem CYBERARMOR_CHEST;
    @ObjectHolder("cyberarmor_leggings") public static UpgradeableArmorItem CYBERARMOR_LEGGINGS;
    @ObjectHolder("cyberarmor_boots") public static UpgradeableArmorItem CYBERARMOR_BOOTS;

    @ObjectHolder("machinebulbreprocessor_bulb") public static MachineBulbItem MACHINEBULBREPROCESSOR_BULB;
    @ObjectHolder("seedsqueezer_bulb") public static MachineBulbItem SEEDSQUEEZER_BULB;
    @ObjectHolder("compressor_bulb") public static MachineBulbItem COMPRESSOR_BULB;
    @ObjectHolder("identifier_bulb") public static MachineBulbItem IDENTIFIER_BULB;
    @ObjectHolder("energy_supplier_bulb") public static MachineBulbItem ENERGY_SUPPLIER_BULB;
    @ObjectHolder("infuser_bulb") public static MachineBulbItem INFUSER_BULB;
    @ObjectHolder("chipalyzer_bulb") public static MachineBulbItem CHIPALYZER_BULB;
    @ObjectHolder("mega_furnace_bulb") public static MachineBulbItem MEGAFURNACE_BULB;
    @ObjectHolder("dna_cleaner_bulb") public static MachineBulbItem DNA_CLEANER_BULB;
    @ObjectHolder("dna_combiner_bulb") public static MachineBulbItem DNA_COMBINER_BULB;
    @ObjectHolder("dna_extractor_bulb") public static MachineBulbItem DNA_EXTRACTOR_BULB;
    @ObjectHolder("dna_remover_bulb") public static MachineBulbItem DNA_REMOVER_BULB;
    @ObjectHolder("seedconstructor_bulb") public static MachineBulbItem SEEDCONSTRUCTOR_BULB;
    @ObjectHolder("plantfarm_bulb") public static MachineBulbItem PLANTFARM_BULB;
    @ObjectHolder("solargenerator_bulb") public static MachineBulbItem SOLARGENERATOR_BULB;

    public static void register(IForgeRegistry<Item> r)
    {
        r.register(make("biomass_bucket", new BucketItem(() -> ModFluids.BIOMASS, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(TOOLS_AND_ARMOR))));

        r.register(make("analyser", new AnalyserItem()));
        r.register(make("advanced_analyser", new AdvancedAnalyserItem()));
        r.register(make("biomass", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("biomasscontainer", new BiomassContainerItem()));
        r.register(make("cropremover", new CropRemover()));
        r.register(make("cyberbow", new RangedWeaponItem(new Item.Properties().tab(TOOLS_AND_ARMOR), 1000, 10)));
        r.register(make("cyberdagger", new UpgradeableHandItem(new Item.Properties().tab(TOOLS_AND_ARMOR), 1000, 10, 1, -1.4F, UpgradeChipItem.MELEE_WEAPON)));
        r.register(make("cyberkatana", new UpgradeableHandItem(new Item.Properties().tab(TOOLS_AND_ARMOR), 1000, 10, 8, -3.4F, UpgradeChipItem.MELEE_WEAPON)));
        r.register(make("cyberrapier", new UpgradeableHandItem(new Item.Properties().tab(TOOLS_AND_ARMOR), 1000, 10, 4, -2.4F, UpgradeChipItem.MELEE_WEAPON)));
        r.register(make("capacityupgrade_1", new TierItem(new Item.Properties().tab(CHIPS), 1, CAPACITY_UPGRADE)));
        r.register(make("capacityupgrade_2", new TierItem(new Item.Properties().tab(CHIPS), 2, CAPACITY_UPGRADE)));
        r.register(make("capacityupgrade_3", new TierItem(new Item.Properties().tab(CHIPS), 3, CAPACITY_UPGRADE)));
        r.register(make("color_particles", new ParticleItem("color")));
        r.register(make("dark_crystal", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("dna_container_empty", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("dna_container", new DNAContainerItem()));
        r.register(make("dancium_ingot", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("dancium_nugget", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("empty_upgradechip_1", new TierItem(new Item.Properties().tab(MAIN), 1, UPGRADE_CHIP)));
        r.register(make("empty_upgradechip_2", new TierItem(new Item.Properties().tab(MAIN), 2, UPGRADE_CHIP)));
        r.register(make("empty_upgradechip_3", new TierItem(new Item.Properties().tab(MAIN), 3, UPGRADE_CHIP)));

        r.register(make("chip_upgradepack_capacity_1", new TierItem(new Item.Properties().tab(MAIN), 2, UPGRADE_CHIP)));
        r.register(make("chip_upgradepack_capacity_2", new TierItem(new Item.Properties().tab(MAIN), 3, UPGRADE_CHIP)));
        r.register(make("chip_upgradepack_harvestlevel_1", new TierItem(new Item.Properties().tab(MAIN), 2, UPGRADE_CHIP)));
        r.register(make("chip_upgradepack_harvestlevel_2", new TierItem(new Item.Properties().tab(MAIN), 3, UPGRADE_CHIP)));
        r.register(make("chip_upgradepack_reactor_1", new TierItem(new Item.Properties().tab(MAIN), 2, UPGRADE_CHIP)));
        r.register(make("chip_upgradepack_reactor_2", new TierItem(new Item.Properties().tab(MAIN), 3, UPGRADE_CHIP)));
        r.register(make("energystorage_tier_1", new EnergyStorageItem(new Item.Properties().tab(MAIN), 500)));
        r.register(make("energystorage_tier_2", new EnergyStorageItem(new Item.Properties().tab(MAIN), 5000)));
        r.register(make("energystorage_tier_3", new EnergyStorageItem(new Item.Properties().tab(MAIN), 50000)));
        r.register(make("fertilizer_tier_1", new FertilizerItem(MAIN)));
        r.register(make("fertilizer_tier_2", new FertilizerItem(MAIN)));
        r.register(make("fertilizer_tier_3", new FertilizerItem(MAIN)));
        r.register(make("fertilizer_tier_4", new FertilizerItem(MAIN)));
        r.register(make("fertilizer_creative", new FertilizerItem(MAIN)));
        r.register(make("gear_kanekium", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("gear_dancium", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("gear_lenthurium", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("gear_kinnoium", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("gear_plantium", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("gear_iron", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("gear_kanekium_infused", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("gear_dancium_infused", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("gear_lenthurium_infused", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("gear_kinnoium_infused", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("gear_plantium_infused", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("gear_iron_infused", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("redstone_infused", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("guide_overview", new GuideItem()));
        r.register(make("guide_plants", new GuideItem()));
        r.register(make("guide_genetic_engineering", new GuideItem()));
        r.register(make("kanekium_ingot", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("kanekium_nugget", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("kinnoium_ingot", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("kinnoium_nugget", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("knowledgechip_0", new KnowledgeChip(0, 50)));
        r.register(make("knowledgechip_1", new KnowledgeChip(1, 250)));
        r.register(make("knowledgechip_2", new KnowledgeChip(2, 1250)));
        r.register(make("knowledgechip_3", new KnowledgeChip(3, 6250)));
        r.register(make("knowledgechip_4", new KnowledgeChip(4, 31250)));
        r.register(make("knowledgechip_5", new KnowledgeChip(5, 156250)));
        r.register(make("lenthurium_ingot", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("lenthurium_nugget", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("multitool", new MultitoolItem()));
        r.register(make("plantcard", new CreditCardItem(new Item.Properties().tab(MAIN).stacksTo(1))));
        r.register(make("plantium_ingot", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("plantium_nugget", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("plant_obtainer", new PlantObtainerItem(new Item.Properties().tab(TOOLS_AND_ARMOR))));
        r.register(make("radiation_metre", new RadiationMetreItem(new Item.Properties().tab(TOOLS_AND_ARMOR))));
        r.register(make("rangeupgrade_1", new TierItem(new Item.Properties().tab(CHIPS), 1, RANGE_UPGRADE)));
        r.register(make("rangeupgrade_2", new TierItem(new Item.Properties().tab(CHIPS), 2, RANGE_UPGRADE)));
        r.register(make("rangeupgrade_3", new TierItem(new Item.Properties().tab(CHIPS), 3, RANGE_UPGRADE)));
        r.register(make("rangeupgrade_4", new TierItem(new Item.Properties().tab(CHIPS), 4, RANGE_UPGRADE)));
        r.register(make("solarfocus_1", new TierItem(new Item.Properties().tab(CHIPS), 1, SOLAR_FOCUS)));
        r.register(make("solarfocus_2", new TierItem(new Item.Properties().tab(CHIPS), 2, SOLAR_FOCUS)));
        r.register(make("solarfocus_3", new TierItem(new Item.Properties().tab(CHIPS), 3, SOLAR_FOCUS)));
        r.register(make("solarfocus_4", new TierItem(new Item.Properties().tab(CHIPS), 4, SOLAR_FOCUS)));
        r.register(make("speedupgrade_1", new TierItem(new Item.Properties().tab(CHIPS), 1, SPEED_UPGRADE)));
        r.register(make("speedupgrade_2", new TierItem(new Item.Properties().tab(CHIPS), 2, SPEED_UPGRADE)));
        r.register(make("speedupgrade_3", new TierItem(new Item.Properties().tab(CHIPS), 3, SPEED_UPGRADE)));
        r.register(make("speedupgrade_4", new TierItem(new Item.Properties().tab(CHIPS), 4, SPEED_UPGRADE)));
        r.register(make("teleporter", new TeleporterItem(new Item.Properties().tab(MAIN), 1000)));
        r.register(make("thermometer", new ThermometerItem()));
        r.register(make("white_crystal", new Item(new Item.Properties().tab(MAIN))));
        r.register(make("wrench", new WrenchItem()));
        r.register(make("testitem", new TestItem()));

        r.register(make("capacitychip_tier_1", new UpgradeChipItem("capacitychip_tier_1").setIncreaseCapacity(2000).setEnergyCost(1)));
        r.register(make("capacitychip_tier_2", new UpgradeChipItem("capacitychip_tier_2").setIncreaseCapacity(5000).setEnergyCost(2)));
        r.register(make("capacitychip_tier_3", new UpgradeChipItem("capacitychip_tier_3").setIncreaseCapacity(10000).setEnergyCost(5)));
        r.register(make("reactorchip_tier_1", new UpgradeChipItem("reactorchip_tier_1").setEnergyProduction(1).setEnergyCost(1)));
        r.register(make("reactorchip_tier_2", new UpgradeChipItem("reactorchip_tier_2").setEnergyProduction(3).setEnergyCost(2)));
        r.register(make("reactorchip_tier_3", new UpgradeChipItem("reactorchip_tier_3").setEnergyProduction(5).setEnergyCost(5)));
        r.register(make("unlockchip_shovel", new UpgradeChipItem("unlockchip_shovel").setUnlockShovelFeat().setEnergyCost(2)));
        r.register(make("unlockchip_axe", new UpgradeChipItem("unlockchip_axe").setUnlockAxeFeat().setEnergyCost(2)));
        r.register(make("unlockchip_shears", new UpgradeChipItem("unlockchip_shears").setUnlockShearsFeat().setEnergyCost(2)));
        r.register(make("unlockchip_hoe", new UpgradeChipItem("unlockchip_hoe").setUnlockHoeFeat().setEnergyCost(2)));
        r.register(make("harvestlevelchip_tier_1", new UpgradeChipItem("harvestlevelchip_tier_1").setIncreaseHarvestlevel(1).setEnergyCost(2)));
        r.register(make("harvestlevelchip_tier_2", new UpgradeChipItem("harvestlevelchip_tier_2").setIncreaseHarvestlevel(2).setEnergyCost(6)));
        r.register(make("harvestlevelchip_tier_3", new UpgradeChipItem("harvestlevelchip_tier_3").setIncreaseHarvestlevel(4).setEnergyCost(15)));
        r.register(make("attackchip_tier_1", new UpgradeChipItem("attackchip_tier_1").setIncreaseAttack(0.5F).setEnergyCost(1)));
        r.register(make("attackchip_tier_2", new UpgradeChipItem("attackchip_tier_2").setIncreaseAttack(1F).setEnergyCost(2)));
        r.register(make("attackchip_tier_3", new UpgradeChipItem("attackchip_tier_3").setIncreaseAttack(2F).setEnergyCost(4)));
        r.register(make("attackspeedchip_tier_1", new UpgradeChipItem("attackspeedchip_tier_1").setIncreaseAttackSpeed(0.1F).setEnergyCost(1)));
        r.register(make("attackspeedchip_tier_2", new UpgradeChipItem("attackspeedchip_tier_2").setIncreaseAttackSpeed(0.25F).setEnergyCost(2)));
        r.register(make("attackspeedchip_tier_3", new UpgradeChipItem("attackspeedchip_tier_3").setIncreaseAttackSpeed(0.5F).setEnergyCost(4)));
        r.register(make("breakdownratechip_tier_1", new UpgradeChipItem("breakdownratechip_tier_1").setIncreaseBreakdownRate(0.5F).setEnergyCost(1)));
        r.register(make("breakdownratechip_tier_2", new UpgradeChipItem("breakdownratechip_tier_2").setIncreaseBreakdownRate(1F).setEnergyCost(3)));
        r.register(make("breakdownratechip_tier_3", new UpgradeChipItem("breakdownratechip_tier_3").setIncreaseBreakdownRate(2.5F).setEnergyCost(8)));
        r.register(make("armorchip_tier_1", new UpgradeChipItem("armorchip_tier_1").setIncreaseArmor(1).setEnergyCost(1)));
        r.register(make("armorchip_tier_2", new UpgradeChipItem("armorchip_tier_2").setIncreaseArmor(2).setEnergyCost(3)));
        r.register(make("armorchip_tier_3", new UpgradeChipItem("armorchip_tier_3").setIncreaseArmor(4).setEnergyCost(7)));
        r.register(make("toughnesschip_tier_1", new UpgradeChipItem("toughnesschip_tier_1").setIncreaseToughness(0.5F).setEnergyCost(1)));
        r.register(make("toughnesschip_tier_2", new UpgradeChipItem("toughnesschip_tier_2").setIncreaseToughness(1F).setEnergyCost(3)));
        r.register(make("toughnesschip_tier_3", new UpgradeChipItem("toughnesschip_tier_3").setIncreaseToughness(2F).setEnergyCost(7)));

        r.register(make("protection_chip", new UpgradeChipItem("protection_chip").setEnchantment(Enchantments.ALL_DAMAGE_PROTECTION).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET, UpgradeChipItem.CHEST, UpgradeChipItem.LEGGINGS, UpgradeChipItem.BOOTS)));
        r.register(make("fire_protection_chip", new UpgradeChipItem("fire_protection_chip").setEnchantment(Enchantments.FIRE_PROTECTION).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET, UpgradeChipItem.CHEST, UpgradeChipItem.LEGGINGS, UpgradeChipItem.BOOTS)));
        r.register(make("feather_falling_chip", new UpgradeChipItem("feather_falling_chip").setEnchantment(Enchantments.FALL_PROTECTION).setEnergyCost(5).addRestriction(UpgradeChipItem.BOOTS)));
        r.register(make("blast_protection_chip", new UpgradeChipItem("blast_protection_chip").setEnchantment(Enchantments.BLAST_PROTECTION).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET, UpgradeChipItem.CHEST, UpgradeChipItem.LEGGINGS, UpgradeChipItem.BOOTS)));
        r.register(make("projectile_protection_chip", new UpgradeChipItem("projectile_protection_chip").setEnchantment(Enchantments.PROJECTILE_PROTECTION).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET, UpgradeChipItem.CHEST, UpgradeChipItem.LEGGINGS, UpgradeChipItem.BOOTS)));
        r.register(make("respiration_chip", new UpgradeChipItem("respiration_chip").setEnchantment(Enchantments.RESPIRATION).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET)));
        r.register(make("aqua_affinity_chip", new UpgradeChipItem("aqua_affinity_chip").setEnchantment(Enchantments.AQUA_AFFINITY).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET)));
        r.register(make("thorns_chip", new UpgradeChipItem("thorns_chip").setEnchantment(Enchantments.THORNS).setEnergyCost(5).addRestriction(UpgradeChipItem.CHEST)));
        r.register(make("depth_strider_chip", new UpgradeChipItem("depth_strider_chip").setEnchantment(Enchantments.DEPTH_STRIDER).setEnergyCost(5).addRestriction(UpgradeChipItem.BOOTS)));
        r.register(make("frost_walker_chip", new UpgradeChipItem("frost_walker_chip").setEnchantment(Enchantments.FROST_WALKER).setEnergyCost(5).addRestriction(UpgradeChipItem.BOOTS)));
        r.register(make("sharpness_chip", new UpgradeChipItem("sharpness_chip").setEnchantment(Enchantments.SHARPNESS).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON)));
        r.register(make("smite_chip", new UpgradeChipItem("smite_chip").setEnchantment(Enchantments.SMITE).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON)));
        r.register(make("bane_of_arthropods_chip", new UpgradeChipItem("bane_of_arthropods_chip").setEnchantment(Enchantments.BANE_OF_ARTHROPODS).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON)));
        r.register(make("knockback_chip", new UpgradeChipItem("knockback_chip").setEnchantment(Enchantments.KNOCKBACK).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON)));
        r.register(make("fire_aspect_chip", new UpgradeChipItem("fire_aspect_chip").setEnchantment(Enchantments.FIRE_ASPECT).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON)));
        r.register(make("looting_chip", new UpgradeChipItem("looting_chip").setEnchantment(Enchantments.MOB_LOOTING).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON)));
        r.register(make("sweeping_chip", new UpgradeChipItem("sweeping_chip").setEnchantment(Enchantments.SWEEPING_EDGE).setEnergyCost(5).addRestriction(UpgradeChipItem.MELEE_WEAPON)));
        r.register(make("efficiency_chip", new UpgradeChipItem("efficiency_chip").setEnchantment(Enchantments.BLOCK_EFFICIENCY).setEnergyCost(5).addRestriction(UpgradeChipItem.TOOL)));
        r.register(make("silk_touch_chip", new UpgradeChipItem("silk_touch_chip").setEnchantment(Enchantments.SILK_TOUCH).setEnergyCost(5).addRestriction(UpgradeChipItem.TOOL)));
        r.register(make("unbreaking_chip", new UpgradeChipItem("unbreaking_chip").setEnchantment(Enchantments.UNBREAKING).setEnergyCost(5).addRestriction(UpgradeChipItem.HELMET, UpgradeChipItem.CHEST, UpgradeChipItem.LEGGINGS, UpgradeChipItem.BOOTS)));
        r.register(make("fortune_chip", new UpgradeChipItem("fortune_chip").setEnchantment(Enchantments.BLOCK_FORTUNE).setEnergyCost(5).addRestriction(UpgradeChipItem.TOOL)));
        r.register(make("power_chip", new UpgradeChipItem("power_chip").setEnchantment(Enchantments.POWER_ARROWS).setEnergyCost(5).addRestriction(UpgradeChipItem.RANGED_WEAPON)));
        r.register(make("punch_chip", new UpgradeChipItem("punch_chip").setEnchantment(Enchantments.PUNCH_ARROWS).setEnergyCost(5).addRestriction(UpgradeChipItem.RANGED_WEAPON)));
        r.register(make("flame_chip", new UpgradeChipItem("flame_chip").setEnchantment(Enchantments.FLAMING_ARROWS).setEnergyCost(5).addRestriction(UpgradeChipItem.RANGED_WEAPON)));
        r.register(make("infinity_chip", new UpgradeChipItem("infinity_chip").setEnchantment(Enchantments.INFINITY_ARROWS).setEnergyCost(20).addRestriction(UpgradeChipItem.RANGED_WEAPON)));

        r.register(make("aura_core_light_decrease_i", new AuraCoreItem(new AuraCoreItem.Builder(1).setLightValueDecrease(2))));
        r.register(make("aura_core_light_decrease_ii", new AuraCoreItem(new AuraCoreItem.Builder(2).setLightValueDecrease(4))));
        r.register(make("aura_core_light_decrease_iii", new AuraCoreItem(new AuraCoreItem.Builder(3).setLightValueDecrease(6))));
        r.register(make("aura_core_water_range_decrease_i", new AuraCoreItem(new AuraCoreItem.Builder(1).setWaterRangeDecrease(2))));
        r.register(make("aura_core_water_range_decrease_ii", new AuraCoreItem(new AuraCoreItem.Builder(2).setWaterRangeDecrease(4))));
        r.register(make("aura_core_water_range_decrease_iii", new AuraCoreItem(new AuraCoreItem.Builder(3).setWaterRangeDecrease(6))));
        r.register(make("aura_core_temperature_extreme_cold", new AuraCoreItem(new AuraCoreItem.Builder(1).setTemperature(EnumTemperature.EXTREME_COLD))));
        r.register(make("aura_core_temperature_cold", new AuraCoreItem(new AuraCoreItem.Builder(1).setTemperature(EnumTemperature.COLD))));
        r.register(make("aura_core_temperature_normal", new AuraCoreItem(new AuraCoreItem.Builder(1).setTemperature(EnumTemperature.NORMAL))));
        r.register(make("aura_core_temperature_warm", new AuraCoreItem(new AuraCoreItem.Builder(1).setTemperature(EnumTemperature.WARM))));
        r.register(make("aura_core_temperature_extreme_warm", new AuraCoreItem(new AuraCoreItem.Builder(1).setTemperature(EnumTemperature.EXTREME_WARM))));
        r.register(make("aura_core_fertility_increase_i", new AuraCoreItem(new AuraCoreItem.Builder(1).setIncreaseFertility(1))));
        r.register(make("aura_core_fertility_increase_ii", new AuraCoreItem(new AuraCoreItem.Builder(2).setIncreaseFertility(2))));
        r.register(make("aura_core_fertility_increase_iii", new AuraCoreItem(new AuraCoreItem.Builder(3).setIncreaseFertility(3))));
        r.register(make("aura_core_productivity_increase_i", new AuraCoreItem(new AuraCoreItem.Builder(1).setIncreaseProductivity(1))));
        r.register(make("aura_core_productivity_increase_ii", new AuraCoreItem(new AuraCoreItem.Builder(2).setIncreaseProductivity(2))));
        r.register(make("aura_core_productivity_increase_iii", new AuraCoreItem(new AuraCoreItem.Builder(3).setIncreaseProductivity(3))));

        r.register(make("cyberarmor_helmet", new UpgradeableArmorItem("cyberarmor", EquipmentSlotType.HEAD, 1000, 10, 1, 0, UpgradeChipItem.HELMET)));
        r.register(make("cyberarmor_chest", new UpgradeableArmorItem("cyberarmor", EquipmentSlotType.CHEST, 1000, 10, 3, 0, UpgradeChipItem.CHEST)));
        r.register(make("cyberarmor_leggings", new UpgradeableArmorItem("cyberarmor", EquipmentSlotType.LEGS, 1000, 10, 2, 0, UpgradeChipItem.LEGGINGS)));
        r.register(make("cyberarmor_boots", new UpgradeableArmorItem("cyberarmor", EquipmentSlotType.FEET, 1000, 10, 1, 0, UpgradeChipItem.BOOTS)));

        r.register(makeBulb("machinebulbreprocessor_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_IRON, () -> ModBlocks.MACHINEBULBREPROCESSOR_GROWING, PlantTechConstants.MACHINETIER_MACHINEBULBREPROCESSOR, 0)));
        r.register(makeBulb("seedsqueezer_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_IRON, () -> ModBlocks.SEEDSQUEEZER_GROWING, PlantTechConstants.MACHINETIER_SEEDSQUEEZER, 0)));
        r.register(makeBulb("compressor_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_IRON, () -> ModBlocks.COMPRESSOR_GROWING, PlantTechConstants.MACHINETIER_COMPRESSOR, 100)));
        r.register(makeBulb("identifier_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_IRON, () -> ModBlocks.IDENTIFIER_GROWING, PlantTechConstants.MACHINETIER_IDENTIFIER, 100)));
        r.register(makeBulb("energy_supplier_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_IRON, () -> ModBlocks.ENERGY_SUPPLIER_GROWING, PlantTechConstants.MACHINETIER_ENERGY_SUPPLIER, 100)));
        r.register(makeBulb("infuser_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_IRON, () -> ModBlocks.INFUSER_GROWING, PlantTechConstants.MACHINETIER_INFUSER, 1000)));
        r.register(makeBulb("chipalyzer_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_PLANTIUM, () -> ModBlocks.CHIPALYZER_GROWING, PlantTechConstants.MACHINETIER_CHIPALYZER, 1000)));
        r.register(makeBulb("mega_furnace_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_PLANTIUM, () -> ModBlocks.MEGAFURNACE_GROWING, PlantTechConstants.MACHINETIER_MEGAFURNACE, 1000)));
        r.register(makeBulb("dna_cleaner_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_PLANTIUM, () -> ModBlocks.DNA_CLEANER_GROWING, PlantTechConstants.MACHINETIER_DNA_CLEANER, 2000)));
        r.register(makeBulb("dna_combiner_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_PLANTIUM, () -> ModBlocks.DNA_COMBINER_GROWING, PlantTechConstants.MACHINETIER_DNA_COMBINER, 2000)));
        r.register(makeBulb("dna_extractor_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_PLANTIUM, () -> ModBlocks.DNA_EXTRACTOR_GROWING, PlantTechConstants.MACHINETIER_DNA_EXTRACTOR, 2000)));
        r.register(makeBulb("dna_remover_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_PLANTIUM, () -> ModBlocks.DNA_REMOVER_GROWING, PlantTechConstants.MACHINETIER_DNA_REMOVER, 2000)));
        r.register(makeBulb("seedconstructor_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_PLANTIUM, () -> ModBlocks.SEEDCONSTRUCTOR_GROWING, PlantTechConstants.MACHINETIER_SEEDCONSTRUCTOR, 2000)));
        r.register(makeBulb("plantfarm_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_PLANTIUM, () -> ModBlocks.PLANTFARM_GROWING, PlantTechConstants.MACHINETIER_PLANTFARM, 2000)));
        r.register(makeBulb("solargenerator_bulb", new MachineBulbItem(() -> ModBlocks.MACHINESHELL_PLANTIUM, () -> ModBlocks.SOLARGENERATOR_GROWING, PlantTechConstants.MACHINETIER_SOLARGENERATOR, 2000)));

        Item tempseed, tempparticle;
        String name;
        for (CropEntry entry : PlantTechMain.getCropList().values())
        {
            name = entry.getName();
            tempseed = new CropSeedItem(name);
            SEEDS.put(name, tempseed);
            tempseed.setRegistryName(name + "_seeds");
            r.register(tempseed);
            if (entry.hasParticle())
            {
                tempparticle = new ParticleItem(name);
                tempparticle.setRegistryName(name + "_particles");
                PARTICLES.put(name, tempparticle);
                r.register(tempparticle);
            }
        }
    }

    static <I extends Item> I make(String registryName, I item)
    {
        item.setRegistryName(registryName);
        return item;
    }

    static <B extends MachineBulbItem> B makeBulb(String registryName, B bulbItem)
    {
        make(registryName, bulbItem);
        MACHINE_BULBS.add(() -> bulbItem);
        return bulbItem;
    }
}
