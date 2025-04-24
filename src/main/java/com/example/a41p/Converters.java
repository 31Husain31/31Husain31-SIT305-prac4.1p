package com.example.a41p;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * This class handles conversion between Date and Long (timestamp) for Room.
 */
public class Converters {

    // Convert timestamp (Long) to Date
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    // Convert Date to timestamp (Long)
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
