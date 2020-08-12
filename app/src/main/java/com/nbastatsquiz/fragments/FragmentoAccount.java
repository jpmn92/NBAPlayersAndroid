package com.nbastatsquiz.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
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
import com.nbastatsquiz.fragments.auth.FragmentoRegister;
import com.nbastatsquiz.tools.FirebaseMethods;
import com.nbastatsquiz.tools.GenerateImageUrl;
import com.nbastatsquiz.tools.SelectorImagenActivity;
import com.nbastatsquiz.tools.SessionManagement;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentoAccount extends Fragment{

    Button btnSetOptions;
    TextView txtUserName, txtCambiaAvatar;
    SessionManagement sm;
    GenerateImageUrl generateImageUrl;
    ImageView ivAvatar;
    CircleImageView circleImageView;
    Spinner spinnerProfile;
    NavigationDrawerActivity navigationDrawerActivity;
    FirebaseMethods firebaseMethods;
    EditText txtCodigo;
    String urlCode;
    ArrayList<NBAPlayer> nbaPlayers;
    boolean codigo;
    int inicializacion;
    private String urlFromDialog;

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

        nbaPlayers = generateImageUrl.getNBAPlayers();

        //ordenamos array
        if (nbaPlayers.size() > 0) {
            Collections.sort(nbaPlayers, new Comparator<NBAPlayer>() {
                @Override
                public int compare(NBAPlayer o1, NBAPlayer o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }

        btnSetOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkInternetConnection() == true) {

                    //cogemos jugador seleccionado y pasamos la url de su imagen


                    String userName = txtUserName.getText().toString();
                    String email = sm.getSessionEmail();

                    if(codigo){
                        sm.saveSession(userName, email, urlCode);

                        firebaseMethods.updateUser(userName, urlCode,getContext());
                    }
                    else{

                        if(urlFromDialog != null && !"".equalsIgnoreCase(urlFromDialog)){
                            sm.saveSession(userName, email, urlFromDialog);
                            firebaseMethods.updateUser(userName, urlFromDialog,getContext());
                        }

                        else if (sm.getSesionImage() != "") {

                            for (int i = 0; i < nbaPlayers.size(); i++) {

                                if (nbaPlayers.get(i).getUrlImage().equalsIgnoreCase(sm.getSesionImage())) {
                                    sm.saveSession(userName, email, nbaPlayers.get(i).getUrlImage());
                                    firebaseMethods.updateUser(userName, nbaPlayers.get(i).getUrlImage(),getContext());
                                }

                            }

                        }
                    }

//                    Toast.makeText(getContext(), R.string.config_updated, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.sin_conexion, Toast.LENGTH_SHORT).show();

                }


            }
        });



        txtCambiaAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new SelectorImagenActivity(fragmentoAccount);
                dialog.show(getFragmentManager(), "NoticeDialogFragment");
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentoSelectorImagen fragmentoSelectorImagen = FragmentoSelectorImagen.newInstance(null);
//
//
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.main_content, fragmentoSelectorImagen, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();

                DialogFragment dialog = new SelectorImagenActivity(fragmentoAccount);
                dialog.show(getFragmentManager(), "NoticeDialogFragment");
            }
        });

        txtCodigo.addTextChangedListener(new TextWatcher() {
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
        txtCodigo = view.findViewById(R.id.txtCode);
//        ivAvatar = view.findViewById(R.id.ivAvatar);
        circleImageView = view.findViewById(R.id.ivAvatar);
        txtCambiaAvatar = view.findViewById(R.id.txtCambiaAvatar);
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
        if(!url.equalsIgnoreCase(nbaPlayers.get(nbaPlayers.size() - 1).getUrlImage())){
            NBAPlayer newNbaPlayer = new NBAPlayer(nbaPlayers.size(), txtCodigo.getText().toString().toUpperCase(), url);
            nbaPlayers.add(newNbaPlayer);
            ArrayAdapter<NBAPlayer> adapter = new ArrayAdapter<NBAPlayer>(getContext(), R.layout.support_simple_spinner_dropdown_item, nbaPlayers);
            Glide.with(getContext()).load(url).into(circleImageView);
            urlCode = url;
            codigo = true;
        }

    }

    public String getUrlFromDialog() {
        return urlFromDialog;
    }

    public void setUrlFromDialog(String urlFromDialog) {
        this.urlFromDialog = urlFromDialog;
    }

    public CircleImageView getCircleImageView() {
        return circleImageView;
    }

    public void setCircleImageView(CircleImageView circleImageView) {
        this.circleImageView = circleImageView;
    }

    public ArrayList<NBAPlayer> getNbaPlayers() {
        return nbaPlayers;
    }

    public void setNbaPlayers(ArrayList<NBAPlayer> nbaPlayers) {
        this.nbaPlayers = nbaPlayers;
    }
}