package com.nbaplayersandroid.beans;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasketballPlayer implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private float id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("height_feet")
    @Expose
    private float heightFeet;
    @SerializedName("height_inches")
    @Expose
    private float heightInches;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("team")
    @Expose
    private Team team;
    @SerializedName("weight_pounds")
    @Expose
    private float weightPounds;
    public final static Parcelable.Creator<BasketballPlayer> CREATOR = new Creator<BasketballPlayer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public BasketballPlayer createFromParcel(Parcel in) {
            return new BasketballPlayer(in);
        }

        public BasketballPlayer[] newArray(int size) {
            return (new BasketballPlayer[size]);
        }

    }
            ;
    private final static long serialVersionUID = -3701246300664134129L;

    protected BasketballPlayer(Parcel in) {
        this.id = ((float) in.readValue((float.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.heightFeet = ((float) in.readValue((float.class.getClassLoader())));
        this.heightInches = ((float) in.readValue((float.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.position = ((String) in.readValue((String.class.getClassLoader())));
        this.team = ((Team) in.readValue((Team.class.getClassLoader())));
        this.weightPounds = ((float) in.readValue((float.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public BasketballPlayer() {
    }

    /**
     *
     * @param firstName
     * @param lastName
     * @param weightPounds
     * @param heightInches
     * @param id
     * @param heightFeet
     * @param position
     * @param team
     */
    public BasketballPlayer(float id, String firstName, float heightFeet, float heightInches, String lastName, String position, Team team, float weightPounds) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.heightFeet = heightFeet;
        this.heightInches = heightInches;
        this.lastName = lastName;
        this.position = position;
        this.team = team;
        this.weightPounds = weightPounds;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public float getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(float heightFeet) {
        this.heightFeet = heightFeet;
    }

    public float getHeightInches() {
        return heightInches;
    }

    public void setHeightInches(float heightInches) {
        this.heightInches = heightInches;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public float getWeightPounds() {
        return weightPounds;
    }

    public void setWeightPounds(float weightPounds) {
        this.weightPounds = weightPounds;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(firstName);
        dest.writeValue(heightFeet);
        dest.writeValue(heightInches);
        dest.writeValue(lastName);
        dest.writeValue(position);
        dest.writeValue(team);
        dest.writeValue(weightPounds);
    }

    public int describeContents() {
        return 0;
    }

}