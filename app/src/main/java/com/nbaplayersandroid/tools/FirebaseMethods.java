package com.nbaplayersandroid.tools;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.nbaplayersandroid.MainActivity;
import com.nbaplayersandroid.beans.FirebasePuntuacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class FirebaseMethods

{
    MainActivity mainActivity;
    DatabaseReference reference;
    FirebasePuntuacion fbPuntuacion;
    Bundle bundlePartida;
    int puntuacion;

    public FirebaseMethods() {
        this.puntuacion = 0;
    }

    public FirebaseMethods(MainActivity mainActivity,Bundle bundlePartida) {
        this.mainActivity = mainActivity;
        this.bundlePartida = bundlePartida;
        this.puntuacion = 0;
    }

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

        fbPuntuacion.setUsername(bundle.getString("username"));


        reference.push().setValue(fbPuntuacion);

    }

    public void getRecord(){
        ArrayList<FirebasePuntuacion> fbPuntuacionList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Puntuacion");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Object object = snapshot.getValue(Object.class);
                    String json = new Gson().toJson(object);
                    FirebasePuntuacion fbTeam = new Gson().fromJson(json, FirebasePuntuacion.class);
                    fbPuntuacionList.add(fbTeam);
                }

                for(FirebasePuntuacion firebasePuntuacion: fbPuntuacionList){
                    boolean datosIguales = firebasePuntuacion.getPerMode().equalsIgnoreCase(bundlePartida.getString("PerMode")) && firebasePuntuacion.getSeason().equalsIgnoreCase(bundlePartida.getString("Season"))
                            && firebasePuntuacion.getStatCategory().equalsIgnoreCase(bundlePartida.getString("StatCategory")) && firebasePuntuacion.getSeasonType().equalsIgnoreCase(bundlePartida.getString("SeasonType"))
                            && firebasePuntuacion.getUsername().equalsIgnoreCase(bundlePartida.getString("userName"));
                    if(datosIguales && firebasePuntuacion.getPoints() > puntuacion){
                        puntuacion  = firebasePuntuacion.getPoints();
                    }
                }
                mainActivity.setRecord(puntuacion);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
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


    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
