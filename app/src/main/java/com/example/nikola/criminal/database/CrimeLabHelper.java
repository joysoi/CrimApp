package com.example.nikola.criminal.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;

public class CrimeLabHelper {

    private static CrimeLabHelper INSTANCE;

    public static CrimeLabHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrimeLabHelper();
        }
        return INSTANCE;
    }

    private CrimeLabHelper() {
    }

    private AppDatabase mAppDatabase;


    public void init(Context context) {
        mAppDatabase = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "crimes.db").build();
    }
    public Single<List<Crime>> getAllCrimes() {
        return mAppDatabase.mCrimeDao().getAllCrimes();
    }

    public Single<Crime> getCrimeById(UUID uuid) {
        return mAppDatabase.mCrimeDao().getCrimeById(uuid);
    }

    public Observable insertCrime(final Crime crime) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                if (!e.isDisposed()) {
                    mAppDatabase.mCrimeDao().insertCrime(crime);
                    e.onComplete();
                }
            }
        });
    }

    public Observable deleteCrime(final Crime crime) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                if (!e.isDisposed()) {
                    mAppDatabase.mCrimeDao().deleteCrime(crime);
                    e.onComplete();
                }
            }
        });
    }


}
