package net.kaneka.planttech2.registries;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.*;
import net.kaneka.planttech2.blocks.baseclasses.*;
import net.kaneka.planttech2.blocks.machines.*;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks
{
	public static List<BaseBlock> BLOCKS = new ArrayList<BaseBlock>();
	public static List<Block> SPECIAL_RENDER_BLOCKS = new ArrayList<>();
	public static List<BaseBlock> BLOCKITEMS = new ArrayList<BaseBlock>();
	public static Block BIOMASSFLUIDBLOCK = new FlowingFluidBlock(() -> ModFluids.BIOMASS, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()).setRegistryName("biomassfluid_block");

	public static BaseBlock
			CABLE = new TestCableBlock(),
			CARVER = new CarverBlock(),
			CHIPALYZER = new MachineFacingBlock("chipalyzer", ModCreativeTabs.groupmachines),
			COMPRESSOR = new MachineFacingBlock("compressor", ModCreativeTabs.groupmachines), 
			CROPBARS = new CropBarsBlock(),
	        DANCIUM_BLOCK = new BaseBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F), "dancium_block", ModCreativeTabs.groupblocks, true),
	        DNA_CLEANER = new MachineFacingBlock("dna_cleaner", ModCreativeTabs.groupmachines), 
	        DNA_COMBINER = new MachineFacingBlock("dna_combiner", ModCreativeTabs.groupmachines),
	        DNA_EXTRACTOR = new MachineFacingBlock("dna_extractor", ModCreativeTabs.groupmachines), 
	        DNA_REMOVER = new MachineFacingBlock("dna_remover", ModCreativeTabs.groupmachines),
	        ELECTRIC_FENCE = new ElectricFence(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(30.0F), "electric_fence", ModCreativeTabs.groupblocks, true),
			ELECTRIC_FENCE_TOP = new ElectricFenceTop(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(30.0F), "electric_fence_top", ModCreativeTabs.groupblocks, true),
			ELECTRIC_FENCE_GATE = new ElectricFenceGate(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(30.0F), "electric_fence_gate", ModCreativeTabs.groupblocks, true),
	        ENERGYSTORAGE = new EnergyStorageBlock(),
	        ENERGY_SUPPLIER = new EnergySupplierBlock("energy_supplier", ModCreativeTabs.groupmachines),
			FIBRE_FENCE = new BaseBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD), "fibre_fence", ModCreativeTabs.groupblocks, true),
	        IDENTIFIER = new MachineFacingBlock("identifier", ModCreativeTabs.groupmachines),
	        INFUSER = new MachineFacingBlock("infuser", ModCreativeTabs.groupmachines),
	        KANEKIUM_BLOCK = new BaseBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F), "kanekium_block", ModCreativeTabs.groupblocks, true),
	        KINNOIUM_BLOCK = new BaseBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F), "kinnoium_block", ModCreativeTabs.groupblocks, true),
	        LENTHURIUM_BLOCK = new BaseBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F), "lenthurium_block", ModCreativeTabs.groupblocks, true),
	        MACHINEBULBREPROCESSOR = new MachineBaseBlock("machinebulbreprocessor", ModCreativeTabs.groupmachines),
	        MACHINESHELL_IRON = new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(0.9F).notSolid(), "machineshell_iron", ModCreativeTabs.groupmain, true, true),
		    MACHINESHELL_PLANTIUM = new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(0.9F).notSolid(), "machineshell_plantium", ModCreativeTabs.groupmain, true, true),
	        MEGAFURNACE = new MachineFacingBlock("mega_furnace", ModCreativeTabs.groupmachines), 
	        PLANTFARM = new MachineBaseBlock("plantfarm", ModCreativeTabs.groupmachines),
	        PLANTIUM_BLOCK = new BaseBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F), "plantium_block", ModCreativeTabs.groupblocks, true),
	        SEEDCONSTRUCTOR = new MachineFacingBlock("seedconstructor", ModCreativeTabs.groupmachines),
	        SEEDSQUEEZER = new MachineFacingBlock("seedsqueezer", ModCreativeTabs.groupmachines),
	        SOLARGENERATOR = new MachineBaseBlock("solargenerator", ModCreativeTabs.groupmachines),
	        PLANTTOPIA_TELEPORTER = new MachineBaseBlock("planttopia_teleporter", ModCreativeTabs.groupmachines),
	        PLANTTOPIA_TELEPORTER_END = new MachineTeleporterEndBlock("planttopia_teleporter_end", Block.Properties.create(Material.IRON).hardnessAndResistance(0.5f), ModCreativeTabs.groupmachines),
	        UNIVERSAL_SOIL = new BaseBlock(Block.Properties.create(Material.EARTH).hardnessAndResistance(0.5F), "universal_soil", ModCreativeTabs.groupblocks, true),
			UNIVERSAL_SOIL_INFUSED = new BaseBlock(Block.Properties.create(Material.EARTH).hardnessAndResistance(0.7F), "universal_soil_infused", ModCreativeTabs.groupblocks, true),
	        DARK_CRYSTAL_GLASSPANE_CROSS = new GlassPanePillar(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "dark_crystal_glasspane_cross", ModCreativeTabs.groupblocks, true),
	        DARK_CRYSTAL_GLASSPANE_MIDDLE = new GlassPanePillar(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "dark_crystal_glasspane_middle", ModCreativeTabs.groupblocks, true),
	        DARK_CRYSTAL_GLASSPANE_END = new GlassPaneEnd(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "dark_crystal_glasspane_end",Color.WHITE.getRGB(), ModCreativeTabs.groupblocks, true),
			DARK_CRYSTAL_BLOCK = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "dark_crystal_block", ModCreativeTabs.groupblocks, true),
			DARK_CRYSTAL_BRICK = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "dark_crystal_brick", ModCreativeTabs.groupblocks, true),
			DARK_CRYSTAL_TILING = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "dark_crystal_tiling", ModCreativeTabs.groupblocks, true),
			DARK_CRYSTAL_FENCE = new CustomFenceBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "dark_crystal_fence", ModCreativeTabs.groupblocks, true),
			DARK_CRYSTAL_LAMP = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F).setLightLevel((p)->{return 15;}).notSolid(), "dark_crystal_lamp", ModCreativeTabs.groupblocks, true),
			DARK_CRYSTAL_DOOR = new CustomDoorBlock(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "dark_crystal_door", ModCreativeTabs.groupblocks, true),
			DARK_CRYSTAL_STAIRS = new CustomStairsBlock(DARK_CRYSTAL_BLOCK.getDefaultState(), Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.2F), "dark_crystal_stairs", ModCreativeTabs.groupblocks, true),
			DARK_CRYSTAL_SLAB = new CustomSlabBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.2F), "dark_crystal_slab", ModCreativeTabs.groupblocks, true),
			DARK_CRYSTAL_ORE = new BaseOreBlock(Block.Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0.2F), "dark_crystal_ore", 1, 3), 
			

			WHITE_CRYSTAL_GLASSPANE_CROSS = new GlassPanePillar(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "white_crystal_glasspane_cross", ModCreativeTabs.groupblocks, true),
			WHITE_CRYSTAL_GLASSPANE_MIDDLE = new GlassPanePillar(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "white_crystal_glasspane_middle", ModCreativeTabs.groupblocks, true),
			WHITE_CRYSTAL_GLASSPANE_END = new GlassPaneEnd(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "white_crystal_glasspane_end",Color.WHITE.getRGB(), ModCreativeTabs.groupblocks, true),
			WHITE_CRYSTAL_BLOCK = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "white_crystal_block", ModCreativeTabs.groupblocks, true),
			WHITE_CRYSTAL_BRICK = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "white_crystal_brick", ModCreativeTabs.groupblocks, true),
			WHITE_CRYSTAL_TILING = new BaseBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "white_crystal_tiling", ModCreativeTabs.groupblocks, true),
			WHITE_CRYSTAL_FENCE = new CustomFenceBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F), "white_crystal_fence", ModCreativeTabs.groupblocks, true),
			WHITE_CRYSTAL_DOOR = new CustomDoorBlock(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), "white_crystal_door", ModCreativeTabs.groupblocks, true),
			WHITE_CRYSTAL_STAIRS = new CustomStairsBlock(WHITE_CRYSTAL_BLOCK.getDefaultState(), Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.2F), "white_crystal_stairs", ModCreativeTabs.groupblocks, true),
			WHITE_CRYSTAL_SLAB = new CustomSlabBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.2F), "white_crystal_slab", ModCreativeTabs.groupblocks, true),
			WHITE_CRYSTAL_ORE = new BaseOreBlock(Block.Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0.2F), "white_crystal_ore", 1, 3), 
			
			
			WALL_LIGHT = new WallLight(Block.Properties.create(Material.ROCK).sound(SoundType.GLASS), "wall_light", ModCreativeTabs.groupblocks, true),

			TEST_BLOCK = new ObtainableNaturalPlants("testblock", true, 4, 6),

			MUTATED_DANDELION = new ObtainableNaturalPlants("mutated_dandelion", true),
			MUTATED_POPPY = new ObtainableNaturalPlants("mutated_poppy", true),
			MUTATED_BLUE_ORCHID = new ObtainableNaturalPlants("mutated_blue_orchid", true),
			MUTATED_ALLIUM = new ObtainableNaturalPlants("mutated_allium", true),
			MUTATED_AZURE_BLUET = new ObtainableNaturalPlants("mutated_azure_bluet", true),
			MUTATED_RED_TULIP = new ObtainableNaturalPlants("mutated_red_tulip", true),
			MUTATED_ORANGE_TULIP = new ObtainableNaturalPlants("mutated_orange_tulip", true),
			MUTATED_WHITE_TULIP = new ObtainableNaturalPlants("mutated_white_tulip", true),
			MUTATED_PINK_TULIP = new ObtainableNaturalPlants("mutated_pink_tulip", true),
			MUTATED_OXEYE_DAISY = new ObtainableNaturalPlants("mutated_oxeye_daisy", true),
			MUTATED_CORNFLOWER = new ObtainableNaturalPlants("mutated_cornflower", true),
			MUTATED_LILY_OF_THE_VALLEY = new ObtainableNaturalPlants("mutated_lily_of_the_valley", true),

			MUTATED_LILAC = new ObtainableTallBushBlock("mutated_lilac", true),
			MUTATED_ROSE_BUSH = new ObtainableTallBushBlock("mutated_rose_bush", true),
			MUTATED_PEONY = new ObtainableTallBushBlock("mutated_peony", true);

	public static BaseBlock MACHINEBULBREPROCESSOR_GROWING = new GrowingBlock("machinebulbreprocessor_growing", ModBlocks.MACHINEBULBREPROCESSOR, true),
							MACHINESHELL_IRON_GROWING = new GrowingBlock("machineshell_iron_growing", ModBlocks.MACHINESHELL_IRON, false),
							MACHINESHELL_PLANTIUM_GROWING = new GrowingBlock("machineshell_plantium_growing", ModBlocks.MACHINESHELL_PLANTIUM, false),
							SEEDSQUEEZER_GROWING = new FacingGrowingBlock("seedsqueezer_growing", ModBlocks.SEEDSQUEEZER, true),
                			CHIPALYZER_GROWING = new FacingGrowingBlock("chipalyzer_growing", ModBlocks.CHIPALYZER, true),
                			COMPRESSOR_GROWING = new FacingGrowingBlock("compressor_growing", ModBlocks.COMPRESSOR, true),
                	        DNA_CLEANER_GROWING = new FacingGrowingBlock("dna_cleaner_growing", ModBlocks.DNA_CLEANER, true),
                	        DNA_COMBINER_GROWING = new FacingGrowingBlock("dna_combiner_growing", ModBlocks.DNA_COMBINER, true),
                	        DNA_EXTRACTOR_GROWING = new FacingGrowingBlock("dna_extractor_growing", ModBlocks.DNA_EXTRACTOR, true),
                	        DNA_REMOVER_GROWING = new FacingGrowingBlock("dna_remover_growing", ModBlocks.DNA_REMOVER, true),
                	        IDENTIFIER_GROWING = new FacingGrowingBlock("identifier_growing", ModBlocks.IDENTIFIER, true),
                	        INFUSER_GROWING = new FacingGrowingBlock("infuser_growing", ModBlocks.INFUSER, true),
                	        MEGAFURNACE_GROWING = new FacingGrowingBlock("mega_furnace_growing", ModBlocks.MEGAFURNACE, true),
                	        PLANTFARM_GROWING = new GrowingBlock("plantfarm_growing", ModBlocks.PLANTFARM, true),
                	        ENERGY_SUPPLIER_GROWING = new GrowingBlock("energy_supplier_growing", ModBlocks.ENERGY_SUPPLIER, true),
                	        SEEDCONSTRUCTOR_GROWING = new FacingGrowingBlock("seedconstructor_growing", ModBlocks.SEEDCONSTRUCTOR, true),
                	        SOLARGENERATOR_GROWING = new GrowingBlock("solargenerator_growing", ModBlocks.SOLARGENERATOR, true);

	
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
		SPECIAL_RENDER_BLOCKS.addAll(ModBlocks.CROPS.values());
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
