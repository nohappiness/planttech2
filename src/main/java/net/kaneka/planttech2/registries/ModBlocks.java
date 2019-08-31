package net.kaneka.planttech2.registries;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.block.FlowingFluidBlock;
import net.kaneka.planttech2.blocks.CropBarsBlock;
import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.blocks.GlassPaneEnd;
import net.kaneka.planttech2.blocks.GlassPanePillar;
import net.kaneka.planttech2.blocks.baseclasses.BaseBlock;
import net.kaneka.planttech2.blocks.baseclasses.CustomDoorBlock;
import net.kaneka.planttech2.blocks.baseclasses.CustomFenceBlock;
import net.kaneka.planttech2.blocks.baseclasses.CustomSlabBlock;
import net.kaneka.planttech2.blocks.baseclasses.CustomStairsBlock;
import net.kaneka.planttech2.blocks.machines.CableBlock;
import net.kaneka.planttech2.blocks.machines.EnergyStorageBlock;
import net.kaneka.planttech2.blocks.machines.MachineBaseBlock;
import net.kaneka.planttech2.blocks.machines.MachineFacingBlock;
import net.kaneka.planttech2.blocks.machines.MachineTeleporterEndBlock;
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
	
	public static Block BIOMASSFLUIDBLOCK = new FlowingFluidBlock(ModFluids.BIOMASS, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()) {};
   

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
	        PLANTTOPIA_TELEPORTER = new MachineBaseBlock("planttopia_teleporter", ModCreativeTabs.groupmachines),
	        PLANTTOPIA_TELEPORTER_END = new MachineTeleporterEndBlock("planttopia_teleporter_end", Block.Properties.create(Material.IRON).hardnessAndResistance(0.5f), ModCreativeTabs.groupmachines),
	        DARK_CRYSTAL_GLASSPANE_CROSS = new GlassPanePillar(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "dark_crystal_glasspane_cross", ModCreativeTabs.groupmain, true), 
	        DARK_CRYSTAL_GLASSPANE_MIDDLE = new GlassPanePillar(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "dark_crystal_glasspane_middle", ModCreativeTabs.groupmain, true), 
	        DARK_CRYSTAL_GLASSPANE_END = new GlassPaneEnd(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "dark_crystal_glasspane_end",Color.WHITE.getRGB(), ModCreativeTabs.groupmain, true),
			DARK_CRYSTAL_BLOCK = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "dark_crystal_block", ModCreativeTabs.groupmain, true), 
			DARK_CRYSTAL_BRICK = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "dark_crystal_brick", ModCreativeTabs.groupmain, true), 
			DARK_CRYSTAL_TILING = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "dark_crystal_tiling", ModCreativeTabs.groupmain, true),
			DARK_CRYSTAL_FENCE = new CustomFenceBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "dark_crystal_fence", ModCreativeTabs.groupmain, true), 
			DARK_CRYSTAL_LAMP = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F).lightValue(15), "dark_crystal_lamp", ModCreativeTabs.groupmain, true),
			DARK_CRYSTAL_DOOR = new CustomDoorBlock(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "dark_crystal_door", ModCreativeTabs.groupmain, true), 
			DARK_CRYSTAL_Stairs = new CustomStairsBlock(DARK_CRYSTAL_BLOCK.getDefaultState(), Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.2F), "dark_crystal_stairs", ModCreativeTabs.groupmain, true), 
			DARK_CRYSTAL_SLAB = new CustomSlabBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.2F), "dark_crystal_slab", ModCreativeTabs.groupmain, true), 
							
			
			WHITE_CRYSTAL_GLASSPANE_CROSS = new GlassPanePillar(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "white_crystal_glasspane_cross", ModCreativeTabs.groupmain, true), 
			WHITE_CRYSTAL_GLASSPANE_MIDDLE = new GlassPanePillar(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "white_crystal_glasspane_middle", ModCreativeTabs.groupmain, true), 
			WHITE_CRYSTAL_GLASSPANE_END = new GlassPaneEnd(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "white_crystal_glasspane_end",Color.WHITE.getRGB(), ModCreativeTabs.groupmain, true),
			WHITE_CRYSTAL_BLOCK = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "white_crystal_block", ModCreativeTabs.groupmain, true), 
			WHITE_CRYSTAL_BRICK = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "white_crystal_brick", ModCreativeTabs.groupmain, true), 
			WHITE_CRYSTAL_TILING = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "white_crystal_tiling", ModCreativeTabs.groupmain, true),
			WHITE_CRYSTAL_FENCE = new CustomFenceBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "white_crystal_fence", ModCreativeTabs.groupmain, true), 
			WHITE_CRYSTAL_DOOR = new CustomDoorBlock(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "white_crystal_door", ModCreativeTabs.groupmain, true),  
			WHITE_CRYSTAL_Stairs = new CustomStairsBlock(WHITE_CRYSTAL_BLOCK.getDefaultState(), Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.2F), "white_crystal_stairs", ModCreativeTabs.groupmain, true),  
			WHITE_CRYSTAL_SLAB = new CustomSlabBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.2F), "white_crystal_slab", ModCreativeTabs.groupmain, true); 
			
	
	
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
		
		BIOMASSFLUIDBLOCK.setRegistryName("biomassblock"); 
		registry.register(BIOMASSFLUIDBLOCK);
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
