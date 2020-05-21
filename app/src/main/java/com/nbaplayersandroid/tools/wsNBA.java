package com.nbaplayersandroid.tools;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nbaplayersandroid.beans.PlayerSeasonStatsList;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface wsNBA {

//    @GET("season_averages")
//    Call<ArrayList<BasketballPlayer>> getPlayerSeasonAverage(
//            @Query("player_ids[]") String param1_players,
//            @Query("season") String param2_season);

//    @GET("season_averages")
//    Call<JSONObject> getPlayerSeasonAverage(
//            @Query("player_ids[]") String param1_players,
//            @Query("season") String param2_season);

    @GET("season_averages")
    Call<PlayerSeasonStatsList> getPlayerSeasonAverage(
            @Query("player_ids[]") String param1_players,
            @Query("season") String param2_season);

    @GET("players/{id}")
    Call<Object> getPlayers(@Path("id") String API_ID);

    @GET("leagueLeaders")
    Call<JsonObject> getLeagueLeaders(
            @Query("LeagueID") String leagueID,
            @Query("PerMode") String perMode,
            @Query("Scope") String scope,
            @Query("Season") String season,
            @Query("SeasonType") String seasonType,
            @Query("StatCategory") String statCategory);


}
