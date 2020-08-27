package net.kaneka.planttech2.filehelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class RecipeJSONGenerator
{
	
	int maxLines = 100; 
	int nextLine = 0; 
	String[] lines = new String[maxLines];
	String[] abc = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I"}; 
	
	public void saveFile(String file)
	{
		
		try 
		{
			File f = new File("recipes/" + file + ".json"); 
			f.createNewFile(); 
			FileWriter fw = new FileWriter(f, true); 
			BufferedWriter bw = new BufferedWriter(fw); 
			for(int i = 0; i < nextLine; i++)
			{
				bw.write(lines[i]); 
				bw.newLine();
			}
			bw.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		lines = new String[maxLines]; 
		nextLine = 0; 
	}
	
	public void addLine(String line)
	{
		this.lines[nextLine] = line;  
		this.nextLine++; 
	}
	
	public void createShapelessRecipeFile(String name, String output, int amount, String... ingredients)
	{
		addLine("{");
		addLine("    \"type\": \"minecraft:crafting_shapeless\",");
		addLine("    \"ingredients\": [");
		int i = ingredients.length; 
		int k = 0; 
		for(String ingredient: ingredients)
		{
			k++; 
			
			addLine("        {");
			String[] withMeta = ingredient.split("-");
			if(withMeta.length < 2)
			{
				addLine("            \"item\": \""+ ingredient + "\""); 
			}
			else
			{
				addLine("            \"item\": \""+ withMeta[0] + "\""); 
			}
			
			
			if(k < i)
			{
				addLine("        },");
			}
			else
			{
				addLine("        }");
			}
		}
		addLine("    ],"); 
		addLine("    \"result\": {");
		addLine("        \"item\": \""+ output + "\",");
		addLine("        \"count\": " + amount + "");
		addLine("    }");
		addLine("}");
		saveFile(name); 
	}
	
	public void createShapedRecipeFile(String row1, String row2, String row3, String name, String output, int amount, String... ingredients)
	{
		addLine("{");
		addLine("    \"type\": \"minecraft:crafting_shaped\",");
		addLine("    \"pattern\": [");
		addLine("        \"" + row1 + "\",");
		if(!row3.equals(""))
		{
			addLine("        \"" + row2 + "\",");
			addLine("        \"" + row3 + "\"");
		}
		else
		{
			addLine("        \"" + row2 + "\"");
		}
		addLine("    ],");
		addLine("    \"key\": {"); 
		int i = ingredients.length; 
		int k = 0; 
		for(String ingredient: ingredients)
		{
			addLine("        \"" + abc[k] + "\": ");
			k++; 
			
			addLine("        {");
			String[] withMeta = ingredient.split("-");
			if(withMeta.length < 2)
			{
				addLine("            \"item\": \""+ ingredient + "\""); 
			}
			else
			{
				addLine("            \"item\": \""+ withMeta[0] + "\""); 
			}
			
			
			if(k < i)
			{
				addLine("        },");
			}
			else
			{
				addLine("        }");
			}
		}
		addLine("    },"); 
		addLine("    \"result\": {");
		addLine("        \"item\": \""+ output + "\",");
		addLine("        \"count\": " + amount + "");
		addLine("    }");
		addLine("}");
		saveFile(name); 
	}
	
	public void createShapedRecipeFileItem(String row1, String row2, String row3, ItemStack output, int amount, ItemStack... ingredients)
	{
		createShapedRecipeFileItem(row1, row2, row3, output, output.getItem().getRegistryName().toString().replace("minecraft:", "").replace("planttech2:", ""), amount, ingredients);
	}
	
	public void createShapedRecipeFileItem(String row1, String row2, String row3, ItemStack output, String filename, int amount, ItemStack... ingredients)
	{
	    	System.out.println(filename);
	    	addLine("{");
		addLine("    \"type\": \"minecraft:crafting_shaped\",");
		addLine("    \"pattern\": [");
		addLine("        \"" + row1 + "\",");
		if(!row3.equals(""))
		{
			addLine("        \"" + row2 + "\",");
			addLine("        \"" + row3 + "\"");
		}
		else
		{
			addLine("        \"" + row2 + "\"");
		}
		addLine("    ],");
		addLine("    \"key\": {"); 
		int i = ingredients.length; 
		int k = 0; 
		for(ItemStack ingredient: ingredients)
		{
			addLine("        \"" + abc[k] + "\": ");
			k++; 
			
			addLine("        {");
			addLine("            \"item\": \""+ ingredient.getItem().getRegistryName() + "\""); 
			
			
			if(k < i)
			{
				addLine("        },");
			}
			else
			{
				addLine("        }");
			}
		}
		addLine("    },"); 
		addLine("    \"result\": {");
		addLine("        \"item\": \""+ output.getItem().getRegistryName() + "\",");
		addLine("        \"count\": " + amount + "");
		addLine("    }");
		addLine("}");
		saveFile(filename); 
	}
	
	public void createXRecipe(ItemStack output, int amount, ItemStack input)
	{
		createShapedRecipeFileItem("A A", " A ", "A A", output, amount, input);
	}
	
	public void createORecipe(ItemStack output, int amount, ItemStack input)
	{
		createShapedRecipeFileItem("AAA", "A A", "AAA", output, amount, input);
	}
	
	public void create2x2Recipe(ItemStack output, int amount, ItemStack input)
	{
		createShapedRecipeFileItem("AA", "AA", "", output, amount, input);
	}
	
	public void create3x3Recipe(ItemStack output, int amount, ItemStack input)
	{
		createShapedRecipeFileItem("AAA", "AAA", "AAA", output, amount, input);
	}
	
	public void createChestplateRecipe(ItemStack output, int amount, ItemStack input)
	{
		createShapedRecipeFileItem("A A", "AAA", "AAA", output, amount, input);
	}
	
	public void createHelmetRecipe(ItemStack output, int amount, ItemStack input)
	{
		createShapedRecipeFileItem("AAA", "A A", "   ", output, amount, input);
	}
	
	public void createLeggingsRecipe(ItemStack output, int amount, ItemStack input)
	{
		createShapedRecipeFileItem("AAA", "A A", "A A", output, amount, input);
	}
	
	public void createBootsRecipe(ItemStack output, int amount, ItemStack input)
	{
		createShapedRecipeFileItem("A A", "A A", "   ", output, amount, input);
	}
	
	public void createRecipes()
	{
		createShapedRecipeFileItem("ABA", "BAB", "ABA", new ItemStack(ModItems.PLANTIUM_INGOT), 1, new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.IRON_NUGGET));
		createShapedRecipeFileItem("AAA", "AAA", "AAA", new ItemStack(ModItems.PLANTIUM_INGOT),"plantium_ingot2", 1, new ItemStack(ModItems.PLANTIUM_NUGGET));
		createShapelessRecipeFile("plantium_ingot3", "planttech2:plantium_ingot", 9, "planttech2:plantium_block");
		createShapelessRecipeFile("plantium_nugget", "planttech2:plantium_nugget", 9, "planttech2:plantium_ingot");
		create3x3Recipe(new ItemStack(ModBlocks.PLANTIUM_BLOCK), 1, new ItemStack(ModItems.PLANTIUM_INGOT));
		
		createShapedRecipeFileItem("AAA", "AAA", "AAA", new ItemStack(ModItems.DANCIUM_INGOT),"dancium_ingot1", 1, new ItemStack(ModItems.DANCIUM_NUGGET));
		createShapelessRecipeFile("dancium_ingot2", "planttech2:dancium_ingot", 9, "planttech2:dancium_block");
		createShapelessRecipeFile("dancium_nugget", "planttech2:dancium_nugget", 9, "planttech2:dancium_ingot");
		create3x3Recipe(new ItemStack(ModBlocks.DANCIUM_BLOCK), 1, new ItemStack(ModItems.DANCIUM_INGOT));
		
		createShapedRecipeFileItem("AAA", "AAA", "AAA", new ItemStack(ModItems.KANEKIUM_INGOT),"kanekium_ingot1", 1, new ItemStack(ModItems.KANEKIUM_NUGGET));
		createShapelessRecipeFile("kanekium_ingot2", "planttech2:kanekium_ingot", 9, "planttech2:kanekium_block");
		createShapelessRecipeFile("kanekium_nugget", "planttech2:kanekium_nugget", 9, "planttech2:kanekium_ingot");
		create3x3Recipe(new ItemStack(ModBlocks.KANEKIUM_BLOCK), 1, new ItemStack(ModItems.KANEKIUM_INGOT));
		
		createShapedRecipeFileItem("AAA", "AAA", "AAA", new ItemStack(ModItems.KINNOIUM_INGOT),"kinnoium_ingot1", 1, new ItemStack(ModItems.KINNOIUM_NUGGET));
		createShapelessRecipeFile("kinnoium_ingot2", "planttech2:kinnoium_ingot", 9, "planttech2:kinnoium_block");
		createShapelessRecipeFile("kinnoium_nugget", "planttech2:kinnoium_nugget", 9, "planttech2:kinnoium_ingot");
		create3x3Recipe(new ItemStack(ModBlocks.KINNOIUM_BLOCK), 1, new ItemStack(ModItems.KINNOIUM_INGOT));
		
		createShapedRecipeFileItem("AAA", "AAA", "AAA", new ItemStack(ModItems.LENTHURIUM_INGOT),"lenthurium_ingot1", 1, new ItemStack(ModItems.LENTHURIUM_NUGGET));
		createShapelessRecipeFile("lenthurium_ingot2", "planttech2:lenthurium_ingot", 9, "planttech2:lenthurium_block");
		createShapelessRecipeFile("lenthurium_nugget", "planttech2:lenthurium_nugget", 9, "planttech2:lenthurium_ingot");
		create3x3Recipe(new ItemStack(ModBlocks.LENTHURIUM_BLOCK), 1, new ItemStack(ModItems.LENTHURIUM_INGOT));
		
		createShapedRecipeFileItem("A", "A", "B", new ItemStack(ModItems.THERMOMETER), 1, new ItemStack(Blocks.GLASS), new ItemStack(ModItems.PLANTIUM_INGOT));
		createShapedRecipeFileItem("AAA", "AAA", " B ", new ItemStack(ModItems.ANALYSER), 1, new ItemStack(Blocks.GLASS), new ItemStack(ModItems.PLANTIUM_INGOT));
		createShapedRecipeFileItem("A A", "AAA", " A ", new ItemStack(ModItems.WRENCH), 1, new ItemStack(ModItems.PLANTIUM_INGOT));
		
		createShapedRecipeFileItem("ABA", "BCB", "ABA", new ItemStack(ModItems.SOLARFOCUS_TIER_1),"solarfocus1", 1, new ItemStack(Blocks.GLASS_PANE), new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(Items.DIAMOND));
		createShapedRecipeFileItem("ABA", "BCB", "ABA", new ItemStack(ModItems.SOLARFOCUS_TIER_2),"solarfocus2", 1, new ItemStack(Blocks.GLASS_PANE), new ItemStack(ModItems.SOLARFOCUS_TIER_1), new ItemStack(Items.DIAMOND));
		createShapedRecipeFileItem("ABA", "BCB", "ABA", new ItemStack(ModItems.SOLARFOCUS_TIER_3),"solarfocus3", 1, new ItemStack(Blocks.GLASS_PANE), new ItemStack(ModItems.SOLARFOCUS_TIER_2), new ItemStack(Items.DIAMOND));
		createShapedRecipeFileItem("ABA", "BCB", "ABA", new ItemStack(ModItems.SOLARFOCUS_TIER_4),"solarfocus4", 1, new ItemStack(Blocks.GLASS_PANE), new ItemStack(ModItems.SOLARFOCUS_TIER_3), new ItemStack(Items.DIAMOND));
		createShapedRecipeFileItem("ABA", "BCB", "ABA", new ItemStack(ModItems.SPEEDUPGRADE_TIER_1), "speedupgrade1", 1, new ItemStack(Blocks.GLASS_PANE), new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(Items.EMERALD));
		createShapedRecipeFileItem("ABA", "BCB", "ABA", new ItemStack(ModItems.SPEEDUPGRADE_TIER_2), "speedupgrade2", 1, new ItemStack(Blocks.GLASS_PANE), new ItemStack(ModItems.SPEEDUPGRADE_TIER_1), new ItemStack(Items.EMERALD));
		createShapedRecipeFileItem("ABA", "BCB", "ABA", new ItemStack(ModItems.SPEEDUPGRADE_TIER_3), "speedupgrade3", 1, new ItemStack(Blocks.GLASS_PANE), new ItemStack(ModItems.SPEEDUPGRADE_TIER_2), new ItemStack(Items.EMERALD));
		createShapedRecipeFileItem("ABA", "BCB", "ABA", new ItemStack(ModItems.SPEEDUPGRADE_TIER_4), "speedupgrade4", 1, new ItemStack(Blocks.GLASS_PANE), new ItemStack(ModItems.SPEEDUPGRADE_TIER_3), new ItemStack(Items.EMERALD));
		createShapedRecipeFileItem("ABA", "BCB", "ABA", new ItemStack(ModItems.RANGEUPGRADE_TIER_1), "rangeupgrade1", 1, new ItemStack(Blocks.GLASS_PANE), new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(Items.WOODEN_HOE));
		createShapedRecipeFileItem("ABA", "BCB", "ABA", new ItemStack(ModItems.RANGEUPGRADE_TIER_2), "rangeupgrade2", 1, new ItemStack(Blocks.GLASS_PANE), new ItemStack(ModItems.RANGEUPGRADE_TIER_1), new ItemStack(Items.IRON_HOE));
		createShapedRecipeFileItem("ABA", "BCB", "ABA", new ItemStack(ModItems.RANGEUPGRADE_TIER_3), "rangeupgrade3", 1, new ItemStack(Blocks.GLASS_PANE), new ItemStack(ModItems.RANGEUPGRADE_TIER_2), new ItemStack(Items.GOLDEN_HOE));
		createShapedRecipeFileItem("ABA", "BCB", "ABA", new ItemStack(ModItems.RANGEUPGRADE_TIER_4), "rangeupgrade4", 1, new ItemStack(Blocks.GLASS_PANE), new ItemStack(ModItems.RANGEUPGRADE_TIER_3), new ItemStack(Items.DIAMOND_HOE));
		
		create2x2Recipe(new ItemStack(ModBlocks.CROPBARS), 1, new ItemStack(Items.STICK));
		
		createShapedRecipeFileItem("AAA", "BCB", "AAA", new ItemStack(ModBlocks.MEGAFURNACE), 1, new ItemStack(Blocks.FURNACE), new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(ModBlocks.PLANTIUM_BLOCK));
		//createShapedRecipeFileItem("AAA", "BCB", "AAA", new ItemStack(ModBlocks.IDENTIFIER), 1, new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(ModBlocks.CABLE), new ItemStack(ModItems.ANALYSER));
		//createShapedRecipeFileItem("ACA", "B B", "ACA", new ItemStack(ModBlocks.SEEDSQUEEZER), 1, new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(ModBlocks.CABLE), new ItemStack(Blocks.PISTON));
		//createShapedRecipeFileItem("AAA", "BCB", "DDD", new ItemStack(ModBlocks.SOLARGENERATOR), 1, new ItemStack(Items.EMERALD), new ItemStack(ModBlocks.CABLE), new ItemStack(Blocks.DAYLIGHT_DETECTOR), new ItemStack(ModItems.PLANTIUM_INGOT));
		//createShapedRecipeFileItem("AAA", "BCB", "AAA", new ItemStack(ModBlocks.PLANTFARM), 1, new ItemStack(ModBlocks.PLANTIUM_BLOCK), new ItemStack(ModBlocks.CABLE), new ItemStack(Items.DIAMOND_HOE));
		//createShapedRecipeFileItem(" A ", "ABA", " A ", new ItemStack(ModBlocks.CABLE), 16, new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(ModBlocks.PLANTIUM_BLOCK));
		
		createShapedRecipeFileItem("ABA", "A A", "AAA", new ItemStack(ModItems.DNA_CONTAINER_EMPTY), 16, new ItemStack(Blocks.GLASS_PANE), new ItemStack(Blocks.BIRCH_PLANKS));
		createShapedRecipeFileItem("ABA", "ACA", "AAA", new ItemStack(ModBlocks.DNA_CLEANER), 1, new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(Items.FLINT), new ItemStack(ModItems.DNA_CONTAINER_EMPTY));
		createShapedRecipeFileItem("ABA", "ACA", "AAA", new ItemStack(ModBlocks.DNA_EXTRACTOR), 1, new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(Items.DIAMOND_HOE), new ItemStack(ModItems.DNA_CONTAINER_EMPTY));
		createShapedRecipeFileItem("ABA", "ACA", "AAA", new ItemStack(ModBlocks.DNA_COMBINER), 1, new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(Items.DIAMOND_SHOVEL), new ItemStack(ModItems.DNA_CONTAINER_EMPTY));
		createShapedRecipeFileItem("ABA", "ACA", "AAA", new ItemStack(ModBlocks.DNA_REMOVER), 1, new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(Items.DIAMOND_AXE), new ItemStack(ModItems.DNA_CONTAINER_EMPTY));
		createShapedRecipeFileItem("ABA", "ACA", "AAA", new ItemStack(ModBlocks.SEEDCONSTRUCTOR), 1, new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(Items.DIAMOND_PICKAXE), new ItemStack(ModItems.DNA_CONTAINER_EMPTY));
		createShapedRecipeFileItem("AAA", "B B", "CCC", new ItemStack(ModBlocks.COMPRESSOR), 1, new ItemStack(ModItems.PLANTIUM_INGOT), new ItemStack(Blocks.PISTON), new ItemStack(ModBlocks.PLANTIUM_BLOCK));
		
		
		createShapedRecipeFileItem(" A ", "ABA", " A ", new ItemStack(ModItems.GUIDE_OVERVIEW), 1, new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.BOOK));
		createShapedRecipeFileItem(" A ", "ABA", " A ", new ItemStack(ModItems.GUIDE_PLANTS), 1, new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(Items.BOOK));
		createShapedRecipeFileItem(" A ", "ABA", " A ", new ItemStack(ModItems.GUIDE_GENETIC_ENGINEERING), 1, new ItemStack(Items.MELON_SEEDS), new ItemStack(Items.BOOK));
		
		/*
		createHelmetRecipe(new ItemStack(ModItems.HELMET_DANCIUM), 1, new ItemStack(ModItems.DANCIUM_INGOT));
		createChestplateRecipe(new ItemStack(ModItems.CHESTPLATE_DANCIUM), 1, new ItemStack(ModItems.DANCIUM_INGOT));
		createLeggingsRecipe(new ItemStack(ModItems.LEGGINGS_DANCIUM), 1, new ItemStack(ModItems.DANCIUM_INGOT));
		createBootsRecipe(new ItemStack(ModItems.BOOTS_DANCIUM), 1, new ItemStack(ModItems.DANCIUM_INGOT));
		
		createHelmetRecipe(new ItemStack(ModItems.HELMET_KANEKIUM), 1, new ItemStack(ModItems.KANEKIUM_INGOT));
		createChestplateRecipe(new ItemStack(ModItems.CHESTPLATE_KANEKIUM), 1, new ItemStack(ModItems.KANEKIUM_INGOT));
		createLeggingsRecipe(new ItemStack(ModItems.LEGGINGS_KANEKIUM), 1, new ItemStack(ModItems.KANEKIUM_INGOT));
		createBootsRecipe(new ItemStack(ModItems.BOOTS_KANEKIUM), 1, new ItemStack(ModItems.KANEKIUM_INGOT));
		
		createHelmetRecipe(new ItemStack(ModItems.HELMET_KINNOIUM), 1, new ItemStack(ModItems.KINNOIUM_INGOT));
		createChestplateRecipe(new ItemStack(ModItems.CHESTPLATE_KINNOIUM), 1, new ItemStack(ModItems.KINNOIUM_INGOT));
		createLeggingsRecipe(new ItemStack(ModItems.LEGGINGS_KINNOIUM), 1, new ItemStack(ModItems.KINNOIUM_INGOT));
		createBootsRecipe(new ItemStack(ModItems.BOOTS_KINNOIUM), 1, new ItemStack(ModItems.KINNOIUM_INGOT));
		
		createHelmetRecipe(new ItemStack(ModItems.HELMET_LENTHURIUM), 1, new ItemStack(ModItems.LENTHURIUM_INGOT));
		createChestplateRecipe(new ItemStack(ModItems.CHESTPLATE_LENTHURIUM), 1, new ItemStack(ModItems.LENTHURIUM_INGOT));
		createLeggingsRecipe(new ItemStack(ModItems.LEGGINGS_LENTHURIUM), 1, new ItemStack(ModItems.LENTHURIUM_INGOT));
		createBootsRecipe(new ItemStack(ModItems.BOOTS_LENTHURIUM), 1, new ItemStack(ModItems.LENTHURIUM_INGOT));
		*/
	}
}
