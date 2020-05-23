package com.nbaplayersandroid.menu;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nbaplayersandroid.MainActivity;
import com.nbaplayersandroid.R;
import com.nbaplayersandroid.tools.Mode;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class Menu extends Activity implements View.OnClickListener{

    Spinner sSeason, sCategory, sSeasonType, sDataType;
        Button startButton;
    Resources res;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sSeason = findViewById(R.id.spinnerSeasons);
        sCategory = findViewById(R.id.spinnerCategory);
        sSeasonType = findViewById(R.id.spinnerSeasonType);
        sDataType = findViewById(R.id.spinnerDataType);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        res = getResources();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startButton:
                Intent juego = new Intent(this, MainActivity.class);
                Bundle params = new Bundle();

                //parametros del modo de juego
                String temporada = sSeason.getSelectedItem().toString();

                if(sSeason.getSelectedItemPosition() == 0){
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


                juego.putExtras(params);
                this.startActivity(juego);
                break;
        }

    }

    public String getParam(int arrayId, Spinner spinner){
        TypedArray resourceIDS = res.obtainTypedArray(arrayId);
        int[] resIds = new int[resourceIDS.length()];
        for (int i = 0; i < resourceIDS.length(); i++) {
            resIds[i] = resourceIDS.getResourceId(i, -1);
        }
        resourceIDS.recycle();
        String getParam = res.getResourceEntryName(resIds[(spinner.getSelectedItemPosition())]);

        return getParam;
    }

}





