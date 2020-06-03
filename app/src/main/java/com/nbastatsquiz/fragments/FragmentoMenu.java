package com.nbastatsquiz.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nbastatsquiz.GameActivity;
import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.FirebasePuntuacion;
import com.nbastatsquiz.tools.FirebaseMethods;
import com.nbastatsquiz.tools.SessionManagement;

import java.util.ArrayList;
import java.util.Collections;


public class FragmentoMenu extends Fragment {

    private static FragmentoMenu fragmentoMenu;

    Spinner sSeason, sCategory, sSeasonType, sDataType;
    Button btnStart, btnRecords;
    Resources res;
    FirebaseMethods firebaseMethods;
    String userName;
    SessionManagement sessionManagement;
    Bundle params;
    Intent juego;
    ArrayList<FirebasePuntuacion> puntuaciones;

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


        sSeason = view.findViewById(R.id.spinnerSeasons);
        sCategory = view.findViewById(R.id.spinnerCategory);
        sSeasonType = view.findViewById(R.id.spinnerSeasonType);
        sDataType = view.findViewById(R.id.spinnerDataType);
        btnStart = view.findViewById(R.id.btnStart);
        btnRecords = view.findViewById(R.id.btnRecords);

        btnStart.setOnClickListener(this::onClick);


        btnRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                firebaseMethods.createFbPuntuacion();

//                sessionManagement = new SessionManagement(getBaseContext());
//                sessionManagement.removeSession();

                //TODO: revisar los parametros a pasar
                firebaseMethods.getRecord2(getParams());


            }
        });

    }

    public void goToPuntuaciones() {
        // puntuaciones.sort();

        params = getParams();

        Collections.sort(puntuaciones, Collections.reverseOrder());

        params.putParcelableArrayList("puntuaciones", puntuaciones);

        FragmentoPuntuaciones fragmentoPuntuaciones = FragmentoPuntuaciones.newInstance(params);


        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragmentoPuntuaciones, "findThisFragment")
                .addToBackStack(null)
                .commit();


//        Intent activityPuntuaciones = new Intent(getContext(), PuntuacionesActivity.class);
//        activityPuntuaciones.putExtra("puntuaciones", puntuaciones);
//        startActivity(activityPuntuaciones);
    }

    public Bundle getParams() {

        Bundle paramsPartida = new Bundle();

        //parametros del modo de juego
        String temporada = sSeason.getSelectedItem().toString();

        if (sSeason.getSelectedItemPosition() == 0) {
            temporada = "MISC";
        }

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
        switch (v.getId()) {
            case R.id.btnStart:
                juego = new Intent(getActivity().getBaseContext(), GameActivity.class);
                params = new Bundle();

                //parametros del modo de juego
                String temporada = sSeason.getSelectedItem().toString();

                if (sSeason.getSelectedItemPosition() == 0) {
                    temporada = "MISC";
                }

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

            case R.id.btnOptions:
//                dialogOptions();
                break;


        }

    }

    private void getUserName() {
        sessionManagement = new SessionManagement(getContext());
        int userID = sessionManagement.getSession();

        if (userID != -1) {
            userName = sessionManagement.getSessionUserName();

        } else {
            userName = "";

        }
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

        sessionManagement = new SessionManagement(getContext());
        int userID = sessionManagement.getSession();

        if (userID != -1) {
            userName = sessionManagement.getSessionUserName();
            juego.putExtras(params);
            getActivity().startActivity(juego);
        } else {

            //No logueados

            //le pedimos username y despues guardamos la sesion
            //AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_Design_BottomSheetDialog);
            builder.setTitle(R.string.introduce_user_name);

            // Set up the input
            final EditText input = new EditText(getContext());
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    userName = input.getText().toString();
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


//            username = "jp"; //o el quue se meta por el dialog

        }
    }
}
