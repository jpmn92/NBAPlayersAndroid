package com.nbastatsquiz.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nbastatsquiz.R;
import com.nbastatsquiz.tools.FirebaseMethods;


public class FragmentoLogin extends Fragment {


    public static FragmentoLogin fragmentoLogin;
    private Button btLogIn;
    private EditText txtLogIn, txtPass;
    private FirebaseMethods firebaseMethods;

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
        initComponents(view);
        btLogIn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                firebaseMethods.logIn(txtLogIn.getText().toString(), txtPass.getText().toString());
            }
        });
        return view;
    }

    private void initComponents(View view) {
        btLogIn = view.findViewById(R.id.btLogIn);
        firebaseMethods = new FirebaseMethods(this);
        txtLogIn = view.findViewById(R.id.txtLoginEmail);
        txtPass = view.findViewById(R.id.txtLoginPasswd);
    }
}