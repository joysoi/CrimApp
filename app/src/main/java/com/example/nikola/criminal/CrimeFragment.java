package com.example.nikola.criminal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.nikola.criminal.database.Crime;
import com.example.nikola.criminal.database.CrimeLabHelper;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "pick_a_date";
    private static final String DIALOG_TIME = "pick_time";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final String TAG = "Crime Fragment";
    private Crime mCrime;
    private Button mDateButton;
    private Button mTimeButton;
    CompositeDisposable mCompositeDisposable;


    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        mDateButton = v.findViewById(R.id.crime_date);
        final CheckBox solvedCheckBox = v.findViewById(R.id.crime_solved);
        final EditText titleField = v.findViewById(R.id.crime_title);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
//        mCrime = AppDatabase.getAppDatabase(getActivity()).mCrimeDao().getCrimeById(crimeId);
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(CrimeLabHelper.getInstance().getCrimeById(crimeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()
                ).subscribeWith(new DisposableSingleObserver<Crime>() {
                    @Override
                    public void onSuccess(Crime o) {
                        mCrime = o;
                        titleField.setText(mCrime.getTitle());
                        updateDate();
                        int hour = mCrime.getHour();
                        int minutes = mCrime.getMinute();
                        mTimeButton.setText(String.format("%d:%02d", hour, minutes));
                        solvedCheckBox.setChecked(mCrime.isSolved());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    titleField.setError(getString(R.string.edit_text_empty_warning));
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    titleField.setError(getString(R.string.edit_text_empty_warning));
                } else {
                    mCrime.setTitle(String.valueOf((charSequence)));
                    populateWithCrimes(mCrime);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    titleField.setError(getString(R.string.edit_text_empty_warning));
                }
            }
        });


        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (screenSize() < 450) {
                    Intent intent = DatePickerActivity.onNewIntent(getActivity(), mCrime.getDate());
                    startActivityForResult(intent, REQUEST_DATE);
                } else {
                    FragmentManager manager = getFragmentManager();
                    DatePickerFragment dialog = DatePickerFragment
                            .onNewInstance(mCrime.getDate());
                    dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                    dialog.show(manager, DIALOG_DATE);
                }
            }

        });

        mTimeButton = v.findViewById(R.id.crime_time);


        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment timeFragment = new TimePickerFragment();
                timeFragment.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                timeFragment.show(manager, DIALOG_TIME);
            }
        });



        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean setChecked) {
                mCrime.setSolved(setChecked);
                populateWithCrimes(mCrime);
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_item:
//                AppDatabase.getAppDatabase(getActivity()).mCrimeDao().deleteCrime(mCrime);
                deleteCrime(mCrime);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteCrime(final Crime crime) {
        mCompositeDisposable.add(CrimeLabHelper.getInstance().deleteCrime(crime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Crime successfully deleted ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
            populateWithCrimes(mCrime);
        }

        if (requestCode == REQUEST_TIME) {
            int hour = data.getIntExtra(TimePickerFragment.H, 0);
            int minute = data.getIntExtra(TimePickerFragment.M, 0);
            mCrime.setHour(hour);
            mCrime.setMinute(minute);
            mTimeButton.setText(String.valueOf(mCrime.getHour() + ":" + mCrime.getMinute()));
            populateWithCrimes(mCrime);
        }
    }

    private void updateDate() {
        mDateButton.setText(DateFormat.getDateInstance(DateFormat.FULL).format(mCrime.getDate()));
    }

    private void populateWithCrimes(Crime crime) {
//        AppDatabase.getAppDatabase(getActivity()).mCrimeDao().insertCrime(mCrime);
        mCompositeDisposable.add(CrimeLabHelper.getInstance().insertCrime(crime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Crime successfully inserted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    private float screenSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        float scaleFactor = metrics.density;

        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;

        return Math.min(widthDp, heightDp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.clear();
    }
}

