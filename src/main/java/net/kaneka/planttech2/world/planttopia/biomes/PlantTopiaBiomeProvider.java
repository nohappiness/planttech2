package net.kaneka.planttech2.world.planttopia.biomes;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import net.kaneka.planttech2.registries.ModBiomes;
import net.kaneka.planttech2.world.planttopia.PlantTopiaGenSettings;
import net.kaneka.planttech2.world.planttopia.PlantTopiaWorldType;
import net.kaneka.planttech2.world.planttopia.biomes.layer.PlantTopiaLayerUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.storage.WorldInfo;

public class PlantTopiaBiomeProvider extends BiomeProvider {
    private final Layer genBiomes;
    private final Layer biomeFactoryLayer;
    private final Biome[] biomes;

    public PlantTopiaBiomeProvider(PlantTopiaBiomeProviderSettings p_i48971_1_) {
        this.biomes = new Biome[]{ModBiomes.PLANTTOPIA_RIVER,ModBiomes.PLANTTOPIA_FOREST, ModBiomes.PLANTTOPIA_PLAINS};
        WorldInfo lvt_2_1_ = p_i48971_1_.getWorldInfo();
        PlantTopiaGenSettings lvt_3_1_ = p_i48971_1_.getGeneratorSettings();
        Layer[] lvt_4_1_ = PlantTopiaLayerUtil.buildOverworldProcedure(lvt_2_1_.getSeed(), new PlantTopiaWorldType(), lvt_3_1_);
        this.genBiomes = lvt_4_1_[0];
        this.biomeFactoryLayer = lvt_4_1_[1];
    }

    @Nullable
    public Biome getBiome(int x, int y) {
    	return this.biomeFactoryLayer.func_215738_a(x, y);
    }

    public Biome[] func_222366_b(int p_201535_1_, int p_201535_2_, int p_201535_3_, int p_201535_4_) {
        return this.genBiomes.generateBiomes(p_201535_1_, p_201535_2_, p_201535_3_, p_201535_4_);
    }

    public Biome[] getBiomes(int x, int z, int width, int length, boolean p_201537_5_) {
    	return this.biomeFactoryLayer.generateBiomes(x, z, width, length);
    }

    public Set<Biome> getBiomesInSquare(int p_201538_1_, int p_201538_2_, int p_201538_3_) {
        int lvt_4_1_ = p_201538_1_ - p_201538_3_ >> 2;
        int lvt_5_1_ = p_201538_2_ - p_201538_3_ >> 2;
        int lvt_6_1_ = p_201538_1_ + p_201538_3_ >> 2;
        int lvt_7_1_ = p_201538_2_ + p_201538_3_ >> 2;
        int lvt_8_1_ = lvt_6_1_ - lvt_4_1_ + 1;
        int lvt_9_1_ = lvt_7_1_ - lvt_5_1_ + 1;
        Set<Biome> lvt_10_1_ = Sets.newHashSet();
        Collections.addAll(lvt_10_1_, this.genBiomes.generateBiomes(lvt_4_1_, lvt_5_1_, lvt_8_1_, lvt_9_1_));
        return lvt_10_1_;
    }

    @Nullable
    public BlockPos findBiomePosition(int p_180630_1_, int p_180630_2_, int p_180630_3_, List<Biome> p_180630_4_, Random p_180630_5_) {
        int lvt_6_1_ = p_180630_1_ - p_180630_3_ >> 2;
        int lvt_7_1_ = p_180630_2_ - p_180630_3_ >> 2;
        int lvt_8_1_ = p_180630_1_ + p_180630_3_ >> 2;
        int lvt_9_1_ = p_180630_2_ + p_180630_3_ >> 2;
        int lvt_10_1_ = lvt_8_1_ - lvt_6_1_ + 1;
        int lvt_11_1_ = lvt_9_1_ - lvt_7_1_ + 1;
        Biome[] lvt_12_1_ = this.genBiomes.generateBiomes(lvt_6_1_, lvt_7_1_, lvt_10_1_, lvt_11_1_);
        BlockPos lvt_13_1_ = null;
        int lvt_14_1_ = 0;

        for(int lvt_15_1_ = 0; lvt_15_1_ < lvt_10_1_ * lvt_11_1_; ++lvt_15_1_) {
            int lvt_16_1_ = lvt_6_1_ + lvt_15_1_ % lvt_10_1_ << 2;
            int lvt_17_1_ = lvt_7_1_ + lvt_15_1_ / lvt_10_1_ << 2;
            if (p_180630_4_.contains(lvt_12_1_[lvt_15_1_])) {
                if (lvt_13_1_ == null || p_180630_5_.nextInt(lvt_14_1_ + 1) == 0) {
                    lvt_13_1_ = new BlockPos(lvt_16_1_, 0, lvt_17_1_);
                }

                ++lvt_14_1_;
            }
        }

        return lvt_13_1_;
    }

    public boolean hasStructure(Structure<?> p_205004_1_) {
        return (Boolean)this.hasStructureCache.computeIfAbsent(p_205004_1_, (p_205006_1_) -> {
            Biome[] var2 = this.biomes;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Biome lvt_5_1_ = var2[var4];
                if (lvt_5_1_.hasStructure(p_205006_1_)) {
                    return true;
                }
            }

            return false;
        });
    }

    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            Biome[] var1 = this.biomes;
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Biome lvt_4_1_ = var1[var3];
                this.topBlocksCache.add(lvt_4_1_.getSurfaceBuilderConfig().getTop());
            }
        }

        return this.topBlocksCache;
    }
}