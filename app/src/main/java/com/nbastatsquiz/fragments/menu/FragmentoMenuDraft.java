package com.nbastatsquiz.fragments.menu;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nbastatsquiz.DraftActivity;
import com.nbastatsquiz.fragments.FragmentoPuntuaciones;
import com.nbastatsquiz.fragments.auth.FragmentoRegister;
import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.FirebasePuntuacion;
import com.nbastatsquiz.tools.FirebaseMethods;
import com.nbastatsquiz.tools.SessionManagement;

import java.util.ArrayList;
import java.util.Collections;


public class FragmentoMenuDraft extends Fragment {

    private static FragmentoMenuDraft fragmentoMenuDraft;

    Spinner sSeason, sTeam, sCollege;
    Button btnStart, btnRecords, btnReset;
    Resources res;
    FirebaseMethods firebaseMethods;
    String userName;
    SessionManagement sessionManagement;
    Bundle params, paramsDraft;
    Intent juego;
    ArrayList<FirebasePuntuacion> puntuaciones, puntuacionPeronal;
    ImageView ivSound, ivCrono, imagenPrincipal;


    public ArrayList<FirebasePuntuacion> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(ArrayList<FirebasePuntuacion> puntuaciones) {
        this.puntuaciones = puntuaciones;
    }

    public void setPuntuacionPeronal(ArrayList<FirebasePuntuacion> puntuacionPeronal){
        this.puntuacionPeronal = puntuacionPeronal;
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
        firebaseMethods = new FirebaseMethods(FragmentoMenuDraft.this);
        return view;
    }

    private void initComponents(View view) {

        sessionManagement = new SessionManagement(getContext());

        imagenPrincipal = view.findViewById(R.id.imageViewPrincipalDraft);
        sSeason = view.findViewById(R.id.spinnerSeasonsDraft);
        sTeam = view.findViewById(R.id.spinnerTeamDraft);
        sCollege = view.findViewById(R.id.spinnerCollegeDraft);
        btnStart = view.findViewById(R.id.btnStartDraft);
        btnRecords = view.findViewById(R.id.btnRecordsDraft);
        btnReset = view.findViewById(R.id.btnResetFilters);


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sCollege.setSelection(0);
                sTeam.setSelection(0);
                sSeason.setSelection(0);

            }
        });

        sCollege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (sCollege.getSelectedItemPosition() != 0) {
                    sSeason.setEnabled(false);
                    sTeam.setEnabled(false);

                } else {
                    sSeason.setEnabled(true);
                    sTeam.setEnabled(true);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (sTeam.getSelectedItemPosition() != 0) {
                    sSeason.setEnabled(false);
                    sCollege.setEnabled(false);
                } else {
                    sSeason.setEnabled(true);
                    sCollege.setEnabled(true);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection() == true) {


                    juego = new Intent(getActivity().getBaseContext(), DraftActivity.class);
                    params = new Bundle();

                    checkSession();


                } else {
                    Toast.makeText(getContext(), R.string.sin_conexion, Toast.LENGTH_SHORT).show();

                }
            }
        });


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

    }


