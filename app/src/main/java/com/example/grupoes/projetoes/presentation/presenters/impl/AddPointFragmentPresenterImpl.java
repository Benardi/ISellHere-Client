package com.example.grupoes.projetoes.presentation.presenters.impl;

import android.util.Log;

import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.LoginBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.AddPointInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.LoginInteractor;
import com.example.grupoes.projetoes.domain.interactors.impl.AddPointInteractorImpl;
import com.example.grupoes.projetoes.domain.interactors.impl.LoginInteractorImpl;
import com.example.grupoes.projetoes.presentation.presenters.api.AddPointFragmentPresenter;
import com.example.grupoes.projetoes.presentation.presenters.base.AbstractPresenter;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InputValidator;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmilicic on 12/13/15.
 */
public class AddPointFragmentPresenterImpl extends AbstractPresenter implements AddPointFragmentPresenter, AddPointInteractorImpl.Callback {
    private MainThread mainThread;
    private Executor executor;
    private AddPointFragmentPresenter.View view;
    private boolean isRunningAddPoint;

    public AddPointFragmentPresenterImpl(Executor executor,
                                         MainThread mainThread,
                                         AddPointFragmentPresenter.View view) {
        super(executor, mainThread);
        this.view = view;
        this.executor = executor;
        this.mainThread = mainThread;
        isRunningAddPoint = false;
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
    public void onAddedPoint() {
        isRunningAddPoint = false;
        view.onSuccessfulAdd();
    }

    @Override
    public void onAddFailed(String error) {
        isRunningAddPoint = false;
        onError(error);
    }

    @Override
    public void requestAddPoint(String creatorName, String pointName, double pointLongitude, double pointLatitude, String pointComment, String pointImage) {
        if (!isRunningAddPoint) {
            List<InvalidInput> invalidInputs = new ArrayList<>();

            if (InputValidator.isNameInvalid(pointName)) {
                invalidInputs.add(new InvalidInput(InputType.ADDPOINT_NAME, "The name must have 1 to 20 characters and no special symbols!"));
            }

            if (InputValidator.isDescriptionInvalid(pointComment)) {
                invalidInputs.add(new InvalidInput(InputType.ADDPOINT_DESCRIPTION, "The description must have 0 to 255 characters and no special symbols!"));
            }

            if (invalidInputs.size() == 0) {
                isRunningAddPoint = true;
                AddPointOfSaleBean body = new AddPointOfSaleBean(creatorName, pointName, pointLongitude, pointLatitude, pointComment, pointImage);
                AddPointInteractor interactor = new AddPointInteractorImpl(executor, mainThread, this, body);
                interactor.execute();
            } else {
                view.onInvalidInput(invalidInputs);
            }
        }
    }
}
