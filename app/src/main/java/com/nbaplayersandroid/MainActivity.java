package com.nbaplayersandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.nbaplayersandroid.beans.PlayerSeasonStats;
import com.nbaplayersandroid.beans.PlayerSeasonStatsList;
import com.nbaplayersandroid.lst_players_data.LstPlayerDataContract;
import com.nbaplayersandroid.lst_players_data.LstPlayerDataPresenter;
import com.nbaplayersandroid.lst_players_season_stats.LstPlayerSeasonStatsContract;
import com.nbaplayersandroid.lst_players_season_stats.LstPlayerSeasonStatsPresenter;
import com.nbaplayersandroid.tools.FirebaseReferences;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener, LstPlayerSeasonStatsContract.View, LstPlayerDataContract.View {

    int record;

    TextView txtP1;
    TextView txtP2;
    TextView txtRecord;
    TextView txtPregunta;
    ImageView ivP1;
    ImageView ivP2;
    ImageView ivT1;
    ImageView ivT2;
    LinearLayout linJ1;
    LinearLayout linJ2;

    LstPlayerSeasonStatsPresenter lstPlayerSeasonStatsPresenter;
    ArrayList<PlayerSeasonStats> lstPlayers;

    LstPlayerDataPresenter lstPlayerDataPresenter;
    ArrayList<BasketballPlayer> basketballPlayers;

    DatabaseReference reference;
    FirebasePlayer fbPlayer;
    FirebasePlayer fbPlayer1;
    FirebasePlayer fbPlayer2;
    FirebaseTeam fbTeam;
    FirebaseTeam fbTeam1;
    FirebaseTeam fbTeam2;

    ArrayList<FirebasePlayer> fbPlayerList;
    ArrayList<FirebaseTeam> fbTeamList;

    BasketballPlayer basketballPlayer1;
    BasketballPlayer basketballPlayer2;

    PlayerSeasonStats playerSeasonStats1;
    PlayerSeasonStats playerSeasonStats2;

    boolean gameStarted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameStarted = false;
        //Instanciamos presenter
        txtPregunta = findViewById(R.id.txtPregunta);
        txtP1 = findViewById(R.id.txtP1);
        txtP2 = findViewById(R.id.txtP2);
        //txtSalary = findViewById(R.id.txtSalary);
        txtRecord = findViewById(R.id.txtRecord);
        ivP1 = findViewById(R.id.ivP1);
        ivP2 = findViewById(R.id.ivP2);
        ivT1 = findViewById(R.id.ivTeam1);
        ivT2 = findViewById(R.id.ivTeam2);
        linJ1 = findViewById(R.id.linJ1);
        linJ1.setOnClickListener(this);
        linJ2 = findViewById(R.id.linJ2);
        linJ2.setOnClickListener(this);



        lstPlayerSeasonStatsPresenter = new LstPlayerSeasonStatsPresenter(this);
        lstPlayerDataPresenter = new LstPlayerDataPresenter(this);
        lstPlayers = new ArrayList<>();
        //lstPlayerSeasonStatsPresenter.getSeasonStatsPlayer("237", "2019");
        getFbTeams();
        getFbPlayers(); // Esto no funciona si llamamos a algo despues ACOJONANTE

//        txtPregunta.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                lstPlayerDataPresenter.getPlayers("370");
//
//            }
//        });

    }

    private void getFbPlayers() {
        reference = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.JUGADORES_REFERENCE);
        fbPlayerList = new ArrayList<FirebasePlayer>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Object object = snapshot.getValue(Object.class);
                    String json = new Gson().toJson(object);
                    FirebasePlayer fbPlayer = new Gson().fromJson(json, FirebasePlayer.class);
                    fbPlayerList.add(fbPlayer);
                }

                startGame();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void getFbTeams() {
        reference = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.EQUIPOS_REFERENCE);
        fbTeamList = new ArrayList<FirebaseTeam>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Object object = snapshot.getValue(Object.class);
                    String json = new Gson().toJson(object);
                    FirebaseTeam fbTeam = new Gson().fromJson(json, FirebaseTeam.class);
                    fbTeamList.add(fbTeam);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    private void createFbPlayer() {

        reference = FirebaseDatabase.getInstance().getReference().child("Jugador");

        fbPlayer = new FirebasePlayer();
        fbPlayer.setIdAPI(268);
        fbPlayer.setIdPlayer(21);
        fbPlayer.setLastName("LaVine");
        fbPlayer.setName("Zach");
        fbPlayer.setUrlImage("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612741/2019/260x190/203897.png");
        reference.push().setValue(fbPlayer);


    }

    private void createFbTeam() {

        reference = FirebaseDatabase.getInstance().getReference().child("Equipo");

        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(2);
        fbTeam.setIdTeam(2);
        fbTeam.setName("Boston Celtics");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/BOS_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (2, 'Boston Celtics', 2, 'https://stats.nba.com/media/img/teams/logos/BOS_logo.svg','-'),

    }

    private void startGame() {
        record = 0;
        int random = 0;
        random = (int) (Math.random() * fbPlayerList.size());
        fbPlayer1 = fbPlayerList.get(random);
        lstPlayerDataPresenter.getPlayers(String.valueOf(fbPlayer1.getIdAPI()));
    }

    private void selectPlayer2() {
        int random = 0;
        do {
            random = (int) (Math.random() * fbPlayerList.size());
            fbPlayer2 = fbPlayerList.get(random);
        } while (fbPlayer1.getIdPlayer() == fbPlayer2.getIdPlayer());

        lstPlayerDataPresenter.getPlayers(String.valueOf(fbPlayer2.getIdAPI()));

    }

    private void finishGame() {
        record = 0;
        txtRecord.setText(String.valueOf(record));
        System.out.println("Perdiste");
        //continueGame();


    }

    private void continueGame() {
        record++;
        basketballPlayer1 = basketballPlayer2;
        playerSeasonStats1 = playerSeasonStats2;
        fbPlayer1 = fbPlayer2;
        fbTeam1 = fbTeam2;
        selectPlayer2();

    }

    private void recogerDatos(){

        int team1 = (int) basketballPlayer1.getTeam().getId() - 1;
        int team2 = (int) basketballPlayer2.getTeam().getId() - 1;


        txtP1.setText(fbPlayer1.getName() + " " + fbPlayer1.getLastName());
        txtP2.setText(fbPlayer2.getName() + " " + fbPlayer2.getLastName());

        Picasso.with(this).load(fbPlayer1.getUrlImage()).into(ivP1);
        Picasso.with(this).load(fbTeamList.get(team1).getUrlImage()).into(ivT1);

        txtP1.setText(fbPlayer1.getName() + " " + fbPlayer1.getLastName());
        txtP2.setText(fbPlayer2.getName() + " " + fbPlayer2.getLastName());

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

        Picasso.with(this).load(fbPlayer2.getUrlImage()).into(ivP2);
        Picasso.with(this).load(fbTeamList.get(team2).getUrlImage()).into(ivT2);

        txtRecord.setText(String.valueOf(record));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.linJ2:
                //Toast.makeText(this, "PULSADO", Toast.LENGTH_SHORT).show();
//                if (fbPlayer2.getIdPlayer() > fbPlayer1.getIdPlayer()) {
//                    continueGame();
//                } else {
//                    finishGame();
//                }

                if(playerSeasonStats2.getFg3Pct() > playerSeasonStats1.getFg3Pct()) {
                    continueGame();
                } else {
                    finishGame();
                }
                break;



            case R.id.linJ1:
                //Toast.makeText(this, "PULSADO", Toast.LENGTH_SHORT).show();
//                if (fbPlayer2.getIdPlayer() < fbPlayer1.getIdPlayer()) {
//                    continueGame();
//                } else {
//                    finishGame();
//                }
                if(playerSeasonStats2.getFg3Pct() < playerSeasonStats1.getFg3Pct()) {
                    continueGame();
                } else {
                    finishGame();
                }
                break;
        }
    }



    @Override
    public void successListSeasonStatsPlayers(PlayerSeasonStatsList lstPlayers) {
        if(!gameStarted){
            playerSeasonStats1 = lstPlayers.getPlayerSeasonStats().get(0);
            gameStarted = true;
            selectPlayer2();
        }
        else{
            playerSeasonStats2 = lstPlayers.getPlayerSeasonStats().get(0);
            recogerDatos();
        }
        float pct3 = lstPlayers.getPlayerSeasonStats().get(0).getFg3Pct();
    }

    @Override
    public void failureListSeasonStatsPlayers(String message) {

    }

    @Override
    public void successListPlayers(BasketballPlayer playerData) {
        if(!gameStarted){
            basketballPlayer1 = playerData;
            lstPlayerSeasonStatsPresenter.getSeasonStatsPlayer(String.valueOf((int)basketballPlayer1.getId()), "2019");

        }
        else{

            basketballPlayer2 = playerData;
            lstPlayerSeasonStatsPresenter.getSeasonStatsPlayer(String.valueOf((int)basketballPlayer2.getId()), "2019");

            //String lastName = playerData.getLastName();
        }


    }

    @Override
    public void failureListPlayers(String message) {

    }
}
