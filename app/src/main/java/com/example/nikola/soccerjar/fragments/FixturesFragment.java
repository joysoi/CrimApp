package com.example.nikola.soccerjar.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.nikola.soccerjar.R;
import com.example.nikola.soccerjar.adapter.FixturesAdapter;
import com.example.nikola.soccerjar.database.ListItems;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.nikola.soccerjar.DetailsActivity.ID_KEY;

public class FixturesFragment extends Fragment implements FixturesView {

    @BindView(R.id.showResultsBTN)
    Button btnMoreResults;
    @BindView(R.id.recyclerFixturesView)
    RecyclerView recyclerViewFixtures;
    ProgressDialog progressDialog;
    FixturesAdapter fixturesAdapter;
    FixturesPresenter presenter;

    public FixturesFragment newInstance(int id) {
        FixturesFragment f = new FixturesFragment();
        Bundle args = new Bundle();
        args.putInt(ID_KEY, id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.registerView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unRegisterView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.my_fixtures_detail_view, container, false);
        ButterKnife.bind(this, view);
        recyclerViewFixtures.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewFixtures.setLayoutManager(layoutManager);
        fixturesAdapter = new FixturesAdapter();
        recyclerViewFixtures.setAdapter(fixturesAdapter);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.please_wait));
        presenter = new FixturesPresenter();
        if (!getArguments().isEmpty()) {
            int id = getArguments().getInt(ID_KEY);
            presenter.getTeamNames(id);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fixtures_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.getFilteredList(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showTeamsWithScheduledStatus(List<ListItems> fixtureList) {
        fixturesAdapter.updateList(fixtureList);
    }

    @Override
    public void showAllTeams(List<ListItems> fixtureList) {
        fixturesAdapter.updateList(fixtureList);
    }

    @Override
    public void showFIlteredList(List<ListItems> filteredList) {
        fixturesAdapter.updateList(filteredList);
    }

    @Override
    public void unsucessfulResponse() {
        Toast.makeText(getActivity(), R.string.noListPresented, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @OnClick(R.id.showResultsBTN)
    public void onShowResultsClick() {
        presenter.getPreviousGames();
    }

}

