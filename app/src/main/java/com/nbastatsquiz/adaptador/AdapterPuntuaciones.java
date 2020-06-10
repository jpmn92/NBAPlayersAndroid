package com.nbastatsquiz.adaptador;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.FirebasePuntuacion;
import com.nbastatsquiz.tools.GenerateImageUrl;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPuntuaciones extends RecyclerView.Adapter<AdapterPuntuaciones.PuntuacionesViewHolder> {

    private ArrayList<FirebasePuntuacion> listadoPuntuaciones;


    public class PuntuacionesViewHolder extends RecyclerView.ViewHolder {

        public TextView puntos, fecha, username, posicion;
        public CircleImageView circleImageView;


        public PuntuacionesViewHolder(@NonNull View v) {
            super(v);


            circleImageView = (CircleImageView) v.findViewById(R.id.avatar);
            fecha = (TextView) v.findViewById(R.id.puntuaciones_fecha);
            puntos = (TextView) v.findViewById(R.id.puntuaciones_puntos);
            username = (TextView) v.findViewById(R.id.puntuaciones_username);
            posicion = (TextView) v.findViewById(R.id.posicionPuntuacion);


        }
    }

    public AdapterPuntuaciones(ArrayList<FirebasePuntuacion> puntuaciones) {

        this.listadoPuntuaciones = puntuaciones;

    }

    @Override
    public PuntuacionesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item,
                        viewGroup, false);


        return new PuntuacionesViewHolder(v);
    }


    @Override
    public void onBindViewHolder(PuntuacionesViewHolder viewHolder, final int i) {


        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String myUid = mAuth.getUid();

        GenerateImageUrl generateImageUrl = new GenerateImageUrl();
        FirebasePuntuacion firebasePuntuacion = listadoPuntuaciones.get(i);

        if (firebasePuntuacion.getImage() == null || firebasePuntuacion.getImage().equalsIgnoreCase("")) {
            Picasso.with(viewHolder.circleImageView.getContext()).load(generateImageUrl.getRandomAvatar()).into(viewHolder.circleImageView);

        } else {
            Picasso.with(viewHolder.circleImageView.getContext())

                    .load(firebasePuntuacion.getImage())
//                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .into(viewHolder.circleImageView);

        }

        if(firebasePuntuacion.getUid().equals(myUid)){
            viewHolder.circleImageView.setFillColor(Color.parseColor("#0288D1"));

        }else{
            viewHolder.circleImageView.setFillColor(Color.parseColor("#FF9800"));
        }

        viewHolder.posicion.setText(Integer.toString(firebasePuntuacion.getRanking()));
        viewHolder.username.setText(firebasePuntuacion.getUsername());
        viewHolder.fecha.setText(firebasePuntuacion.getDate());
        viewHolder.puntos.setText("Pts: " + Integer.toString(firebasePuntuacion.getPoints()));


    }

    @Override
    public int getItemCount() {
        if (listadoPuntuaciones == null) {


            return 0;
        } else {
            return listadoPuntuaciones.size();
        }
    }


}


