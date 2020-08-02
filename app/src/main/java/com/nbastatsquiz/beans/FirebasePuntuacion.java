package com.nbastatsquiz.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;
import com.google.type.Date;

import java.io.Serializable;
import java.util.Map;

public class FirebasePuntuacion implements Parcelable, Serializable, Comparable<FirebasePuntuacion> {

    String username, season, seasonType, statCategory, perMode, date, image, uid, modoJuego, draftTeam, draftCollege, liga, hour;
    int points, ranking;

    Timestamp timestamp;

    public FirebasePuntuacion(String username, String season, String seasonType, String statCategory, String perMode, String date, String image, String modoJuego, String liga, int points, String uid, int ranking, String draftTeam, String draftCollege) {
        this.username = username;
        this.season = season;
        this.seasonType = seasonType;
        this.statCategory = statCategory;
        this.perMode = perMode;
        this.date = date;
        this.points = points;
        this.image = image;
        this.uid = uid;
        this.ranking = ranking;
        this.modoJuego = modoJuego;
        this.draftTeam = draftTeam;
        this.draftCollege = draftCollege;
        this.liga = liga;
    }

    // Constructor con nueva fecha


    public FirebasePuntuacion(String username, String season, String seasonType, String statCategory, String perMode, String date, String image, String uid, String modoJuego, String draftTeam, String draftCollege, String liga, String hour, int points, int ranking, Timestamp timestamp) {
        this.username = username;
        this.season = season;
        this.seasonType = seasonType;
        this.statCategory = statCategory;
        this.perMode = perMode;
        this.date = date;
        this.image = image;
        this.uid = uid;
        this.modoJuego = modoJuego;
        this.draftTeam = draftTeam;
        this.draftCollege = draftCollege;
        this.liga = liga;
        this.hour = hour;
        this.points = points;
        this.ranking = ranking;
        this.timestamp = timestamp;
    }

    //Constructor con hora
    public FirebasePuntuacion(String username, String season, String seasonType, String statCategory, String perMode, String date, String image, String uid, String modoJuego, String draftTeam, String draftCollege, String liga, String hour, int points, int ranking) {
        this.username = username;
        this.season = season;
        this.seasonType = seasonType;
        this.statCategory = statCategory;
        this.perMode = perMode;
        this.date = date;
        this.image = image;
        this.uid = uid;
        this.modoJuego = modoJuego;
        this.draftTeam = draftTeam;
        this.draftCollege = draftCollege;
        this.liga = liga;
        this.hour = hour;
        this.points = points;
        this.ranking = ranking;
    }

    //constructor para draft
    public FirebasePuntuacion(String username, String season, String date, String image, String uid, String modoJuego, int points, String draftTeam) {
        this.username = username;
        this.season = season;
        this.date = date;
        this.image = image;
        this.uid = uid;
        this.modoJuego = modoJuego;
        this.points = points;
        this.draftTeam = draftTeam;
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
        uid = in.readString();
        ranking = in.readInt();
        modoJuego = in.readString();
        draftTeam = in.readString();
        draftCollege = in.readString();
        liga = in.readString();
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

    //TODO: EL GETTER TIENE QUE SER CASTEADO A LONG


    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public String getDraftCollege() {
        return draftCollege;
    }

    public void setDraftCollege(String draftCollege) {
        this.draftCollege = draftCollege;
    }

    public String getDraftTeam() {
        return draftTeam;
    }

    public void setDraftTeam(String draftTeam) {
        this.draftTeam = draftTeam;
    }

    public String getModoJuego() {
        return modoJuego;
    }

    public void setModoJuego(String modoJuego) {
        this.modoJuego = modoJuego;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
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
        dest.writeString(uid);
        dest.writeString(modoJuego);
        dest.writeString(draftTeam);
        dest.writeString(draftCollege);
        dest.writeString(liga);
        dest.writeInt(ranking);
    }

    @Override
    public int compareTo(FirebasePuntuacion o) {
        return Integer.compare(this.points, o.points);
    }
}
