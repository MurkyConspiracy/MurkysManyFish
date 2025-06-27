package com.disruptioncomplex.entity.client;

import java.util.Arrays;
import java.util.Comparator;


public enum AnchovyVariant {

    DEFAULT(0),
    PRIDE(1);

    private static final AnchovyVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(AnchovyVariant::getId)).toArray(AnchovyVariant[]::new);
    private final int id;

    AnchovyVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }


    public static AnchovyVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

}
