package com.nbaplayersandroid.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbaplayersandroid.R;


public class FragmentoSettings extends Fragment {


    private static FragmentoSettings fragmentoSettings;

    public FragmentoSettings() {
        // Required empty public constructor
    }

    public static FragmentoSettings newInstance(Bundle datos) {
        if (fragmentoSettings == null) {
            fragmentoSettings =
                    new FragmentoSettings();
        }

        if (datos != null) {
            fragmentoSettings.setArguments(datos);
        }
        return fragmentoSettings;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_settings, container, false);
        return view;
    }
}
