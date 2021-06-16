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

    protected static final HashMap<RegistryKey<Biome>, Set<BIOMEFLAGS>> FLAGS = Util.make(new HashMap<RegistryKey<Biome>, Set<BIOMEFLAGS>>(),
            (hmap) -> {
                categorize(hmap, getBiomeRegistryKey(ModReferences.BEE_FOREST));
                categorize(hmap, getBiomeRegistryKey(ModReferences.CHORUS_FOREST));
                categorize(hmap, getBiomeRegistryKey(ModReferences.DARK_WETLANDS));
                categorize(hmap, getBiomeRegistryKey(ModReferences.DEAD_FOREST));
                categorize(hmap, getBiomeRegistryKey(ModReferences.DREAM_FOREST));
                categorize(hmap, getBiomeRegistryKey(ModReferences.DRIED_LAKE), BIOMEFLAGS.WARM, BIOMEFLAGS.LAKE);
                categorize(hmap, getBiomeRegistryKey(ModReferences.FLOWER_HILLS));
                categorize(hmap, getBiomeRegistryKey(ModReferences.FLOWER_MEADOWS), BIOMEFLAGS.NORMAL);
                categorize(hmap, getBiomeRegistryKey(ModReferences.FLOWER_MOUNTAINS));
                categorize(hmap, getBiomeRegistryKey(ModReferences.ICY_CLIFFS), BIOMEFLAGS.COLD);
                categorize(hmap, getBiomeRegistryKey(ModReferences.ICY_MEADOWS), BIOMEFLAGS.BASE, BIOMEFLAGS.UNCOMMON);
                categorize(hmap, getBiomeRegistryKey(ModReferences.LAKE), BIOMEFLAGS.LAKE, BIOMEFLAGS.NORMAL);
                categorize(hmap, getBiomeRegistryKey(ModReferences.MEADOWS), BIOMEFLAGS.BASE, BIOMEFLAGS.COMMON, BIOMEFLAGS.NORMAL);
                categorize(hmap, getBiomeRegistryKey(ModReferences.MUSHROOM_FOREST));
                categorize(hmap, getBiomeRegistryKey(ModReferences.MUSHROOM_HILLS));
                categorize(hmap, getBiomeRegistryKey(ModReferences.NIGHTMARE_FOREST), BIOMEFLAGS.BASE, BIOMEFLAGS.RARE);
                categorize(hmap, getBiomeRegistryKey(ModReferences.PUMPKIN_FOREST));
                categorize(hmap, getBiomeRegistryKey(ModReferences.RADIATED_WASTELANDS), BIOMEFLAGS.BASE, BIOMEFLAGS.COMMON, BIOMEFLAGS.WARM);
                categorize(hmap, getBiomeRegistryKey(ModReferences.RIVER), BIOMEFLAGS.RIVER);
                categorize(hmap, getBiomeRegistryKey(ModReferences.VULCANO));
                categorize(hmap, getBiomeRegistryKey(ModReferences.WASTELAND_MESA));
                categorize(hmap, getBiomeRegistryKey(ModReferences.WETLANDS), BIOMEFLAGS.BASE, BIOMEFLAGS.UNCOMMON);

            }
    );

    private static void categorize(HashMap<RegistryKey<Biome>, Set<BIOMEFLAGS>> hmap, RegistryKey<Biome> key, BIOMEFLAGS... flags) {
        Set<BIOMEFLAGS> set = new HashSet<BIOMEFLAGS>();
        Arrays.asList(flags).stream().forEach(flag -> set.add(flag));
        hmap.put(key, set);
    }

    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> genLayers(LongFunction<C> seed, Registry<Biome> registry) {
        IAreaFactory<T> biomes = new GenLayerBase().setup(registry).run(seed.apply(1L));
        biomes = ZoomLayer.FUZZY.run(seed.apply(2000L), biomes);
        biomes = repeat(1000L, ZoomLayer.NORMAL, biomes, 6, seed);


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

    private static RegistryKey<Biome> getBiomeRegistryKey(String s) {
        return RegistryKey.create(Registry.BIOME_REGISTRY, ModReferences.prefix(s));
    }

    public static boolean onlyInside(String s) {
        return FLAGS.get(getBiomeRegistryKey(s)).contains(BIOMEFLAGS.ONLY_INSIDE);
    }

    public static List<RegistryKey<Biome>> byFlag(BIOMEFLAGS flag){
        List<RegistryKey<Biome>> list = new ArrayList<>();
        FLAGS.forEach((k,v) ->{
            if(v.contains(flag)) list.add(k);
        });
        return list;
    }

    public static List<RegistryKey<Biome>> byFlags(BIOMEFLAGS... flags){
        List<RegistryKey<Biome>> list = new ArrayList<>();
        FLAGS.forEach((k,v) ->{
            boolean fitting = true;
            for(BIOMEFLAGS f : flags) if(!v.contains(f)) fitting = false;
            if(fitting) list.add(k);
        });
        return list;
    }
}
