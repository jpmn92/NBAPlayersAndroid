package com.nbaplayersandroid.lst_players_data;

import com.nbaplayersandroid.beans.BasketballPlayer;

public class LstPlayerDataPresenter implements LstPlayerDataContract.Presenter {

    private LstPlayerDataContract.View vista;
    private LstPlayerDataModel modelo;

    public LstPlayerDataPresenter(LstPlayerDataContract.View vista) {
        this.vista = vista;
        this.modelo = new LstPlayerDataModel();
    }

    @Override
    public void getPlayers(String id_api) {

        this.modelo.getPlayerService(new LstPlayerDataContract.Model.OnLstPlayerListener() {
            @Override
            public void onFinished(BasketballPlayer playerData) {
                vista.successListPlayers(playerData);
            }

            @Override
            public void onFailure(String error) {

            }
        }, id_api);

    }

}
