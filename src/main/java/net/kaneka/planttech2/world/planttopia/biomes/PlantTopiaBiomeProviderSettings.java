package net.kaneka.planttech2.world.planttopia.biomes;

import net.kaneka.planttech2.world.planttopia.PlantTopiaGenSettings;
import net.kaneka.planttech2.world.planttopia.PlantTopiaWorldType;
import net.minecraft.world.biome.provider.IBiomeProviderSettings;
import net.minecraft.world.storage.WorldInfo;

public class PlantTopiaBiomeProviderSettings implements IBiomeProviderSettings {
    private WorldInfo worldInfo;
    private PlantTopiaGenSettings generatorSettings;

    public PlantTopiaBiomeProviderSettings() {
    }

    public PlantTopiaBiomeProviderSettings setWorldInfo(WorldInfo worldInfo) {
    	worldInfo.setGenerator(new PlantTopiaWorldType());
        this.worldInfo = worldInfo;
        return this;
    }

    public PlantTopiaBiomeProviderSettings setGeneratorSettings(PlantTopiaGenSettings generatorSettings) {
        this.generatorSettings = generatorSettings;
        return this;
    }

    public WorldInfo getWorldInfo() {
    	worldInfo.setGenerator(new PlantTopiaWorldType());
        return worldInfo;
    }

    public PlantTopiaGenSettings getGeneratorSettings() {
        return generatorSettings;
    }
}