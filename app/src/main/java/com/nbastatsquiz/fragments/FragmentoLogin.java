package com.nbastatsquiz.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nbastatsquiz.R;
import com.nbastatsquiz.tools.FirebaseMethods;

import java.util.Collections;


public class FragmentoLogin extends FragmentoAutentificacion {


    public static FragmentoLogin fragmentoLogin;
    private Button btLogIn, btnGoogle;
    private EditText txtLogIn, txtPass;
    private FirebaseMethods firebaseMethods;
    private TextView goToRegister;
    private FirebaseAuth firebaseAuth;


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

        firebaseAuth = FirebaseAuth.getInstance();

        btLogIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                firebaseMethods.logIn(txtLogIn.getText().toString(), txtPass.getText().toString(), getContext());
            }
        });


        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signInGoogle(getContext());


            }
        });


        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToRegister();

            }


        });

        return view;
    }

    private void initComponents(View view) {
        btLogIn = view.findViewById(R.id.btLogIn);
        firebaseMethods = new FirebaseMethods(this);
        txtLogIn = view.findViewById(R.id.txtLoginEmail);
        txtPass = view.findViewById(R.id.txtLoginPasswd);
        goToRegister = view.findViewById(R.id.txtGoToRegister);
        btnGoogle = view.findViewById(R.id.btnLogInGoogle);
    }

    private void goToRegister() {
        FragmentoRegister fragmentoRegister = FragmentoRegister.newInstance(null);


        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragmentoRegister, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }


}