//    private void checkSound() {
//
//        if (sessionManagement.getSound()) {
//            ivSound.setImageResource(R.drawable.volume_on);
//        } else {
//            ivSound.setImageResource(R.drawable.volume_off);
//        }
//    }
//
//    private void checkCrono() {
//
//        if(sessionManagement.getCrono()){
//            ivCrono.setImageResource(R.drawable.temp_on);
//        }
//        else{
//            ivCrono.setImageResource(R.drawable.temp_off);
//        }
//    }

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

        params.putParcelableArrayList("puntuacionPersonal", puntuacionPeronal);

        FragmentoPuntuaciones fragmentoPuntuaciones = FragmentoPuntuaciones.newInstance(params);


        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragmentoPuntuaciones, "findThisFragment")
                .addToBackStack(null)
                .commit();


    }

    public Bundle getParams() {

        Bundle paramsPartida = new Bundle();

//        if (sSeason.getSelectedItemPosition() == 0 && sCollege.getSelectedItemPosition() == 0 && sTeam.getSelectedItemPosition() == 0) {
//            Toast.makeText(getContext(), "Modo de juego prohibido", Toast.LENGTH_SHORT).show();
//            paramsPartida = null;
//        } else {

        //parametros del modo de juego

        paramsPartida.putString("modoJuego", "Draft");

        //TEMPORADA
        String temporada = "";


        if (sSeason.getSelectedItemPosition() == 0) {
            temporada = "MISC";

        } else {
            temporada = sSeason.getSelectedItem().toString();

        }

        //EQUIPO
        String team = sTeam.getSelectedItem().toString();

        String teamParam = "0";


        if(sTeam.getSelectedItemPosition() == 0){

            teamParam = "0";


        }else{
            switch (team) {
                case "Atlanta Hawks":

                    teamParam = "1610612737";

                    break;

                case "Boston Celtics":

                    teamParam = "1610612738";

                    break;

                case "Brooklyn Nets":

                    teamParam = "1610612751";

                    break;

                case "Charlotte Hornets":

                    teamParam = "1610612766";

                    break;

                case "Chicago Bulls":

                    teamParam = "1610612741";

                    break;

                case "Cleveland Cavaliers":

                    teamParam = "1610612739";

                    break;

                case "Dallas Mavericks":

                    teamParam = "1610612742";

                    break;

                case "Denver Nuggets":

                    teamParam = "1610612743";

                    break;

                case "Golden State Warriors":

                    teamParam = "1610612744";

                    break;
                case "Houston Rockets":

                    teamParam = "1610612745";

                    break;
                case "Indiana Pacers":

                    teamParam = "1610612754";

                    break;
                case "LA Clippers":

                    teamParam = "1610612746";

                    break;
                case "Los Angeles Lakers":

                    teamParam = "1610612747";

                    break;
                case "Memphis Grizzlies":

                    teamParam = "1610612763";

                    break;
                case "Miami Heat":

                    teamParam = "1610612748";

                    break;

                case "Milwaukee Bucks":

                    teamParam = "1610612749";

                    break;

                case "Minnesota Timberwolves":

                    teamParam = "1610612750";

                    break;

                case "New Orleans Pelicans":

                    teamParam = "1610612740";

                    break;


                case "New York Knicks":

                    teamParam = "1610612752";

                    break;
                case "Oklahoma City Thunder":

                    teamParam = "1610612760";

                    break;
                case "Orlando Magic":

                    teamParam = "1610612753";

                    break;
                case "Philadelphia 76ers":

                    teamParam = "1610612755";

                    break;
                case "Phoenix Suns":

                    teamParam = "1610612748";

                    break;

                case "Portland Trail Blazers":

                    teamParam = "1610612757";

                    break;
                case "Sacramento Kings":

                    teamParam = "1610612758";

                    break;
                case "San Antonio Spurs":

                    teamParam = "1610612759";

                    break;
                case "Toronto Raptors":

                    teamParam = "1610612761";

                    break;
                case "Utah Jazz":

                    teamParam = "1610612762";

                    break;

                case "Washington Wizards":

                    teamParam = "1610612764";

                    break;


            }
            temporada = "0";
        }


        paramsPartida.putString("Team", teamParam);

        //UNIVERSIDAD
        String college = "";

        if (sCollege.getSelectedItemPosition() == 0) {
            college = "0";
        } else {
            college = sCollege.getSelectedItem().toString();
            temporada = "0";

        }



        paramsPartida.putString("Season", temporada);


        paramsPartida.putString("College", college);




        return paramsPartida;


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


    private void checkSession() {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null) {
            if (!sessionManagement.getCrono()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.temporizador_off);
                builder.setMessage(R.string.aviso_temporizador);
                builder.setPositiveButton(R.string.empezar_partida, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userName = firebaseUser.getDisplayName();

                        paramsDraft = getParams();
                        paramsDraft.putBoolean("loged", true);
                        paramsDraft.putString("userName", userName);
                        juego.putExtra("miBundle", paramsDraft);
                        getActivity().startActivity(juego);

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
            else{
                userName = firebaseUser.getDisplayName();

                paramsDraft = getParams();
                paramsDraft.putBoolean("loged", true);
                paramsDraft.putString("userName", userName);
                juego.putExtra("miBundle", paramsDraft);
                getActivity().startActivity(juego);
            }
        } else {

            paramsDraft = getParams();
            //No logueados

            //le pedimos username y despues guardamos la sesion
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.registrarse);
            builder.setMessage(R.string.sugerencia_registro);
            builder.setPositiveButton(R.string.empezar_partida, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //userName = input.getText().toString();
                    paramsDraft.putBoolean("loged", false);
                    sessionManagement.saveSession(userName);
                    juego.putExtra("miBundle", paramsDraft);
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
