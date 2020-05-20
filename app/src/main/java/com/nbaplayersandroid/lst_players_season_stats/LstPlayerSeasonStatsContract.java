package com.nbaplayersandroid.lst_players_season_stats;

import com.nbaplayersandroid.beans.PlayerSeasonStatsList;

public interface LstPlayerSeasonStatsContract {

    public interface View{
        void successListSeasonStatsPlayers(PlayerSeasonStatsList lstPlayers);
        void failureListSeasonStatsPlayers(String message);
    }

    public interface Presenter{
        void getSeasonStatsPlayer(String id_api, String season);
    }

    public interface Model{
        interface OnLstPlayerListener{
            void onFinished(PlayerSeasonStatsList lstPlayers);
            void onFailure(String error);
        }

        void getPlayerService(

                OnLstPlayerListener onLstPlayerListener, String id_api, String season
        );
    }
}
