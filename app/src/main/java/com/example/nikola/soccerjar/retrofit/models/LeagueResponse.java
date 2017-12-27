package com.example.nikola.soccerjar.retrofit.models;

import com.example.nikola.soccerjar.database.ListItems;

import java.util.List;


public class LeagueResponse {

    private final List<ListItems> standing;

    public LeagueResponse(List<ListItems> standing) {
        this.standing = standing;
    }
    public List<ListItems> getStanding() {
        return standing;
    }

}
