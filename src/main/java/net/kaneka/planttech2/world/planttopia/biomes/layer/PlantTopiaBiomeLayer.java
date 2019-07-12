package net.kaneka.planttech2.world.planttopia.biomes.layer;

import java.util.ArrayList;

import com.google.common.collect.ImmutableList;

import net.kaneka.planttech2.registries.ModBiomes;
import net.kaneka.planttech2.world.planttopia.PlantTopiaGenSettings;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class PlantTopiaBiomeLayer implements IC0Transformer {
	public static ImmutableList<BiomeManager.BiomeEntry> getBiomesToGeneration(){
        ArrayList<BiomeManager.BiomeEntry> list = new ArrayList<>();
        list.add(new BiomeManager.BiomeEntry(ModBiomes.PLANTTOPIA_PLAINS,100));
        list.add(new BiomeManager.BiomeEntry(ModBiomes.PLANTTOPIA_FOREST,60));
        return ImmutableList.copyOf(list);
    }
	@SuppressWarnings("unchecked")
	private java.util.List<BiomeEntry>[] biomes = new java.util.ArrayList[BiomeType.values().length];
	private final PlantTopiaGenSettings settings;
	
	public PlantTopiaBiomeLayer(WorldType p_i48641_1_, PlantTopiaGenSettings p_i48641_2_) {
		for (BiomeType type : BiomeType.values()) {
			ImmutableList<BiomeManager.BiomeEntry> biomesToAdd = getBiomesToGeneration();
			int idx = type.ordinal();
	
			if (biomes[idx] == null) biomes[idx] = new java.util.ArrayList<BiomeEntry>();
			if (biomesToAdd != null) biomes[idx].addAll(biomesToAdd);
		}
	
		if (p_i48641_1_ == WorldType.DEFAULT_1_1) {
			this.settings = null;
		} else {
			this.settings = p_i48641_2_;
		}
	}
	
	public int apply(INoiseRandom context, int value) {
		/*if (this.settings != null && this.settings.getBiomeId() >= 0) {
			return this.settings.getBiomeId();
		} else {
			int i = (value & 3840) >> 8;
			value = value & -3841;
			if (value != MUSHROOM_FIELDS) {
				switch(value) {
				case 1:
					if (i > 0) {
						return context.random(3) == 0 ? BADLANDS_PLATEAU : WOODED_BADLANDS_PLATEAU;
					}
	
					return Registry.BIOME.getId(getWeightedBiomeEntry(BiomeType.DESERT, context).biome);
				case 2:
					if (i > 0) {
						return JUNGLE;
					}
	
					return Registry.BIOME.getId(getWeightedBiomeEntry(BiomeType.WARM, context).biome);
				case 3:
					if (i > 0) {
						return GIANT_TREE_TAIGA;
					}
	
					return Registry.BIOME.getId(getWeightedBiomeEntry(BiomeType.COOL, context).biome);
				case 4:
					return Registry.BIOME.getId(getWeightedBiomeEntry(BiomeType.ICY, context).biome);
				default:
					return MUSHROOM_FIELDS;
				}
			} else {
				return value;
			}
		}*/
		if (this.settings != null && this.settings.func_202199_l() >= 0) {
            return this.settings.func_202199_l();
        } else {
            return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.COOL, context).biome);
        }
	}
	
	protected BiomeEntry getWeightedBiomeEntry(BiomeType type, INoiseRandom context) {
		java.util.List<BiomeEntry> biomeList = biomes[type.ordinal()];
		int totalWeight = net.minecraft.util.WeightedRandom.getTotalWeight(biomeList);
		int weight = BiomeManager.isTypeListModded(type)?context.random(totalWeight):context.random(totalWeight / 10) * 10;
		return (BiomeEntry)net.minecraft.util.WeightedRandom.getRandomItem(biomeList, weight);
	}
}