package net.kaneka.planttech2.datagen;

import net.kaneka.planttech2.PlantTechClient;
import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.Objects;

import static net.kaneka.planttech2.registries.ModItems.*;

public class ItemModels extends ItemModelProvider
{
	protected final ResourceLocation generated = mcLoc("generated");
	protected final ResourceLocation handheld = mcLoc("handheld");

	public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper)
	{
		super(generator, PlantTechMain.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		addCrops();
		addBulbs();
		addBiomassContainer();
		addChipsUpgrades();
		addAuraCores();

		singleTexturedItem(ADVANCED_ANALYSER);
		singleTexturedItem(ANALYSER);
		singleTexturedItem(BIOMASS);
		singleTexturedItem(BIOMASS_BUCKET);
		singleTexture("cable", generated, "layer0", itemPrefix("cable"));
		singleTexturedItem(COLOR_PARTICLES);
		// TODO: crop? crop_seeds?
		singleTexturedItem(CROPREMOVER);
		singleTexturedItem(CYBERARMOR_BOOTS, "armor/cyberarmor_boots");
		singleTexturedItem(CYBERARMOR_CHEST, "armor/cyberarmor_chest");
		singleTexturedItem(CYBERARMOR_HELMET, "armor/cyberarmor_helmet");
		singleTexturedItem(CYBERARMOR_LEGGINGS, "armor/cyberarmor_leggings");
		// TODO: cyberbow
		singleTexturedItem(CYBERDAGGER, handheld, "weapons/cyberdagger");
		singleTexturedItem(CYBERKATANA, handheld, "weapons/cyberkatana");
		singleTexturedItem(CYBERRAPIER, handheld, "weapons/cyberrapier");
		singleTexturedItem(DANCIUM_INGOT);
		singleTexturedItem(DANCIUM_NUGGET);
		singleTexturedItem(DARK_CRYSTAL);
		singleTexturedItem(ENERGYSTORAGE_TIER_1);
		singleTexturedItem(ENERGYSTORAGE_TIER_2);
		singleTexturedItem(ENERGYSTORAGE_TIER_3);
		singleTexturedItem(FERTILIZER_TIER_1, "fert_1");
		singleTexturedItem(FERTILIZER_TIER_2, "fert_2");
		singleTexturedItem(FERTILIZER_TIER_3, "fert_3");
		singleTexturedItem(FERTILIZER_TIER_4, "fert_4");
		singleTexturedItem(FERTILIZER_CREATIVE, "fert_creative");
		// TODO: dna_container ; gears ; guides
		// TODO: more kanekium
		singleTexturedItem(KANEKIUM_INGOT);
		singleTexturedItem(KANEKIUM_NUGGET);
		singleTexturedItem(KINNOIUM_INGOT);
		singleTexturedItem(KINNOIUM_NUGGET);
		singleTexturedItem(LENTHURIUM_INGOT);
		singleTexturedItem(LENTHURIUM_NUGGET);
		// TODO: multitool ; mutated plants
		// TODO: particles?
		// TODO: plant obtainer
		singleTexturedItem(PLANTCARD);
		singleTexturedItem(PLANTIUM_INGOT);
		singleTexturedItem(PLANTIUM_NUGGET);
		// TODO: radiation_metre
		singleTexturedItem(RANGEUPGRADE_TIER_1);
		singleTexturedItem(RANGEUPGRADE_TIER_2);
		singleTexturedItem(RANGEUPGRADE_TIER_3);
		singleTexturedItem(RANGEUPGRADE_TIER_4);
		singleTexturedItem(REDSTONE_INFUSED);
		singleTexturedItem(TELEPORTER, mcLoc(ITEM_FOLDER + "/barrier"));
		singleTexturedItem(TESTITEM, mcLoc(ITEM_FOLDER + "/barrier"));
		singleTexturedItem(THERMOMETER);
		// TODO: wall light
		singleTexturedItem(WHITE_CRYSTAL);

	}

	private void addCrops()
	{
		final ResourceLocation seedsTexture = itemPrefix("seeds");
		final ResourceLocation particlesTexture = itemPrefix("particles");
		PlantTechMain.getCropList().values().forEach(entry -> {
			String name = entry.getName();
			singleTexture(name + "_seeds", generated, "layer0", seedsTexture);
			if (entry.hasParticle())
			{
				singleTexture(name + "_particles", generated, "layer0", particlesTexture);
			}
		});
	}

	private void addBiomassContainer()
	{
		final String basePath = BIOMASSCONTAINER.getRegistryName().getPath();
		ItemModelBuilder baseContainerItem = withExistingParent(basePath, generated);
		ItemModelBuilder empty = withExistingParent(basePath + "_empty", generated);

		baseContainerItem.override().predicate(PlantTechClient.FILLED_PREDICATE, 0).model(empty);
		baseContainerItem.texture("layer0", itemPrefix("biomasscontainer_empty"));
		empty.texture("layer0", itemPrefix("biomasscontainer_empty"));

		int max = 9;
		for (int i = 0; i < max; i++)
		{
			ItemModelBuilder override = withExistingParent(basePath + "_" + i, generated);
			override.texture("layer0", itemPrefix("biomasscontainer_" + i));
			baseContainerItem.override().predicate(PlantTechClient.FILLED_PREDICATE, (float) i + 1).model(override);
		}
	}

	private void addChipsUpgrades()
	{
		singleTexturedItem(AQUA_AFFINITY_CHIP, "chips/enchantment_chip");
		singleTexturedItem(ARMORCHIP_TIER_1, "chips/armor_tier_1");
		singleTexturedItem(ARMORCHIP_TIER_2, "chips/armor_tier_2");
		singleTexturedItem(ARMORCHIP_TIER_3, "chips/armor_tier_3");
		singleTexturedItem(ATTACKCHIP_TIER_1, "chips/attack_tier_1");
		singleTexturedItem(ATTACKCHIP_TIER_2, "chips/attack_tier_2");
		singleTexturedItem(ATTACKCHIP_TIER_3, "chips/attack_tier_3");
		singleTexturedItem(ATTACKSPEEDCHIP_TIER_1, "chips/attackspeed_tier_1");
		singleTexturedItem(ATTACKSPEEDCHIP_TIER_2, "chips/attackspeed_tier_2");
		singleTexturedItem(ATTACKSPEEDCHIP_TIER_3, "chips/attackspeed_tier_3");
		singleTexturedItem(BLAST_PROTECTION_CHIP, "chips/enchantment_chip");
		singleTexturedItem(BANE_OF_ARTHROPODS_CHIP, "chips/enchantment_chip");
		singleTexturedItem(BREAKDOWNRATECHIP_TIER_1, "chips/harvestspeed_tier_1");
		singleTexturedItem(BREAKDOWNRATECHIP_TIER_2, "chips/harvestspeed_tier_2");
		singleTexturedItem(BREAKDOWNRATECHIP_TIER_3, "chips/harvestspeed_tier_3");
		singleTexturedItem(CAPACITYCHIP_TIER_1, "chips/capacity_tier_1");
		singleTexturedItem(CAPACITYCHIP_TIER_2, "chips/capacity_tier_2");
		singleTexturedItem(CAPACITYCHIP_TIER_3, "chips/capacity_tier_3");
		singleTexturedItem(CAPACITYUPGRADE_TIER_1);
		singleTexturedItem(CAPACITYUPGRADE_TIER_2);
		singleTexturedItem(CAPACITYUPGRADE_TIER_3);
		singleTexturedItem(CHIP_UPGRADEPACK_CAPACITY_1, "chips/upgradepack/capacity_1");
		singleTexturedItem(CHIP_UPGRADEPACK_CAPACITY_2, "chips/upgradepack/capacity_2");
		singleTexturedItem(CHIP_UPGRADEPACK_HARVESTLEVEL_1, "chips/upgradepack/harvestlevel_1");
		singleTexturedItem(CHIP_UPGRADEPACK_HARVESTLEVEL_2, "chips/upgradepack/harvestlevel_2");
		singleTexturedItem(CHIP_UPGRADEPACK_REACTOR_1, "chips/upgradepack/reactor_1");
		singleTexturedItem(CHIP_UPGRADEPACK_REACTOR_2, "chips/upgradepack/reactor_2");
		singleTexturedItem(DEPTH_STRIDER_CHIP, "chips/enchantment_chip");
		singleTexturedItem(EFFICIENCY_CHIP, "chips/enchantment_chip");
		singleTexturedItem(EMPTY_UPGRADECHIP_TIER_1, "chips/template_tier_1");
		singleTexturedItem(EMPTY_UPGRADECHIP_TIER_2, "chips/template_tier_2");
		singleTexturedItem(EMPTY_UPGRADECHIP_TIER_3, "chips/template_tier_3");
		singleTexturedItem(FEATHER_FALLING_CHIP, "chips/enchantment_chip");
		singleTexturedItem(FIRE_ASPECT_CHIP, "chips/enchantment_chip");
		singleTexturedItem(FIRE_PROTECTION_CHIP, "chips/enchantment_chip");
		singleTexturedItem(FLAME_CHIP, "chips/enchantment_chip");
		singleTexturedItem(FORTUNE_CHIP, "chips/enchantment_chip");
		singleTexturedItem(FROST_WALKER_CHIP, "chips/enchantment_chip");
		singleTexturedItem(HARVESTLEVELCHIP_TIER_1, "chips/harvestlevel_tier_1");
		singleTexturedItem(HARVESTLEVELCHIP_TIER_2, "chips/harvestlevel_tier_2");
		singleTexturedItem(HARVESTLEVELCHIP_TIER_3, "chips/harvestlevel_tier_3");
		singleTexturedItem(INFINITY_CHIP, "chips/enchantment_chip");
		singleTexturedItem(KNOCKBACK_CHIP, "chips/enchantment_chip");
		singleTexturedItem(KNOWLEDGECHIP_TIER_0);
		singleTexturedItem(KNOWLEDGECHIP_TIER_1);
		singleTexturedItem(KNOWLEDGECHIP_TIER_2);
		singleTexturedItem(KNOWLEDGECHIP_TIER_3);
		singleTexturedItem(KNOWLEDGECHIP_TIER_4);
		singleTexturedItem(KNOWLEDGECHIP_TIER_5);
		singleTexturedItem(LOOTING_CHIP, "chips/enchantment_chip");
		singleTexturedItem(POWER_CHIP, "chips/enchantment_chip");
		singleTexturedItem(PROJECTILE_PROTECTION_CHIP, "chips/enchantment_chip");
		singleTexturedItem(PROTECTION_CHIP, "chips/enchantment_chip");
		singleTexturedItem(PUNCH_CHIP, "chips/enchantment_chip");
		singleTexturedItem(REACTORCHIP_TIER_1, "chips/reactor_tier_1");
		singleTexturedItem(REACTORCHIP_TIER_2, "chips/reactor_tier_2");
		singleTexturedItem(REACTORCHIP_TIER_3, "chips/reactor_tier_3");
		singleTexturedItem(RESPIRATION_CHIP, "chips/enchantment_chip");
		singleTexturedItem(SHARPNESS_CHIP, "chips/enchantment_chip");
		singleTexturedItem(SILK_TOUCH_CHIP, "chips/enchantment_chip");
		singleTexturedItem(SMITE_CHIP, "chips/enchantment_chip");
		singleTexturedItem(SOLARFOCUS_TIER_1, "solarfocus_tier_1");
		singleTexturedItem(SOLARFOCUS_TIER_2, "solarfocus_tier_2");
		singleTexturedItem(SOLARFOCUS_TIER_3, "solarfocus_tier_3");
		singleTexturedItem(SOLARFOCUS_TIER_4, "solarfocus_tier_4");
		singleTexturedItem(SPEEDUPGRADE_TIER_1);
		singleTexturedItem(SPEEDUPGRADE_TIER_2);
		singleTexturedItem(SPEEDUPGRADE_TIER_3);
		singleTexturedItem(SPEEDUPGRADE_TIER_4);
		singleTexturedItem(SWEEPING_CHIP, "chips/enchantment_chip");
		singleTexturedItem(THORNS_CHIP, "chips/enchantment_chip");
		singleTexturedItem(TOUGHNESSCHIP_TIER_1, "chips/toughness_tier_1");
		singleTexturedItem(TOUGHNESSCHIP_TIER_2, "chips/toughness_tier_2");
		singleTexturedItem(TOUGHNESSCHIP_TIER_3, "chips/toughness_tier_3");
		singleTexturedItem(UNBREAKING_CHIP, "chips/enchantment_chip");
		singleTexturedItem(UNLOCKCHIP_AXE, "chips/axe");
		singleTexturedItem(UNLOCKCHIP_HOE, "chips/hoe");
		singleTexturedItem(UNLOCKCHIP_SHEARS, "chips/shears");
		singleTexturedItem(UNLOCKCHIP_SHOVEL, "chips/shovel");
	}

	private void addAuraCores()
	{
		singleTexturedItem(AURA_CORE_LIGHT_DECREASE_I);
		singleTexturedItem(AURA_CORE_LIGHT_DECREASE_II);
		singleTexturedItem(AURA_CORE_LIGHT_DECREASE_III);
		singleTexturedItem(AURA_CORE_WATER_RANGE_DECREASE_I);
		singleTexturedItem(AURA_CORE_WATER_RANGE_DECREASE_II);
		singleTexturedItem(AURA_CORE_WATER_RANGE_DECREASE_III);
		singleTexturedItem(AURA_CORE_TEMPERATURE_EXTREME_COLD);
		singleTexturedItem(AURA_CORE_TEMPERATURE_COLD);
		singleTexturedItem(AURA_CORE_TEMPERATURE_NORMAL);
		singleTexturedItem(AURA_CORE_TEMPERATURE_WARM);
		singleTexturedItem(AURA_CORE_TEMPERATURE_EXTREME_WARM);
		singleTexturedItem(AURA_CORE_FERTILITY_INCREASE_I);
		singleTexturedItem(AURA_CORE_FERTILITY_INCREASE_II);
		singleTexturedItem(AURA_CORE_FERTILITY_INCREASE_III);
		singleTexturedItem(AURA_CORE_PRODUCTIVITY_INCREASE_I);
		singleTexturedItem(AURA_CORE_PRODUCTIVITY_INCREASE_II);
		singleTexturedItem(AURA_CORE_PRODUCTIVITY_INCREASE_III);
	}

	private void addBulbs()
	{
		bulbItem("chipalyzer");
		bulbItem("compressor");
		bulbItem("dna_cleaner");
		bulbItem("dna_combiner");
		bulbItem("dna_extractor");
		bulbItem("dna_remover");
		bulbItem("energy_supplier");
		bulbItem("identifier");
		bulbItem("infuser");
		bulbItem("machinebulbreprocessor");
		bulbItem("mega_furnace");
		bulbItem("plantfarm");
		bulbItem("seedconstructor");
		bulbItem("seedsqueezer");
		bulbItem("solargenerator");
	}

	private void bulbItem(String item)
	{
		ItemModelBuilder model = withExistingParent(item + "_bulb", generated);
		model.texture("layer0", itemPrefix("machinebulbs/machinebulb_layer_1"));
		model.texture("layer1", itemPrefix("machinebulbs/machinebulb_layer_2_" + item));
	}

	// Creates model for given item
	private void singleTexturedItem(IItemProvider item)
	{
		singleTexturedItem(item, (ResourceLocation) null);
	}

	// Creates model for given item, with given texture path
	private void singleTexturedItem(IItemProvider item, String texturePath)
	{
		singleTexturedItem(item, generated, texturePath);
	}

	// Creates model for given item, with given parent and texture path
	private void singleTexturedItem(IItemProvider item, ResourceLocation parent, String texturePath)
	{
		singleTexturedItem(item, parent, itemPrefix(texturePath));
	}

	// Creates model for given item, with parent `item/generated` and optional texture
	private void singleTexturedItem(IItemProvider item, @Nullable ResourceLocation textureLocation)
	{
		singleTexturedItem(item, generated, textureLocation);
	}

	// Creates model for given item, with given parent and optional texture (defaults to "item/" + item name)
	private void singleTexturedItem(IItemProvider item, ResourceLocation parent, @Nullable ResourceLocation textureLocation)
	{
		final ResourceLocation loc = Objects.requireNonNull(item.asItem().getRegistryName());
		if (textureLocation == null)
		{
			textureLocation = itemLoc(loc, "");
		}
		singleTexture(loc.getPath(), parent, "layer0", textureLocation);
	}

	// Helper function to prefix "item/" to a ResourceLocation's path
	private ResourceLocation itemLoc(ResourceLocation loc, String subPath)
	{
		return new ResourceLocation(loc.getNamespace(), "items/" + subPath + loc.getPath());
	}

	private ResourceLocation itemPrefix(String path)
	{
		return new ResourceLocation(PlantTechMain.MODID, "items/" + path);
	}
}
