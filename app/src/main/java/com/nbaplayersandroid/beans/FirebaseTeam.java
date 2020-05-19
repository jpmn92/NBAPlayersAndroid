package com.nbaplayersandroid.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FirebaseTeam implements Serializable, Parcelable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("urlImage")
    @Expose
    private String urlImage;
    @SerializedName("urlBackground")
    @Expose
    private String urlBackground;
    @SerializedName("idAPI")
    @Expose
    private int idAPI;
    @SerializedName("idTeam")
    @Expose
    private int idTeam;
    public final static Parcelable.Creator<FirebaseTeam> CREATOR = new Parcelable.Creator<FirebaseTeam>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FirebaseTeam createFromParcel(Parcel in) {
            return new FirebaseTeam(in);
        }

        public FirebaseTeam[] newArray(int size) {
            return (new FirebaseTeam[size]);
        }

    };
    private final static long serialVersionUID = 8349869639957837125L;

    protected FirebaseTeam(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.urlImage = ((String) in.readValue((String.class.getClassLoader())));
        this.urlBackground = ((String) in.readValue((String.class.getClassLoader())));
        this.idAPI = ((int) in.readValue((int.class.getClassLoader())));
        this.idTeam = ((int) in.readValue((int.class.getClassLoader())));
    }

    public FirebaseTeam() {
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

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getIdAPI() {
        return idAPI;
    }

    public void setIdAPI(int idAPI) {
        this.idAPI = idAPI;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public String getUrlBackground() {
        return urlBackground;
    }

    public void setUrlBackground(String urlBackground) {
        this.urlBackground = urlBackground;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(urlImage);
        dest.writeValue(urlBackground);
        dest.writeValue(idAPI);
        dest.writeValue(idTeam);
    }

    public int describeContents() {
        return 0;
    }

}
