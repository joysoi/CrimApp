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


public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime,container,false);

        mTitleField = v.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean setChecked) {
                mCrime.setSolved(setChecked);
            }
        });


        return v;
    }
}














///package com.bignerdranch.android.criminalintent;
//
//        import android.os.Bundle;
//        import android.support.v4.app.Fragment;
//        import android.support.v7.widget.LinearLayoutManager;
//        import android.support.v7.widget.RecyclerView;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import java.util.List;
//
//public class CrimeListFragment extends Fragment {
//    private RecyclerView mCrimeRecyclerView;
//    private CrimeAdapter mAdapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
//
//        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
//        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        updateUI();
//
//        return view;
//    }
//
//    private void updateUI() {
//        CrimeLab crimeLab = CrimeLab.get(getActivity());
//        List<Crime> crimes = crimeLab.getCrimes();
//
//        mAdapter = new CrimeAdapter(crimes);
//        mCrimeRecyclerView.setAdapter(mAdapter);
//    }
//
//    private abstract class AbstractCrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private TextView mTitleTextView;
//        private TextView mDateTextView;
//        private Crime mCrime;
//
//        public AbstractCrimeHolder(LayoutInflater inflater, ViewGroup parent, int layoutId) {
//            super(inflater.inflate(layoutId, parent, false));
//            itemView.setOnClickListener(this);
//
//            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
//            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
//        }
//
//        public void bind(Crime crime) {
//            mCrime = crime;
//            mTitleTextView.setText(mCrime.getTitle());
//            mDateTextView.setText(mCrime.getDate().toString());
//        }
//
//        public Crime getCrime() {
//            return mCrime;
//        }
//
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(getActivity(),
//                    getCrime().getTitle() + " clicked!", Toast.LENGTH_SHORT)
//                    .show();
//        }
//    }
//
//    private class CrimeHolder extends AbstractCrimeHolder {
//        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater, parent, R.layout.list_item_crime);
//        }
//    }
//
//    private class PoliceCrimeHolder extends AbstractCrimeHolder {
//        public PoliceCrimeHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater, parent, R.layout.list_item_crime_police);
//        }
//    }
//
//    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//        private static final int LIST_ITEM_CRIME = 0;
//        private static final int LIST_ITEM_CRIME_POLICE = 1;
//
//        private List<Crime> mCrimes;
//
//        public CrimeAdapter(List<Crime> crimes) {
//            mCrimes = crimes;
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            if (viewType == LIST_ITEM_CRIME) {
//                return new CrimeHolder(layoutInflater, parent);
//            } else if (viewType == LIST_ITEM_CRIME_POLICE) {
//                return new PoliceCrimeHolder(layoutInflater, parent);
//            } else {
//                return null;
//            }
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            Crime crime = mCrimes.get(position);
//            if (holder instanceof CrimeHolder) {
//                ((CrimeHolder) holder).bind(crime);
//            } else if (holder instanceof PoliceCrimeHolder) {
//                ((PoliceCrimeHolder) holder).bind(crime);
//            }
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return mCrimes.size();
//        }
//
//        @Override
//        public int getItemViewType (int position) {
//            boolean requiresPolice = mCrimes.get(position).isRequiresPolice();
//            if (requiresPolice) {
//                return LIST_ITEM_CRIME_POLICE;
//            } else {
//                return LIST_ITEM_CRIME;
//            }
//        }
//    }