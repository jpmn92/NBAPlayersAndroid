package com.nbaplayersandroid.lst_players_data;

import com.nbaplayersandroid.beans.BasketballPlayer;

public interface LstPlayerDataContract {

    public interface View{
        void successListPlayers(BasketballPlayer playerData);
        void failureListPlayers(String message);
    }

    public interface Presenter{
        void getPlayers(String id_api);
    }

    public interface Model{
        interface OnLstPlayerListener{
            void onFinished(BasketballPlayer playerData);
            void onFailure(String error);
        }

        void getPlayerService(

                OnLstPlayerListener onLstPlayerListener, String id_api
        );
    }

}
