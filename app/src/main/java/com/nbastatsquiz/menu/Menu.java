package com.nbastatsquiz.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.nbastatsquiz.GameActivity;
import com.nbastatsquiz.PuntuacionesActivity;
import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.FirebasePuntuacion;
import com.nbastatsquiz.tools.FirebaseMethods;
import com.nbastatsquiz.tools.SessionManagement;

import java.util.ArrayList;
import java.util.Collections;


public class Menu extends Activity implements View.OnClickListener {

    Spinner sSeason, sCategory, sSeasonType, sDataType;
    Button startButton, btnRecords, btnOptions, btnOptionsOk;
    Resources res;
    FirebaseMethods firebaseMethods;
    String userName;
    SessionManagement sessionManagement;
    Bundle params;
    Intent juego;
    ArrayList<FirebasePuntuacion> puntuaciones;
    boolean sound;

    public ArrayList<FirebasePuntuacion> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(ArrayList<FirebasePuntuacion> puntuaciones) {
        this.puntuaciones = puntuaciones;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sound = true;
        sSeason = findViewById(R.id.spinnerSeasons);
        sCategory = findViewById(R.id.spinnerCategory);
        sSeasonType = findViewById(R.id.spinnerSeasonType);
        sDataType = findViewById(R.id.spinnerDataType);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        btnRecords = findViewById(R.id.btnRecords);
        //btnOptionsOk = findViewById(R.id.btnOptionsOk);
        btnOptionsOk.setOnClickListener(this);
        btnOptions = findViewById(R.id.btnOptions);
        btnOptions.setOnClickListener(this);
        res = getResources();

        firebaseMethods = new FirebaseMethods(this);

        btnRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                firebaseMethods.createFbPuntuacion();

//                sessionManagement = new SessionManagement(getBaseContext());
//                sessionManagement.removeSession();

                //TODO: revisar los parametros a pasar
                 firebaseMethods.getTopPuntuaciones(getParams());


            }
        });

    }

    public void goToPuntuaciones() {
        // puntuaciones.sort();
        Collections.sort(puntuaciones, Collections.reverseOrder());
        Intent activityPuntuaciones = new Intent(getApplicationContext(), PuntuacionesActivity.class);
        activityPuntuaciones.putExtra("puntuaciones", puntuaciones);
        startActivity(activityPuntuaciones);
    }

    public Bundle getParams(){

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

        paramsPartida.putBoolean("Sound", sound);

        return  paramsPartida;


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                juego = new Intent(this, GameActivity.class);
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

                params.putBoolean("Sound", sound);

                checkSession();
                break;
                
            case R.id.btnOptions:
                dialogOptions();
                break;


        }

    }

    private void dialogOptions() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.options);
        View optionsView = View.inflate(this, R.layout.options_layout, null);
        getUserName();

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(optionsView);
        TextView txtUserNameOptions = optionsView.findViewById(R.id.txtUserNameOptions);
        txtUserNameOptions.setText(userName);
        CheckBox checkBoxSound = optionsView.findViewById(R.id.checkboxSoundOptions);
        checkBoxSound.setChecked(sound);


        // Set up the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userName = txtUserNameOptions.getText().toString();
                sound = checkBoxSound.isChecked();
                sessionManagement.saveSession(userName, sound);
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

    private void getUserName() {
        sessionManagement = new SessionManagement(this);
        int userID = sessionManagement.getSession();

        if (userID != -1) {
            userName = sessionManagement.getSessionUserName();
            sound = sessionManagement.getSound();
        }
        else{
            userName = "";
            sound = true;
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

        sessionManagement = new SessionManagement(this);
        int userID = sessionManagement.getSession();

        if (userID != -1) {
            userName = sessionManagement.getSessionUserName();
            juego.putExtras(params);
            Menu.this.startActivity(juego);
        } else {

            //No logueados

            //le pedimos username y despues guardamos la sesion
            //AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_Design_BottomSheetDialog);
            builder.setTitle(R.string.introduce_user_name);

            // Set up the input
            final EditText input = new EditText(this);
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
                    Menu.this.startActivity(juego);

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





