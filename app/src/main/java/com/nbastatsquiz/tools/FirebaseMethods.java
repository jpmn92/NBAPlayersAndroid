package com.nbastatsquiz.tools;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.nbastatsquiz.DraftActivity;
import com.nbastatsquiz.GameActivity;
import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.Codigo;
import com.nbastatsquiz.beans.FirebasePuntuacion;
import com.nbastatsquiz.fragments.FragmentoAccount;
import com.nbastatsquiz.fragments.auth.FragmentoAutentificacion;
import com.nbastatsquiz.fragments.auth.FragmentoLogin;
import com.nbastatsquiz.fragments.menu.FragmentoMenu;
import com.nbastatsquiz.fragments.auth.FragmentoRegister;
import com.nbastatsquiz.fragments.menu.FragmentoMenuDraft;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class FirebaseMethods extends Activity {

    private final int LIMITE_PUNTUACIONES = 100;

    GameActivity gameActivity;
    DraftActivity draftActivity;
    FragmentoMenu fragmentoMenu;
    FragmentoMenuDraft fragmentoMenuDraft;

    FragmentoRegister fragmentoRegister;
    FragmentoLogin fragmentoLogin;
    FragmentoAutentificacion fragmentoAutentificacion;
    Context context;
    Boolean processDone;
    DatabaseReference reference;
    FirebasePuntuacion fbPuntuacion;
    Bundle bundlePartida;
    int puntuacion, puntosTotales;
    ArrayList<FirebasePuntuacion> listadoFinal;
    SessionManagement sessionManagement;

    public FirebaseMethods() {
        this.puntuacion = 0;
    }

    public FirebaseMethods(GameActivity gameActivity, Bundle bundlePartida) {
        this.gameActivity = gameActivity;
        this.bundlePartida = bundlePartida;
        this.puntuacion = 0;
    }

    public FirebaseMethods(DraftActivity draftActivity, Bundle bundlePartida) {
        this.draftActivity = draftActivity;
        this.bundlePartida = bundlePartida;
        this.puntuacion = 0;
    }

    public FirebaseMethods(Context context) {
        this.context = context;
    }

    public FirebaseMethods(FragmentoRegister fragmentoRegister) {
        this.fragmentoRegister = fragmentoRegister;
    }

    public FirebaseMethods(FragmentoAutentificacion fragmentoAutentificacion) {
        this.fragmentoAutentificacion = fragmentoAutentificacion;
    }

    public FirebaseMethods(FragmentoLogin fragmentoLogin) {
        this.fragmentoLogin = fragmentoLogin;
    }

    public FirebaseMethods(FragmentoMenu menu) {
        this.fragmentoMenu = menu;
    }

    public FirebaseMethods(FragmentoMenuDraft menuDraft) {
        this.fragmentoMenuDraft = menuDraft;
    }


    public void createFbPuntuacion(Bundle bundle) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String hour = "";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime  currentHour = LocalDateTime.now();
            hour = currentHour.getHour() + ":" + currentHour.getMinute() + ":" + currentHour.getSecond();
        }

        reference = FirebaseDatabase.getInstance().getReference().child("Puntuacion");

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();


        fbPuntuacion = new FirebasePuntuacion();

        if (bundle.getString("modoJuego").equalsIgnoreCase("Stats")) {
            fbPuntuacion.setPoints(bundle.getInt("puntos"));
            fbPuntuacion.setDate(currentDate);
            fbPuntuacion.setHour(hour);
            fbPuntuacion.setPerMode(bundle.getString("PerMode"));
            fbPuntuacion.setSeason(bundle.getString("Season"));
            fbPuntuacion.setSeasonType(bundle.getString("SeasonType"));
            fbPuntuacion.setStatCategory(bundle.getString("StatCategory"));
            fbPuntuacion.setImage(bundle.getString("image"));
            fbPuntuacion.setUid(uid);
            fbPuntuacion.setUsername(bundle.getString("userName"));
            fbPuntuacion.setModoJuego(bundle.getString("modoJuego"));
            fbPuntuacion.setLiga(bundle.getString("liga"));

        }

        if (bundle.getString("modoJuego").equalsIgnoreCase("Draft")) {
            fbPuntuacion.setPoints(bundle.getInt("puntos"));
            fbPuntuacion.setDate(currentDate);
            fbPuntuacion.setHour(hour);
            fbPuntuacion.setImage(bundle.getString("image"));
            fbPuntuacion.setUid(uid);
            fbPuntuacion.setUsername(bundle.getString("userName"));

            if (bundle.getString("Team").equals("") || bundle.getString("Team") == null) {
                String draftTeam = "0";
                fbPuntuacion.setDraftTeam(draftTeam);
            } else {
                fbPuntuacion.setDraftTeam(bundle.getString("Team"));
            }

            if (bundle.getString("College").equals("") || bundle.getString("College") == null) {
                String draftCollege = "0";
                fbPuntuacion.setDraftCollege(draftCollege);
            } else {
                fbPuntuacion.setDraftCollege(bundle.getString("College"));
            }

            if (bundle.getString("Season").equals("") || bundle.getString("Season") == null) {
                String season = "0";
                fbPuntuacion.setSeason(season);
            } else {
                fbPuntuacion.setSeason(bundle.getString("Season"));
            }


            fbPuntuacion.setModoJuego(bundle.getString("modoJuego"));

        }


        //draft

