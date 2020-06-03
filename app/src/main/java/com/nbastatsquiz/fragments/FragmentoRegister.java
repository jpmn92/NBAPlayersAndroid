package com.nbastatsquiz.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbastatsquiz.R;


public class FragmentoRegister extends Fragment {


    private static FragmentoRegister fragmentoRegister;

    public FragmentoRegister() {
        // Required empty public constructor
    }

    public static FragmentoRegister newInstance(Bundle datos) {
        if (fragmentoRegister == null) {
            fragmentoRegister =
                    new FragmentoRegister();
        }

        if (datos != null) {
            fragmentoRegister.setArguments(datos);
        }
        return fragmentoRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_register, container, false);
        return view;
    }
}
