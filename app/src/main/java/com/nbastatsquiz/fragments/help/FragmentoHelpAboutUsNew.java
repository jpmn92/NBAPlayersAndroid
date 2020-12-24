package com.nbastatsquiz.fragments.help;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nbastatsquiz.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class FragmentoHelpAboutUsNew extends Fragment {


    private static FragmentoHelpAboutUsNew fragmentoHelpAboutUsNew;

    private String version;
    private Element versionElement;

    public FragmentoHelpAboutUsNew() {
        // Required empty public constructor
    }

    public static FragmentoHelpAboutUsNew newInstance(Bundle datos) {
        if (fragmentoHelpAboutUsNew == null) {
            fragmentoHelpAboutUsNew =
                    new FragmentoHelpAboutUsNew();
        }

        if (datos != null) {
            fragmentoHelpAboutUsNew.setArguments(datos);
        }
        return fragmentoHelpAboutUsNew;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkVersion();



    }

    private void checkVersion() {

        //comprobaciones de version
        PackageInfo packageInfo;

        try {

            packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version = packageInfo.versionName; //version actual instalada

            versionElement = new Element();
            versionElement.setTitle("Version "+version);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new AboutPage(getContext())
                .isRTL(false)
                .setImage(R.drawable.logo_bowl)
                .setDescription(getString(R.string.aboutUs_paragraph))
                .addEmail("hello@bowlofricedev.com", "Envíanos un e-mail")
                .addTwitter("bowlofricedev")
                .addInstagram("bowlofricedev")
                .addPlayStore("com.nbastatsquiz")
                .addWebsite("https://bowlofricedev.com/")
                .addItem(versionElement)
                .create();
    }


}
