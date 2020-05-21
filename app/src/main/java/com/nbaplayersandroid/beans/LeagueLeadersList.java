package com.nbaplayersandroid.beans;

import java.util.ArrayList;

public class LeagueLeadersList {
    ArrayList<LeagueLeader> rowSet;

    public LeagueLeadersList() {
    }

    public LeagueLeadersList(ArrayList<LeagueLeader> rowSet) {
        this.rowSet = rowSet;
    }

    public ArrayList<LeagueLeader> getRowSet() {
        return rowSet;
    }

    public void setRowSet(ArrayList<LeagueLeader> rowSet) {
        this.rowSet = rowSet;
    }
}
