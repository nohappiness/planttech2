package net.kaneka.planttech2.world.planttopia.layers;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer2;

public enum GenLayerMixRiver implements AreaTransformer2, DimOffset0Transformer {
    INSTANCE;

    public int applyPixel(Context rand, Area area1, Area area2, int x, int y) {
        int i = area1.get(this.getParentX(x), this.getParentY(y));
        int j = area2.get(this.getParentX(x), this.getParentY(y));
        return i;
    }
}
