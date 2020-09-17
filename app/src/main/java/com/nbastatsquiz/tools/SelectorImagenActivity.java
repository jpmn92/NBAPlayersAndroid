package com.nbastatsquiz.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bumptech.glide.Glide;
import com.nbastatsquiz.R;
import com.nbastatsquiz.adaptador.AdapterAvatar;
import com.nbastatsquiz.beans.NBAPlayer;
import com.nbastatsquiz.fragments.FragmentoAccount;

public class SelectorImagenActivity extends DialogFragment {


    private static GridView grid;
    private FragmentoAccount fragmentoAccount;

    public SelectorImagenActivity() {
    }

    public SelectorImagenActivity(FragmentoAccount fragmentoAccount){
        this.fragmentoAccount = fragmentoAccount;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.selector_imagenes, null);

        builder.setView(view);

        grid = view.findViewById(R.id.grid);

        grid.setAdapter(new AdapterAvatar(this.getContext(), fragmentoAccount.getNbaPlayers()));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                NBAPlayer nbaPlayer = (NBAPlayer) grid.getAdapter().getItem(position);
                fragmentoAccount.setUrlFromDialog(nbaPlayer.getUrlImage());
                Glide.with(getContext()).load(nbaPlayer.getUrlImage()).into(fragmentoAccount.getCircleImageView());
                dismiss();
            }
        });

        return builder.create();
    }
}
