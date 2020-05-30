package com.nbaplayersandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.nbaplayersandroid.adaptador.AdapterPuntuaciones;
import com.nbaplayersandroid.beans.FirebasePuntuacion;

import java.util.ArrayList;

public class PuntuacionesActivity extends Activity {

    private RecyclerView myrv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private ArrayList<FirebasePuntuacion> listadoPuntuaciones;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaciones);

        listadoPuntuaciones = new ArrayList<>();
        listadoPuntuaciones = (ArrayList<FirebasePuntuacion>) getIntent().getSerializableExtra("puntuaciones");


        myrv = findViewById(R.id.recycler_view_puntuaciones);

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
