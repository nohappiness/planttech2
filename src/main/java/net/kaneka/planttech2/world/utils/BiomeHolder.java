package net.kaneka.planttech2.world.utils;

import net.kaneka.planttech2.registries.ModReferences;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class BiomeHolder {
    private final ResourceKey<Biome> biome;
    private ResourceKey<Biome> targetBiome;
    private final RARITY rarity;
    private final int phase;
    private boolean onlyInside = false;
    private boolean isRiver = false;
    private int id = -1, targetId = -1; // value for not initialized

    public BiomeHolder(String biome, RARITY rarity, int phase) {
        this.biome = getBiomeRegistryKey(biome);
        this.rarity = rarity;
        this.phase = phase;
    }

    public BiomeHolder addTarget(String targetBiome, boolean onlyInside){
        this.targetBiome = getBiomeRegistryKey(targetBiome);
        this.onlyInside = onlyInside;
        return this;
    }

    public BiomeHolder addTarget(String targetBiome){
        return addTarget(targetBiome, false);
    }

    public BiomeHolder river(){
        this.isRiver = true;
        return this;
    }

    public boolean isRiver(){
        return isRiver;
    }

    public int getBiomeId(Registry<Biome> registry) {
        if (id == -1) {
            id = registry.getId(registry.get(biome));
        }
        return id;
    }

    public int getTargetId(Registry<Biome> registry){
        if(hasTarget()) {
            if (targetId == -1) {
                targetId = registry.getId(registry.get(targetBiome));
            }
            return targetId;
        }
        return -1;
    }

    public boolean hasTarget() {
        return targetBiome != null;
    }

    private ResourceKey<Biome> getBiomeRegistryKey(String s) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, ModReferences.prefix(s));
    }

    public int getPhase() {
        return phase;
    }

    public RARITY getRarity() {
        return rarity;
    }

    public boolean isOnlyInside() {
        return onlyInside;
    }

    public static enum RARITY{
        COMMON(0),
        UNCOMMON(1),
        RARE(2);

        private int value;
        private RARITY(int value){
            this.value = value;
        }

        public int getRarity(){
            return value;
        }
    }
}
