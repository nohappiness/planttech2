package net.kaneka.planttech2.world.planttopia;

import java.util.function.LongFunction;

import net.kaneka.planttech2.world.planttopia.biomes.layer.PlantTopiaBiomeLayer;
import net.kaneka.planttech2.world.planttopia.biomes.layer.PlantTopiaLayerUtil;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.layer.EdgeBiomeLayer;
import net.minecraft.world.gen.layer.ZoomLayer;

public class PlantTopiaWorldType extends WorldType {

	public PlantTopiaWorldType() {
		super("planttopia");
	}

	@Override
	public <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> getBiomeLayer(IAreaFactory<T> parentLayer, OverworldGenSettings chunkSettings, LongFunction<C> contextFactory) {
        parentLayer = (new PlantTopiaBiomeLayer(getWorldType(), (PlantTopiaGenSettings) chunkSettings)).apply(contextFactory.apply(200L), parentLayer);
        parentLayer = PlantTopiaLayerUtil.repeat(1000L, ZoomLayer.NORMAL, parentLayer, 2, contextFactory);
        parentLayer = EdgeBiomeLayer.INSTANCE.apply(contextFactory.apply(1000L), parentLayer);
        return parentLayer;
    }
	
	@Override
    public boolean canBeCreated() 
	{
        return false;
    }
	
}
