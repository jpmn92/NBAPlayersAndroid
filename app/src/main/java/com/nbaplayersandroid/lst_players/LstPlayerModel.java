package com.nbaplayersandroid.lst_players;

import android.os.AsyncTask;

import com.nbaplayersandroid.beans.BasketballPlayer;
import com.nbaplayersandroid.beans.BasketballPlayerList;
import com.nbaplayersandroid.tools.wsNBA;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LstPlayerModel implements LstPlayerContract.Model {

    private OnLstPlayerListener onLstPlayerListener;
    private ArrayList<BasketballPlayer> lstPlayers;
    private BasketballPlayerList basketballPlayerList;


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


            Call<BasketballPlayerList> response = service.getPlayerSeasonAverage(jugador, temporada);

//            Call<ArrayList<BasketballPlayer>> response = service.getAllPlayers();

            try {
                basketballPlayerList = response.execute().body();


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
                    onLstPlayerListener.onFinished(basketballPlayerList);


                }
            } catch (Exception e) {

                onLstPlayerListener.onFailure("Error: Listar Jugadores");

            }
        }


    }
}

