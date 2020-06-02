package com.nbaplayersandroid.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.nbaplayersandroid.R;
import com.nbaplayersandroid.beans.NBAPlayer;
import com.nbaplayersandroid.tools.GenerateImageUrl;
import com.nbaplayersandroid.tools.SessionManagement;

import java.util.ArrayList;


public class FragmentoSettings extends Fragment {

    Button btnSetOptions;
    CheckBox cbSound;
    TextView txtUserName;
    Boolean sound;
    SessionManagement sm;
    Spinner spinnerProfile;
    GenerateImageUrl generateImageUrl;

    private static FragmentoSettings fragmentoSettings;

    public FragmentoSettings() {
        // Required empty public constructor
    }

    public static FragmentoSettings newInstance(Bundle datos) {
        if (fragmentoSettings == null) {
            fragmentoSettings =
                    new FragmentoSettings();
        }

        if (datos != null) {
            fragmentoSettings.setArguments(datos);
        }
        return fragmentoSettings;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_settings, container, false);

        sm = new SessionManagement(getContext());
        generateImageUrl = new GenerateImageUrl();
        btnSetOptions = view.findViewById(R.id.btnSetOptions);
        cbSound = view.findViewById(R.id.checkboxSoundOptions);
        txtUserName = view.findViewById(R.id.txtUserNameOptions);
        spinnerProfile = view.findViewById(R.id.spinnerProfilePicture);

        txtUserName.setText(sm.getSessionUserName());

        if(sm.getSound() == true){
            cbSound.setChecked(true);
        }


        ArrayList<NBAPlayer> nbaPlayers = generateImageUrl.getNBAPlayers();
        ArrayAdapter<NBAPlayer> adapter = new ArrayAdapter<NBAPlayer>(getContext(), R.layout.support_simple_spinner_dropdown_item, nbaPlayers);
        spinnerProfile.setAdapter(adapter);


        btnSetOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cbSound.isChecked()) {
                    sound = true;

                } else {
                    sound = false;
                }

                //cogemos jugador seleccionado y pasamos la url de su imagen
                NBAPlayer nbaPlayer = (NBAPlayer) spinnerProfile.getSelectedItem();

                sm.saveSession(txtUserName.getText().toString(), sound, nbaPlayer.getUrlImage());




            }
        });


        return view;
    }
}
