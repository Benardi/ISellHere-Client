package com.example.grupoes.projetoes.presentation.presenters.impl;

import android.util.Log;

import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.DeletePointOfSaleBean;
import com.example.grupoes.projetoes.beans.DeleteProductBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.DeletePointInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.DeleteProductInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.GetProductsInteractor;
import com.example.grupoes.projetoes.domain.interactors.impl.DeletePointInteractorImpl;
import com.example.grupoes.projetoes.domain.interactors.impl.DeleteProductInteractorImpl;
import com.example.grupoes.projetoes.domain.interactors.impl.GetProductsInteractorImpl;
import com.example.grupoes.projetoes.domain.repository.PointsRepository;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.presentation.presenters.api.ViewPointPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.ViewProductPresenter;
import com.example.grupoes.projetoes.presentation.presenters.base.AbstractPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmilicic on 12/13/15.
 */
public class ViewProductPresenterImpl extends AbstractPresenter implements ViewProductPresenter, DeleteProductInteractor.Callback {
    private MainThread mainThread;
    private Executor executor;
    private View view;
    private boolean isDeleteRunning;
    private Product product;

    public ViewProductPresenterImpl(Executor executor,
                                    MainThread mainThread,
                                    View view) {
        super(executor, mainThread);
        this.view = view;
        this.executor = executor;
        this.mainThread = mainThread;
        this.isDeleteRunning = false;

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
    public void requestDelete(String productName) {
        if (!isDeleteRunning) {
            isDeleteRunning = true;

            SessionStorage storage =  new SessionStorage(ISellHereApplication.getInstance());
            DeleteProductBean body = new DeleteProductBean(storage.getLoggedUser().getUsername(), productName);
            Log.d("PRODUCT123", body.getRequester());
            Log.d("PRODUCT123", body.getProductName());
            DeleteProductInteractor interactor = new DeleteProductInteractorImpl(executor, mainThread, this, body);
            interactor.execute();

            view.setPresenter(this);
        }
    }

    @Override
    public void onDeleteComplete() {
        isDeleteRunning = false;
        view.onSuccessfulDeletion();
    }

    @Override
    public void onDeleteFailed(String error) {
        isDeleteRunning = false;
        onError(error);
    }
}
