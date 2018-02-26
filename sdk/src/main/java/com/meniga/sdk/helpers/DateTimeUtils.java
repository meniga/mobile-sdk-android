package com.meniga.sdk.helpers;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.annotation.Nonnull;

import static com.meniga.sdk.helpers.Objects.requireNonNull;

public final class DateTimeUtils {

    @Nonnull
    public static String toString(@Nonnull DateTime dateTime) {
        requireNonNull(dateTime);
        DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();
        return dateTimeFormatter.print(dateTime);
    }

    private DateTimeUtils() {
        throw new AssertionError("No instances.");
    }
}
