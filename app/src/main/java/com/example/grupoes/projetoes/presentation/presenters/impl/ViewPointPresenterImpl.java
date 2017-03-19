package com.example.grupoes.projetoes.presentation.presenters.impl;

import android.util.Log;

import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.beans.DeletePointOfSaleBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.DeletePointInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.GetProductsInteractor;
import com.example.grupoes.projetoes.domain.interactors.impl.DeletePointInteractorImpl;
import com.example.grupoes.projetoes.domain.interactors.impl.GetProductsInteractorImpl;
import com.example.grupoes.projetoes.domain.repository.PointsRepository;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.presentation.presenters.api.ViewPointPresenter;
import com.example.grupoes.projetoes.presentation.presenters.base.AbstractPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmilicic on 12/13/15.
 */
public class ViewPointPresenterImpl extends AbstractPresenter implements ViewPointPresenter, DeletePointInteractor.Callback, GetProductsInteractor.Callback {
    private MainThread mainThread;
    private Executor executor;
    private View view;
    private boolean isDeleteRunning;
    private boolean isFetchProductsRunning;
    private PointOfSale pointOfSale;
    private List<Product> products;

    public ViewPointPresenterImpl(Executor executor,
                                  MainThread mainThread,
                                  View view) {
        super(executor, mainThread);
        this.view = view;
        this.executor = executor;
        this.mainThread = mainThread;
        this.isDeleteRunning = false;
        this.isFetchProductsRunning = false;
        this.products = new ArrayList<>();

        view.setPresenter(this);
        this.pointOfSale = PointsRepository.getInstance().findPointOfSaleByName(view.getPointName());
        Log.d("POINT", view.getPointName());
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
    public PointOfSale getPointOfSale() {
        return pointOfSale;
    }

    @Override
    public List<Product> getProducts() { return products; }

    @Override
    public void requestDelete(String pointName) {
        if (!isDeleteRunning) {
            isDeleteRunning = true;

            SessionStorage storage =  new SessionStorage(ISellHereApplication.getInstance());
            DeletePointOfSaleBean body = new DeletePointOfSaleBean(storage.getLoggedUser().getUsername(), pointName);

            DeletePointInteractor interactor = new DeletePointInteractorImpl(executor, mainThread, this, body);
            interactor.execute();
        }
    }

    @Override
    public void requestFetchProducts() {
        if (!isFetchProductsRunning) {
            isFetchProductsRunning = true;
            GetProductsInteractor interactor = new GetProductsInteractorImpl(executor, mainThread, this, view.getPointName());
            interactor.execute();
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

    @Override
    public void onProductsRetrieved(List<Product> products) {
        isFetchProductsRunning = false;
        this.products = products;
        view.onSucessfulProductsFetch();
    }

    @Override
    public void onProductsRetrievalFailed(String error) {
        isFetchProductsRunning = false;
        onError(error);
    }
}
