package com.nbastatsquiz.fragments.auth;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.nbastatsquiz.R;
import com.nbastatsquiz.tools.FirebaseMethods;


public class FragmentoLogin extends FragmentoAutentificacion {


    public static FragmentoLogin fragmentoLogin;
    private Button btLogIn, btnGoogle;
    private EditText txtLogIn, txtPass;
    private FirebaseMethods firebaseMethods;
    private TextView goToRegister, txtErrorLogIn;
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
        txtErrorLogIn = view.findViewById(R.id.txtErrorLogIn);
        txtErrorLogIn.setVisibility(View.GONE);
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

    public TextView getTxtErrorLogIn() {
        return txtErrorLogIn;
    }

    public void setTxtErrorLogIn(TextView txtErrorLogIn) {
        this.txtErrorLogIn = txtErrorLogIn;
    }
}