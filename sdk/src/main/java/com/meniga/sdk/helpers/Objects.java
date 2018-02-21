package com.meniga.sdk.helpers;

import javax.annotation.Nonnull;

public final class Objects {

    @Nonnull
    public static <T> T requireNonNull(T object) {
        if (object == null)
            throw new NullPointerException();
        return object;
    }

    private Objects() {
        throw new AssertionError("No instances.");
    }
}
