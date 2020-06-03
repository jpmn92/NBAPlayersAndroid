package com.nbastatsquiz.beans;

import java.io.Serializable;

public class LeagueLeader implements Serializable {

    Double PLAYER_ID, RANK;
    String PLAYER, TEAM, PLAYER_NAME;
    Double GP, MIN, FGM, FGA, FG_PCT, FG3M, FG3A, FG3_PCT, FTM, FTA, FT_PCT, OREB, DREB, REB, AST, STL, BLK, TOV, PTS, EFF;

    public LeagueLeader(Double PLAYER_ID, Double RANK, String PLAYER, String TEAM, Double GP, Double MIN, Double FGM, Double FGA, Double FG_PCT, Double FG3M, Double FG3A, Double FG3_PCT, Double FTM, Double FTA, Double FT_PCT, Double OREB, Double DREB, Double REB, Double AST, Double STL, Double BLK, Double TOV, Double PTS, Double EFF, String PLAYER_NAME) {
        this.PLAYER_ID = PLAYER_ID;
        this.RANK = RANK;
        this.PLAYER = PLAYER;
        this.TEAM = TEAM;
        this.GP = GP;
        this.MIN = MIN;
        this.FGM = FGM;
        this.FGA = FGA;
        this.FG_PCT = FG_PCT;
        this.FG3M = FG3M;
        this.FG3A = FG3A;
        this.FG3_PCT = FG3_PCT;
        this.FTM = FTM;
        this.FTA = FTA;
        this.FT_PCT = FT_PCT;
        this.OREB = OREB;
        this.DREB = DREB;
        this.REB = REB;
        this.AST = AST;
        this.STL = STL;
        this.BLK = BLK;
        this.TOV = TOV;
        this.PTS = PTS;
        this.EFF = EFF;
        this.PLAYER_NAME = PLAYER_NAME;
    }

    public LeagueLeader() {
    }

    public String getPLAYER_NAME() {
        return PLAYER_NAME;
    }

    public void setPLAYER_NAME(String PLAYER_NAME) {
        this.PLAYER_NAME = PLAYER_NAME;
    }

    public Double getPLAYER_ID() {
        return PLAYER_ID;
    }

    public void setPLAYER_ID(Double PLAYER_ID) {
        this.PLAYER_ID = PLAYER_ID;
    }

    public Double getRANK() {
        return RANK;
    }

    public void setRANK(Double RANK) {
        this.RANK = RANK;
    }

    public String getPLAYER() {
        return PLAYER;
    }

    public void setPLAYER(String PLAYER) {
        this.PLAYER = PLAYER;
    }

    public String getTEAM() {
        return TEAM;
    }

    public void setTEAM(String TEAM) {
        this.TEAM = TEAM;
    }

    public Double getGP() {
        return GP;
    }

    public void setGP(Double GP) {
        this.GP = GP;
    }

    public Double getMIN() {
        return MIN;
    }

    public void setMIN(Double MIN) {
        this.MIN = MIN;
    }

    public Double getFGM() {
        return FGM;
    }

    public void setFGM(Double FGM) {
        this.FGM = FGM;
    }

    public Double getFGA() {
        return FGA;
    }

    public void setFGA(Double FGA) {
        this.FGA = FGA;
    }

    public Double getFG_PCT() {
        return FG_PCT;
    }

    public void setFG_PCT(Double FG_PCT) {
        this.FG_PCT = FG_PCT;
    }

    public Double getFG3M() {
        return FG3M;
    }

    public void setFG3M(Double FG3M) {
        this.FG3M = FG3M;
    }

    public Double getFG3A() {
        return FG3A;
    }

    public void setFG3A(Double FG3A) {
        this.FG3A = FG3A;
    }

    public Double getFG3_PCT() {
        return FG3_PCT;
    }

    public void setFG3_PCT(Double FG3_PCT) {
        this.FG3_PCT = FG3_PCT;
    }

    public Double getFTM() {
        return FTM;
    }

    public void setFTM(Double FTM) {
        this.FTM = FTM;
    }

    public Double getFTA() {
        return FTA;
    }

    public void setFTA(Double FTA) {
        this.FTA = FTA;
    }

    public Double getFT_PCT() {
        return FT_PCT;
    }

    public void setFT_PCT(Double FT_PCT) {
        this.FT_PCT = FT_PCT;
    }

    public Double getOREB() {
        return OREB;
    }

    public void setOREB(Double OREB) {
        this.OREB = OREB;
    }

    public Double getDREB() {
        return DREB;
    }

    public void setDREB(Double DREB) {
        this.DREB = DREB;
    }

    public Double getREB() {
        return REB;
    }

    public void setREB(Double REB) {
        this.REB = REB;
    }

    public Double getAST() {
        return AST;
    }

    public void setAST(Double AST) {
        this.AST = AST;
    }

    public Double getSTL() {
        return STL;
    }

    public void setSTL(Double STL) {
        this.STL = STL;
    }

    public Double getBLK() {
        return BLK;
    }

    public void setBLK(Double BLK) {
        this.BLK = BLK;
    }

    public Double getTOV() {
        return TOV;
    }

    public void setTOV(Double TOV) {
        this.TOV = TOV;
    }

    public Double getPTS() {
        return PTS;
    }

    public void setPTS(Double PTS) {
        this.PTS = PTS;
    }

    public Double getEFF() {
        return EFF;
    }

    public void setEFF(Double EFF) {
        this.EFF = EFF;
    }
}
