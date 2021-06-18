package net.kaneka.planttech2.world.planttopia.layers;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

import java.util.*;

public class GenLayerPhase implements ICastleTransformer {

    private Registry<Biome> registry;

    private HashMap<Integer, HashMap<BIOMEFLAGS, List<Integer>>> biomes_all, biomes_border;
    private HashMap<Integer, Set<Integer>> categories;
    private final int RARITY_COMMON = 2, RARITY_UNCOMMON = 4, RARITY_RARE = 8;
    private int phase;

    public GenLayerPhase setup(int phase, Registry<Biome> registry) {
        this.registry = registry;
        this.phase = phase;
        biomes_all = new HashMap<>();
        biomes_border = new HashMap<>();
        categories = new HashMap<>();
        for(BIOMEFLAGS category: BIOMEFLAGS.byPhase(1)) {
            int baseId = GenLayerUtils.getBiomeId(GenLayerUtils.byFlags(category, BIOMEFLAGS.BASE), registry);
            biomes_all.put(baseId, new HashMap<BIOMEFLAGS, List<Integer>>(){{
                put(BIOMEFLAGS.COMMON, biomes(category, BIOMEFLAGS.COMMON));
                put(BIOMEFLAGS.UNCOMMON, biomes(category, BIOMEFLAGS.UNCOMMON));
                put(BIOMEFLAGS.RARE, biomes( category, BIOMEFLAGS.RARE));
            }});

            biomes_border.put(baseId, new HashMap<BIOMEFLAGS, List<Integer>>(){{
                put(BIOMEFLAGS.COMMON, biomesBorder(category, BIOMEFLAGS.COMMON));
                put(BIOMEFLAGS.UNCOMMON, biomesBorder(category, BIOMEFLAGS.UNCOMMON));
                put(BIOMEFLAGS.RARE, biomesBorder( category, BIOMEFLAGS.RARE));
            }});

            Set<Integer> set = new HashSet<>(biomes(category));
            set.add(baseId);
            categories.put(baseId, set);
        }

        return this;
    }

    @Override
    public int apply(INoiseRandom rand, int north, int west, int south, int east, int center) {
        HashMap<BIOMEFLAGS, List<Integer>> list = biomes_all.get(center);

        if(category(center) != category(north) || category(center) != category(west) || category(center) != category(south) || category(center) != category(east)){
            list = biomes_border.get(center);
        }

        if(category(center) != -1) {

            if (rand.nextRandom(RARITY_RARE) == 0 && !list.get(BIOMEFLAGS.RARE).isEmpty()) {
                return randomBiome(rand, list.get(BIOMEFLAGS.RARE));
            }
            if (rand.nextRandom(RARITY_UNCOMMON) == 0 && !list.get(BIOMEFLAGS.UNCOMMON).isEmpty()) {
                return randomBiome(rand, list.get(BIOMEFLAGS.UNCOMMON));
            }
            if (rand.nextRandom(RARITY_COMMON) == 0 && !list.get(BIOMEFLAGS.COMMON).isEmpty()) {
                return randomBiome(rand, list.get(BIOMEFLAGS.COMMON));
            }
        }
        return center;
    }

    private int category(int i){
        for(Map.Entry<Integer, Set<Integer>> entry: categories.entrySet())
        {
            if(entry.getValue().contains(i)) return entry.getKey();
        }
        return -1;
    }

    private List<Integer> biomes(BIOMEFLAGS... flags){
        List<Integer> list = new ArrayList<>();
        GenLayerUtils.FLAGS.forEach((k,v) ->{
            boolean fitting = true;
            for(BIOMEFLAGS f : flags)
                if (!v.contains(f)) {
                    fitting = false;
                    break;
                }
            if(v.contains(BIOMEFLAGS.BASE)) fitting = false;
            if(fitting) list.add( GenLayerUtils.getBiomeId(k, registry));
        });
        return list;
    }

    private List<Integer> biomesBorder(BIOMEFLAGS... flags){
        List<Integer> list = new ArrayList<>();
        GenLayerUtils.FLAGS.forEach((k,v) ->{
            boolean fitting = true;
            for(BIOMEFLAGS f : flags)
                if (!v.contains(f)) {
                    fitting = false;
                    break;
                }
            if(v.contains(BIOMEFLAGS.BASE) || v.contains(BIOMEFLAGS.ONLY_INSIDE)) fitting = false;
            if(fitting) list.add( GenLayerUtils.getBiomeId(k, registry));
        });
        return list;
    }

    private int randomBiome(INoiseRandom random, List<Integer> biomes) {
        return biomes.get(random.nextRandom(biomes.size()));
    }
}
