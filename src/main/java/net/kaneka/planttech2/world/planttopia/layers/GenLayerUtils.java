package net.kaneka.planttech2.world.planttopia.layers;

import net.kaneka.planttech2.registries.ModReferences;
import net.kaneka.planttech2.world.utils.BiomeHolder;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

import java.util.*;
import java.util.List;
import java.util.function.LongFunction;

public class GenLayerUtils {

    protected static final List<BiomeHolder> BIOMES = Util.make(new ArrayList<>(),
            (list) -> {
                //BASEBIOMES WHICH ARE DISTRIBUTED FIRST AND PARTIALLY REPLACED LATER (PHASE 0)
                list.add(new BiomeHolder(ModReferences.MEADOWS, BiomeHolder.RARITY.COMMON, 0));
                list.add(new BiomeHolder(ModReferences.ICY_MEADOWS , BiomeHolder.RARITY.COMMON, 0));
                list.add(new BiomeHolder(ModReferences.RADIATED_WASTELANDS, BiomeHolder.RARITY.COMMON, 0));
                list.add(new BiomeHolder(ModReferences.WETLANDS, BiomeHolder.RARITY.UNCOMMON, 0));
                list.add(new BiomeHolder(ModReferences.NIGHTMARE_FOREST, BiomeHolder.RARITY.UNCOMMON, 0));

                //PARTIALLY REPLACES BASEBIOMES IN PHASE 1
                list.add(new BiomeHolder(ModReferences.FLOWER_MEADOWS, BiomeHolder.RARITY.UNCOMMON, 1)
                        .addTarget(ModReferences.MEADOWS));
                list.add(new BiomeHolder(ModReferences.LAKE, BiomeHolder.RARITY.UNCOMMON, 1)
                        .addTarget(ModReferences.MEADOWS, true));
                list.add(new BiomeHolder(ModReferences.LLAMA_MEADOW, BiomeHolder.RARITY.UNCOMMON, 1)
                        .addTarget(ModReferences.MEADOWS));
                list.add(new BiomeHolder(ModReferences.ICY_CLIFFS , BiomeHolder.RARITY.UNCOMMON, 1)
                        .addTarget(ModReferences.ICY_MEADOWS, true));
                list.add(new BiomeHolder(ModReferences.DRIED_LAKE , BiomeHolder.RARITY.UNCOMMON,  1)
                        .addTarget(ModReferences.RADIATED_WASTELANDS, true));
                list.add(new BiomeHolder(ModReferences.VULCANO, BiomeHolder.RARITY.RARE,1)
                        .addTarget(ModReferences.RADIATED_WASTELANDS, true));
                list.add(new BiomeHolder(ModReferences.WASTELAND_MESA, BiomeHolder.RARITY.UNCOMMON, 1)
                        .addTarget(ModReferences.RADIATED_WASTELANDS, true));
                list.add(new BiomeHolder(ModReferences.RADIATED_WETLANDS, BiomeHolder.RARITY.UNCOMMON, 1)
                        .addTarget(ModReferences.RADIATED_WASTELANDS, true));
                list.add(new BiomeHolder(ModReferences.ENERGIZED_FOREST, BiomeHolder.RARITY.UNCOMMON, 1)
                        .addTarget(ModReferences.RADIATED_WASTELANDS));
                list.add(new BiomeHolder(ModReferences.DARK_WETLANDS , BiomeHolder.RARITY.UNCOMMON, 1)
                        .addTarget(ModReferences.WETLANDS));

                //PARTIALLY REPLACES BIOMES FROM PHASE 1 IN PHASE 2
                list.add(new BiomeHolder(ModReferences.CHORUS_FOREST , BiomeHolder.RARITY.UNCOMMON, 1)
                        .addTarget(ModReferences.NIGHTMARE_FOREST));
                list.add(new BiomeHolder(ModReferences.DEAD_FOREST , BiomeHolder.RARITY.COMMON, 1)
                        .addTarget(ModReferences.NIGHTMARE_FOREST));
                list.add(new BiomeHolder(ModReferences.DREAM_FOREST , BiomeHolder.RARITY.UNCOMMON, 1)
                        .addTarget(ModReferences.NIGHTMARE_FOREST));
                list.add(new BiomeHolder(ModReferences.MUSHROOM_FOREST, BiomeHolder.RARITY.UNCOMMON, 1)
                        .addTarget(ModReferences.NIGHTMARE_FOREST));
                list.add(new BiomeHolder(ModReferences.PUMPKIN_FOREST, BiomeHolder.RARITY.UNCOMMON, 1)
                        .addTarget(ModReferences.NIGHTMARE_FOREST));

                //PARTIALLY REPLACES BIOMES FROM PHASE 2 IN PHASE 3
                list.add(new BiomeHolder(ModReferences.FLOWER_HILLS , BiomeHolder.RARITY.UNCOMMON, 2)
                        .addTarget(ModReferences.FLOWER_MEADOWS));
                list.add( new BiomeHolder(ModReferences.BEE_FOREST , BiomeHolder.RARITY.UNCOMMON, 2)
                        .addTarget(ModReferences.DREAM_FOREST));
                list.add(new BiomeHolder(ModReferences.MUSHROOM_HILLS, BiomeHolder.RARITY.UNCOMMON, 2)
                        .addTarget(ModReferences.MUSHROOM_HILLS));

                //PARTIALLY REPLACES BIOMES FROM PHASE 3 IN PHASE 4
                list.add(new BiomeHolder(ModReferences.FLOWER_MOUNTAINS , BiomeHolder.RARITY.UNCOMMON, 3)
                    .addTarget(ModReferences.FLOWER_HILLS, true));

                //OTHER, MARKED WITH PHASE -1
                list.add(new BiomeHolder(ModReferences.RIVER, BiomeHolder.RARITY.COMMON, -1));
            }
    );


    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> genLayers(LongFunction<C> seed, Registry<Biome> registry){
        //PREPERATION AND LOADING OF DATA
        //-> CATEGORY FOR BORDER-DETECTION (ONLY INSIDE BIOMES AND RIVERS)
        HashMap<Integer, List<Integer>> category = new HashMap<>();
        idByPhase(0, registry).forEach(h -> category.put(h, new ArrayList<Integer>(){{add(h);}}));
        //      phase            targetid             rarity          biomeid
        HashMap<Integer, HashMap<Integer, HashMap<BiomeHolder.RARITY, List<Integer>>>> replacemap = new HashMap<>();
        HashMap<Integer, HashMap<Integer, HashMap<BiomeHolder.RARITY, List<Integer>>>> replacemap_border = new HashMap<>();
        for(int i = 1; i <= 3; i++){
            replacemap.put(i, new HashMap<>());
            replacemap_border.put(i, new HashMap<>());
            for(BiomeHolder holder: biomeByPhase(i)){
                if(holder.hasTarget() ) {
                    int id = holder.getBiomeId(registry);
                    int targetid = holder.getTargetId(registry);

                    for(Map.Entry<Integer, List<Integer>> entry: category.entrySet()) {

                        //CATEGORY STUFF
                        if (entry.getValue().contains(targetid)) {
                            category.get(entry.getKey()).add(id);
                        }
                    }

                    //REPLACEMAP STUFF
                    if(!holder.isOnlyInside()) {
                        fillInnerReplaceMap(replacemap_border, i, targetid, holder.getRarity(), id);
                    }
                    fillInnerReplaceMap(replacemap, i, targetid, holder.getRarity(), id);

                }
            }
        }


        //GENERATING LAYERS
        IAreaFactory<T> biomes = new GenLayerBase().setup(idByPhaseWithRarity(0, registry)).run(seed.apply(1L));
        biomes = repeat(1000L, ZoomLayer.NORMAL, biomes, 1, seed);
        biomes = new GenLayerPhase().setup(1, replacemap.get(1), replacemap_border.get(1),category).run(seed.apply(1L), biomes);
        biomes = repeat(1001L, ZoomLayer.NORMAL, biomes, 2, seed);
        biomes = new GenLayerPhase().setup(2, replacemap.get(2), replacemap_border.get(2),category).run(seed.apply(1L), biomes);
        biomes = repeat(1002L, ZoomLayer.NORMAL, biomes, 2, seed);
        biomes = new GenLayerPhase().setup(3, replacemap.get(3), replacemap_border.get(3),category).run(seed.apply(1L), biomes);
        biomes = new GenLayerRiver().setup(getRiverId(registry), category).run(seed.apply(1L), biomes);;
        //biomes = ZoomLayer.FUZZY.run(seed.apply(1000L), biomes);
        biomes = repeat(1003L, ZoomLayer.NORMAL, biomes, 1, seed);

        /*
        IAreaFactory<T> riverLayer = new GenLayerRiver().setup(getRiverId(registry), category).run(seed.apply(1L), biomes);
        riverLayer = SmoothLayer.INSTANCE.run(seed.apply(7000L), riverLayer);
        biomes = GenLayerMixRiver.INSTANCE.run(seed.apply(100L), biomes, riverLayer);
        */
        return biomes;
    }

