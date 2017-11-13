package com.example.nikola.criminal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.UUID;


public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String ARG_POS_ID = "position";
    private static final String ARG_CRIME_PARCELABLE = "extra";
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public static CrimeFragment newInstance(UUID crimeId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
//        args.putInt(ARG_POS_ID, idPosition);
//        args.putParcelable(ARG_CRIME_PARCELABLE, crime);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mCrime = getActivity().getIntent().getParcelableExtra(ARG_CRIME_PARCELABLE);
//        mCrime = getArguments().getParcelable(ARG_CRIME_PARCELABLE);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
//        int idPos = getArguments().getInt(ARG_POS_ID);
//
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(String.valueOf((charSequence)));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = v.findViewById(R.id.crime_date);
        mDateButton.setText(DateFormat.getDateInstance(DateFormat.FULL).format(mCrime.getDate()));
        mDateButton.setEnabled(false);

        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean setChecked) {
                mCrime.setSolved(setChecked);
            }
        });
        return v;
    }
}

