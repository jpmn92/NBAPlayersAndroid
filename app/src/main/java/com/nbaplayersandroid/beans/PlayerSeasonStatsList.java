package com.nbaplayersandroid.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlayerSeasonStatsList {

    @SerializedName("data")
    private ArrayList<PlayerSeasonStats> playerSeasonStats;

    public ArrayList<PlayerSeasonStats> getPlayerSeasonStats() {
        return playerSeasonStats;
    }

    public void setPlayerSeasonStats(ArrayList<PlayerSeasonStats> playerSeasonStats) {
        this.playerSeasonStats = playerSeasonStats;
    }
}
