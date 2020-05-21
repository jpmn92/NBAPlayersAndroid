package com.nbaplayersandroid.lst_league_leaders;

import com.nbaplayersandroid.beans.PlayerSeasonStatsList;

public interface LstLeagueLeaderContract {

    public interface View{
        void successListLeagueLeaders(PlayerSeasonStatsList lstPlayers);
        void failureListLeagueLeaders(String message);
    }

    public interface Presenter{
        void getLeagueLeaders(String id_api);
    }

    public interface Model{
        interface OnLstLeagueLeaderListener{
            void onFinished(PlayerSeasonStatsList lstPlayers);
            void onFailure(String error);
        }

        void getPlayerService(

                OnLstLeagueLeaderListener onLstLeagueLeaderListener, String id_api
        );
    }
}