//        if(bundle.getString("draftTeam").equals("") || bundle.getString("draftTeam") == null){
//            String draftTeam = "0";
//            fbPuntuacion.setDraftTeam(draftTeam);
//        }else{
//            fbPuntuacion.setDraftTeam(bundle.getString("draftTeam"));
//        }
//
//        if(bundle.getString("draftCollege").equals("") || bundle.getString("draftCollege") == null){
//            String draftCollege = "0";
//            fbPuntuacion.setDraftCollege(draftCollege);
//        }else{
//            fbPuntuacion.setDraftCollege(bundle.getString("draftCollege"));
//        }
//
//        if(bundle.getString("Season").equals("") || bundle.getString("Season") == null){
//            String season = "0";
//            fbPuntuacion.setSeason(season);
//        }else{
//            fbPuntuacion.setSeason(bundle.getString("Season"));
//        }
//
//
//        fbPuntuacion.setModoJuego(bundle.getString("modoJuego"));


        reference.push().setValue(fbPuntuacion);

    }

    public void getRecord() {

        String modoJuego = bundlePartida.getString("modoJuego");

        ArrayList<FirebasePuntuacion> mArrayList = new ArrayList<>();
//        reference = FirebaseDatabase.getInstance().getReference().child("Puntuacion");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        CollectionReference puntuacionesRef = db.collection("Puntuacion");

        puntuacionesRef.whereEqualTo("season", bundlePartida.getString("Season"))
                .whereEqualTo("perMode", bundlePartida.getString("PerMode"))
                .whereEqualTo("liga", bundlePartida.getString("liga"))
                .whereEqualTo("seasonType", bundlePartida.getString("SeasonType"))
                .whereEqualTo("statCategory", bundlePartida.getString("StatCategory"))
                .whereEqualTo("modoJuego", modoJuego)
                .whereEqualTo("uid", mAuth.getUid()).whereGreaterThan("points", -1)
                .orderBy("points", Query.Direction.DESCENDING).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Convert the whole Query Snapshot to a list
                // of objects directly! No need to fetch each
                // document.
                List<FirebasePuntuacion> fbPuntuaciones = queryDocumentSnapshots.toObjects(FirebasePuntuacion.class);

                if(fbPuntuaciones.size() > 0 ) {
                    puntuacion = fbPuntuaciones.get(0).getPoints();
                }

                else {
                    puntuacion = 0;
                }

                if(modoJuego.equalsIgnoreCase("Draft")){
                    draftActivity.setRecord(puntuacion);
                }
                if(modoJuego.equalsIgnoreCase("Stats")){
                    gameActivity.setRecord(puntuacion);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });
    }



    //SI LO HACEMOS ARRAYLIST NO VA
    public void getTopPuntuaciones(Bundle bundle) {

        String modoJuego = bundle.getString("modoJuego");

        ArrayList<FirebasePuntuacion> mArrayList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        CollectionReference puntuacionesRef = db.collection("Puntuacion");

        if(modoJuego.equalsIgnoreCase("stats")){
            puntuacionesRef.whereEqualTo("season", bundle.getString("Season"))
                    .whereEqualTo("perMode", bundle.getString("PerMode"))
                    .whereEqualTo("liga", bundle.getString("liga"))
                    .whereEqualTo("seasonType", bundle.getString("SeasonType"))
                    .whereEqualTo("statCategory", bundle.getString("StatCategory"))
                    .whereEqualTo("modoJuego", modoJuego)
                    .whereGreaterThan("points", -1)
                    .orderBy("points", Query.Direction.DESCENDING).limit(LIMITE_PUNTUACIONES).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    // Convert the whole Query Snapshot to a list
                    // of objects directly! No need to fetch each
                    // document.
                    List<FirebasePuntuacion> fbPuntuaciones = queryDocumentSnapshots.toObjects(FirebasePuntuacion.class);

                    // Add all to your list
                    mArrayList.addAll(fbPuntuaciones);
                    Log.d("TAG", "onSuccess: " + mArrayList);

                    if (modoJuego.equalsIgnoreCase("Draft")) {
                        fragmentoMenuDraft.setPuntuaciones(mArrayList);
                    }

                    if (modoJuego.equalsIgnoreCase("Stats")) {
                        fragmentoMenu.setPuntuaciones(mArrayList);
                    }

                    getPersonalRecordFS(bundle);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                }
            });
        } else if(modoJuego.equalsIgnoreCase("draft")){
            puntuacionesRef.whereEqualTo("season", bundle.getString("Season"))
                    .whereEqualTo("draftTeam", bundle.getString("Team"))
                    .whereEqualTo("draftCollege", bundle.getString("College"))
                    .whereEqualTo("modoJuego", modoJuego)
                    .whereGreaterThan("points", -1)
                    .orderBy("points", Query.Direction.DESCENDING).limit(LIMITE_PUNTUACIONES).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    // Convert the whole Query Snapshot to a list
                    // of objects directly! No need to fetch each
                    // document.
                    List<FirebasePuntuacion> fbPuntuaciones = queryDocumentSnapshots.toObjects(FirebasePuntuacion.class);

                    // Add all to your list
                    mArrayList.addAll(fbPuntuaciones);
                    Log.d("TAG", "onSuccess: " + mArrayList);

                    if (modoJuego.equalsIgnoreCase("Draft")) {
                        fragmentoMenuDraft.setPuntuaciones(mArrayList);
                    }

                    if (modoJuego.equalsIgnoreCase("Stats")) {
                        fragmentoMenu.setPuntuaciones(mArrayList);
                    }

                    getPersonalRecordFS(bundle);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                }
            });
        }



    }

    public void registerUser(String email, String passwd, String username, String urlImage, Context RegisterContext) {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        mAuth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuth.getCurrentUser().getUid();

                    FirebaseUser myUser = mAuth.getCurrentUser();

//                                myUser.sendEmailVerification();


                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username).setPhotoUri(Uri.parse(urlImage)).build();

                    myUser.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task3) {
                                    if (task3.isSuccessful()) {
                                        //USERNAME ACTUALIZADO
                                        String OK = "OK";
                                        //TODO: loguear de otra manera mas limpia
                                        logIn(myUser, RegisterContext);


                                    } else {
                                        //no se le ha asignado el username
                                        String NOOK = "NOOK";
                                    }
                                }
                            });

                    //AQUI CREAMOS LOS PARAMETROS A NUESTRO ANTOJO, POR EJEMPLO URL DE IMAGEN DEL USUARIO O LO QUE SEA
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("uid", id);
//                    map.put("email", email);
//                    map.put("name", username);


