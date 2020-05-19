
package com.nbaplayersandroid.beans;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirebasePlayer implements Serializable, Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("urlImage")
    @Expose
    private String urlImage;
    @SerializedName("idAPI")
    @Expose
    private int idAPI;
    @SerializedName("idPlayer")
    @Expose
    private int idPlayer;
    public final static Parcelable.Creator<FirebasePlayer> CREATOR = new Creator<FirebasePlayer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FirebasePlayer createFromParcel(Parcel in) {
            return new FirebasePlayer(in);
        }

        public FirebasePlayer[] newArray(int size) {
            return (new FirebasePlayer[size]);
        }

    };
    private final static long serialVersionUID = 8349869639957837125L;

    protected FirebasePlayer(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.urlImage = ((String) in.readValue((String.class.getClassLoader())));
        this.idAPI = ((int) in.readValue((int.class.getClassLoader())));
        this.idPlayer = ((int) in.readValue((int.class.getClassLoader())));
    }

    public FirebasePlayer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(lastName);
        dest.writeValue(urlImage);
        dest.writeValue(idAPI);
        dest.writeValue(idPlayer);
    }

    public int describeContents() {
        return 0;
    }

}