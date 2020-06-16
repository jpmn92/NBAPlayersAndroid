package com.nbastatsquiz.beans;

import java.io.Serializable;

public class DraftPick implements Serializable {

    int PERSON_ID, ROUND_NUMBER, ROUND_PICK, OVERALL_PICK, TEAM_ID;
    String PLAYER_NAME, SEASON, DRAFT_TYPE, TEAM_CITY, TEAM_NAME, TEAM_ABBREVIATION, ORGANIZATION, ORGANIZATION_TYPE;


    public DraftPick(int PERSON_ID, int ROUND_NUMBER, int ROUND_PICK, int OVERALL_PICK, int TEAM_ID, String PLAYER_NAME, String SEASON, String DRAFT_TYPE, String TEAM_CITY, String TEAM_NAME, String TEAM_ABBREVIATION, String ORGANIZATION, String ORGANIZATION_TYPE) {
        this.PERSON_ID = PERSON_ID;
        this.ROUND_NUMBER = ROUND_NUMBER;
        this.ROUND_PICK = ROUND_PICK;
        this.OVERALL_PICK = OVERALL_PICK;
        this.TEAM_ID = TEAM_ID;
        this.PLAYER_NAME = PLAYER_NAME;
        this.SEASON = SEASON;
        this.DRAFT_TYPE = DRAFT_TYPE;
        this.TEAM_CITY = TEAM_CITY;
        this.TEAM_NAME = TEAM_NAME;
        this.TEAM_ABBREVIATION = TEAM_ABBREVIATION;
        this.ORGANIZATION = ORGANIZATION;
        this.ORGANIZATION_TYPE = ORGANIZATION_TYPE;
    }

    public DraftPick() {
    }

    public int getPERSON_ID() {
        return PERSON_ID;
    }

    public void setPERSON_ID(int PERSON_ID) {
        this.PERSON_ID = PERSON_ID;
    }

    public int getROUND_NUMBER() {
        return ROUND_NUMBER;
    }

    public void setROUND_NUMBER(int ROUND_NUMBER) {
        this.ROUND_NUMBER = ROUND_NUMBER;
    }

    public int getROUND_PICK() {
        return ROUND_PICK;
    }

    public void setROUND_PICK(int ROUND_PICK) {
        this.ROUND_PICK = ROUND_PICK;
    }

    public int getOVERALL_PICK() {
        return OVERALL_PICK;
    }

    public void setOVERALL_PICK(int OVERALL_PICK) {
        this.OVERALL_PICK = OVERALL_PICK;
    }

    public int getTEAM_ID() {
        return TEAM_ID;
    }

    public void setTEAM_ID(int TEAM_ID) {
        this.TEAM_ID = TEAM_ID;
    }

    public String getPLAYER_NAME() {
        return PLAYER_NAME;
    }

    public void setPLAYER_NAME(String PLAYER_NAME) {
        this.PLAYER_NAME = PLAYER_NAME;
    }

    public String getSEASON() {
        return SEASON;
    }

    public void setSEASON(String SEASON) {
        this.SEASON = SEASON;
    }

    public String getDRAFT_TYPE() {
        return DRAFT_TYPE;
    }

    public void setDRAFT_TYPE(String DRAFT_TYPE) {
        this.DRAFT_TYPE = DRAFT_TYPE;
    }

    public String getTEAM_CITY() {
        return TEAM_CITY;
    }

    public void setTEAM_CITY(String TEAM_CITY) {
        this.TEAM_CITY = TEAM_CITY;
    }

    public String getTEAM_NAME() {
        return TEAM_NAME;
    }

    public void setTEAM_NAME(String TEAM_NAME) {
        this.TEAM_NAME = TEAM_NAME;
    }

    public String getTEAM_ABBREVIATION() {
        return TEAM_ABBREVIATION;
    }

    public void setTEAM_ABBREVIATION(String TEAM_ABBREVIATION) {
        this.TEAM_ABBREVIATION = TEAM_ABBREVIATION;
    }

    public String getORGANIZATION() {
        return ORGANIZATION;
    }

    public void setORGANIZATION(String ORGANIZATION) {
        this.ORGANIZATION = ORGANIZATION;
    }

    public String getORGANIZATION_TYPE() {
        return ORGANIZATION_TYPE;
    }

    public void setORGANIZATION_TYPE(String ORGANIZATION_TYPE) {
        this.ORGANIZATION_TYPE = ORGANIZATION_TYPE;
    }
}
