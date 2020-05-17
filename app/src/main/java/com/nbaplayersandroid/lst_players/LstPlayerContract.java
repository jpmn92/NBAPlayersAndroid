package com.nbaplayersandroid.lst_players;

import com.nbaplayersandroid.beans.BasketballPlayer;

import java.util.ArrayList;

public interface LstPlayerContract {

    public interface View{
        void successListPlayers(ArrayList<BasketballPlayer> lstPlayers);
        void failureListPlayers(String message);
    }

    public interface Presenter{
        void getPlayers();
    }

    public interface Model{
        interface OnLstPlayerListener{
            void onFinished(ArrayList<BasketballPlayer> lstPlayers);
            void onFailure(String error);
        }

        void getPlayerService(

                OnLstPlayerListener onLstPlayerListener
        );
    }
}
