package com.dextra.hp.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BloodStatus {
    PURE_BLOOD("pure-blood"),
    HALF_BLOOD("half-blood"),
    MUGGLE_BORN("muggle-born"),
    MUGGLE("muggle"),
    QUARTER_VILLA("quarter-villa"),
    UNKOWN("unknown");

    String name;

    BloodStatus(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
