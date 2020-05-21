package com.nbaplayersandroid.lst_league_leaders;

import android.os.Bundle;

import com.nbaplayersandroid.beans.LeagueLeader;
import com.nbaplayersandroid.beans.PlayerSeasonStatsList;

import java.util.ArrayList;

public class LstLeagueLeaderPresenter implements LstLeagueLeaderContract.Presenter{

    private LstLeagueLeaderContract.View vista;
    private LstLeagueLeaderModel modelo;

    public LstLeagueLeaderPresenter(LstLeagueLeaderContract.View vista) {
        this.vista = vista;
        this.modelo = new LstLeagueLeaderModel();
    }

    @Override
    public void getLeagueLeaders(Bundle params) {

        this.modelo.getPlayerService(new LstLeagueLeaderContract.Model.OnLstLeagueLeaderListener() {
            @Override
            public void onFinished(ArrayList<LeagueLeader> leagueLeaders) {
                vista.successListLeagueLeaders(leagueLeaders);
            }

            @Override
            public void onFailure(String error) {


            }
        }, params);



    }
}
