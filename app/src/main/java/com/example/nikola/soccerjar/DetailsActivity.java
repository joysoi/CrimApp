package com.example.nikola.soccerjar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nikola.soccerjar.adapter.LeagueTablesAdapter;
import com.example.nikola.soccerjar.database.ListItems;
import com.example.nikola.soccerjar.fragments.FixturesActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity implements DetailsView {

    @BindView(R.id.my_recyclerDetail_view)
    RecyclerView recyclerView;
    @BindView(R.id.my_detailToolbar)
    Toolbar toolbar;
    public int id;
    DetailsPresenter detailsPresenter;
    LeagueTablesAdapter tablesAdapter;
    ProgressDialog progressDialog;
    public static final String CAPTION_KEY = "caption";
    public static final String ID_KEY = "id";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_detail);
        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        id = intent.getIntExtra(ID_KEY, 0);
        String pageName = intent.getStringExtra(CAPTION_KEY);
        toolbar.setTitle(pageName);
        progressDialog = new ProgressDialog(this);
        tablesAdapter = new LeagueTablesAdapter();
        recyclerView.setAdapter(tablesAdapter);
        detailsPresenter = new DetailsPresenter();
        detailsPresenter.getLeagueTable(id);

    }

    @Override
    protected void onStart() {
        super.onStart();
        detailsPresenter.registerView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        detailsPresenter.unRegisterView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        MenuItem frwdButton = menu.findItem(R.id.action_forward);
        MenuItemCompat.getActionView(frwdButton);
        return super.onCreateOptionsMenu(menu);
    }

    public void showFixtures(MenuItem item) {
        Intent intent1 = new Intent(DetailsActivity.this, FixturesActivity.class);
        intent1.putExtra(ID_KEY, id);
        startActivity(intent1);

    }

    @Override
    public void showLeagueTable(List<ListItems> teams) {
        tablesAdapter.updateLeagueTable(teams);
    }

    @Override
    public void unsuccesfulResponse() {
        Toast.makeText(this, R.string.table_loading, Toast.LENGTH_SHORT).show();
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
