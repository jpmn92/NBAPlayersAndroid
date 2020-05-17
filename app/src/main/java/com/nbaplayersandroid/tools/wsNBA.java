package com.nbaplayersandroid.tools;

import com.nbaplayersandroid.beans.PlayerSeasonStats;
import com.nbaplayersandroid.beans.BasketballPlayerList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
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
    Call<BasketballPlayerList> getPlayerSeasonAverage(
            @Query("player_ids[]") String param1_players,
            @Query("season") String param2_season);

    @GET("players")
    Call<ArrayList<PlayerSeasonStats>> getAllPlayers();



}
