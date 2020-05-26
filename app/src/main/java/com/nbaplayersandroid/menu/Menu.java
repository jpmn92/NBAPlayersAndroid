package com.nbaplayersandroid.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nbaplayersandroid.MainActivity;
import com.nbaplayersandroid.R;
import com.nbaplayersandroid.tools.FirebaseMethods;
import com.nbaplayersandroid.tools.Mode;
import com.nbaplayersandroid.tools.SessionManagement;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class Menu extends Activity implements View.OnClickListener {

    Spinner sSeason, sCategory, sSeasonType, sDataType;
    CheckBox checkSound;
    Button startButton, btnRecords;
    Resources res;
    FirebaseMethods firebaseMethods;
    String userName;
    SessionManagement sessionManagement;
    Bundle params;
    Intent juego;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        checkSound = findViewById(R.id.checkSound);
        sSeason = findViewById(R.id.spinnerSeasons);
        sCategory = findViewById(R.id.spinnerCategory);
        sSeasonType = findViewById(R.id.spinnerSeasonType);
        sDataType = findViewById(R.id.spinnerDataType);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        btnRecords = findViewById(R.id.btnRecords);
        res = getResources();

        firebaseMethods = new FirebaseMethods();

        btnRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                firebaseMethods.createFbPuntuacion();
                sessionManagement = new SessionManagement(getBaseContext());
                sessionManagement.removeSession();

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                juego = new Intent(this, MainActivity.class);
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

                params.putBoolean("Sound", checkSound.isChecked());

                checkSession();
//                userName = "jp"; //Para pruebas de momento
//                params.putString("userName", userName);
//                juego.putExtras(params);
//                this.startActivity(juego);
                break;


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

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            builder.setTitle(R.string.introduce_user_name);

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
//                    dialog.cancel();
//                    userName = "Usuario anonimo";
//                    sessionManagement.saveSession(userName);
//                    juego.putExtras(params);
//                    Menu.this.startActivity(juego);
                    dialog.dismiss();
                }
            });

            builder.show();


//            username = "jp"; //o el quue se meta por el dialog

        }
    }

}





