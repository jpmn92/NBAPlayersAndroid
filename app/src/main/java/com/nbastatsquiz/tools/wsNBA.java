package com.nbastatsquiz.tools;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface wsNBA {


//    @Headers({
//            "Referer: https://stats.nba.com/draft/history/",
//            "Accept: */*",
//            "Accept-Encoding: gzip, deflate, br",
//            "Connection: keep-alive",
//            "Cache-Control: no-cache",
//            "Strict-Transport-Security: max-age=86400",
//            "content-type: application/json; charset=utf-8",
//            "x-nba-stats-origin: stats"
//    })

    @GET("drafthistory")
    Call<JsonObject> getDraft(
            @Query("College") String college,
            @Query("LeagueID") String leagueID,
            @Query("OverallPick") String overallPick,
            @Query("RoundNum") String roundNum,
            @Query("RoundPick") String roundPick,
            @Query("Season") String season,
            @Query("TeamID") String teamID,
            @Query("TopX") String topX);


    @GET("leagueLeaders")
    Call<JsonObject> getLeagueLeaders(
            @Query("LeagueID") String leagueID,
            @Query("PerMode") String perMode,
            @Query("Scope") String scope,
            @Query("Season") String season,
            @Query("SeasonType") String seasonType,
            @Query("StatCategory") String statCategory,
            @Query("ActiveFlag") String activeFlag);

    @GET("playerprofilev2")
    Call<JsonObject> getCareerHighs(
            @Query("LeagueID") String leagueID,
            @Query("PerMode") String perMode,
            @Query("PlayerID") String playerID);


}