    private static int getRiverId(Registry<Biome> reg){
        for(BiomeHolder holder: BIOMES){
            if(holder.isRiver()){
                return holder.getBiomeId(reg);
            }
        }
        return 0;
    }

    private static void fillInnerReplaceMap(HashMap<Integer, HashMap<Integer, HashMap<BiomeHolder.RARITY, List<Integer>>>> replacemap, int phase, int targetid, BiomeHolder.RARITY rarity, int biomeid) {
        if (!replacemap.get(phase).containsKey(targetid)) {
            replacemap.get(phase).put(targetid, new HashMap<BiomeHolder.RARITY, List<Integer>>() {{
                put(BiomeHolder.RARITY.COMMON, new ArrayList<>());
                put(BiomeHolder.RARITY.UNCOMMON, new ArrayList<>());
                put(BiomeHolder.RARITY.RARE, new ArrayList<>());
            }});
        }
        replacemap.get(phase).get(targetid).get(rarity).add(biomeid);
    }

    public static Layer genLayers(long seed, Registry<Biome> registry) {
        IAreaFactory<LazyArea> areaFactory = genLayers((context) -> new LazyAreaLayerContext(25, seed, context), registry);
        return new Layer(areaFactory);
    }

    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> repeat(long seed, IAreaTransformer1 parent, IAreaFactory<T> iareafactoryparent, int count,
                                                                                              LongFunction<C> contextFactory) {
        IAreaFactory<T> iareafactory = iareafactoryparent;
        for (int i = 0; i < count; ++i) iareafactory = parent.run(contextFactory.apply(seed + (long) i), iareafactory);
        return iareafactory;
    }

