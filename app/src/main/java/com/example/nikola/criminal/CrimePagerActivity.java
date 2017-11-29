package com.example.nikola.criminal;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "com.example.nikola.criminal.crime_id";
    private List<Crime> mCrimes;
    private ViewPager viewPager;
    private Button firstButton;
    private Button lastButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);


        viewPager = findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager manager = getSupportFragmentManager();


        firstButton = findViewById(R.id.first_crime_btn);
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
                firstButton.setEnabled(false);
            }
        });

        lastButton = findViewById(R.id.last_crime_btn);
        lastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(mCrimes.size());
                lastButton.setEnabled(false);
            }
        });

        UUID uuid = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        viewPager.setAdapter(new FragmentStatePagerAdapter(manager) {
            @Override
            public int getCount() {
                return mCrimes.size();
            }

            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                firstButton.setEnabled(true);
                lastButton.setEnabled(true);
                return CrimeFragment.newInstance(crime.getID());
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getID().equals(uuid)) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent onNewIntent(Context getPackage, UUID id) {
        CrimeLab.get(getPackage);
        Intent intent = new Intent(getPackage, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, id);
        return intent;
    }
}

