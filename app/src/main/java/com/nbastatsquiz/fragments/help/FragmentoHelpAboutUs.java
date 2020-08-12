package com.nbastatsquiz.fragments.help;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nbastatsquiz.R;


public class FragmentoHelpAboutUs extends Fragment {


    private static FragmentoHelpAboutUs fragmentoHelpAboutUs;

    TextView linkBowl, linkBowlTerms, linkBowlPrivacy;
    ImageView ig, gp;


    public FragmentoHelpAboutUs() {
        // Required empty public constructor
    }

    public static FragmentoHelpAboutUs newInstance(Bundle datos) {
        if (fragmentoHelpAboutUs == null) {
            fragmentoHelpAboutUs =
                    new FragmentoHelpAboutUs();
        }

        if (datos != null) {
            fragmentoHelpAboutUs.setArguments(datos);
        }
        return fragmentoHelpAboutUs;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_help_about_us, container, false);

        linkBowl = view.findViewById(R.id.linkBowl);
        linkBowlTerms = view.findViewById(R.id.linkBowlTerms);
        linkBowlPrivacy = view.findViewById(R.id.linkBowlPrivacy);
        ig = view.findViewById(R.id.followIG);
        gp = view.findViewById(R.id.rateGP);

        gp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateMe(v);
            }


        });

        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIg(v);
            }
        });






        return view;
    }

// Metodo para abrir la app en google play
    public void rateMe(View view){
        try{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+ getActivity().getPackageName())));
        }catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+getActivity().getPackageName())));
        }
    }

    public void openIg(View view){
        Uri uri = Uri.parse("http://instagram.com/_u/bowlofricedev");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/bowlofricedev")));
        }
    }
}
