package com.nbastatsquiz.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nbastatsquiz.DraftActivity;
import com.nbastatsquiz.GameActivity;
import com.nbastatsquiz.pruebas.Main3Activity_pruebaDraft;
import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.FirebasePuntuacion;
import com.nbastatsquiz.pruebas.Main4Activity_pruebaCH;
import com.nbastatsquiz.tools.FirebaseMethods;
import com.nbastatsquiz.tools.SessionManagement;

import java.util.ArrayList;
import java.util.Collections;


public class FragmentoMenuDraft extends Fragment {

    private static FragmentoMenuDraft fragmentoMenuDraft;

    Spinner sSeason, sTeam, sCollege;
    Button btnStart, btnRecords;
    Resources res;
    FirebaseMethods firebaseMethods;
    String userName;
    SessionManagement sessionManagement;
    Bundle params;
    Intent juego, draft, ch;
    ArrayList<FirebasePuntuacion> puntuaciones;
    ImageView ivSound, imagenPrincipal;

    public ArrayList<FirebasePuntuacion> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(ArrayList<FirebasePuntuacion> puntuaciones) {
        this.puntuaciones = puntuaciones;
    }

    public FragmentoMenuDraft() {
        // Required empty public constructor
    }


    public static FragmentoMenuDraft newInstance(Bundle datos) {
        if (fragmentoMenuDraft == null) {
            fragmentoMenuDraft =
                    new FragmentoMenuDraft();
        }

        if (datos != null) {
            fragmentoMenuDraft.setArguments(datos);
        }
        return fragmentoMenuDraft;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_menu_draft, container, false);


        initComponents(view);
        res = getResources();
//        firebaseMethods = new FirebaseMethods(this);
        return view;
    }

    private void initComponents(View view) {

        sessionManagement = new SessionManagement(getContext());
        ivSound = view.findViewById(R.id.ivSoundDraft);
        checkSound();
        ivSound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (sessionManagement.getSound()) {
                    sessionManagement.saveSession(false);
                } else {
                    sessionManagement.saveSession(true);
                }
                checkSound();
            }
        });

        imagenPrincipal = view.findViewById(R.id.imageViewPrincipalDraft);
        sSeason = view.findViewById(R.id.spinnerSeasonsDraft);
        sTeam = view.findViewById(R.id.spinnerTeamDraft);
        sCollege = view.findViewById(R.id.spinnerCollegeDraft);
        btnStart = view.findViewById(R.id.btnStartDraft);
        btnRecords = view.findViewById(R.id.btnRecordsDraft);

        btnStart.setOnClickListener(this::onClick);


        btnRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                firebaseMethods.createFbPuntuacion();

//                sessionManagement = new SessionManagement(getBaseContext());
//                sessionManagement.removeSession();

                if (checkInternetConnection() == true) {

                    firebaseMethods.getTopPuntuaciones(getParams());


                } else {
                    Toast.makeText(getContext(), R.string.sin_conexion, Toast.LENGTH_SHORT).show();

                }


            }
        });

        imagenPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                draft = new Intent(getActivity().getBaseContext(), Main3Activity_pruebaDraft.class);
//                ch = new Intent(getActivity().getBaseContext(), Main4Activity_pruebaCH.class);
//
//                getActivity().startActivity(ch);


            }
        });

    }


    private void checkSound() {

        if (sessionManagement.getSound()) {
            ivSound.setImageResource(R.drawable.volume_on);
        } else {
            ivSound.setImageResource(R.drawable.volume_off);
        }
    }

    public void goToRegister() {
        // puntuaciones.sort();


        FragmentoRegister fragmentoRegister = FragmentoRegister.newInstance(null);


        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragmentoRegister, "findThisFragment")
                .addToBackStack(null)
                .commit();


//        Intent activityPuntuaciones = new Intent(getContext(), PuntuacionesActivity.class);
//        activityPuntuaciones.putExtra("puntuaciones", puntuaciones);
//        startActivity(activityPuntuaciones);

    }

    public void goToPuntuaciones() {
        // puntuaciones.sort();

        params = getParams();

        params.putString("userName", getUserName());

        Collections.sort(puntuaciones, Collections.reverseOrder());

        params.putParcelableArrayList("puntuaciones", puntuaciones);

        FragmentoPuntuaciones fragmentoPuntuaciones = FragmentoPuntuaciones.newInstance(params);


        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragmentoPuntuaciones, "findThisFragment")
                .addToBackStack(null)
                .commit();


    }

    public Bundle getParams() {

        Bundle paramsPartida = new Bundle();

        //parametros del modo de juego
        String temporada = sSeason.getSelectedItem().toString();

        if (sSeason.getSelectedItemPosition() == 0) {
            temporada = "MISC";
        }

        paramsPartida.putString("Season", temporada);
        String team = getParam(R.array.NBA_Teams, sTeam);
        paramsPartida.putString("Team", team); //PerGame para por partido

        String college = getParam(R.array.Colleges, sCollege);
        paramsPartida.putString("College", college);


        return paramsPartida;


    }

    public void onClick(View v) {

        if (checkInternetConnection() == true) {
            switch (v.getId()) {
                case R.id.btnStartDraft:
                    juego = new Intent(getActivity().getBaseContext(), DraftActivity.class);
                    params = new Bundle();

                    //parametros del modo de juego
                    String temporada = sSeason.getSelectedItem().toString();

                    if (sSeason.getSelectedItemPosition() == 0) {
                        temporada = "MISC";
                    }

                    params.putString("Season", temporada);

                    String team = getParam(R.array.NBA_Teams, sTeam);

                    params.putString("Team", team);


                    String college = getParam(R.array.NBA_Teams, sCollege);
                    params.putString("College", college);

//                    params.putString("ActiveFlag", "No"); //si se activa solo aparecen jugadores en activo

//                params.putBoolean("Sound", sound);

                    checkSession();
                    break;


            }

        } else {
            Toast.makeText(getContext(), R.string.sin_conexion, Toast.LENGTH_SHORT).show();

        }


    }

    private String getUserName() {
        int userID = sessionManagement.getSession();

        if (userID != -1) {
            userName = sessionManagement.getSessionUserName();

        } else {
            userName = "";

        }
        return userName;
    }

    public String getParam(int arrayId, Spinner spinner) {
        TypedArray resourceIDS = res.obtainTypedArray(arrayId);
        int[] resIds = new int[resourceIDS.length()];
        for (int i = 0; i < resourceIDS.length(); i++) {
            resIds[i] = resourceIDS.getResourceId(i, -1);
        }
        resourceIDS.recycle();
        String getParam = res.getResourceEntryName(resIds[(spinner.getSelectedItemPosition())]);

        return getParam;
    }

    private void checkSession() {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null) {
            userName = firebaseUser.getDisplayName();
            params.putBoolean("loged", true);
            juego.putExtras(params);
            getActivity().startActivity(juego);
        } else {

            //No logueados

            //le pedimos username y despues guardamos la sesion
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.registrarse);
            builder.setMessage(R.string.sugerencia_registro);
            builder.setPositiveButton(R.string.empezar_partida, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //userName = input.getText().toString();
                    params.putBoolean("loged", false);
                    sessionManagement.saveSession(userName);
                    juego.putExtras(params);
                    getActivity().startActivity(juego);

                }
            });
            builder.setNegativeButton(R.string.registrarse, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    goToRegister();

                }
            });

            builder.show();

        }
    }

    private Boolean checkInternetConnection() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;


        } else {
            connected = false;
//            Toast.makeText(getContext(), "no hay conexion a internet", Toast.LENGTH_SHORT).show();
        }

        return connected;

    }
}
