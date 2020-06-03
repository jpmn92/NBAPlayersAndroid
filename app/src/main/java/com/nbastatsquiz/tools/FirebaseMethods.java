package com.nbastatsquiz.tools;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.nbastatsquiz.GameActivity;
import com.nbastatsquiz.beans.FirebasePuntuacion;
import com.nbastatsquiz.fragments.FragmentoMenu;
import com.nbastatsquiz.menu.Menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FirebaseMethods {
    GameActivity gameActivity;
    Menu menu;
    FragmentoMenu fragmentoMenu;

    Boolean processDone;
    DatabaseReference reference;
    FirebasePuntuacion fbPuntuacion;
    Bundle bundlePartida;
    int puntuacion;
    ArrayList<FirebasePuntuacion> listadoFinal;

    public FirebaseMethods() {
        this.puntuacion = 0;
    }

    public FirebaseMethods(GameActivity gameActivity, Bundle bundlePartida) {
        this.gameActivity = gameActivity;
        this.bundlePartida = bundlePartida;
        this.puntuacion = 0;
    }

    public FirebaseMethods(Menu menu) {
        this.menu = menu;

    }

    public FirebaseMethods(FragmentoMenu menu) {
        this.fragmentoMenu = menu;
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

        fbPuntuacion.setUsername(bundle.getString("userName"));


        reference.push().setValue(fbPuntuacion);

    }

    public void getRecord() {
        ArrayList<FirebasePuntuacion> fbPuntuacionList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Puntuacion");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Object object = snapshot.getValue(Object.class);
                    String json = new Gson().toJson(object);
                    FirebasePuntuacion fbPuntuacion = new Gson().fromJson(json, FirebasePuntuacion.class);
                    fbPuntuacionList.add(fbPuntuacion);
                }

                for (FirebasePuntuacion firebasePuntuacion : fbPuntuacionList) {
                    boolean datosIguales = firebasePuntuacion.getPerMode().equalsIgnoreCase(bundlePartida.getString("PerMode")) && firebasePuntuacion.getSeason().equalsIgnoreCase(bundlePartida.getString("Season"))
                            && firebasePuntuacion.getStatCategory().equalsIgnoreCase(bundlePartida.getString("StatCategory")) && firebasePuntuacion.getSeasonType().equalsIgnoreCase(bundlePartida.getString("SeasonType"))
                            && firebasePuntuacion.getUsername().equalsIgnoreCase(bundlePartida.getString("userName"));

                    if (datosIguales && firebasePuntuacion.getPoints() > puntuacion) {
                        puntuacion = firebasePuntuacion.getPoints();
                    }
                }
                gameActivity.setRecord(puntuacion);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }

    //SI LO HACEMOS ARRAYLIST NO VA
    public void getRecord2(Bundle paramsPartida) {

        processDone = false;

        ArrayList<FirebasePuntuacion> fbPuntuacionList = new ArrayList<>();
        listadoFinal = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Puntuacion");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (!processDone) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Object object = snapshot.getValue(Object.class);
                        String json = new Gson().toJson(object);
                        FirebasePuntuacion fbPuntuacion = new Gson().fromJson(json, FirebasePuntuacion.class);
                        fbPuntuacionList.add(fbPuntuacion);
                    }

                    for (FirebasePuntuacion firebasePuntuacion : fbPuntuacionList) {
//                    boolean datosIguales = firebasePuntuacion.getPerMode().equalsIgnoreCase(bundlePartida.getString("PerMode")) && firebasePuntuacion.getSeason().equalsIgnoreCase(bundlePartida.getString("Season"))
//                            && firebasePuntuacion.getStatCategory().equalsIgnoreCase(bundlePartida.getString("StatCategory")) && firebasePuntuacion.getSeasonType().equalsIgnoreCase(bundlePartida.getString("SeasonType"))
//                            && firebasePuntuacion.getUsername().equalsIgnoreCase(bundlePartida.getString("userName"));

                        if (
                                firebasePuntuacion.getPerMode().equalsIgnoreCase(paramsPartida.getString("PerMode"))
                                        && firebasePuntuacion.getSeason().equalsIgnoreCase(paramsPartida.getString("Season"))
                                        && firebasePuntuacion.getStatCategory().equalsIgnoreCase(paramsPartida.getString("StatCategory"))
                                        && firebasePuntuacion.getSeasonType().equalsIgnoreCase(paramsPartida.getString("SeasonType"))) {

                            listadoFinal.add(firebasePuntuacion);


                        }


                    }
                    processDone = true;
//                    menu.setPuntuaciones(listadoFinal);
//                    menu.goToPuntuaciones();
                    fragmentoMenu.setPuntuaciones(listadoFinal);
                    fragmentoMenu.goToPuntuaciones();


                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        });


    }

    public void registerUser(String email, String passwd) {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        mAuth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    //AQUI CREAMOS LOS PARAMETROS A NUESTRO ANTOJO, POR EJEMPLO URL DE IMAGEN DEL USUARIO O LO QUE SEA
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", email);
                    map.put("password", passwd);

                    String id = mAuth.getCurrentUser().getUid();

                    reference.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {

                                String o = "esta registrado correctamente";
                                //AQUI LO SUYO SERIA LLEVARLE AL MENU O ALGUNA COSA QUE DEMOSTRARA QUE SE HA REGISTRADO


                            } else {
                                //no se ha creado correctamente
                            }

                        }
                    });

                } else {

//                    no se ha podido crear el usuario

                }
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
