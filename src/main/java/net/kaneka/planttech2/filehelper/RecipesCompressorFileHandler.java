package net.kaneka.planttech2.filehelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import net.minecraft.block.Blocks;
import net.minecraft.item.Items;

public class RecipesCompressorFileHandler 
{
    int maxLines = 500;
    int nextLine = 0;
    String[] lines = new String[maxLines];

    private void saveFile(String file)
    {

	try
	{
	    File f = new File("json/" + file + ".json");
	    f.createNewFile();
	    FileWriter fw = new FileWriter(f, true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    for (int i = 0; i < nextLine; i++)
	    {
		bw.write(lines[i]);
		bw.newLine();
	    }
	    bw.close();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}

	lines = new String[maxLines];
	nextLine = 0;
    }

    private void addLine(String line)
    {
	this.lines[nextLine] = line;
	this.nextLine++;
    }

    private void createCompressorFile(String input, int inputint, String result, int resultint)
    {
	addLine("{");
	addLine("    \"type\": \"planttech2:compressing\",");
	addLine("    \"input\": {");
	addLine("      \"item\": \""+ input +"\",");
	addLine("      \"amount\": " +  inputint + "");
	addLine("    },");
	addLine("    \"result\": {");
	addLine("      \"item\": \"" + result + "\",");
	addLine("      \"amount\": " + resultint + "");
	addLine("    }"); 
	addLine("  }");
	saveFile(result.replace("minecraft:", "").replace("planttech2:", ""));
    }
    
    public void defaultValues()
    {
	createCompressorFile("planttech2:blaze_particles",8,"minecraft:blaze_rod",1);
	createCompressorFile("planttech2:chicken_particles",8,"minecraft:chicken",1);
	createCompressorFile("planttech2:coal_particles",8,"minecraft:coal_ore",1);
	createCompressorFile("planttech2:cow_particles",8,"minecraft:beef",1);
	createCompressorFile("planttech2:creeper_particles",8,"minecraft:gunpowder",1);
	createCompressorFile("planttech2:dancium_particles",8,"planttech2:dancium_ingot",1);
	createCompressorFile("planttech2:diamond_particles",8,"minecraft:diamond_ore",1);
	createCompressorFile("planttech2:dirt_particles",8,"minecraft:dirt",1);
	createCompressorFile("planttech2:emerald_particles",8,"minecraft:emerald_ore",1);
	createCompressorFile("planttech2:enderdragon_particles",8,"minecraft:dragon_egg",1);
	createCompressorFile("planttech2:enderdragon_particles",8,"minecraft:dragon_breath",1);
	createCompressorFile("planttech2:enderman_particles",8,"minecraft:ender_pearl",1);
	createCompressorFile("planttech2:endstone_particles",8,"minecraft:end_stone",1);
	createCompressorFile("planttech2:fish_particles",8,"minecraft:cod",1);
	createCompressorFile("planttech2:ghast_particles",8,"minecraft:ghast_tear",1);
	createCompressorFile("planttech2:glowstone_particles",8,"minecraft:glowstone",1);
	createCompressorFile("planttech2:gold_particles",8,"minecraft:gold_ore",1);
	createCompressorFile("planttech2:guardian_particles",8,"minecraft:experience_bottle",1);
	createCompressorFile("planttech2:iron_particles",8,"minecraft:iron_ore",1);
	createCompressorFile("planttech2:kanekium_particles",8,"planttech2:kanekium_ingot",1);
	createCompressorFile("planttech2:kinnoium_particles",8,"planttech2:kinnoium_ingot",1);
	createCompressorFile("planttech2:lapis_particles",8,"minecraft:lapis_ore",1);
	createCompressorFile("planttech2:lava_particles",8,"minecraft:lava_bucket",1);
	createCompressorFile("planttech2:lenthurium_particles",8,"planttech2:lenthurium_ingot",1);
	createCompressorFile("planttech2:magma_cube_particles",8,"minecraft:magma_cream",1);
	createCompressorFile("planttech2:mooshroom_particles",8,"minecraft:beef",1);
	createCompressorFile("planttech2:mycelium_particles",8,"minecraft:mycelium",1);
	createCompressorFile("planttech2:netherrack_particles",8,"minecraft:netherrack",1);
	createCompressorFile("planttech2:parrot_particles",8,"minecraft:feather",1);
	createCompressorFile("planttech2:pig_particles",8,"minecraft:porkchop",1);
	createCompressorFile("planttech2:plantium_particles",8,"planttech2:plantium_ingot",1);
	createCompressorFile("planttech2:prismarin_particles",8,"minecraft:prismarine_crystals",1);
	createCompressorFile("planttech2:prismarin_particles",8,"minecraft:prismarine_shard",1);
	createCompressorFile("planttech2:quartz_particles",8,"minecraft:quartz_ore",1);
	createCompressorFile("planttech2:redstone_particles",8,"minecraft:redstone_ore",1);
	createCompressorFile("planttech2:sand_particles",8,"minecraft:sand",1);
	createCompressorFile("planttech2:sheep_particles",8,"minecraft:white_wool",1);
	createCompressorFile("planttech2:shulker_particles",8,"minecraft:shulker_shell",1);
	createCompressorFile("planttech2:skeleton_particles",8,"minecraft:bone",1);
	createCompressorFile("planttech2:slime_particles",8,"minecraft:slime_ball",1);
	createCompressorFile("planttech2:snow_particles",8,"minecraft:snowball",1);
	createCompressorFile("planttech2:snow_particles",8,"minecraft:ice",1);
	createCompressorFile("planttech2:soulsand_particles",8,"minecraft:soul_sand",1);
	createCompressorFile("planttech2:spider_particles",8,"minecraft:string",1);
	createCompressorFile("planttech2:sponge_particles",8,"minecraft:sponge",1);
	createCompressorFile("planttech2:squid_particles",8,Items.INK_SAC.getRegistryName().toString(),1);
	createCompressorFile("planttech2:stone_particles",8,"minecraft:cobblestone",1);
	createCompressorFile("planttech2:stray_particles",8,"minecraft:arrow",1);
	createCompressorFile("planttech2:villager_particles",8,"minecraft:emerald",1);
	createCompressorFile("planttech2:water_particles",8,"minecraft:water_bucket",1);
	createCompressorFile("planttech2:witch_particles",8,"minecraft:glass_bottle",1);
	createCompressorFile("planttech2:wither_skeleton_particles",8,"minecraft:wither_skeleton_skull",1);
	createCompressorFile("planttech2:wither_particles",8,"minecraft:nether_star",1);
	createCompressorFile("planttech2:wood_particles",8,Blocks.OAK_LOG.getRegistryName().toString(),1);
	createCompressorFile("planttech2:wood_particles",8,Blocks.SPRUCE_LOG.getRegistryName().toString(),1);
	createCompressorFile("planttech2:wood_particles",8,Blocks.BIRCH_LOG.getRegistryName().toString(),1);
	createCompressorFile("planttech2:wood_particles",8,Blocks.JUNGLE_LOG.getRegistryName().toString(),1);
	createCompressorFile("planttech2:wood_particles",8,Blocks.ACACIA_LOG.getRegistryName().toString(),1);
	createCompressorFile("planttech2:wood_particles",8,Blocks.DARK_OAK_LOG.getRegistryName().toString(),1);
	createCompressorFile("planttech2:zombie_pigman_particles",8,"minecraft:gold_nugget",1);
	createCompressorFile("planttech2:zombie_villager_particles",8,"minecraft:iron_ingot",1);
	createCompressorFile("planttech2:zombie_particles",8,"minecraft:rotten_flesh",1);
	createCompressorFile("planttech2:color_particles",8,Items.INK_SAC.getRegistryName().toString(),1);
	//createCompressorFile("planttech2:color_particles",8,Items.ROSE_RED.getRegistryName().toString(),1);
	//createCompressorFile("planttech2:color_particles",8,Items.CACTUS_GREEN.getRegistryName().toString(),1);
	createCompressorFile("planttech2:color_particles",8,Items.COCOA_BEANS.getRegistryName().toString(),1);
	createCompressorFile("planttech2:color_particles",8,Items.LAPIS_LAZULI.getRegistryName().toString(),1);
	createCompressorFile("planttech2:color_particles",8,Items.PURPLE_DYE.getRegistryName().toString(),1);
	createCompressorFile("planttech2:color_particles",8,Items.CYAN_DYE.getRegistryName().toString(),1);
	createCompressorFile("planttech2:color_particles",8,Items.LIGHT_GRAY_DYE.getRegistryName().toString(),1);
	createCompressorFile("planttech2:color_particles",8,Items.GRAY_DYE.getRegistryName().toString(),1);
	createCompressorFile("planttech2:color_particles",8,Items.PINK_DYE.getRegistryName().toString(),1);
	createCompressorFile("planttech2:color_particles",8,Items.LIME_DYE.getRegistryName().toString(),1);
	//createCompressorFile("planttech2:color_particles",8,Items.DANDELION_YELLOW.getRegistryName().toString(),1);
	createCompressorFile("planttech2:color_particles",8,Items.LIGHT_BLUE_DYE.getRegistryName().toString(),1);
	createCompressorFile("planttech2:color_particles",8,Items.MAGENTA_DYE.getRegistryName().toString(),1);
	createCompressorFile("planttech2:color_particles",8,Items.ORANGE_DYE.getRegistryName().toString(),1);
	createCompressorFile("planttech2:color_particles",8,Items.BONE_MEAL.getRegistryName().toString(),1);
	
	
    }
}
