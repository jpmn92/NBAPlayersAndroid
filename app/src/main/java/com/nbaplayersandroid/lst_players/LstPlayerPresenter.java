package com.nbaplayersandroid.lst_players;

import com.nbaplayersandroid.beans.BasketballPlayer;

import java.util.ArrayList;

public class LstPlayerPresenter implements LstPlayerContract.Presenter {

    private LstPlayerContract.View vista;
    private LstPlayerModel modelo;

    public LstPlayerPresenter(LstPlayerContract.View vista) {
        this.vista = vista;
        this.modelo = new LstPlayerModel();
    }

    @Override
    public void getPlayers() {

        this.modelo.getPlayerService(new LstPlayerContract.Model.OnLstPlayerListener() {
            @Override
            public void onFinished(ArrayList<BasketballPlayer> lstPlayers) {
                vista.successListPlayers(lstPlayers);
            }

            @Override
            public void onFailure(String error) {

            }
        });



    }
}
