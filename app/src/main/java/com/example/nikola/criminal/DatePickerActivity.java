package com.example.nikola.criminal;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.Date;


public class DatePickerActivity extends SingleFragmentActivity {


    private static final String ARG_DATE = "date_arg";

    @Override
    protected Fragment createFragment() {
        Date date = (Date) getIntent().getSerializableExtra(ARG_DATE);
        return DatePickerFragment.onNewInstance(date);
    }

    public static Intent onNewIntent(Context context, Date date) {
        Intent intent = new Intent(context, DatePickerActivity.class);
        intent.putExtra(ARG_DATE, date);
        return intent;
    }

}
