package net.kaneka.planttech2.world.planttopia.layers;

import net.kaneka.planttech2.registries.ModReferences;
import net.minecraft.util.RegistryKey;
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
import java.util.function.LongFunction;

public class GenLayerUtils {

    protected static final HashMap<RegistryKey<Biome>, Set<BIOMEFLAGS>> FLAGS = Util.make(new HashMap<>(),
            (hmap) -> {
                categorize(hmap, getBiomeRegistryKey(ModReferences.BEE_FOREST), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.FOREST);
                categorize(hmap, getBiomeRegistryKey(ModReferences.CHORUS_FOREST), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.FOREST);
                categorize(hmap, getBiomeRegistryKey(ModReferences.DARK_WETLANDS), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.WETLAND);
                categorize(hmap, getBiomeRegistryKey(ModReferences.DEAD_FOREST), BIOMEFLAGS.COMMON, BIOMEFLAGS.FOREST);
                categorize(hmap, getBiomeRegistryKey(ModReferences.DREAM_FOREST), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.FOREST);
                categorize(hmap, getBiomeRegistryKey(ModReferences.DRIED_LAKE), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.WASTELAND, BIOMEFLAGS.ONLY_INSIDE);
                categorize(hmap, getBiomeRegistryKey(ModReferences.FLOWER_HILLS), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.MEADOW);
                categorize(hmap, getBiomeRegistryKey(ModReferences.FLOWER_MEADOWS), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.MEADOW);
                categorize(hmap, getBiomeRegistryKey(ModReferences.FLOWER_MOUNTAINS), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.MEADOW);
                categorize(hmap, getBiomeRegistryKey(ModReferences.ICY_CLIFFS), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.ICY_MEADOW, BIOMEFLAGS.ONLY_INSIDE);
                categorize(hmap, getBiomeRegistryKey(ModReferences.ICY_MEADOWS), BIOMEFLAGS.BASE, BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.ICY_MEADOW);
                categorize(hmap, getBiomeRegistryKey(ModReferences.LAKE), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.MEADOW, BIOMEFLAGS.ONLY_INSIDE);
                categorize(hmap, getBiomeRegistryKey(ModReferences.MEADOWS), BIOMEFLAGS.BASE, BIOMEFLAGS.COMMON, BIOMEFLAGS.MEADOW,BIOMEFLAGS.NORMAL);
                categorize(hmap, getBiomeRegistryKey(ModReferences.MUSHROOM_FOREST), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.FOREST);
                categorize(hmap, getBiomeRegistryKey(ModReferences.MUSHROOM_HILLS), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.FOREST);
                categorize(hmap, getBiomeRegistryKey(ModReferences.NIGHTMARE_FOREST), BIOMEFLAGS.BASE, BIOMEFLAGS.RARE, BIOMEFLAGS.FOREST);
                categorize(hmap, getBiomeRegistryKey(ModReferences.PUMPKIN_FOREST), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.FOREST);
                categorize(hmap, getBiomeRegistryKey(ModReferences.RADIATED_WASTELANDS), BIOMEFLAGS.BASE, BIOMEFLAGS.COMMON, BIOMEFLAGS.WASTELAND, BIOMEFLAGS.WARM);
                categorize(hmap, getBiomeRegistryKey(ModReferences.RIVER), BIOMEFLAGS.RIVER);
                categorize(hmap, getBiomeRegistryKey(ModReferences.VULCANO), BIOMEFLAGS.RARE, BIOMEFLAGS.WASTELAND);
                categorize(hmap, getBiomeRegistryKey(ModReferences.WASTELAND_MESA), BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.WASTELAND);
                categorize(hmap, getBiomeRegistryKey(ModReferences.WETLANDS), BIOMEFLAGS.BASE, BIOMEFLAGS.UNCOMMON, BIOMEFLAGS.WETLAND);

            }
    );

    private static void categorize(HashMap<RegistryKey<Biome>, Set<BIOMEFLAGS>> hmap, RegistryKey<Biome> key, BIOMEFLAGS... flags) {
        Set<BIOMEFLAGS> set = new HashSet<>(Arrays.asList(flags));
        hmap.put(key, set);
    }

    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> genLayers(LongFunction<C> seed, Registry<Biome> registry) {
        IAreaFactory<T> biomes = new GenLayerBase().setup(registry).run(seed.apply(1L));
        //biomes = ZoomLayer.FUZZY.run(seed.apply(1000L), biomes);
        biomes = repeat(1000L, ZoomLayer.NORMAL, biomes, 1, seed);
        biomes = new GenLayerPhase().setup(1, registry).run(seed.apply(1L), biomes);
        //biomes = ZoomLayer.FUZZY.run(seed.apply(1000L), biomes);
        biomes = repeat(1001L, ZoomLayer.NORMAL, biomes, 5, seed);

        return biomes;
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

    public static int getBiomeId(RegistryKey<Biome> biome, Registry<Biome> registry) {
            return registry.getId(registry.get(biome));
    }

    public static int getBiomeId(String biome, Registry<Biome> registry) {
        return registry.getId(registry.get(getBiomeRegistryKey(biome)));
    }

    private static RegistryKey<Biome> getBiomeRegistryKey(String s) {
        return RegistryKey.create(Registry.BIOME_REGISTRY, ModReferences.prefix(s));
    }

    public static List<RegistryKey<Biome>> byFlag(BIOMEFLAGS flag){
        List<RegistryKey<Biome>> list = new ArrayList<>();
        FLAGS.forEach((k,v) ->{
            if(v.contains(flag)) list.add(k);
        });
        return list;
    }

    public static List<RegistryKey<Biome>> byFlagsList(BIOMEFLAGS... flags){
        List<RegistryKey<Biome>> list = new ArrayList<>();
        FLAGS.forEach((k,v) ->{
            boolean fitting = true;
            for(BIOMEFLAGS f : flags)
                if (!v.contains(f)) {
                    fitting = false;
                    break;
                }
            if(fitting) list.add(k);
        });
        return list;
    }

    public static RegistryKey<Biome> byFlags(BIOMEFLAGS... flags){
        for(Map.Entry<RegistryKey<Biome>, Set<BIOMEFLAGS>> entry: FLAGS.entrySet()){
            boolean fitting = true;
            for(BIOMEFLAGS f : flags)
                if (!entry.getValue().contains(f)) {
                    fitting = false;
                    break;
                }
            if(fitting) return entry.getKey();
        }
        return null;
    }

    public static List<RegistryKey<Biome>> byFlagsExcept(BIOMEFLAGS except, BIOMEFLAGS... flags){
        List<RegistryKey<Biome>> list = new ArrayList<>();
        FLAGS.forEach((k,v) ->{
            boolean fitting = true;
            for(BIOMEFLAGS f : flags)
                if (!v.contains(f)) {
                    fitting = false;
                    break;
                }
            if(v.contains(except)) fitting = false;
            if(fitting) list.add(k);
        });
        return list;
    }



}
