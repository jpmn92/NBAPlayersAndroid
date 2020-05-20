package com.nbaplayersandroid.lst_players_season_stats;

import com.nbaplayersandroid.beans.PlayerSeasonStatsList;

public class LstPlayerSeasonStatsPresenter implements LstPlayerSeasonStatsContract.Presenter {

    private LstPlayerSeasonStatsContract.View vista;
    private LstPlayerSeasonStatsModel modelo;

    public LstPlayerSeasonStatsPresenter(LstPlayerSeasonStatsContract.View vista) {
        this.vista = vista;
        this.modelo = new LstPlayerSeasonStatsModel();
    }

    @Override
    public void getSeasonStatsPlayer() {

        this.modelo.getPlayerService(new LstPlayerSeasonStatsContract.Model.OnLstPlayerListener() {
            @Override
            public void onFinished(PlayerSeasonStatsList lstPlayers) {
                vista.successListSeasonStatsPlayers(lstPlayers);
            }

            @Override
            public void onFailure(String error) {

            }
        });



    }
}
