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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nbastatsquiz.GameActivity;
import com.nbastatsquiz.fragments.FragmentoPuntuaciones;
import com.nbastatsquiz.fragments.auth.FragmentoRegister;
import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.FirebasePuntuacion;
import com.nbastatsquiz.tools.FirebaseMethods;
import com.nbastatsquiz.tools.SessionManagement;

import java.util.ArrayList;
import java.util.Collections;


public class FragmentoMenu extends Fragment {

    private static FragmentoMenu fragmentoMenu;

    Spinner sSeason, sCategory, sSeasonType, sDataType, sLiga;
    Button btnStart, btnRecords;
    Resources res;
    FirebaseMethods firebaseMethods;
    String userName;
    SessionManagement sessionManagement;
    Bundle params;
    boolean sound, crono;
    Intent juego, draft, ch;
    ArrayList<FirebasePuntuacion> puntuaciones;
    ImageView ivSound, ivCrono, imagenPrincipal;
    ArrayAdapter<String> stringArrayAdapterNBA, stringArrayAdapterWNBA, stringArrayAdapterGLEAGUE;


    public ArrayList<FirebasePuntuacion> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(ArrayList<FirebasePuntuacion> puntuaciones) {
        this.puntuaciones = puntuaciones;
    }

    public FragmentoMenu() {
        // Required empty public constructor
    }


    public static FragmentoMenu newInstance(Bundle datos) {
        if (fragmentoMenu == null) {
            fragmentoMenu =
                    new FragmentoMenu();
        }

        if (datos != null) {
            fragmentoMenu.setArguments(datos);
        }
        return fragmentoMenu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_menu, container, false);


