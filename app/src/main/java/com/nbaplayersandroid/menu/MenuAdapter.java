package com.nbaplayersandroid.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nbaplayersandroid.MainActivity;
import com.nbaplayersandroid.R;
import com.nbaplayersandroid.tools.Mode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MenuAdapter extends BaseAdapter {
    //Activity que esta usando
    private Context context;

    private ArrayList<Button> buttons;

    //para pintar un layout
    private LayoutInflater inflater;

    boolean mostrarNombre;
    boolean mostrarPuntuacion;
    boolean mostrarFoto;


    public MenuAdapter(Activity context, ArrayList<Button> buttons) {
        this.context = context;
        this.buttons = buttons;
        //El inflater es el mismo que el activity main
        inflater = LayoutInflater.from(context);
    }

    @Override
    //devuelve tamaño de la lista de peliculas
    public int getCount() {
        return buttons.size();
    }

    @Override
    //devuelve elemento en la posicion que le pasan
    public Object getItem(int position) {
        return buttons.get(position);
    }

    @Override
    //devuelve el id de una posicion
    public long getItemId(int position) {
        return buttons.get(position).getId();
    }

    @Override
    //posicion del elemento, lo que tenemos que pintar, no se usa
    public View getView(int position, View convertView, ViewGroup parent) {
        //la pelicula que nos traemos la pintamos en el holder que pinta en la view

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.view_lvmenu_row, null);


            Button rm_btn = (Button) convertView.findViewById(R.id.button);
            rm_btn.setText(Mode.values()[position].getBoton());
            buttons.get(position).setText(Mode.values()[position].getBoton());

            //Model m = modelList.get(position);
            //tv.setText(m.getName());

            // click listener for remove button  คลิกลบปุ่ม
            rm_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.button:
                            System.out.println("PULSADO " + position);
                            Intent juego = new Intent(context, MainActivity.class);
                            Bundle params = new Bundle();
                            params.putInt("mode", position);
                            juego.putExtras(params);
                            context.startActivity(juego);
                            //Toast.makeText(context.getApplicationContext(), "PULSADO", Toast.LENGTH_SHORT);
                            break;
                    }
                }
            });
        }


//        Button button = buttons.get(position);
//        button.setText("Boton");


        //devuelve la view pintada
        return convertView;
    }
}