//                    reference.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task2) {
//                            if (task2.isSuccessful()) {
//
//
//                                //Una vez creado el usuario, le ponemos displayName
//                                FirebaseUser myUser = mAuth.getCurrentUser();
//
////                                myUser.sendEmailVerification();
//
//
//                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                        .setDisplayName(username).setPhotoUri(Uri.parse(urlImage)).build();
//
//                                myUser.updateProfile(profileUpdates)
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task3) {
//                                                if (task3.isSuccessful()) {
//                                                    //USERNAME ACTUALIZADO
//                                                    String OK = "OK";
//                                                    //TODO: loguear de otra manera mas limpia
//                                                    logIn(myUser, RegisterContext);
//
//
//                                                } else {
//                                                    //no se le ha asignado el username
//                                                    String NOOK = "NOOK";
//                                                }
//                                            }
//                                        });
//
//
//                            } else {
//                                //no se ha creado correctamente
//                                fragmentoRegister.setMensaje(fragmentoRegister.getString(R.string.error_ocurred));
//                            }
//
//                        }
//                    });

                } else {

                    fragmentoRegister.mostrarError(fragmentoRegister.getString(R.string.already_registred));
//                    no se ha podido crear el usuario

                }
            }
        });
    }

    public void logIn(String email, String password, Context loginContext) {
        FirebaseAuth mAuth;

        sessionManagement = new SessionManagement(loginContext);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        try {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        //LOGIN CORRECTO
                        FirebaseUser user = mAuth.getCurrentUser();

                        String urlAvatar = user.getPhotoUrl().toString();

                        sessionManagement.saveSession(user.getDisplayName(), user.getEmail(), urlAvatar);
//                        Toast.makeText(loginContext, R.string.login_correcto + " " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        goToMenu(user.getDisplayName());


                    } else {
                        // If sign in fails, display a message to the user.
                        //LOGIN INCORRECTO
                        //Toast.makeText(loginContext, getApplicationContext().getString(R.string.login_incorrecto), Toast.LENGTH_SHORT).show();
                        fragmentoLogin.getTxtErrorLogIn().setText(loginContext.getString(R.string.login_incorrecto));
                        fragmentoLogin.getTxtErrorLogIn().setVisibility(View.VISIBLE);
                    }

                }
            });
        } catch (IllegalArgumentException e) {
            Toast.makeText(fragmentoLogin.getContext(), "Campos de texto vacios", Toast.LENGTH_SHORT).show();
        }

    }

    public void logIn(FirebaseUser myUser, Context loginContext) {
        sessionManagement = new SessionManagement(loginContext);
        reference = FirebaseDatabase.getInstance().getReference();
        sessionManagement.saveSession(myUser.getDisplayName(), myUser.getEmail(), myUser.getPhotoUrl().toString());


        goToMenu(myUser.getDisplayName());


    }

    public void goToMenu(String userName) {
        FragmentoMenu goToMenu = FragmentoMenu.newInstance(null);

        String message = "";
        //si viene de login que utilice su contexto

        if (fragmentoAutentificacion != null) {


            fragmentoAutentificacion.getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, goToMenu, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
            message = fragmentoAutentificacion.getString(R.string.login_correcto);
            Toast.makeText(fragmentoAutentificacion.getContext(), message + ", " + userName, Toast.LENGTH_SHORT).show();


        } else {

            String prueba = "";
//            //si viene de register que utilice su contexto
            if (fragmentoRegister != null) {
                fragmentoRegister.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, goToMenu, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                message = fragmentoRegister.getString(R.string.registro_correcto);

                Toast.makeText(fragmentoRegister.getContext(), message + ", " + userName, Toast.LENGTH_SHORT).show();
            // Toast.makeText(fragmentoRegister.getContext(), getString(R.string.login_incorrecto), Toast.LENGTH_SHORT).show();
//
        }

            if (fragmentoLogin != null) {
                fragmentoLogin.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, goToMenu, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                message = fragmentoLogin.getString(R.string.registro_correcto);

                Toast.makeText(fragmentoLogin.getContext(), message + ", " + userName, Toast.LENGTH_SHORT).show();
                // Toast.makeText(fragmentoRegister.getContext(), getString(R.string.login_incorrecto), Toast.LENGTH_SHORT).show();
//
            }//        }
    }}

    public void updateUser(String userName, String urlImage, Context avatarContext) {
        FirebaseAuth mAuth;
        sessionManagement = new SessionManagement(avatarContext);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser avatarUser = mAuth.getCurrentUser();


        UserProfileChangeRequest avatarUpdates = new UserProfileChangeRequest.Builder().
                setPhotoUri(Uri.parse(urlImage)).setDisplayName(userName).build();

        avatarUser.updateProfile(avatarUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task3) {
                        if (task3.isSuccessful()) {
                            //AVATAR ACTUALIZADO
                            Toast.makeText(context, R.string.config_updated, Toast.LENGTH_SHORT).show();
                            changeImageRecord(avatarUser);


                        } else {
                            //no se le ha asignado el avatar
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
    }

    private void changeImageRecord(FirebaseUser user) {
        processDone = false;
        ArrayList<FirebasePuntuacion> fbPuntuacionList = new ArrayList<>();
        ArrayList<DataSnapshot> dataSnapshotArrayList = new ArrayList<>();
        FirebasePuntuacion miPuntacion;
        listadoFinal = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference puntuacionesRef = db.collection("Puntuacion");

        puntuacionesRef
                .whereEqualTo("uid", user.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Convert the whole Query Snapshot to a list
                // of objects directly! No need to fetch each
                // document.

                for(QueryDocumentSnapshot querySnapshot: queryDocumentSnapshots){
                    Map puntuacionCambio = new HashMap<String, Object>();
                    puntuacionCambio.put("image", String.valueOf(user.getPhotoUrl()));
                    puntuacionCambio.put("username", String.valueOf(user.getDisplayName()));
                    db.collection("Puntuacion").document(querySnapshot.getId()).update(puntuacionCambio);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });

    }

    //METODO PARA RECOGER LAS IMAGENES DE LOS USUARIOS
    public void getUser(String email) {


    }

    private void toastAutentificacion(String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }


    public void getUserAverage(FirebaseUser user) {
        processDone = false;
        puntosTotales = 0;
        ArrayList<FirebasePuntuacion> fbPuntuacionList = new ArrayList<>();
        ArrayList<FirebasePuntuacion> fbMisPuntuaciones = new ArrayList<>();

        ArrayList<DataSnapshot> dataSnapshotArrayList = new ArrayList<>();
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
                        dataSnapshotArrayList.add(dataSnapshot.child(snapshot.getKey()));


                    }

                    for (int i = 0; i < fbPuntuacionList.size(); i++) {

                        fbPuntuacionList.get(i);

                        String uidActual = user.getUid();
                        String uidPuntuacion = fbPuntuacionList.get(i).getUid();

                        if (uidActual.equals(uidPuntuacion)) {

                            fbMisPuntuaciones.add(fbPuntuacionList.get(i));
                            puntosTotales = puntosTotales + fbPuntuacionList.get(i).getPoints();


                        }


                    }

                    double media = (puntosTotales / fbMisPuntuaciones.size());


                    processDone = true;


                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        });
    }

    public void readCode(String codigo, FragmentoAccount fragmentoAccount){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        final String[] url = {""};

        CollectionReference codigosRef = db.collection("Codigo");

        codigosRef.whereEqualTo("codigo", codigo)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Codigo> codigos = queryDocumentSnapshots.toObjects(Codigo.class);
                if(codigos.size() > 0){
                    url[0] = codigos.get(0).getUrl();

                    if(!"".equalsIgnoreCase(url[0])){
                        fragmentoAccount.urlCode(url[0]);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });
    }


    //METODOS POST FIRESTORE


    public void migracionPuntuaciones() {
        ArrayList<FirebasePuntuacion> fbPuntuacionList = new ArrayList<>();
        listadoFinal = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        processDone = false;
        reference = FirebaseDatabase.getInstance().getReference().child("Puntuacion");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (!processDone) {

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Object object = snapshot.getValue(Object.class);
                        String json = new Gson().toJson(object);
                        FirebasePuntuacion fbPuntuacion = new Gson().fromJson(json, FirebasePuntuacion.class);
//                        fbPuntuacionList.add(fbPuntuacion);

                        db.collection("Puntuacion")
                                .add(fbPuntuacion)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error adding document", e);
                                    }
                                });

                    }





                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Object object = snapshot.getValue(Object.class);
                        String json = new Gson().toJson(object);
                        FirebasePuntuacion fbPuntuacion = new Gson().fromJson(json, FirebasePuntuacion.class);
                        fbPuntuacionList.add(fbPuntuacion);
                    }

                    for (FirebasePuntuacion firebasePuntuacion : fbPuntuacionList) {
                        Map<String, Object> docData = new HashMap<>();
                        docData.put("puntuacion", firebasePuntuacion.getPoints());
                        db.collection("Puntuacion").add(docData);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void createFbPuntuacionFS(Bundle bundle) {


        //TODO: SER CAPACES DE PASAR LA HORA EN TIMESTAMP

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String hour = "";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime  currentHour = LocalDateTime.now();
            hour = currentHour.getHour() + ":" + currentHour.getMinute() + ":" + currentHour.getSecond();
        }

        //NUEVO FORMATO DE FECHA
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String formatedDate = simpleDateFormat.format(new Date());

        // Map<String, String> timestamp = ServerValue.TIMESTAMP;
        Timestamp timestamp = new Timestamp(new Date());


        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();


        fbPuntuacion = new FirebasePuntuacion();

        if (bundle.getString("modoJuego").equalsIgnoreCase("Stats")) {
            fbPuntuacion.setPoints(bundle.getInt("puntos"));
            fbPuntuacion.setDate(currentDate);
            fbPuntuacion.setHour(hour);
            fbPuntuacion.setPerMode(bundle.getString("PerMode"));
            fbPuntuacion.setSeason(bundle.getString("Season"));
            fbPuntuacion.setSeasonType(bundle.getString("SeasonType"));
            fbPuntuacion.setStatCategory(bundle.getString("StatCategory"));
            fbPuntuacion.setImage(bundle.getString("image"));
            fbPuntuacion.setUid(uid);
//            fbPuntuacion.setUsername(bundle.getString("userName"));
            fbPuntuacion.setUsername(bundle.getString("userName"));
            fbPuntuacion.setModoJuego(bundle.getString("modoJuego"));
            fbPuntuacion.setLiga(bundle.getString("liga"));
            fbPuntuacion.setTimestamp(timestamp);

        }

        if (bundle.getString("modoJuego").equalsIgnoreCase("Draft")) {
            fbPuntuacion.setPoints(bundle.getInt("puntos"));
            fbPuntuacion.setDate(currentDate);
            fbPuntuacion.setHour(hour);
            fbPuntuacion.setImage(bundle.getString("image"));
            fbPuntuacion.setUid(uid);
            fbPuntuacion.setUsername(bundle.getString("userName"));
            fbPuntuacion.setTimestamp(timestamp);


            if (bundle.getString("Team").equals("") || bundle.getString("Team") == null) {
                String draftTeam = "0";
                fbPuntuacion.setDraftTeam(draftTeam);
            } else {
                fbPuntuacion.setDraftTeam(bundle.getString("Team"));
            }

            if (bundle.getString("College").equals("") || bundle.getString("College") == null) {
                String draftCollege = "0";
                fbPuntuacion.setDraftCollege(draftCollege);
            } else {
                fbPuntuacion.setDraftCollege(bundle.getString("College"));
            }

            if (bundle.getString("Season").equals("") || bundle.getString("Season") == null) {
                String season = "0";
                fbPuntuacion.setSeason(season);
            } else {
                fbPuntuacion.setSeason(bundle.getString("Season"));
            }


            fbPuntuacion.setModoJuego(bundle.getString("modoJuego"));

        }



        db.collection("Puntuacion")
                .add(fbPuntuacion)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
//        reference.push().setValue(fbPuntuacion);

    }


    public void getPersonalRecordFS(Bundle bundle) {

        String modoJuego = bundle.getString("modoJuego");

        ArrayList<FirebasePuntuacion> mArrayList = new ArrayList<>();
//        reference = FirebaseDatabase.getInstance().getReference().child("Puntuacion");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        CollectionReference puntuacionesRef = db.collection("Puntuacion");

        puntuacionesRef.whereEqualTo("season", bundle.getString("Season"))
                .whereEqualTo("perMode", bundle.getString("PerMode"))
                .whereEqualTo("liga", bundle.getString("liga"))
                .whereEqualTo("seasonType", bundle.getString("SeasonType"))
                .whereEqualTo("statCategory", bundle.getString("StatCategory"))
                .whereEqualTo("modoJuego", modoJuego)
                .whereEqualTo("uid", mAuth.getUid()).whereGreaterThan("points", -1)
                .orderBy("points", Query.Direction.DESCENDING).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    // Convert the whole Query Snapshot to a list
                    // of objects directly! No need to fetch each
                    // document.
                    List<FirebasePuntuacion> fbPuntuaciones = queryDocumentSnapshots.toObjects(FirebasePuntuacion.class);

                    // Add all to your list
                    mArrayList.addAll(fbPuntuaciones);
                    Log.d("TAG", "onSuccess: " + mArrayList);

                    if (modoJuego.equalsIgnoreCase("Draft")) {
                        fragmentoMenuDraft.setPuntuacionPeronal(mArrayList);
                        fragmentoMenuDraft.goToPuntuaciones();
                    }

                    if (modoJuego.equalsIgnoreCase("Stats")) {
                        fragmentoMenu.setPuntuacionPersonal(mArrayList);
                        fragmentoMenu.goToPuntuaciones();
                    }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });

    }

}