package com.example.nikola.criminal.database;


import android.arch.persistence.room.TypeConverter;

import java.util.UUID;

public class UUIDConverter {

    @TypeConverter
    public static UUID toUUID(Long value) {
        return value == null ? null : new UUID(value, value);
    }

    @TypeConverter
    public static Long toLong(UUID value) {
        return value == null ? null : value.getMostSignificantBits();
    }
}
