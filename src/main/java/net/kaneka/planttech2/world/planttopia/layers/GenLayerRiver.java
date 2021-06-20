package net.kaneka.planttech2.world.planttopia.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenLayerRiver implements ICastleTransformer {

    private int riverid;
    private HashMap<Integer, List<Integer>> categories;

    public GenLayerRiver setup(int riverid, HashMap<Integer, List<Integer>> category){
        this.riverid = riverid;
        this.categories = category;
        return this;
    }

    @Override
    public int apply(INoiseRandom rand, int north, int west, int south, int east, int center) {

        if(category(north) != category(south) || category(west) != category(east)){
            return riverid;
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
}
