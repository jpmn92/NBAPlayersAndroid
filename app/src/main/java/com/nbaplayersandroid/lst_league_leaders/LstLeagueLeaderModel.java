package com.nbaplayersandroid.lst_league_leaders;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nbaplayersandroid.beans.LeagueLeader;
import com.nbaplayersandroid.beans.PlayerSeasonStatsList;
import com.nbaplayersandroid.tools.wsNBA;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LstLeagueLeaderModel implements LstLeagueLeaderContract.Model {

    private OnLstLeagueLeaderListener onLstLeagueLeaderListener;
    private PlayerSeasonStatsList playerSeasonStatsList;
    private String id_api, season, leagueID, perMode, scope, seasonType, statCategory;
    private JsonObject jsonObject;
    private LeagueLeader leagueLeader;

    @Override
    public void getPlayerService(OnLstLeagueLeaderListener onLstLeagueLeaderListener, String id_api) {

        this.onLstLeagueLeaderListener = onLstLeagueLeaderListener;
        this.id_api = id_api;
        this.season = season;
        this.leagueID = leagueID;
        this.perMode = perMode;
        this.scope = scope;
        this.seasonType = seasonType;
        this.statCategory = statCategory;

        new PeticionGetLeagueLeaders().execute();

    }

    public class PeticionGetLeagueLeaders extends AsyncTask<Void, Void, Boolean> {

        public PeticionGetLeagueLeaders() {
            super();

        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            jsonObject = new JsonObject();
            leagueLeader = new LeagueLeader();


            String statsJson;
            String url = "https://stats.nba.com/stats/";

            leagueID = "00";
            perMode = "PerGame";
            statCategory = "PTS";
            season = "2016-17";
            seasonType = "Playoffs";
            scope = "S";


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory
                            .create()).build();

            wsNBA service = retrofit.create(wsNBA.class);


            Call<JsonObject> response = service.getLeagueLeaders(leagueID, perMode, scope, season, seasonType, statCategory);

            try {

                statsJson = new Gson().toJson(response.execute().body());

                jsonObject = new Gson().fromJson(statsJson, JsonObject.class);

//                playerSeasonStatsList = new Gson().fromJson(statsJson, PlayerSeasonStatsList.class);



//                leagueLeader = new Gson().fromJson(statsJson, LeagueLeader.class);
                leagueLeader = new Gson().fromJson(jsonObject, LeagueLeader.class);


            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean resp) {
            try {
                if (resp) {
                    //al componente reactivo le devuelvo la lista de sesiones
                    onLstLeagueLeaderListener.onFinished(playerSeasonStatsList);


                }
            } catch (Exception e) {

                onLstLeagueLeaderListener.onFailure("Error: Listar Jugadores");

            }
        }


    }
}
