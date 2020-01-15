package com.ivzh.k8sadmin.util;

import javax.validation.constraints.NotNull;
import java.util.Iterator;
import java.util.Objects;

public class Utils {

    private Utils () {}

    public static <T> T getFirstOrDefault(@NotNull Iterable<T> iterable, @NotNull T defaultValue) {
        if (Objects.isNull(defaultValue)) {
            return defaultValue;
        }

        Iterator<T> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return defaultValue;
        }
    }
}
