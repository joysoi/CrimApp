package com.example.nikola.criminal.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.io.File;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;

public class CrimeLabHelper {

    private static CrimeLabHelper INSTANCE;
    private Context mContext;

    public static CrimeLabHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CrimeLabHelper(context);
        }
        return INSTANCE;
    }

    private CrimeLabHelper(Context context) {
        mContext = context.getApplicationContext();
    }

    private AppDatabase mAppDatabase;


    public void init(Context context) {
        mAppDatabase = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "crimes.db").build();
    }

//    This method returns File objects that point in the right direction

    public File getPhotoFile(Crime crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }

    public Single<List<Crime>> getAllCrimes() {
        return mAppDatabase.mCrimeDao().getAllCrimes();
    }

    public Single<Crime> getCrimeById(UUID uuid) {
        return mAppDatabase.mCrimeDao().getCrimeById(uuid);
    }


    //Observables represent the sources of data
    public Observable insertCrime(final Crime crime) {
        return Observable.create(new ObservableOnSubscribe() {
            //They start emitting data once a subscriber starts listening
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                //It can be terminated eater successfully or with an error
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

/*An Observable is like speaker which emit value. It does some work and emits some values.
The following are the different types of Observables in RxJava:
    Observable  (emit more than one value)
    Flowable    (when the Observable is emitting huge number of values and they cant be consumed by the Observer,
                   uses BackPressureStrategy that buffers most of the data and keeps only the latest)
    Single      (used when the Observable emit only one value, like a response from a network call)
    Maybe       (used when the Observable emits a value or no value)
    Completable (used when the Observable has to do some task without emitting a value)


An Observer/Subscriber gets those values.
Types of Observers/Subscribers in RxJava:
    Observer
    SingleObserver
    MaybeObserver
    CompletableObserver
 */

/*
CompositeDisposable is a container that holds other disposables
*/

