package com.example.grupoes.projetoes.presentation.presenters.api;


import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditPointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditUserBean;
import com.example.grupoes.projetoes.presentation.presenters.base.BasePresenter;
import com.example.grupoes.projetoes.presentation.ui.api.BaseView;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.List;

public interface SettingsPresenter extends BasePresenter {

    interface View extends BaseView {
        void onSuccessfulEdit();
        void setPresenter(SettingsPresenter presenter);
        void onInvalidInput(List<InvalidInput> invalidInputs);
    }

    void requestEditPassword(String username, String newPassword, String oldPassword);
}
