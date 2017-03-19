package com.example.grupoes.projetoes.presentation.presenters.api;


import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditPointOfSaleBean;
import com.example.grupoes.projetoes.presentation.presenters.base.BasePresenter;
import com.example.grupoes.projetoes.presentation.ui.api.BaseView;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.List;

public interface EditPointPresenter extends BasePresenter {

    interface View extends BaseView {
        void onSuccessfulEdit();
        void onInvalidInput(List<InvalidInput> invalidInputs);
    }

    void requestEditPoint(String requester, String pointName, String newName, String pointDescription, String pointImageStr);
}
