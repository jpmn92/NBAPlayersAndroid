package com.nbastatsquiz.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbastatsquiz.R;


public class FragmentoHelp extends Fragment {


    private static FragmentoHelp fragmentoHelp;

    public FragmentoHelp() {
        // Required empty public constructor
    }

    public static FragmentoHelp newInstance(Bundle datos) {
        if (fragmentoHelp == null) {
            fragmentoHelp =
                    new FragmentoHelp();
        }

        if (datos != null) {
            fragmentoHelp.setArguments(datos);
        }
        return fragmentoHelp;
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
