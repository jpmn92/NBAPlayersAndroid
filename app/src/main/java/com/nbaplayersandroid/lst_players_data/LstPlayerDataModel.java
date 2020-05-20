package com.nbaplayersandroid.lst_players_data;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.nbaplayersandroid.beans.BasketballPlayer;
import com.nbaplayersandroid.tools.wsNBA;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LstPlayerDataModel implements LstPlayerDataContract.Model {

    private LstPlayerDataContract.Model.OnLstPlayerListener onLstPlayerListener;
    private BasketballPlayer basketballPlayer;
    private String id_api;

    @Override
    public void getPlayerService(OnLstPlayerListener onLstPlayerListener, String id_api) {
        this.onLstPlayerListener = onLstPlayerListener;
        this.id_api = id_api;
        new PeticionGetPlayersData().execute();
    }

    public class PeticionGetPlayersData extends AsyncTask<Void, Void, Boolean> {

        public PeticionGetPlayersData() {
            super();

        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            String playerJson;
            String url = "https://www.balldontlie.io/api/v1/";


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory
                            .create()).build();

            wsNBA service = retrofit.create(wsNBA.class);


            Call<Object> response = service.getPlayers(id_api);


            try {

                playerJson = new Gson().toJson(response.execute().body());
                basketballPlayer = new Gson().fromJson(playerJson, BasketballPlayer.class);



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
                    onLstPlayerListener.onFinished(basketballPlayer);


                }
            } catch (Exception e) {

                onLstPlayerListener.onFailure("Error: Listar Jugadores");

            }
        }


    }

}
