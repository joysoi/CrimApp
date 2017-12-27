package com.example.nikola.soccerjar.fragments;

import com.example.nikola.soccerjar.database.ListItems;

import java.util.List;

interface FixturesView {

    void showTeamsWithScheduledStatus(List<ListItems> fixtureList);

    void showAllTeams(List<ListItems> fixtureList);

    void showFIlteredList(List<ListItems> filteredList);

    void unsucessfulResponse();

    void showProgressDialog();

    void dismissProgressDialog();

}
