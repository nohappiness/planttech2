package net.kaneka.planttech2.world.planttopia.biomes.layer;

import java.util.ArrayList;

import net.kaneka.planttech2.registries.ModBiomes;
import net.minecraft.world.biome.Biome;

/**
 * @author Krevik
 */
public class BiomeVariantsUtil {

    private ArrayList<Biome> biomesWithVariantsList=new ArrayList<>();

    public BiomeVariantsUtil() {
        biomesWithVariantsList.add(ModBiomes.PLANTTOPIA_FOREST);
    }

    public ArrayList<Biome> getBiomesWithVariantsList() {
        return biomesWithVariantsList;
    }

    public Biome getBiomeVariant(Biome biome) {
        if (biome==ModBiomes.PLANTTOPIA_FOREST) {
            return ModBiomes.PLANTTOPIA_FOREST;
        } else {
            return biome;
        }

    }
}