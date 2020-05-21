package com.nbaplayersandroid.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nbaplayersandroid.R;
import com.nbaplayersandroid.tools.Mode;

import java.util.ArrayList;
import java.util.Arrays;

public class Menu extends Activity {

    ListView lvMenu;
    Button boton;

    private ArrayList<String> mArrData;
    private MenuAdapter mAdapter;
    private ArrayList<Button> buttons;

    private Mode modoSeleccionado;
    private int posicionLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        mArrData = new ArrayList<>(Arrays.asList("111,222,333,444,555,666".split(",")));
        buttons = new ArrayList<>();
        lvMenu = findViewById(R.id.lvMenu);
//        mAdapter.notifyDataSetChanged();

        mAdapter = new MenuAdapter(Menu.this,  buttons);
        lvMenu.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();


        crearClickListeners(mAdapter);

    }

    private void crearClickListeners(final MenuAdapter adapter) {
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                posicionLista = i;
                modoSeleccionado = (Mode) lvMenu.getItemAtPosition(posicionLista);
                System.out.println("PULSADO");
                Toast.makeText(Menu.this,boton.getText(), Toast.LENGTH_SHORT);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        buttons.clear();
        for (Mode mode: Mode.values()){
            boton = new Button(this);
            buttons.add(boton);
        }
        this.mAdapter.notifyDataSetChanged();
        System.out.println("");
    }


}
