package com.nbaplayersandroid.tools;

import com.nbaplayersandroid.beans.BasketballPlayer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface wsNBA {

    @GET("season_averages")
    Call<ArrayList<BasketballPlayer>> getPlayerSeasonAverage(
            @Query("player_ids[]") String param1_players,
            @Query("season") String param2_season);

    @GET("players")
    Call<ArrayList<BasketballPlayer>> getAllPlayers();



}
