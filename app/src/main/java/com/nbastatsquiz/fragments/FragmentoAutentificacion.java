package com.nbastatsquiz.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nbastatsquiz.R;
import com.nbastatsquiz.tools.FirebaseMethods;

public class FragmentoAutentificacion extends Fragment {


    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private int RC_SIGN_IN = 1;
    private FirebaseMethods firebaseMethods;

    public FragmentoAutentificacion() {
    }

    public FragmentoAutentificacion(GoogleSignInClient googleSignInClient, FirebaseAuth firebaseAuth) {
        this.googleSignInClient = googleSignInClient;
        this.firebaseAuth = firebaseAuth;
    }

    public void signInGoogle(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        firebaseMethods = new FirebaseMethods(context);
        firebaseAuth = FirebaseAuth.getInstance();
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else{
            Toast.makeText(getContext(), "se ha producido un error", Toast.LENGTH_SHORT).show();

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(getContext(), "Signed in sucessfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);


        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error, Signed in unsucessfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);

        }

    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {


        try{
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(getContext(), "successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        firebaseMethods.logIn(user, getContext());


                    } else {
                        Toast.makeText(getContext(), "Unsuccessful login", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Se ha producido un error", Toast.LENGTH_SHORT).show();

        }




    }


}
