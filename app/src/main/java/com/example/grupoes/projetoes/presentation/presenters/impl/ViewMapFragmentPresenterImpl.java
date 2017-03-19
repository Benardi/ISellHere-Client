package com.example.grupoes.projetoes.presentation.presenters.impl;

import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.GetPointsInteractor;
import com.example.grupoes.projetoes.domain.interactors.impl.GetPointsInteractorImpl;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.presentation.presenters.api.ViewMapFragmentPresenter;
import com.example.grupoes.projetoes.presentation.presenters.base.AbstractPresenter;


import java.util.List;

/**
 * Created by dmilicic on 12/13/15.
 */
public class ViewMapFragmentPresenterImpl extends AbstractPresenter implements ViewMapFragmentPresenter, GetPointsInteractor.Callback {
    private MainThread mainThread;
    private Executor executor;
    private View view;
    private boolean isRunningFetch;

    private List<PointOfSale> pointsOfSale;

    public ViewMapFragmentPresenterImpl(Executor executor,
                                        MainThread mainThread,
                                        View view) {
        super(executor, mainThread);
        this.view = view;
        this.executor = executor;

        this.mainThread = mainThread;
        this.isRunningFetch = false;

        view.setPresenter(this);
    }

    @Override
    public void resume() {
        requestFetch();
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
        isRunningFetch = false;
        view.showError(message);
    }

    @Override
    public void onRetrievalFailed(String error) {
        isRunningFetch = false;
        onError(error);
    }

    @Override
    public void onPointsRetrieved(List<PointOfSale> pointsOfSale) {
        this.pointsOfSale = pointsOfSale;
        isRunningFetch = false;
        view.onSuccessfulFetch(pointsOfSale);
    }

    @Override
    public void requestFetch() {
        if (!isRunningFetch) {
            isRunningFetch = true;
            GetPointsInteractor interactor = new GetPointsInteractorImpl(executor, mainThread, this);
            interactor.execute();
        }
    }
}
