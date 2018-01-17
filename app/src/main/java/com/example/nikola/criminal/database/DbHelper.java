package com.example.nikola.criminal.database;


import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

public class DbHelper {

    private static DbHelper INSTANCE;

    private DbHelper(Context context) {
    }


    public static DbHelper getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = new DbHelper(context);
        }
        return INSTANCE;
    }

    private AppDatabase mAppDatabase;

    public void init(Context context) {
        mAppDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "crimes.db")
                .allowMainThreadQueries()
                .build();
    }

    public List<Crime> getAllCrimes() {
        return mAppDatabase.mCrimeDao().getAllCrimes();
    }

    public Crime getSingleCrime(int id){
     return mAppDatabase.mCrimeDao().getCrimeById(id);
    }

    public void insertCrime(Crime crime) {
        mAppDatabase.mCrimeDao().insertCrime(crime);
    }

    public void deleteCrime(Crime crime) {
        mAppDatabase.mCrimeDao().deleteCrime(crime);
    }
}
