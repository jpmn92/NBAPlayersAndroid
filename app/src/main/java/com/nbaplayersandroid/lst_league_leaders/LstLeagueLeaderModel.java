package com.nbaplayersandroid.lst_league_leaders;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nbaplayersandroid.beans.LeagueLeader;
import com.nbaplayersandroid.beans.LeagueLeadersList;
import com.nbaplayersandroid.beans.PlayerSeasonStatsList;
import com.nbaplayersandroid.tools.wsNBA;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LstLeagueLeaderModel implements LstLeagueLeaderContract.Model {

    private OnLstLeagueLeaderListener onLstLeagueLeaderListener;
    private PlayerSeasonStatsList playerSeasonStatsList;
    private String season, leagueID, perMode, scope, seasonType, statCategory;
    private JsonObject jsonObject;
    private LeagueLeader leagueLeader;
    private LeagueLeadersList leagueLeaderList;
    private ArrayList<LeagueLeader> leagueLeaders;
    private  Bundle bundle;

    @Override
    public void getPlayerService(OnLstLeagueLeaderListener onLstLeagueLeaderListener, Bundle params) {

        this.onLstLeagueLeaderListener = onLstLeagueLeaderListener;
        this.season = season;
        this.leagueID = leagueID;
        this.perMode = perMode;
        this.scope = scope;
        this.seasonType = seasonType;
        this.statCategory = statCategory;
        this.bundle = params;

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

            // recogemos del bundle los parametros para el WS
            leagueID = "00";

//            perMode = "Totals";
//            statCategory = "FG3_PCT";
//            season = "2018-19";
//            seasonType = "Regular Season";
            scope = "S";
            perMode = bundle.getString("PerMode");
            statCategory = bundle.getString("StatCategory");
            season = bundle.getString("Season");
            seasonType = bundle.getString("SeasonType");


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory
                            .create()).build();

            wsNBA service = retrofit.create(wsNBA.class);


            Call<JsonObject> response = service.getLeagueLeaders(leagueID, perMode, scope, season, seasonType, statCategory);

            try {

                statsJson = new Gson().toJson(response.execute().body());

                jsonObject = new Gson().fromJson(statsJson, JsonObject.class).getAsJsonObject();

//                playerSeasonStatsList = new Gson().fromJson(statsJson, PlayerSeasonStatsList.class);

                JsonElement resultSet = jsonObject.get("resultSet");
                JsonObject resultSetObject = resultSet.getAsJsonObject();
                JsonElement headers = resultSetObject.get("headers");
                JsonArray headersArray = headers.getAsJsonArray();
                String jsonHeaders = headersArray.toString();
                JsonElement rowSet = resultSetObject.get("rowSet");
                JsonArray jsonArray = rowSet.getAsJsonArray();
                String jsonRowSet = rowSet.toString();
                leagueLeaders = new ArrayList<>();
                JsonArray jsonArrayTop = new JsonArray();
                //TODO: PONER BUCLE A 100
                for (int i = 0; i < 100; i++) {
                    jsonArrayTop.add(jsonArray.get(i));
                }
                for (JsonElement jsonElement : jsonArrayTop) {
                    jsonElement.toString();
                    String jsonPlayer = "";
                    jsonPlayer = intoString(jsonElement.toString(), headersArray.get(0) + ":", 1);
                    jsonPlayer = jsonPlayer.substring(1, jsonPlayer.length() - 1);

                    jsonPlayer = intoString(jsonPlayer, "{", 0);
                    jsonPlayer = intoString(jsonPlayer, "}", jsonPlayer.length());
                    int posicion = jsonPlayer.indexOf(',') + 1;
                    for (int i = 1; i < headersArray.size(); i++) {
                        jsonPlayer = intoString(jsonPlayer, headersArray.get(i) + ":", posicion);
                        posicion = jsonPlayer.indexOf(',', posicion + 1) + 1;
                        System.out.println(jsonPlayer);
                    }
                    LeagueLeader leagueLeader = new Gson().fromJson(jsonPlayer, LeagueLeader.class);
                    if (leagueLeader.getTEAM().equalsIgnoreCase("NOP")){
                        leagueLeader.setTEAM("NO");
                    }
                    if (leagueLeader.getTEAM().equalsIgnoreCase("UTA")){
                        leagueLeader.setTEAM("UTAH");
                    }
                    leagueLeaders.add(leagueLeader);
                }
                System.out.println("");


            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        private String intoString(String textoReal, String textoInsert, int pos) {
            StringBuilder stringBuilder = new StringBuilder(textoReal);
            stringBuilder.insert(pos, textoInsert);
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(Boolean resp) {
            try {
                if (resp) {
                    //al componente reactivo le devuelvo la lista de sesiones
                    onLstLeagueLeaderListener.onFinished(leagueLeaders);


                }
            } catch (Exception e) {

                onLstLeagueLeaderListener.onFailure("Error: Listar Jugadores");

            }
        }


    }
}
