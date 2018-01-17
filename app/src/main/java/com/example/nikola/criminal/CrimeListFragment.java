package com.example.nikola.criminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikola.criminal.database.Crime;
import com.example.nikola.criminal.database.DbHelper;

import java.text.DateFormat;
import java.util.List;


public class CrimeListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private List<Crime> mCrimes;
    private int mLastPositionChanged = -1;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        TextView noDataView = view.findViewById(R.id.no_data_txt_view);
        Button newCrimeBtn = view.findViewById(R.id.new_crime_btn);
        newCrimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewCrime();
            }
        });
        if (mAdapter.getItemCount() == 0) {
            mCrimeRecyclerView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            newCrimeBtn.setVisibility(View.VISIBLE);
        } else {
            mCrimeRecyclerView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
            newCrimeBtn.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.new_crime:
                createNewCrime();
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNewCrime() {
        Crime crime = new Crime();
//        CrimeLab.get(getActivity()).addCrimes(crime);
        DbHelper.getInstance(getActivity()).insertCrime(crime);
//          once created, the crime has to be edited:
        Intent intent = CrimePagerActivity.onNewIntent(getActivity(), crime.getID());
        startActivity(intent);
    }

    private void updateSubtitle() {
//        CrimeLab crimeLab = CrimeLab.get(getActivity());
        DbHelper dbHelper = DbHelper.getInstance(getActivity());
        int crimeCount = dbHelper.getAllCrimes().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, crimeCount, crimeCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
//        CrimeLab crimeLab = CrimeLab.get(getActivity());
        DbHelper dbHelper = DbHelper.getInstance(getActivity());
        List<Crime> crimes = dbHelper.getAllCrimes();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else if (mLastPositionChanged > -1) {
            mAdapter.notifyItemChanged(mLastPositionChanged);
            mLastPositionChanged = -1;
        } else {
            mAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeViewHolder> {


        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeViewHolder(layoutInflater, parent);


        }

        @Override
        public void onBindViewHolder(CrimeViewHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }


//        @Override
//        public int getItemViewType(int position) {
//            Crime crime = mCrimes.get(position);
//            if (crime.isRequiredPolice()) {
//                return R.layout.list_item_crime;
//            } else
//                return R.layout.serious_list_item_crime;
//        }
    }


    private class CrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        //        private Button mCallPoliceButton;
        private ImageView mSolvedImageView;
        private Crime mCrime;


        public CrimeViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
            mSolvedImageView = itemView.findViewById(R.id.crime_solved);

        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(DateFormat.getDateInstance(DateFormat.FULL).format(mCrime.getDate()));
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            mLastPositionChanged = this.getAdapterPosition();
            Intent intent = CrimePagerActivity.onNewIntent(getActivity(), mCrime.getID());
//            Intent intent = CrimeActivity.onNewIntent(getActivity(), mCrimes.get(mLastPositionChanged));
            startActivity(intent);
        }
    }
}

//
//    private class SeriousCrimeViewHolder extends AbstractCrimeViewHolder {
//        public SeriousCrimeViewHolder(LayoutInflater inflater, ViewGroup parent) {
////            super(inflater, parent, R.layout.serious_list_item_crime);
//            super(inflater, parent, R.layout.list_item_crime);
//        }
//    }

