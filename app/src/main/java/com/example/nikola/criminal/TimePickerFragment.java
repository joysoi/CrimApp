package com.example.nikola.criminal;


import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_HOUR = "extra_hour";
    public static final String H = "h";
    public static final String M = "m";
    TimePicker mTimePicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        mTimePicker = view.findViewById(R.id.time_picker);
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        sendResult(Activity.RESULT_OK, hour, minute);
    }

    private void sendResult(int resultOk, int hour, int minute) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(H, hour);
        intent.putExtra(M, minute);
        intent.putExtra(EXTRA_HOUR, hour);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk, intent);
    }

}


