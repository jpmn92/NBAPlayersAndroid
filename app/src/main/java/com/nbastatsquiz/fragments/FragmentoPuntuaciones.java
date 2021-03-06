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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.nbastatsquiz.R;
import com.nbastatsquiz.adaptador.AdapterPuntuaciones;
import com.nbastatsquiz.beans.FirebasePuntuacion;
import com.nbastatsquiz.tools.ConfigApp;

import java.util.ArrayList;


public class FragmentoPuntuaciones extends Fragment {

    private RecyclerView myrv, rvPuntuacionPersonal;
    private RecyclerView.Adapter adapter, adapterPersonal;
    private RecyclerView.LayoutManager lManager, lManagerPersonal;
    private ArrayList<FirebasePuntuacion> listadoPuntuaciones, listadoPuntuacionesTop50, puntuacionPersonal; // listadoRecordPersonal es de un único elemento
    TextView txtPuntuacion;
    String tipoTemporada;
    String draftTeam, draftCollege, draftSeason, liga;

    private static FragmentoPuntuaciones fragmentoPuntuaciones;

    private AdView mAdView;
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

        inicializarPublicidad(view);

        bundle = fragmentoPuntuaciones.getArguments();
        tipoTemporada = "";
//        listadoPuntuaciones = new ArrayList<>();
        listadoPuntuaciones = bundle.getParcelableArrayList("puntuaciones");
        puntuacionPersonal = bundle.getParcelableArrayList("puntuacionPersonal");
        // listadoRecordPersonal = bundle.getParcelableArrayList("puntuaciones");

        rvPuntuacionPersonal = view.findViewById(R.id.recycler_view_puntuacion_personal);
        myrv = view.findViewById(R.id.recycler_view_puntuaciones);
        txtPuntuacion = view.findViewById(R.id.txtPuntuaciones);


        if (listadoPuntuaciones.size() > 0) {

            if (bundle.getString("modoJuego").equalsIgnoreCase("Stats")) {

                if (listadoPuntuaciones.get(0).getSeasonType().equalsIgnoreCase("Playoffs")) {
                    tipoTemporada = "PO";
                } else {
                    tipoTemporada = "RS";
                }

                if(listadoPuntuaciones.get(0).getSeason().equals("CONCURSO")){
                    txtPuntuacion.setText(getResources().getString(R.string.texto_puntuaciones_concurso));

                }else{
                    txtPuntuacion.setText(listadoPuntuaciones.get(0).getLiga()+" | "+listadoPuntuaciones.get(0).getStatCategory() + " | " + listadoPuntuaciones.get(0).getSeason() + " | " + tipoTemporada + " | " + listadoPuntuaciones.get(0).getPerMode());

                }

            } else {

                if(listadoPuntuaciones.get(0).getDraftTeam().equalsIgnoreCase("")
                        || listadoPuntuaciones.get(0).getDraftTeam().equalsIgnoreCase("0")
                        || listadoPuntuaciones.get(0).getDraftTeam() == null
                ){

                    draftTeam = "MIX";
                }else{
                    draftTeam = listadoPuntuaciones.get(0).getDraftTeam();
                }

                if(listadoPuntuaciones.get(0).getDraftCollege().equalsIgnoreCase("")
                        || listadoPuntuaciones.get(0).getDraftCollege().equalsIgnoreCase("0")
                        || listadoPuntuaciones.get(0).getDraftCollege() == null
                ){

                    draftCollege = "MIX";
                }else{
                    draftCollege = listadoPuntuaciones.get(0).getDraftCollege();

                }

                if(listadoPuntuaciones.get(0).getSeason().equalsIgnoreCase("")
                        || listadoPuntuaciones.get(0).getSeason().equalsIgnoreCase("0")
                        || listadoPuntuaciones.get(0).getSeason() == null
                ){

                    draftSeason = "MIX";
                }else{
                    draftSeason = listadoPuntuaciones.get(0).getSeason();

                }

                txtPuntuacion.setText("Draft: "+draftSeason +" | "+draftCollege+" | "+ draftTeam);

            }

        } else {
            txtPuntuacion.setText(R.string.no_puntuaciones);
        }

        getTop50();
        //ordenamos listado puntuaciones
        adapter = new AdapterPuntuaciones(listadoPuntuacionesTop50);
        lManager = new LinearLayoutManager(getContext());
        lManagerPersonal = new LinearLayoutManager(getContext());

        adapterPersonal = new AdapterPuntuaciones(puntuacionPersonal);
        rvPuntuacionPersonal.setLayoutManager(lManagerPersonal);
        rvPuntuacionPersonal.setAdapter(adapterPersonal);
        //rvPuntuacionPersonal.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        myrv.setLayoutManager(lManager);
        myrv.setAdapter(adapter);
        myrv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        return view;
    }

    private void getTop50() {
        listadoPuntuacionesTop50 = new ArrayList<FirebasePuntuacion>();
        FirebasePuntuacion puntuacionActualTop;

        for (int i = 0; i < listadoPuntuaciones.size(); i++) {
            puntuacionActualTop = listadoPuntuaciones.get(i);
            puntuacionActualTop.setRanking(i + 1);
            listadoPuntuacionesTop50.add(puntuacionActualTop);
        }

    }

    private void buscarRecordPersonal() {
        FirebaseAuth mAuth;
        FirebasePuntuacion puntuacionRecord;
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getUid() != null) {
            String uid = mAuth.getUid();
            if(puntuacionPersonal.size() > 0){
                puntuacionRecord = puntuacionPersonal.get(0);
                rvPuntuacionPersonal.setAdapter(adapterPersonal);
            }
        }
    }

    private void inicializarPublicidad( View view) {
        MobileAds.initialize(getContext());
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        System.out.println(mAdView.getAdUnitId());
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                mAdView.loadAd(new AdRequest.Builder().build());
            }
        });
    }
}