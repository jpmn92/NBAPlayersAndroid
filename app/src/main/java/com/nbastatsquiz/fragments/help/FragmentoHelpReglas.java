package com.nbastatsquiz.fragments.help;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbastatsquiz.R;


public class FragmentoHelpReglas extends Fragment {


    private static FragmentoHelpReglas fragmentoHelpReglas;

    public FragmentoHelpReglas() {
        // Required empty public constructor
    }

    public static FragmentoHelpReglas newInstance(Bundle datos) {
        if (fragmentoHelpReglas == null) {
            fragmentoHelpReglas =
                    new FragmentoHelpReglas();
        }

        if (datos != null) {
            fragmentoHelpReglas.setArguments(datos);
        }
        return fragmentoHelpReglas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_help, container, false);


        return view;
    }


}
