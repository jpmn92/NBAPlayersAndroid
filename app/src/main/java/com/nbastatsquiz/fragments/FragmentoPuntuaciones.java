package com.nbastatsquiz.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nbastatsquiz.R;
import com.nbastatsquiz.adaptador.AdapterPuntuaciones;
import com.nbastatsquiz.beans.FirebasePuntuacion;

import java.util.ArrayList;


public class FragmentoPuntuaciones extends Fragment {

    private RecyclerView myrv, rvPuntuacionPersonal;
    private RecyclerView.Adapter adapter, adapterPersonal;
    private RecyclerView.LayoutManager lManager, lManagerPersonal;
    private ArrayList<FirebasePuntuacion> listadoPuntuaciones, listadoRecordPersonal; // listadoRecordPersonal es de un único elemento
    TextView txtPuntuacion;
    String tipoTemporada;
    private static FragmentoPuntuaciones fragmentoPuntuaciones;
    Bundle bundle;


    public FragmentoPuntuaciones() {
        // Required empty public constructor
    }


    public static FragmentoPuntuaciones newInstance(Bundle datos) {
        if (fragmentoPuntuaciones == null) {
            fragmentoPuntuaciones =
                    new FragmentoPuntuaciones();
        }

        if (datos != null) {
            fragmentoPuntuaciones.setArguments(datos);
        }
        return fragmentoPuntuaciones;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragmento_puntuaciones, container, false);


        bundle = fragmentoPuntuaciones.getArguments();
        tipoTemporada = "";
//        listadoPuntuaciones = new ArrayList<>();
        listadoPuntuaciones = bundle.getParcelableArrayList("puntuaciones");
        // listadoRecordPersonal = bundle.getParcelableArrayList("puntuaciones");

        rvPuntuacionPersonal = view.findViewById(R.id.recycler_view_puntuacion_personal);
        myrv = view.findViewById(R.id.recycler_view_puntuaciones);
        txtPuntuacion = view.findViewById(R.id.txtPuntuaciones);

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
        lManager = new LinearLayoutManager(getContext());
        lManagerPersonal = new LinearLayoutManager(getContext());

        buscarRecordPersonal();

        adapterPersonal = new AdapterPuntuaciones(listadoRecordPersonal);
        rvPuntuacionPersonal.setLayoutManager(lManagerPersonal);
        rvPuntuacionPersonal.setAdapter(adapterPersonal);
        //rvPuntuacionPersonal.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        myrv.setLayoutManager(lManager);
        myrv.setAdapter(adapter);
        myrv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        return view;
    }

    private void buscarRecordPersonal() {
        listadoRecordPersonal = new ArrayList<FirebasePuntuacion>();
        for(FirebasePuntuacion firebasePuntuacion: listadoPuntuaciones){
            if(firebasePuntuacion.getUsername().equals(bundle.getString("userName"))){
                listadoRecordPersonal.add(firebasePuntuacion);
            }
        }
        if(listadoRecordPersonal.size() > 0){
            FirebasePuntuacion record = listadoRecordPersonal.get(0);
            listadoRecordPersonal = new ArrayList<FirebasePuntuacion>();
            listadoRecordPersonal.add(record);
        }
    }
}
