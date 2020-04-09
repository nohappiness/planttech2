package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.tileentity.CropsTileEntity;
import net.kaneka.planttech2.tileentity.cable.CableTileEntity;
import net.kaneka.planttech2.tileentity.machine.ChipalyzerTileEntity;
import net.kaneka.planttech2.tileentity.machine.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.IForgeRegistry;

public class ModTileEntities 
{
    public static final TileEntityType<CropsTileEntity> CROPS_TE = TileEntityType.Builder.create(CropsTileEntity::new, ModBlocks.CROPS.values().stream().toArray(Block[]::new)).build(null); 
    public static final TileEntityType<MegaFurnaceTileEntity> MEGAFURNACE_TE= TileEntityType.Builder.create(MegaFurnaceTileEntity::new, ModBlocks.MEGAFURNACE).build(null); 
    public static final TileEntityType<IdentifierTileEntity> IDENTIFIER_TE = TileEntityType.Builder.create(IdentifierTileEntity::new, ModBlocks.IDENTIFIER).build(null); 
    public static final TileEntityType<SeedSqueezerTileEntity> SEEDSQUEEZER_TE = TileEntityType.Builder.create(SeedSqueezerTileEntity::new, ModBlocks.SEEDSQUEEZER).build(null); 
    public static final TileEntityType<SolarGeneratorTileEntity> SOLARGENERATOR_TE = TileEntityType.Builder.create(SolarGeneratorTileEntity::new, ModBlocks.SOLARGENERATOR).build(null); 
    public static final TileEntityType<PlantFarmTileEntity> PLANTFARM_TE = TileEntityType.Builder.create(PlantFarmTileEntity::new, ModBlocks.PLANTFARM).build(null); 
    public static final TileEntityType<CableTileEntity> CABLE_TE = TileEntityType.Builder.create(CableTileEntity::new, ModBlocks.CABLE).build(null); 
    public static final TileEntityType<DNAExtractorTileEntity> DNAEXTRACTOR_TE = TileEntityType.Builder.create(DNAExtractorTileEntity::new, ModBlocks.DNA_EXTRACTOR).build(null); 
    public static final TileEntityType<DNARemoverTileEntity> DNAREMOVER_TE = TileEntityType.Builder.create(DNARemoverTileEntity::new, ModBlocks.DNA_REMOVER).build(null); 
    public static final TileEntityType<DNACleanerTileEntity> DNACLEANER_TE = TileEntityType.Builder.create(DNACleanerTileEntity::new, ModBlocks.DNA_CLEANER).build(null); 
    public static final TileEntityType<DNACombinerTileEntity> DNACOMBINER_TE = TileEntityType.Builder.create(DNACombinerTileEntity::new, ModBlocks.DNA_COMBINER).build(null); 
    public static final TileEntityType<SeedconstructorTileEntity> SEEDCONSTRUCTOR_TE = TileEntityType.Builder.create(SeedconstructorTileEntity::new, ModBlocks.SEEDCONSTRUCTOR).build(null); 
    public static final TileEntityType<CompressorTileEntity> COMPRESSOR_TE = TileEntityType.Builder.create(CompressorTileEntity::new, ModBlocks.COMPRESSOR).build(null); 
    public static final TileEntityType<EnergyStorageTileEntity> ENERGYSTORAGE_TE = TileEntityType.Builder.create(EnergyStorageTileEntity::new, ModBlocks.ENERGYSTORAGE).build(null); 
    public static final TileEntityType<InfuserTileEntity> INFUSER_TE = TileEntityType.Builder.create(InfuserTileEntity::new, ModBlocks.INFUSER).build(null); 
    public static final TileEntityType<ChipalyzerTileEntity> CHIPALYZER_TE = TileEntityType.Builder.create(ChipalyzerTileEntity::new, ModBlocks.CHIPALYZER).build(null); 
    public static final TileEntityType<MachineBulbReprocessorTileEntity> MACHINEBULBREPROCESSOR_TE = TileEntityType.Builder.create(MachineBulbReprocessorTileEntity::new, ModBlocks.MACHINEBULBREPROCESSOR).build(null); 
    public static final TileEntityType<PlantTopiaTeleporterTileEntity> PLANTTOPIATELEPORTER_TE = TileEntityType.Builder.create(PlantTopiaTeleporterTileEntity::new, ModBlocks.PLANTTOPIA_TELEPORTER).build(null); 
    public static final TileEntityType<EnergySupplierTileEntity> ENERGY_SUPPLIER_TE = TileEntityType.Builder.create(EnergySupplierTileEntity::new, ModBlocks.ENERGY_SUPPLIER).build(null);
    
    
	public static final void register(IForgeRegistry<TileEntityType<?>> registry)
	{
	    registry.registerAll(CROPS_TE.setRegistryName(PlantTechMain.MODID,  "tileentitycrops"), 
		    		 MEGAFURNACE_TE.setRegistryName(PlantTechMain.MODID,  "tileentitymegafurnace"), 
		    		 IDENTIFIER_TE.setRegistryName(PlantTechMain.MODID,  "tileentityidentifier"), 
		    		 SEEDSQUEEZER_TE.setRegistryName(PlantTechMain.MODID,  "tileentityseedsqueezer"), 
		    		 SOLARGENERATOR_TE.setRegistryName(PlantTechMain.MODID,  "tileentitysolargenerator"), 
		    		 PLANTFARM_TE.setRegistryName(PlantTechMain.MODID,  "tileentityplantfarm"), 
		    		 CABLE_TE.setRegistryName(PlantTechMain.MODID,  "tileentitycable"), 
		    		 DNAEXTRACTOR_TE.setRegistryName(PlantTechMain.MODID,  "tileentitydnaextractor"), 
		    		 DNAREMOVER_TE.setRegistryName(PlantTechMain.MODID,  "tileentitydnaremover"), 
		    		 DNACLEANER_TE.setRegistryName(PlantTechMain.MODID,  "tileentitydnacleaner"), 
		    		 DNACOMBINER_TE.setRegistryName(PlantTechMain.MODID,  "tileentitydnacombiner"), 
		    		 SEEDCONSTRUCTOR_TE.setRegistryName(PlantTechMain.MODID,  "tileentityseedconstructor"), 
		    		 COMPRESSOR_TE.setRegistryName(PlantTechMain.MODID,  "tileentitycompressor"), 
		    		 ENERGYSTORAGE_TE.setRegistryName(PlantTechMain.MODID,  "tileentityenergystorage"), 
		    		 INFUSER_TE.setRegistryName(PlantTechMain.MODID,  "tileentityinfuser"), 
	    			 CHIPALYZER_TE.setRegistryName(PlantTechMain.MODID,  "tileentitychipalyzer"), 
	    			 MACHINEBULBREPROCESSOR_TE.setRegistryName(PlantTechMain.MODID,  "tileentitymachinebulbreprocessor"),
	    			 ENERGY_SUPPLIER_TE.setRegistryName(PlantTechMain.MODID,  "tileentityenergysupplier"));
		
	}

}
