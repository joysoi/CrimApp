package com.example.nikola.soccerjar.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nikola.soccerjar.R;

import static com.example.nikola.soccerjar.DetailsActivity.ID_KEY;


public class FixturesActivity extends AppCompatActivity {

    public int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);

        Intent intent = getIntent();
        id = intent.getIntExtra(ID_KEY, 0);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new FixturesFragment().newInstance(id)).commit();
    }
}