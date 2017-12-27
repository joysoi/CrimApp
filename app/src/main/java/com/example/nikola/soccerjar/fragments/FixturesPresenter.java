package com.example.nikola.soccerjar.fragments;

import com.example.nikola.soccerjar.database.ListItems;
import com.example.nikola.soccerjar.retrofit.ApiManager;
import com.example.nikola.soccerjar.retrofit.ApiService;
import com.example.nikola.soccerjar.retrofit.models.FixturesResponse;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class FixturesPresenter {

    private FixturesView view;
    private List<ListItems> teamList = new ArrayList<>();
    private static final String SCHEDULED_GAMES = "SCHEDULED";

    void getTeamNames(int id) {

        if (view != null) {
            view.showProgressDialog();
        }

        ApiManager.getClient().create(ApiService.class).getFixtures(id).enqueue(new Callback<FixturesResponse>() {
            @Override
            public void onResponse(Call<FixturesResponse> call, Response<FixturesResponse> response) {
                if (response.isSuccessful()) {
                    FixturesResponse fixturesResponse = response.body();
                    teamList = fixturesResponse.getFixtures();
                    List<ListItems> gameStatusList = new ArrayList<>();
                    for (ListItems team : teamList) {
                        if (team.getStatus().equals(SCHEDULED_GAMES)) {
                            gameStatusList.add(team);
                        }
                    }
                    if (view != null) {
                        view.showTeamsWithScheduledStatus(gameStatusList);
                        view.dismissProgressDialog();
                    }
                } else {
                    if (view != null) {
                        view.unsucessfulResponse();
                        view.dismissProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<FixturesResponse> call, Throwable t) {
                if (view != null) {
                    view.unsucessfulResponse();
                    view.dismissProgressDialog();
                }
            }
        });
    }

    void registerView(FixturesView fixturesView) {
        this.view = fixturesView;
    }

    void unRegisterView() {
        this.view = null;
    }

    void getPreviousGames() {
        if (view != null) {
            view.showAllTeams(teamList);
        }
    }

    void getFilteredList(String s) {
        if (view != null) {
            String searchText = s.toLowerCase();
            ArrayList<ListItems> newFilteredList = new ArrayList<>();
            for (ListItems team : teamList) {
                if (team.getHomeTeamName().toLowerCase().contains(searchText) || team.getAwayTeamName().toLowerCase().contains(searchText)) {
                    newFilteredList.add(team);
                }
            }
            if (newFilteredList.size() != 0) {
                if (view != null) {
                    view.showFIlteredList(newFilteredList);
                }
            }
        }
    }
}
