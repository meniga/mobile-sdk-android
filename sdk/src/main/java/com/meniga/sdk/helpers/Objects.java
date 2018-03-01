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

    @Nonnull
    public static <T> T firstNonNull(@Nullable T first, @Nonnull T second) {
        return first != null ? first : requireNonNull(second);
    }

    private Objects() {
        throw new AssertionError("No instances.");
    }
}
