package com.nbastatsquiz.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.NBAPlayer;
import com.nbastatsquiz.tools.GenerateImageUrl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * {@link BaseAdapter} para poblar coches en un grid view
 */

public class AdapterAvatar extends BaseAdapter {
    private Context context;
    private ArrayList<NBAPlayer> nbaPlayers;

    public AdapterAvatar(Context context, ArrayList<NBAPlayer> nbaPlayers) {
        this.context = context;
        this.nbaPlayers = nbaPlayers;
    }

    @Override
    public int getCount() {
        return nbaPlayers.size();
    }

    @Override
    public NBAPlayer getItem(int position) {
        return nbaPlayers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView imagenPlayer = (ImageView) view.findViewById(R.id.imagen_player);
        TextView nombrePlayer = (TextView) view.findViewById(R.id.nombre_player);


        final NBAPlayer item = getItem(position);
        Glide.with(context).load(item.getUrlImage()).into(imagenPlayer);
        nombrePlayer.setText(item.getName());

        return view;
    }

}