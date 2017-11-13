package com.example.nikola.criminal;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "com.example.nikola.criminal.crime_id";
    private static final String GET_ID_POSITION = "pos";
    private static final String CRIME_ITEM_KEY = "item_key";

    public static Intent onNewIntent(Context getPackage, UUID id) {
        CrimeLab.get(getPackage);
        Intent intent = new Intent(getPackage, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, id);
//        intent.putExtra(CRIME_ITEM_KEY, crime);
//        intent.putExtra(GET_ID_POSITION, idPosition);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
//        int idPos = getIntent().getIntExtra(GET_ID_POSITION, 0);
//        Crime crime = getIntent().getParcelableExtra(CRIME_ITEM_KEY);
        return CrimeFragment.newInstance(crimeId);
    }

}