        initComponents(view);
        res = getResources();
        firebaseMethods = new FirebaseMethods(this);
        return view;
    }

    private void initComponents(View view) {


        ivSound = view.findViewById(R.id.ivSound);
        ivSound.setClickable(true);
        ivCrono = view.findViewById(R.id.ivCrono);
        ivCrono.setClickable(true);
        sessionManagement = new SessionManagement(getContext());
        sound = sessionManagement.getSound();
        crono = sessionManagement.getCrono();

        stringArrayAdapterNBA = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Temporadas));
        stringArrayAdapterNBA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stringArrayAdapterWNBA = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.TemporadasWNBA));
        stringArrayAdapterWNBA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stringArrayAdapterGLEAGUE = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.TemporadasGLEAGUE));
        stringArrayAdapterGLEAGUE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        ivSound = view.findViewById(R.id.ivSound);
        checkSound();
        checkCrono();

        //dependiendo de si es true pintamos una imagen u otra


        ivSound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("");
                if (sound) {
                    sound = false;
                    sessionManagement.saveSession(sound, "sound"); //NUEVO
                    checkSound();
                } else {
                    sound = true;
                    sessionManagement.saveSession(sound, "sound"); //NUEVO
                    checkSound();
                }
            }
        });

        ivCrono.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (crono) {
                    crono = false;
                    sessionManagement.saveSession(crono, "crono"); //NUEVO
                    checkCrono();
                } else {
                    crono = true;
                    sessionManagement.saveSession(crono, "crono"); //NUEVO
                    checkCrono();
                }
            }
        });

                imagenPrincipal = view.findViewById(R.id.imageViewPrincipal);
        sSeason = view.findViewById(R.id.spinnerSeasons);
        sCategory = view.findViewById(R.id.spinnerCategory);
        sSeasonType = view.findViewById(R.id.spinnerSeasonType);
        sDataType = view.findViewById(R.id.spinnerDataType);
        btnStart = view.findViewById(R.id.btnStart);
        btnRecords = view.findViewById(R.id.btnRecords);
        sLiga = view.findViewById(R.id.spinnerLiga);

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


        sLiga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(sLiga.getSelectedItemPosition() == 0){

                    sSeason.setAdapter(stringArrayAdapterNBA);
                    imagenPrincipal.setImageResource(R.drawable.logo_ligero);


                }

                if(sLiga.getSelectedItemPosition() == 1){


                    sSeason.setAdapter(stringArrayAdapterWNBA);
                    imagenPrincipal.setImageResource(R.drawable.wnba_logo);



                }

                if(sLiga.getSelectedItemPosition() == 2){


                    sSeason.setAdapter(stringArrayAdapterGLEAGUE);
                    imagenPrincipal.setImageResource(R.drawable.gleague_logo);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void checkSound() {

        if(sessionManagement.getSound()){
            ivSound.setImageResource(R.drawable.volume_on);
        }
        else{
            ivSound.setImageResource(R.drawable.volume_off);
        }
    }

    private void checkCrono() {

        if(sessionManagement.getCrono()){
            ivCrono.setImageResource(R.drawable.temp_on);
        }
        else{
            ivCrono.setImageResource(R.drawable.temp_off);
        }
    }

    public void goToRegister() {

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
        params.putString("modoJuego", "Stats");

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
        paramsPartida.putString("modoJuego", "Stats");

        paramsPartida.putString("liga", sLiga.getSelectedItem().toString());

        paramsPartida.putString("Season", temporada);
        paramsPartida.putString("SeasonType", sSeasonType.getSelectedItem().toString()); //Playoffs

        String stat = getParam(R.array.TipoCategoria, sCategory);

        paramsPartida.putString("StatCategory", stat); //PTS para puntos
        // params.putString("StatCategory", sCategory.getSelectedItem().toString()); //PTS para puntos


        // String statMode = res.getResourceEntryName(resIds[(sDataType.getSelectedItemPosition())]);
        String statMode = getParam(R.array.TipoDatos, sDataType);
        paramsPartida.putString("PerMode", statMode); //PerGame para por partido
//                params.putString("PerMode", "PerGame"); //PerGame para por partido

        paramsPartida.putString("ActiveFlag", "No"); //si se activa solo aparecen jugadores en activo

//        paramsPartida.putBoolean("Sound", sound);

        return paramsPartida;


    }

    public void onClick(View v) {

        if (checkInternetConnection() == true) {
            switch (v.getId()) {
                case R.id.btnStart:
                    juego = new Intent(getActivity().getBaseContext(), GameActivity.class);
                    params = new Bundle();

                    //parametros del modo de juego
                    String temporada = sSeason.getSelectedItem().toString();

                    if (sSeason.getSelectedItemPosition() == 0) {
                        temporada = "MISC";
                    }



                    params.putString("liga", sLiga.getSelectedItem().toString());


                    params.putString("Season", temporada);
                    params.putString("SeasonType", sSeasonType.getSelectedItem().toString()); //Playoffs

                    String stat = getParam(R.array.TipoCategoria, sCategory);

                    params.putString("StatCategory", stat); //PTS para puntos
                    // params.putString("StatCategory", sCategory.getSelectedItem().toString()); //PTS para puntos


                    // String statMode = res.getResourceEntryName(resIds[(sDataType.getSelectedItemPosition())]);
                    String statMode = getParam(R.array.TipoDatos, sDataType);
                    params.putString("PerMode", statMode); //PerGame para por partido
//                params.putString("PerMode", "PerGame"); //PerGame para por partido

                    params.putString("ActiveFlag", "No"); //si se activa solo aparecen jugadores en activo

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
            if (!crono) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.temporizador_off);
                builder.setMessage(R.string.aviso_temporizador);
                builder.setPositiveButton(R.string.empezar_partida, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        params.putBoolean("loged", true);
                        params.putBoolean("sound", sound); //NUEVO
                        params.putBoolean("crono", crono);
                        sessionManagement.saveSession(userName);

                        juego.putExtras(params);

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
                params.putBoolean("loged", true);
                params.putBoolean("sound", sound);//NUEVO
                params.putBoolean("crono", crono);//NUEVO
                juego.putExtras(params);
                getActivity().startActivity(juego);
            }

        } else {

            //No logueados
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.registrarse);
            builder.setMessage(R.string.sugerencia_registro);
            builder.setPositiveButton(R.string.empezar_partida, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    params.putBoolean("loged", false);
                    params.putBoolean("sound", sound); //NUEVO
                    params.putBoolean("crono", crono);
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
