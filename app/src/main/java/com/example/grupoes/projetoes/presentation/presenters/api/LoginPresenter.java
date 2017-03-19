package com.example.grupoes.projetoes.presentation.presenters.api;


import com.example.grupoes.projetoes.beans.LoginBean;
import com.example.grupoes.projetoes.beans.RegistrationBean;
import com.example.grupoes.projetoes.presentation.presenters.base.BasePresenter;
import com.example.grupoes.projetoes.presentation.ui.api.BaseView;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.List;

public interface LoginPresenter extends BasePresenter {

    interface View extends BaseView {
        void onSuccessfulLogin();
        void onInvalidInput(List<InvalidInput> invalidInputs);
        void onSkip();
        void setPresenter(LoginPresenter presenter);
    }

    void checkLoginStatus();
    void requestLogin(String username, String password);
}
