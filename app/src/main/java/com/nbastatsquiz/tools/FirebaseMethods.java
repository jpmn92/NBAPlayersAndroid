package com.nbastatsquiz.tools;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.nbastatsquiz.GameActivity;
import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.FirebasePuntuacion;
import com.nbastatsquiz.fragments.FragmentoAutentificacion;
import com.nbastatsquiz.fragments.FragmentoLogin;
import com.nbastatsquiz.fragments.FragmentoMenu;
import com.nbastatsquiz.fragments.FragmentoRegister;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FirebaseMethods extends Activity {
    GameActivity gameActivity;
    FragmentoMenu fragmentoMenu;
    FragmentoRegister fragmentoRegister;
    FragmentoLogin fragmentoLogin;
    FragmentoAutentificacion fragmentoAutentificacion;
    Context context;
    Boolean processDone;
    DatabaseReference reference;
    FirebasePuntuacion fbPuntuacion;
    Bundle bundlePartida;
    int puntuacion;
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


    public void createFbPuntuacion(Bundle bundle) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        reference = FirebaseDatabase.getInstance().getReference().child("Puntuacion");

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();

        fbPuntuacion = new FirebasePuntuacion();
        fbPuntuacion.setPoints(bundle.getInt("puntos"));
        fbPuntuacion.setDate(currentDate);
        fbPuntuacion.setPerMode(bundle.getString("PerMode"));
        fbPuntuacion.setSeason(bundle.getString("Season"));
        fbPuntuacion.setSeasonType(bundle.getString("SeasonType"));
        fbPuntuacion.setStatCategory(bundle.getString("StatCategory"));
        fbPuntuacion.setImage(bundle.getString("image"));
        fbPuntuacion.setUid(uid);

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
    public void getTopPuntuaciones(Bundle paramsPartida) {

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

    public void registerUser(String email, String passwd, String username, String urlImage, Context RegisterContext) {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        mAuth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuth.getCurrentUser().getUid();
                    //AQUI CREAMOS LOS PARAMETROS A NUESTRO ANTOJO, POR EJEMPLO URL DE IMAGEN DEL USUARIO O LO QUE SEA
                    Map<String, Object> map = new HashMap<>();
                    map.put("uid", id);
                    map.put("email", email);
                    map.put("name", username);


                    reference.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {


                                //Una vez creado el usuario, le ponemos displayName
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


                            } else {
                                //no se ha creado correctamente
                                fragmentoRegister.setMensaje(fragmentoRegister.getString(R.string.error_ocurred));
                            }

                        }
                    });

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
                        //TODO: Inicias sesión correctamete
                        FirebaseUser user = mAuth.getCurrentUser();

                        String urlAvatar = user.getPhotoUrl().toString();

                        sessionManagement.saveSession(user.getDisplayName(), user.getEmail(), urlAvatar);
//                        Toast.makeText(loginContext, R.string.login_correcto + " " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        goToMenu(user.getDisplayName());


                    } else {
                        // If sign in fails, display a message to the user.
                        //TODO: Error en inicio de sesión
                        Toast.makeText(loginContext, getString(R.string.login_incorrecto), Toast.LENGTH_SHORT).show();

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


        }
        else {
//            //si viene de register que utilice su contexto
//            if (fragmentoRegister != null) {
//                fragmentoRegister.getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.main_content, goToMenu, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
//                message = fragmentoRegister.getString(R.string.registro_correcto);
//
//                Toast.makeText(fragmentoRegister.getContext(), message + ", " + userName, Toast.LENGTH_SHORT).show();
           // Toast.makeText(fragmentoRegister.getContext(), getString(R.string.login_incorrecto), Toast.LENGTH_SHORT).show();
//
           }
//        }
    }

    public void updateAvatar(String urlImage, Context avatarContext) {
        FirebaseAuth mAuth;
        sessionManagement = new SessionManagement(avatarContext);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser avatarUser = mAuth.getCurrentUser();


        UserProfileChangeRequest avatarUpdates = new UserProfileChangeRequest.Builder().
                setPhotoUri(Uri.parse(urlImage)).build();

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
//                        String namePuntuacion = fbPuntuacionList.get(i).getUsername();

                        if (uidActual.equals(uidPuntuacion)) {

                            DatabaseReference recordRef = reference.child(dataSnapshotArrayList.get(i).getKey());
                            Map<String, Object> photoUpdates = new HashMap<>();
                            photoUpdates.put("/image", String.valueOf(user.getPhotoUrl()));
                            recordRef.updateChildren(photoUpdates);
                        }


                    }

//                    for (FirebasePuntuacion firebasePuntuacionImage : fbPuntuacionList) {
//
//                        String uidActual = user.getUid();
//                        String uidPuntuacion = firebasePuntuacionImage.getUid();
//                        String namePuntuacion = firebasePuntuacionImage.getUsername();
//                        int pointPuntuacion = firebasePuntuacionImage.getPoints();
//
//
//                        if (uidActual.equals(uidPuntuacion)) {
//                            String h = "";
////                            DatabaseReference recordRef = reference.child(snapshot.getKey());
////                            Map<String, Object> photoUpdates = new HashMap<>();
////                            photoUpdates.put("/image", String.valueOf(user.getPhotoUrl()));
////                            recordRef.updateChildren(photoUpdates);
//                        }
//
//                    }


                    processDone = true;
//                    menu.setPuntuaciones(listadoFinal);
//                    menu.goToPuntuaciones();
//                    fragmentoMenu.setPuntuaciones(listadoFinal);
//                    fragmentoMenu.goToPuntuaciones();


                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
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


}