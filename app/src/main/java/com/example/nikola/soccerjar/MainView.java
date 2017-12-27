package com.example.nikola.soccerjar;


import com.example.nikola.soccerjar.database.ListItems;

import java.util.List;

interface MainView {

    void showCompetitionsList(List<ListItems> listCompetitionResponse);

    void unsuccesfulResponse();

    void showProgressDialog();

    void dismissProgressDialog();

}
