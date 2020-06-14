package com.nbastatsquiz.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.DraftPick;
import com.nbastatsquiz.lst_drafts.LstDraftsContract;
import com.nbastatsquiz.lst_drafts.LstDraftsPresenter;
import com.nbastatsquiz.lst_league_leaders.LstLeagueLeaderPresenter;

import java.util.ArrayList;

public class Main3Activity_pruebaDraft extends AppCompatActivity implements LstDraftsContract.View {

    private LstDraftsPresenter lstDraftsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Bundle bundle = new Bundle();

        lstDraftsPresenter = new LstDraftsPresenter(this);
        lstDraftsPresenter.getDrafts(bundle);


    }

    @Override
    public void successListDrafts(ArrayList<DraftPick> draftPicks) {

        draftPicks.get(0);
        String h = "";
    }

    @Override
    public void failureListDrafts(String message) {

    }
}
