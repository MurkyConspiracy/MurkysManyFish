package com.disruptioncomplex.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum BettaVariant {

    DEFAULT(0),
    PRIDE(1);

    private static final BettaVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(BettaVariant::getId)).toArray(BettaVariant[]::new);
    private final int id;

    BettaVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }


    public static BettaVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

}
