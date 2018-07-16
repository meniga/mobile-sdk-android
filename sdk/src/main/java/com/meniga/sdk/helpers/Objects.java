package com.meniga.sdk.helpers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Objects {

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public static <T> T requireNonNull(@Nonnull T object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }

    public static <T> boolean equals(@Nullable T a, @Nullable T b) {
        return (a == b) || (a != null && a.equals(b));
    }

    @Nonnull
    public static <T> T firstNonNull(@Nullable T first, @Nonnull T second) {
        return first != null ? first : requireNonNull(second);
    }

    public static void checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    private Objects() {
        throw new AssertionError("No instances.");
    }
}
