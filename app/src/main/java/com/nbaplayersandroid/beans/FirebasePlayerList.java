package com.nbaplayersandroid.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FirebasePlayerList {
    @SerializedName("data")
    private ArrayList<FirebasePlayer> firebasePlayers;

    public ArrayList<FirebasePlayer> getFirebasePlayers() {
        return firebasePlayers;
    }

    public void setFirebasePlayers(ArrayList<FirebasePlayer> firebasePlayers) {
        this.firebasePlayers = firebasePlayers;
    }

}
