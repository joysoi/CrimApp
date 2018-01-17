package com.example.nikola.criminal.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CrimeDao {

    @Query("SELECT * FROM Crime")
    List<Crime> getAllCrimes();

    @Query("SELECT * FROM Crime WHERE id = :id")
    Crime getCrimeById(int id);

    @Insert
    void insertCrime(Crime crime);

    @Delete
    void deleteCrime(Crime crime);

}
