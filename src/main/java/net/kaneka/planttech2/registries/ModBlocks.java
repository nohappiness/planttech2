package net.kaneka.planttech2.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.BaseBlock;
import net.kaneka.planttech2.blocks.CropBarsBlock;
import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.blocks.machines.CableBlock;
import net.kaneka.planttech2.blocks.machines.EnergyStorageBlock;
import net.kaneka.planttech2.blocks.machines.MachineBaseBlock;
import net.kaneka.planttech2.blocks.machines.MachineFacingBlock;
import net.kaneka.planttech2.blocks.machines.MachinePortalBlock;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks
{
	public static List<BaseBlock> BLOCKS = new ArrayList<BaseBlock>();
	public static List<BaseBlock> BLOCKITEMS = new ArrayList<BaseBlock>();

	public static BaseBlock 
			CABLE = new CableBlock(), 
			CHIPALYZER = new MachineFacingBlock("chipalyzer", ModCreativeTabs.groupmachines),
			COMPRESSOR = new MachineFacingBlock("compressor", ModCreativeTabs.groupmachines), 
			CROPBARS = new CropBarsBlock(),
	        DANCIUM_BLOCK = new BaseBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F), "dancium_block", ModCreativeTabs.groupmain, true),
	        DNA_CLEANER = new MachineFacingBlock("dna_cleaner", ModCreativeTabs.groupmachines), 
	        DNA_COMBINER = new MachineFacingBlock("dna_combiner", ModCreativeTabs.groupmachines),
	        DNA_EXTRACTOR = new MachineFacingBlock("dna_extractor", ModCreativeTabs.groupmachines), 
	        DNA_REMOVER = new MachineFacingBlock("dna_remover", ModCreativeTabs.groupmachines),
	        ENERGYSTORAGE = new EnergyStorageBlock(), 
	        IDENTIFIER = new MachineFacingBlock("identifier", ModCreativeTabs.groupmachines),
	        INFUSER = new MachineFacingBlock("infuser", ModCreativeTabs.groupmachines),
	        KANEKIUM_BLOCK = new BaseBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F), "kanekium_block", ModCreativeTabs.groupmain, true),
	        KINNOIUM_BLOCK = new BaseBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F), "kinnoium_block", ModCreativeTabs.groupmain, true),
	        LENTHURIUM_BLOCK = new BaseBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F), "lenthurium_block", ModCreativeTabs.groupmain, true),
		    MACHINESHELL = new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(0.9F), "machineshell", ModCreativeTabs.groupmain, true),  
		    MACHINESHELL_INFUSED = new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(0.9F), "machineshell_infused", ModCreativeTabs.groupmain, true),  
	        MEGAFURNACE = new MachineFacingBlock("mega_furnace", ModCreativeTabs.groupmachines), 
	        PLANTFARM = new MachineBaseBlock("plantfarm", ModCreativeTabs.groupmachines),
	        PLANTIUM_BLOCK = new BaseBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F), "plantium_block", ModCreativeTabs.groupmain, true),
	        SEEDCONSTRUCTOR = new MachineFacingBlock("seedconstructor", ModCreativeTabs.groupmachines),
	        SEEDSQUEEZER = new MachineFacingBlock("seedsqueezer", ModCreativeTabs.groupmachines),
	        SOLARGENERATOR = new MachineBaseBlock("solargenerator", ModCreativeTabs.groupmachines),
	        PLANTTOPIA_PORTAL = new MachinePortalBlock("planttopia_portal", Block.Properties.create(Material.IRON).hardnessAndResistance(0.5f), ModCreativeTabs.groupmachines);

	public static HashMap<String, CropBaseBlock> CROPS = new HashMap<String, CropBaseBlock>();

	public static void register(IForgeRegistry<Block> registry)
	{
		for (BaseBlock block : BLOCKS)
		{
			registry.register(block);
		}

		CropBaseBlock tempcrop;
		String name;
		for (CropListEntry entry : PlantTechMain.croplist.getAllEntries())
		{
			name = entry.getString();
			tempcrop = new CropBaseBlock(name);
			CROPS.put(name, tempcrop);
			registry.register(tempcrop);
		}
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry)
	{

		for (BaseBlock block : BLOCKITEMS)
		{
			registry.register(block.createItemBlock());
		}

	}

	@OnlyIn(Dist.CLIENT)
	public static void registerBlockColorHandler(ColorHandlerEvent.Block event)
	{
		for (CropBaseBlock block : CROPS.values())
		{
			event.getBlockColors().register(new CropBaseBlock.ColorHandler(), block);
		}
	}
}
