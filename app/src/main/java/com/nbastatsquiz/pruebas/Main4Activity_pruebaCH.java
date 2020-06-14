package com.nbastatsquiz.pruebas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.CareerHigh;
import com.nbastatsquiz.lst_career_highs.LstCareerHighsContract;
import com.nbastatsquiz.lst_career_highs.LstCareerHighsPresenter;

import java.util.ArrayList;

public class Main4Activity_pruebaCH extends AppCompatActivity implements LstCareerHighsContract.View {

    private LstCareerHighsPresenter lstCareerHighsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4_prueba_c_h);

        Bundle bundle = new Bundle();


        lstCareerHighsPresenter = new LstCareerHighsPresenter(this);
        lstCareerHighsPresenter.getCareerHighs(bundle);
    }

    @Override
    public void successListCareerHighs(ArrayList<CareerHigh> careerHighs) {

        careerHighs.get(0);
        String h = "";
    }

    @Override
    public void failureListCareerHighs(String message) {

    }
}
