package com.example.grupoes.projetoes.presentation.presenters.api;


import com.example.grupoes.projetoes.beans.RegistrationBean;
import com.example.grupoes.projetoes.presentation.presenters.base.BasePresenter;
import com.example.grupoes.projetoes.presentation.ui.api.BaseView;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.List;

public interface RegistrationPresenter extends BasePresenter {

    interface View extends BaseView {
        void onSuccessfulRegistration();
        void onInvalidInput(List<InvalidInput> invalidInputs);
        void setPresenter(RegistrationPresenter presenter);
    }

    void requestRegistration(String username, String email, String password, String confirmPassword);
}
