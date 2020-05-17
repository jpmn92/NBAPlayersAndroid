package com.nbaplayersandroid.lst_players;

import com.nbaplayersandroid.beans.BasketballPlayer;
import com.nbaplayersandroid.beans.BasketballPlayerList;

import java.util.ArrayList;

public interface LstPlayerContract {

    public interface View{
        void successListPlayers(BasketballPlayerList lstPlayers);
        void failureListPlayers(String message);
    }

    public interface Presenter{
        void getPlayers();
    }

    public interface Model{
        interface OnLstPlayerListener{
            void onFinished(BasketballPlayerList lstPlayers);
            void onFailure(String error);
        }

        void getPlayerService(

                OnLstPlayerListener onLstPlayerListener
        );
    }
}
