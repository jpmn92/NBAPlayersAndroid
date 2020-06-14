package com.nbastatsquiz.lst_career_highs;

import android.os.Bundle;

import com.nbastatsquiz.beans.CareerHigh;

import java.util.ArrayList;

public interface LstCareerHighsContract {

    public interface View{
        void successListCareerHighs(ArrayList<CareerHigh> careerHighs);
        void failureListCareerHighs(String message);
    }

    public interface Presenter{
        void getCareerHighs(Bundle params);
    }

    public interface Model{
        interface OnLstCareerHighsListener{
            void onFinished(ArrayList<CareerHigh> careerHighs);
            void onFailure(String error);
        }

        void getCareerHighService(

                OnLstCareerHighsListener onLstCareerHighsListener, Bundle params
        );
    }

}
