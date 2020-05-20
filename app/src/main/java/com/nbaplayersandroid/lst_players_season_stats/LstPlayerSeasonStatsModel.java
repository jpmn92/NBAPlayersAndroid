package com.nbaplayersandroid.lst_players_season_stats;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.nbaplayersandroid.beans.BasketballPlayer;
import com.nbaplayersandroid.beans.PlayerSeasonStats;
import com.nbaplayersandroid.beans.PlayerSeasonStatsList;
import com.nbaplayersandroid.tools.wsNBA;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LstPlayerSeasonStatsModel implements LstPlayerSeasonStatsContract.Model {

    private OnLstPlayerListener onLstPlayerListener;
    private ArrayList<PlayerSeasonStats> lstPlayers;
    private PlayerSeasonStatsList playerSeasonStatsList;
    private String id_api;
    private String season;


    @Override
    public void getPlayerService(final OnLstPlayerListener onLstPlayerListener, String id_api, String season) {

        this.onLstPlayerListener = onLstPlayerListener;
        this.id_api = id_api;
        this.season = season;

        new PeticionGetPlayerStats().execute();


    }


    public class PeticionGetPlayerStats extends AsyncTask<Void, Void, Boolean> {

        public PeticionGetPlayerStats() {
            super();

        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            String statsJson;
            String url = "https://www.balldontlie.io/api/v1/";
//            String jugador = "237";
//            String temporada = "2018";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory
                            .create()).build();

            wsNBA service = retrofit.create(wsNBA.class);


            Call<PlayerSeasonStatsList> response = service.getPlayerSeasonAverage(id_api, season);

//            Call<ArrayList<BasketballPlayer>> response = service.getAllPlayers();

            try {
                statsJson = new Gson().toJson(response.execute().body());
                playerSeasonStatsList = new Gson().fromJson(statsJson, PlayerSeasonStatsList.class);


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
                    onLstPlayerListener.onFinished(playerSeasonStatsList);


                }
            } catch (Exception e) {

                onLstPlayerListener.onFailure("Error: Listar Jugadores");

            }
        }


    }
}

