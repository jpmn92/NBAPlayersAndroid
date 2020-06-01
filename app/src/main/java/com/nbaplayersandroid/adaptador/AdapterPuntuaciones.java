package com.nbaplayersandroid.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nbaplayersandroid.R;
import com.nbaplayersandroid.beans.FirebasePuntuacion;
import com.nbaplayersandroid.tools.GenerateImageUrl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPuntuaciones extends RecyclerView.Adapter<AdapterPuntuaciones.PuntuacionesViewHolder> {

    private ArrayList<FirebasePuntuacion> listadoPuntuaciones;
    ArrayList<String> images;


    public class PuntuacionesViewHolder extends RecyclerView.ViewHolder {

        public TextView puntos, fecha, username;
        public CircleImageView circleImageView;


        public PuntuacionesViewHolder(@NonNull View v) {
            super(v);


            circleImageView = (CircleImageView) v.findViewById(R.id.avatar);
            fecha = (TextView) v.findViewById(R.id.puntuaciones_fecha);
            puntos = (TextView) v.findViewById(R.id.puntuaciones_puntos);
            username = (TextView) v.findViewById(R.id.puntuaciones_username);


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


        GenerateImageUrl generateImageUrl = new GenerateImageUrl();
        FirebasePuntuacion firebasePuntuacion = listadoPuntuaciones.get(i);

        Picasso.with(viewHolder.circleImageView.getContext()).load(generateImageUrl.getRandomAvatar()).into(viewHolder.circleImageView);
        viewHolder.username.setText(firebasePuntuacion.getUsername());
        viewHolder.fecha.setText(firebasePuntuacion.getDate());
        viewHolder.puntos.setText("Pts: " + Integer.toString(firebasePuntuacion.getPoints()));


        //listener para el cardview
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {


                                                   }


                                               }
        );


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


