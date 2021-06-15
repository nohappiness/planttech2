package net.kaneka.planttech2.world.planttopia.layers;

import com.google.common.collect.ImmutableList;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.LongFunction;

public class GenLayerUtils {

    protected static final HashMap<BIOME_TYPE, List<RegistryKey<Biome>>> CATEGORYS = Util.make(new HashMap<BIOME_TYPE, List<RegistryKey<Biome>>>(),
            (hmap) -> {
                prepareCategories(hmap);
                categorize(hmap, getBiomeRegistryKey(ModReferences.FLOWER_MEADOW), BIOME_TYPE.NORMAL);
                categorize(hmap, getBiomeRegistryKey(ModReferences.RADIATED_WASTELAND));
                categorize(hmap, getBiomeRegistryKey(ModReferences.LAKE));
                categorize(hmap, getBiomeRegistryKey(ModReferences.DRIED_LAKE));
                categorize(hmap, getBiomeRegistryKey(ModReferences.Icy));

            }
    );

    private static void prepareCategories(HashMap<BIOME_TYPE, List<RegistryKey<Biome>>> categorys){
        Arrays.asList(BIOME_TYPE.values()).forEach(type -> categorys.put(type, new ArrayList<RegistryKey<Biome>>()));
    }

    private static void categorize(HashMap<BIOME_TYPE, List<RegistryKey<Biome>>> categorys,RegistryKey<Biome> key, GenLayerUtils.BIOME_TYPE... types){
        Arrays.asList(types).stream().forEach(type -> categorys.get(type).add(key));
    }

    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> genLayers(LongFunction<C> seed, Registry<Biome> registry) {
        IAreaFactory<T> biomes = new GenLayerBiomes().setup(registry).run(seed.apply(1L));
        biomes = ZoomLayer.FUZZY.run(seed.apply(2000L), biomes);
        biomes = repeat(1000L, ZoomLayer.NORMAL, biomes, 6, seed);


        return biomes;
    }

    public static Layer genLayers(long seed, Registry<Biome> registry) {
        IAreaFactory<LazyArea> areaFactory = genLayers((context) -> new LazyAreaLayerContext(25, seed, context), registry);
        return new Layer(areaFactory);
    }

    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> repeat(long seed, IAreaTransformer1 parent, IAreaFactory<T> p_202829_3_, int count,
                                                                                              LongFunction<C> contextFactory) {
        IAreaFactory<T> iareafactory = p_202829_3_;

        for (int i = 0; i < count; ++i)
        {
            iareafactory = parent.run(contextFactory.apply(seed + (long) i), iareafactory);
        }

        return iareafactory;
    }

    public static int getBiomeId(RegistryKey<Biome> biome, Registry<Biome> registry) {
        return registry.getId(registry.get(biome));
    }

    private static RegistryKey<Biome> getBiomeRegistryKey(String s){
        return RegistryKey.create(Registry.BIOME_REGISTRY, ModReferences.prefix(s));
    }

    public static enum BIOME_TYPE{
        BASE,
        LAKE,
        RIVER,
        COLD,
        WARM,
        NORMAL;
    }
}
