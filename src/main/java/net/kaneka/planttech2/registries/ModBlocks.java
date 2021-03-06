package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.*;
import net.kaneka.planttech2.blocks.baseclasses.BaseOreBlock;
import net.kaneka.planttech2.blocks.baseclasses.CustomDoorBlock;
import net.kaneka.planttech2.blocks.baseclasses.CustomFenceBlock;
import net.kaneka.planttech2.blocks.baseclasses.ObtainableNaturalPlants;
import net.kaneka.planttech2.blocks.entity.machine.*;
import net.kaneka.planttech2.blocks.machines.*;
import net.kaneka.planttech2.crops.CropEntry;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import static net.kaneka.planttech2.utilities.ModCreativeTabs.*;

@ObjectHolder(PlantTechMain.MODID)
public class ModBlocks
{
	public static List<Supplier<? extends Block>> SPECIAL_RENDER_BLOCKS_CUTOUT = new ArrayList<>();
	public static List<Supplier<? extends Block>> SPECIAL_RENDER_BLOCKS_TRANSLUCENT = new ArrayList<>();
	public static List<Supplier<? extends Item>> BLOCK_ITEM_SUPPLIERS = new ArrayList<>();
	public static List<Hedge> HEDGE_BLOCKS = new ArrayList<>();
	public static HashMap<String, CropBaseBlock> CROPS = new HashMap<>();

	@ObjectHolder("biomassfluid_block") public static LiquidBlock BIOMASSFLUIDBLOCK;

	@ObjectHolder("cable") public static TestCableBlock CABLE;
	@ObjectHolder("carver") public static CarverBlock CARVER;
	@ObjectHolder("chipalyzer") public static MachineFacingBlock CHIPALYZER;
	@ObjectHolder("compressor") public static MachineFacingBlock COMPRESSOR;
	@ObjectHolder("crop_aura_generator") public static MachineFacingBlock CROP_AURA_GENERATOR;
	@ObjectHolder("cropbars") public static CropBarsBlock CROPBARS;
	@ObjectHolder("dancium_block") public static Block DANCIUM_BLOCK;
	@ObjectHolder("dna_cleaner") public static MachineFacingBlock DNA_CLEANER;
	@ObjectHolder("dna_combiner") public static MachineFacingBlock DNA_COMBINER;
	@ObjectHolder("dna_extractor") public static MachineFacingBlock DNA_EXTRACTOR;
	@ObjectHolder("dna_remover") public static MachineFacingBlock DNA_REMOVER;
	@ObjectHolder("electric_fence") public static ElectricFence ELECTRIC_FENCE;
	@ObjectHolder("electric_fence_top") public static ElectricFenceTop ELECTRIC_FENCE_TOP;
	@ObjectHolder("electric_fence_gate") public static ElectricFenceGate ELECTRIC_FENCE_GATE;
	@ObjectHolder("energystorage") public static EnergyStorageBlock ENERGYSTORAGE;
	@ObjectHolder("energy_supplier") public static EnergySupplierBlock ENERGY_SUPPLIER;
	@ObjectHolder("fibre_fence") public static Block FIBRE_FENCE;
	@ObjectHolder("identifier") public static MachineFacingBlock IDENTIFIER;
	@ObjectHolder("infuser") public static MachineFacingBlock INFUSER;
	@ObjectHolder("kanekium_block") public static Block KANEKIUM_BLOCK;
	@ObjectHolder("kinnoium_block") public static Block KINNOIUM_BLOCK;
	@ObjectHolder("lenthurium_block") public static Block LENTHURIUM_BLOCK;
	@ObjectHolder("machinebulbreprocessor") public static Block MACHINEBULBREPROCESSOR;
	@ObjectHolder("machineshell_iron") public static Block MACHINESHELL_IRON;
	@ObjectHolder("machineshell_plantium") public static Block MACHINESHELL_PLANTIUM;
	@ObjectHolder("mega_furnace") public static MachineFacingBlock MEGAFURNACE;
	@ObjectHolder("plantfarm") public static MachineBaseBlock PLANTFARM;
	@ObjectHolder("plantium_block") public static Block PLANTIUM_BLOCK;
	@ObjectHolder("seedconstructor") public static MachineFacingBlock SEEDCONSTRUCTOR;
	@ObjectHolder("seedsqueezer") public static MachineFacingBlock SEEDSQUEEZER;
	@ObjectHolder("solargenerator") public static MachineBaseBlock SOLARGENERATOR;
	@ObjectHolder("planttopia_teleporter") public static MachineBaseBlock PLANTTOPIA_TELEPORTER;
	@ObjectHolder("planttopia_teleporter_end") public static MachineTeleporterEndBlock PLANTTOPIA_TELEPORTER_END;
	@ObjectHolder("universal_soil") public static Block UNIVERSAL_SOIL;
	@ObjectHolder("universal_soil_infused") public static Block UNIVERSAL_SOIL_INFUSED;

