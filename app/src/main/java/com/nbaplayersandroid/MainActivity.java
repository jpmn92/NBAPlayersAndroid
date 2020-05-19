package com.nbaplayersandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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
import com.nbaplayersandroid.beans.FirebasePlayer;
import com.nbaplayersandroid.beans.FirebaseTeam;
import com.nbaplayersandroid.beans.PlayerSeasonStats;
import com.nbaplayersandroid.beans.BasketballPlayerList;
import com.nbaplayersandroid.lst_players_season_stats.LstPlayerSeasonStatsContract;
import com.nbaplayersandroid.lst_players_season_stats.LstPlayerSeasonStatsPresenter;
import com.nbaplayersandroid.tools.FirebaseReferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, LstPlayerSeasonStatsContract.View {

    int record;

    TextView txtP1;
    TextView txtP2;
    TextView txtRecord;
    TextView txtSalary;
    ImageView ivP1;
    ImageView ivP2;
    ImageView ivT1;
    ImageView ivT2;
    LinearLayout linJ1;
    LinearLayout linJ2;

    LstPlayerSeasonStatsPresenter lstPlayerSeasonStatsPresenter;
    ArrayList<PlayerSeasonStats> lstPlayers;

    DatabaseReference reference;
    FirebasePlayer fbPlayer;
    FirebasePlayer fbPlayer1;
    FirebasePlayer fbPlayer2;
    FirebaseTeam fbTeam;

    ArrayList<FirebasePlayer> fbPlayerList;
    ArrayList<FirebaseTeam> fbTeamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciamos presenter

        txtP1 = findViewById(R.id.txtP1);
        txtP2 = findViewById(R.id.txtP2);
        txtSalary = findViewById(R.id.txtSalary);
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
        lstPlayers = new ArrayList<>();
        getFbTeams();
        getFbPlayer(); // Esto no funciona si llamamos a algo despues ACOJONANTE
    }

    private void getFbPlayer() {
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
        selectPlayer2();
    }

    private void selectPlayer2() {
        int random = 0;
        do {
            random = (int) (Math.random() * fbPlayerList.size());
            fbPlayer2 = fbPlayerList.get(random);
        } while (fbPlayer1.getIdPlayer() == fbPlayer2.getIdPlayer());

        //Prueba para escudos
        int randomT = (int) (Math.random() * fbTeamList.size());
        int randomT2 = (int) (Math.random() * fbTeamList.size());
        // Fin prueba

        txtP1.setText(fbPlayer1.getName() + " " + fbPlayer1.getLastName());
        txtP2.setText(fbPlayer2.getName() + " " + fbPlayer2.getLastName());

        Picasso.with(this).load(fbPlayer1.getUrlImage()).into(ivP1);
        Picasso.with(this).load(fbTeamList.get(randomT).getUrlImage()).into(ivT1);
        Picasso.with(this).load(fbPlayer2.getUrlImage()).into(ivP2);
        Picasso.with(this).load(fbTeamList.get(randomT2).getUrlImage()).into(ivT2);

        txtRecord.setText(String.valueOf(record));

//        txtP1.setText(playerOld1.getFirstName() + " " + playerOld1.getLastName());
//        txtSalary.setText("$" + String.valueOf(playerOld1.getContract()) + "M");
//        ivP1.setImageResource(playerOld1.getImage());
//        txtP2.setText(playerOld2.getFirstName() + " " + playerOld2.getLastName());
//        ivP2.setImageResource(playerOld2.getImage());
    }

    private void finishGame() {
        record = 0;
        txtRecord.setText(String.valueOf(record));
        System.out.println("Perdiste");
        //continueGame();


    }

    private void continueGame() {
        record++;
        fbPlayer1 = fbPlayer2;
        selectPlayer2();

    }

    @Override
    public void onClick(View v) {


//        createFbPlayer();
        lstPlayerSeasonStatsPresenter.getPlayers();

        switch (v.getId()) {
            case R.id.linJ2:
                //Toast.makeText(this, "PULSADO", Toast.LENGTH_SHORT).show();
                if (fbPlayer2.getIdPlayer() > fbPlayer1.getIdPlayer()) {
                    continueGame();
                } else {
                    finishGame();
                }
                break;

            case R.id.linJ1:
                //Toast.makeText(this, "PULSADO", Toast.LENGTH_SHORT).show();
                if (fbPlayer2.getIdPlayer() < fbPlayer1.getIdPlayer()) {
                    continueGame();
                } else {
                    finishGame();
                }
                break;
        }
    }

    @Override
    public void successListPlayers(BasketballPlayerList lstPlayers) {

        //convertimos el basketballplayerlist a arraylist de basketballplayers por probar
        ArrayList<PlayerSeasonStats> list = lstPlayers.getPlayerSeasonStats();

    }

    @Override
    public void failureListPlayers(String message) {

    }

}
