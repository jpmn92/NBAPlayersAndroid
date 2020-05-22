package com.nbaplayersandroid;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.nbaplayersandroid.beans.BasketballPlayer;
import com.nbaplayersandroid.beans.FirebasePlayer;
import com.nbaplayersandroid.beans.FirebaseTeam;
import com.nbaplayersandroid.beans.LeagueLeader;
import com.nbaplayersandroid.beans.PlayerSeasonStats;
import com.nbaplayersandroid.beans.PlayerSeasonStatsList;
import com.nbaplayersandroid.lst_league_leaders.LstLeagueLeaderContract;
import com.nbaplayersandroid.lst_league_leaders.LstLeagueLeaderPresenter;
import com.nbaplayersandroid.lst_players_data.LstPlayerDataContract;
import com.nbaplayersandroid.lst_players_data.LstPlayerDataPresenter;
import com.nbaplayersandroid.lst_players_season_stats.LstPlayerSeasonStatsContract;
import com.nbaplayersandroid.lst_players_season_stats.LstPlayerSeasonStatsPresenter;
import com.nbaplayersandroid.tools.ColorApp;
import com.nbaplayersandroid.tools.FirebaseReferences;
import com.nbaplayersandroid.tools.Mode;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener, LstPlayerSeasonStatsContract.View, LstPlayerDataContract.View, LstLeagueLeaderContract.View {

    int record;

    TextView txtP1, txtP2, txtRecord, txtPregunta, txtNameP1, txtNameP2;
    ImageView ivP1, ivP2, ivT1, ivT2;
    LinearLayout linJ1, linJ2, linFront;
    RelativeLayout relCircle;

    LstLeagueLeaderPresenter lstLeagueLeaderPresenter;
    ArrayList<LeagueLeader> leagueLeadersGlobal;
    LeagueLeader leagueLeader1, leagueLeader2;

    Bundle params;

    float valueP1;
    float valueP2;

    boolean gameStarted;

    String season;
    String seasonType;
    String statCategory;
    String perMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //txtP1.setVisibility(View.INVISIBLE);
        params = this.getIntent().getExtras();
        season = params.getString("Season");
        seasonType = params.getString("SeasonType");
        statCategory = params.getString("StatCategory");
        perMode = params.getString("PerMode");

        System.out.println("");

        gameStarted = false;

        initComponents();


        lstLeagueLeaderPresenter = new LstLeagueLeaderPresenter(this);


        leagueLeadersGlobal = new ArrayList<>();
        lstLeagueLeaderPresenter.getLeagueLeaders(params);


    }

    private void initComponents() {

        txtPregunta = findViewById(R.id.txtPregunta);
        txtP1 = findViewById(R.id.txtP1);
        txtP1.setTextColor(Color.RED);
        txtP1.setTextSize(50);
        //txtP1.setVisibility(View.GONE);
        txtP2 = findViewById(R.id.txtP2);
        txtP2.setTextSize(50);
        txtP2.setTextColor(Color.RED);
        txtNameP1 = findViewById(R.id.txtNameP1);
        txtNameP2 = findViewById(R.id.txtNameP2);
        //txtP2.setVisibility(View.GONE);
        txtRecord = findViewById(R.id.txtRecord);
        ivP1 = findViewById(R.id.ivP1);
        ivP2 = findViewById(R.id.ivP2);
        ivT1 = findViewById(R.id.ivTeam1);
        ivT2 = findViewById(R.id.ivTeam2);
        linJ1 = findViewById(R.id.linJ1);
        linJ1.setOnClickListener(this);
        linJ2 = findViewById(R.id.linJ2);
        linJ2.setOnClickListener(this);
        linFront = findViewById(R.id.linFront);
        linFront.setVisibility(View.GONE);
        relCircle = findViewById(R.id.relCircle);

        txtPregunta.setText(statCategory + " " + season);


    }


    private void startGame() {
        record = 0;
        int random = 0;
        random = (int) (Math.random() * leagueLeadersGlobal.size());
        leagueLeader1 = leagueLeadersGlobal.get(random);
        selectPlayer2();


    }

    private void selectPlayer2() {
        int random = 0;
        do {

            random = (int) (Math.random() * leagueLeadersGlobal.size());
            leagueLeader2 = leagueLeadersGlobal.get(random);
            recogerDatos();

        } while (leagueLeader1.getPLAYER_ID() == leagueLeader2.getPLAYER_ID());

        random = (int) (Math.random() * leagueLeadersGlobal.size());
        leagueLeader2 = leagueLeadersGlobal.get(random);
    }

    private void iluminar(String color) {
        txtP1.setText(String.valueOf(valueP1));
        txtP2.setText(String.valueOf(valueP2));
        linFront.setBackgroundColor(Color.parseColor(color));
        linFront.setVisibility(View.VISIBLE);
        linFront.postDelayed(new Runnable() {
            public void run() {
                linFront.setVisibility(View.GONE);
            }
        }, 1000);

    }

    private void finishGame() {
        iluminar(ColorApp.RED);
        record = 0;
        txtRecord.setText(String.valueOf(record));
        System.out.println("Perdiste");
        //continueGame();


    }

    private void continueGame() {
        iluminar(ColorApp.GREEN);

        record++;

        leagueLeader1 = leagueLeader2;

        selectPlayer2();

    }

    private void recogerDatos() {

        Double id1 = leagueLeader1.getPLAYER_ID();
        Double id2 = leagueLeader2.getPLAYER_ID();

        String url_image = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/";
        String url_imageTeam = "https://a.espncdn.com/i/teamlogos/nba/500/";

        String url_imagen1 = url_image + id1.intValue() + ".png";
        String url_imagen2 = url_image + id2.intValue() + ".png";

        if(leagueLeader1.getPLAYER().equals("Steve Nash")){
            url_imagen1 = "https://i.dlpng.com/static/png/219514_preview.png";
        }if(leagueLeader2.getPLAYER().equals("Steve Nash")){
            url_imagen2 = "https://i.dlpng.com/static/png/219514_preview.png";
        }

        /*if(leagueLeader1.getTEAM().equals("SEA")){
            url_imagen1 = "https://www.pngfind.com/pngs/m/482-4820379_seattle-sonics-seattle-washington-usa-seattle-supersonics-logo.png";
        }if(leagueLeader2.getTEAM().equals("SEA")){
            url_imagen2 = "https://www.pngfind.com/pngs/m/482-4820379_seattle-sonics-seattle-washington-usa-seattle-supersonics-logo.png";
        }

        if(leagueLeader1.getTEAM().equals("NOH")){
            url_imagen1 = "https://w7.pngwing.com/pngs/589/435/png-transparent-new-orleans-pelicans-charlotte-hornets-smoothie-king-center-nba-new-orleans-saints-nba-text-logo-fictional-character.png";
        }if(leagueLeader2.getTEAM().equals("NOH")){
            url_imagen2 = "https://w7.pngwing.com/pngs/589/435/png-transparent-new-orleans-pelicans-charlotte-hornets-smoothie-king-center-nba-new-orleans-saints-nba-text-logo-fictional-character.png";
        }*/

        Picasso.with(this).load(url_imagen1).into(ivP1);
        Picasso.with(this).load(url_imageTeam + leagueLeader1.getTEAM() + ".png").into(ivT1);

        txtNameP1.setText(leagueLeader1.getPLAYER());
        txtNameP2.setText(leagueLeader2.getPLAYER());


        //Para cambiar el fondo, se podrá mejorar
//        Picasso.with(this).load(fbTeamList.get(team1).getUrlBackground()).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                linJ1.setBackground(new BitmapDrawable(bitmap));
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });
//        Picasso.with(this).load(fbTeamList.get(team2).getUrlBackground()).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                linJ2.setBackground(new BitmapDrawable(bitmap));
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });

        Picasso.with(this).load(url_imagen2).into(ivP2);
        Picasso.with(this).load(url_imageTeam + leagueLeader2.getTEAM() + ".png").into(ivT2);

        txtRecord.setText(String.valueOf(record));
        calculateValues();
    }

