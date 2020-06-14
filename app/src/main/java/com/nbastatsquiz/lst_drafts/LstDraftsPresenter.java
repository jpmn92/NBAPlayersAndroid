package com.nbastatsquiz.lst_drafts;

import android.os.Bundle;

import com.nbastatsquiz.beans.DraftPick;

import java.util.ArrayList;

public class LstDraftsPresenter implements LstDraftsContract.Presenter{

    private LstDraftsContract.View vista;
    private LstDraftsModel modelo;

    public LstDraftsPresenter(LstDraftsContract.View vista) {
        this.vista = vista;
        this.modelo = new LstDraftsModel();
    }

    @Override
    public void getDrafts(Bundle params) {

        this.modelo.getDraftService(new LstDraftsContract.Model.OnLstDraftListener() {
            @Override
            public void onFinished(ArrayList<DraftPick> draftPicks) {
                vista.successListDrafts(draftPicks);
            }

            @Override
            public void onFailure(String error) {
                vista.failureListDrafts(error);
            }
        }, params);



    }
}

