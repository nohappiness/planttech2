package net.kaneka.planttech2.world.planttopia.layers;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum BIOMEFLAGS {
    BASE(0),
    //CATEGORY
    MEADOW(1),
    ICY_MEADOW(1),
    WASTELAND(1),
    FOREST(1),
    WETLAND(1),

    //RARITY
    COMMON(-1),
    UNCOMMON(-1),
    RARE(-1),

    //SPECIAL CATEGORY
    LAKE(-1),
    RIVER(-1),

    //TEMPERATURE
    COLD(-1),
    WARM(-1),
    NORMAL(-1),
    //SPECIAL
    ONLY_INSIDE(-1);

    private final int phase;
    BIOMEFLAGS(int phase) {
        this.phase = phase;
    }

    public int phase(){
        return phase;
    }

    public static Set<BIOMEFLAGS> byPhase(int phase){
        return Arrays.stream(BIOMEFLAGS.values()).filter(f -> f.phase() == phase).collect(Collectors.toSet());
    }
}
