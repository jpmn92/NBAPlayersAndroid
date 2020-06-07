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


public class FragmentoLogin extends Fragment {


    public static FragmentoLogin fragmentoLogin;
    private Button btLogIn, btnGoogle;
    private EditText txtLogIn, txtPass;
    private FirebaseMethods firebaseMethods;
    private TextView goToRegister;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private int RC_SIGN_IN = 1;




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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        btLogIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                firebaseMethods.logIn(txtLogIn.getText().toString(), txtPass.getText().toString(), getContext());
            }
        });





        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signInGoogle();



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

    private void signInGoogle(){

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){

        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(getContext(), "Signed in sucessfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);


        }catch (ApiException e){
            Toast.makeText(getContext(), "Error, Signed in unsucessfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);

        }

    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct){


        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(getContext(), "successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    firebaseMethods.logIn(user, getContext());



                }else{
                    Toast.makeText(getContext(), "UNsuccessful", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void initComponents(View view) {
        btLogIn = view.findViewById(R.id.btLogIn);
        firebaseMethods = new FirebaseMethods(this);
        txtLogIn = view.findViewById(R.id.txtLoginEmail);
        txtPass = view.findViewById(R.id.txtLoginPasswd);
        goToRegister = view.findViewById(R.id.txtGoToRegister);
        btnGoogle = view.findViewById(R.id.btnLogInGoogle);
    }

    private void goToRegister(){
        FragmentoRegister fragmentoRegister = FragmentoRegister.newInstance(null);


        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragmentoRegister, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }


}