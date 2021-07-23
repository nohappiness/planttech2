package net.kaneka.planttech2.filehelper;

import net.minecraft.world.item.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonGenerator
{
	int nextLine = 0;
	List<String> lines = new ArrayList<String>();
	List<String> template = new ArrayList<String>();
	String actualtemplate = "";

	private void saveFile(String file)
	{
		try
		{
			File f = new File("json/outputs/" + file + ".json");
			f.createNewFile();
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (String line : lines)
			{
				bw.write(line);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		lines.isEmpty();
		nextLine = 0;
	}

	private void loadTemplate(String temp)
	{
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader("json/templates/" + temp + ".json"));
			String line = reader.readLine();
			while (line != null)
			{
				template.add(line);
				line = reader.readLine();
			}
			reader.close();
			actualtemplate = temp;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void createFile(String temp, String filename, HashMap<String, String> replacements)
	{
		if (actualtemplate != temp)
		{
			loadTemplate(temp);
		}
		lines.clear();
		lines.addAll(template);
		for (Map.Entry<String, String> entry : replacements.entrySet())
		{
			for (int i = 0; i < lines.size(); i++)
			{
				lines.set(i, lines.get(i).replace(entry.getKey(), entry.getValue()));
			}
		}
		saveFile(filename);

	}

	public void create()
	{
		/*
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.ARMORCHIP_TIER_1,
		 * Items.IRON_CHESTPLATE, 1);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.ARMORCHIP_TIER_2,
		 * Items.IRON_CHESTPLATE, 2);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.ARMORCHIP_TIER_3,
		 * Items.IRON_CHESTPLATE, 3);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.ATTACKCHIP_TIER_1,
		 * Items.IRON_SWORD, 1);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.ATTACKCHIP_TIER_2,
		 * Items.IRON_SWORD, 2);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.ATTACKCHIP_TIER_3,
		 * Items.IRON_SWORD, 3);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.ATTACKSPEEDCHIP_TIER_1,
		 * Items.GOLDEN_SWORD, 1);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.ATTACKSPEEDCHIP_TIER_2,
		 * Items.GOLDEN_SWORD, 2);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.ATTACKSPEEDCHIP_TIER_3,
		 * Items.GOLDEN_SWORD, 3);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.BREAKDOWNRATECHIP_TIER_1,
		 * Items.GOLDEN_PICKAXE, 1);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.BREAKDOWNRATECHIP_TIER_2,
		 * Items.GOLDEN_PICKAXE, 2);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.BREAKDOWNRATECHIP_TIER_3,
		 * Items.GOLDEN_PICKAXE, 3);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.CAPACITYCHIP_TIER_1,
		 * ModItems.ENERGYSTORAGE_TIER_1, 1);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.CAPACITYCHIP_TIER_2,
		 * ModItems.ENERGYSTORAGE_TIER_2, 2);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.CAPACITYCHIP_TIER_3,
		 * ModItems.ENERGYSTORAGE_TIER_3, 3);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.HARVESTLEVELCHIP_TIER_1,
		 * Items.DIAMOND_PICKAXE, 1);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.HARVESTLEVELCHIP_TIER_2,
		 * Items.DIAMOND_PICKAXE, 2);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.HARVESTLEVELCHIP_TIER_3,
		 * Items.DIAMOND_PICKAXE, 3);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.REACTORCHIP_TIER_1,
		 * ModItems.SOLARFOCUS_TIER_1, 1);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.REACTORCHIP_TIER_2,
		 * ModItems.SOLARFOCUS_TIER_2, 2);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.REACTORCHIP_TIER_3,
		 * ModItems.SOLARFOCUS_TIER_3, 3);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.TOUGHNESSCHIP_TIER_1,
		 * Items.DIAMOND_CHESTPLATE, 1);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.TOUGHNESSCHIP_TIER_2,
		 * Items.DIAMOND_CHESTPLATE, 2);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.TOUGHNESSCHIP_TIER_3,
		 * Items.DIAMOND_CHESTPLATE, 3);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.UNLOCKCHIP_AXE,
		 * Items.IRON_AXE, 1);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.UNLOCKCHIP_HOE,
		 * Items.IRON_HOE, 1);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.UNLOCKCHIP_SHEARS,
		 * Items.SHEARS, 1);
		 * createChipalyzerItemWithoutEnchantmentFile(ModItems.UNLOCKCHIP_SHOVEL,
		 * Items.IRON_SHOVEL, 1);
		 */

		/**
		createChipalyzerItemWithEnchantmentFile(ModItems.PROTECTION_CHIP, Enchantments.PROTECTION);
		createChipalyzerItemWithEnchantmentFile(ModItems.FIRE_PROTECTION_CHIP, Enchantments.FIRE_PROTECTION);
		createChipalyzerItemWithEnchantmentFile(ModItems.FEATHER_FALLING_CHIP, Enchantments.FEATHER_FALLING);
		createChipalyzerItemWithEnchantmentFile(ModItems.BLAST_PROTECTION_CHIP, Enchantments.BLAST_PROTECTION);
		createChipalyzerItemWithEnchantmentFile(ModItems.PROJECTILE_PROTECTION_CHIP, Enchantments.PROJECTILE_PROTECTION);
		createChipalyzerItemWithEnchantmentFile(ModItems.RESPIRATION_CHIP, Enchantments.RESPIRATION);
		createChipalyzerItemWithEnchantmentFile(ModItems.AQUA_AFFINITY_CHIP, Enchantments.AQUA_AFFINITY);
		createChipalyzerItemWithEnchantmentFile(ModItems.THORNS_CHIP, Enchantments.THORNS);
		createChipalyzerItemWithEnchantmentFile(ModItems.DEPTH_STRIDER_CHIP, Enchantments.DEPTH_STRIDER);
		createChipalyzerItemWithEnchantmentFile(ModItems.FROST_WALKER_CHIP, Enchantments.FROST_WALKER);
		createChipalyzerItemWithEnchantmentFile(ModItems.SHARPNESS_CHIP, Enchantments.SHARPNESS);
		createChipalyzerItemWithEnchantmentFile(ModItems.SMITE_CHIP, Enchantments.SMITE);
		createChipalyzerItemWithEnchantmentFile(ModItems.BANE_OF_ARTHROPODS_CHIP, Enchantments.BANE_OF_ARTHROPODS);
		createChipalyzerItemWithEnchantmentFile(ModItems.KNOCKBACK_CHIP, Enchantments.KNOCKBACK);
		createChipalyzerItemWithEnchantmentFile(ModItems.FIRE_ASPECT_CHIP, Enchantments.FIRE_ASPECT);
		createChipalyzerItemWithEnchantmentFile(ModItems.LOOTING_CHIP, Enchantments.LOOTING);
		createChipalyzerItemWithEnchantmentFile(ModItems.SWEEPING_CHIP, Enchantments.SWEEPING);
		createChipalyzerItemWithEnchantmentFile(ModItems.EFFICIENCY_CHIP, Enchantments.EFFICIENCY);
		createChipalyzerItemWithEnchantmentFile(ModItems.SILK_TOUCH_CHIP, Enchantments.SILK_TOUCH);
		createChipalyzerItemWithEnchantmentFile(ModItems.UNBREAKING_CHIP, Enchantments.UNBREAKING);
		createChipalyzerItemWithEnchantmentFile(ModItems.FORTUNE_CHIP, Enchantments.FORTUNE);
		createChipalyzerItemWithEnchantmentFile(ModItems.POWER_CHIP, Enchantments.POWER);
		createChipalyzerItemWithEnchantmentFile(ModItems.PUNCH_CHIP, Enchantments.PUNCH);
		createChipalyzerItemWithEnchantmentFile(ModItems.FLAME_CHIP, Enchantments.FLAME);
		createChipalyzerItemWithEnchantmentFile(ModItems.INFINITY_CHIP, Enchantments.INFINITY);
		*/
		
		/*
		createItemModelFile(ModItems.PROTECTION_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.FIRE_PROTECTION_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.FEATHER_FALLING_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.BLAST_PROTECTION_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.PROJECTILE_PROTECTION_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.RESPIRATION_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.AQUA_AFFINITY_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.THORNS_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.DEPTH_STRIDER_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.FROST_WALKER_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.SHARPNESS_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.SMITE_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.BANE_OF_ARTHROPODS_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.KNOCKBACK_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.FIRE_ASPECT_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.LOOTING_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.SWEEPING_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.EFFICIENCY_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.SILK_TOUCH_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.UNBREAKING_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.FORTUNE_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.POWER_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.PUNCH_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.FLAME_CHIP, "chips/enchantment_chip");
		createItemModelFile(ModItems.INFINITY_CHIP, "chips/enchantment_chip");
		*/
		

	}

	/*
	private void createChipalyzerItemWithoutEnchantmentFile(Item output, Item input, int tier)
	{
		createFile("chipalyzeritem", output.getRegistryName().getPath(), new HashMap<String, String>()
		{
			{
				put("%tier", String.valueOf(tier));
				put("%input", input.getRegistryName().toString());
				put("%output", output.getRegistryName().toString());
			}
		});

	}

	private void createChipalyzerItemWithEnchantmentFile(Item output, Enchantment input)
	{
		createFile("chipalyzerenchantment", output.getRegistryName().getPath(), new HashMap<String, String>()
		{
			{
				put("%tier", String.valueOf(2));
				put("%enchantment", input.getName().replace("enchantment.minecraft.", ""));
				put("%output", output.getRegistryName().toString());
			}
		});

	}
	*/
	
	@SuppressWarnings("unused")
	private void createItemModelFile(Item output, String file)
	{
		createFile("itemmodel", output.getRegistryName().getPath(), new HashMap<String, String>()
		{
			{
				put("%file", file);
				
			}
		});

	}
}
