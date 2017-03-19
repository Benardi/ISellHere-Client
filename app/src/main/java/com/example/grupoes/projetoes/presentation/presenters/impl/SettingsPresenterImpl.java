package com.example.grupoes.projetoes.presentation.presenters.impl;

import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditPointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditUserBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.AddPointInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.EditPasswordInteractor;
import com.example.grupoes.projetoes.domain.interactors.impl.AddPointInteractorImpl;
import com.example.grupoes.projetoes.domain.interactors.impl.EditPasswordInteractorImpl;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.User;
import com.example.grupoes.projetoes.presentation.presenters.api.AddPointFragmentPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.SettingsPresenter;
import com.example.grupoes.projetoes.presentation.presenters.base.AbstractPresenter;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InputValidator;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmilicic on 12/13/15.
 */
public class SettingsPresenterImpl extends AbstractPresenter implements SettingsPresenter, EditPasswordInteractorImpl.Callback {
    private MainThread mainThread;
    private Executor executor;
    private View view;
    private boolean isRunningEditUser;

    public SettingsPresenterImpl(Executor executor,
                                 MainThread mainThread,
                                 View view) {
        super(executor, mainThread);
        this.view = view;
        this.executor = executor;
        this.mainThread = mainThread;
        this.isRunningEditUser = false;

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
    public void onEditComplete() {
        isRunningEditUser = false;
        view.onSuccessfulEdit();
    }

    @Override
    public void onEditFailed(String error) {
        isRunningEditUser = false;
        onError(error);
    }

    @Override
    public void requestEditPassword(String username, String newPassword, String oldPassword) {
        if (!isRunningEditUser) {
            List<InvalidInput> invalidInputs = new ArrayList<>();

            if (InputValidator.isEmpty(oldPassword)) {
                invalidInputs.add(new InvalidInput(InputType.SETTINGS_OLDPASSWORD, "Username cannot be empty!"));
            }

            if (InputValidator.isPasswordInvalid(newPassword)) {
                invalidInputs.add(new InvalidInput(InputType.SETTINGS_NEWPASSWORD, "Password should have at least 6 characters, one number and one letter! No special symbols are allowed!"));
            }

            if (invalidInputs.size() == 0) {
                EditUserBean body = new EditUserBean(username, newPassword, oldPassword);
                isRunningEditUser = true;
                EditPasswordInteractor interactor = new EditPasswordInteractorImpl(executor, mainThread, this, body);
                interactor.execute();
            } else {
                view.onInvalidInput(invalidInputs);
            }
        }
    }
}
