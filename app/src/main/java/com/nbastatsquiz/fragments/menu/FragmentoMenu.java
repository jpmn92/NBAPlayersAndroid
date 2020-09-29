package com.nbastatsquiz.fragments.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.testing.FakeReviewManager;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.nbastatsquiz.GameActivity;
import com.nbastatsquiz.fragments.FragmentoPuntuaciones;
import com.nbastatsquiz.fragments.auth.FragmentoRegister;
import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.FirebasePuntuacion;
import com.nbastatsquiz.tools.FirebaseMethods;
import com.nbastatsquiz.tools.SessionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hotchemi.android.rate.AppRate;


public class FragmentoMenu extends Fragment {

    private static FragmentoMenu fragmentoMenu;

    public boolean concurso;

    Spinner sSeason, sCategory, sSeasonType, sDataType, sLiga;
    Button btnStart, btnRecords;
    Resources res;
    FirebaseMethods firebaseMethods;
    String userName, selectedSeason;
    SessionManagement sessionManagement;
    Bundle params;
    boolean sound, crono, loged;
    Intent juego, draft, ch;
    ArrayList<FirebasePuntuacion> puntuaciones, puntuacionPersonal;
    ImageView imagenPrincipal;
    ArrayAdapter<String> stringArrayAdapterNBA, stringArrayAdapterWNBA, stringArrayAdapterGLEAGUE;


    //REMOTE CONFIG
    int version; //version de nuestra app
    FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
    List<String> temporadasNBASorteo;
    ReviewManager rwManager;
    ReviewInfo reviewInfo;


    public ArrayList<FirebasePuntuacion> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(ArrayList<FirebasePuntuacion> puntuaciones) {
        this.puntuaciones = puntuaciones;
    }

    public void setPuntuacionPersonal(ArrayList<FirebasePuntuacion> puntuacionPersonal) {
        this.puntuacionPersonal = puntuacionPersonal;
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


    public void compruebaConcurso(){

        sessionManagement = new SessionManagement(getContext());
        concurso = sessionManagement.getConcurso();



    }
    //en el onresume comprobamos que esta en la ultima version
    @Override
    public void onResume() {
        super.onResume();


        comprobacionUltimaVersion();
        compruebaConcurso();


    }

    public void onStart() {
        super.onStart();
        compruebaConcurso();

//        comprobacionModoSorteo();

    }

//    private void comprobacionModoSorteo() {
//
//        remoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(true).build());
//        Task<Void> recogerInfoFbRc = remoteConfig.fetch(0);
//        recogerInfoFbRc.addOnSuccessListener((Activity) getContext(), aVoid -> {
//
//            remoteConfig.activateFetched();
//            gestionSorteo();
//
//        });
//
//    }
//
//    private void gestionSorteo() {
//
//        boolean sorteoActivo = remoteConfig.getBoolean("sorteoActivo");
//        concurso = sorteoActivo;
//        temporadasNBASorteo = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.Temporadas)));
//
//
//        //AQUI LA LOGICA DE SI EL SORTEO ESTA ACTIVO
//        if (concurso) {
//
////            temporadasNBASorteo.add(0, "MODO CONCURSO");
//
//
////            Toast.makeText(getContext(), "SORTEO ACTIVO - " + sorteoActivo, Toast.LENGTH_SHORT).show();
//
//
//        } else {
//            Toast.makeText(getContext(), "NO HAY SORTEO - " + sorteoActivo, Toast.LENGTH_SHORT).show();
//
//        }
//
//    }


    //metodo que comprueba la ultima version
    private void comprobacionUltimaVersion() {


        //comprobaciones de version
        PackageInfo packageInfo;

        try {
            packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version = packageInfo.versionCode; //version actual instalada

        } catch (Exception e) {
            e.printStackTrace();
        }

        //configuracion firebase remote config

        remoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(true).build());
        HashMap<String, Object> actualizacion = new HashMap<>();
        actualizacion.put("versioncode", version);
        Task<Void> recogerInfoFbRc = remoteConfig.fetch(0);
        recogerInfoFbRc.addOnSuccessListener((Activity) getContext(), aVoid -> {

            remoteConfig.activateFetched();
            checkVersion(version);

        });

    }

