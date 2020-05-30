package com.nbaplayersandroid.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nbaplayersandroid.R;
import com.nbaplayersandroid.beans.FirebasePuntuacion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPuntuaciones extends RecyclerView.Adapter<AdapterPuntuaciones.PuntuacionesViewHolder> {

    private ArrayList<FirebasePuntuacion> listadoPuntuaciones;
    ArrayList<String> images;


    public class PuntuacionesViewHolder extends RecyclerView.ViewHolder {

        public TextView pelicula, usuario, puntos, hora, sala, fecha, username;
        public CircleImageView circleImageView;


        public PuntuacionesViewHolder(@NonNull View v) {
            super(v);


            circleImageView = (CircleImageView) v.findViewById(R.id.avatar);
            fecha = (TextView) v.findViewById(R.id.fecha_puntuacion);
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


        FirebasePuntuacion firebasePuntuacion = listadoPuntuaciones.get(i);

        Picasso.with(viewHolder.circleImageView.getContext()).load(getRandomAvatar()).into(viewHolder.circleImageView);
        viewHolder.username.setText(firebasePuntuacion.getUsername());
//        viewHolder.fecha.setText(firebasePuntuacion.getDate());
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

    public String getRandomAvatar() {

        ArrayList<String> images = new ArrayList<String>();
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612745/2019/260x190/201935.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612749/2019/260x190/203507.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612757/2019/260x190/203081.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2019/260x190/2544.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612744/2019/260x190/201939.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612742/2019/260x190/1629029.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2019/260x190/203076.png");
        images.add("https://i.dlpng.com/static/png/219514_preview.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612759/2015/260x190/1495.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/893.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612742/2018/260x190/1717.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612751/2019/260x190/201142.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612751/2019/260x190/202681.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612747/2015/260x190/977.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612755/2019/260x190/203954.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612746/2019/260x190/202695.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612746/2019/260x190/202331.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612743/2019/260x190/203999.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612748/2019/260x190/202710.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612750/2019/260x190/1626157.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612740/2019/260x190/1629627.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612760/2019/260x190/101108.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/1610612748/2018/260x190/2548.png");
        images.add("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/406.png");


        Random r = new Random();
        int low = 0;
        int high = 22;
        int result = r.nextInt(high - low) + low;

        return images.get(result).toString();
    }


}