	@ObjectHolder("dark_crystal_glasspane_cross") public static GlassPanePillar DARK_CRYSTAL_GLASSPANE_CROSS;
	@ObjectHolder("dark_crystal_glasspane_middle") public static GlassPanePillar DARK_CRYSTAL_GLASSPANE_MIDDLE;
	@ObjectHolder("dark_crystal_glasspane_end") public static GlassPaneEnd DARK_CRYSTAL_GLASSPANE_END;
	@ObjectHolder("dark_crystal_block") public static Block DARK_CRYSTAL_BLOCK;
	@ObjectHolder("dark_crystal_brick") public static Block DARK_CRYSTAL_BRICK;
	@ObjectHolder("dark_crystal_tiling") public static Block DARK_CRYSTAL_TILING;
	@ObjectHolder("dark_crystal_fence") public static CustomFenceBlock DARK_CRYSTAL_FENCE;
	@ObjectHolder("dark_crystal_lamp") public static Block DARK_CRYSTAL_LAMP;
	@ObjectHolder("dark_crystal_door") public static CustomDoorBlock DARK_CRYSTAL_DOOR;
	@ObjectHolder("dark_crystal_stairs") public static StairBlock DARK_CRYSTAL_STAIRS;
	@ObjectHolder("dark_crystal_slab") public static SlabBlock DARK_CRYSTAL_SLAB;
	@ObjectHolder("dark_crystal_ore") public static BaseOreBlock DARK_CRYSTAL_ORE;

	@ObjectHolder("white_crystal_glasspane_cross") public static GlassPanePillar WHITE_CRYSTAL_GLASSPANE_CROSS;
	@ObjectHolder("white_crystal_glasspane_middle") public static GlassPanePillar WHITE_CRYSTAL_GLASSPANE_MIDDLE;
	@ObjectHolder("white_crystal_glasspane_end") public static GlassPaneEnd WHITE_CRYSTAL_GLASSPANE_END;
	@ObjectHolder("white_crystal_block") public static Block WHITE_CRYSTAL_BLOCK;
	@ObjectHolder("white_crystal_brick") public static Block WHITE_CRYSTAL_BRICK;
	@ObjectHolder("white_crystal_tiling") public static Block WHITE_CRYSTAL_TILING;
	@ObjectHolder("white_crystal_fence") public static CustomFenceBlock WHITE_CRYSTAL_FENCE;
	@ObjectHolder("white_crystal_door") public static CustomDoorBlock WHITE_CRYSTAL_DOOR;
	@ObjectHolder("white_crystal_stairs") public static StairBlock WHITE_CRYSTAL_STAIRS;
	@ObjectHolder("white_crystal_slab") public static SlabBlock WHITE_CRYSTAL_SLAB;
	@ObjectHolder("white_crystal_ore") public static BaseOreBlock WHITE_CRYSTAL_ORE;

	@ObjectHolder("wall_light") public static WallLight WALL_LIGHT;

	@ObjectHolder("testblock") public static ObtainableNaturalPlants TEST_BLOCK;
	
