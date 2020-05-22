package com.nbaplayersandroid.lst_league_leaders;

import android.os.Bundle;

import com.nbaplayersandroid.beans.LeagueLeader;

import java.util.ArrayList;

public interface LstLeagueLeaderContract {

    public interface View{
        void successListLeagueLeaders(ArrayList<LeagueLeader> leagueLeaders);
        void failureListLeagueLeaders(String message);
    }

    public interface Presenter{
        void getLeagueLeaders(Bundle params);
    }

    public interface Model{
        interface OnLstLeagueLeaderListener{
            void onFinished(ArrayList<LeagueLeader> leagueLeaders);
            void onFailure(String error);
        }

        void getPlayerService(

                OnLstLeagueLeaderListener onLstLeagueLeaderListener, Bundle params
        );
    }
}
