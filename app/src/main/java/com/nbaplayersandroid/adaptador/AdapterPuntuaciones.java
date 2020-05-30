package com.nbaplayersandroid.adaptador;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nbaplayersandroid.R;
import com.nbaplayersandroid.beans.FirebasePuntuacion;

import java.util.ArrayList;

public class AdapterPuntuaciones extends RecyclerView.Adapter<AdapterPuntuaciones.PuntuacionesViewHolder> {

    private ArrayList<FirebasePuntuacion> listadoPuntuaciones;


    public class PuntuacionesViewHolder extends RecyclerView.ViewHolder {

        public TextView pelicula, cine, fecha, hora, sala;


        public PuntuacionesViewHolder(@NonNull View v) {
            super(v);

            pelicula = (TextView) v.findViewById(R.id.title_session);
            cine = (TextView) v.findViewById(R.id.cine_session);
            fecha = (TextView) v.findViewById(R.id.fecha_session);
            hora = (TextView) v.findViewById(R.id.hora_session);
            sala = (TextView) v.findViewById(R.id.sala_sesion);

        }
    }

    public AdapterPuntuaciones(ArrayList<FirebasePuntuacion> puntuaciones) {

        this.listadoPuntuaciones = puntuaciones;

    }

    @Override
    public PuntuacionesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sesion_list_row,
                        viewGroup, false);



        return new PuntuacionesViewHolder(v);
    }


    @Override
    public void onBindViewHolder(PuntuacionesViewHolder viewHolder, final int i) {

        for(int posicion = 0; posicion < listadoPuntuaciones.size(); posicion++){

            final FirebasePuntuacion firebasePuntuacion = listadoPuntuaciones.get(posicion);
            viewHolder.cine.setText(listadoPuntuaciones.get(posicion).getUsername());
            viewHolder.fecha.setText(Integer.toString(listadoPuntuaciones.get(posicion).getPoints()));
            viewHolder.pelicula.setText(listadoPuntuaciones.get(posicion).getSeason());
            viewHolder.hora.setText(listadoPuntuaciones.get(posicion).getStatCategory());
            viewHolder.sala.setText(listadoPuntuaciones.get(posicion).getPerMode());


        }






        //listener para el cardview
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {

                                                       //PARA ABRIR FRAGMENTO CON DETALLE PELICULA DE SESION





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
        }    }


}