    //metodo que compara las versiones
    private void checkVersion(int version) {

        int newerVersion = (int) remoteConfig.getLong("versioncode");
        String urlApp = remoteConfig.getString("web");
        String newerVersionName = remoteConfig.getString("versionname");
//        boolean sorteoActivo =  remoteConfig.getBoolean("sorteoActivo");

        if (newerVersion > version) {
            mostrarDialogVersion(urlApp, newerVersionName);

//            Toast.makeText(getContext(), "Existe una nueva version", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(getContext(), "NO HAY VERSIONES DISPONIBLES ", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        comprobacionModoSorteo();
        compruebaConcurso();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_menu, container, false);


        //rating

        AppRate.with(this.getContext())
                .setInstallDays(2) // minimo dos dias de la instalacion
                .setLaunchTimes(3) //minimo haberla abierto 3 veces
                .setRemindInterval(2) // si le da a recordar mas tarde pasan X dias
                .monitor();

        try {
            AppRate.showRateDialogIfMeetsConditions(this.getActivity());

        } catch (Exception e) {

        }

        //INAPP RATING

//        rwManager = ReviewManagerFactory.create(getContext());
//        com.google.android.play.core.tasks.Task<ReviewInfo> request = rwManager.requestReviewFlow();
//
//        request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
//            @Override
//            public void onComplete(@NonNull com.google.android.play.core.tasks.Task<ReviewInfo> task) {
//
//                if(task.isSuccessful()){
//                    reviewInfo = task.getResult();
//                    com.google.android.play.core.tasks.Task<Void> flow = rwManager.launchReviewFlow(getActivity(), reviewInfo);
//
//                    flow.addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void result) {
//
//                        }
//                    });
//                }else{
//                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });


        initComponents(view);
        res = getResources();
        firebaseMethods = new FirebaseMethods(this);

        return view;
    }

    public void ProgressManagement() {

        com.google.android.play.core.tasks.Task<Void> flow = rwManager.launchReviewFlow(this.getActivity(), reviewInfo);
        flow.addOnCompleteListener(task -> {
            Log.d("myTag", "Review process completed");
        });
    }

