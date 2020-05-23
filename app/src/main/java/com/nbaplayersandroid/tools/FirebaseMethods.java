package com.nbaplayersandroid.tools;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.nbaplayersandroid.beans.FirebasePuntuacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FirebaseMethods

{

    DatabaseReference reference;
    FirebasePuntuacion fbPuntuacion;



//    private void getFbPlayers() {
//        reference = FirebaseDatabase.getInstance().getReference().child("Jugador");
//        fbPlayerList = new ArrayList<FirebasePlayer>();
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//
//                    Object object = snapshot.getValue(Object.class);
//                    String json = new Gson().toJson(object);
//                    FirebasePlayer fbPlayer = new Gson().fromJson(json, FirebasePlayer.class);
//                    fbPlayerList.add(fbPlayer);
//                }
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//    }

    public void createFbPuntuacion(Bundle bundle) {


        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        reference = FirebaseDatabase.getInstance().getReference().child("Puntuacion");


        fbPuntuacion = new FirebasePuntuacion();
        fbPuntuacion.setPoints(bundle.getInt("puntos"));
        fbPuntuacion.setDate(currentDate);
        fbPuntuacion.setPerMode(bundle.getString("PerMode"));
        fbPuntuacion.setSeason(bundle.getString("Season"));
        fbPuntuacion.setSeasonType(bundle.getString("SeasonType"));
        fbPuntuacion.setStatCategory(bundle.getString("StatCategory"));


//        fbPuntuacion.setPerMode("Per Game");
//        fbPuntuacion.setSeasonType("Regular Season");
//        fbPuntuacion.setStatCategory("PTS");
        fbPuntuacion.setUsername(bundle.getString("username"));


        reference.push().setValue(fbPuntuacion);

    }



//    private void getFbTeams() {
//        reference = FirebaseDatabase.getInstance().getReference().child("Equipo");
//        fbTeamList = new ArrayList<FirebaseTeam>();
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//
//                    Object object = snapshot.getValue(Object.class);
//                    String json = new Gson().toJson(object);
//                    FirebaseTeam fbTeam = new Gson().fromJson(json, FirebaseTeam.class);
//                    fbTeamList.add(fbTeam);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//    }




//    private void createFbTeam() {
//
//        reference = FirebaseDatabase.getInstance().getReference().child("Equipo");
//
//        fbTeam = new FirebaseTeam();
//        fbTeam.setIdAPI(2);
//        fbTeam.setIdTeam(2);
//        fbTeam.setName("Boston Celtics");
//        fbTeam.setUrlImage("https://stats.nba.com/media/img/teams/logos/BOS_logo.svg");
//        fbTeam.setUrlBackground("-");
//        reference.push().setValue(fbTeam);
////        (2, 'Boston Celtics', 2, 'https://stats.nba.com/media/img/teams/logos/BOS_logo.svg','-'),
//
//    }
}
