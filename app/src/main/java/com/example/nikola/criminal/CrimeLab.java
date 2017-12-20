package com.example.nikola.criminal;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CrimeLab {

    private static CrimeLab sCrimeLab;

    //    private Map<UUID, Crime> mCrimes;
    private List<Crime> mCrimes;

    static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public void addCrimes(Crime c) {
        mCrimes.add(c);
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();

//        linked hashMap:
//        mCrimes = new LinkedHashMap<>();
//        for (int i = 0; i < 100; i++) {
//            Crime crime = new Crime();
//            crime.setTitle("Crime #" + " " + i);
//            crime.setSolved(i % 2 == 0);
////            crime.setRequiredPolice(i % 3 == 0);
//            mCrimes.put(crime.getID(), crime);
//
//        }
    }

    List<Crime> getCrimes() {
        return mCrimes;
//        return new ArrayList<>(mCrimes.values());
    }


    public Crime getCrime(UUID id) {
//        return mCrimes.get(id);
        for (Crime crime : mCrimes) {
            if (crime.getID().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}