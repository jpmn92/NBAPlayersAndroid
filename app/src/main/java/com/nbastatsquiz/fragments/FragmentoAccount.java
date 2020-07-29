package com.nbastatsquiz.fragments;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nbastatsquiz.NavigationDrawerActivity;
import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.NBAPlayer;
import com.nbastatsquiz.tools.FirebaseMethods;
import com.nbastatsquiz.tools.GenerateImageUrl;
import com.nbastatsquiz.tools.SessionManagement;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentoAccount extends Fragment {

    Button btnSetOptions;
    TextView txtUserName;
    SessionManagement sm;
    Spinner spinnerProfile;
    GenerateImageUrl generateImageUrl;
    ImageView ivAvatar;
    CircleImageView circleImageView;
    NavigationDrawerActivity navigationDrawerActivity;
    FirebaseMethods firebaseMethods;
    EditText txtCodgio;
    String urlCode;
    boolean codigo;
    int inicializacion;

    private static FragmentoAccount fragmentoAccount;

    public FragmentoAccount() {
        // Required empty public constructor
    }

    public static FragmentoAccount newInstance(Bundle datos) {
        if (fragmentoAccount == null) {
            fragmentoAccount =
                    new FragmentoAccount();
        }

        if (datos != null) {
            fragmentoAccount.setArguments(datos);
        }
        return fragmentoAccount;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_account, container, false);

        sm = new SessionManagement(getContext());
        generateImageUrl = new GenerateImageUrl();

        initComponents(view);

        firebaseMethods = new FirebaseMethods(getContext());

        txtUserName.setText(sm.getSessionUserName());
//TODO: no permitimos cambiar el username de momento
        txtUserName.setEnabled(false);


        ArrayList<NBAPlayer> nbaPlayers = generateImageUrl.getNBAPlayers();

        //ordenamos array
        if (nbaPlayers.size() > 0) {
            Collections.sort(nbaPlayers, new Comparator<NBAPlayer>() {
                @Override
                public int compare(NBAPlayer o1, NBAPlayer o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }

        ArrayAdapter<NBAPlayer> adapter = new ArrayAdapter<NBAPlayer>(getContext(), R.layout.support_simple_spinner_dropdown_item, nbaPlayers);
        spinnerProfile.setAdapter(adapter);

        //si el perfil tiene imagen, que busque de quien es esa imagen y la ponga como valor por defecto del spinner
        if (sm.getSesionImage() != "") {

            for (int i = 0; i < nbaPlayers.size(); i++) {

                if (nbaPlayers.get(i).getUrlImage().equalsIgnoreCase(sm.getSesionImage())) {
                    spinnerProfile.setSelection(i);
                }

            }


        }


        btnSetOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkInternetConnection() == true) {

                    //cogemos jugador seleccionado y pasamos la url de su imagen


                    String userName = txtUserName.getText().toString();
                    String email = sm.getSessionEmail();

                    if(codigo){
                        sm.saveSession(txtUserName.getText().toString(), email, urlCode);

                        firebaseMethods.updateAvatar(urlCode,getContext());
                    }
                    else{
                        NBAPlayer nbaPlayer = (NBAPlayer) spinnerProfile.getSelectedItem();

                        sm.saveSession(txtUserName.getText().toString(), email, nbaPlayer.getUrlImage());


                        firebaseMethods.updateAvatar(nbaPlayer.getUrlImage(),getContext());
                    }





//                    Toast.makeText(getContext(), R.string.config_updated, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.sin_conexion, Toast.LENGTH_SHORT).show();

                }


            }
        });

        spinnerProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(++inicializacion > 1){
                    NBAPlayer nbaPlayer = (NBAPlayer) spinnerProfile.getSelectedItem();
                    Glide.with(getContext()).load(nbaPlayer.getUrlImage()).into(circleImageView);
                    codigo = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtCodgio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                firebaseMethods.readCode(s.toString(), fragmentoAccount);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //inicializacion = false;

        return view;
    }


    private void initComponents(View view) {
        inicializacion = 0;
        codigo = false;
        btnSetOptions = view.findViewById(R.id.btnSetOptions);
        txtUserName = view.findViewById(R.id.txtUserNameOptions);
        txtCodgio = view.findViewById(R.id.txtCode);
        spinnerProfile = view.findViewById(R.id.spinnerProfilePicture);
//        ivAvatar = view.findViewById(R.id.ivAvatar);
        circleImageView = view.findViewById(R.id.ivAvatar);
        Glide.with(getContext()).load(sm.getSesionImage()).into(circleImageView);

    }

    private Boolean checkInternetConnection() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;


        } else {
            connected = false;
//            Toast.makeText(getContext(), "no hay conexion a internet", Toast.LENGTH_SHORT).show();
        }

        return connected;

    }

    public void urlCode(String url){
        Glide.with(getContext()).load(url).into(circleImageView);
        urlCode = url;
        codigo = true;
    }
}