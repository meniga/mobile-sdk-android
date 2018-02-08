package com.meniga.sdk.helpers;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public final class DateTimeUtils {

    public static String toString(DateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();
        return dateTimeFormatter.print(dateTime);
    }

    private DateTimeUtils() {
        throw new AssertionError("No instances.");
    }
}
