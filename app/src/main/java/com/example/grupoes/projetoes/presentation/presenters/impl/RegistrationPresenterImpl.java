package com.example.grupoes.projetoes.presentation.presenters.impl;

import android.util.Log;

import com.example.grupoes.projetoes.beans.RegistrationBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.RegistrationInteractor;
import com.example.grupoes.projetoes.domain.interactors.impl.RegistrationInteractorImpl;
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
public class RegistrationPresenterImpl extends AbstractPresenter implements RegistrationPresenter, RegistrationInteractorImpl.Callback {
    private MainThread mainThread;
    private Executor executor;
    private RegistrationPresenter.View view;
    private boolean isRunningRegistration;

    public RegistrationPresenterImpl(Executor executor,
                                     MainThread mainThread,
                                     View view) {
        super(executor, mainThread);
        this.view = view;
        this.executor = executor;
        this.mainThread = mainThread;
        this.isRunningRegistration = false;

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
    public void onRegistrationComplete() {
        isRunningRegistration = false;
        view.onSuccessfulRegistration();
    }

    @Override
    public void onRegistrationFailed(String error) {
        isRunningRegistration = false;
        onError(error);
    }

    @Override
    public void requestRegistration(String username, String email, String password, String confirmPassword) {
        if (!isRunningRegistration) {
            List<InvalidInput> invalidInputs = checkRegistration(username, email, password, confirmPassword);

            if (invalidInputs.size() == 0) {
                RegistrationBean body = new RegistrationBean(username, password, email);
                RegistrationInteractor interactor = new RegistrationInteractorImpl(executor, mainThread, this, body);
                isRunningRegistration = true;
                interactor.execute();
            } else {
                view.onInvalidInput(invalidInputs);
            }
        }
    }

    private List<InvalidInput> checkRegistration(String username, String email, String password, String confirmPassword) {
        List<InvalidInput> invalidInputs = new ArrayList<>();

        if (!password.equals(confirmPassword)) {
            invalidInputs.add(new InvalidInput(InputType.REG_CONFIRM_PASSWORD, "Passwords do not match!"));
        }

        if (InputValidator.isEmailInvalid(email)) {
            invalidInputs.add(new InvalidInput(InputType.REG_EMAIL, "Insert a valid email!"));
        }

        if (InputValidator.isUsernameInvalid(username)) {
            invalidInputs.add(new InvalidInput(InputType.REG_USERNAME, "Username should have 4 to 15 characters. Between letters and numbers."));
        }

        if (InputValidator.isPasswordInvalid(password)) {
            invalidInputs.add(new InvalidInput(InputType.REG_PASSWORD, "Password should have at least 6 characters, one number and one letter! No special symbols are allowed!"));
        }

        return invalidInputs;
    }
}
