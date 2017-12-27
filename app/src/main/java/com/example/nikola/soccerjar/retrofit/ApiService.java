package com.example.nikola.soccerjar.retrofit;

import com.example.nikola.soccerjar.database.ListItems;
import com.example.nikola.soccerjar.retrofit.models.FixturesResponse;
import com.example.nikola.soccerjar.retrofit.models.LeagueResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface ApiService {

    @Headers("X-Auth-Token: 29c98aeca58b4f52a9b3ffb122ca9c31")
    @GET("/v1/competitions/")
    Call<List<ListItems>> getCompetitions();

    @Headers("X-Auth-Token: 29c98aeca58b4f52a9b3ffb122ca9c31")
    @GET("/v1/competitions/{id}/leagueTable")
    Call<LeagueResponse> getLeague(@Path("id") int id);

    @Headers("X-Auth-Token: 29c98aeca58b4f52a9b3ffb122ca9c31")
    @GET("/v1/competitions/{id}/fixtures")
    Call<FixturesResponse> getFixtures(@Path("id") int id);

}
