package com.nbastatsquiz.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nbastatsquiz.R;


public class FragmentoHelpGlosario extends Fragment {


    private static FragmentoHelpGlosario fragmentoHelpGlosario;

    private TextView pts, reb, dreb, oreb, ast, stl, blk, tov, min, fg3pct, fg3m, ftpct, ftm;

    public FragmentoHelpGlosario() {
        // Required empty public constructor
    }

    public static FragmentoHelpGlosario newInstance(Bundle datos) {
        if (fragmentoHelpGlosario == null) {
            fragmentoHelpGlosario =
                    new FragmentoHelpGlosario();
        }

        if (datos != null) {
            fragmentoHelpGlosario.setArguments(datos);
        }
        return fragmentoHelpGlosario;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_help_glosario, container, false);


        initComponents(view);

        String ptstxt = getString(R.string.PTS);
        String rebtxt = getString(R.string.REB);
        String drebtxt = getString(R.string.DREB);
        String orebtxt = getString(R.string.OREB);
        String asttxt = getString(R.string.AST);
        String stltxt = getString(R.string.STL);
        String blktxt = getString(R.string.BLK);
        String tovtxt = getString(R.string.TOV);
        String mintxt = getString(R.string.MIN);
        String fg3pcttxt = getString(R.string.FG3_PCT);
        String fg3mtxt = getString(R.string.FG3M);
        String ftpcttxt = getString(R.string.FT_PCT);
        String ftmtxt = getString(R.string.FTM);

        String hpts = "<b> PTS: </b> "+ptstxt;
        String hrebtxt = "<b>REB:</b> "+rebtxt;
        String hdrebtxt = "<b>DREB:</b> "+drebtxt;
        String horebtxt = "<b>OREB:</b> "+orebtxt;
        String hasttxt = "<b>AST:</b> "+asttxt;
        String hstltxt = "<b>STL:</b> "+stltxt;
        String hblktxt = "<b>BLK: </b>"+blktxt;
        String htovtxt = "<b>TOV</b>: "+tovtxt;
        String hmintxt = "<b>MIN: </b>"+mintxt;
        String hfg3pcttxt = "<b>FG3_PCT:</b> "+fg3pcttxt;
        String hfg3mtxt = "<b>FG3M:</b> "+fg3mtxt;
        String hftpcttxt = "<b>FT_PCT:</b> "+ftpcttxt;
        String hftmtxt = "<b>FTM: </b>"+ftmtxt;

        pts.setText(Html.fromHtml(hpts));
        reb.setText(Html.fromHtml(hrebtxt));
        dreb.setText(Html.fromHtml(hdrebtxt));
        oreb.setText(Html.fromHtml(horebtxt));
        ast.setText(Html.fromHtml(hasttxt));
        stl.setText(Html.fromHtml(hstltxt));
        blk.setText(Html.fromHtml(hblktxt));
        tov.setText(Html.fromHtml(htovtxt));
        min.setText(Html.fromHtml(hmintxt));
        fg3pct.setText(Html.fromHtml(hfg3pcttxt));
        fg3m.setText(Html.fromHtml(hfg3mtxt));
        ftpct.setText(Html.fromHtml(hftpcttxt));
        ftm.setText(Html.fromHtml(hftmtxt));


        return view;
    }

    private void initComponents(View view) {

        pts = view.findViewById(R.id.glossary_PTS);
        reb = view.findViewById(R.id.glossary_REB);
        dreb = view.findViewById(R.id.glossary_DREB);
        oreb = view.findViewById(R.id.glossary_OREB);
        ast = view.findViewById(R.id.glossary_AST);
        stl = view.findViewById(R.id.glossary_STL);
        blk = view.findViewById(R.id.glossary_BLK);
        tov = view.findViewById(R.id.glossary_TOV);
        min = view.findViewById(R.id.glossary_MIN);
        fg3m = view.findViewById(R.id.glossary_FG3M);
        fg3pct = view.findViewById(R.id.glossary_FG3_PCT);
        ftm = view.findViewById(R.id.glossary_FTM);
        ftpct = view.findViewById(R.id.glossary_FT_PCT);
    }


}
