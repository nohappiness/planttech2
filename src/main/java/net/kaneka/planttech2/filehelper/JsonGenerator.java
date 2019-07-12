package net.kaneka.planttech2.filehelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

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
		createChipalyzerItemWithoutEnchantmentFile(ModItems.ARMORCHIP_TIER_1, Items.IRON_CHESTPLATE, 1);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.ARMORCHIP_TIER_2, Items.IRON_CHESTPLATE, 2);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.ARMORCHIP_TIER_3, Items.IRON_CHESTPLATE, 3);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.ATTACKCHIP_TIER_1, Items.IRON_SWORD, 1);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.ATTACKCHIP_TIER_2, Items.IRON_SWORD, 2);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.ATTACKCHIP_TIER_3, Items.IRON_SWORD, 3);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.ATTACKSPEEDCHIP_TIER_1, Items.GOLDEN_SWORD, 1);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.ATTACKSPEEDCHIP_TIER_2, Items.GOLDEN_SWORD, 2);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.ATTACKSPEEDCHIP_TIER_3, Items.GOLDEN_SWORD, 3);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.BREAKDOWNRATECHIP_TIER_1, Items.GOLDEN_PICKAXE, 1);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.BREAKDOWNRATECHIP_TIER_2, Items.GOLDEN_PICKAXE, 2);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.BREAKDOWNRATECHIP_TIER_3, Items.GOLDEN_PICKAXE, 3);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.CAPACITYCHIP_TIER_1, ModItems.ENERGYSTORAGE_TIER_1, 1);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.CAPACITYCHIP_TIER_2, ModItems.ENERGYSTORAGE_TIER_2, 2);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.CAPACITYCHIP_TIER_3, ModItems.ENERGYSTORAGE_TIER_3, 3);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.HARVESTLEVELCHIP_TIER_1, Items.DIAMOND_PICKAXE, 1);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.HARVESTLEVELCHIP_TIER_2, Items.DIAMOND_PICKAXE, 2);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.HARVESTLEVELCHIP_TIER_3, Items.DIAMOND_PICKAXE, 3);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.REACTORCHIP_TIER_1, ModItems.SOLARFOCUS_TIER_1, 1);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.REACTORCHIP_TIER_2, ModItems.SOLARFOCUS_TIER_2, 2);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.REACTORCHIP_TIER_3, ModItems.SOLARFOCUS_TIER_3, 3);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.TOUGHNESSCHIP_TIER_1, Items.DIAMOND_CHESTPLATE, 1);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.TOUGHNESSCHIP_TIER_2, Items.DIAMOND_CHESTPLATE, 2);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.TOUGHNESSCHIP_TIER_3, Items.DIAMOND_CHESTPLATE, 3);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.UNLOCKCHIP_AXE, Items.IRON_AXE, 1);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.UNLOCKCHIP_HOE, Items.IRON_HOE, 1);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.UNLOCKCHIP_SHEARS, Items.SHEARS, 1);
		createChipalyzerItemWithoutEnchantmentFile(ModItems.UNLOCKCHIP_SHOVEL, Items.IRON_SHOVEL, 1);

	}

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
}
