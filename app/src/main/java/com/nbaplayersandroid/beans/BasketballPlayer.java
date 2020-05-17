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
    private int id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("height_feet")
    @Expose
    private Object heightFeet;
    @SerializedName("height_inches")
    @Expose
    private Object heightInches;
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
    private Object weightPounds;
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
    private final static long serialVersionUID = 8161452363399736315L;

    protected BasketballPlayer(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.heightFeet = ((Object) in.readValue((Object.class.getClassLoader())));
        this.heightInches = ((Object) in.readValue((Object.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.position = ((String) in.readValue((String.class.getClassLoader())));
        this.team = ((Team) in.readValue((Team.class.getClassLoader())));
        this.weightPounds = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public BasketballPlayer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(Object heightFeet) {
        this.heightFeet = heightFeet;
    }

    public Object getHeightInches() {
        return heightInches;
    }

    public void setHeightInches(Object heightInches) {
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

    public Object getWeightPounds() {
        return weightPounds;
    }

    public void setWeightPounds(Object weightPounds) {
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