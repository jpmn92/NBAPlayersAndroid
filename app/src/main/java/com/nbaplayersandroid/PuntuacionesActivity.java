package com.nbaplayersandroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.nbaplayersandroid.adaptador.AdapterPuntuaciones;
import com.nbaplayersandroid.beans.FirebasePuntuacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PuntuacionesActivity extends Activity {

    private RecyclerView myrv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private ArrayList<FirebasePuntuacion> listadoPuntuaciones;
    TextView txtPuntuacion;
    String tipoTemporada;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaciones);

        tipoTemporada = "";
        listadoPuntuaciones = new ArrayList<>();
        listadoPuntuaciones = (ArrayList<FirebasePuntuacion>) getIntent().getSerializableExtra("puntuaciones");


        myrv = findViewById(R.id.recycler_view_puntuaciones);
        txtPuntuacion = findViewById(R.id.txtPuntuaciones);

        if(listadoPuntuaciones.size() > 0 ){
            if(listadoPuntuaciones.get(0).getSeasonType().equalsIgnoreCase("Playoffs")){
                tipoTemporada = "PO";
            }else{
                tipoTemporada = "RS";
            }
            txtPuntuacion.setText(listadoPuntuaciones.get(0).getStatCategory() + " | " + listadoPuntuaciones.get(0).getSeason()+" | "+tipoTemporada +" | "+listadoPuntuaciones.get(0).getPerMode());
        }
        else {
            txtPuntuacion.setText("No hay puntuaciones");
        }


        //ordenamos listado puntuaciones




        adapter = new AdapterPuntuaciones(listadoPuntuaciones);
        lManager = new LinearLayoutManager(this);

        myrv.setLayoutManager(lManager);
        myrv.setAdapter(adapter);
        myrv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    public PuntuacionesActivity(ArrayList<FirebasePuntuacion> listadoPuntuacionesParam) {

        this.listadoPuntuaciones = listadoPuntuacionesParam;
    }

    public PuntuacionesActivity() {
    }


}
