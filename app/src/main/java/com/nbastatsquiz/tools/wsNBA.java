package com.nbastatsquiz.tools;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface wsNBA {



    @GET("leagueLeaders")
    Call<JsonObject> getLeagueLeaders(
            @Query("LeagueID") String leagueID,
            @Query("PerMode") String perMode,
            @Query("Scope") String scope,
            @Query("Season") String season,
            @Query("SeasonType") String seasonType,
            @Query("StatCategory") String statCategory,
            @Query("ActiveFlag") String activeFlag);


}
