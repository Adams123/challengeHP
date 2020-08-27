package com.dextra.hp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Utils {

    /**
     * @return ifNull value is value is null
     */
    public static <T> T orDefault(T value, T ifNull) {
        return Objects.isNull(value) ? ifNull : value;
    }

    /**
     * @return an editable list if value is null.
     */
    public static <T> List<T> orDefaultList(List<T> value) {
        if (value == null) {
            return new ArrayList<T>();
        }
        return value;
    }
}
