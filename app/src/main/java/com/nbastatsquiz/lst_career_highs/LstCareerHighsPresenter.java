package com.nbastatsquiz.lst_career_highs;

import android.os.Bundle;

import com.nbastatsquiz.beans.CareerHigh;

import java.util.ArrayList;

public class LstCareerHighsPresenter implements LstCareerHighsContract.Presenter{

    private LstCareerHighsContract.View vista;
    private LstCareerHighsModel modelo;

    public LstCareerHighsPresenter(LstCareerHighsContract.View vista) {
        this.vista = vista;
        this.modelo = new LstCareerHighsModel();
    }

    @Override
    public void getCareerHighs(Bundle params) {

        this.modelo.getCareerHighService(new LstCareerHighsContract.Model.OnLstCareerHighsListener() {
            @Override
            public void onFinished(ArrayList<CareerHigh> careerHighs) {
                vista.successListCareerHighs(careerHighs);
            }

            @Override
            public void onFailure(String error) {
                vista.failureListCareerHighs(error);
            }
        }, params);



    }
}
