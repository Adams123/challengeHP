package com.dextra.hp.utils;

import lombok.experimental.UtilityClass;

import java.util.*;


@UtilityClass
public class Utils {

    /**
     * @return ifNull value is value is null
     */
    public <T> T orDefault(T value, T ifNull) {
        return Objects.isNull(value) ? ifNull : value;
    }

    /**
     * @return an editable list if value is null.
     */
    public <T> List<T> orDefaultList(List<T> value) {
        if (value == null) {
            return new ArrayList<>();
        }
        return value;
    }

    /**
     * @return an editable set if value is null.
     */
    public <T> Set<T> orDefaultSet(Set<T> value) {
        if (value == null) {
            return new HashSet<>();
        }
        return value;
    }
}
