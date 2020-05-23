package com.nbaplayersandroid.beans;

public class FirebasePuntuacion {

    String username, season, seasonType, statCategory, perMode, date;
    int points;

    public FirebasePuntuacion(String username, String season, String seasonType, String statCategory, String perMode, String date, int points) {
        this.username = username;
        this.season = season;
        this.seasonType = seasonType;
        this.statCategory = statCategory;
        this.perMode = perMode;
        this.date = date;
        this.points = points;
    }

    public FirebasePuntuacion() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSeasonType() {
        return seasonType;
    }

    public void setSeasonType(String seasonType) {
        this.seasonType = seasonType;
    }

    public String getStatCategory() {
        return statCategory;
    }

    public void setStatCategory(String statCategory) {
        this.statCategory = statCategory;
    }

    public String getPerMode() {
        return perMode;
    }

    public void setPerMode(String perMode) {
        this.perMode = perMode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
