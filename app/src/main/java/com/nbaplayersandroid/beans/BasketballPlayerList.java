package com.nbaplayersandroid.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BasketballPlayerList {

    @SerializedName("data")
    private ArrayList<BasketballPlayer> basketballPlayers;

    public ArrayList<BasketballPlayer> getBasketballPlayers() {
        return basketballPlayers;
    }

    public void setBasketballPlayers(ArrayList<BasketballPlayer> basketballPlayers) {
        this.basketballPlayers = basketballPlayers;
    }
}