    public static List<BiomeHolder> biomeByPhase(int phase){
        List<BiomeHolder> list = new ArrayList<>();
        for(BiomeHolder holder: BIOMES){
            if(holder.getPhase() == phase) list.add(holder);
        }
        return list;
    }

    public static List<Integer> idByPhase(int phase, Registry<Biome> reg){
        List<Integer> list = new ArrayList<>();
        for(BiomeHolder holder: BIOMES){
            if(holder.getPhase() == phase) list.add(holder.getBiomeId(reg));
        }
        return list;
    }

    public static HashMap<BiomeHolder.RARITY, List<BiomeHolder>> idByPhaseWithRarity(int phase){
        HashMap<BiomeHolder.RARITY,List<BiomeHolder>> hmap = Util.make(new HashMap<>(), s->{
            s.put(BiomeHolder.RARITY.COMMON, new ArrayList<>());
            s.put(BiomeHolder.RARITY.UNCOMMON, new ArrayList<>());
            s.put(BiomeHolder.RARITY.RARE, new ArrayList<>());
        });

        for(BiomeHolder holder: BIOMES){
            if(holder.getPhase() == phase) hmap.get(holder.getRarity()).add(holder);
        }
        return hmap;
    }

    public static HashMap<BiomeHolder.RARITY, List<Integer>> idByPhaseWithRarity(int phase, Registry<Biome> reg){
        HashMap<BiomeHolder.RARITY,List<Integer>> hmap = Util.make(new HashMap<>(), s->{
            s.put(BiomeHolder.RARITY.COMMON, new ArrayList<>());
            s.put(BiomeHolder.RARITY.UNCOMMON, new ArrayList<>());
            s.put(BiomeHolder.RARITY.RARE, new ArrayList<>());
        });

        for(BiomeHolder holder: BIOMES){
            if(holder.getPhase() == phase) hmap.get(holder.getRarity()).add(holder.getBiomeId(reg));
        }
        return hmap;
    }

}
