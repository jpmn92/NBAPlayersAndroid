package com.nbastatsquiz.lst_career_highs;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nbastatsquiz.beans.CareerHigh;
import com.nbastatsquiz.tools.wsNBA;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LstCareerHighsModel implements LstCareerHighsContract.Model {


    private LstCareerHighsContract.Model.OnLstCareerHighsListener onLstCareerHighsListener;
    private JsonObject jsonObject;
    private CareerHigh careerHigh;
    private ArrayList<CareerHigh> careerHighs;
    private Bundle bundle;


    @Override
    public void getCareerHighService(LstCareerHighsContract.Model.OnLstCareerHighsListener onLstCareerHighsListener, Bundle params) {

        this.onLstCareerHighsListener = onLstCareerHighsListener;
//        this.season = season;
//        this.leagueID = leagueID;
//        this.college = college;
//        this.overallPick = overallPick;
//        this.roundNum = roundNum;
//        this.roundPick = roundPick;
//        this.teamID = teamID;
//        this.topX = topX;
        this.bundle = params;

        new PeticionGetCareerHighs().execute();

    }


    public class PeticionGetCareerHighs extends AsyncTask<Void, Void, Boolean> {

        public PeticionGetCareerHighs() {
            super();

        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            jsonObject = new JsonObject();
            careerHigh = new CareerHigh();


            String statsJson;
            String url = "https://stats.nba.com/stats/";

            // recogemos del bundle los parametros para el WS


//            leagueID = "00";
//            scope = "S";
//            perMode = bundle.getString("PerMode");
//            statCategory = bundle.getString("StatCategory");
//            season = bundle.getString("Season");
//            seasonType = bundle.getString("SeasonType");
//            activeFlag = "YES";

//            college = "";
//            leagueID = "00";
//            overallPick = "";
//            roundNum = "";
//            roundPick = "";
//            season = "";
//            teamID = "0";
//            topX = "";

            //Creando el okhttpclient pasamos los headers necesarios y funciona la peticion
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            //TODO: HACE FALTA PONER ID DEL JUGADOR AQUI
                            .addHeader("Referer", "https://stats.nba.com/player/201935/career/")
                            .addHeader("x-nba-stats-origin", "stats")
                            .addHeader("User-Agent", System.getProperty("http.agent"))
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });


            OkHttpClient client = httpClient.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory
                            .create()).client(client).build();


            wsNBA service = retrofit.create(wsNBA.class);


            Call<JsonObject> response = service.getCareerHighs("00", "PerGame", "201935");
//            String myRequest = response.request().header("x-nba-stats-origin").toString();
//            String my2 = response.request().header("Referer").toString();
//            String myurl = response.request().url().toString();

            try {

                statsJson = new Gson().toJson(response.execute().body());

                if (statsJson == null) {
                    return false;
                }

                jsonObject = new Gson().fromJson(statsJson, JsonObject.class).getAsJsonObject();


                JsonElement resultSet = jsonObject.get("resultSets");
                JsonArray jsonArray2 = resultSet.getAsJsonArray();

                for(JsonElement resultSetElement: jsonArray2){
                    JsonObject resultSetObject = resultSetElement.getAsJsonObject();
                    String name = resultSetObject.get("name").toString().substring(1, resultSetObject.get("name").toString().length()-1);
                    if(name.equals("CareerHighs")){
                        JsonElement headers = resultSetObject.get("headers");
                        JsonArray headersArray = headers.getAsJsonArray();
                        String jsonHeaders = headersArray.toString();

                        JsonElement rowSet = resultSetObject.get("rowSet");
                        JsonArray jsonArray = rowSet.getAsJsonArray();
                        String jsonRowSet = rowSet.toString();
                        careerHighs = new ArrayList<>();
                        JsonArray jsonArrayTop = new JsonArray();

                        if (jsonArray.size() == 0) {
                            return false;
                        }

                        if (jsonArray.size() < 10000) {
                            for (int i = 0; i < jsonArray.size(); i++) {
                                jsonArrayTop.add(jsonArray.get(i));
                            }
                        } else {
                            //TODO: PONER BUCLE A 100
                            for (int i = 0; i < jsonArrayTop.size(); i++) {
                                jsonArrayTop.add(jsonArray.get(i));
                            }
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
                            if (jsonPlayer.contains("McClain") || jsonPlayer.contains("Hoffman")) {
                                String h = jsonPlayer;

                            } else {
                                careerHigh = new Gson().fromJson(jsonPlayer, CareerHigh.class);

                            }
                            careerHighs.add(careerHigh);
                        }
                    }

                }


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
                    onLstCareerHighsListener.onFinished(careerHighs);
                } else {
                    onLstCareerHighsListener.onFailure("No hay jugadores para esa selección");
                }

            } catch (Exception e) {

                onLstCareerHighsListener.onFailure("Error: Listar Draft");

            }
        }


    }
}
