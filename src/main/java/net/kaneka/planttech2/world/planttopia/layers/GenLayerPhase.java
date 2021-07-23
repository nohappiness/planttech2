package net.kaneka.planttech2.world.planttopia.layers;

import net.kaneka.planttech2.world.utils.BiomeHolder;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.CastleTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenLayerPhase implements CastleTransformer {


    private HashMap<Integer, HashMap<BiomeHolder.RARITY, List<Integer>>> biomes_all, biomes_border;
    private HashMap<Integer, List<Integer>> categories;
    private final int RARITY_COMMON = 2, RARITY_UNCOMMON = 4, RARITY_RARE = 8;
    private int phase;

    public GenLayerPhase setup(int phase, HashMap<Integer, HashMap<BiomeHolder.RARITY, List<Integer>>> replacemap_all, HashMap<Integer, HashMap<BiomeHolder.RARITY, List<Integer>>> replacemap_border, HashMap<Integer, List<Integer>> category) {
        this.phase = phase;
        this.biomes_all = replacemap_all;
        this.biomes_border = replacemap_border;
        this.categories = category;
        return this;
    }

    @Override
    public int apply(Context rand, int north, int west, int south, int east, int center) {
        HashMap<BiomeHolder.RARITY, List<Integer>> list = biomes_all.get(center);

        if(category(center) != category(north) || category(center) != category(west) || category(center) != category(south) || category(center) != category(east)){
            list = biomes_border.get(center);
        }

        if(category(center) != -1 && list != null) {

            if (rand.nextRandom(RARITY_RARE) == 0 && !list.get(BiomeHolder.RARITY.RARE).isEmpty()) {
                return randomBiome(rand, list.get(BiomeHolder.RARITY.RARE));
            }
            if (rand.nextRandom(RARITY_UNCOMMON) == 0 && !list.get(BiomeHolder.RARITY.UNCOMMON).isEmpty()) {
                return randomBiome(rand, list.get(BiomeHolder.RARITY.UNCOMMON));
            }
            if (rand.nextRandom(RARITY_COMMON) == 0 && !list.get(BiomeHolder.RARITY.COMMON).isEmpty()) {
                return randomBiome(rand, list.get(BiomeHolder.RARITY.COMMON));
            }
        }
        return center;
    }

    private int category(int i){
        for(Map.Entry<Integer, List<Integer>> entry: categories.entrySet())
        {
            if(entry.getValue().contains(i)) return entry.getKey();
        }
        return -1;
    }




    private int randomBiome(Context random, List<Integer> biomes) {
        return biomes.get(random.nextRandom(biomes.size()));
    }
}
