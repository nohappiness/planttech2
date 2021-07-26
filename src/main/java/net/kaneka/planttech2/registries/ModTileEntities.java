package net.kaneka.planttech2.registries;

import com.mojang.datafixers.types.Type;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.CropsBlockEntity;
import net.kaneka.planttech2.blocks.entity.cable.TestCableBlockEntity;
import net.kaneka.planttech2.blocks.entity.machine.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@ObjectHolder(PlantTechMain.MODID)
public class ModTileEntities
{
	@ObjectHolder("tileentitycrops") public static BlockEntityType<CropsBlockEntity> CROPS_TE;
	@ObjectHolder("tileentitymegafurnace") public static BlockEntityType<MegaFurnaceBlockEntity> MEGAFURNACE_TE;
	@ObjectHolder("tileentityidentifier") public static BlockEntityType<IdentifierBlockEntity> IDENTIFIER_TE;
	@ObjectHolder("tileentityseedsqueezer") public static BlockEntityType<SeedSqueezerBlockEntity> SEEDSQUEEZER_TE;
	@ObjectHolder("tileentitysolargenerator") public static BlockEntityType<SolarGeneratorBlockEntity> SOLARGENERATOR_TE;
	@ObjectHolder("tileentityplantfarm") public static BlockEntityType<PlantFarmBlockEntity> PLANTFARM_TE;
	@ObjectHolder("tileentitycable") public static BlockEntityType<TestCableBlockEntity> CABLE_TE;
	@ObjectHolder("tileentitydnaextractor") public static BlockEntityType<DNAExtractorBlockEntity> DNAEXTRACTOR_TE;
	@ObjectHolder("tileentitydnaremover") public static BlockEntityType<DNARemoverBlockEntity> DNAREMOVER_TE;
	@ObjectHolder("tileentitydnacleaner") public static BlockEntityType<DNACleanerBlockEntity> DNACLEANER_TE;
	@ObjectHolder("tileentitydnacombiner") public static BlockEntityType<DNACombinerBlockEntity> DNACOMBINER_TE;
	@ObjectHolder("tileentityseedconstructor") public static BlockEntityType<SeedConstructorBlockEntity> SEEDCONSTRUCTOR_TE;
	@ObjectHolder("tileentitycompressor") public static BlockEntityType<CompressorBlockEntity> COMPRESSOR_TE;
	@ObjectHolder("tileentityenergystorage") public static BlockEntityType<EnergyStorageBlockEntity> ENERGYSTORAGE_TE;
	@ObjectHolder("tileentityinfuser") public static BlockEntityType<InfuserBlockEntity> INFUSER_TE;
	@ObjectHolder("tileentitychipalyzer") public static BlockEntityType<ChipalyzerBlockEntity> CHIPALYZER_TE;
	@ObjectHolder("tileentitymachinebulbreprocessor") public static BlockEntityType<MachineBulbReprocessorBlockEntity> MACHINEBULBREPROCESSOR_TE;
	@ObjectHolder("tileentitycrops") public static BlockEntityType<PlantTopiaTeleporterBlockEntity> PLANTTOPIATELEPORTER_TE;
	@ObjectHolder("tileentityenergysupplier") public static BlockEntityType<EnergySupplierBlockEntity> ENERGY_SUPPLIER_TE;
	@ObjectHolder("tileentitycropauragenerator") public static BlockEntityType<CropAuraGeneratorBlockEntity> CROP_AURA_GENERATOR_TE;

	public static void register(IForgeRegistry<BlockEntityType<?>> registry)
	{
		registry.register(make("tileentitycrops", CropsBlockEntity::new, ModBlocks.CROPS.values().toArray(new Block[0])));
		registry.register(make("tileentitymegafurnace", MegaFurnaceBlockEntity::new, ModBlocks.MEGAFURNACE));
		registry.register(make("tileentityidentifier", IdentifierBlockEntity::new, ModBlocks.IDENTIFIER));
		registry.register(make("tileentityseedsqueezer", SeedSqueezerBlockEntity::new, ModBlocks.SEEDSQUEEZER));
		registry.register(make("tileentitysolargenerator", SolarGeneratorBlockEntity::new, ModBlocks.SOLARGENERATOR));
		registry.register(make("tileentityplantfarm", PlantFarmBlockEntity::new, ModBlocks.PLANTFARM));
		registry.register(make("tileentitycable", TestCableBlockEntity::new, ModBlocks.CABLE));
		registry.register(make("tileentitydnaextractor", DNAExtractorBlockEntity::new, ModBlocks.DNA_EXTRACTOR));
		registry.register(make("tileentitydnaremover", DNARemoverBlockEntity::new, ModBlocks.DNA_REMOVER));
		registry.register(make("tileentitydnacleaner", DNACleanerBlockEntity::new, ModBlocks.DNA_CLEANER));
		registry.register(make("tileentitydnacombiner", DNACombinerBlockEntity::new, ModBlocks.DNA_COMBINER));
		registry.register(make("tileentityseedconstructor", SeedConstructorBlockEntity::new, ModBlocks.SEEDCONSTRUCTOR));
		registry.register(make("tileentitycompressor", CompressorBlockEntity::new, ModBlocks.COMPRESSOR));
		registry.register(make("tileentityenergystorage", EnergyStorageBlockEntity::new, ModBlocks.ENERGYSTORAGE));
		registry.register(make("tileentityinfuser", InfuserBlockEntity::new, ModBlocks.INFUSER));
		registry.register(make("tileentitychipalyzer", ChipalyzerBlockEntity::new, ModBlocks.CHIPALYZER));
		registry.register(make("tileentitymachinebulbreprocessor", MachineBulbReprocessorBlockEntity::new, ModBlocks.MACHINEBULBREPROCESSOR));
		registry.register(make("tileentityplanttopiateleporter", MachineBulbReprocessorBlockEntity::new, ModBlocks.PLANTTOPIA_TELEPORTER, ModBlocks.PLANTTOPIA_TELEPORTER_END));
		registry.register(make("tileentityenergysupplier", EnergySupplierBlockEntity::new, ModBlocks.ENERGY_SUPPLIER));
		registry.register(make("tileentitycropauragenerator", CropAuraGeneratorBlockEntity::new, ModBlocks.CROP_AURA_GENERATOR));
	}

	static <T extends BlockEntity> BlockEntityType<T> make(String registryName, BlockEntityType.BlockEntitySupplier<T> factory, Block... validBlocks) {
		return make(registryName, factory, null, validBlocks);
	}

	static <T extends BlockEntity> BlockEntityType<T> make(String registryName, BlockEntityType.BlockEntitySupplier<T> factory, Type<?> dataFixer, Block... validBlocks) {
		BlockEntityType<T> result = BlockEntityType.Builder.of(factory, validBlocks).build(dataFixer);
		result.setRegistryName(registryName);
		return result;
	}
}
