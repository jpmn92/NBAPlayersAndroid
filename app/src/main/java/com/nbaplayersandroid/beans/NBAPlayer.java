package com.nbaplayersandroid.beans;

import androidx.annotation.NonNull;

public class NBAPlayer {

    int id;
    String name, urlImage;

    public NBAPlayer(int id, String name, String urlImage) {
        this.id = id;
        this.name = name;
        this.urlImage = urlImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUrlImage() {
        return urlImage;
    }


    @NonNull
    @Override
    public String toString() {
        return this.getName();
    }


}
