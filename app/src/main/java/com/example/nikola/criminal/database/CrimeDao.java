package com.example.nikola.criminal.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;
import java.util.UUID;

import io.reactivex.Single;

@Dao
public interface CrimeDao {

    @Query("SELECT * FROM Crime")
    Single<List<Crime>> getAllCrimes();

    @Query("SELECT * FROM crime WHERE id LIKE :id")
    Single<Crime> getCrimeById(UUID id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCrime(Crime crime);

    @Delete
    void deleteCrime(Crime crime);

}

