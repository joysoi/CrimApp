package com.example.nikola.criminal.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {Crime.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, UUIDConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CrimeDao mCrimeDao();
}
