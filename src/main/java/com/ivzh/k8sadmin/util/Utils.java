package com.ivzh.k8sadmin.util;

import javax.validation.constraints.NotNull;
import java.util.Iterator;

public class Utils {

    private Utils () {}

    public static <T> T getFirstOrThrow(@NotNull Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            throw new IllegalArgumentException("empty collection has been provided");
        }
    }
}
