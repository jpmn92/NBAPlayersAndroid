package com.nbaplayersandroid.lst_league_leaders;

import com.nbaplayersandroid.beans.PlayerSeasonStatsList;

public class LstLeagueLeaderPresenter implements LstLeagueLeaderContract.Presenter{

    private LstLeagueLeaderContract.View vista;
    private LstLeagueLeaderModel modelo;

    public LstLeagueLeaderPresenter(LstLeagueLeaderContract.View vista) {
        this.vista = vista;
        this.modelo = new LstLeagueLeaderModel();
    }

    @Override
    public void getLeagueLeaders(String id_api) {

        this.modelo.getPlayerService(new LstLeagueLeaderContract.Model.OnLstLeagueLeaderListener() {
            @Override
            public void onFinished(PlayerSeasonStatsList lstPlayers) {
                vista.successListLeagueLeaders(lstPlayers);
            }

            @Override
            public void onFailure(String error) {


            }
        }, id_api);



    }
}
