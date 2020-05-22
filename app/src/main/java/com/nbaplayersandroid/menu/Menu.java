package com.nbaplayersandroid.menu;

import android.app.Activity;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;

public class Menu extends Activity implements View.OnClickListener{

    Spinner sSeason, sCategory, sSeasonType;
    Button startButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sSeason = findViewById(R.id.spinnerSeasons);
        sCategory = findViewById(R.id.spinnerCategory);
        sSeasonType = findViewById(R.id.spinnerSeasonType);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startButton:
                Intent juego = new Intent(this, MainActivity.class);
                Bundle params = new Bundle();

                //parametros del modo de juego
                params.putString("Season", sSeason.getSelectedItem().toString());
                params.putString("SeasonType", sSeasonType.getSelectedItem().toString()); //Playoffs
                params.putString("StatCategory", sCategory.getSelectedItem().toString()); //PTS para puntos
                params.putString("PerMode", "PerGame"); //PerGame para por partido


                juego.putExtras(params);
                this.startActivity(juego);
                break;
        }

    }
}





