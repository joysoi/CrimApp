package com.example.nikola.soccerjar;

import android.arch.persistence.room.Room;

import com.example.nikola.soccerjar.database.ListItems;
import com.example.nikola.soccerjar.database.ListItemsDataBase;
import com.example.nikola.soccerjar.retrofit.ApiManager;
import com.example.nikola.soccerjar.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MainPresenter {

    private MainView view;
    private ListItemsDataBase dataBase;

    MainPresenter(MainActivity activity) {
        this.dataBase = Room.databaseBuilder(activity, ListItemsDataBase.class, "SoccerJar").build();
    }

    void getCompetitionList() {
        if (view != null) {
            view.showProgressDialog();
        }


        ApiManager.getClient().create(ApiService.class).getCompetitions().enqueue(new Callback<List<ListItems>>() {
            @Override
            public void onResponse(Call<List<ListItems>> call, Response<List<ListItems>> response) {
                if (response.isSuccessful()) {
                    List<ListItems> competitionListResponse = response.body();
                    final List<ListItems> responseList = new ArrayList<>();
                    for (ListItems competitionResponse : competitionListResponse) {
                        String[] strList = new String[]{"445", "446", "447", "449", "452", "455", "456", "459"};
                        int[] intList = new int[strList.length];
                        for (int i = 0; i < strList.length; i++) {
                            try {
                                intList[i] = Integer.parseInt(strList[i]);
                            } catch (NumberFormatException nfe) {
                                nfe.printStackTrace();
                            }
                        }
                        for (int anIntList : intList) {
                            if (competitionResponse.getId() == anIntList) {
                                responseList.add(competitionResponse);
                                dataBase.listItemsDao().getListItemsById(intList);

                            }
                            if (view != null) {
                                view.showCompetitionsList(responseList);
                                view.dismissProgressDialog();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ListItems>> call, Throwable t) {
                if (view != null) {
                    view.unsuccesfulResponse();
                    view.dismissProgressDialog();
                }
            }
        });

//        ApiManager.getClient().create(ApiService.class).getCompetitions().enqueue(new Callback<List<ListItems>>() {
//            @Override
//            public void onResponse(Call<List<ListItems>> call, Response<List<ListItems>> response) {
//                if(response.isSuccessful()){
//                    List<ListItems> competitionListResponse = response.body();
//                    final List<ListItems> responseList = new ArrayList<>();
//                    for (ListItems competitionResponse : competitionListResponse) {
//                        String[] strList = new String[]{"445", "446", "447", "449", "452", "455", "456", "459"};
//                        int[] intList = new int[strList.length];
//                        for (int i = 0; i < strList.length; i++) {
//                            try {
//                                intList[i] = Integer.parseInt(strList[i]);
//                            } catch (NumberFormatException nfe) {
//                                nfe.printStackTrace();
//                            }
//                        }
//                        for (int anIntList : intList) {
//                            if (competitionResponse.getId() == anIntList) {
//                                responseList.add(competitionResponse);
//                            }
//                            if (view != null) {
//                                view.showCompetitionsList(responseList);
//                                view.dismissProgressDialog();
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ListItems>> call, Throwable t) {
//                if (view != null) {
//                    view.unsuccesfulResponse();
//                    view.dismissProgressDialog();
//                }
//            }
//        });


    }

    void registerView(MainView mainView) {
        this.view = mainView;
    }

    void unRegisterView() {
        this.view = null;
    }

}
