package com.nbastatsquiz.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nbastatsquiz.R;
import com.nbastatsquiz.tools.FirebaseMethods;


public class FragmentoRegister extends Fragment {


    private EditText email, passwd, passwd2;
    private Button btnRegister;
    private FirebaseMethods firebaseMethods;

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

        firebaseMethods = new FirebaseMethods();
        initComponents(view);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (passwd.getText().toString().equals(passwd2.getText().toString())) {

                    //6 es el minimo de seguridad de firebase
                    if (passwd.getText().toString().length() >= 6) {
                        //registrar
                        firebaseMethods.registerUser(email.getText().toString(), passwd.getText().toString());

                    } else {
//                        La contraseña minimo son 6 caracteres para firebase

                    }

                } else {
//                        La contraseñas no coinciden
                }
            }
        });

        return view;
    }

    private void initComponents(View view) {

        email = view.findViewById(R.id.txtRegisterEmail);
        passwd = view.findViewById(R.id.txtRegisterPasswd);
        passwd2 = view.findViewById(R.id.txtRegisterConfirmPasswd);
        btnRegister = view.findViewById(R.id.btnRegister);


    }
}
