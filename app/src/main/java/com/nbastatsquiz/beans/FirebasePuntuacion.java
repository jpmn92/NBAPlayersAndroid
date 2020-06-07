package com.nbastatsquiz.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class FirebasePuntuacion implements Parcelable, Serializable, Comparable<FirebasePuntuacion> {

    String username, season, seasonType, statCategory, perMode, date, image;
    int points;

    public FirebasePuntuacion(String username, String season, String seasonType, String statCategory, String perMode, String date, String image, int points) {
        this.username = username;
        this.season = season;
        this.seasonType = seasonType;
        this.statCategory = statCategory;
        this.perMode = perMode;
        this.date = date;
        this.points = points;
        this.image = image;
    }

    public FirebasePuntuacion() {
    }

    protected FirebasePuntuacion(Parcel in) {
        username = in.readString();
        season = in.readString();
        seasonType = in.readString();
        statCategory = in.readString();
        perMode = in.readString();
        date = in.readString();
        points = in.readInt();
        image = in.readString();
    }

    public static final Creator<FirebasePuntuacion> CREATOR = new Creator<FirebasePuntuacion>() {
        @Override
        public FirebasePuntuacion createFromParcel(Parcel in) {
            return new FirebasePuntuacion(in);
        }

        @Override
        public FirebasePuntuacion[] newArray(int size) {
            return new FirebasePuntuacion[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSeasonType() {
        return seasonType;
    }

    public void setSeasonType(String seasonType) {
        this.seasonType = seasonType;
    }

    public String getStatCategory() {
        return statCategory;
    }

    public void setStatCategory(String statCategory) {
        this.statCategory = statCategory;
    }

    public String getPerMode() {
        return perMode;
    }

    public void setPerMode(String perMode) {
        this.perMode = perMode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(season);
        dest.writeString(seasonType);
        dest.writeString(statCategory);
        dest.writeString(perMode);
        dest.writeString(date);
        dest.writeInt(points);
        dest.writeString(image);
    }

    @Override
    public int compareTo(FirebasePuntuacion o) {
        return Integer.compare(this.points, o.points);
    }
}