    //metodo que salta si no estas en la version mas moderna
    private void mostrarDialogVersion(String urlApp, String versionName) {

        new AlertDialog.Builder(getContext())
                .setTitle("Actualización Disponible")
                .setMessage("¿Quieres Actualizar NBA STATS QUIZ a la versión " + versionName + "?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // si damos a aceptar

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getActivity().getPackageName())));
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                        }

                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void initComponents(View view) {

//        sessionManagement = new SessionManagement(getContext());
        concurso = sessionManagement.getConcurso();
        sound = sessionManagement.getSound();
        crono = sessionManagement.getCrono();
        temporadasNBASorteo = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.Temporadas)));

        if (concurso) {

//            Toast.makeText(getContext(), "HAY SORTEO MENU " + concurso, Toast.LENGTH_SHORT).show();


            //SI HAY CONCURSO, CARGA EL LISTADO AÑADIENDOLE EL MODO CONCURSO
            temporadasNBASorteo.add(0, "CONCURSO \uD83C\uDF81");


            stringArrayAdapterNBA = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, temporadasNBASorteo);
            stringArrayAdapterNBA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //temporadas intactas
            stringArrayAdapterWNBA = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.TemporadasWNBA));
            stringArrayAdapterWNBA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stringArrayAdapterGLEAGUE = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.TemporadasGLEAGUE));
            stringArrayAdapterGLEAGUE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        } else {

//            Toast.makeText(getContext(), "NO HAY SORTEO MENU " + concurso, Toast.LENGTH_SHORT).show();

            //SI NO HAY CONCURSO, CARGA LAS TEMPORADAS NORMALES
            stringArrayAdapterNBA = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Temporadas));
            stringArrayAdapterNBA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stringArrayAdapterWNBA = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.TemporadasWNBA));
            stringArrayAdapterWNBA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stringArrayAdapterGLEAGUE = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.TemporadasGLEAGUE));
            stringArrayAdapterGLEAGUE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }


        imagenPrincipal = view.findViewById(R.id.imageViewPrincipal);
        sSeason = view.findViewById(R.id.spinnerSeasons);
        sCategory = view.findViewById(R.id.spinnerCategory);
        sSeasonType = view.findViewById(R.id.spinnerSeasonType);
        sDataType = view.findViewById(R.id.spinnerDataType);
        btnStart = view.findViewById(R.id.btnStart);
        btnRecords = view.findViewById(R.id.btnRecords);
        sLiga = view.findViewById(R.id.spinnerLiga);

        btnStart.setOnClickListener(this::onClick);

        //SI HAY CONCURSO ACTIVO PONEMOS LISTENER QUE DESACTIVA LAS DEMAS OPCIONES
        if(concurso){
            sSeason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(sLiga.getSelectedItemPosition() == 0){

                        if (sSeason.getSelectedItemPosition() == 0) {

                            sLiga.setEnabled(false);
                            sCategory.setEnabled(false);
                            sSeasonType.setEnabled(false);
                            sDataType.setEnabled(false);


                        }else{
                            sLiga.setEnabled(true);
                            sCategory.setEnabled(true);
                            sSeasonType.setEnabled(true);
                            sDataType.setEnabled(true);
                        }
                    }else{
                        sLiga.setEnabled(true);
                        sCategory.setEnabled(true);
                        sSeasonType.setEnabled(true);
                        sDataType.setEnabled(true);
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        btnRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkInternetConnection() == true) {
                    ;
                    firebaseMethods.getTopPuntuaciones(getParams());
//                    firebaseMethods.getUserTop100_Admin("XqcUJErnXsNz0HGeQC3A1I0aRbG3");


                } else {
                    Toast.makeText(getContext(), R.string.sin_conexion, Toast.LENGTH_SHORT).show();

                }


            }
        });


        sLiga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (sLiga.getSelectedItemPosition() == 0) {

                    sSeason.setAdapter(stringArrayAdapterNBA);
                    imagenPrincipal.setImageResource(R.drawable.logo_ligero);


                }

                if (sLiga.getSelectedItemPosition() == 1) {


                    sSeason.setAdapter(stringArrayAdapterWNBA);
                    imagenPrincipal.setImageResource(R.drawable.wnba_logo);


                }

                if (sLiga.getSelectedItemPosition() == 2) {


                    sSeason.setAdapter(stringArrayAdapterGLEAGUE);
                    imagenPrincipal.setImageResource(R.drawable.gleague_logo);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void pruebaCrearPuntuacion() {
        firebaseMethods.createFbPuntuacionFS(getParams());
    }

    private void pruebaRecogerPuntuacion() {
        firebaseMethods.getPersonalRecordFS(getParams());
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

        params.putParcelableArrayList("puntuacionPersonal", puntuacionPersonal);

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
        if (sSeason.getSelectedItemPosition() == 1) {
            temporada = "All Time";
        } else if (sSeason.getSelectedItemPosition() == 0) {
            temporada = "MISC";
        }

        if(concurso && sLiga.getSelectedItemPosition() == 0){
            if (sSeason.getSelectedItemPosition() == 0) {
                temporada = "CONCURSO";
                selectedSeason = "CONCURSO";
            }
            if (sSeason.getSelectedItemPosition() == 1) {
                temporada = "MISC";
                selectedSeason = "MISC";
            }
            if(sSeason.getSelectedItemPosition() == 2){
                temporada = "All Time";
                selectedSeason = "All Time";

            }
        }else{

            if (sSeason.getSelectedItemPosition() == 0) {
                temporada = "MISC";
                selectedSeason = "MISC";
            }
            if (sSeason.getSelectedItemPosition() == 1) {
                temporada = "All Time";
                selectedSeason = "All Time";
            }
        }

        paramsPartida.putBoolean("loged", loged);
        paramsPartida.putBoolean("sound", sound);//NUEVO
        paramsPartida.putBoolean("crono", crono);//NUEVO
        paramsPartida.putBoolean("concurso", concurso);


        paramsPartida.putString("modoJuego", "Stats");

        if (temporada.equals("CONCURSO") && sLiga.getSelectedItemPosition() == 0) {

            paramsPartida.putString("liga", "NBA");
            paramsPartida.putString("Season", "CONCURSO");
            paramsPartida.putString("SeasonType", "Regular Season");
            paramsPartida.putString("StatCategory", "MISC");
            paramsPartida.putString("PerMode", "PerGame");
            paramsPartida.putString("ActiveFlag", "No"); //si se activa solo aparecen jugadores en activo

        }else{
            String stat = getParam(R.array.TipoCategoria, sCategory);



            paramsPartida.putString("liga", sLiga.getSelectedItem().toString());

            paramsPartida.putString("Season", temporada);
            paramsPartida.putString("SeasonType", sSeasonType.getSelectedItem().toString()); //Playoffs


            paramsPartida.putString("StatCategory", stat); //PTS para puntos
            // params.putString("StatCategory", sCategory.getSelectedItem().toString()); //PTS para puntos


            // String statMode = res.getResourceEntryName(resIds[(sDataType.getSelectedItemPosition())]);
            String statMode = getParam(R.array.TipoDatos, sDataType);
            paramsPartida.putString("PerMode", statMode); //PerGame para por partido
//                params.putString("PerMode", "PerGame"); //PerGame para por partido

            paramsPartida.putString("ActiveFlag", "No"); //si se activa solo aparecen jugadores en activo

//        paramsPartida.putBoolean("Sound", sound);
        }





        return paramsPartida;


    }

    // LISTENER BOTON COMENZAR
    public void onClick(View v) {

        if (checkInternetConnection() == true) {
            switch (v.getId()) {
                case R.id.btnStart:
                    juego = new Intent(getActivity().getBaseContext(), GameActivity.class);
                    // params = new Bundle();
                    params = getParams();

                    //parametros del modo de juego
                    String temporada = sSeason.getSelectedItem().toString();

                    String stat = getParam(R.array.TipoCategoria, sCategory);


                    if(concurso && sLiga.getSelectedItemPosition() == 0){
                        if (sSeason.getSelectedItemPosition() == 0) {
                            temporada = "CONCURSO";
                            selectedSeason = "CONCURSO";
                        }
                        if (sSeason.getSelectedItemPosition() == 1) {
                            temporada = "MISC";
                            selectedSeason = "MISC";
                        }
                        if(sSeason.getSelectedItemPosition() == 2){
                            temporada = "All Time";
                            selectedSeason = "All Time";

                        }
                    }else{

                        if (sSeason.getSelectedItemPosition() == 0) {
                            temporada = "MISC";
                            selectedSeason = "MISC";
                        }
                        if (sSeason.getSelectedItemPosition() == 1) {
                            temporada = "All Time";
                            selectedSeason = "All Time";
                        }
                    }



                    if (temporada.equals("CONCURSO") && sLiga.getSelectedItemPosition() == 0) {

                        params.putString("liga", "NBA");
                        params.putString("Season", "CONCURSO");
                        params.putString("SeasonType", "Regular Season");
                        params.putString("StatCategory", "MISC");
                        params.putString("PerMode", "PerGame");
                        params.putString("ActiveFlag", "No"); //si se activa solo aparecen jugadores en activo

                    } else {
                        params.putString("liga", sLiga.getSelectedItem().toString());
                        params.putString("Season", temporada);
                        params.putString("SeasonType", sSeasonType.getSelectedItem().toString()); //Playoffs
                        params.putString("StatCategory", "MISC"); //PTS para puntos
                        String statMode = getParam(R.array.TipoDatos, sDataType);
                        params.putString("PerMode", statMode); //PerGame para por partido
                        params.putString("ActiveFlag", "No"); //si se activa solo aparecen jugadores en activo

                    }


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

        crono = sessionManagement.getCrono();
        sound = sessionManagement.getSound();
        if (firebaseUser != null) {

            loged = true;

            if (!crono) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.temporizador_off);
                builder.setMessage(R.string.aviso_temporizador);
                builder.setPositiveButton(R.string.empezar_partida, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        params.putBoolean("loged", loged);
                        params.putBoolean("sound", sound); //NUEVO
                        params.putBoolean("crono", crono);
                        params.putBoolean("concurso", concurso);
                        sessionManagement.saveSession(userName);

                        juego.putExtras(getParams());

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
            } else {
                userName = firebaseUser.getDisplayName();
                params.putBoolean("loged", true);
                params.putBoolean("sound", sound);//NUEVO
                params.putBoolean("crono", crono);//NUEVO
                juego.putExtras(getParams());
                getActivity().startActivity(juego);
            }

        } else {

            //No logueados
            loged = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.registrarse);
            builder.setMessage(R.string.sugerencia_registro);
            builder.setPositiveButton(R.string.empezar_partida, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    params.putBoolean("loged", loged);
                    params.putBoolean("sound", sound); //NUEVO
                    params.putBoolean("crono", crono);
                    params.putBoolean("concurso", concurso);
                    sessionManagement.saveSession(userName);


                    juego.putExtras(getParams());

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

    private void buscarRecord() {
        if (params.getBoolean("loged") && crono) {
            firebaseMethods.getRecord();
        }

    }
}
