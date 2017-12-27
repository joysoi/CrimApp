package com.example.nikola.soccerjar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.nikola.soccerjar.adapter.CompetitionsAdapter;
import com.example.nikola.soccerjar.database.ListItems;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerViewMain;
    ProgressDialog progressDialog;
    MainPresenter mainPresenter;
    CompetitionsAdapter competitionsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerViewMain.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewMain.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(this);
        mainPresenter = new MainPresenter(this);
        mainPresenter.getCompetitionList();
        competitionsAdapter = new CompetitionsAdapter();
        recyclerViewMain.setAdapter(competitionsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.registerView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainPresenter.unRegisterView();
    }

    @Override
    public void showCompetitionsList(List<ListItems> competitionResponses) {
        competitionsAdapter.getCompetitions(this, competitionResponses);
    }

    @Override
    public void unsuccesfulResponse() {
        Toast.makeText(this, R.string.please_wait, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }
}







