package com.nbaplayersandroid.lst_players_season_stats;

import android.os.AsyncTask;

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


    @Override
    public void getPlayerService(final OnLstPlayerListener onLstPlayerListener) {

        this.onLstPlayerListener = onLstPlayerListener;

        new PeticionGetPlayers().execute();


    }


    public class PeticionGetPlayers extends AsyncTask<Void, Void, Boolean> {

        public PeticionGetPlayers() {
            super();

        }

        @Override
        protected Boolean doInBackground(Void... voids) {


            String url = "https://www.balldontlie.io/api/v1/";
            String jugador = "237";
            String temporada = "2018";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory
                            .create()).build();

            wsNBA service = retrofit.create(wsNBA.class);


            Call<PlayerSeasonStatsList> response = service.getPlayerSeasonAverage(jugador, temporada);

//            Call<ArrayList<BasketballPlayer>> response = service.getAllPlayers();

            try {
                playerSeasonStatsList = response.execute().body();


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

