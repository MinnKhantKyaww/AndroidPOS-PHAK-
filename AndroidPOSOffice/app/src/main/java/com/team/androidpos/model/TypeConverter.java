package com.team.androidpos.model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.FormatUtils;

import java.util.TimeZone;

public class TypeConverter {

    @androidx.room.TypeConverter
    public static long fromDateTime(LocalDateTime dateTime) {

        return dateTime.toDateTime(DateTimeZone.getDefault()).getMillis();
    }

    @androidx.room.TypeConverter
    public static LocalDateTime toDateTime(long value) {
        return new DateTime(value, DateTimeZone.getDefault()).toLocalDateTime();
    }
}
