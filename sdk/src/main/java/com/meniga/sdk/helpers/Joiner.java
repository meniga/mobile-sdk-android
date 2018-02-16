package com.meniga.sdk.helpers;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

public final class Joiner {

    public static <T> String join(Iterable<T> iterable, CharSequence delimiter) {
        return Stream.of(iterable)
                .map(Object::toString)
                .collect(Collectors.joining(delimiter));
    }

    private Joiner() {
        throw new AssertionError("No instances.");
    }
}
