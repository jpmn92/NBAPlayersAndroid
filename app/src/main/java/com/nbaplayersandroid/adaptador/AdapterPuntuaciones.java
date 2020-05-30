package com.nbaplayersandroid.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nbaplayersandroid.R;
import com.nbaplayersandroid.beans.FirebasePuntuacion;

import java.util.ArrayList;

public class AdapterPuntuaciones extends RecyclerView.Adapter<AdapterPuntuaciones.PuntuacionesViewHolder> {

    private ArrayList<FirebasePuntuacion> listadoPuntuaciones;


    public class PuntuacionesViewHolder extends RecyclerView.ViewHolder {

        public TextView pelicula, usuario, puntos, hora, sala, fecha;


        public PuntuacionesViewHolder(@NonNull View v) {
            super(v);


            usuario = (TextView) v.findViewById(R.id.usuario_puntuacion);
            puntos = (TextView) v.findViewById(R.id.puntos_puntuacion);
            fecha = (TextView) v.findViewById(R.id.fecha_puntuacion);

//            hora = (TextView) v.findViewById(R.id.hora_session);
//            sala = (TextView) v.findViewById(R.id.sala_sesion);
        }
    }

    public AdapterPuntuaciones(ArrayList<FirebasePuntuacion> puntuaciones) {

        this.listadoPuntuaciones = puntuaciones;

    }

    @Override
    public PuntuacionesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.puntuacion_list_row,
                        viewGroup, false);


        return new PuntuacionesViewHolder(v);
    }


    @Override
    public void onBindViewHolder(PuntuacionesViewHolder viewHolder, final int i) {


        FirebasePuntuacion firebasePuntuacion = listadoPuntuaciones.get(i);
        viewHolder.usuario.setText(firebasePuntuacion.getUsername());
        viewHolder.fecha.setText(firebasePuntuacion.getDate());
        viewHolder.puntos.setText("Pts: "+Integer.toString(firebasePuntuacion.getPoints()));
//        viewHolder.hora.setText(firebasePuntuacion.getStatCategory());
//        viewHolder.sala.setText(firebasePuntuacion.getPerMode());


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


