package com.nbastatsquiz.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nbastatsquiz.NavigationDrawerActivity;
import com.nbastatsquiz.R;
import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.NBAPlayer;
import com.nbastatsquiz.tools.GenerateImageUrl;
import com.nbastatsquiz.tools.SessionManagement;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentoSettings extends Fragment {

    Button btnSetOptions;
    CheckBox cbSound;
    TextView txtUserName;
    Boolean sound;
    SessionManagement sm;
    Spinner spinnerProfile;
    GenerateImageUrl generateImageUrl;
    ImageView ivAvatar;
    CircleImageView circleImageView;
    NavigationDrawerActivity navigationDrawerActivity;

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

        initComponents(view);


        txtUserName.setText(sm.getSessionUserName());

        if (sm.getSound() == true) {
            cbSound.setChecked(true);
        }


        ArrayList<NBAPlayer> nbaPlayers = generateImageUrl.getNBAPlayers();

        //ordenamos array
        if(nbaPlayers.size() > 0){
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
        if(sm.getSesionImage() != ""){

            for(int i = 0; i<nbaPlayers.size(); i++){

                if(nbaPlayers.get(i).getUrlImage().equalsIgnoreCase(sm.getSesionImage())){
                    spinnerProfile.setSelection(i);
                }

            }


        }


        btnSetOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sound = cbSound.isChecked();

                //cogemos jugador seleccionado y pasamos la url de su imagen
                NBAPlayer nbaPlayer = (NBAPlayer) spinnerProfile.getSelectedItem();

                String userName = txtUserName.getText().toString();

                sm.saveSession(txtUserName.getText().toString(), sound, nbaPlayer.getUrlImage());

                Toast.makeText(getContext(), R.string.config_updated, Toast.LENGTH_SHORT).show();

            }
        });

        spinnerProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NBAPlayer nbaPlayer = (NBAPlayer) spinnerProfile.getSelectedItem();
                Picasso.with(getContext()).load(nbaPlayer.getUrlImage()).into(circleImageView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }



    private void initComponents(View view) {

        btnSetOptions = view.findViewById(R.id.btnSetOptions);
        cbSound = view.findViewById(R.id.checkboxSoundOptions);
        txtUserName = view.findViewById(R.id.txtUserNameOptions);
        spinnerProfile = view.findViewById(R.id.spinnerProfilePicture);
//        ivAvatar = view.findViewById(R.id.ivAvatar);
        circleImageView = view.findViewById(R.id.ivAvatar);
        Picasso.with(getContext()).load(sm.getSesionImage()).into(circleImageView);

    }
}
