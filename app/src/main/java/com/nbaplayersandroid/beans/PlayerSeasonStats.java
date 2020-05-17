        package com.nbaplayersandroid.beans;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerSeasonStats implements Serializable, Parcelable
{

    @SerializedName("games_played")
    @Expose
    private int gamesPlayed;
    @SerializedName("player_id")
    @Expose
    private int playerId;
    @SerializedName("season")
    @Expose
    private int season;
    @SerializedName("min")
    @Expose
    private String min;
    @SerializedName("fgm")
    @Expose
    private float fgm;
    @SerializedName("fga")
    @Expose
    private float fga;
    @SerializedName("fg3m")
    @Expose
    private float fg3m;
    @SerializedName("fg3a")
    @Expose
    private float fg3a;
    @SerializedName("ftm")
    @Expose
    private float ftm;
    @SerializedName("fta")
    @Expose
    private float fta;
    @SerializedName("oreb")
    @Expose
    private float oreb;
    @SerializedName("dreb")
    @Expose
    private float dreb;
    @SerializedName("reb")
    @Expose
    private float reb;
    @SerializedName("ast")
    @Expose
    private float ast;
    @SerializedName("stl")
    @Expose
    private float stl;
    @SerializedName("blk")
    @Expose
    private float blk;
    @SerializedName("turnover")
    @Expose
    private float turnover;
    @SerializedName("pf")
    @Expose
    private float pf;
    @SerializedName("pts")
    @Expose
    private float pts;
    @SerializedName("fg_pct")
    @Expose
    private float fgPct;
    @SerializedName("fg3_pct")
    @Expose
    private float fg3Pct;
    @SerializedName("ft_pct")
    @Expose
    private float ftPct;
    public final static Parcelable.Creator<PlayerSeasonStats> CREATOR = new Creator<PlayerSeasonStats>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PlayerSeasonStats createFromParcel(Parcel in) {
            return new PlayerSeasonStats(in);
        }

        public PlayerSeasonStats[] newArray(int size) {
            return (new PlayerSeasonStats[size]);
        }

    }
            ;
    private final static long serialVersionUID = -2625881109355125990L;

    protected PlayerSeasonStats(Parcel in) {
        this.gamesPlayed = ((int) in.readValue((int.class.getClassLoader())));
        this.playerId = ((int) in.readValue((int.class.getClassLoader())));
        this.season = ((int) in.readValue((int.class.getClassLoader())));
        this.min = ((String) in.readValue((String.class.getClassLoader())));
        this.fgm = ((float) in.readValue((float.class.getClassLoader())));
        this.fga = ((float) in.readValue((float.class.getClassLoader())));
        this.fg3m = ((float) in.readValue((float.class.getClassLoader())));
        this.fg3a = ((float) in.readValue((float.class.getClassLoader())));
        this.ftm = ((float) in.readValue((float.class.getClassLoader())));
        this.fta = ((float) in.readValue((float.class.getClassLoader())));
        this.oreb = ((float) in.readValue((float.class.getClassLoader())));
        this.dreb = ((float) in.readValue((float.class.getClassLoader())));
        this.reb = ((float) in.readValue((float.class.getClassLoader())));
        this.ast = ((float) in.readValue((float.class.getClassLoader())));
        this.stl = ((float) in.readValue((float.class.getClassLoader())));
        this.blk = ((float) in.readValue((float.class.getClassLoader())));
        this.turnover = ((float) in.readValue((float.class.getClassLoader())));
        this.pf = ((float) in.readValue((float.class.getClassLoader())));
        this.pts = ((float) in.readValue((float.class.getClassLoader())));
        this.fgPct = ((float) in.readValue((float.class.getClassLoader())));
        this.fg3Pct = ((float) in.readValue((float.class.getClassLoader())));
        this.ftPct = ((float) in.readValue((float.class.getClassLoader())));
    }

    public PlayerSeasonStats() {
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public float getFgm() {
        return fgm;
    }

    public void setFgm(float fgm) {
        this.fgm = fgm;
    }

    public float getFga() {
        return fga;
    }

    public void setFga(float fga) {
        this.fga = fga;
    }

    public float getFg3m() {
        return fg3m;
    }

    public void setFg3m(float fg3m) {
        this.fg3m = fg3m;
    }

    public float getFg3a() {
        return fg3a;
    }

    public void setFg3a(float fg3a) {
        this.fg3a = fg3a;
    }

    public float getFtm() {
        return ftm;
    }

    public void setFtm(float ftm) {
        this.ftm = ftm;
    }

    public float getFta() {
        return fta;
    }

    public void setFta(float fta) {
        this.fta = fta;
    }

    public float getOreb() {
        return oreb;
    }

    public void setOreb(float oreb) {
        this.oreb = oreb;
    }

    public float getDreb() {
        return dreb;
    }

    public void setDreb(float dreb) {
        this.dreb = dreb;
    }

    public float getReb() {
        return reb;
    }

    public void setReb(float reb) {
        this.reb = reb;
    }

    public float getAst() {
        return ast;
    }

    public void setAst(float ast) {
        this.ast = ast;
    }

    public float getStl() {
        return stl;
    }

    public void setStl(float stl) {
        this.stl = stl;
    }

    public float getBlk() {
        return blk;
    }

    public void setBlk(float blk) {
        this.blk = blk;
    }

    public float getTurnover() {
        return turnover;
    }

    public void setTurnover(float turnover) {
        this.turnover = turnover;
    }

    public float getPf() {
        return pf;
    }

    public void setPf(float pf) {
        this.pf = pf;
    }

    public float getPts() {
        return pts;
    }

    public void setPts(float pts) {
        this.pts = pts;
    }

    public float getFgPct() {
        return fgPct;
    }

    public void setFgPct(float fgPct) {
        this.fgPct = fgPct;
    }

    public float getFg3Pct() {
        return fg3Pct;
    }

    public void setFg3Pct(float fg3Pct) {
        this.fg3Pct = fg3Pct;
    }

    public float getFtPct() {
        return ftPct;
    }

    public void setFtPct(float ftPct) {
        this.ftPct = ftPct;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(gamesPlayed);
        dest.writeValue(playerId);
        dest.writeValue(season);
        dest.writeValue(min);
        dest.writeValue(fgm);
        dest.writeValue(fga);
        dest.writeValue(fg3m);
        dest.writeValue(fg3a);
        dest.writeValue(ftm);
        dest.writeValue(fta);
        dest.writeValue(oreb);
        dest.writeValue(dreb);
        dest.writeValue(reb);
        dest.writeValue(ast);
        dest.writeValue(stl);
        dest.writeValue(blk);
        dest.writeValue(turnover);
        dest.writeValue(pf);
        dest.writeValue(pts);
        dest.writeValue(fgPct);
        dest.writeValue(fg3Pct);
        dest.writeValue(ftPct);
    }

    public int describeContents() {
        return 0;
    }

}