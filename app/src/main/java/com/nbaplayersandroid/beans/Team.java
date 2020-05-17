package com.nbaplayersandroid.beans;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("abbreviation")
    @Expose
    private String abbreviation;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("conference")
    @Expose
    private String conference;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Parcelable.Creator<Team> CREATOR = new Creator<Team>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        public Team[] newArray(int size) {
            return (new Team[size]);
        }

    }
            ;
    private final static long serialVersionUID = -4120927843212369857L;

    protected Team(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.abbreviation = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.conference = ((String) in.readValue((String.class.getClassLoader())));
        this.division = ((String) in.readValue((String.class.getClassLoader())));
        this.fullName = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Team() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(abbreviation);
        dest.writeValue(city);
        dest.writeValue(conference);
        dest.writeValue(division);
        dest.writeValue(fullName);
        dest.writeValue(name);
    }

    public int describeContents() {
        return 0;
    }

}