package com.example.nikola.soccerjar;


import com.example.nikola.soccerjar.database.ListItems;

import java.util.List;

interface DetailsView {

    void showLeagueTable(List<ListItems> teams);

    void unsuccesfulResponse();

    void showProgressDialog();

    void dismissProgressDialog();

}
