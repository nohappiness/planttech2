package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.tileentity.TileEntityCrops;
import net.kaneka.planttech2.tileentity.cable.TileEntityCable;
import net.kaneka.planttech2.tileentity.machine.TileEntityCompressor;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNACleaner;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNACombiner;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNAExtractor;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNARemover;
import net.kaneka.planttech2.tileentity.machine.TileEntityEnergyStorage;
import net.kaneka.planttech2.tileentity.machine.TileEntityIdentifier;
import net.kaneka.planttech2.tileentity.machine.TileEntityInfuser;
import net.kaneka.planttech2.tileentity.machine.TileEntityMegaFurnace;
import net.kaneka.planttech2.tileentity.machine.TileEntityPlantFarm;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedSqueezer;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedconstructor;
import net.kaneka.planttech2.tileentity.machine.TileEntitySolarGenerator;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.IForgeRegistry;

public class ModTileEntities 
{
    public static final TileEntityType<?> CROPS_TE = TileEntityType.Builder.func_223042_a(TileEntityCrops::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitycrops"); 
    public static final TileEntityType<?> MEGAFURNACE_TE= TileEntityType.Builder.func_223042_a(TileEntityMegaFurnace::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitymegafurnace"); 
    public static final TileEntityType<?> IDENTIFIER_TE = TileEntityType.Builder.func_223042_a(TileEntityIdentifier::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentityidentifier"); 
    public static final TileEntityType<?> SEEDSQUEEZER_TE = TileEntityType.Builder.func_223042_a(TileEntitySeedSqueezer::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentityseedsqueezer"); 
    public static final TileEntityType<?> SOLARGENERATOR_TE = TileEntityType.Builder.func_223042_a(TileEntitySolarGenerator::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitysolargenerator"); 
    public static final TileEntityType<?> PLANTFARM_TE = TileEntityType.Builder.func_223042_a(TileEntityPlantFarm::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentityplantfarm"); 
    public static final TileEntityType<?> CABLE_TE = TileEntityType.Builder.func_223042_a(TileEntityCable::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitycable"); 
    public static final TileEntityType<?> DNAEXTRACTOR_TE = TileEntityType.Builder.func_223042_a(TileEntityDNAExtractor::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitydnaextractor"); 
    public static final TileEntityType<?> DNAREMOVER_TE = TileEntityType.Builder.func_223042_a(TileEntityDNARemover::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitydnaremover"); 
    public static final TileEntityType<?> DNACLEANER_TE = TileEntityType.Builder.func_223042_a(TileEntityDNACleaner::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitydnacleaner"); 
    public static final TileEntityType<?> DNACOMBINER_TE = TileEntityType.Builder.func_223042_a(TileEntityDNACombiner::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitydnacombiner"); 
    public static final TileEntityType<?> SEEDCONSTRUCTOR_TE = TileEntityType.Builder.func_223042_a(TileEntitySeedconstructor::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentityseedconstructor"); 
    public static final TileEntityType<?> COMPRESSOR_TE = TileEntityType.Builder.func_223042_a(TileEntityCompressor::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitycompressor"); 
    public static final TileEntityType<?> ENERGYSTORAGE_TE = TileEntityType.Builder.func_223042_a(TileEntityEnergyStorage::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentityenergystorage"); 
    public static final TileEntityType<?> INFUSER_TE = TileEntityType.Builder.func_223042_a(TileEntityInfuser::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentityinfuser"); 
    
    
    
	public static final void register(IForgeRegistry<TileEntityType<?>> registry)
	{
	    registry.registerAll(CROPS_TE, 
		    		 MEGAFURNACE_TE, 
		    		 IDENTIFIER_TE, 
		    		 SEEDSQUEEZER_TE, 
		    		 SOLARGENERATOR_TE, 
		    		 PLANTFARM_TE, 
		    		 CABLE_TE, 
		    		 DNAEXTRACTOR_TE, 
		    		 DNAREMOVER_TE, 
		    		 DNACLEANER_TE, 
		    		 DNACOMBINER_TE, 
		    		 SEEDCONSTRUCTOR_TE, 
		    		 COMPRESSOR_TE, 
		    		 ENERGYSTORAGE_TE, 
		    		 INFUSER_TE);
		
	}

}
