package com.example.grupoes.projetoes.presentation.presenters.impl;

import android.util.Log;

import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.LoginBean;
import com.example.grupoes.projetoes.beans.RegistrationBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.LoginInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.RegistrationInteractor;
import com.example.grupoes.projetoes.domain.interactors.impl.LoginInteractorImpl;
import com.example.grupoes.projetoes.domain.interactors.impl.RegistrationInteractorImpl;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.presentation.presenters.api.LoginPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.RegistrationPresenter;
import com.example.grupoes.projetoes.presentation.presenters.base.AbstractPresenter;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InputValidator;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmilicic on 12/13/15.
 */
public class LoginPresenterImpl extends AbstractPresenter implements LoginPresenter, LoginInteractorImpl.Callback {
    private MainThread mainThread;
    private Executor executor;
    private View view;

    private boolean isRunningLogin;

    public LoginPresenterImpl(Executor executor,
                              MainThread mainThread,
                              View view) {
        super(executor, mainThread);
        this.view = view;
        this.executor = executor;
        this.mainThread = mainThread;
        this.isRunningLogin = false;

        view.setPresenter(this);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        view.showError(message);
    }

    @Override
    public void onLoginComplete() {
        isRunningLogin = false;
        view.onSuccessfulLogin();
    }

    @Override
    public void onLoginFailed(String error) {
        isRunningLogin = false;
        onError(error);
    }

    @Override
    public void checkLoginStatus() {
        if (new SessionStorage(ISellHereApplication.getInstance()).isUserLoggedIn()) {
            view.onSkip();
        }
    }

    @Override
    public void requestLogin(String username, String password) {
        if (!isRunningLogin) {
            List<InvalidInput> invalidInputs = new ArrayList<>();

            if (InputValidator.isEmpty(username)) {
                invalidInputs.add(new InvalidInput(InputType.LOGIN_USERNAME, "Username cannot be empty!"));
            }

            if (InputValidator.isEmpty(password)) {
                invalidInputs.add(new InvalidInput(InputType.LOGIN_PASSWORD, "Password cannot be empty!"));
            }

            if (invalidInputs.size() == 0) {
                LoginBean body = new LoginBean(username, password);
                LoginInteractor interactor = new LoginInteractorImpl(executor, mainThread, this, body);
                isRunningLogin = true;
                interactor.execute();
            } else {
                view.onInvalidInput(invalidInputs);
            }
        }
    }
}
