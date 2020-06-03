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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FragmentoRegister extends Fragment {


    private EditText email, passwd, passwd2;
    private TextView txtError;
    private Button btnRegister;
    private FirebaseMethods firebaseMethods;
    private String mensaje;
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

        firebaseMethods = new FirebaseMethods(this);
        initComponents(view);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtError.setVisibility(View.GONE);
                if(isEmailValid(email.getText().toString())){
                    if (passwd.getText().toString().equals(passwd2.getText().toString())) {

                        //6 es el minimo de seguridad de firebase
                        if (passwd.getText().toString().length() >= 6) {
                            //registrar
                            firebaseMethods.registerUser(email.getText().toString(), passwd.getText().toString());
                            //mostrarError(mensaje);
                        } else {
//                        La contraseña minimo son 6 caracteres para firebase
                            mostrarError(getString(R.string.password_characters));
                        }

                    } else {
//                        La contraseñas no coinciden
                        mostrarError(getString(R.string.passwords_not_match));
                    }
                }
                else{
                    mostrarError(getString(R.string.invalid_email));
                }

            }
        });

        return view;
    }

    public void mostrarError(String mensaje) {
        txtError.setText(mensaje);
        txtError.setVisibility(View.VISIBLE);
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void initComponents(View view) {

        email = view.findViewById(R.id.txtRegisterEmail);
        passwd = view.findViewById(R.id.txtRegisterPasswd);
        passwd2 = view.findViewById(R.id.txtRegisterConfirmPasswd);
        btnRegister = view.findViewById(R.id.btnRegister);
        txtError = view.findViewById(R.id.txtErrorRegister);


    }

    public TextView getTxtError() {
        return txtError;
    }

    public void setTxtError(TextView txtError) {
        this.txtError = txtError;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