	@ObjectHolder("hedge_oak_spruce_dirt") public static Hedge OAK_SPRUCE_DIRT_HEDGE;
	@ObjectHolder("hedge_oak_birch_dirt") public static Hedge OAK_BIRCH_DIRT_HEDGE;
	@ObjectHolder("hedge_oak_jungle_dirt") public static Hedge OAK_JUNGLEDIRT_HEDGE;
	@ObjectHolder("hedge_oak_acacia_dirt") public static Hedge OAK_ACACIADIRT_HEDGE;
	@ObjectHolder("hedge_oak_dark_oak_dirt") public static Hedge OAK_DARK_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_spruce_oak_dirt") public static Hedge SPRUCE_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_spruce_spruce_dirt") public static Hedge SPRUCE_SPRUCE_DIRT_HEDGE;
	@ObjectHolder("hedge_spruce_birch_dirt") public static Hedge SPRUCE_BIRCH_DIRT_HEDGE;
	@ObjectHolder("hedge_spruce_jungle_dirt") public static Hedge SPRUCE_JUNGLE_DIRT_HEDGE;
	@ObjectHolder("hedge_spruce_acacia_dirt") public static Hedge SPRUCE_ACACIA_DIRT_HEDGE;
	@ObjectHolder("hedge_spruce_dark_oak_dirt") public static Hedge SPRUCE_DARK_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_birch_oak_dirt") public static Hedge BIRCH_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_birch_spruce_dirt") public static Hedge BIRCH_SPRUCE_DIRT_HEDGE;
	@ObjectHolder("hedge_birch_birch_dirt") public static Hedge BIRCH_BIRCH_DIRT_HEDGE;
	@ObjectHolder("hedge_birch_jungle_dirt") public static Hedge BIRCH_JUNGLE_DIRT_HEDGE;
	@ObjectHolder("hedge_birch_acacia_dirt") public static Hedge BIRCH_ACACIA_DIRT_HEDGE;
	@ObjectHolder("hedge_birch_dark_oak_dirt") public static Hedge BIRCH_DARK_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_jungle_oak_dirt") public static Hedge JUNGLE_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_jungle_spruce_dirt") public static Hedge JUNGLE_SPRUCE_DIRT_HEDGE;
	@ObjectHolder("hedge_jungle_birch_dirt") public static Hedge JUNGLE_BIRCH_DIRT_HEDGE;
	@ObjectHolder("hedge_jungle_jungle_dirt") public static Hedge JUNGLE_JUNGLE_DIRT_HEDGE;
	@ObjectHolder("hedge_jungle_acacia_dirt") public static Hedge JUNGLE_ACACIA_DIRT_HEDGE;
	@ObjectHolder("hedge_jungle_dark_oak_dirt") public static Hedge JUNGLE_DARK_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_acacia_oak_dirt") public static Hedge ACACIA_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_acacia_spruce_dirt") public static Hedge ACACIA_SPRUCE_DIRT_HEDGE;
	@ObjectHolder("hedge_acacia_birch_dirt") public static Hedge ACACIA_BIRCH_DIRT_HEDGE;
	@ObjectHolder("hedge_acacia_jungle_dirt") public static Hedge ACACIA_JUNGLE_DIRT_HEDGE;
	@ObjectHolder("hedge_acacia_acacia_dirt") public static Hedge ACACIA_ACACIA_DIRT_HEDGE;
	@ObjectHolder("hedge_acacia_dark_oak_dirt") public static Hedge ACACIA_DARK_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_dark_oak_oak_dirt") public static Hedge DARK_OAK_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_dark_oak_spruce_dirt") public static Hedge DARK_OAK_SPRUCE_DIRT_HEDGE;
	@ObjectHolder("hedge_dark_oak_birch_dirt") public static Hedge DARK_OAK_BIRCH_DIRT_HEDGE;
	@ObjectHolder("hedge_dark_oak_jungle_dirt") public static Hedge DARK_OAK_JUNGLE_DIRT_HEDGE;
	@ObjectHolder("hedge_dark_oak_acacia_dirt") public static Hedge DARK_OAK_ACACIA_DIRT_HEDGE;
	@ObjectHolder("hedge_dark_oak_dark_oak_dirt") public static Hedge DARK_OAK_DARK_OAK_DIRT_HEDGE;
	
	@ObjectHolder("hedge_oak_spruce_grass") public static Hedge OAK_SPRUCE_GRASS_HEDGE;
	@ObjectHolder("hedge_oak_birch_grass") public static Hedge OAK_BIRCH_GRASS_HEDGE;
	@ObjectHolder("hedge_oak_jungle_grass") public static Hedge OAK_JUNGLE_GRASS_HEDGE;
	@ObjectHolder("hedge_oak_acacia_grass") public static Hedge OAK_ACACIA_GRASS_HEDGE;
	@ObjectHolder("hedge_oak_dark_oak_grass") public static Hedge OAK_DARK_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_spruce_oak_grass") public static Hedge SPRUCE_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_spruce_spruce_grass") public static Hedge SPRUCE_SPRUCE_GRASS_HEDGE;
	@ObjectHolder("hedge_spruce_birch_grass") public static Hedge SPRUCE_BIRCH_GRASS_HEDGE;
	@ObjectHolder("hedge_spruce_jungle_grass") public static Hedge SPRUCE_JUNGLE_GRASS_HEDGE;
	@ObjectHolder("hedge_spruce_acacia_grass") public static Hedge SPRUCE_ACACIA_GRASS_HEDGE;
	@ObjectHolder("hedge_spruce_dark_oak_grass") public static Hedge SPRUCE_DARK_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_birch_oak_grass") public static Hedge BIRCH_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_birch_spruce_grass") public static Hedge BIRCH_SPRUCE_GRASS_HEDGE;
	@ObjectHolder("hedge_birch_birch_grass") public static Hedge BIRCH_BIRCH_GRASS_HEDGE;
	@ObjectHolder("hedge_birch_jungle_grass") public static Hedge BIRCH_JUNGLE_GRASS_HEDGE;
	@ObjectHolder("hedge_birch_acacia_grass") public static Hedge BIRCH_ACACIA_GRASS_HEDGE;
	@ObjectHolder("hedge_birch_dark_oak_grass") public static Hedge BIRCH_DARK_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_jungle_oak_grass") public static Hedge JUNGLE_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_jungle_spruce_grass") public static Hedge JUNGLE_SPRUCE_GRASS_HEDGE;
	@ObjectHolder("hedge_jungle_birch_grass") public static Hedge JUNGLE_BIRCH_GRASS_HEDGE;
	@ObjectHolder("hedge_jungle_jungle_grass") public static Hedge JUNGLE_JUNGLE_GRASS_HEDGE;
	@ObjectHolder("hedge_jungle_acacia_grass") public static Hedge JUNGLE_ACACIA_GRASS_HEDGE;
	@ObjectHolder("hedge_jungle_dark_oak_grass") public static Hedge JUNGLE_DARK_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_acacia_oak_grass") public static Hedge ACACIA_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_acacia_spruce_grass") public static Hedge ACACIA_SPRUCE_GRASS_HEDGE;
	@ObjectHolder("hedge_acacia_birch_grass") public static Hedge ACACIA_BIRCH_GRASS_HEDGE;
	@ObjectHolder("hedge_acacia_jungle_grass") public static Hedge ACACIA_JUNGLE_GRASS_HEDGE;
	@ObjectHolder("hedge_acacia_acacia_grass") public static Hedge ACACIA_ACACIA_GRASS_HEDGE;
	@ObjectHolder("hedge_acacia_dark_oak_grass") public static Hedge ACACIA_DARK_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_dark_oak_oak_grass") public static Hedge DARK_OAK_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_dark_oak_spruce_grass") public static Hedge DARK_OAK_SPRUCE_GRASS_HEDGE;
	@ObjectHolder("hedge_dark_oak_birch_grass") public static Hedge DARK_OAK_BIRCH_GRASS_HEDGE;
	@ObjectHolder("hedge_dark_oak_jungle_grass") public static Hedge DARK_OAK_JUNGLE_GRASS_HEDGE;
	@ObjectHolder("hedge_dark_oak_acacia_grass") public static Hedge DARK_OAK_ACACIA_GRASS_HEDGE;
	@ObjectHolder("hedge_dark_oak_dark_oak_grass") public static Hedge DARK_OAK_DARK_OAK_GRASS_HEDGE;
	
	@ObjectHolder("hedge_oak_spruce_podzol") public static Hedge OAK_SPRUCE_PODZOL_HEDGE;
	@ObjectHolder("hedge_oak_birch_podzol") public static Hedge OAK_BIRCH_PODZOL_HEDGE;
	@ObjectHolder("hedge_oak_jungle_podzol") public static Hedge OAK_JUNGLE_PODZOL_HEDGE;
	@ObjectHolder("hedge_oak_acacia_podzol") public static Hedge OAK_ACACIA_PODZOL_HEDGE;
	@ObjectHolder("hedge_oak_dark_oak_podzol") public static Hedge OAK_DARK_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_spruce_oak_podzol") public static Hedge SPRUCE_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_spruce_spruce_podzol") public static Hedge SPRUCE_SPRUCE_PODZOL_HEDGE;
	@ObjectHolder("hedge_spruce_birch_podzol") public static Hedge SPRUCE_BIRCH_PODZOL_HEDGE;
	@ObjectHolder("hedge_spruce_jungle_podzol") public static Hedge SPRUCE_JUNGLE_PODZOL_HEDGE;
	@ObjectHolder("hedge_spruce_acacia_podzol") public static Hedge SPRUCE_ACACIA_PODZOL_HEDGE;
	@ObjectHolder("hedge_spruce_dark_oak_podzol") public static Hedge SPRUCE_DARK_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_birch_oak_podzol") public static Hedge BIRCH_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_birch_spruce_podzol") public static Hedge BIRCH_SPRUCE_PODZOL_HEDGE;
	@ObjectHolder("hedge_birch_birch_podzol") public static Hedge BIRCH_BIRCH_PODZOL_HEDGE;
	@ObjectHolder("hedge_birch_jungle_podzol") public static Hedge BIRCH_JUNGLE_PODZOL_HEDGE;
	@ObjectHolder("hedge_birch_acacia_podzol") public static Hedge BIRCH_ACACIA_PODZOL_HEDGE;
	@ObjectHolder("hedge_birch_dark_oak_podzol") public static Hedge BIRCH_DARK_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_jungle_oak_podzol") public static Hedge JUNGLE_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_jungle_spruce_podzol") public static Hedge JUNGLE_SPRUCE_PODZOL_HEDGE;
	@ObjectHolder("hedge_jungle_birch_podzol") public static Hedge JUNGLE_BIRCH_PODZOL_HEDGE;
	@ObjectHolder("hedge_jungle_jungle_podzol") public static Hedge JUNGLE_JUNGLE_PODZOL_HEDGE;
	@ObjectHolder("hedge_jungle_acacia_podzol") public static Hedge JUNGLE_ACACIA_PODZOL_HEDGE;
	@ObjectHolder("hedge_jungle_dark_oak_podzol") public static Hedge JUNGLE_DARK_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_acacia_oak_podzol") public static Hedge ACACIA_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_acacia_spruce_podzol") public static Hedge ACACIA_SPRUCE_PODZOL_HEDGE;
	@ObjectHolder("hedge_acacia_birch_podzol") public static Hedge ACACIA_BIRCH_PODZOL_HEDGE;
	@ObjectHolder("hedge_acacia_jungle_podzol") public static Hedge ACACIA_JUNGLE_PODZOL_HEDGE;
	@ObjectHolder("hedge_acacia_acacia_podzol") public static Hedge ACACIA_ACACIA_PODZOL_HEDGE;
	@ObjectHolder("hedge_acacia_dark_oak_podzol") public static Hedge ACACIA_DARK_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_dark_oak_oak_podzol") public static Hedge DARK_OAK_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_dark_oak_spruce_podzol") public static Hedge DARK_OAK_SPRUCE_PODZOL_HEDGE;
	@ObjectHolder("hedge_dark_oak_birch_podzol") public static Hedge DARK_OAK_BIRCH_PODZOL_HEDGE;
	@ObjectHolder("hedge_dark_oak_jungle_podzol") public static Hedge DARK_OAK_JUNGLE_PODZOL_HEDGE;
	@ObjectHolder("hedge_dark_oak_acacia_podzol") public static Hedge DARK_OAK_ACACIA_PODZOL_HEDGE;
	@ObjectHolder("hedge_dark_oak_dark_oak_podzol") public static Hedge DARK_OAK_DARK_OAK_PODZOL_HEDGE;

	@ObjectHolder("mutated_dandelion") public static ObtainableNaturalPlants MUTATED_DANDELION;
	@ObjectHolder("mutated_poppy") public static ObtainableNaturalPlants MUTATED_POPPY;
	@ObjectHolder("mutated_blue_orchid") public static ObtainableNaturalPlants MUTATED_BLUE_ORCHID;
	@ObjectHolder("mutated_allium") public static ObtainableNaturalPlants MUTATED_ALLIUM;
	@ObjectHolder("mutated_azure_bluet") public static ObtainableNaturalPlants MUTATED_AZURE_BLUET;
	@ObjectHolder("mutated_red_tulip") public static ObtainableNaturalPlants MUTATED_RED_TULIP;
	@ObjectHolder("mutated_orange_tulip") public static ObtainableNaturalPlants MUTATED_ORANGE_TULIP;
	@ObjectHolder("mutated_white_tulip") public static ObtainableNaturalPlants MUTATED_WHITE_TULIP;
	@ObjectHolder("mutated_pink_tulip") public static ObtainableNaturalPlants MUTATED_PINK_TULIP;
	@ObjectHolder("mutated_oxeye_daisy") public static ObtainableNaturalPlants MUTATED_OXEYE_DAISY;
	@ObjectHolder("mutated_cornflower") public static ObtainableNaturalPlants MUTATED_CORNFLOWER;
	@ObjectHolder("mutated_lily_of_the_valley") public static ObtainableNaturalPlants MUTATED_LILY_OF_THE_VALLEY;
	@ObjectHolder("mutated_lilac") public static ObtainableTallBushBlock MUTATED_LILAC;
	@ObjectHolder("mutated_rose_bush") public static ObtainableTallBushBlock MUTATED_ROSE_BUSH;
	@ObjectHolder("mutated_peony") public static ObtainableTallBushBlock MUTATED_PEONY;

	@ObjectHolder("machinebulbreprocessor_growing") public static GrowingBlock MACHINEBULBREPROCESSOR_GROWING;
	@ObjectHolder("machineshell_iron_growing") public static GrowingBlock MACHINESHELL_IRON_GROWING;
	@ObjectHolder("machineshell_plantium_growing") public static GrowingBlock MACHINESHELL_PLANTIUM_GROWING;
	@ObjectHolder("seedsqueezer_growing") public static GrowingBlock SEEDSQUEEZER_GROWING;
	@ObjectHolder("chipalyzer_growing") public static GrowingBlock CHIPALYZER_GROWING;
	@ObjectHolder("compressor_growing") public static GrowingBlock COMPRESSOR_GROWING;
	@ObjectHolder("dna_cleaner_growing") public static GrowingBlock DNA_CLEANER_GROWING;
	@ObjectHolder("dna_combiner_growing") public static GrowingBlock DNA_COMBINER_GROWING;
	@ObjectHolder("dna_extractor_growing") public static GrowingBlock DNA_EXTRACTOR_GROWING;
	@ObjectHolder("dna_remover_growing") public static GrowingBlock DNA_REMOVER_GROWING;
	@ObjectHolder("identifier_growing") public static GrowingBlock IDENTIFIER_GROWING;
	@ObjectHolder("infuser_growing") public static GrowingBlock INFUSER_GROWING;
	@ObjectHolder("mega_furnace_growing") public static GrowingBlock MEGAFURNACE_GROWING;
	@ObjectHolder("plantfarm_growing") public static GrowingBlock PLANTFARM_GROWING;
	@ObjectHolder("energy_supplier_growing") public static GrowingBlock ENERGY_SUPPLIER_GROWING;
	@ObjectHolder("seedconstructor_growing") public static GrowingBlock SEEDCONSTRUCTOR_GROWING;
	@ObjectHolder("solargenerator_growing") public static GrowingBlock SOLARGENERATOR_GROWING;

	//Terrain Blocks
	@ObjectHolder("infused_ice") public static Block INFUSED_ICE;
	@ObjectHolder("infused_packed_ice") public static Block INFUSED_PACKED_ICE;
	@ObjectHolder("infused_blue_ice") public static Block INFUSED_BLUE_ICE;
	@ObjectHolder("black_ice") public static Block BLACK_ICE;
	@ObjectHolder("infused_stone") public static Block INFUSED_STONE;
	@ObjectHolder("infused_cobblestone") public static Block INFUSED_COBBLESTONE;


	public static void register(IForgeRegistry<Block> r)
	{
		r.register(makeBlock("biomassfluid_block", new LiquidBlock(() -> ModFluids.BIOMASS, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops())));

		r.register(makeSpecialWithItem("cable", BLOCKS, new TestCableBlock(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("carver", MAIN, new CarverBlock(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("chipalyzer", MACHINES, new MachineFacingBlock(ChipalyzerBlockEntity::new, PlantTechConstants.MACHINETIER_CHIPALYZER), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("compressor", MACHINES, new MachineFacingBlock(CompressorBlockEntity::new, PlantTechConstants.MACHINETIER_COMPRESSOR), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("crop_aura_generator", MACHINES, new MachineFacingBlock(CropAuraGeneratorBlockEntity::new, PlantTechConstants.MACHINETIER_CROP_AURA_GENERATOR), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("cropbars", MAIN, new CropBarsBlock(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeWithItem("dancium_block", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(0.9F))));
		r.register(makeSpecialWithItem("dna_cleaner", MACHINES, new MachineFacingBlock(DNACleanerBlockEntity::new, PlantTechConstants.MACHINETIER_DNA_CLEANER), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("dna_combiner", MACHINES, new MachineFacingBlock(DNACombinerBlockEntity::new, PlantTechConstants.MACHINETIER_DNA_COMBINER), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("dna_extractor", MACHINES, new MachineFacingBlock(DNAExtractorBlockEntity::new, PlantTechConstants.MACHINETIER_DNA_EXTRACTOR), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("dna_remover", MACHINES, new MachineFacingBlock(DNARemoverBlockEntity::new, PlantTechConstants.MACHINETIER_DNA_REMOVER), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeWithItem("electric_fence", BLOCKS, new ElectricFence(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(30.0F))));
		r.register(makeWithItem("electric_fence_top", BLOCKS, new ElectricFenceTop(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(30.0F))));
		r.register(makeWithItem("electric_fence_gate", BLOCKS, new ElectricFenceGate(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(30.0F))));
		r.register(makeSpecialWithItem("energystorage", MACHINES, new EnergyStorageBlock(EnergyStorageBlockEntity::new), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("energy_supplier", MACHINES, new EnergySupplierBlock(EnergySupplierBlockEntity::new, PlantTechConstants.MACHINETIER_ENERGY_SUPPLIER), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeWithItem("fibre_fence", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD))));
		r.register(makeSpecialWithItem("identifier", MACHINES, new MachineFacingBlock(IdentifierBlockEntity::new, PlantTechConstants.MACHINETIER_IDENTIFIER), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("infuser", MACHINES, new MachineFacingBlock(InfuserBlockEntity::new, PlantTechConstants.MACHINETIER_INFUSER), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeWithItem("kanekium_block", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(0.9F))));
		r.register(makeWithItem("kinnoium_block", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(0.9F))));
		r.register(makeWithItem("lenthurium_block", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(0.9F))));
		r.register(makeSpecialWithItem("machinebulbreprocessor", MACHINES, new MachineBaseBlock(MachineBulbReprocessorBlockEntity::new, PlantTechConstants.MACHINETIER_MACHINEBULBREPROCESSOR), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("machineshell_iron", MAIN, new Block(BlockBehaviour.Properties.of(Material.METAL).strength(0.9F).noOcclusion()), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("machineshell_plantium", MAIN, new Block(BlockBehaviour.Properties.of(Material.METAL).strength(0.9F).noOcclusion()), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mega_furnace", MACHINES, new MachineFacingBlock(MegaFurnaceBlockEntity::new, PlantTechConstants.MACHINETIER_MEGAFURNACE), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("plantfarm", MACHINES, new MachineBaseBlock(PlantFarmBlockEntity::new, PlantTechConstants.MACHINETIER_PLANTFARM), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeWithItem("plantium_block", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(0.9F))));
		r.register(makeSpecialWithItem("seedconstructor", MACHINES, new MachineFacingBlock(SeedConstructorBlockEntity::new, PlantTechConstants.MACHINETIER_SEEDCONSTRUCTOR), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("seedsqueezer", MACHINES, new MachineFacingBlock(SeedSqueezerBlockEntity::new, PlantTechConstants.MACHINETIER_SEEDSQUEEZER), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("solargenerator", MACHINES, new MachineBaseBlock(SolarGeneratorBlockEntity::new, PlantTechConstants.MACHINETIER_SOLARGENERATOR), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("planttopia_teleporter", MACHINES, new MachineBaseBlock(PlantTopiaTeleporterBlockEntity::new), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeWithItem("planttopia_teleporter_end", MACHINES, new MachineTeleporterEndBlock(BlockBehaviour.Properties.of(Material.METAL).strength(0.5f))));
		r.register(makeWithItem("universal_soil", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(0.5F))));
		r.register(makeWithItem("universal_soil_infused", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.DIRT).strength(0.7F))));

		r.register(makeSpecialWithItem("dark_crystal_glasspane_cross", BLOCKS, new GlassPanePillar(BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(0.2F)), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("dark_crystal_glasspane_middle", BLOCKS, new GlassPanePillar(BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(0.2F)), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("dark_crystal_glasspane_end", BLOCKS, new GlassPaneEnd(BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(0.2F), Color.WHITE.getRGB()), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeWithItem("dark_crystal_block", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.9F))));
		r.register(makeWithItem("dark_crystal_brick", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.9F))));
		r.register(makeWithItem("dark_crystal_tiling", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.9F))));
		r.register(makeWithItem("dark_crystal_fence", BLOCKS, new CustomFenceBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.9F))));
		r.register(makeWithItem("dark_crystal_lamp", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.9F).lightLevel((p) -> {return 15;}).noOcclusion())));
		r.register(makeSpecialWithItem("dark_crystal_door", BLOCKS, new CustomDoorBlock(BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(0.2F)), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeWithItem("dark_crystal_stairs", BLOCKS, new StairBlock(() -> DARK_CRYSTAL_BLOCK.defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.2F))));
		r.register(makeWithItem("dark_crystal_slab", BLOCKS, new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.2F))));
		r.register(makeWithItem("dark_crystal_ore", BLOCKS, new BaseOreBlock(BlockBehaviour.Properties.of(Material.DIRT).sound(SoundType.GRAVEL).strength(0.2F), 1, 3)));

		r.register(makeSpecialWithItem("white_crystal_glasspane_cross", BLOCKS, new GlassPanePillar(BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(0.2F)), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("white_crystal_glasspane_middle", BLOCKS, new GlassPanePillar(BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(0.2F)), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("white_crystal_glasspane_end", BLOCKS, new GlassPaneEnd(BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(0.2F), Color.WHITE.getRGB()), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeWithItem("white_crystal_block", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.9F))));
		r.register(makeWithItem("white_crystal_brick", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.9F))));
		r.register(makeWithItem("white_crystal_tiling", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.9F))));
		r.register(makeWithItem("white_crystal_fence", BLOCKS, new CustomFenceBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.9F))));
		r.register(makeWithItem("white_crystal_lamp", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.9F).lightLevel((p) -> {return 15;}).noOcclusion())));
		r.register(makeSpecialWithItem("white_crystal_door", BLOCKS, new CustomDoorBlock(BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(0.2F)), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeWithItem("white_crystal_stairs", BLOCKS, new StairBlock(() -> WHITE_CRYSTAL_BLOCK.defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.2F))));
		r.register(makeWithItem("white_crystal_slab", BLOCKS, new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(0.2F))));
		r.register(makeWithItem("white_crystal_ore", BLOCKS, new BaseOreBlock(BlockBehaviour.Properties.of(Material.DIRT).sound(SoundType.GRAVEL).strength(0.2F), 1, 3)));

		r.register(makeWithItem("wall_light", BLOCKS, new WallLight(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.GLASS))));

		r.register(makeSpecialWithItem("testblock", BLOCKS, new ObtainableNaturalPlants(4, 6), SPECIAL_RENDER_BLOCKS_CUTOUT));
		

        r.register(makeHedge("hedge_oak_oak_dirt", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_oak_spruce_dirt", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.SPRUCE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_oak_birch_dirt", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.BIRCH_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_oak_jungle_dirt", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.JUNGLE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_oak_acacia_dirt", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.ACACIA_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_oak_dark_oak_dirt", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_spruce_oak_dirt", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_spruce_spruce_dirt", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_spruce_birch_dirt", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_spruce_jungle_dirt", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.JUNGLE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_spruce_acacia_dirt", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.ACACIA_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_spruce_dark_oak_dirt", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_birch_oak_dirt", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_birch_spruce_dirt", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.SPRUCE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_birch_birch_dirt", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_birch_jungle_dirt", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_birch_acacia_dirt", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.ACACIA_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_birch_dark_oak_dirt", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_jungle_oak_dirt", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_jungle_spruce_dirt", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.SPRUCE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_jungle_birch_dirt", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.BIRCH_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_jungle_jungle_dirt", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_jungle_acacia_dirt", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES , Blocks.ACACIA_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_jungle_dark_oak_dirt", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_acacia_oak_dirt", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_acacia_spruce_dirt", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.SPRUCE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_acacia_birch_dirt", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.BIRCH_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_acacia_jungle_dirt", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.JUNGLE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_acacia_acacia_dirt", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_acacia_dark_oak_dirt", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_dark_oak_oak_dirt", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_dark_oak_spruce_dirt", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.SPRUCE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_dark_oak_birch_dirt", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.BIRCH_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_dark_oak_jungle_dirt", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_dark_oak_acacia_dirt", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_dark_oak_dark_oak_dirt", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DIRT)));

        
        r.register(makeHedge("hedge_oak_oak_grass", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_oak_spruce_grass", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.SPRUCE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_oak_birch_grass", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.BIRCH_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_oak_jungle_grass", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.JUNGLE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_oak_acacia_grass", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.ACACIA_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_oak_dark_oak_grass", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_spruce_oak_grass", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_spruce_spruce_grass", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_spruce_birch_grass", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_spruce_jungle_grass", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.JUNGLE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_spruce_acacia_grass", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.ACACIA_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_spruce_dark_oak_grass", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_birch_oak_grass", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_birch_spruce_grass", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.SPRUCE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_birch_birch_grass", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_birch_jungle_grass", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_birch_acacia_grass", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.ACACIA_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_birch_dark_oak_grass", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_jungle_oak_grass", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_jungle_spruce_grass", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.SPRUCE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_jungle_birch_grass", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.BIRCH_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_jungle_jungle_grass", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_jungle_acacia_grass", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES , Blocks.ACACIA_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_jungle_dark_oak_grass", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.DARK_OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_acacia_oak_grass", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_acacia_spruce_grass", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.SPRUCE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_acacia_birch_grass", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.BIRCH_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_acacia_jungle_grass", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.JUNGLE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_acacia_acacia_grass", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_acacia_dark_oak_grass", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_dark_oak_oak_grass", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_dark_oak_spruce_grass", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.SPRUCE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_dark_oak_birch_grass", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.BIRCH_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_dark_oak_jungle_grass", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_dark_oak_acacia_grass", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_dark_oak_dark_oak_grass", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.GRASS_BLOCK)));
        
        r.register(makeHedge("hedge_oak_oak_podzol", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_oak_spruce_podzol", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.SPRUCE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_oak_birch_podzol", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.BIRCH_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_oak_jungle_podzol", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.JUNGLE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_oak_acacia_podzol", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.ACACIA_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_oak_dark_oak_podzol", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_spruce_oak_podzol", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_spruce_spruce_podzol", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_spruce_birch_podzol", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_spruce_jungle_podzol", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.JUNGLE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_spruce_acacia_podzol", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.ACACIA_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_spruce_dark_oak_podzol", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_birch_oak_podzol", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_birch_spruce_podzol", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.SPRUCE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_birch_birch_podzol", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_birch_jungle_podzol", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_birch_acacia_podzol", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.ACACIA_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_birch_dark_oak_podzol", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_jungle_oak_podzol", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_jungle_spruce_podzol", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.SPRUCE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_jungle_birch_podzol", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.BIRCH_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_jungle_jungle_podzol", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_jungle_acacia_podzol", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES , Blocks.ACACIA_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_jungle_dark_oak_podzol", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.DARK_OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_acacia_oak_podzol", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_acacia_spruce_podzol", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.SPRUCE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_acacia_birch_podzol", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.BIRCH_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_acacia_jungle_podzol", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.JUNGLE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_acacia_acacia_podzol", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_acacia_dark_oak_podzol", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_dark_oak_oak_podzol", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_dark_oak_spruce_podzol", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.SPRUCE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_dark_oak_birch_podzol", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.BIRCH_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_dark_oak_jungle_podzol", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_dark_oak_acacia_podzol", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_dark_oak_dark_oak_podzol", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.PODZOL)));

		r.register(makeSpecialWithItem("mutated_dandelion", BLOCKS, new ObtainableNaturalPlants(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_poppy", BLOCKS, new ObtainableNaturalPlants(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_blue_orchid", BLOCKS, new ObtainableNaturalPlants(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_allium", BLOCKS, new ObtainableNaturalPlants(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_azure_bluet", BLOCKS, new ObtainableNaturalPlants(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_red_tulip", BLOCKS, new ObtainableNaturalPlants(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_orange_tulip", BLOCKS, new ObtainableNaturalPlants(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_white_tulip", BLOCKS, new ObtainableNaturalPlants(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_pink_tulip", BLOCKS, new ObtainableNaturalPlants(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_oxeye_daisy", BLOCKS, new ObtainableNaturalPlants(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_cornflower", BLOCKS, new ObtainableNaturalPlants(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_lily_of_the_valley", BLOCKS, new ObtainableNaturalPlants(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_lilac", BLOCKS, new ObtainableTallBushBlock(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_rose_bush", BLOCKS, new ObtainableTallBushBlock(), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecialWithItem("mutated_peony", BLOCKS, new ObtainableTallBushBlock(), SPECIAL_RENDER_BLOCKS_CUTOUT));

		r.register(makeSpecial("machinebulbreprocessor_growing", new GrowingBlock(() -> ModBlocks.MACHINEBULBREPROCESSOR, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("machineshell_iron_growing", new GrowingBlock(() -> ModBlocks.MACHINESHELL_IRON, false), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("machineshell_plantium_growing", new GrowingBlock(() -> ModBlocks.MACHINESHELL_PLANTIUM, false), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("seedsqueezer_growing", new FacingGrowingBlock(() -> ModBlocks.SEEDSQUEEZER, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("chipalyzer_growing", new FacingGrowingBlock(() -> ModBlocks.CHIPALYZER, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("compressor_growing", new FacingGrowingBlock(() -> ModBlocks.COMPRESSOR, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("dna_cleaner_growing", new FacingGrowingBlock(() -> ModBlocks.DNA_CLEANER, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("dna_combiner_growing", new FacingGrowingBlock(() -> ModBlocks.DNA_COMBINER, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("dna_extractor_growing", new FacingGrowingBlock(() -> ModBlocks.DNA_EXTRACTOR, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("dna_remover_growing", new FacingGrowingBlock(() -> ModBlocks.DNA_REMOVER, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("identifier_growing", new FacingGrowingBlock(() -> ModBlocks.IDENTIFIER, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("infuser_growing", new FacingGrowingBlock(() -> ModBlocks.INFUSER, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("mega_furnace_growing", new FacingGrowingBlock(() -> ModBlocks.MEGAFURNACE, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("plantfarm_growing", new GrowingBlock(() -> ModBlocks.PLANTFARM, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("energy_supplier_growing", new GrowingBlock(() -> ModBlocks.ENERGY_SUPPLIER, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("seedconstructor_growing", new FacingGrowingBlock(() -> ModBlocks.SEEDCONSTRUCTOR, true), SPECIAL_RENDER_BLOCKS_CUTOUT));
		r.register(makeSpecial("solargenerator_growing", new GrowingBlock(() -> ModBlocks.SOLARGENERATOR, true), SPECIAL_RENDER_BLOCKS_CUTOUT));

		//Terrain Blocks
		r.register(makeSpecialWithItem("infused_ice", BLOCKS, new InfusedIceBlock(BlockBehaviour.Properties.of(Material.ICE).friction(0.98F).randomTicks().strength(0.5F).sound(SoundType.GLASS).noOcclusion()), SPECIAL_RENDER_BLOCKS_TRANSLUCENT));
		r.register(makeWithItem("infused_packed_ice", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.ICE_SOLID).friction(0.98F).strength(0.5F).sound(SoundType.GLASS))));
		r.register(makeWithItem("infused_blue_ice", BLOCKS, new HalfTransparentBlock(BlockBehaviour.Properties.of(Material.ICE_SOLID).strength(2.8F).friction(0.989F).sound(SoundType.GLASS))));
		r.register(makeSpecialWithItem("black_ice", BLOCKS, new IceBlock(BlockBehaviour.Properties.of(Material.ICE).friction(0.98F).randomTicks().strength(0.5F).sound(SoundType.GLASS).noOcclusion()), SPECIAL_RENDER_BLOCKS_TRANSLUCENT));

		r.register(makeWithItem("infused_stone", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F))));
		r.register(makeWithItem("infused_cobblestone", BLOCKS, new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F))));
		CROPS.clear();
		CropBaseBlock tempcrop;
		String name;
		for (CropEntry entry : PlantTechMain.getCropList().values())
		{
			name = entry.getName();
			tempcrop = new CropBaseBlock(name);
			CROPS.put(name, tempcrop);
			r.register(tempcrop);
		}
		SPECIAL_RENDER_BLOCKS_CUTOUT.addAll(ModBlocks.CROPS.values().stream().map(b -> (Supplier<? extends Block>) () -> b).toList());
	}

	static <B extends Block> B makeWithItem(String registryName, CreativeModeTab group, B block)
	{
		final B b = makeBlock(registryName, block);
		BLOCK_ITEM_SUPPLIERS.add(() -> new BlockItem(b, new Item.Properties().tab(group)).setRegistryName(registryName));
		return b;
	}

	static <B extends Block> B makeSpecialWithItem(String registryName, CreativeModeTab group, B block, List<Supplier<? extends Block>> renderlist)
	{
		final B b = makeSpecial(registryName, block, renderlist);
		BLOCK_ITEM_SUPPLIERS.add(() -> new BlockItem(b, new Item.Properties().tab(group)).setRegistryName(registryName));
		return b;
	}
	
	static <B extends Hedge> B makeHedge(String registryName, CreativeModeTab group, B block)
	{
		final B b = makeSpecial(registryName, block, SPECIAL_RENDER_BLOCKS_CUTOUT);
		BLOCK_ITEM_SUPPLIERS.add(() -> new BlockItem(b, new Item.Properties().tab(group)).setRegistryName(registryName));
		HEDGE_BLOCKS.add(b);
		return b;
	}

	static <B extends Block> B makeBlock(String registryName, B block)
	{
		block.setRegistryName(registryName);
		return block;
	}

	static <B extends Block> B makeSpecial(String registryName, B block, List<Supplier<? extends Block>> renderlist)
	{
		block.setRegistryName(registryName);
		renderlist.add(() -> block);
		return block;
	}


	public static void registerItemBlocks(IForgeRegistry<Item> registry)
	{
		for (Supplier<? extends Item> supplier : BLOCK_ITEM_SUPPLIERS)
		{
			registry.register(supplier.get());
		}
	}
}
