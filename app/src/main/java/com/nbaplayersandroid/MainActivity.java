package com.nbaplayersandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nbaplayersandroid.beans.FirebasePlayer;
import com.nbaplayersandroid.beans.FirebasePlayerList;
import com.nbaplayersandroid.beans.PlayerSeasonStats;
import com.nbaplayersandroid.beans.BasketballPlayerList;
import com.nbaplayersandroid.lst_players_season_stats.LstPlayerSeasonStatsContract;
import com.nbaplayersandroid.lst_players_season_stats.LstPlayerSeasonStatsPresenter;
import com.nbaplayersandroid.tools.FirebaseReferences;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
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
    ImageButton buttonMas;
    ImageButton buttonMenos;
    ImageView ivP1;
    ImageView ivP2;

    LstPlayerSeasonStatsPresenter lstPlayerSeasonStatsPresenter;
    ArrayList<PlayerSeasonStats> lstPlayers;

    DatabaseReference reference;
    FirebasePlayer fbPlayer;

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
        buttonMas = findViewById(R.id.buttonMas);
        buttonMas.setOnClickListener(this);
        buttonMenos = findViewById(R.id.buttonMenos);
        buttonMenos.setOnClickListener(this);
        ivP1 = findViewById(R.id.ivP1);
        ivP2 = findViewById(R.id.ivP2);

        lstPlayerSeasonStatsPresenter = new LstPlayerSeasonStatsPresenter(this);
        lstPlayers = new ArrayList<>();


        createPlayers();
        startGame();
        getFbPlayer();


    }

    private void getFbPlayer() {

        ArrayList<FirebasePlayer> list = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.JUGADORES_REFERENCE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Object object = snapshot.getValue(Object.class);
                    String json = new Gson().toJson(object);
                    FirebasePlayer fbPlayer = new Gson().fromJson(json, FirebasePlayer.class);



                    String name = (String) dataSnapshot.child(FirebaseReferences.JUGADORES_REFERENCE).child("name").getValue();

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
        fbPlayer.setIdAPI(132);
        fbPlayer.setIdPlayer(1);
        fbPlayer.setLastName("Doncic");
        fbPlayer.setName("Luka");
        fbPlayer.setUrlImage("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612742/2019/260x190/1629029.png");

        reference.push().setValue(fbPlayer);
    }

    private void startGame() {
        record = 0;
        int random = 0;
        random = (int) (Math.random() * playerOlds.size());
        playerOld1 = playerOlds.get(random);
        selectPlayer2();
    }

    private void selectPlayer2() {
        int random = 0;
        do {
            random = (int) (Math.random() * playerOlds.size());
            playerOld2 = playerOlds.get(random);
        } while (playerOld1.getId() - 1 == playerOld2.getId() - 1 || playerOld1.getContract() == playerOld2.getContract());

        txtP1.setText(playerOld1.getFirstName() + " " + playerOld1.getLastName());
        txtSalary.setText("$" + String.valueOf(playerOld1.getContract()) + "M");
        ivP1.setImageResource(playerOld1.getImage());
        txtP2.setText(playerOld2.getFirstName() + " " + playerOld2.getLastName());
        ivP2.setImageResource(playerOld2.getImage());
        txtRecord.setText(String.valueOf(record));
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


    }

    private void continueGame() {
        record++;
        playerOld1 = playerOld2;
        selectPlayer2();

    }

    @Override
    public void onClick(View v) {


//        createFbPlayer();
        lstPlayerSeasonStatsPresenter.getPlayers();

        switch (v.getId()) {
            case R.id.buttonMas:
                //Toast.makeText(this, "PULSADO", Toast.LENGTH_SHORT).show();
                if (playerOld2.getContract() > playerOld1.getContract()) {
                    continueGame();
                } else {
                    finishGame();
                }
                break;

            case R.id.buttonMenos:
                //Toast.makeText(this, "PULSADO", Toast.LENGTH_SHORT).show();
                if (playerOld2.getContract() < playerOld1.getContract()) {
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
