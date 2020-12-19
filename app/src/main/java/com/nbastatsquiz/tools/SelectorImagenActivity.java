package com.nbastatsquiz.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.bumptech.glide.Glide;
import com.nbastatsquiz.R;
import com.nbastatsquiz.adaptador.AdapterAvatar;
import com.nbastatsquiz.beans.NBAPlayer;
import com.nbastatsquiz.fragments.FragmentoAccount;
import com.nbastatsquiz.fragments.auth.FragmentoRegister;

import java.util.ArrayList;

public class SelectorImagenActivity extends DialogFragment {


    private static GridView grid;
    private FragmentoAccount fragmentoAccount;
    private FragmentoRegister fragmentoRegister;
    private EditText txtSearchAvatar;
    private ArrayList<NBAPlayer> nbaPlayers;
    private ArrayList<NBAPlayer> avatares;

    public SelectorImagenActivity() {
    }

    public SelectorImagenActivity(FragmentoAccount fragmentoAccount){
        this.fragmentoAccount = fragmentoAccount;
        this.fragmentoRegister = null;
    }

    public SelectorImagenActivity(FragmentoRegister fragmentoRegister){
        this.fragmentoRegister = fragmentoRegister;
        this.fragmentoAccount = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.selector_imagenes, null);

        builder.setView(view);

        nbaPlayers = new ArrayList<>();
        avatares = new ArrayList<>();
        grid = view.findViewById(R.id.grid);

        if(fragmentoAccount != null){
            nbaPlayers.addAll(fragmentoAccount.getNbaPlayers());
            avatares.addAll(fragmentoAccount.getNbaPlayers());
        }
        else if (fragmentoRegister != null){
            nbaPlayers.addAll(fragmentoRegister.getNbaPlayers());
            avatares.addAll(fragmentoRegister.getNbaPlayers());
        }

        txtSearchAvatar = view.findViewById(R.id.txtSearchAvatar);

        txtSearchAvatar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                avatares = searchAvatar(s.toString());
                loadAvatars();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loadAvatars();

        return builder.create();
    }

    private void loadAvatars() {
        if(fragmentoAccount != null){
            grid.setAdapter(new AdapterAvatar(this.getContext(), avatares));

            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    NBAPlayer nbaPlayer = (NBAPlayer) grid.getAdapter().getItem(position);
                    fragmentoAccount.setUrlFromDialog(nbaPlayer.getUrlImage());
                    Glide.with(getContext()).load(nbaPlayer.getUrlImage()).into(fragmentoAccount.getCircleImageView());
                    dismiss();
                }
            });
        }

        if(fragmentoRegister != null){
            grid.setAdapter(new AdapterAvatar(this.getContext(), avatares));

            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    NBAPlayer nbaPlayer = (NBAPlayer) grid.getAdapter().getItem(position);
                    fragmentoRegister.setNbaPlayer(nbaPlayer);
                    Glide.with(getContext()).load(nbaPlayer.getUrlImage()).into(fragmentoRegister.getCircleImageView());
                    dismiss();
                }
            });
        }
    }

    private ArrayList<NBAPlayer> searchAvatar(String search) {
        ArrayList<NBAPlayer> nbaPlayersAux = new ArrayList<>();
        if(!search.equalsIgnoreCase("") && search != null){
            //laLigaPlayersAux.addAll(laLigaPlayers);
            nbaPlayersAux.clear();
            for(NBAPlayer nbaPlayer: nbaPlayers){

                if(nbaPlayer.getName().toUpperCase().contains(search.toUpperCase())){
                    nbaPlayersAux.add(nbaPlayer);
                }
            }
        }
        else{
            if(fragmentoAccount != null){
                nbaPlayersAux.addAll(fragmentoAccount.getNbaPlayers());
            }
            else if (fragmentoRegister != null){
                nbaPlayersAux.addAll(fragmentoRegister.getNbaPlayers());
            }
        }

        return nbaPlayersAux;
    }
}
