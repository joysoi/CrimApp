package com.example.nikola.criminal;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;
    Crime crime = new Crime();
//    LinkedHashMap<UUID, Crime> hashMap = new LinkedHashMap();

    static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            crime = new Crime();
            crime.setTitle("Crime #" + " " + i);
            crime.setSolved(i % 2 == 0);
            crime.setRequiredPolice(i % 3 == 0);
//            hashMap.put(crime.getID(), crime);
            mCrimes.add(crime);
        }
    }

    List<Crime> getCrimes() {
        return mCrimes;
    }


//    public Crime getCrime(UUID id, int idPos) {
//        if (mCrimes.size() == idPos) {
//            if (crime.getID().equals(id)){
//                return crime;
//            }else {
//                return null;
//            }
//        }
//        return null;
//    }
//}

    public Crime getCrime(UUID id) {

        for (Crime crime : mCrimes) {
            if (crime.getID().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
