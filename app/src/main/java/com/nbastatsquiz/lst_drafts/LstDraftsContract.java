package com.nbastatsquiz.lst_drafts;

import android.os.Bundle;

import com.nbastatsquiz.beans.DraftPick;

import java.util.ArrayList;

public interface LstDraftsContract {

    public interface View{
        void successListDrafts(ArrayList<DraftPick> draftPicks);
        void failureListDrafts(String message);
    }

    public interface Presenter{
        void getDrafts(Bundle params);
    }

    public interface Model{
        interface OnLstDraftListener{
            void onFinished(ArrayList<DraftPick> draftPicks);
            void onFailure(String error);
        }

        void getDraftService(

                OnLstDraftListener onLstDraftListener, Bundle params
        );
    }
}
