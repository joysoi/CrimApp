package com.example.nikola.criminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;


public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private List<Crime> mCrimes;
    private int mLastPositionChanged = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();

    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else if (mLastPositionChanged > -1) {
            mAdapter.notifyItemChanged(mLastPositionChanged);
            mLastPositionChanged = -1;
        } else {
            mAdapter.notifyDataSetChanged();
        }
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

