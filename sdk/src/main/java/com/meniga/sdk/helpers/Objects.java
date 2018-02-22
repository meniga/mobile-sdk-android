package com.meniga.sdk.helpers;

import javax.annotation.Nonnull;

public final class Objects {

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public static <T> T requireNonNull(@Nonnull T object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }

    private Objects() {
        throw new AssertionError("No instances.");
    }
}
