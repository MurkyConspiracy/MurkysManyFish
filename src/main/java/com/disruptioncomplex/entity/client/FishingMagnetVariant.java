package com.disruptioncomplex.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum FishingMagnetVariant {

    DEFAULT(0),
    PRIDE(1);

    private static final FishingMagnetVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(FishingMagnetVariant::getId)).toArray(FishingMagnetVariant[]::new);
    private final int id;

    FishingMagnetVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }


    public static FishingMagnetVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

}