//    private String generaImagen(String equipo, String id_jugador) {
//
//        String url_image = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/";
//
//
//        switch (equipo) {
//            case "OKC":
//                return url_image+"/1610612760/2019/260x190/"+id_jugador+".png";
//
//            case "CLE":
//                return url_image+"/1610612739/2019/260x190/"+id_jugador+".png";
//
//            case "GSW":
//                return url_image+"/1610612744/2019/260x190/"+id_jugador+".png";
//
//            case "GSW":
//                return url_image+"/1610612744/2019/260x190/"+id_jugador+".png";
//
//            case "GSW":
//                return url_image+"/1610612744/2019/260x190/"+id_jugador+".png";
//
//
//            default: return url_image;
//
//        }
//    }


    private void calculateValues() {
        switch (statCategory) {
            case "PTS":
                valueP1 = leagueLeader1.getPTS().floatValue();
                valueP2 = leagueLeader2.getPTS().floatValue();
                break;

            case "AST":
                valueP1 = leagueLeader1.getAST().floatValue();
                valueP2 = leagueLeader2.getAST().floatValue();
                break;

            case "OREB":
                valueP1 = leagueLeader1.getOREB().floatValue();
                valueP2 = leagueLeader2.getOREB().floatValue();
                break;

            case "DREB":
                valueP1 = leagueLeader1.getDREB().floatValue();
                valueP2 = leagueLeader2.getDREB().floatValue();
                break;

            case "STL":
                valueP1 = leagueLeader1.getSTL().floatValue();
                valueP2 = leagueLeader2.getSTL().floatValue();
                break;

            case "BLK":
                valueP1 = leagueLeader1.getBLK().floatValue();
                valueP2 = leagueLeader2.getBLK().floatValue();
                break;

            case "TOV":
                valueP1 = leagueLeader1.getTOV().floatValue();
                valueP2 = leagueLeader2.getTOV().floatValue();
                break;

            case "MIN":
                valueP1 = leagueLeader1.getMIN().floatValue();
                valueP2 = leagueLeader2.getMIN().floatValue();
                break;

            case "3PM":
                valueP1 = leagueLeader1.getFG3M().floatValue();
                valueP2 = leagueLeader2.getFG3M().floatValue();
                break;

            case "FTM":
                valueP1 = leagueLeader1.getFTM().floatValue();
                valueP2 = leagueLeader2.getFTM().floatValue();
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.linJ2:
                if (valueP2 >= valueP1) {
                    continueGame();
                } else {
                    finishGame();
                }
                break;

            case R.id.linJ1:
                if (valueP2 <= valueP1) {
                    continueGame();
                } else {
                    finishGame();
                }
                break;
        }
    }


    @Override
    public void successListSeasonStatsPlayers(PlayerSeasonStatsList lstPlayers) {
//        if(!gameStarted){
//            playerSeasonStats1 = lstPlayers.getPlayerSeasonStats().get(0);
//            gameStarted = true;
//            selectPlayer2();
//        }
//        else{
//            playerSeasonStats2 = lstPlayers.getPlayerSeasonStats().get(0);
//            recogerDatos();
//        }
//        float pct3 = lstPlayers.getPlayerSeasonStats().get(0).getFg3Pct();
    }

    @Override
    public void failureListSeasonStatsPlayers(String message) {

    }

    @Override
    public void successListPlayers(BasketballPlayer playerData) {
//        if(!gameStarted){
//            basketballPlayer1 = playerData;
//            lstPlayerSeasonStatsPresenter.getSeasonStatsPlayer(String.valueOf((int)basketballPlayer1.getId()), "2019");
//
//        }
//        else{
//
//            basketballPlayer2 = playerData;
//            lstPlayerSeasonStatsPresenter.getSeasonStatsPlayer(String.valueOf((int)basketballPlayer2.getId()), "2019");
//
//        }


    }

    @Override
    public void failureListPlayers(String message) {

    }

    @Override
    public void successListLeagueLeaders(ArrayList<LeagueLeader> leagueLeaders) {

        //pasamos a arraylist global el arraygenerado
        leagueLeadersGlobal = leagueLeaders;
        //iniciamos juego
        startGame();


    }

    @Override
    public void failureListLeagueLeaders(String message) {

    }
}
