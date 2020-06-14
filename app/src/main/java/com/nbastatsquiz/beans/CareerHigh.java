package com.nbastatsquiz.beans;

public class CareerHigh {

    int PLAYER_ID, VS_TEAM_ID, STAT_VALUE, STAT_ORDER;
    String GAME_ID, GAME_DATE, VS_TEAM_CITY, VS_TEAM_ABBREVIATION, STAT, DATE_EST;

    public CareerHigh(int PLAYER_ID, int VS_TEAM_ID, int STAT_VALUE, int STAT_ORDER, String GAME_ID, String GAME_DATE, String VS_TEAM_CITY, String VS_TEAM_ABBREVIATION, String STAT, String DATE_EST) {
        this.PLAYER_ID = PLAYER_ID;
        this.VS_TEAM_ID = VS_TEAM_ID;
        this.STAT_VALUE = STAT_VALUE;
        this.STAT_ORDER = STAT_ORDER;
        this.GAME_ID = GAME_ID;
        this.GAME_DATE = GAME_DATE;
        this.VS_TEAM_CITY = VS_TEAM_CITY;
        this.VS_TEAM_ABBREVIATION = VS_TEAM_ABBREVIATION;
        this.STAT = STAT;
        this.DATE_EST = DATE_EST;
    }

    public CareerHigh() {
    }

    public int getPLAYER_ID() {
        return PLAYER_ID;
    }

    public void setPLAYER_ID(int PLAYER_ID) {
        this.PLAYER_ID = PLAYER_ID;
    }

    public int getVS_TEAM_ID() {
        return VS_TEAM_ID;
    }

    public void setVS_TEAM_ID(int VS_TEAM_ID) {
        this.VS_TEAM_ID = VS_TEAM_ID;
    }

    public int getSTAT_VALUE() {
        return STAT_VALUE;
    }

    public void setSTAT_VALUE(int STAT_VALUE) {
        this.STAT_VALUE = STAT_VALUE;
    }

    public int getSTAT_ORDER() {
        return STAT_ORDER;
    }

    public void setSTAT_ORDER(int STAT_ORDER) {
        this.STAT_ORDER = STAT_ORDER;
    }

    public String getGAME_ID() {
        return GAME_ID;
    }

    public void setGAME_ID(String GAME_ID) {
        this.GAME_ID = GAME_ID;
    }

    public String getGAME_DATE() {
        return GAME_DATE;
    }

    public void setGAME_DATE(String GAME_DATE) {
        this.GAME_DATE = GAME_DATE;
    }

    public String getVS_TEAM_CITY() {
        return VS_TEAM_CITY;
    }

    public void setVS_TEAM_CITY(String VS_TEAM_CITY) {
        this.VS_TEAM_CITY = VS_TEAM_CITY;
    }

    public String getVS_TEAM_ABBREVIATION() {
        return VS_TEAM_ABBREVIATION;
    }

    public void setVS_TEAM_ABBREVIATION(String VS_TEAM_ABBREVIATION) {
        this.VS_TEAM_ABBREVIATION = VS_TEAM_ABBREVIATION;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }

    public String getDATE_EST() {
        return DATE_EST;
    }

    public void setDATE_EST(String DATE_EST) {
        this.DATE_EST = DATE_EST;
    }
}
