package com.example.grupoes.projetoes.presentation.presenters.impl;

import android.util.Log;

import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditPointOfSaleBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.AddPointInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.EditPointInteractor;
import com.example.grupoes.projetoes.domain.interactors.impl.AddPointInteractorImpl;
import com.example.grupoes.projetoes.domain.interactors.impl.EditPointInteractorImpl;
import com.example.grupoes.projetoes.presentation.presenters.api.AddPointFragmentPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.EditPointPresenter;
import com.example.grupoes.projetoes.presentation.presenters.base.AbstractPresenter;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InputValidator;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmilicic on 12/13/15.
 */
public class EditPointPresenterImpl extends AbstractPresenter implements EditPointPresenter, EditPointInteractorImpl.Callback {
    private MainThread mainThread;
    private Executor executor;
    private View view;
    private boolean isRunningEditPoint;

    public EditPointPresenterImpl(Executor executor,
                                  MainThread mainThread,
                                  View view) {
        super(executor, mainThread);
        this.view = view;
        this.executor = executor;
        this.mainThread = mainThread;
        this.isRunningEditPoint = false;
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
    public void onEditedPoint() {
        isRunningEditPoint = false;
        Log.d("RUNNING", "SUCCESS EDITPOINT");
        view.onSuccessfulEdit();
    }

    @Override
    public void onEditFailed(String error) {
        isRunningEditPoint = false;
        onError(error);
    }

    @Override
    public void requestEditPoint(String requester, String pointName, String newName, String pointDescription, String pointImageStr) {
        if (!isRunningEditPoint) {
            List<InvalidInput> invalidInputs = new ArrayList<>();

            if (InputValidator.isNameInvalid(newName)) {
                invalidInputs.add(new InvalidInput(InputType.ADDPOINT_NAME, "The name must have 1 to 20 characters and no special symbols!"));
            }

            if (InputValidator.isDescriptionInvalid(pointDescription)) {
                invalidInputs.add(new InvalidInput(InputType.ADDPOINT_DESCRIPTION, "The description must have 0 to 255 characters and no special symbols!"));
            }

            if (invalidInputs.size() == 0) {
                isRunningEditPoint = true;
                Log.d("RUNNING", "IT HAS RAN EDITPOINT");
                EditPointOfSaleBean body = new EditPointOfSaleBean(requester, pointName, newName, pointDescription, pointImageStr);
                EditPointInteractor interactor = new EditPointInteractorImpl(executor, mainThread, this, body);
                interactor.execute();
            } else {
                view.onInvalidInput(invalidInputs);
            }
        }
    }
}
