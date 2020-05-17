package com.nbaplayersandroid.lst_players_season_stats;

import com.nbaplayersandroid.beans.BasketballPlayerList;

public interface LstPlayerSeasonStatsContract {

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
