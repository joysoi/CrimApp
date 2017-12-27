package com.example.nikola.soccerjar.retrofit.models;

import com.example.nikola.soccerjar.database.ListItems;

import java.util.List;

public class FixturesResponse {

    private List<ListItems> fixtures;

    public FixturesResponse(List<ListItems> fixtures) {
        this.fixtures = fixtures;
    }

    public List<ListItems> getFixtures() {
        return fixtures;
    }

}

