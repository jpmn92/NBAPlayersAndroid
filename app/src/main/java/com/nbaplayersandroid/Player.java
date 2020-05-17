package com.nbaplayersandroid;

import android.widget.ImageView;

/**
 * Created by jp on 17/05/2020.
 */

public class Player {
    int id;
    String lastName;
    String firstName;
    float contract;
    int image;

    public Player(int id, String lastName, String firstName, float contract, int image) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.contract = contract;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public float getContract() {
        return contract;
    }

    public void setContract(float contract) {
        this.contract = contract;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
