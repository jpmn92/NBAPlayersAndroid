package com.nbaplayersandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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

    List<Player_old> playerOlds;
    int record;
    Player_old playerOld1;
    Player_old playerOld2;

    TextView txtP1;
    TextView txtP2;
    TextView txtRecord;
    TextView txtSalary;
    ImageView ivP1;
    ImageView ivP2;

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

        playerOlds = new ArrayList<>();
        txtP1 = findViewById(R.id.txtP1);
        txtP2 = findViewById(R.id.txtP2);
        txtSalary = findViewById(R.id.txtSalary);
        txtRecord = findViewById(R.id.txtRecord);
        ivP1 = findViewById(R.id.ivP1);
        ivP1.setOnClickListener(this);
        ivP2 = findViewById(R.id.ivP2);
        ivP2.setOnClickListener(this);

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

                startGame();

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
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(3);
        fbTeam.setIdTeam(3);
        fbTeam.setName("Brooklyn Nets");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/BKN_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (3, 'Brooklyn Nets', 3, 'https://stats.nba.com/media/img/teams/logos/BKN_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(4);
        fbTeam.setIdTeam(4);
        fbTeam.setName("Charlotte Hornets");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/CHA_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (4, 'Charlotte Hornets', 4, 'https://stats.nba.com/media/img/teams/logos/CHA_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(5);
        fbTeam.setIdTeam(5);
        fbTeam.setName("Chicago Bulls");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/CHI_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (5, 'Chicago Bulls', 5, 'https://stats.nba.com/media/img/teams/logos/CHI_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(6);
        fbTeam.setIdTeam(6);
        fbTeam.setName("Cleveland Cavaliers");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/CLE_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (6, 'Cleveland Cavaliers', 6, 'https://stats.nba.com/media/img/teams/logos/CLE_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(7);
        fbTeam.setIdTeam(7);
        fbTeam.setName("Dallas Mavericks");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/DAL_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (7, 'Dallas Mavericks', 7, 'https://stats.nba.com/media/img/teams/logos/DAL_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(8);
        fbTeam.setIdTeam(8);
        fbTeam.setName("Denver Nuggets");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/DEN_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (8, 'Denver Nuggets', 8, 'https://stats.nba.com/media/img/teams/logos/DEN_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(9);
        fbTeam.setIdTeam(9);
        fbTeam.setName("Detroit Pistons");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/DET_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (9, 'Detroit Pistons', 9, 'https://stats.nba.com/media/img/teams/logos/DET_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(10);
        fbTeam.setIdTeam(10);
        fbTeam.setName("Golden State Warriors");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/GSW_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (10, 'Golden State Warriors', 10, 'https://stats.nba.com/media/img/teams/logos/GSW_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(11);
        fbTeam.setIdTeam(11);
        fbTeam.setName("Houston Rockets");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/HOU_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (11, 'Houston Rockets', 11, 'https://stats.nba.com/media/img/teams/logos/HOU_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(12);
        fbTeam.setIdTeam(12);
        fbTeam.setName("Indiana Pacers");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/IND_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (12, 'Indiana Pacers', 12, 'https://stats.nba.com/media/img/teams/logos/IND_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(13);
        fbTeam.setIdTeam(13);
        fbTeam.setName("LA Clippers");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/LAC_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (13, 'LA Clippers', 13, 'https://stats.nba.com/media/img/teams/logos/LAC_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(14);
        fbTeam.setIdTeam(14);
        fbTeam.setName("Los Angeles Lakers");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/LAL_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (14, 'Los Angeles Lakers', 14, 'https://stats.nba.com/media/img/teams/logos/LAL_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(15);
        fbTeam.setIdTeam(15);
        fbTeam.setName("Memphis Grizzlies");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/MEM_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (15, 'Memphis Grizzlies', 15, 'https://stats.nba.com/media/img/teams/logos/MEM_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(16);
        fbTeam.setIdTeam(16);
        fbTeam.setName("Miami Heat");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/MIA_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (16, 'Miami Heat', 16, 'https://stats.nba.com/media/img/teams/logos/MIA_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(17);
        fbTeam.setIdTeam(17);
        fbTeam.setName("Milwaukee Bucks");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/MIL_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (17, 'Milwaukee Bucks', 17, 'https://stats.nba.com/media/img/teams/logos/MIL_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(18);
        fbTeam.setIdTeam(18);
        fbTeam.setName("Minnesota Timberwolves");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/MIN_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (18, 'Minnesota Timberwolves', 18, 'https://stats.nba.com/media/img/teams/logos/MIN_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(19);
        fbTeam.setIdTeam(19);
        fbTeam.setName("New Orleans Pelicans");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/NOP_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (19, 'New Orleans Pelicans', 19, 'https://stats.nba.com/media/img/teams/logos/NOP_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(20);
        fbTeam.setIdTeam(20);
        fbTeam.setName("New York Knicks");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/NYK_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (20, 'New York Knicks', 20, 'https://stats.nba.com/media/img/teams/logos/NYK_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(21);
        fbTeam.setIdTeam(21);
        fbTeam.setName("Oklahoma City Thunder");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/OKC_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (21, 'Oklahoma City Thunder', 21, 'https://stats.nba.com/media/img/teams/logos/OKC_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(22);
        fbTeam.setIdTeam(22);
        fbTeam.setName("Orlando Magic");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/ORL_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (22, 'Orlando Magic', 22, 'https://stats.nba.com/media/img/teams/logos/ORL_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(23);
        fbTeam.setIdTeam(23);
        fbTeam.setName("Philadelphia 76ers");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/PHI_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (23, 'Philadelphia 76ers', 23, 'https://stats.nba.com/media/img/teams/logos/PHI_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(24);
        fbTeam.setIdTeam(24);
        fbTeam.setName("Phoenix Suns");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/PHX_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (24, 'Phoenix Suns', 24, 'https://stats.nba.com/media/img/teams/logos/PHX_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(25);
        fbTeam.setIdTeam(25);
        fbTeam.setName("Portland Trail Blazers");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/POR_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (25, 'Portland Trail Blazers', 25, 'https://stats.nba.com/media/img/teams/logos/POR_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(26);
        fbTeam.setIdTeam(26);
        fbTeam.setName("Sacramento King");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/SAC_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (26, 'Sacramento Kings', 26, 'https://stats.nba.com/media/img/teams/logos/SAC_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(27);
        fbTeam.setIdTeam(27);
        fbTeam.setName("San Antonio Spurs");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/SAS_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (27, 'San Antonio Spurs', 27, 'https://stats.nba.com/media/img/teams/logos/SAS_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(28);
        fbTeam.setIdTeam(28);
        fbTeam.setName("Toronto Raptors");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/TOR_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (28, 'Toronto Raptors', 28, 'https://stats.nba.com/media/img/teams/logos/TOR_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(29);
        fbTeam.setIdTeam(29);
        fbTeam.setName("Utah Jazz");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/UTA_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (29, 'Utah Jazz', 29, 'https://stats.nba.com/media/img/teams/logos/UTA_logo.svg','-'),
        fbTeam = new FirebaseTeam();
        fbTeam.setIdAPI(30);
        fbTeam.setIdTeam(30);
        fbTeam.setName("Washington Wizards");
        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/WAS_logo.svg");
        fbTeam.setUrlBackground("-");
        reference.push().setValue(fbTeam);
//        (30, 'Washington Wizards', 30, 'https://stats.nba.com/media/img/teams/logos/WAS_logo.svg','-');

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


        txtP1.setText(fbPlayer1.getName() + " " + fbPlayer1.getLastName());
        txtP2.setText(fbPlayer2.getName() + " " + fbPlayer2.getLastName());

        Picasso.with(this).load(fbPlayer1.getUrlImage()).into(ivP1);
        Picasso.with(this).load(fbPlayer2.getUrlImage()).into(ivP2);

        txtRecord.setText(String.valueOf(record));

//        txtP1.setText(playerOld1.getFirstName() + " " + playerOld1.getLastName());
//        txtSalary.setText("$" + String.valueOf(playerOld1.getContract()) + "M");
//        ivP1.setImageResource(playerOld1.getImage());
//        txtP2.setText(playerOld2.getFirstName() + " " + playerOld2.getLastName());
//        ivP2.setImageResource(playerOld2.getImage());
    }

    private void createPlayers() {
        Player_old playerOld;
        playerOld = new Player_old(1, "Doncic", "Luka", 7.3f, (R.drawable.doncic));
        playerOlds.add(playerOld);
        playerOld = new Player_old(2, "James", "LeBron", 37.43f, (R.drawable.lebron));
        playerOlds.add(playerOld);
        playerOld = new Player_old(3, "Curry", "Stephen", 40.2f, (R.drawable.curry));
        playerOlds.add(playerOld);
        playerOld = new Player_old(4, "Durant", "Kevin", 37.2f, (R.drawable.durant));
        playerOlds.add(playerOld);
        playerOld = new Player_old(5, "Gasol", "Marc", 25.6f, (R.drawable.marc));
        playerOlds.add(playerOld);
        playerOld = new Player_old(6, "Siakam", "Pascal", 2.35f, (R.drawable.siakam));
        playerOlds.add(playerOld);
        playerOld = new Player_old(7, "Leonard", "Kawhi", 32.74f, (R.drawable.leonard));
        playerOlds.add(playerOld);
        playerOld = new Player_old(8, "Simmons", "Ben", 8.11f, (R.drawable.simmons));
        playerOlds.add(playerOld);
        playerOld = new Player_old(9, "Porzingis", "Kristaps", 27.28f, (R.drawable.porzingis));
        playerOlds.add(playerOld);
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
            case R.id.ivP2:
                //Toast.makeText(this, "PULSADO", Toast.LENGTH_SHORT).show();
                if (fbPlayer2.getIdPlayer() > fbPlayer1.getIdPlayer()) {
                    continueGame();
                } else {
                    finishGame();
                }
                break;

            case R.id.ivP1:
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
