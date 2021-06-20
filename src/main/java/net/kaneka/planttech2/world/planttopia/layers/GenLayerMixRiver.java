package net.kaneka.planttech2.world.planttopia.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum GenLayerMixRiver implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    public int applyPixel(INoiseRandom rand, IArea area1, IArea area2, int x, int y) {
        int i = area1.get(this.getParentX(x), this.getParentY(y));
        int j = area2.get(this.getParentX(x), this.getParentY(y));
        return i;
    }
}
