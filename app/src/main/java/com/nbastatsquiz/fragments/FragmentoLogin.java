package com.nbastatsquiz.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbastatsquiz.R;


public class FragmentoLogin extends Fragment {


    private static FragmentoLogin fragmentoLogin;

    public FragmentoLogin() {
        // Required empty public constructor
    }

    public static FragmentoLogin newInstance(Bundle datos) {
        if (fragmentoLogin == null) {
            fragmentoLogin =
                    new FragmentoLogin();
        }

        if (datos != null) {
            fragmentoLogin.setArguments(datos);
        }
        return fragmentoLogin;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_login, container, false);
        return view;
    }
}
